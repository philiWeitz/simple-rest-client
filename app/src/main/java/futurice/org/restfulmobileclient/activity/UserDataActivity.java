package futurice.org.restfulmobileclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.Collections;
import java.util.List;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.fragment.NoNetworkConnectionFragment;
import futurice.org.restfulmobileclient.fragment.UserDetailsFragment;
import futurice.org.restfulmobileclient.http.HttpUtil;
import futurice.org.restfulmobileclient.http.IUserDataCallback;
import futurice.org.restfulmobileclient.http.UserDataEndpoint;
import futurice.org.restfulmobileclient.model.UserDataModel;
import futurice.org.restfulmobileclient.ui.UserListRecyclerViewAdapter;


// AppCompatActivity -> implements fragment activity and
// can be used to show the support toolbar
public class UserDataActivity extends AppCompatActivity {

    // holds all user data items
    private UserListRecyclerViewAdapter mUserDataListAdapter;
    // is shown while the user data is loading
    private ProgressBar mProgressBar;

    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        initUI();
    }


    // gets the UI fields and initializes the recycler view
    private void initUI() {
        // set new toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_toolbar_title);
        // set the color programmatically (XML tag only supported API >= 23)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(toolbar);
        // set back click listener (toolbar needs to be set first!!)
        toolbar.setNavigationOnClickListener(mOnToolbarClick);

        // setup the search
        mSearchView = (SearchView) findViewById(R.id.searchView);
        // set the search filter
        mSearchView.setOnQueryTextListener(mOnSearchListener);
        // expand the search view to make it everywhere clickable
        mSearchView.setIconified(false);
        mSearchView.clearFocus();

        // get the UI elements
        final RecyclerView userListView = (RecyclerView) findViewById(R.id.activity_user_data_user_list);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_user_data_loading);

        // initializes the recycler view similar to list view
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        userListView.setLayoutManager(layoutManager);

        mUserDataListAdapter = new UserListRecyclerViewAdapter(UserDataActivity.this);
        // set the adapter
        userListView.setAdapter(mUserDataListAdapter);
    }


    // shows the user details and hides the contacts
    public void showUserDetailsFragment(UserDataModel userData) {
        final UserDetailsFragment userDetailsFragment = new UserDetailsFragment();

        // hide the search view
        hideSearchSoftKeyboard();

        // pass the user data to the fragment
        final Bundle args = new Bundle();
        args.putSerializable(UserDetailsFragment.ARG_USER_DATA, userData);
        userDetailsFragment.setArguments(args);

        // adds the fragment to the container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_user_data_fragment_container, userDetailsFragment)
                .addToBackStack(null)
                .commit();
    }


    // shows the no network connection fragment
    private void showNoNetworkConnectionFragment() {
        // hide the search view
        hideSearchSoftKeyboard();

        final NoNetworkConnectionFragment noNetworkFragment =
                new NoNetworkConnectionFragment();

        // adds the fragment to the container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_user_data_fragment_container, noNetworkFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    protected void onResume() {
        super.onResume();

        // only load the data once

        if(null == mUserDataListAdapter
                || mUserDataListAdapter.getItemCount() <= 0) {
            loadUserData();
        }
    }


    // callback from getting the user data from the RESTful API
    public void loadUserData() {
        // sets the user data
        UserDataEndpoint.getInstance().getUserList(new IUserDataCallback() {
            @Override
            public void onFail() {
                updateList(Collections.<UserDataModel>emptyList());
            }

            @Override
            public void onResponse(List<UserDataModel> userDataList) {
                updateList(userDataList);
            }

            private void updateList(final List<UserDataModel> userDataList) {
                UserDataActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // hide the progress bar
                        mProgressBar.setVisibility(View.GONE);

                        if(!userDataList.isEmpty()) {
                            // sort the list of users
                            Collections.sort(userDataList);

                            // add the user data to the recycler view adapter
                            mUserDataListAdapter.setUserDataList(userDataList);

                        // if list is empty -> check if there is a network connection
                        } else if(!HttpUtil.isNetworkConnectionAvailable(UserDataActivity.this)) {
                            showNoNetworkConnectionFragment();
                        }
                    }
                });
            }
        });
    }


    private View.OnClickListener mOnToolbarClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // go back -> should only be visible if fragments are open
            onBackPressed();
        }
    };


    private SearchView.OnQueryTextListener mOnSearchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mUserDataListAdapter.getFilter().filter(newText);
            return false;
        }
    };


    private void hideSearchSoftKeyboard() {
        mSearchView.clearFocus();

        InputMethodManager inputManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
