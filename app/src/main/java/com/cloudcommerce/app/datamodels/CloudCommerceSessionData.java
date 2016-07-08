package com.cloudcommerce.app.datamodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.utils.SerializeDeserialize;
import com.cloudcommerce.app.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 12/05/16.
 */
public class CloudCommerceSessionData {
    private static CloudCommerceSessionData sessionData;
    public static String CLOUD_COMMERCE_APP = "CloudCommerceApp";
    public static String SHPREF_KEY_USER_OBJECT = "SHPREF_KEY_USER_OBJECT";
    public static String SHPREF_KEY_IS_FIRST_LAUNCH = "SHPREF_KEY_IS_FIRST_LAUNCH";
    public static String SHPREF_KEY_FROM_SCREEN_LOGIN = "SHPREF_KEY_FROM_SCREEN_LOGIN";
    public static String SHPREF_KEY_FROM_SCREEN_REG = "SHPREF_KEY_FROM_SCREEN_REG";

    public static boolean isFirstlaunch;
    public static String selectedMenuTitle;
    public static String SHPREF_KEY_SELECTED_MENU_TITLE = "SHPREF_KEY_SELECTED_MENU_TITLE";
    public static String SHPREF_KEY_ACCESS_TOKEN = "SHPREF_KEY_ACCESS_TOKEN";
    public static String SHPREF_KEY_USER_ID = "SHPREF_KEY_USER_ID";
    public static String SHPREF_KEY_ADDRESS_ID = "SHPREF_KEY_ADDRESS_ID";
    public static String SHPREF_KEY_USER_ADDRESSES = "SHPREF_KEY_USER_ADDRESSES";
    public static String SHPREF_KEY_USER_ADDRESSES_OBJ = "SHPREF_KEY_USER_ADDRESSES_OBJ";

    public static CategoryDataModel categoryDataModel;
    public static SubCategoryDataModel subCategoryDataModel;
    public static SubCategoryListDataModel subCategoryListDataModel;
    public static UserAddresses userAddresses;
    public static Address address;
    public static String registerResponse="";
    public static String loginResponse="";
    public static String getAddressListResponse="";
    public static String serviceConfirmationResponse="";
    public static String userid="";
    public static String add_address_response="";
    public static String address_id="";
    public static ArrayList<UserAddresses> userAddressesList;
    public static boolean isaddressSelected;
    public static USERADDRESSOBJ useraddressobj;

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

    public static void setFromScreenLogin(String  from_screen_login) {
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(SHPREF_KEY_FROM_SCREEN_LOGIN, from_screen_login);
        prefsEditor.commit();
    }

    public static String getFromScreenLogin() {
        String  from_screen_login = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_FROM_SCREEN_LOGIN, null);
        return from_screen_login;
    }

    public static void setFromScreenReg(String  from_screen_reg) {
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(SHPREF_KEY_FROM_SCREEN_REG, from_screen_reg);
        prefsEditor.commit();
    }

    public static String getFromScreenReg() {
        String  from_screen_login = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_FROM_SCREEN_REG, null);
        return from_screen_login;
    }

    public static void setIsFirstlaunch(boolean isFirstlaunch) {
        CloudCommerceSessionData.isFirstlaunch = isFirstlaunch;
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putBoolean(SHPREF_KEY_IS_FIRST_LAUNCH, isFirstlaunch);
        prefsEditor.commit();
    }

    public static String getUserid(){
        String userid = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_USER_ID, "0");
        Log.d("USERID IS","<>"+userid);
        return userid;
    }

    public static void setUserid(String userid) {
        CloudCommerceSessionData.userid = userid;
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(SHPREF_KEY_USER_ID, userid);
        prefsEditor.commit();
    }

    public static String getAddressid(){
        String userid = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_ADDRESS_ID, "0");
        return userid;
    }

    public static void setAddressid(String address_id) {
        CloudCommerceSessionData.address_id = address_id;
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(SHPREF_KEY_ADDRESS_ID, address_id);
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

    public static SubCategoryListDataModel getSubCategoryListDataModel() {
        return subCategoryListDataModel;
    }

    public static void setSubCategoryListDataModel(SubCategoryListDataModel subcategoryListDataModel) {
        CloudCommerceSessionData.subCategoryListDataModel=subcategoryListDataModel;
    }

    public static void setAddress(Address address){
        CloudCommerceSessionData.address=address;
    }
    public static Address getAddress(){
        return address;
    }

    public static void setRegisterResponse(String registerResponse){
        CloudCommerceSessionData.registerResponse = registerResponse;
    }
    public static String getRegisterResponse(){;
        return registerResponse;
    }

    public static void setLoginResponse(String loginResponse){
        CloudCommerceSessionData.loginResponse = loginResponse;
    }
    public static String getLoginResponse(){;
        return loginResponse;
    }

    public static void setAddressListResponse(String getAddressListResponse){
        CloudCommerceSessionData.getAddressListResponse = getAddressListResponse;
    }
    public static String getAddressListResponse(){;
        return getAddressListResponse;
    }

    public static void setServiceConfirmationResponse(String serviceConfirmationResponse){
        CloudCommerceSessionData.serviceConfirmationResponse = serviceConfirmationResponse;
    }
    public static String getServiceConfirmationResponse(){;
        return serviceConfirmationResponse;
    }

    public static void setAdd_address_response(String add_address_response){
        CloudCommerceSessionData.add_address_response=add_address_response;
    }

    public static String getAdd_address_response(){
        return add_address_response;
    }

    public static void setUserAddressesList(ArrayList<UserAddresses> userAddressesList){
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(SHPREF_KEY_USER_ADDRESSES, SerializeDeserialize.serialize(userAddressesList));
        prefsEditor.commit();
    }

    public static ArrayList<UserAddresses> getUserAddressesList()
    {
        userAddressesList = (ArrayList<UserAddresses>) SerializeDeserialize.deserialize(CloudCommerceApplication.getAppContext()
                .getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE)
                .getString(SHPREF_KEY_USER_ADDRESSES, SerializeDeserialize.serialize(new ArrayList<UserAddresses>())));
        return userAddressesList;
    }

    public static void setUserAddressObj(USERADDRESSOBJ userAddressObj){
        SharedPreferences.Editor prefsEditor = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAddressObj); // myObject - instance of MyObject
        prefsEditor.putString(SHPREF_KEY_USER_ADDRESSES_OBJ, json);
        prefsEditor.commit();
    }

    public static USERADDRESSOBJ getUserAddressesObj()
    {
        Gson gson = new Gson();
        String json = CloudCommerceApplication.getAppContext().getSharedPreferences(CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_USER_ADDRESSES_OBJ, null);
        USERADDRESSOBJ useraddressobj = gson.fromJson(json, USERADDRESSOBJ.class);
        return useraddressobj;
    }
}
