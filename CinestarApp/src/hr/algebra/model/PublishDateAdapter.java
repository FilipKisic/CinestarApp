package hr.algebra.model;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author filip
 */
public class PublishDateAdapter extends XmlAdapter<String, LocalDateTime>{

    @Override
    public LocalDateTime unmarshal(String dateText) throws Exception {
        return LocalDateTime.parse(dateText, Movie.DATE_TIME_FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime date) throws Exception {
        return date.format(Movie.DATE_TIME_FORMATTER);
    }
    
}
