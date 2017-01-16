package futurice.org.restfulmobileclient.http;


import android.graphics.Bitmap;

public interface IUserProfileImageCallback {
    void onFail();
    void onResponse(Bitmap profileImage);
}
