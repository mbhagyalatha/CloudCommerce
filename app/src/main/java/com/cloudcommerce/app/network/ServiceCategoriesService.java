package com.cloudcommerce.app.network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.network.webservices.AuthenticationWsImpl;
import com.cloudcommerce.app.network.webservices.BaseWsImpl;
import com.cloudcommerce.app.utils.AppConstants;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * Created by bhagya on 06/25/2016.
 */
public class ServiceCategoriesService extends BaseCloudCommerceService implements WebServiceResultListener {

    private Intent broadcastIntent = new Intent(AppConstants.GET_ALL_SERVICE_CATEGORIES);

    public ServiceCategoriesService(Context context) {
        super(context);
    }


    public void getAllServiceCategories() {
        AuthenticationWsImpl authenticationWs = new AuthenticationWsImpl(AppConstants.GET_ALL_SERVICE_CATEGORIES_REQUEST_ID, this);
        authenticationWs.sendRegisterRequest();
    }


    @Override
    public void onServiceCompleted(Object response, Object error, int reqId) {
        if (error == null) {
            switch (reqId) {

                case AppConstants.GET_ALL_SERVICE_CATEGORIES_REQUEST_ID:
                    broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
                    break;

            }
        } else {
            String netError = CloudCommerceApplication.getAppContext().getResources().getString(R.string.net_error);
            String errorMsg = (String) response;
            if (((VolleyError) error).networkResponse != null) {

                int statusCode = ((VolleyError) error).networkResponse.statusCode;
                if (statusCode == 401) {
                    //User not authenticated --TODO

                } else {
                    sendError(netError);
                }
            } else {
                if (reqId == AppConstants.LOGIN_REQUEST_ID || BaseWsImpl.statusCode == 401)
                    sendError(AppConstants.INVALID_GRANT);
                else if (errorMsg.equals(context.getResources().getString(R.string.no_internet_access))) {
                    sendError(context.getResources().getString(R.string.no_internet_access));
                } else {
                    sendError(netError);
                }
            }
        }
    }


    private void sendError(String response) {
        broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, false);
        broadcastIntent.putExtra(AppConstants.ERROR_TEXT, response);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }

    private void sendResponse(Object response) {
        broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }
}
