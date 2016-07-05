package com.cloudcommerce.app.network.webservices;

import android.util.Log;

import com.cloudcommerce.app.datamodels.CategoryDataModel;
import com.cloudcommerce.app.datamodels.SubCategoryDataModel;
import com.cloudcommerce.app.network.WebServiceResultListener;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;


public class SubServiceCategoryWsImpl extends BaseWsImpl {

    public SubServiceCategoryWsImpl(int reqId, WebServiceResultListener webServiceResultListener) {
        super(reqId, webServiceResultListener);
    }
    public void getAllSubServiceCategory(int cat_id){
        String url = WsUrlConstants.getUrl(WsUrlConstants.EP_SUB_SERVICES_CATEGORIES)+cat_id;
        Log.d("Register url: ", url);
        sendGetRequest(url);
    }

    @Override
    protected void parseResponse(Object response) {
        if (response != null) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(response.toString().getBytes())));
            Object result = gson.fromJson(jsonReader, SubCategoryDataModel.class);
            broadcastResponse(result);
        } else
            broadcastResponse(response);
    }
}
