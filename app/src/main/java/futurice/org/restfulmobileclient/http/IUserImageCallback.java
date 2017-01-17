package futurice.org.restfulmobileclient.http;


import android.graphics.Bitmap;

public interface IUserImageCallback {
    void onFail();
    void onResponse(Bitmap profileImage);
}
