package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by developer on 30/6/16.
 */
public class USERADDRESSSID implements Serializable {
    @SerializedName("$oid")
    private String user_address_id;

    public String getUser_address_id() {
        return user_address_id;
    }

    public void setUser_address_id(String user_address_id) {
        this.user_address_id = user_address_id;
    }
}
