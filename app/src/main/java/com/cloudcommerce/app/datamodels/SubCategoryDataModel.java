package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by developer on 29/6/16.
 */
public class SubCategoryDataModel implements Serializable {
    public List<SubCategoryListDataModel> getSubCategoryListDataModels() {
        return subCategoryListDataModels;
    }

    public void setSubCategoryListDataModels(List<SubCategoryListDataModel> subCategoryListDataModels) {
        this.subCategoryListDataModels = subCategoryListDataModels;
    }

    @SerializedName("category_specification")
    private List<SubCategoryListDataModel> subCategoryListDataModels;
}
