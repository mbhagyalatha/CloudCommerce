package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bhagya on 22/06/16.
 */
public class SubServiceDataModel implements Serializable {
    @SerializedName("sub_service_name")
    private String subServiceName;
    @SerializedName("sub_service_desc")
    private String subServiceDesc;
    private String servicePicUrl;

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

    public String getSubServiceDesc() {
        return subServiceDesc;
    }

    public void setSubServiceDesc(String subServiceDesc) {
        this.subServiceDesc = subServiceDesc;
    }

    public String getServicePicUrl() {
        return servicePicUrl;
    }

    public void setServicePicUrl(String servicePicUrl) {
        this.servicePicUrl = servicePicUrl;
    }
}
