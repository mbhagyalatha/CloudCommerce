package com.cloudcommerce.app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.fragments.HomeFragment;
import com.cloudcommerce.app.interfaces.HomeInterface;
import com.cloudcommerce.app.network.ServiceCategoriesService;
import com.cloudcommerce.app.utils.AppConstants;

public class HomeActivity extends BaseNavigationActivity implements HomeInterface {
    private static final String TAG = "HomeActivity";
    HomeFragment homeFragment;
    private String menuTitle;
    private AllServiceCategoriesResultReceiver allServiceCategoriesResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuTitle = getResources().getString(R.string.menu_services);
        mTitle.setText(menuTitle);
        CloudCommerceSessionData.getSessionDataInstance().setSelectedMenuTitle(menuTitle);
        //load home fragment
        homeFragment = HomeFragment.newInstance();
        loadFragment(homeFragment, R.id.container_body, "Home");
    }

    @Override
    public void getServiceCategoriesList() {
        registerGetAllServiceCategories();
    }

    public void registerGetAllServiceCategories() {
        IntentFilter intentFilter = new IntentFilter(AppConstants.GET_ALL_SERVICE_CATEGORIES);
        allServiceCategoriesResultReceiver = new AllServiceCategoriesResultReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(allServiceCategoriesResultReceiver, intentFilter);
        //Show progress dialog -- TODO
        showProgressDialog();
        ServiceCategoriesService adminService = new ServiceCategoriesService(getBaseContext());
        adminService.getAllServiceCategories();
    }

    public class AllServiceCategoriesResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            unRegisterAllServiceCategoriesServiceResultReceiver();
            if (intent.getBooleanExtra(AppConstants.SUCCESS_TEXT, false)) {
                System.out.println("Stats receiver");
                //update list
               /* if (homeFragment != null) {
                    homeFragment.updateServiceCategories(allServiceCategoriesList);
                }*/
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

    private void unRegisterAllServiceCategoriesServiceResultReceiver() {
        if (allServiceCategoriesResultReceiver != null) {
            LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(allServiceCategoriesResultReceiver);
            allServiceCategoriesResultReceiver = null;
        }
    }
}
