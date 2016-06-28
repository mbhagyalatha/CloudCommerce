package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bhagya on 21/06/16.
 */
public class ServiceDataModel implements Serializable {
    @SerializedName("serviceName")
    private String serviceName;
    @SerializedName("serviceOfferInfo")
    private String serviceOfferInfo;
    private String servicePicUrl;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePicUrl() {
        return servicePicUrl;
    }

    public void setServicePicUrl(String servicePicUrl) {
        this.servicePicUrl = servicePicUrl;
    }

    public String getServiceOfferInfo() {
        return serviceOfferInfo;
    }

    public void setServiceOfferInfo(String serviceOfferInfo) {
        this.serviceOfferInfo = serviceOfferInfo;
    }
}
