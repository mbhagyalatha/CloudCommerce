package com.cloudcommerce.app.network;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.UserDataModel;
import com.cloudcommerce.app.network.webservices.AuthenticationWsImpl;
import com.cloudcommerce.app.network.webservices.BaseWsImpl;
import com.cloudcommerce.app.utils.AppConstants;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhagya on 06/25/2016.
 */
public class AuthenticationService extends BaseCloudCommerceService implements WebServiceResultListener {

    private Intent broadcastIntent = new Intent(AppConstants.LOGIN_SERVICE);


    public AuthenticationService(Context context) {
        super(context);
    }


    public void sendRegisterService() {
        AuthenticationWsImpl authenticationWs = new AuthenticationWsImpl(AppConstants.REGISTER_REQUEST_ID, this);
        authenticationWs.sendRegisterRequest();
    }

    public void sendLoginService() {
        AuthenticationWsImpl authenticationWs = new AuthenticationWsImpl(AppConstants.LOGIN_REQUEST_ID, this);
        authenticationWs.sendRegisterRequest();
    }

    public void sendGuestLoginService() {
        AuthenticationWsImpl authenticationWs = new AuthenticationWsImpl(AppConstants.GUEST_LOGIN_REQUEST_ID, this);
        authenticationWs.sendRegisterRequest();
    }


    @Override
    public void onServiceCompleted(Object response, Object error, int reqId) {
        if (error == null) {
            switch (reqId) {

                case AppConstants.REGISTER_REQUEST_ID:
                    //broad cast message
                    broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
                    //save auth token in session data
                    LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
                    break;
                case AppConstants.LOGIN_REQUEST_ID:
                    parseLoginResponse(response.toString());
                    break;
                case AppConstants.GUEST_LOGIN_REQUEST_ID:
                    broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
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
                if (reqId == AppConstants.LOGIN_REQUEST_ID || BaseWsImpl.statusCode == 401)
                    sendError(AppConstants.INVALID_GRANT);
                else if (errorMsg.equals(context.getResources().getString(R.string.no_internet_access))) {
                    sendError(context.getResources().getString(R.string.no_internet_access));
                } else {
                    sendError(netError);
                }
            }
        }
    }

    private void parseAuthentication(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            String access_token = jsonObject.getString("access_token");
            String token_type = jsonObject.getString("token_type");
            Long expires_in = jsonObject.getLong("expires_in");


            Log.d("access", access_token + " " + token_type + " " + expires_in);
            //save auth token in session data  --TODO

        } catch (Exception e) {
            e.printStackTrace();
        }
        sendResponse(response);
    }

    private void parseLoginResponse(Object response) {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(response.toString().getBytes())));

        try {
            JSONObject obj = new JSONObject(response.toString());
            //save user data in session data --TODO (user details, addres)

        } catch (Exception e) {
            e.printStackTrace();
        }

        sendResponse(response);
    }


    private void sendError(String response) {
        broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, false);
        broadcastIntent.putExtra(AppConstants.ERROR_TEXT, response);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }

    private void sendResponse(Object response) {
        broadcastIntent.putExtra(AppConstants.SUCCESS_TEXT, true);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }
}
