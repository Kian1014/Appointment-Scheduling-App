package helpers;

/**
 * Helper class to make sql statements more readable.
 */

public class StringBuild {

    /**
     * Surrounds given string with single quotes for sql operations.
     * @param s String to be surrounded with single quotes.
     * @return Returns a string surrounded with single quotes '
     */

    public static String surroundWithQuote (String s) {

        StringBuilder newString = new StringBuilder();
        newString.append("\'").append(s).append("\'");
        return newString.toString();

    }

}
