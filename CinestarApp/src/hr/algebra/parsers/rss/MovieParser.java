package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.utils.DateUtils;
import hr.algebra.utils.FileUtils;
import hr.algebra.utils.TextUtils;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author filip
 */
public class MovieParser {

    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final int TIMEOUT = 10_000;
    private static final String REQUEST_METHOD = "GET";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;
    private static final DateTimeFormatter START_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Random RANDOM = new Random();

    public static List<Movie> parse() throws IOException, XMLStreamException, ParseException {
        List<Movie> movies = new ArrayList<>();

        HttpsURLConnection con = UrlConnectionFactory.getHttpsUrlConnection(RSS_URL, TIMEOUT, REQUEST_METHOD);
        XMLEventReader reader = ParserFactory.createStaxParser(con.getInputStream());

        Optional<TagType> tagType = Optional.empty();
        Movie movie = null;
        StartElement startElement = null;
        int firstIteration = 0;

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();                //get next item to read
            switch (event.getEventType()) {                     //split into item header and item data
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();      //get header and save it to startElement field
                    String qName = startElement.getName().getLocalPart();
                    tagType = TagType.parseFromString(qName); //get item tags and parse them into enums
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (tagType.isPresent()) {                  //check if there is data in item
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim();
                        switch (tagType.get()) {
                            case ITEM:
                                movie = new Movie();
                            case TITLE:
                                firstIteration++;
                                if (movie != null && !data.isEmpty() && firstIteration > 1) {
                                    String title = TextUtils.stripData(data);
                                    if (TextUtils.checkTitle(title)) {                      //making sure to not add same movies with 3D, IMAX...
                                        movies.add(movie);
                                        movie.setTitle(title);
                                    }
                                }
                                break;
                            case PUB_DATE:
                                if (movie != null && !data.isEmpty()) {
                                    LocalDateTime pubDate = LocalDateTime.parse(data, DATE_FORMATTER);
                                    movie.setPublishDate(pubDate);
                                }
                                break;
                            case DESCRIPTION:
                                if (movie != null && !data.isEmpty() && firstIteration > 1) {
                                    data = TextUtils.stripData(data);
                                    data = TextUtils.cleanText(data);               //removes all unnecessary data
                                    movie.setDescription(data);
                                }
                                break;
                            case ORIG_TITLE:
                                if (movie != null && !data.isEmpty()) {
                                    String originalTitle = data;
                                    movies.removeIf(m -> originalTitle.equals(m.getOriginalTitle()));       //removes duplicates with translated title
                                    movie.setOriginalTitle(originalTitle);
                                }
                                break;
                            case DIRECTOR:
                                if (movie != null && !data.isEmpty()) {
                                    String director = data;
                                    String firstName = director.substring(0, director.indexOf(" "));
                                    String lastName = director.substring(director.indexOf(" ") + 1);
                                    movie.setDirector(new Director(firstName, lastName));
                                }
                                break;
                            case ACTORS:
                                if (movie != null && !data.isEmpty()) {
                                    List<Actor> actors = new ArrayList<>();
                                    String newContent = data;
                                    String[] acs = newContent.split(", ");
                                    for (String actor : acs) {
                                        String[] details = actor.split(" ");
                                        if (details.length >= 2) {
                                            actors.add(new Actor(details[0], details[details.length - 1]));
                                        } else {
                                            actors.add(new Actor(details[0]));
                                        }
                                        movie.setActors(actors);
                                    }
                                }
                                break;
                            case DURATION:
                                if (movie != null && !data.isEmpty()) {
                                    String duration = data;
                                    movie.setDuration(Integer.valueOf(duration));
                                }
                                break;
                            case GENRE:
                                if (movie != null && !data.isEmpty()) {
                                    String genre = data;
                                    movie.setGenre(genre);
                                }
                                break;
                            case IMAGE_URL:
                                if (movie != null && !data.isEmpty()) {
                                    String imageUrl = data;
                                    if (imageUrl != null) {
                                        imageUrl = TextUtils.prepareLink(imageUrl);
                                        handlePicture(movie, imageUrl);
                                    }
                                }
                                break;
                            case LINK:
                                if (movie != null && !data.isEmpty() && firstIteration > 1) {
                                    String link = data;
                                    movie.setLink(link);
                                }
                                break;
                            case START_DATE:
                                if (movie != null && !data.isEmpty()) {
                                    String date = DateUtils.parseDate(data, "d.M.yyyy", "yyyy-MM-dd");
                                    LocalDateTime startDate = LocalDate.parse(date, START_DATE_FORMATTER).atStartOfDay();
                                    movie.setStartDate(startDate);
                                }
                                break;
                            default:
                                System.out.println("Banana...");
                                break;
                        }
                    }
            }
        }
        return movies;
    }

    private static void handlePicture(Movie movie, String imageUrl) throws IOException {
        String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String image = Math.abs(RANDOM.nextInt()) + ext;
        String localPicturePath = DIR + File.separator + image;
        FileUtils.copyFromUrl(imageUrl, localPicturePath);
        movie.setPicturePath(localPicturePath);
    }

}
