package futurice.org.restfulmobileclient.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Collections;
import java.util.List;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.fragment.UserDetailsFragment;
import futurice.org.restfulmobileclient.http.HttpUtil;
import futurice.org.restfulmobileclient.http.IUserDataCallback;
import futurice.org.restfulmobileclient.http.UserDataEndpoint;
import futurice.org.restfulmobileclient.model.UserDataModel;
import futurice.org.restfulmobileclient.ui.UserListRecyclerViewAdapter;

public class UserDataActivity extends FragmentActivity {


    private RecyclerView mUserListView;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        initUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_data_activity_menu, menu);

        return true;
    }


    private void initUI() {
        // gets the recycler view which holds all users
        mUserListView = (RecyclerView) findViewById(R.id.activity_user_data_user_list);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_user_data_loading);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mUserListView.setLayoutManager(layoutManager);
    }


    public void showUserDetailsFragment(UserDataModel userData) {
        UserDetailsFragment userDetailsFragment = new UserDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(UserDetailsFragment.ARG_USER_DATA, userData);
        userDetailsFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_user_data_fragment_container, userDetailsFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    protected void onResume() {
        super.onResume();

        // only load the data once
        if(null == mUserListView.getAdapter()
                || mUserListView.getAdapter().getItemCount() <= 0) {
            loadUserData();
        }
    }


    private void loadUserData() {
        // sets the user data
        UserDataEndpoint.getInstance().getUserList(new IUserDataCallback() {
            @Override
            public void onFail() {
                updateList(Collections.<UserDataModel>emptyList());
            }

            @Override
            public void onResponse(List<UserDataModel> userData) {
                updateList(userData);
            }

            private void updateList(final List<UserDataModel> userData) {
                UserDataActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // hide the progress bar
                        mProgressBar.setVisibility(View.GONE);

                        if(!userData.isEmpty()) {
                            // sort the list of users
                            Collections.sort(userData);

                            // add the users to the list
                            UserListRecyclerViewAdapter mAdapter =
                                    new UserListRecyclerViewAdapter(UserDataActivity.this, userData);

                            mUserListView.setAdapter(mAdapter);

                        // if list is empty -> check if there is a network connection
                        } else if(!HttpUtil.isNetworkConnectionAvailable(UserDataActivity.this)) {
                            new AlertDialog.Builder(UserDataActivity.this)
                                    .setTitle(R.string.message_box_no_network_title)
                                    .setMessage(R.string.message_box_no_network_msg)
                                    .setPositiveButton(R.string.button_ok, null)
                                    .show();
                        }
                    }
                });
            }
        });
    }
}
