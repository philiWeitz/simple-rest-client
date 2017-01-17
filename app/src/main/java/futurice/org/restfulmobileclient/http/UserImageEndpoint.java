package futurice.org.restfulmobileclient.http;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import futurice.org.restfulmobileclient.model.UserDataModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class UserImageEndpoint extends AbstractEndpoint {

    // singleton instance
    private static final UserImageEndpoint instance;


    static {
        // instantiate the singleton
        instance = new UserImageEndpoint();
    }


    public static UserImageEndpoint getInstance() {
        return instance;
    }


    private UserImageEndpoint() {

    }

    // returns the user profile image
    // Warning: is not running and returning the object on the UI thread
    public void getUserProfileImage(UserDataModel userData, IUserImageCallback userImageCallback) {
        final Request request = new Request.Builder()
                .url(userData.getProfileImageURL())
                .build();

        getHttpClient().newCall(request).enqueue(
                new OkHttpUserImageCallback(userImageCallback));
    }


    private class OkHttpUserImageCallback implements Callback {

        private IUserImageCallback mCallback;

        public OkHttpUserImageCallback(IUserImageCallback callback) {
            mCallback = callback;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            mCallback.onFail();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            // TODO: return different messages based on http code
            if(response.isSuccessful()) {
                try {
                    // create bitmap
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    // only successful if bitmap is not null
                    if(null != bitmap) {
                        mCallback.onResponse(bitmap);
                    } else {
                        mCallback.onFail();
                    }

                } catch (Exception e) {
                    mCallback.onFail();
                }
            } else {
                mCallback.onFail();
            }
        }
    }
}
