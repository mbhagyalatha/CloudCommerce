package com.cloudcommerce.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.fragments.ForgotPasswordFragment;
import com.cloudcommerce.app.fragments.LoginFragment;

public class ForgotpasswordActivity extends BaseActivity {
    private ForgotPasswordFragment forgotPasswordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        initialiseToolbar();
        forgotPasswordFragment = ForgotPasswordFragment.newInstance();
        //load login fragment
        loadFragment(forgotPasswordFragment, R.id.forgot_pwd_container, "Login");
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
                if(forgotPasswordFragment != null)
                    //hide keyboard
                    hideKeyBoard(forgotPasswordFragment.getCurrentFocussedEditText());
                //finish activity
                finish();
        }
        return true;
    }
}
