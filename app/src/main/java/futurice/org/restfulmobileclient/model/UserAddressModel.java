package futurice.org.restfulmobileclient.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import futurice.org.restfulmobileclient.util.StringUtil;

// holds the address of a user
public class UserAddressModel implements Serializable {

    @SerializedName("street")
    private String street = "";

    @SerializedName("suite")
    private String suite = "";

    @SerializedName("city")
    private String city = "";

    @SerializedName("zipcode")
    private String zipcode = "";

    @SerializedName("geo")
    private UserAddressLocationModel location = new UserAddressLocationModel();


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = StringUtil.setNonNull(street);
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = StringUtil.setNonNull(suite);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = StringUtil.setNonNull(city);
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = StringUtil.setNonNull(zipcode);
    }

    public UserAddressLocationModel getLocation() {
        if(null == location) {
            location = new UserAddressLocationModel();
        }
        return location;
    }

    public void setLocation(UserAddressLocationModel location) {
        this.location = location;
    }

    private String getFirstAddressLine() {
        final StringBuilder sb = new StringBuilder();
        sb.append(street);
        StringUtil.appendToStringWithSeparator(sb, suite, ", ");
        return sb.toString();
    }

    private String getSecondAddressLine() {
        final StringBuilder sb = new StringBuilder();
        sb.append(zipcode);
        StringUtil.appendToStringWithSeparator(sb, city, ", ");
        return sb.toString();
    }

    public String getAddressString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getFirstAddressLine());
        StringUtil.appendToStringWithSeparator(sb, getSecondAddressLine(), "\n");
        return sb.toString();
    }
}
