package futurice.org.restfulmobileclient.fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import futurice.org.restfulmobileclient.BR;
import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.databinding.FragmentUserDetailsBinding;
import futurice.org.restfulmobileclient.http.IUserImageCallback;
import futurice.org.restfulmobileclient.http.UserImageEndpoint;
import futurice.org.restfulmobileclient.model.UserAddressModel;
import futurice.org.restfulmobileclient.model.UserDataModel;


public class UserDetailsFragment extends AbstractBaseFragment {

    // serializer key for the user data object
    public static final String ARG_USER_DATA = "UserDataArgument";
    // prefix for showing the number on the dialer activity
    private static final String INTENT_DIALER_PREFIX = "tel:";
    // prefix for passing the email address to the email program
    private static final String INTENT_EMAIL_MAIL_TO = "mailto";
    // package name for google maps app
    private static final String INTENT_GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps";

    // holds the user profile image (lazy loading)
    private ImageView mProfileImageView;

    private UserDataModel mUserData = new UserDataModel();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // gets the data binding object
        final FragmentUserDetailsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user_details, container, false);
        // binds the user data to the view
        setDataBinding(binding);
        // gets all UI components + sets their properties
        initUI(binding.getRoot());
        // click listener for dialer, email and google maps
        initClickListener(binding.getRoot());

        return binding.getRoot();
    }


    private void setDataBinding(final FragmentUserDetailsBinding binding) {
        // get the serialized user data
        if(getArguments().containsKey(ARG_USER_DATA)) {
            final UserDataModel userData = (UserDataModel) getArguments().getSerializable(ARG_USER_DATA);
            mUserData = userData;

            // binds the data to the current and the included layout
            binding.setUserData(userData);
            binding.userDetailsIncluded.setVariable(BR.userData, userData);
            binding.userDetailsIncluded.setVariable(BR.userAddress, userData.getAddress());
            binding.executePendingBindings();

            // http call for getting the profile image
            UserImageEndpoint.getInstance().getUserProfileImage(userData, mUserProfileCallback);
        }
    }


    private void initUI(View view) {
        mProfileImageView = (ImageView) view.findViewById(R.id.fragment_user_details_profile_image);
        // fits the image to the available space and crops it accordingly (keeps the image ratio!)
        mProfileImageView.setAdjustViewBounds(true);
        mProfileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }


    private void initClickListener(View view) {
        // email shows a chooser -> no need to check
        view.findViewById(R.id.fragment_user_details_email)
                .setOnClickListener(mOnEmailClickListener);

        final PackageManager packageManager = getActivity().getPackageManager();

        // check that the device supports calling
        if(null != new Intent(Intent.ACTION_DIAL).resolveActivity(packageManager)) {
            view.findViewById(R.id.fragment_user_details_phone)
                    .setOnClickListener(mOnPhoneNumberClickListener);
        }

        // check that the device supports google maps
        if(null != new Intent(Intent.ACTION_VIEW).resolveActivity(packageManager)) {
            view.findViewById(R.id.fragment_user_details_location)
                    .setOnClickListener(mOnAddressClickListener);
        }
    }


    private final View.OnClickListener mOnEmailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // switch to email program
            if(null != mUserData && !"".equals(mUserData.getEmail())) {
                final Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts(INTENT_EMAIL_MAIL_TO, mUserData.getEmail(), null));
                startActivity(Intent.createChooser(emailIntent,
                        getString(R.string.intent_email_send_to)));
            }
        }
    };


    private final View.OnClickListener mOnPhoneNumberClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // switch to dialer
            if(null != mUserData && !"".equals(mUserData.getPhone())) {
                final Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                // show on the screen
                dialIntent.setData(Uri.parse(INTENT_DIALER_PREFIX + mUserData.getPhone()));
                startActivity(dialIntent);
            }
        }
    };


    private final View.OnClickListener mOnAddressClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final UserAddressModel userAddress = mUserData.getAddress();

            // show location on map
            if(null != userAddress && null != userAddress.getLocation()
                    && !"".equals(userAddress.getLocation().getLat())
                    && !"".equals(userAddress.getLocation().getLng())) {

                final Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(userAddress.getLocation().getGoogleMapsString()));
                mapIntent.setPackage(INTENT_GOOGLE_MAPS_PACKAGE);
                startActivity(mapIntent);
            }
        }
    };


    private final IUserImageCallback mUserProfileCallback = new IUserImageCallback() {
        @Override
        public void onFail() {
            processResponse(null);
        }

        @Override
        public void onResponse(Bitmap profileImage) {
            processResponse(profileImage);
        }

        private void processResponse(final Bitmap profileImage) {
            UserDetailsFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(null != profileImage) {
                        // set the profile bitmap
                        mProfileImageView.setImageBitmap(profileImage);

                    } else {
                        // set placeholder image
                        mProfileImageView.setImageBitmap(BitmapFactory.decodeResource(
                                getActivity().getResources(), R.drawable.user_image_placeholder));
                    }

                    // hide the progress bar (also for espresso tests!)
                    getActivity().findViewById(
                            R.id.fragment_user_details_loading).setVisibility(View.GONE);
                }
            });
        }
    };



}
