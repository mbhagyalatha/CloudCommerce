package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Santosh on 11/8/2015.
 */
public class EnvironmentSettings implements Serializable {
    @SerializedName("protocall")
    private String protocall;
    @SerializedName("crittercism_api_key")
    private String crittercismApiKey;

    public String getProtocall() {
        return protocall;
    }

    public void setProtocall(String protocall) {
        this.protocall = protocall;
    }

    public String getCrittercismApiKey() {
        return crittercismApiKey;
    }

    public void setCrittercismApiKey(String crittercismApiKey) {
        this.crittercismApiKey = crittercismApiKey;
    }

}
