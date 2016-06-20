package com.cloudcommerce.app.datamodels;

import android.content.Context;
import android.content.SharedPreferences;

import com.cloudcommerce.app.CloudCommerceApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by developer on 12/05/16.
 */
public class CloudCommerceSessionData {
    private static CloudCommerceSessionData sessionData;
    public static String MURPHY_APP = "MurphyApp";
    public static String SHPREF_KEY_USER_OBJECT = "SHPREF_KEY_USER_OBJECT";
    public static String SHPREF_KEY_IS_FIRST_LAUNCH = "SHPREF_KEY_IS_FIRST_LAUNCH";
    public static boolean isFirstlaunch;
    public static String selectedMenuTitle;
    public static String SHPREF_KEY_SELECTED_MENU_TITLE = "SHPREF_KEY_SELECTED_MENU_TITLE";

    public static CloudCommerceSessionData getSessionDataInstance() {
        if (sessionData == null) {
            sessionData = new CloudCommerceSessionData();
        }
        return sessionData;
    }

    public void clearData(){
        CloudCommerceApplication.getAppContext().getSharedPreferences(MURPHY_APP, Context.MODE_PRIVATE).edit().clear().commit();
    }

    public static boolean isFirstlaunch() {
        boolean isFirstlaunch = CloudCommerceApplication.getAppContext().getSharedPreferences(MURPHY_APP, Context.MODE_PRIVATE).getBoolean(SHPREF_KEY_IS_FIRST_LAUNCH, false);
        return isFirstlaunch;
    }

    public static void setIsFirstlaunch(boolean isFirstlaunch) {
        CloudCommerceSessionData.isFirstlaunch = isFirstlaunch;
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(MURPHY_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putBoolean(SHPREF_KEY_IS_FIRST_LAUNCH, isFirstlaunch);
        prefsEditor.commit();
    }

    public static String getSelectedMenuTitle() {
        String selectedTitle = CloudCommerceApplication.getAppContext().getSharedPreferences(MURPHY_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_SELECTED_MENU_TITLE, null);
        return selectedTitle;
    }

    public static void setSelectedMenuTitle(String selectedMenuTitle) {
        CloudCommerceSessionData.selectedMenuTitle = selectedMenuTitle;
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(MURPHY_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(SHPREF_KEY_SELECTED_MENU_TITLE, selectedMenuTitle);
        prefsEditor.commit();
    }
}
