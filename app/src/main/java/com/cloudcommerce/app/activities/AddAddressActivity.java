package com.cloudcommerce.app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.Address;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.fragments.AddAddressFragment;
import com.cloudcommerce.app.fragments.LoginFragment;
import com.cloudcommerce.app.interfaces.AddAddressInterface;
import com.cloudcommerce.app.network.AddressService;
import com.cloudcommerce.app.network.AuthenticationService;
import com.cloudcommerce.app.utils.AppConstants;
import com.cloudcommerce.app.utils.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddAddressActivity extends BaseActivity implements AddAddressInterface{
    private AddAddressFragment addAddressFragment;
    ConnectionDetector connectionDetector;
    AddressServiceResultReceiver addressServiceResultReceiver;

    @Override
    public void submitAddress(Address address) {
        if(connectionDetector.isConnectingToInternet()){
            submitAddressTServer(address);
        }else{
            Toast.makeText(this, getResources().getString(R.string.no_internet_access), Toast.LENGTH_SHORT).show();
        }
    }

    public void submitAddressTServer(Address address) {
        IntentFilter intentFilter = new IntentFilter(AppConstants.ADD_ADDRESS_SERVICE);
        addressServiceResultReceiver = new AddressServiceResultReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(addressServiceResultReceiver, intentFilter);
        //Show progress dialog -- TODO
        showProgressDialog();
        AddressService addressService = new AddressService(getBaseContext());
        addressService.postAddress(address);
    }

    public class AddressServiceResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterAddressServiceResultReceiver();
            if (intent.getBooleanExtra(AppConstants.SUCCESS_TEXT, false)) {
                System.out.println("Stats receiver");
                //save login details
                try{
                    JSONObject jsonObject = new JSONObject(CloudCommerceSessionData.getSessionDataInstance().getAdd_address_response());
                    if(jsonObject.getBoolean(AppConstants.SUCCESS)){
                        JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.MESSAGES);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            jsonObject1.getInt(AppConstants.CODE);
                            jsonObject1.getString(AppConstants.MESSAGE);
                        }
                        JSONObject jsonObject2=jsonObject.getJSONObject(AppConstants.USER_ADDRESS_ID);
                        CloudCommerceSessionData.getSessionDataInstance().setAddressid(jsonObject2.getString(AppConstants.USERID));
                        Log.d("user id is", "<>" + jsonObject2.getString(AppConstants.USERID));
                        launchCartSummaryScreen();
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

    private void launchCartSummaryScreen() {
        Intent serviceDescIntent = new Intent(this, CartSummaryActivity.class);
        startActivity(serviceDescIntent);
        finish();
    }

    private void unregisterAddressServiceResultReceiver() {
        if (addressServiceResultReceiver != null) {
            LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(addressServiceResultReceiver);
            addressServiceResultReceiver = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initialiseToolbar();
        connectionDetector = new ConnectionDetector(this);
        addAddressFragment = AddAddressFragment.newInstance();
        //load login fragment
        loadFragment(addAddressFragment, R.id.add_address_container, "Add Address");
    }

    private void initialiseToolbar() {
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.add_address_title));
        mTitle.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (addAddressFragment != null)
                    //hide keyboard
                    hideKeyBoard(addAddressFragment.getCurrentFocussedEditText());
                //finish activity
                finish();
        }
        return true;
    }
}
