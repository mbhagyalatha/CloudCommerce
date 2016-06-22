package com.cloudcommerce.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.fragments.SubServicesFragment;
import com.cloudcommerce.app.utils.AppConstants;

public class SubServicesActivity extends BaseActivity {
    SubServicesFragment subServicesFragment;
    private String serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_services);
        Intent intent = getIntent();
        serviceName = intent.getStringExtra(AppConstants.SERVICENAME);
        initialiseToolbar();
        //load fragment
        subServicesFragment = SubServicesFragment.newInstance();
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
}
