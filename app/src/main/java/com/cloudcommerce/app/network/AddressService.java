package com.cloudcommerce.app.network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.Address;
import com.cloudcommerce.app.datamodels.CategoryDataModel;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.CategoryListDataModel;
import com.cloudcommerce.app.network.webservices.AddressServiceWsImpl;
import com.cloudcommerce.app.network.webservices.BaseWsImpl;
import com.cloudcommerce.app.network.webservices.ServicecategoriesWsImpl;
import com.cloudcommerce.app.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class AddressService extends BaseCloudCommerceService implements WebServiceResultListener {

    private Intent broadcastIntent = new Intent(AppConstants.ADD_ADDRESS_SERVICE);

    public AddressService(Context context) {
        super(context);
    }


    public void postAddress(Address address) {
        AddressServiceWsImpl addressServiceWs = new AddressServiceWsImpl(AppConstants.ADD_ADDRESS, this);
        addressServiceWs.postAddress(address);
    }


    @Override
    public void onServiceCompleted(Object response, Object error, int reqId) {
        if (error == null) {
            switch (reqId)
            {
                case AppConstants.ADD_ADDRESS:
                    broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
                    sendResponse(response.toString());
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
                if (reqId == AppConstants.ADD_ADDRESS || BaseWsImpl.statusCode == 401)
                    sendError(context.getResources().getString(R.string.net_error));
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

    private void sendResponse(String response) {
        broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        CloudCommerceSessionData.getSessionDataInstance().setAdd_address_response(response);
    }
}
