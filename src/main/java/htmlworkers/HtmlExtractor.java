package htmlworkers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * Created by Anna on 6/22/2017.
 */
public class HtmlExtractor {

    public String extractHtml(HttpURLConnection connection) {
        //URLConnector urlConnector = new URLConnector(url);
//        HttpURLConnection connection = urlConnector.getConnection();
        if (connection == null) return "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(connection.getInputStream());
        } catch (IOException e) {
            return "";
        }
        scanner.useDelimiter("\\Z");
        try {
            return scanner.next();
        } catch (Exception e) {
            return "";
        }
    }


}
