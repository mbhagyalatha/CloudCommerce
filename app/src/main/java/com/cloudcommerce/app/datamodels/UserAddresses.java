package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by developer on 30/6/16.
 */
public class UserAddresses implements Serializable {
    @SerializedName("user_address_id")
    private USERADDRESSSID useraddresssid;

    @SerializedName("name")
    private String name;

    @SerializedName("street")
    private String street;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @SerializedName("pincode")
    private String pincode;
    @SerializedName("state")
    private String state;
    @SerializedName("city")
    private String city;
    private boolean isSelected;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public USERADDRESSSID getUseraddresssid() {
        return useraddresssid;
    }

    public void setUseraddresssid(USERADDRESSSID useraddresssid) {
        this.useraddresssid = useraddresssid;
    }

    @SerializedName("phone_number")
    private String phone_number;

}
