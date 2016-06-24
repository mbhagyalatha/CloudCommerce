package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bhagya on 09/05/16.
 */
public class CloudCommerceTestData implements Serializable {

    @SerializedName("services_list")
    private List<ServiceDataModel> servicesList;
    @SerializedName("sub_services_list")
    private List<SubServiceDataModel> subServicesList;
    @SerializedName("address_list")
    private List<Address> addressList;

    public List<ServiceDataModel> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<ServiceDataModel> servicesList) {
        this.servicesList = servicesList;
    }

    public List<SubServiceDataModel> getSubServicesList() {
        return subServicesList;
    }

    public void setSubServicesList(List<SubServiceDataModel> subServicesList) {
        this.subServicesList = subServicesList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}

