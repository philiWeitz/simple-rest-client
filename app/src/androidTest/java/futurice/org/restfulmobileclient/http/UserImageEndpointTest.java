package futurice.org.restfulmobileclient.http;


import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.CountDownLatch;

import futurice.org.restfulmobileclient.model.UserDataModel;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;

import static org.junit.Assert.fail;


@RunWith(AndroidJUnit4.class)
public class UserImageEndpointTest {

    // Ugly way of passing pass fail results
    private volatile boolean mFailed = true;

    // basic mock server api url
    protected static final String MOCK_URL = "unit/testing";

    // holds the last mock server instance
    private MockWebServer mMockServer = new MockWebServer();


    @After
    public void stopMockWebServer() {
        try {
            mMockServer.shutdown();
        } catch (Exception e) {}
    }


    @Test
    public void getUserProfileImageInvalidBitmapTest() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        // create the user data
        UserDataModel userData = new UserDataModel();
        // start the mock server with an empty buffer
        MockWebServer server = startMockServer(new byte[] {}, HttpURLConnection.HTTP_OK);
        // set the mock url
        userData.setProfileImageURL(server.url(MOCK_URL).toString());

        UserImageEndpoint.getInstance().getUserProfileImage(userData, new IUserImageCallback() {
            @Override
            public void onFail() {
                mFailed = false;
                signal.countDown();
            }

            @Override
            public void onResponse(Bitmap profileImage) {
                mFailed = true;
                signal.countDown();
            }
        });

        // wait for callback
        signal.await();

        // let it fail!
        if(mFailed) {
            fail();
        }
    }


    @Test
    public void getUserProfileImageValidBitmapTest() throws Exception {
        // creates the test bitmap
        final Bitmap bitmap = Bitmap.createBitmap(10,10, Bitmap.Config.ARGB_8888);

        final CountDownLatch signal = new CountDownLatch(1);
        // create the user data
        UserDataModel userData = new UserDataModel();
        // start the mock server with an empty buffer
        MockWebServer server = startMockServer(
                bitmapToJsonArray(bitmap), HttpURLConnection.HTTP_OK);
        // set the mock url
        userData.setProfileImageURL(server.url(MOCK_URL).toString());

        UserImageEndpoint.getInstance().getUserProfileImage(userData, new IUserImageCallback() {
            @Override
            public void onFail() {
                mFailed = true;
                signal.countDown();
            }

            @Override
            public void onResponse(Bitmap profileImage) {
                mFailed = true;

                try {
                    Assert.assertNotNull(profileImage);
                    Assert.assertEquals(bitmap.getByteCount(), profileImage.getByteCount());
                    mFailed = false;
                } finally {
                    signal.countDown();
                }
            }
        });

        // wait for callback
        signal.await();

        // let it fail!
        if(mFailed) {
            fail();
        }
    }


    @Test
    public void getUserProfileImageServerUnreachable() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        // create the user data
        UserDataModel userData = new UserDataModel();
        // start the mock server with an empty buffer
        MockWebServer server = startMockServer(new byte[] {}, HttpURLConnection.HTTP_OK);
        // set the mock url
        userData.setProfileImageURL(server.url(MOCK_URL).toString());
        // shutdown the server again
        server.shutdown();

        UserImageEndpoint.getInstance().getUserProfileImage(userData, new IUserImageCallback() {
            @Override
            public void onFail() {
                mFailed = false;
                signal.countDown();
            }

            @Override
            public void onResponse(Bitmap profileImage) {
                mFailed = true;
                signal.countDown();
            }
        });

        // wait for callback
        signal.await();

        // let it fail!
        if(mFailed) {
            fail();
        }
    }


    private static byte[] bitmapToJsonArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        return stream.toByteArray();
    }


    private MockWebServer startMockServer(byte[] body, int responseCode) throws Exception {
        Buffer buffer = new Buffer();
        buffer.write(body);

        // init response
        MockResponse response = new MockResponse();
        response.setResponseCode(responseCode);
        response.setBody(buffer);

        // init web server (only one instance is allowed)
        if(null != mMockServer) {
            mMockServer.shutdown();
        }
        mMockServer = new MockWebServer();
        mMockServer.enqueue(response);
        mMockServer.start();

        return mMockServer;
    }
}
