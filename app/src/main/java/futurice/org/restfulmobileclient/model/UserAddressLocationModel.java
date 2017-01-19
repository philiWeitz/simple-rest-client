package futurice.org.restfulmobileclient.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import futurice.org.restfulmobileclient.util.StringUtil;

// holds the address location of a user
public class UserAddressLocationModel implements Serializable {

    private static final String LOCATION_STRING_PREFIX = "geo:";
    private static final String LOCATION_STRING_SEPARATOR = ",";
    private static final String LOCATION_STRING_ZOOM_FACTOR = "?z=20";


    @SerializedName("lat")
    private String lat = "";

    @SerializedName("lng")
    private String lng = "";


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = StringUtil.setNonNull(lat);
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = StringUtil.setNonNull(lng);
    }

    public String getGoogleMapsString() {
        final StringBuilder sb = new StringBuilder();

        sb.append(LOCATION_STRING_PREFIX)
                .append(lat).append(LOCATION_STRING_SEPARATOR)
                .append(lng).append(LOCATION_STRING_ZOOM_FACTOR);

        return sb.toString();
    }
}
