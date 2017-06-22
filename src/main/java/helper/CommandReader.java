package helper;

import java.util.Scanner;

/**
 * Created by Anna on 6/22/2017.
 */
public class CommandReader {

    public static String waitForSearchTerm() {
        Scanner input = new Scanner(System.in);
        SystemPrinter.enterSearchTerm();
        String searchTerm = input.nextLine();
        while (searchTerm == null || (searchTerm != null && searchTerm.isEmpty())) {
            SystemPrinter.enterValidString();
            SystemPrinter.enterSearchTerm();
            searchTerm = input.nextLine();
        }
        return searchTerm;
    }
}
