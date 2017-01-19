package futurice.org.restfulmobileclient.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.activity.UserDataActivity;


public class NoNetworkConnectionFragment extends AbstractBaseFragment {

    private static final String TAG = "NoNetworkFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(
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
