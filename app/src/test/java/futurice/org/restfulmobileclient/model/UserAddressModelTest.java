package futurice.org.restfulmobileclient.model;


import junit.framework.Assert;

import org.junit.Test;

public class UserAddressModelTest {

    private static final String CITY_NAME = "city";
    private static final String STREET_NAME = "street name";
    private static final String SUITE_NAME = "suite";
    private static final String ZIP_CODE_NAME = "zip code";
    private static final String STREET_NAME_AND_CITY = STREET_NAME +  "\n" +CITY_NAME;

    @Test
    public void testGetAddressString() throws Exception {
        UserAddressModel addressModel = new UserAddressModel();

        // should be an empty string
        Assert.assertTrue("".equals(addressModel.getAddressString()));

        // should show only the city (without the ',' and a new line)
        addressModel.setCity(CITY_NAME);
        Assert.assertTrue(CITY_NAME.equals(addressModel.getAddressString()));

        // should show two lines
        addressModel.setStreet(STREET_NAME);
        Assert.assertTrue(STREET_NAME_AND_CITY.equals(addressModel.getAddressString()));

        // full address line
        addressModel.setSuite(SUITE_NAME);
        addressModel.setZipcode(ZIP_CODE_NAME);
        Assert.assertFalse("".equals(addressModel.getAddressString()));
    }
}
