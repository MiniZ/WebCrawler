import comparators.ValueComparator;
import concurency.LibraryExtractor;
import config.ProgramConstants;
import connectors.URLConnector;
import helper.*;
import htmlworkers.HtmlExtractor;
import htmlworkers.HtmlParser;

import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Anna on 6/22/2017.
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException, InterruptedException, ExecutionException {
        Scanner input = new Scanner(System.in);
        HtmlExtractor htmlExtractor = new HtmlExtractor();
        HtmlParser htmlParser = new HtmlParser();
        URLConnector urlConnector = new URLConnector();
        String searchedTerm = "";
        System.out.print(ProgramConstants.INITIAL_MESSAGE);
        while (true) {
            System.out.print(ProgramConstants.ENTER_MESSAGE);
            searchedTerm = input.nextLine();
            if (searchedTerm == null || searchedTerm.isEmpty()) break;

            List<String> webLinks = getWebLinksForTwoPages(searchedTerm, urlConnector, htmlParser, htmlExtractor);
            TreeMap<String, Integer> sortedLibs = getSortedLibraries(webLinks);

            if (sortedLibs.size() == 0) {
                System.out.println("No Libraries found.");
            } else {
                System.out.println("Most popular javascript libraries are : ");
                int i = 0;
                for (String s : sortedLibs.keySet()) {
                    if (i == 5) break;
                    i++;
                    System.out.println(i + ". " + s);
                }
            }
        }
    }

    private static TreeMap<String, Integer> getSortedLibraries(List<String> webLinks) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Collection<Callable<List<String>>> tasks = new ArrayList<>();
        for (String webLink : webLinks) {
            tasks.add(new LibraryExtractor(webLink));
        }

        List<Future<List<String>>> results = null;
        try {
            results = executorService.invokeAll(tasks, 30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return new TreeMap<>();
        }
        HashMap<String, Integer> popularLibraries = new HashMap<>();
        for (Future<List<String>> libs : results) {
            try {
                for (String s : libs.get()) {
                    if (popularLibraries.containsKey(s)) {
                        popularLibraries.put(s, popularLibraries.get(s) + 1);
                    } else {
                        popularLibraries.put(s, 1);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                return new TreeMap<>();
            }
        }
        ValueComparator valueComparator = new ValueComparator(popularLibraries);
        TreeMap<String, Integer> sortedLibs = new TreeMap<>(valueComparator);
        sortedLibs.putAll(popularLibraries);
        executorService.shutdown();
        return sortedLibs;
    }

    private static List<String> getWebLinksForTwoPages(String searchedTerm, URLConnector urlConnector, HtmlParser htmlParser, HtmlExtractor htmlExtractor) {
        urlConnector.setUrl(QueryBuilder.generateQuery(searchedTerm));
        List<String> res = htmlParser.getGoogleLinksFromResultPage(htmlExtractor.extractHtml(urlConnector.getConnection()));

        urlConnector.setUrl(QueryBuilder.generateQuery(searchedTerm) + ProgramConstants.SECOND_PAGE);
        res.addAll(htmlParser.getGoogleLinksFromResultPage(htmlExtractor.extractHtml(urlConnector.getConnection())));
        return res;
    }

}
