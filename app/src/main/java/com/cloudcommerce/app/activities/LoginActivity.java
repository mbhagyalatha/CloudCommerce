package com.cloudcommerce.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.fragments.LoginFragment;

public class LoginActivity extends BaseActivity {
    LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseToolbar();
        loginFragment = LoginFragment.newInstance();
        //load login fragment
        loadFragment(loginFragment, R.id.login_container, "Login");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(loginFragment != null)
                    //hide keyboard
                    hideKeyBoard(loginFragment.getCurrentFocussedEditText());
                //finish activity
                finish();
        }
        return true;
    }
}
