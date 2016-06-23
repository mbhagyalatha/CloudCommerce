package com.cloudcommerce.app.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.SubServiceDataModel;
import com.cloudcommerce.app.fragments.SubServiceDescriptionFragment;
import com.cloudcommerce.app.fragments.SubServicesFragment;
import com.cloudcommerce.app.utils.AppConstants;

public class SubServiceDesciptionActivity extends BaseActivity {
    private SubServiceDescriptionFragment descriptionFragment;
    private SubServiceDataModel selectedService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_service_desciption);
        initialiseToolbar();
        //get service data
        Intent intent = getIntent();
        if (intent.hasExtra(AppConstants.SELECTED_SERVICE)) {
            selectedService = (SubServiceDataModel) intent.getSerializableExtra(AppConstants.SELECTED_SERVICE);
        }
        //load fragment
        descriptionFragment = SubServiceDescriptionFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.SELECTED_SERVICE, selectedService);
        descriptionFragment.setArguments(bundle);
        loadFragment(descriptionFragment, R.id.service_desc_container, "SubServices");
    }

    private void initialiseToolbar() {
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
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
