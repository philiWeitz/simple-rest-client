package futurice.org.restfulmobileclient.http;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import futurice.org.restfulmobileclient.model.UserDataModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class UserDataEndpoint extends AbstractEndpoint {

    // avoid final modifier for unit testing (final will inline the string!)
    private static String URL_USER_LIST = "http://jsonplaceholder.typicode.com/users";
    // singleton instance
    private static UserDataEndpoint instance;


    static {
        // instantiate the singleton
        instance = new UserDataEndpoint();
    }


    public static UserDataEndpoint getInstance() {
        return instance;
    }


    private UserDataEndpoint() {

    }


    public void getUserList(IUserDataCallback userDataCallback) {
        Request request = new Request.Builder()
                .url(URL_USER_LIST)
                .build();

        getHttpClient().newCall(request).enqueue(
                new OkHttpUserDataCallback(userDataCallback));
    }


    public void getUserImage(UserDataModel userData, IUserProfileImageCallback userProfileImageCallback) {
        Request request = new Request.Builder()
                .url(userData.getProfileImageURL())
                .build();

        getHttpClient().newCall(request).enqueue(
                new OkHttpUserProfileImageCallback(userProfileImageCallback));
    }


    private class OkHttpUserDataCallback implements Callback {

        private IUserDataCallback mCallback;

        public OkHttpUserDataCallback(IUserDataCallback callback) {
            mCallback = callback;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            mCallback.onFail();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            if(response.isSuccessful()) {
                try {
                    // parse the JSON String
                    Type listType = new TypeToken<List<UserDataModel>>() {
                    }.getType();
                    List<UserDataModel> userData = new Gson().fromJson(response.body().string(), listType);
                    mCallback.onResponse(userData);

                } catch (Exception e) {
                    mCallback.onFail();
                }
            } else {
                mCallback.onFail();
            }
        }
    }

    private class OkHttpUserProfileImageCallback implements Callback {

        private IUserProfileImageCallback mCallback;

        public OkHttpUserProfileImageCallback(IUserProfileImageCallback callback) {
            mCallback = callback;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            mCallback.onFail();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            if(response.isSuccessful()) {
                try {
                    // create bitmap
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mCallback.onResponse(bitmap);

                } catch (Exception e) {
                    mCallback.onFail();
                }
            } else {
                mCallback.onFail();
            }
        }
    }
}
