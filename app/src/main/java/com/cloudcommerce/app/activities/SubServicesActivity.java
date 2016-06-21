package com.cloudcommerce.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.fragments.SubServicesFragment;

public class SubServicesActivity extends BaseActivity {
    SubServicesFragment subServicesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_services);
        //load fragment
        subServicesFragment = SubServicesFragment.newInstance();
        loadFragment(subServicesFragment,R.id.sub_service_container,"SubServices");
    }
}
