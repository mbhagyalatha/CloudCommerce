package com.cloudcommerce.app.network.webservices;

import android.util.Log;

import com.cloudcommerce.app.datamodels.CategoryDataModel;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.network.WebServiceResultListener;
import com.cloudcommerce.app.utils.AppConstants;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by bhagya on 06/25/2016.
 */
public class AddressListWsImpl extends BaseWsImpl {

    public AddressListWsImpl(int reqId, WebServiceResultListener webServiceResultListener) {
        super(reqId, webServiceResultListener);
    }


    public void getAddressList() {
        String url = WsUrlConstants.getUrl(WsUrlConstants.EP_GET_ADDRESS_LIST);
        url=url.replace(WsUrlConstants.GET_ADDR_LIST_USERID, CloudCommerceSessionData.getSessionDataInstance().getUserid());
        Log.d("Register url: ", url);
        sendGetRequest(url);
    }

    @Override
    protected void parseResponse(Object response) {
        broadcastResponse(response);
    }
}
