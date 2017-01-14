package futurice.org.restfulmobileclient.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.fragment.UserDetailsFragment;
import futurice.org.restfulmobileclient.http.HttpUtil;
import futurice.org.restfulmobileclient.model.UserDataModel;
import futurice.org.restfulmobileclient.ui.UserListRecyclerViewAdapter;

public class UserDataActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        initUI();
    }


    private void initUI() {
        // gets the recycler view which holds all users
        RecyclerView userListView = (RecyclerView) findViewById(R.id.activity_user_data_user_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        userListView.setLayoutManager(layoutManager);

        // sets the user data
        UserListRecyclerViewAdapter mAdapter = new UserListRecyclerViewAdapter(this, createUserDataList());
        userListView.setAdapter(mAdapter);
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

        if(!HttpUtil.isNetworkConnectionAvailable(this)) {
            // TODO: Show a message box to notify the user
        }
    }


    // TODO: DEBUG only - remove this!!
    private List<UserDataModel> createUserDataList() {
        List<UserDataModel> result = new ArrayList<>();

        UserDataModel user = new UserDataModel();
        user.setName("Test 1");

        UserDataModel user2 = new UserDataModel();
        user2.setName("Test 2");

        result.add(user);
        result.add(user2);

        return result;
    }
}
