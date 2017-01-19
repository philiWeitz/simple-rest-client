package futurice.org.restfulmobileclient.ui;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import futurice.org.restfulmobileclient.BR;
import futurice.org.restfulmobileclient.activity.UserDataActivity;
import futurice.org.restfulmobileclient.model.UserDataModel;



public class UserDataRecyclerViewHolder   extends RecyclerView.ViewHolder {

    private final UserDataActivity mActivity;
    // used to rebind passed user data
    private final ViewDataBinding mBinding;
    // keeps the current view user data
    private UserDataModel mUserData = new UserDataModel();


    public UserDataRecyclerViewHolder(UserDataActivity activity, ViewDataBinding binding) {
        super(binding.getRoot());

        mBinding = binding;
        mActivity = activity;

        binding.getRoot().setOnClickListener(mOnUserClick);
    }


    // sets the user data and binds it to the view
    public void setUserData(UserDataModel userData) {
        mUserData = userData;
        mBinding.setVariable(BR.userData, userData);
        mBinding.executePendingBindings();
    }


    // show the user details fragment
    private final View.OnClickListener mOnUserClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // show user details
            mActivity.showUserDetailsFragment(mUserData);
        }
    };
}
