package futurice.org.restfulmobileclient.fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import futurice.org.restfulmobileclient.BR;
import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.activity.UserDataActivity;
import futurice.org.restfulmobileclient.databinding.FragmentUserDetailsBinding;
import futurice.org.restfulmobileclient.http.IUserImageCallback;
import futurice.org.restfulmobileclient.http.UserImageEndpoint;
import futurice.org.restfulmobileclient.model.UserAddressModel;
import futurice.org.restfulmobileclient.model.UserDataModel;


public class NoNetworkConnectionFragment extends Fragment {

    private static final String TAG = "NoNetworkFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_no_network_connection, container, false);

        initUI(view);
        return view;
    }


    private void initUI(View view) {
        // add the click listener for the retry button
        view.findViewById(R.id.fragment_no_network_connection_retry)
                .setOnClickListener(mOnRetryPress);
    }


    private View.OnClickListener mOnRetryPress = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().popBackStack();

            // not the best way of solving this :(
            if(getActivity() instanceof UserDataActivity) {
                ((UserDataActivity) getActivity()).loadUserData();
            } else {
                Log.w(TAG, "Activity maybe needs to define a method call here");
            }
        }
    };


    @Override
    public void onPause() {
        // if this fragment is still visible when the
        // activity is put to pause-> close it
        getActivity().getSupportFragmentManager().popBackStack();

        super.onPause();
    }
}
