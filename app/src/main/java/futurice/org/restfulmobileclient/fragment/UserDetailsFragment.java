package futurice.org.restfulmobileclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import futurice.org.restfulmobileclient.R;
import futurice.org.restfulmobileclient.model.UserDataModel;


public class UserDetailsFragment extends Fragment {

    public static final String ARG_USER_DATA = "UserDataArgument";

    private UserDataModel mUserData = new UserDataModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        if(getArguments().containsKey(ARG_USER_DATA)) {
            mUserData = (UserDataModel) getArguments().getSerializable(ARG_USER_DATA);
            initUI(view);
        }

        // Inflate the layout for this fragment
        return view;
    }


    private void initUI(View view) {
        TextView name = (TextView) view.findViewById(R.id.fragment_user_details_name);
        name.setText(mUserData.getName());
    }
}
