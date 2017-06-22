package helper;

import config.ProgramConstants;

/**
 * Created by Anna on 6/22/2017.
 */
public class SystemPrinter {

    public static void enterValidString() {
        System.out.println(ProgramConstants.VALID_STRING_MESSAGE);
    }

    public static void enterSearchTerm() {
        System.out.print(ProgramConstants.ENTER_MESSAGE);
    }

    public static void unableToConnect() {
        System.out.println(ProgramConstants.UNABLE_TO_CONNECT);
    }
}
