package com.cloudcommerce.app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.USERADDRESSOBJ;
import com.cloudcommerce.app.datamodels.UserAddresses;
import com.cloudcommerce.app.fragments.LoginFragment;
import com.cloudcommerce.app.fragments.SelectAddressFragment;
import com.cloudcommerce.app.interfaces.AddressListInterface;
import com.cloudcommerce.app.network.AddressListService;
import com.cloudcommerce.app.network.AddressService;
import com.cloudcommerce.app.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SelectAddressActivity extends BaseActivity implements AddressListInterface {
    private SelectAddressFragment selectAddressFragment;
    private AddressListServiceResultReceiver addressServiceResultReceiver;
    ArrayList<UserAddresses> userAddressesArrayList = new ArrayList<UserAddresses>();
    TextView tv_no_addr_for_user;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getAddressList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        initialiseToolbar();
        selectAddressFragment = SelectAddressFragment.newInstance();
        //load  fragment
        loadFragment(selectAddressFragment, R.id.select_address_container, "Select Address");
    }

    private void initialiseToolbar() {
        tv_no_addr_for_user = (TextView)findViewById(R.id.txt_no_addr_for_user);
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.select_addr_title));
        mTitle.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_addr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish activity
                finish();
                break;
            case R.id.add_address:
                //navigate user to add address screen
                launchAddAddressScreen();
                break;
        }
        return true;
    }

    private void launchAddAddressScreen() {
        Intent intent = new Intent(this, AddAddressActivity.class);
        startActivity(intent);
        //finish();
    }

    @Override
    public void addressList() {
        getAddressList();
    }

    private void getAddressList() {
        IntentFilter intentFilter = new IntentFilter(AppConstants.GET_ADDRESS_LIST);
        addressServiceResultReceiver = new AddressListServiceResultReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(addressServiceResultReceiver, intentFilter);
        //Show progress dialog -- TODO
        showProgressDialog();
        AddressListService addressListService = new AddressListService(getBaseContext());
        addressListService.getAddressList();
    }

    public class AddressListServiceResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterAddressServiceResultReceiver();
            if (intent.getBooleanExtra(AppConstants.SUCCESS_TEXT, false)) {
                System.out.println("Stats receiver");
                //save login details
                try {
                    JSONObject jsonObject = new JSONObject(CloudCommerceSessionData.getSessionDataInstance().getAddressListResponse());
                    if (jsonObject.getBoolean(AppConstants.SUCCESS)) {
                        Type dataList = new TypeToken<List<UserAddresses>>() {
                        }.getType();
                        JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.USER_ADDRESSES);
                        userAddressesArrayList = new Gson().fromJson(jsonArray.toString(), dataList);
                        CloudCommerceSessionData.getSessionDataInstance().setUserAddressesList(userAddressesArrayList);
                        selectAddressFragment.updateAddressList(userAddressesArrayList);
                        tv_no_addr_for_user.setVisibility(View.GONE);
                    } else {
                        JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.ERRORS);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            jsonObject1.getInt(AppConstants.CODE);
                            tv_no_addr_for_user.setVisibility(View.VISIBLE);
                            //showErrorDialog(jsonObject1.getString(AppConstants.MESSAGE), AppConstants.ERROR_DIALOG_TITLE);
                        }
                    }
                } catch (Exception e) {
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

    private void unregisterAddressServiceResultReceiver() {
        if (addressServiceResultReceiver != null) {
            LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(addressServiceResultReceiver);
            addressServiceResultReceiver = null;
        }
    }


}
