package futurice.org.restfulmobileclient.http;


import java.util.List;

import futurice.org.restfulmobileclient.model.UserDataModel;

public interface IUserDataCallback {
    void onFail();
    void onResponse(List<UserDataModel> userData);
}
