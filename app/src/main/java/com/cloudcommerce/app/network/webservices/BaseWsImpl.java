package com.cloudcommerce.app.network.webservices;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.network.WebServiceResultListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bhagya on 06/25/2016.
 */
public abstract class BaseWsImpl {

    protected static RequestQueue queue = null;
    protected Gson gson;
    protected WsUrlConstants wsUrlConstants;

    private int reqId;
    private List<WebServiceResultListener> requestListeners;
    public static int statusCode;

    public BaseWsImpl(int reqId, WebServiceResultListener listener) {
        if (queue == null) {
            queue = Volley.newRequestQueue(CloudCommerceApplication.getAppContext());
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        requestListeners = new ArrayList<WebServiceResultListener>();
        this.reqId = reqId;
        this.addWsRequestListner(listener);
        if (wsUrlConstants == null) {
            wsUrlConstants = new WsUrlConstants();
        }
    }

    public int getReqId() {
        return reqId;
    }

    private void addWsRequestListner(WebServiceResultListener listener) {
        if (listener != null) {
            requestListeners.add(listener);
        }
    }

    public List<WebServiceResultListener> getRequestListeners() {
        return requestListeners;
    }

    protected void broadcastError(VolleyError error) {
        for (WebServiceResultListener listener : getRequestListeners()) {
            if (listener != null) {
                try {
                    if (error != null) {
                        if (error.networkResponse != null) {
                            Log.e("error code", error.networkResponse.statusCode + "");
                            if (error.networkResponse.statusCode == 401) {
                                String errorMsg = CloudCommerceApplication.getAppContext().getResources().getString(R.string.net_error);
                                error = new VolleyError(new String(errorMsg));
                                listener.onServiceCompleted(errorMsg, error, getReqId());
                            }
                        } else {

                            if (error.getMessage().contains("No authentication challenges found")) {
                                statusCode = 401;
                            }
                            Log.d("status code", "<>" + error.getMessage());
                            //String errorMsg = YEIApplication.getAppContext().getResources().getString(R.string.net_error);
                            String errorMsg = CloudCommerceApplication.getAppContext().getResources().getString(R.string.no_internet_access);
                            error = new VolleyError(new String(errorMsg));
                            listener.onServiceCompleted(errorMsg, error, getReqId());
                        }
                    } else {
                        String errorMsg = CloudCommerceApplication.getAppContext().getResources().getString(R.string.net_error);
                        error = new VolleyError(new String(errorMsg));
                        listener.onServiceCompleted(errorMsg, error, getReqId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onServiceCompleted(null, error, getReqId());
                }
            }
        }
    }

    protected void broadcastResponse(Object result) {
        for (WebServiceResultListener listener : getRequestListeners()) {
            if (listener != null) {
                listener.onServiceCompleted(result, null, getReqId());
            }
        }
    }

    public String setAccessToken(Context context) {
        return context.getSharedPreferences(CloudCommerceSessionData.CLOUD_COMMERCE_APP, Context.MODE_PRIVATE).getString(CloudCommerceSessionData.SHPREF_KEY_ACCESS_TOKEN, null);
    }

    public Map<String, String> setHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        //headers.put("Authorization",  setAccessToken(CloudCommerceApplication.getAppContext()));
        headers.put("API_KEY", WsUrlConstants.API_KEY);
        Log.d("API_KEY ", WsUrlConstants.API_KEY);
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    public void sendPostRequest(String endPointUrl) {
        String url = WsUrlConstants.getUrl(endPointUrl);
        Log.d("get account url", url);
        JsonObjectRequest reqs = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                broadcastError(error);
            }
        }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return setHeaders();
            }
        };
        reqs.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(reqs);
    }

    public void sendGetRequest(String url) {
        Log.d("final url", url);
        JsonObjectRequest reqs = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // handle response
                        Log.d("user response", "user response" + response + "  " + response.toString());

                        broadcastResponse(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                broadcastError(error);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return setHeaders();
            }
        };
        reqs.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(reqs);

    }


    protected abstract void parseResponse(Object response);


}
