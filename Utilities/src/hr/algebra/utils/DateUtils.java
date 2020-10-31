package hr.algebra.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author filip
 */
public class DateUtils {
    
    public static String parseDate(String data, String originalFormat, String requstedFormat) throws ParseException{
        SimpleDateFormat parser = new SimpleDateFormat(originalFormat);
        Date date = parser.parse(data);
        SimpleDateFormat formatter = new SimpleDateFormat(requstedFormat);
        return formatter.format(date);
    }
    
}
