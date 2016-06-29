package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bhagya on 09/05/16.
 */
public class CloudCommerceTestData implements Serializable {

    @SerializedName("services_list")
    private List<CategoryListDataModel> servicesList;
    @SerializedName("sub_services_list")
    private List<SubCategoryListDataModel> subServicesList;
    @SerializedName("address_list")
    private List<Address> addressList;

    public List<CategoryListDataModel> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<CategoryListDataModel> servicesList) {
        this.servicesList = servicesList;
    }

    public List<SubCategoryListDataModel> getSubServicesList() {
        return subServicesList;
    }

    public void setSubServicesList(List<SubCategoryListDataModel> subServicesList) {
        this.subServicesList = subServicesList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}

