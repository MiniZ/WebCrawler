package helper;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anna on 6/22/2017.
 */
public class URLConnector {

    private String url;

    public URLConnector(String url) {
        this.url = url;
    }

    public HttpURLConnection getConnection() {
        HttpURLConnection connection = null;
        try {
            URL urlConnection = new URL(url);
            connection = (HttpURLConnection) urlConnection.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.76");
            return connection;
        }catch ( Exception ex ) {
            ex.printStackTrace();
            SystemPrinter.unableToConnect();
            return null;
        }
    }
}
