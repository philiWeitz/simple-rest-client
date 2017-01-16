package futurice.org.restfulmobileclient.util;


public class FuturiceStringUtil {

    private FuturiceStringUtil() {

    }

    public static void appendToStringWithSeparator(StringBuilder sb, String toAppend, String separator) {
        if(sb.length() > 0 && toAppend.length() > 0) {
            sb.append(separator);
        }
        sb.append(toAppend);
    }
}
