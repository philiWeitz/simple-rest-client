package futurice.org.restfulmobileclient.http;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
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
    private static final UserDataEndpoint instance;


    static {
        // instantiate the singleton
        instance = new UserDataEndpoint();
    }


    public static UserDataEndpoint getInstance() {
        return instance;
    }


    private UserDataEndpoint() {

    }


    // gets a list of all available users and their data
    // Warning: is not running and returning the object on the UI thread
    public void getUserList(IUserDataCallback userDataCallback) {
        final Request request = new Request.Builder()
                .url(URL_USER_LIST)
                .build();

        getHttpClient().newCall(request).enqueue(
                new OkHttpUserDataCallback(userDataCallback));
    }


    private class OkHttpUserDataCallback implements Callback {

        private final IUserDataCallback mCallback;

        public OkHttpUserDataCallback(IUserDataCallback callback) {
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
                    // parse the JSON String using GSON
                    Type listType = new TypeToken<List<UserDataModel>>() {
                    }.getType();
                    List<UserDataModel> userData = new Gson().fromJson(response.body().string(), listType);
                    // returns the list of user data
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
