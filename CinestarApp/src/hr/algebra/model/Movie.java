package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author filip
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"title", "originalTitle", "publishDate", "description", "director", "actors", "duration", "genre", "picturePath", "link", "startDate"})
public class Movie {
    
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    
    @XmlAttribute
    private int id;
    
    private String title;
    @XmlJavaTypeAdapter(PublishDateAdapter.class)
    @XmlElement(name = "publisheddate")
    private LocalDateTime publishDate;
    
    private String description;
    @XmlElement(name = "originaltitle")
    private String originalTitle;
    
    private Director director;
    private int duration;
    private String genre;
    @XmlElement(name = "picturepath")
    private String picturePath;
    
    private String link;
    @XmlJavaTypeAdapter(PublishDateAdapter.class)
    @XmlElement(name = "startdate")
    private LocalDateTime startDate;
    
    @XmlElementWrapper
    @XmlElement(name = "actor")
    private List<Actor> actors;

    public Movie() {
    }

    public Movie(String title, LocalDateTime publishDate, String description, String originalTitle, Director director, int duration, String genre, String picturePath, String link, LocalDateTime startDate, List<Actor> actors) {
        this.title = title;
        this.publishDate = publishDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.director = director;
        this.duration = duration;
        this.genre = genre;
        this.picturePath = picturePath;
        this.link = link;
        this.startDate = startDate;
        this.actors = actors;
    }
    
    public Movie(int id, String title, LocalDateTime publishDate, String description, String originalTitle, Director director, int duration, String genre, String picturePath, String link, LocalDateTime startDate, List<Actor> actors) {
        this(title, publishDate, description, originalTitle, director, duration, genre, picturePath, link, startDate, actors);
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", publishDate=" + publishDate + ", description=" + description + ", originalTitle=" + originalTitle + ", director=" + director + ", duration=" + duration + ", genre=" + genre + ", picturePath=" + picturePath + ", link=" + link + ", startDate=" + startDate + ", actors=" + actors + '}';
    }
}
