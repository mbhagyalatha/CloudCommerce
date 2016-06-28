package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bhagya on 13/05/16.
 */
public class UserDataModel implements Serializable {
    @SerializedName("userName")
    private String userName;
    @SerializedName("email")
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
