package hr.algebra.factory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author filip
 */
public class UrlConnectionFactory {
   
     public static HttpsURLConnection getHttpsUrlConnection(String path, int timeout, String requestMethod) throws MalformedURLException, IOException {
        URL url = new URL(path);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setConnectTimeout(timeout);
        con.setReadTimeout(timeout);
        con.setRequestMethod(requestMethod);
        con.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        return con;
    }

}
