package futurice.org.restfulmobileclient.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

abstract class AbstractEndpoint {

    // server read timeout
    private static final int READ_TIMEOUT_IN_MS = 4000;

    // it is recommended that OkHttp is a singleton
    // -> it is static and therefore shared for all endpoints
    private static final OkHttpClient mClient;


    static {
        // initialize the http client before accessing the class
        mClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .build();
    }

    OkHttpClient getHttpClient() {
        return mClient;
    }
}
