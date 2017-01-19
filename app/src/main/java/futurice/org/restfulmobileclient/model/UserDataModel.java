package futurice.org.restfulmobileclient.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import futurice.org.restfulmobileclient.util.StringUtil;

// holds the whole user data including address and location
public class UserDataModel implements Serializable, Comparable<UserDataModel> {

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private UserAddressModel address = new UserAddressModel();

    // Only to show how to load profile images on the fly
    private String profileImageURL = "http://placehold.it/600/f66b97";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtil.setNonNull(name);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = StringUtil.setNonNull(username);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = StringUtil.setNonNull(email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = StringUtil.setNonNull(phone);
    }

    public UserAddressModel getAddress() {
        if(null == address) {
            address = new UserAddressModel();
        }
        return address;
    }

    public void setAddress(UserAddressModel address) {
        this.address = address;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = StringUtil.setNonNull(profileImageURL);
    }

    public String getFirstCharacterOfName() {
        if(null != name && !"".equals(name)) {
            return name.substring(0, 1);
        }
        return "";
    }

    @Override
    public int compareTo(UserDataModel userDataModel) {
        if(name != null) {
            return name.compareTo(userDataModel.name);
        }
        return 1;
    }
}
