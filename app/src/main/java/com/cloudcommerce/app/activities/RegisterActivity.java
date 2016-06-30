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
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.fragments.LoginFragment;
import com.cloudcommerce.app.fragments.RegisterFragment;
import com.cloudcommerce.app.interfaces.RegisterInterface;
import com.cloudcommerce.app.network.AuthenticationService;
import com.cloudcommerce.app.network.ServiceCategoriesService;
import com.cloudcommerce.app.utils.AppConstants;
import com.cloudcommerce.app.utils.ConnectionDetector;

public class RegisterActivity extends BaseActivity implements RegisterInterface{
    private static final String TAG = "RegisterActivity";
    RegisterFragment registerFragment;
    private RegisterServiceResultReceiver registerServiceResultReceiver;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialiseToolbar();
        connectionDetector= new ConnectionDetector(this);
        registerFragment = RegisterFragment.newInstance();
        //load register fragment
        loadFragment(registerFragment, R.id.register_container, "Register");
    }

    private void initialiseToolbar() {
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.register_title));
        mTitle.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (registerFragment != null)
                    //hide keyboard
                    hideKeyBoard(registerFragment.getCurrentFocussedEditText());
                //finish activity
                finish();
        }
        return true;
    }

    public void registerRegistrationService(String first_name,String last_name,String email) {
        IntentFilter intentFilter = new IntentFilter(AppConstants.REGISTER_SERVICE);
        registerServiceResultReceiver = new RegisterServiceResultReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(registerServiceResultReceiver, intentFilter);
        //Show progress dialog -- TODO
        showProgressDialog();
        AuthenticationService loginService = new AuthenticationService(getBaseContext());
        loginService.sendRegisterService(first_name,last_name,email);
    }

    @Override
    public void sendRegistrationRequest(String first_name,String last_name,String email) {
        if(connectionDetector.isConnectingToInternet()){
            registerRegistrationService(first_name,last_name,email);
        }else{
            Toast.makeText(this, getResources().getString(R.string.no_internet_access), Toast.LENGTH_SHORT).show();
        }
    }

    public class RegisterServiceResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterRegistrationServiceResultReceiver();
            if (intent.getBooleanExtra(AppConstants.SUCCESS_TEXT, false)) {
                System.out.println("Stats receiver");
                //save login details
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

    private void unregisterRegistrationServiceResultReceiver() {
        if (registerServiceResultReceiver != null) {
            LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(registerServiceResultReceiver);
            registerServiceResultReceiver = null;
        }
    }
}
