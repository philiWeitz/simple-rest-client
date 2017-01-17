package futurice.org.restfulmobileclient.util;


public class StringUtil {

    private StringUtil() {

    }

    // appends the separator before the text only if the string builder is not empty
    public static void appendToStringWithSeparator(StringBuilder sb, String toAppend, String separator) {
        if(sb.length() > 0 && toAppend.length() > 0) {
            sb.append(separator);
        }
        sb.append(toAppend);
    }

    // replaces null with empty strings
    public static String setNonNull(String input) {
        if(null != input) {
            return input;
        }
        return "";
    }
}
