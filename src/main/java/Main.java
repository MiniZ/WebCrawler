import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anna on 6/22/2017.
 */
public class Main {
    public static void main(String[] args) throws MalformedURLException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter search term : ");
        String searchWord = input.nextLine();
        System.out.println("Word is : " + searchWord);

        String content = null;
        HttpURLConnection connection = null;
        URL url = new URL("http://www.google.com/search?q=bla");
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.76");
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            parseLinks(content);
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        System.out.println(content);
    }

    public static List<String> parseLinks(final String html) throws Exception {
        List<String> result = new ArrayList<String>();
        String pattern1 = "<a href=\"/url?q=";
        String pattern2 = "\">";
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(html);

        while (m.find()) {
            String domainName = m.group(0).trim();

            /** remove the unwanted text */
            domainName = domainName.substring(domainName.indexOf("/url?q=") + 7);
            domainName = domainName.substring(0, domainName.indexOf("&amp;"));

            result.add(domainName);
        }
        return result;
    }
}
