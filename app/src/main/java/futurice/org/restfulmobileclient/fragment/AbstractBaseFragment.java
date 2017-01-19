package futurice.org.restfulmobileclient.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


public abstract class AbstractBaseFragment extends Fragment {

    @Override
    public void onPause() {
        displayBackButton(false);
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        displayBackButton(true);
    }


    private void displayBackButton(boolean display) {
        // check that action bar is visible
        if(getActivity() instanceof AppCompatActivity) {
            final ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            // check that toolbar was integragted
            if(null != toolbar) {
                // show back button
                toolbar.setDisplayHomeAsUpEnabled(display);
                toolbar.setDisplayShowHomeEnabled(display);
            }
        }
    }
}
