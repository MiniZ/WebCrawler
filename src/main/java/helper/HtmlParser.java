package helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anna on 6/22/2017.
 */
public class HtmlParser {

    public List<String> getGoogleLinksFromResultPage(String html) {
        List<String> res = new ArrayList<>();
        String pattern1 = "<a href=\"/url?q=";
        String pattern2 = "\">";
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(html);

        while (m.find()) {
            String domainName = m.group(0).trim();
            domainName = domainName.substring(domainName.indexOf("/url?q=") + 7);
            domainName = domainName.substring(0, domainName.indexOf("&amp;"));
            res.add(domainName);
        }
        return res;
    }

    public List<String> getJavascriptLibraries(String html) {
        List<String> res = new ArrayList<>();
        String pattern1 = "<script src=";
        String pattern2 = "</script>";
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(html);

        while (m.find()) {
            String domainName = m.group(0).trim();
            domainName = domainName.substring(domainName.indexOf("<script src=") + 12);
            domainName = domainName.substring(0, domainName.indexOf(".js") + 3);
            res.add(domainName);
        }
        return res;
    }

}
