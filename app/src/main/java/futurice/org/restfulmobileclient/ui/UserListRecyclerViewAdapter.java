package futurice.org.restfulmobileclient.ui;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.activity.UserDataActivity;
import futurice.org.restfulmobileclient.model.UserDataModel;


public class UserListRecyclerViewAdapter extends
        RecyclerView.Adapter<UserDataRecyclerViewHolder> implements Filterable {

    // reference to the parent activity to show user data details
    private UserDataActivity mActivity;
    // holds all user data items
    private List<UserDataModel> mUserDataList = new ArrayList<>();
    // holds all the filtered results
    private List<UserDataModel> mUserDataListFiltered = new ArrayList<>();


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

        mUserDataListFiltered = mUserDataList;

        // update the recycler view
        notifyDataSetChanged();
    }


    @Override
    public UserDataRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // gets the data binding object
        final ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.layout_user_list_entry, parent, false);

        return new UserDataRecyclerViewHolder(mActivity, binding);
    }


    @Override
    public void onBindViewHolder(UserDataRecyclerViewHolder holder, int position) {
        if(mUserDataListFiltered.size() > position) {
            holder.setUserData(mUserDataListFiltered.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return mUserDataListFiltered.size();
    }


    @Override
    public Filter getFilter() {
        return mUserDataResultsFilter;
    }


    private final Filter mUserDataResultsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length() == 0) {
                results.values = mUserDataList;
                results.count = mUserDataList.size();
            } else {
                List<UserDataModel> filteredList = new ArrayList<>();

                for (UserDataModel userData : mUserDataList) {
                    if (userData.getName().toUpperCase().contains(charSequence.toString().toUpperCase())) {
                        filteredList.add(userData);
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mUserDataListFiltered = (List<UserDataModel>) results.values;
            UserListRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    };
}