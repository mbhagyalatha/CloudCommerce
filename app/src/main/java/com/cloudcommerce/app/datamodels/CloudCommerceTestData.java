package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bhagya on 09/05/16.
 */
public class CloudCommerceTestData {

    @SerializedName("services_list")
    private List<ServiceDataModel> servicesList;

    public List<ServiceDataModel> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<ServiceDataModel> servicesList) {
        this.servicesList = servicesList;
    }
}

