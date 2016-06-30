package com.cloudcommerce.app.network.webservices;

import android.util.Log;

import com.cloudcommerce.app.CloudCommerceApplication;

/**
 * Created by bhagya on 06/25/2016.
 */
public class WsUrlConstants {

    public static final String REG_FIRST_NAME="[first_name]";
    public static final String REG_LAST_NAME="[last_name]";
    public static final String REG_EMAIL="[email]";

    public static final String ADD_ADDR_NAME="[name]";
    public static final String ADD_ADDR_STREET="[street]";
    public static final String ADD_ADDR_CITY="[city]";
    public static final String ADD_ADDR_STATE="[state]";
    public static final String ADD_ADDR_PHONE_NUM="[phone_number]";
    public static final String ADD_ADDR_USERID="[user_id]";
    public static final String ADD_ADDR_PINCODE="[pincode]";

    public static final String SER_CNF_USERID="[userid]";
    public static final String SER_CNF_USER_ADDRESSID="[user_address_id]";
    public static final String SER_CNF_SERVICEID="[service_id]";

    public static final String API_KEY = "MevVHxhd5bNkYpVjZJ9QrjuHg627m9IdUp8SL45Dw";
    public static final String EP_LOGIN = "user_login?email=";
    public static final String EP_REGISTER = "register_user?first_name="+REG_FIRST_NAME +"&last_name="+REG_LAST_NAME+"&email="+REG_EMAIL;
    public static final String EP_GUEST_LOGIN = "";
    public static final String EP_SERVICES_CATEGORIES = "get_categories_list";
    public static final String EP_SUB_SERVICES_CATEGORIES = "get_category_specification?cat_id=";
    public static final String EP_ADD_ADDRESS = "create_new_address?user_id="+ADD_ADDR_USERID+"&name="+ADD_ADDR_NAME+"&street="+ADD_ADDR_STREET+
            "&pincode="+ADD_ADDR_PINCODE+"&city="+ADD_ADDR_CITY+"&state="+ADD_ADDR_STATE+"&phone_number="+ADD_ADDR_PHONE_NUM;

    public static final String EP_SERVICE_CONFIRMATION = "service_confirmation?user_id="+SER_CNF_USERID+"&user_address_id="+SER_CNF_USER_ADDRESSID+"&service_id="+SER_CNF_SERVICEID;

    public static String getUrl(String endPoint) {
        String url = null;
        String baseUrl = CloudCommerceApplication.getEnvSettings().getBaseUrl();
        url = baseUrl + endPoint;
        Log.d("service url : ", url);
        return url;
    }
}
