package futurice.org.restfulmobileclient.model;


import com.android.annotations.NonNull;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import futurice.org.restfulmobileclient.util.FuturiceStringUtil;

public class UserAddressModel implements Serializable {

    @SerializedName("street")
    String street = "";

    @SerializedName("suite")
    String suite = "";

    @SerializedName("city")
    String city = "";

    @SerializedName("zipcode")
    String zipcode = "";

    @SerializedName("geo")
    UserAddressLocationModel location;


    public String getStreet() {
        return street;
    }

    public void setStreet(@NonNull String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(@NonNull String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(@NonNull String zipcode) {
        this.zipcode = zipcode;
    }

    public UserAddressLocationModel getLocation() {
        return location;
    }

    public void setLocation(UserAddressLocationModel location) {
        this.location = location;
    }

    private String getFirstAddressLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(street);
        FuturiceStringUtil.appendToStringWithSeparator(sb, suite, ", ");
        return sb.toString();
    }

    private String getSecondAddressLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(zipcode);
        FuturiceStringUtil.appendToStringWithSeparator(sb, city, ", ");
        return sb.toString();
    }

    public String getAddressString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFirstAddressLine());
        FuturiceStringUtil.appendToStringWithSeparator(sb, getSecondAddressLine(), "\n");
        return sb.toString();
    }
}
