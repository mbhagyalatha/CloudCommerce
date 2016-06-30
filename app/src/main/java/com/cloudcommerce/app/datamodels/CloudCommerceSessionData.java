package com.cloudcommerce.app.datamodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cloudcommerce.app.CloudCommerceApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by developer on 12/05/16.
 */
public class CloudCommerceSessionData {
    private static CloudCommerceSessionData sessionData;
    public static String CLOUD_COMMERCE_APP = "CloudCommerceApp";
    public static String SHPREF_KEY_USER_OBJECT = "SHPREF_KEY_USER_OBJECT";
    public static String SHPREF_KEY_IS_FIRST_LAUNCH = "SHPREF_KEY_IS_FIRST_LAUNCH";
    public static boolean isFirstlaunch;
    public static String selectedMenuTitle;
    public static String SHPREF_KEY_SELECTED_MENU_TITLE = "SHPREF_KEY_SELECTED_MENU_TITLE";
    public static String SHPREF_KEY_ACCESS_TOKEN = "SHPREF_KEY_ACCESS_TOKEN";

    public static CategoryDataModel categoryDataModel;
    public static SubCategoryDataModel subCategoryDataModel;
    public static Address address;

    public static CloudCommerceSessionData getSessionDataInstance() {
        if (sessionData == null) {
            sessionData = new CloudCommerceSessionData();
        }
        return sessionData;
    }

    public void clearData() {
        CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit().clear().commit();
    }

    public static boolean isFirstlaunch() {
        boolean isFirstlaunch = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getBoolean(SHPREF_KEY_IS_FIRST_LAUNCH, false);
        return isFirstlaunch;
    }

    public static void setIsFirstlaunch(boolean isFirstlaunch) {
        CloudCommerceSessionData.isFirstlaunch = isFirstlaunch;
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putBoolean(SHPREF_KEY_IS_FIRST_LAUNCH, isFirstlaunch);
        prefsEditor.commit();
    }

    public static String getSelectedMenuTitle() {
        String selectedTitle = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_SELECTED_MENU_TITLE, null);
        return selectedTitle;
    }

    public static void setSelectedMenuTitle(String selectedMenuTitle) {
        CloudCommerceSessionData.selectedMenuTitle = selectedMenuTitle;
        Log.d("Session data", " context " + CloudCommerceApplication.getAppContext());
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(SHPREF_KEY_SELECTED_MENU_TITLE, selectedMenuTitle);
        prefsEditor.commit();
    }

    public static UserDataModel getUserData() {
        Gson gson = new Gson();
        String json = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_USER_OBJECT, null);
        UserDataModel userDataModel = gson.fromJson(json, UserDataModel.class);
        return userDataModel;
    }

    public static void setUserJsonData(UserDataModel currentUserDataModel) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String userJsonString = gson.toJson(currentUserDataModel);
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(SHPREF_KEY_USER_OBJECT, userJsonString);
        prefsEditor.commit();
    }

    public static CategoryDataModel getCategoryDataModel() {
        return categoryDataModel;
    }

    public static void setCategoryDataModel(CategoryDataModel categoryDataModel) {
        CloudCommerceSessionData.categoryDataModel=categoryDataModel;
    }

    public static SubCategoryDataModel getSubCategoryDataModel() {
        return subCategoryDataModel;
    }

    public static void setSubCategoryDataModel(SubCategoryDataModel subcategoryDataModel) {
        CloudCommerceSessionData.subCategoryDataModel=subcategoryDataModel;
    }

    public static void setAddress(Address address){
        CloudCommerceSessionData.address=address;
    }
    public static Address getAddress(){
        return address;
    }
}
