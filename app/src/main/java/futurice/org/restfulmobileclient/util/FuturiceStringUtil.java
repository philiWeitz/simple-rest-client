package futurice.org.restfulmobileclient.util;

/**
 * Created by my on 15.01.2017.
 */

public class FuturiceStringUtil {

    private FuturiceStringUtil() {

    }

    public static void appendToStringWithSeparator(StringBuilder sb, String toAppend, String separator) {
        if(sb.length() > 0) {
            sb.append(separator).append(toAppend);
        }
    }

}
