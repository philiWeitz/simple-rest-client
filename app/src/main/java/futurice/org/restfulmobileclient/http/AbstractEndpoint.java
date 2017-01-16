package futurice.org.restfulmobileclient.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public abstract class AbstractEndpoint {

    private static final int READ_TIMEOUT_IN_MS = 4000;
    private static OkHttpClient mClient;

    static {
        mClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .build();
    }

    protected OkHttpClient getHttpClient() {
        return mClient;
    }
}
