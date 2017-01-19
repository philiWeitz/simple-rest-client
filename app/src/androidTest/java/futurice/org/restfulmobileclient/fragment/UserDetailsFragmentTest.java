package futurice.org.restfulmobileclient.fragment;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.activity.UserDataActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static futurice.org.restfulmobileclient.EspressoUtil.nthChildOf;
import static futurice.org.restfulmobileclient.EspressoUtil.waitUntilFound;


// TODO: Mock the backend server
// the backend server should be mocked to avoid external dependencies
// and to ensure consistent test results

@RunWith(AndroidJUnit4.class)
public class UserDetailsFragmentTest {

    private static final String USER_NAME = "Kamren";
    private static final String NAME = "Chelsey Dietrich";
    private static final String EMAIL = "Lucio_Hettinger@annie.ca";
    private static final String PHONE = "(254)954-1289";
    private static final String ADDRESS = "Skiles Walks, Suite 351\n33263, Roscoeview";

    private static final int INIT_TIMEOUT_IN_MS = 3 * 1000;


    @Rule
    public ActivityTestRule<UserDataActivity> mActivityRule =
            new ActivityTestRule<>(UserDataActivity.class);


    @Test
    public void dataBindingTest() {
        // waits until the first element was loaded
        waitUntilFound(R.id.activity_user_data_user_list, 0, INIT_TIMEOUT_IN_MS);

        // click on the first item
        onView(nthChildOf(withId(R.id.activity_user_data_user_list), 0))
                .perform(click());

        // get fragment
        final ViewInteraction fragment = onView(withId(R.id.activity_user_data_fragment_container));
        fragment.check(matches(hasDescendant(withText(USER_NAME))));
        fragment.check(matches(hasDescendant(withText(NAME))));
        fragment.check(matches(hasDescendant(withText(PHONE))));
        fragment.check(matches(hasDescendant(withText(EMAIL))));
        fragment.check(matches(hasDescendant(withText(ADDRESS))));
    }
}
