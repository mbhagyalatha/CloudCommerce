package com.cloudcommerce.app.network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CategoryDataModel;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.CategoryListDataModel;
import com.cloudcommerce.app.datamodels.SubCategoryDataModel;
import com.cloudcommerce.app.network.webservices.BaseWsImpl;
import com.cloudcommerce.app.network.webservices.ServicecategoriesWsImpl;
import com.cloudcommerce.app.network.webservices.SubServiceCategoryWsImpl;
import com.cloudcommerce.app.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SubCategoryService extends BaseCloudCommerceService implements WebServiceResultListener {

    private Intent broadcastIntent = new Intent(AppConstants.GET_ALL_SUB_SERVICE_CATEGORIES);

    public SubCategoryService(Context context) {
        super(context);
    }


    public void getAllSubServiceCategories(int cat_id) {
        SubServiceCategoryWsImpl subServiceCategoryWs = new SubServiceCategoryWsImpl(AppConstants.GET_ALL_SUB_SERVICE_CATEGORIES_REQUEST_ID, this);
        subServiceCategoryWs.getAllSubServiceCategory(cat_id);
    }


    @Override
    public void onServiceCompleted(Object response, Object error, int reqId) {
        if (error == null) {
            switch (reqId)
            {
                case AppConstants.GET_ALL_SUB_SERVICE_CATEGORIES_REQUEST_ID:
                    broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
                    sendResponse((SubCategoryDataModel)response);
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
                if (reqId == AppConstants.GET_ALL_SUB_SERVICE_CATEGORIES_REQUEST_ID || BaseWsImpl.statusCode == 401)
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

    private void sendResponse(SubCategoryDataModel response) {
        broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        CloudCommerceSessionData.getSessionDataInstance().setSubCategoryDataModel(response);
    }
}
