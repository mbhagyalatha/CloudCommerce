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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.fragments.LoginFragment;
import com.cloudcommerce.app.network.AuthenticationService;
import com.cloudcommerce.app.network.ServiceCategoriesService;
import com.cloudcommerce.app.utils.AppConstants;

public class LoginActivity extends BaseActivity {
    LoginFragment loginFragment;
    private LoginServiceResultReceiver loginServiceResultReceiver;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseToolbar();
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

    public void registerLoginService(String status, int pageNumber) {
        IntentFilter intentFilter = new IntentFilter(AppConstants.LOGIN_SERVICE);
        loginServiceResultReceiver = new LoginServiceResultReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(loginServiceResultReceiver, intentFilter);
        //Show progress dialog -- TODO
        showProgressDialog();
        AuthenticationService loginService = new AuthenticationService(getBaseContext());
        loginService.sendLoginService();
    }

    public class LoginServiceResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterLoginServiceResultReceiver();
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

    private void unregisterLoginServiceResultReceiver() {
        if (loginServiceResultReceiver != null) {
            LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(loginServiceResultReceiver);
            loginServiceResultReceiver = null;
        }
    }
}
