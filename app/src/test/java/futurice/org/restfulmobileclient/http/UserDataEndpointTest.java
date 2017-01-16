package futurice.org.restfulmobileclient.http;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import futurice.org.restfulmobileclient.TestUtil;
import futurice.org.restfulmobileclient.model.UserDataModel;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;



public class UserDataEndpointTest {
    private static final String TAG = "UserDataEndpointTest";

    // url field for user data
    private static final String FIELD_USER_DATA_URL = "URL_USER_LIST";

    // invalid JSON response
    private static String JSON_INVALID_BODY = "invalid body";
    // valid JSON response
    private static String JSON_VALID_BODY = "[{\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Leanne Graham\",\n" +
            "    \"username\": \"Bret\",\n" +
            "    \"email\": \"Sincere@april.biz\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"Kulas Light\",\n" +
            "      \"suite\": \"Apt. 556\",\n" +
            "      \"city\": \"Gwenborough\",\n" +
            "      \"zipcode\": \"92998-3874\",\n" +
            "      \"geo\": {\n" +
            "        \"lat\": \"-37.3159\",\n" +
            "        \"lng\": \"81.1496\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\": \"1-770-736-8031 x56442\",\n" +
            "    \"website\": \"hildegard.org\",\n" +
            "    \"company\": {\n" +
            "      \"name\": \"Romaguera-Crona\",\n" +
            "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
            "      \"bs\": \"harness real-time e-markets\"\n" +
            "    }\n" +
            "  }]";


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
    public void testGetUserListInvalidJsonResponse() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        // start web server
        MockWebServer server = startMockServer(JSON_INVALID_BODY, HttpURLConnection.HTTP_OK);
        // change the url
        TestUtil.changePrivateStaticVariable(UserDataEndpoint.class,
                FIELD_USER_DATA_URL, server.url(MOCK_URL).toString());

        UserDataEndpoint.getInstance().getUserList(new IUserDataCallback() {
            @Override
            public void onFail() {
                mFailed = false;
                signal.countDown();
            }

            @Override
            public void onResponse(List<UserDataModel> userData) {
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
    public void testGetUserListValidJsonResponse() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        // start web server
        MockWebServer server = startMockServer(JSON_VALID_BODY, HttpURLConnection.HTTP_OK);
        // change the url
        TestUtil.changePrivateStaticVariable(UserDataEndpoint.class,
                FIELD_USER_DATA_URL, server.url(MOCK_URL).toString());

        UserDataEndpoint.getInstance().getUserList(new IUserDataCallback() {
            @Override
            public void onFail() {
                mFailed = true;
                signal.countDown();
            }

            @Override
            public void onResponse(List<UserDataModel> userData) {
                mFailed = true;

                try {
                    Assert.assertTrue(userData.size() > 0);
                    Assert.assertFalse("".equals(userData.get(0).getName()));
                    Assert.assertFalse("".equals(userData.get(0).getUsername()));
                    Assert.assertFalse("".equals(userData.get(0).getEmail()));
                    Assert.assertFalse("".equals(userData.get(0).getPhone()));
                    Assert.assertNotNull(userData.get(0).getAddress());
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

    private MockWebServer startMockServer(String body, int responseCode) throws Exception {
        // init response
        MockResponse response = new MockResponse();
        response.setResponseCode(responseCode);
        response.setBody(body);

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
