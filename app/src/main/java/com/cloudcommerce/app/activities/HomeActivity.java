package com.cloudcommerce.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.fragments.HomeFragment;

public class HomeActivity extends BaseNavigationActivity {
    HomeFragment homeFragment;
    private String menuTitle;

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
}
