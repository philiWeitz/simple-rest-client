package futurice.org.restfulmobileclient.activity;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import futurice.org.restfulmobileclient.EspressoUtil;
import futurice.org.restfulmobileclient.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static futurice.org.restfulmobileclient.EspressoUtil.nthChildOf;
import static futurice.org.restfulmobileclient.EspressoUtil.waitUntilFound;


// TODO: Mock the backend server
// the backend server should be mocked to avoid external dependencies
// and to ensure consistent test results

@RunWith(AndroidJUnit4.class)
public class UserDataActivityTest {

    private static final String USER_NAME_FIRST_RECORD = "Kamren";
    private static final String NAME_FIRST_RECORD = "Chelsey Dietrich ...";
    private static final String USER_NAME_SECOND_RECORD = "Moriah.Stanton";
    private static final String NAME_SECOND_RECORD = "Clementina DuBuque ...";
    private static final String SEARCH_NAME_TYPED = "Ervin Howell";
    private static final String SEARCH_NAME_RESULT = "Ervin Howell ...";

    private static final int INIT_TIMEOUT_IN_MS = 3 * 1000;


    @Rule
    public ActivityTestRule<UserDataActivity> mActivityRule =
            new ActivityTestRule<>(UserDataActivity.class);


    @Test
    public void dataBindingTest() {
        // waits until the first element was loaded
        waitUntilFound(R.id.activity_user_data_user_list, 0, INIT_TIMEOUT_IN_MS);

        // test that at least 2 elements are loaded
        onView(nthChildOf(withId(R.id.activity_user_data_user_list), 2))
                .check(matches(isDisplayed()));

        // checks the values of the first record
        onView(nthChildOf(withId(R.id.activity_user_data_user_list), 0))
                .check(matches(hasDescendant(withText(NAME_FIRST_RECORD))));
        onView(nthChildOf(withId(R.id.activity_user_data_user_list), 0))
                .check(matches(hasDescendant(withText(USER_NAME_FIRST_RECORD))));

        // checks the values of the second record
        onView(nthChildOf(withId(R.id.activity_user_data_user_list), 1))
                .check(matches(hasDescendant(withText(NAME_SECOND_RECORD))));
        onView(nthChildOf(withId(R.id.activity_user_data_user_list), 1))
                .check(matches(hasDescendant(withText(USER_NAME_SECOND_RECORD))));
    }


    @Test
    public void searchBarTest() {
        // waits until the first element was loaded
        waitUntilFound(R.id.activity_user_data_user_list, 0, INIT_TIMEOUT_IN_MS);

        // perform a search
        onView(withId(R.id.searchView))
                .perform(typeText(SEARCH_NAME_TYPED));

        // wait for 500ms
        EspressoUtil.waitUntil(500);

        // check that the first item is the one we searched for
        onView(nthChildOf(withId(R.id.activity_user_data_user_list), 0))
                .check(matches(hasDescendant(withText(SEARCH_NAME_RESULT))));
    }


    @Test
    public void showUserDetailsFragmentTest() {
        // waits until the first element was loaded
        waitUntilFound(R.id.activity_user_data_user_list, 0, INIT_TIMEOUT_IN_MS);

        // click on the first item
        onView(nthChildOf(withId(R.id.activity_user_data_user_list), 0))
                .perform(click());

        // check if fragment is visible
        onView(withId(R.id.user_details_included))
                .check(matches(isDisplayed()));
    }
}