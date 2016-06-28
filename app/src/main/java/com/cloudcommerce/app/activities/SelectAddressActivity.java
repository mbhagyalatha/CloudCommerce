package com.cloudcommerce.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.fragments.LoginFragment;
import com.cloudcommerce.app.fragments.SelectAddressFragment;

public class SelectAddressActivity extends BaseActivity {
    private SelectAddressFragment selectAddressFragment;

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
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.app_name));
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
        finish();
    }
}
