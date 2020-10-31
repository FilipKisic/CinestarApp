package hr.algebra.utils;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author filip
 */
public class TextUtils {

    private static List<String> extensions = Arrays.asList("4DX", "3D", "IMAX");

    public static String stripData(String data) {
        if (data.contains("[") && data.contains("]")) {
            return data.substring(data.lastIndexOf("[") + 2, data.indexOf("]") - 1);
        }
        if (data.contains("<") && data.contains(">")) {
            try {
                return data.substring(data.indexOf(">") + 1, data.lastIndexOf("<") - 1);
            } catch (Exception e) {
                return "";
            }
        }
        return data;
    }

    public static boolean checkTitle(String title) {
        return extensions.stream().noneMatch(ext -> title.contains(ext));
    }
    
    public static String cleanText(String data){
        while (true) {
            if (!data.contains("<") && !data.contains(">")) {
                break;
            }
            String stringToRemove = data.substring(data.indexOf("<"), data.indexOf(">") + 1);
            data = data.replace(stringToRemove, "");
        }
        return data;
    }
    
    public static String prepareLink(String url){
        return url = url.substring(0,4) + "s" + url.substring(4, url.length());
    }
}
