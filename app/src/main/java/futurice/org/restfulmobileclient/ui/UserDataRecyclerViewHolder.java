package futurice.org.restfulmobileclient.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.activity.UserDataActivity;
import futurice.org.restfulmobileclient.model.UserDataModel;



public class UserDataRecyclerViewHolder   extends RecyclerView.ViewHolder {

    private UserDataModel mUserData;
    private TextView mUserNameTextView;
    private UserDataActivity mActivity;

    public UserDataRecyclerViewHolder(UserDataActivity activity, View itemView) {
        super(itemView);

        // init UI components
        mActivity = activity;
        mUserNameTextView = (TextView) itemView.findViewById(R.id.layout_user_list_entry_name);

        itemView.setOnClickListener(mOnUserClick);
    }

    public void setUserData(UserDataModel userData) {
        mUserData = userData;
        mUserNameTextView.setText(userData.getName());
    }

    View.OnClickListener mOnUserClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // show user details
            mActivity.showUserDetailsFragment(mUserData);
        }
    };
}
