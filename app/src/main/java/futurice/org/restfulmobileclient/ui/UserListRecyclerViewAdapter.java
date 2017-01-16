package futurice.org.restfulmobileclient.ui;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.activity.UserDataActivity;
import futurice.org.restfulmobileclient.databinding.LayoutUserListEntryBinding;
import futurice.org.restfulmobileclient.model.UserDataModel;


public class UserListRecyclerViewAdapter extends
        RecyclerView.Adapter<UserDataRecyclerViewHolder> {

    private UserDataActivity mActivity;
    private List<UserDataModel> mUserDataList = new ArrayList<>();


    public UserListRecyclerViewAdapter(UserDataActivity activity, List<UserDataModel> userDataList) {
        mActivity = activity;

        if(null != userDataList) {
            mUserDataList = userDataList;
        }
    }


    @Override
    public UserDataRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        LayoutUserListEntryBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.layout_user_list_entry, parent, false);

        UserDataRecyclerViewHolder viewHolder = new UserDataRecyclerViewHolder(mActivity, binding);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(UserDataRecyclerViewHolder holder, int position) {
        if(mUserDataList.size() > position) {
            holder.setUserData(mUserDataList.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return mUserDataList.size();
    }
}