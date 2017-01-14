package futurice.org.restfulmobileclient.http;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import futurice.org.restfulmobileclient.model.UserDataModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by my on 14.01.2017.
 */

public class UserDataModelEndpointTest {
    @Test
    public void testGetUserList() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        UserDataEndpoint.getInstance().getUserList(new IUserDataCallback() {
            @Override
            public void onFail() {
                fail();
                signal.countDown();
            }

            @Override
            public void onResponse(List<UserDataModel> userData) {

                Assert.assertNotNull(userData);
                Assert.assertTrue(userData.size() > 0);
                Assert.assertFalse("".equals(userData.get(0).getName()));

                signal.countDown();
            }
        });

        signal.await();

    }
}
