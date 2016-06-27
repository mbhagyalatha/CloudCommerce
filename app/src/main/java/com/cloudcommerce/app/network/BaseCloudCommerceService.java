package com.cloudcommerce.app.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Santosh on 11/9/2015.
 */
public class BaseCloudCommerceService {

    protected Context context;
    protected Gson gson;
    public static String CODE;
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    public static final String TOGGLE = "TOGGLE";
    public static final String SIGN_IN = "SIGN_IN";
    private WebServiceResultListener onServiceResultListener;

    /**
     * Context
     */
    public BaseCloudCommerceService(Context context) {
        this.context = context;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public WebServiceResultListener getOnServiceResultListener() {
        return onServiceResultListener;
    }

    public void setOnServiceResultListener(WebServiceResultListener onServiceResultListener) {
        this.onServiceResultListener = onServiceResultListener;
    }
}
