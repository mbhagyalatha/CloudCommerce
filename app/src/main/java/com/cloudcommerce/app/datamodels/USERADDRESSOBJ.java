package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by developer on 30/6/16.
 */
public class USERADDRESSOBJ implements Serializable {
    @SerializedName("user_addresses")
    private List<UserAddresses> useraddresses;

    public List<UserAddresses> getUseraddresses() {
        return useraddresses;
    }

    public void setUseraddresses(List<UserAddresses> useraddresses) {
        this.useraddresses = useraddresses;
    }
}
