package futurice.org.restfulmobileclient.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import futurice.org.restfulmobileclient.activity.UserDataActivity;
import futurice.org.restfulmobileclient.databinding.LayoutUserListEntryBinding;
import futurice.org.restfulmobileclient.model.UserDataModel;



public class UserDataRecyclerViewHolder   extends RecyclerView.ViewHolder {

    private LayoutUserListEntryBinding mBinding;
    private UserDataActivity mActivity;


    public UserDataRecyclerViewHolder(UserDataActivity activity, LayoutUserListEntryBinding binding) {
        super(binding.getRoot());

        mBinding = binding;
        mActivity = activity;

        binding.getRoot().setOnClickListener(mOnUserClick);
    }

    public void setUserData(UserDataModel userData) {
        mBinding.setUserData(userData);
        mBinding.executePendingBindings();
    }

    View.OnClickListener mOnUserClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // show user details
            mActivity.showUserDetailsFragment(mBinding.getUserData());
        }
    };
}
