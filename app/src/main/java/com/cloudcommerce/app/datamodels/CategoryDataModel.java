package com.cloudcommerce.app.datamodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by developer on 29/6/16.
 */
public class CategoryDataModel implements Serializable {

    @SerializedName("categories_list")
    private List<CategoryListDataModel> categoryListDataModels;

    public List<CategoryListDataModel> getCategoryListDataModels() {
        return categoryListDataModels;
    }

    public void setCategoryListDataModels(List<CategoryListDataModel> categoryListDataModels) {
        this.categoryListDataModels = categoryListDataModels;
    }
}
