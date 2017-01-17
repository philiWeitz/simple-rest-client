package futurice.org.restfulmobileclient.ui;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.activity.UserDataActivity;
import futurice.org.restfulmobileclient.model.UserDataModel;


public class UserListRecyclerViewAdapter extends RecyclerView.Adapter<UserDataRecyclerViewHolder> {

    // reference to the parent activity to show user data details
    private UserDataActivity mActivity;
    // holds all user data items
    private List<UserDataModel> mUserDataList = new ArrayList<>();


    public UserListRecyclerViewAdapter(UserDataActivity activity) {
        init(activity, Collections.<UserDataModel>emptyList());
    }


    public UserListRecyclerViewAdapter(UserDataActivity activity, List<UserDataModel> userDataList) {
        init(activity, userDataList);
    }


    private void init(UserDataActivity activity, List<UserDataModel> userDataList) {
        mActivity = activity;
        setUserDataList(userDataList);
    }


    public void setUserDataList(List<UserDataModel> userDataList) {
        if(null != userDataList) {
            mUserDataList = userDataList;
        } else {
            mUserDataList = Collections.emptyList();
        }

        // update the recycler view
        notifyDataSetChanged();
    }


    @Override
    public UserDataRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // gets the data binding object
        ViewDataBinding binding = DataBindingUtil.inflate(
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