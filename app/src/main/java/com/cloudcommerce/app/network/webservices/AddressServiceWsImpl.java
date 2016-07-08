package com.cloudcommerce.app.network.webservices;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.datamodels.Address;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.UserAddresses;
import com.cloudcommerce.app.network.WebServiceResultListener;
import com.cloudcommerce.app.utils.AppConstants;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhagya on 06/25/2016.
 */
public class AddressServiceWsImpl extends BaseWsImpl {

    public AddressServiceWsImpl(int reqId, WebServiceResultListener webServiceResultListener) {
        super(reqId, webServiceResultListener);
    }


    public void postAddress(UserAddresses address) {
        String url = WsUrlConstants.getUrl(WsUrlConstants.EP_ADD_ADDRESS);
        url=url.replace(WsUrlConstants.ADD_ADDR_USERID, CloudCommerceSessionData.getSessionDataInstance().getUserid());
        url=url.replace(WsUrlConstants.ADD_ADDR_NAME,address.getName().replace(" ","%20"));
        url=url.replace(WsUrlConstants.ADD_ADDR_STREET,address.getStreet().replace(" ","%20"));
        url=url.replace(WsUrlConstants.ADD_ADDR_PINCODE,address.getPincode());
        url=url.replace(WsUrlConstants.ADD_ADDR_CITY,address.getCity().replace(" ","%20"));
        url=url.replace(WsUrlConstants.ADD_ADDR_STATE,address.getState().replace(" ","%20"));
        url=url.replace(WsUrlConstants.ADD_ADDR_PHONE_NUM,address.getPhone_number());
        Log.d("Register url: ", url);
        sendPostRequest(url);
        CloudCommerceSessionData.userAddresses = address;
    }

    @Override
    protected void parseResponse(Object response) {
        broadcastResponse(response);
    }
}
