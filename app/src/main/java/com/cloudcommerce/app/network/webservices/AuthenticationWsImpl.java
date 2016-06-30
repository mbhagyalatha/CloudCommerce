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
import com.cloudcommerce.app.network.WebServiceResultListener;
import com.cloudcommerce.app.utils.AppConstants;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhagya on 06/25/2016.
 */
public class AuthenticationWsImpl extends BaseWsImpl {

    public AuthenticationWsImpl(int reqId, WebServiceResultListener webServiceResultListener) {
        super(reqId, webServiceResultListener);
    }


    public void sendRegisterRequest(String first_name,String last_name,String email) {
        String url = WsUrlConstants.getUrl(WsUrlConstants.EP_REGISTER);
        url=url.replace(WsUrlConstants.REG_FIRST_NAME,first_name);
        url=url.replace(WsUrlConstants.REG_LAST_NAME,last_name);
        url=url.replace(WsUrlConstants.REG_EMAIL,email);
        Log.d("Register url: ", url);
        sendPostRequest(url);
    }

    public void loginRequest(String email,String password) {
        String url = WsUrlConstants.getUrl(WsUrlConstants.EP_LOGIN)+email;
        Log.d("Login url: ", url);
        sendPostRequest(url);
    }

    public void sendGuestLoginRequest() {
        String url = WsUrlConstants.getUrl(WsUrlConstants.EP_GUEST_LOGIN);
        Log.d("Guest Login url: ", url);
        sendPostRequest(url);
    }

    /* public void sendPostRequest(String endPointUrl) {
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

     public void sendGetRequest(String endPointUrl) {
         String url = WsUrlConstants.getUrl(endPointUrl);
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
 */
    @Override
    protected void parseResponse(Object response) {
        broadcastResponse(response);
    }
}
