package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bhagya on 22/06/16.
 */
public class SubCategoryListDataModel implements Serializable {
    @SerializedName("sub_cat_id")
    private int sub_cat_id;
    @SerializedName("image_url")
    private String sub_image_url;
    @SerializedName("description")
    private String sub_description;
    @SerializedName("service_charge")
    private String sub_service_charge;
    @SerializedName("sub_cat_name")
    private String sub_cat_name;

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public int getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(int sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getSub_image_url() {
        return sub_image_url;
    }

    public void setSub_image_url(String sub_image_url) {
        this.sub_image_url = sub_image_url;
    }

    public String getSub_description() {
        return sub_description;
    }

    public void setSub_description(String sub_description) {
        this.sub_description = sub_description;
    }

    public String getSub_service_charge() {
        return sub_service_charge;
    }

    public void setSub_service_charge(String sub_service_charge) {
        this.sub_service_charge = sub_service_charge;
    }
}
