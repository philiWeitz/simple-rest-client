package futurice.org.restfulmobileclient.http;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import futurice.org.restfulmobileclient.model.UserDataModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserDataEndpoint {

    private static final int READ_TIMEOUT_IN_MS = 1000;
    private static final String URL_USER_LIST = "http://jsonplaceholder.typicode.com/users";

    private static UserDataEndpoint instance;


    public static UserDataEndpoint getInstance() {
        if(null == instance) {
            instance = new UserDataEndpoint();
        }
        return instance;
    }


    // OKHttp recommends this to be a singleton
    OkHttpClient mClient;


    private UserDataEndpoint() {
        mClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .build();
    }


    public void getUserList(IUserDataCallback userDataCallback) {
        Request request = new Request.Builder()
                .url(URL_USER_LIST)
                .build();

        mClient.newCall(request).enqueue(
                new OkHttpUserDataCallback(userDataCallback));
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
}
