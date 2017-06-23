package concurency;

import connectors.URLConnector;
import htmlworkers.HtmlExtractor;
import htmlworkers.HtmlParser;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Anna on 6/22/2017.
 */
public class LibraryExtractor implements Callable<List<String>> {

    private String webLink;

    public LibraryExtractor(String webLink) {
        this.webLink = webLink;
    }

    @Override
    public List<String> call() throws Exception {
        HtmlExtractor htmlExtractor = new HtmlExtractor();
        HtmlParser htmlParser = new HtmlParser();
        URLConnector urlConnector = new URLConnector(webLink);
        List<String> libraries = htmlParser.getJavascriptLibraries(htmlExtractor.extractHtml(urlConnector.getConnection()));
        return libraries;
    }
}
