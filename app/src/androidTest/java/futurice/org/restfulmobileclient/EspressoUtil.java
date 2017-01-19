package futurice.org.restfulmobileclient;

import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class EspressoUtil {

    private EspressoUtil() {

    }


    // waits for the specified time
    // maybe not the best implementation but it works quite well :)
    public static void waitUntil(long timeInMs) {
        final long time = System.currentTimeMillis() + timeInMs;

        while(time > System.currentTimeMillis()) {

        }
    }

    // waits until it finds the element or until the timeout occurs
    public static void waitUntilFound(int itemId, int childIdx, long timeoutInMs) {
        final long time = System.currentTimeMillis() + timeoutInMs;

        while(time > System.currentTimeMillis()) {
            if(null != onView(nthChildOf(withId(itemId), childIdx))) {
                break;
            }
        }
    }


    // Source - http://stackoverflow.com/a/30073528/681493
    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with " + childPosition + " child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                final ViewGroup viewGroup = (ViewGroup) view.getParent();

                return parentMatcher.matches(view.getParent()) &&
                        viewGroup.getChildAt(childPosition).equals(view);
            }
        };
    }

}
