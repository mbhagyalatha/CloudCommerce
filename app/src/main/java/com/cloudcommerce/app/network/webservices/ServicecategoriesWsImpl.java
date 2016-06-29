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
        if (response != null) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(response.toString().getBytes())));
            Object result = gson.fromJson(jsonReader, CategoryDataModel.class);
            broadcastResponse(result);
        } else
            broadcastResponse(response);
    }
}
