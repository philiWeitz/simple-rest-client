package futurice.org.restfulmobileclient.model;


import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDataModelTest {

    private static final String USER_TOP_NAME = "aa";
    private static final String USER_MIDDLE_NAME = "ab";
    private static final String USER_BOTTOM_NAME = "b";

    @Test
    public void testSortUserDataByName() throws Exception {
        List<UserDataModel> userDataList = new ArrayList<>();
        userDataList.add(getUserData(USER_MIDDLE_NAME));
        userDataList.add(getUserData(USER_BOTTOM_NAME));
        userDataList.add(getUserData(USER_TOP_NAME));

        Collections.sort(userDataList);

        Assert.assertTrue(USER_TOP_NAME.equals(userDataList.get(0).getName()));
        Assert.assertTrue(USER_MIDDLE_NAME.equals(userDataList.get(1).getName()));
        Assert.assertTrue(USER_BOTTOM_NAME.equals(userDataList.get(2).getName()));
    }

    private UserDataModel getUserData(String name) {
        UserDataModel userData = new UserDataModel();
        userData.setName(name);
        return userData;
    }
}
