package com.cloudcommerce.app.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
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
import com.cloudcommerce.app.fragments.AddAddressFragment;
import com.cloudcommerce.app.fragments.CartSummaryFragment;
import com.cloudcommerce.app.interfaces.ServiceConfirmationInterface;
import com.cloudcommerce.app.network.AuthenticationService;
import com.cloudcommerce.app.network.ServiceConfirmationService;
import com.cloudcommerce.app.utils.AppConstants;
import com.cloudcommerce.app.utils.ConnectionDetector;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class CartSummaryActivity extends BaseActivity implements ServiceConfirmationInterface {
    private CartSummaryFragment cartSummaryFragment;
    ServiceConfirmationResultReceiver serviceConfirmationResultReceiver;
    ConnectionDetector connectionDetector;
    int code=0;
    String Service_Confirmation_message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_summary);
        initialiseToolbar();
        connectionDetector = new ConnectionDetector(this);
        cartSummaryFragment = CartSummaryFragment.newInstance();
        //load login fragment
        loadFragment(cartSummaryFragment, R.id.add_address_container, "Add Address");
    }

    private void initialiseToolbar() {
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.cart_summary_title));
        mTitle.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (cartSummaryFragment != null)
                    //finish activity
                    finish();
        }
        return true;
    }

    @Override
    public void serviceConfirmation(String userid, String user_address_id, String service_id) {
        if(connectionDetector.isConnectingToInternet()){
            sendServiceConfirmation(userid, user_address_id,service_id);
        }else{
            Toast.makeText(this, getResources().getString(R.string.no_internet_access), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendServiceConfirmation(String user_id, String user_address_id,String service_id) {
        IntentFilter intentFilter = new IntentFilter(AppConstants.SERVICE_CONFIRMATION);
        serviceConfirmationResultReceiver = new ServiceConfirmationResultReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(serviceConfirmationResultReceiver, intentFilter);
        //Show progress dialog -- TODO
        showProgressDialog();
        ServiceConfirmationService serviceConfirmationService = new ServiceConfirmationService(getBaseContext());
        serviceConfirmationService.getConfirmation(user_id, user_address_id, service_id);
    }

    public class ServiceConfirmationResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterLoginServiceResultReceiver();
            if (intent.getBooleanExtra(AppConstants.SUCCESS_TEXT, false)) {
                System.out.println("Stats receiver");
                try{
                    JSONObject jsonObject = new JSONObject(CloudCommerceSessionData.getSessionDataInstance().getServiceConfirmationResponse());
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.SERVICE_CONFIRM);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        code = jsonObject1.getInt(AppConstants.CODE);
                        Service_Confirmation_message = jsonObject1.getString(AppConstants.MESSAGE);
                    }
                    if(code == 118){
                        showErrorDialog(Service_Confirmation_message, AppConstants.SERVICE_CONFIRMATION_TITLE, code);
                    }else{
                        showErrorDialog(Service_Confirmation_message,AppConstants.SERVICE_CONFIRMATION_TITLE,code);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            } else {
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
        if (serviceConfirmationResultReceiver != null) {
            LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(serviceConfirmationResultReceiver);
            serviceConfirmationResultReceiver = null;
        }
    }
}
