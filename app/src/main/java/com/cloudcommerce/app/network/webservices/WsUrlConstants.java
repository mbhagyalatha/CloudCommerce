package com.cloudcommerce.app.network.webservices;

import android.util.Log;

import com.cloudcommerce.app.CloudCommerceApplication;

/**
 * Created by bhagya on 06/25/2016.
 */
public class WsUrlConstants {

    public static final String API_KEY = "MevVHxhd5bNkYpVjZJ9QrjuHg627m9IdUp8SL45Dw";
    public static final String EP_LOGIN = "";
    public static final String EP_REGISTER = "";
    public static final String EP_GUEST_LOGIN = "";
    public static final String EP_SERVICES_CATEGORIES = "get_categories_list";


    public static String getUrl(String endPoint) {
        String url = null;
        String baseUrl = CloudCommerceApplication.getEnvSettings().getBaseUrl();
        url = baseUrl + endPoint;
        Log.d("service url : ", url);
        return url;
    }
}
