package com.cloudcommerce.app.network.webservices;

import android.util.Log;

import com.cloudcommerce.app.datamodels.CategoryDataModel;
import com.cloudcommerce.app.network.WebServiceResultListener;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class ServiceConfirmationWsImpl extends BaseWsImpl {

    public ServiceConfirmationWsImpl(int reqId, WebServiceResultListener webServiceResultListener) {
        super(reqId, webServiceResultListener);
    }


    public void getConfirmation(String userid,String user_address_id,String service_id) {
        String url = WsUrlConstants.getUrl(WsUrlConstants.EP_SERVICE_CONFIRMATION);
        url = url.replace(WsUrlConstants.SER_CNF_USERID,userid);
        url = url.replace(WsUrlConstants.SER_CNF_USER_ADDRESSID,user_address_id);
        url = url.replace(WsUrlConstants.SER_CNF_SERVICEID,service_id);
        Log.d("Register url: ", url);
        sendGetRequest(url);
    }

    @Override
    protected void parseResponse(Object response) {
        broadcastResponse(response);
    }
}
