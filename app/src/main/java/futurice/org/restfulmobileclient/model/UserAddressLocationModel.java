package futurice.org.restfulmobileclient.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserAddressLocationModel implements Serializable {

    @SerializedName("lat")
    String lat;

    @SerializedName("lng")
    String lng;


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getGoogleMapsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("geo:")
                .append(lat).append(",")
                .append(lng).append("?z=20");

        return sb.toString();
    }
}
