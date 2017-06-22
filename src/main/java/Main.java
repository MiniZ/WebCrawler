import config.ProgramConstants;
import helper.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anna on 6/22/2017.
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        String searchedTerm = CommandReader.waitForSearchTerm();
        String searchQuery = QueryBuilder.generateQuery(searchedTerm);
        System.out.println(searchQuery);
        HtmlExtractor htmlExtractor = new HtmlExtractor();
        HtmlParser htmlParser = new HtmlParser();
        List<String> webLinks = htmlParser.getGoogleLinksFromResultPage(htmlExtractor.extractHtml(searchQuery));
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (String webLink : webLinks) {
            executorService.execute(new LibraryExtractor(webLink));
            //new Thread(new LibraryExtractor(webLink)).start();
        }
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(3, TimeUnit.MINUTES);
        if (finished) {
            System.out.println("bla");
        }
    }

}
