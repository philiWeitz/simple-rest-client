package futurice.org.restfulmobileclient.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDataModel implements Serializable, Comparable<UserDataModel> {

    @SerializedName("name")
    String name;

    @SerializedName("username")
    String username;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("address")
    UserAddressModel address;

    // Only to show how to load profile images on the fly
    String profileImageURL = "http://placehold.it/600/f66b97";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserAddressModel getAddress() {
        return address;
    }

    public void setAddress(UserAddressModel address) {
        this.address = address;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    @Override
    public int compareTo(UserDataModel userDataModel) {
        if(name != null) {
            return name.compareTo(userDataModel.name);
        }
        return 1;
    }
}
