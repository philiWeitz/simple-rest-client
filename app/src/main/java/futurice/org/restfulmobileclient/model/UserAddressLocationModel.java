package futurice.org.restfulmobileclient.model;


import com.google.gson.annotations.SerializedName;

public class UserAddressLocationModel {

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
}
