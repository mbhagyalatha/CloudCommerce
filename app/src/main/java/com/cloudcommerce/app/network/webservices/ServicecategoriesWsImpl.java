package com.cloudcommerce.app.network.webservices;

import android.util.Log;

import com.cloudcommerce.app.network.WebServiceResultListener;

/**
 * Created by bhagya on 06/25/2016.
 */
public class ServicecategoriesWsImpl extends BaseWsImpl {

    public ServicecategoriesWsImpl(int reqId, WebServiceResultListener webServiceResultListener) {
        super(reqId, webServiceResultListener);
    }


    public void getAllServiceCategoriesRequest() {
        String url = WsUrlConstants.getUrl(WsUrlConstants.EP_SERVICES_CATEGORIES);
        Log.d("Register url: ", url);
        sendGetRequest(url);
    }


    @Override
    protected void parseResponse(Object response) {
        broadcastResponse(response);
    }
}
