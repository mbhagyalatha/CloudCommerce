package com.cloudcommerce.app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.UserAddresses;
import com.cloudcommerce.app.fragments.LoginFragment;
import com.cloudcommerce.app.interfaces.LoginInterface;
import com.cloudcommerce.app.network.AuthenticationService;
import com.cloudcommerce.app.utils.AppConstants;
import com.cloudcommerce.app.utils.ConnectionDetector;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements LoginInterface{
    LoginFragment loginFragment;
    private LoginServiceResultReceiver loginServiceResultReceiver;
    private static final String TAG = "LoginActivity";
    ConnectionDetector connectionDetector;
    UserAddresses userAddresses = new UserAddresses();
    ArrayList<UserAddresses> userAddressesList = new ArrayList<UserAddresses>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseToolbar();
        connectionDetector= new ConnectionDetector(this);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.LOGIN_FROM_SCREEN, getIntent().getStringExtra(AppConstants.LOGIN_FROM_SCREEN));
        loginFragment = LoginFragment.newInstance();
        loginFragment.setArguments(bundle);
        //load login fragment
        loadFragment(loginFragment, R.id.login_container, "Login");
    }

    private void initialiseToolbar() {
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.login_title));
        mTitle.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (loginFragment != null)
                    //hide keyboard
                    hideKeyBoard(loginFragment.getCurrentFocussedEditText());
                //finish activity
                finish();
        }
        return true;
    }

    public void registerLoginService(String email, String password) {
        IntentFilter intentFilter = new IntentFilter(AppConstants.LOGIN_SERVICE);
        loginServiceResultReceiver = new LoginServiceResultReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(loginServiceResultReceiver, intentFilter);
        //Show progress dialog -- TODO
        showProgressDialog();
        AuthenticationService loginService = new AuthenticationService(getBaseContext());
        loginService.sendLoginService(email,password);
    }

    @Override
    public void login(String email, String password) {
        if(connectionDetector.isConnectingToInternet()){
            registerLoginService(email,password);
        }else{
            Toast.makeText(this,getResources().getString(R.string.no_internet_access),Toast.LENGTH_SHORT).show();
        }
    }

    public class LoginServiceResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterLoginServiceResultReceiver();
            if (intent.getBooleanExtra(AppConstants.SUCCESS_TEXT, false)) {
                System.out.println("Stats receiver");
                //save login details
                try{
                    JSONObject jsonObject = new JSONObject(CloudCommerceSessionData.getSessionDataInstance().getLoginResponse());
                    if(jsonObject.getBoolean(AppConstants.SUCCESS)){
                        JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.MESSAGES);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            jsonObject1.getInt(AppConstants.CODE);
                            jsonObject1.getString(AppConstants.MESSAGE);
                        }
                        JSONObject jsonObject2=jsonObject.getJSONObject(AppConstants.USERID_OBJ);
                        CloudCommerceSessionData.getSessionDataInstance().setUserid(jsonObject2.getString(AppConstants.USERID));
                        Log.d("user id is", "<>" + jsonObject2.getString(AppConstants.USERID));
                        JSONArray jsonArray1 = jsonObject.getJSONArray(AppConstants.USER_ADDRESSES);
                        for(int i=0;i<jsonArray1.length();i++){
                            userAddresses = new Gson().fromJson(jsonArray1.getJSONObject(i).toString(), UserAddresses.class);
                            userAddressesList.add(userAddresses);
                        }
                        CloudCommerceSessionData.getSessionDataInstance().setUserAddressesList(userAddressesList);
                        launchAddressSelectionScreen();
                    }else{
                        JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.ERRORS);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            jsonObject1.getInt(AppConstants.CODE);
                            showErrorDialog(jsonObject1.getString(AppConstants.MESSAGE),AppConstants.ERROR_DIALOG_TITLE);
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            } else {
                Log.v(TAG, "ResultReceiver redemptions");
                String error = intent.getStringExtra(AppConstants.ERROR_TEXT);
                if (error != null) {
                    if (error.contains(AppConstants.UNKNOWNHOST_TEXT))
                        showErrorDialog(getResources().getString(R.string.no_internet_access), AppConstants.ERROR_DIALOG_TITLE);
                    else
                        showErrorDialog(error, AppConstants.ERROR_DIALOG_TITLE);
                } else
                    showErrorDialog(getResources().getString(R.string.net_error), AppConstants.ERROR_DIALOG_TITLE);
            }
            //Dismiss Progress dialog -- TODO
            cancelProgressDialog();
        }
    }

    private void unregisterLoginServiceResultReceiver() {
        if (loginServiceResultReceiver != null) {
            LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(loginServiceResultReceiver);
            loginServiceResultReceiver = null;
        }
    }
    private void launchAddressSelectionScreen() {
        Intent serviceDescIntent = new Intent(this, SelectAddressActivity.class);
        startActivity(serviceDescIntent);
        finish();
    }
}
