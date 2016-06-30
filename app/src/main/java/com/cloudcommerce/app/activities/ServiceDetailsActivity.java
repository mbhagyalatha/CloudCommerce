package com.cloudcommerce.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.SubCategoryListDataModel;
import com.cloudcommerce.app.fragments.ServiceDetailsFragment;
import com.cloudcommerce.app.utils.AppConstants;

public class ServiceDetailsActivity extends BaseActivity {
    private ServiceDetailsFragment descriptionFragment;
    private SubCategoryListDataModel selectedService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        //get service data
        Intent intent = getIntent();
        if (intent.hasExtra(AppConstants.SELECTED_SERVICE)) {
            selectedService = (SubCategoryListDataModel) intent.getSerializableExtra(AppConstants.SELECTED_SERVICE);
        }
        initialiseToolbar();
        //load fragment
        descriptionFragment = ServiceDetailsFragment.newInstance();
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
        mTitle.setText(selectedService.getSub_cat_name());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", " onActivityResult called");
    }
}
