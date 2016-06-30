package com.cloudcommerce.app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CategoryDataModel;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.SubCategoryDataModel;
import com.cloudcommerce.app.fragments.ServicesListFragment;
import com.cloudcommerce.app.interfaces.SubCategoryInterface;
import com.cloudcommerce.app.network.ServiceCategoriesService;
import com.cloudcommerce.app.network.SubCategoryService;
import com.cloudcommerce.app.utils.AppConstants;
import com.cloudcommerce.app.utils.ConnectionDetector;

public class ServicesListActivity extends BaseActivity implements SubCategoryInterface{
    ServicesListFragment subServicesFragment;
    private String serviceName;
    private int cat_id;
    ConnectionDetector connectionDetector;
    private AllSubServiceCategoriesResultReceiver allSubServiceCategoriesResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);
        Intent intent = getIntent();
        serviceName = intent.getStringExtra(AppConstants.SERVICENAME);
        cat_id = intent.getIntExtra(AppConstants.CAT_ID,0);
        initialiseToolbar();
        connectionDetector = new ConnectionDetector(this);
        //load fragment
        subServicesFragment = ServicesListFragment.newInstance();
        loadFragment(subServicesFragment, R.id.sub_service_container, "SubServices");
    }

    private void initialiseToolbar() {
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
        mTitle.setText(serviceName);
        mTitle.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish activity
                finish();
        }
        return true;
    }

    @Override
    public void getSubCategoryList() {
        if(connectionDetector.isConnectingToInternet()){
            registerGetAllSubServiceCategories();
        }else{
            Toast.makeText(this, getResources().getString(R.string.no_internet_access), Toast.LENGTH_SHORT).show();
        }
    }

    public void registerGetAllSubServiceCategories() {
        IntentFilter intentFilter = new IntentFilter(AppConstants.GET_ALL_SUB_SERVICE_CATEGORIES);
        allSubServiceCategoriesResultReceiver = new AllSubServiceCategoriesResultReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(allSubServiceCategoriesResultReceiver, intentFilter);
        //Show progress dialog -- TODO
        showProgressDialog();
        SubCategoryService subCategoryService = new SubCategoryService(getBaseContext());
        subCategoryService.getAllSubServiceCategories(cat_id);
    }

    public class AllSubServiceCategoriesResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            unRegisterAllServiceCategoriesServiceResultReceiver();
            if (intent.getBooleanExtra(AppConstants.SUCCESS_TEXT, false)) {
                System.out.println("Stats receiver");
                SubCategoryDataModel subCategoryDataModel = CloudCommerceSessionData.getSubCategoryDataModel();
                //update list
                if (subServicesFragment != null) {
                    subServicesFragment.updateSubServiceCategories(subCategoryDataModel);
                }
            } else {
                //Log.v(TAG, "ResultReceiver redemptions");
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
        if (allSubServiceCategoriesResultReceiver != null) {
            LocalBroadcastManager.getInstance(getBaseContext()).unregisterReceiver(allSubServiceCategoriesResultReceiver);
            allSubServiceCategoriesResultReceiver = null;
        }
    }
}
