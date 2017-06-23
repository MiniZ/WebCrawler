package helper;

import config.ProgramConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Anna on 6/22/2017.
 */
public class QueryBuilder {

    public static String generateQuery(String searchTerm) {
        String term = generateTermForSearchQuery(searchTerm);
        return ProgramConstants.GOOGLE + ProgramConstants.SEARCH_QUERY + term;
    }

    private static String generateTermForSearchQuery(String searchTerm) {
        String res = "";
        String[] termParts = searchTerm.split(" ");
        if (termParts.length == 1) return toUtf8(termParts[0]);
        for (String termPart : termParts) {
            res += toUtf8(termPart) + "+";
        }
        return res.substring(0, res.length() - 1);
    }

    private static String toUtf8(String termPart) {
        try {
            return URLEncoder.encode(termPart, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}
