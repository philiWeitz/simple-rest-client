package futurice.org.restfulmobileclient.fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import futurice.org.restfulmobileclient.BR;
import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.databinding.FragmentUserDetailsBinding;
import futurice.org.restfulmobileclient.http.IUserProfileImageCallback;
import futurice.org.restfulmobileclient.http.UserDataEndpoint;
import futurice.org.restfulmobileclient.model.UserAddressModel;
import futurice.org.restfulmobileclient.model.UserDataModel;


public class UserDetailsFragment extends Fragment {
    public static final String ARG_USER_DATA = "UserDataArgument";

    private static final String INTENT_DIALER_PREFIX = "tel:";
    private static final String INTENT_EMAIL_MAIL_TO = "mailto";
    private static final String INTENT_GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps";

    private ImageView mProfileImageView;
    private UserDataModel mUserData = new UserDataModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentUserDetailsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user_details, container, false);

        setDataBinding(binding);
        initUI(binding.getRoot());
        initClickListener(binding.getRoot());

        return binding.getRoot();
    }


    private void setDataBinding(FragmentUserDetailsBinding binding) {
        // get the user data
        if(getArguments().containsKey(ARG_USER_DATA)) {
            UserDataModel userData = (UserDataModel) getArguments().getSerializable(ARG_USER_DATA);
            mUserData = userData;

            binding.setUserData(userData);
            binding.userDetailsIncluded.setVariable(BR.userData, userData);
            binding.userDetailsIncluded.setVariable(BR.userAddress, userData.getAddress());
            binding.executePendingBindings();

            UserDataEndpoint.getInstance().getUserImage(userData, mUserProfileCallback);
        }
    }


    private void initUI(View view) {
        mProfileImageView = (ImageView) view.findViewById(R.id.fragment_user_details_profile_image);
        mProfileImageView.setAdjustViewBounds(true);
        mProfileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }


    private void initClickListener(View view) {
        view.findViewById(R.id.fragment_user_details_email)
                .setOnClickListener(mOnEmailClickListener);

        PackageManager packageManager = getActivity().getPackageManager();

        // check that the device supports calling
        if(null != new Intent(Intent.ACTION_DIAL).resolveActivity(packageManager)) {
            view.findViewById(R.id.fragment_user_details_phone)
                    .setOnClickListener(mOnPhoneNumberClickListener);
        }

        // check that the device supports calling
        if(null != new Intent(Intent.ACTION_VIEW).resolveActivity(packageManager)) {
            view.findViewById(R.id.fragment_user_details_location)
                    .setOnClickListener(mOnAddressClickListener);
        }
    }


    private View.OnClickListener mOnEmailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // switch to email intent
            if(null != mUserData && !"".equals(mUserData.getEmail())) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts(INTENT_EMAIL_MAIL_TO, mUserData.getEmail(), null));
                startActivity(Intent.createChooser(emailIntent,
                        getString(R.string.intent_email_send_to)));
            }
        }
    };


    private View.OnClickListener mOnPhoneNumberClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // call the person
            if(null != mUserData && !"".equals(mUserData.getPhone())) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                // show on the screen
                dialIntent.setData(Uri.parse(INTENT_DIALER_PREFIX + mUserData.getPhone()));
                startActivity(dialIntent);
            }
        }
    };


    private View.OnClickListener mOnAddressClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserAddressModel userAddress = mUserData.getAddress();

            // show location on map
            if(null != userAddress && null != userAddress.getLocation()
                    && !"".equals(userAddress.getLocation().getLat())
                    && !"".equals(userAddress.getLocation().getLng())) {

                Uri gmmIntentUri = Uri.parse(userAddress.getLocation().getGoogleMapsString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage(INTENT_GOOGLE_MAPS_PACKAGE);
                startActivity(mapIntent);
            }
        }
    };


    private IUserProfileImageCallback mUserProfileCallback = new IUserProfileImageCallback() {
        @Override
        public void onFail() {
            // TODO: show default image
        }

        @Override
        public void onResponse(final Bitmap profileImage) {
            UserDetailsFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    View parentView = UserDetailsFragment.this.getView();
                    mProfileImageView.setImageBitmap(profileImage);
                }
            });
        }
    };
}
