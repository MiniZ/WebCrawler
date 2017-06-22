package helper;

import java.util.List;

/**
 * Created by Anna on 6/22/2017.
 */
public class LibraryExtractor implements Runnable {

    private String webLink;

    public LibraryExtractor(String webLink) {
        this.webLink = webLink;
    }

    @Override
    public void run() {
        HtmlExtractor htmlExtractor = new HtmlExtractor();
        HtmlParser htmlParser = new HtmlParser();
        List<String> libraries = htmlParser.getJavascriptLibraries(htmlExtractor.extractHtml(webLink));
        System.out.println(libraries.toString());
    }
}
