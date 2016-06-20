package com.cloudcommerce.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.fragments.FragmentDrawer;


public class BaseNavigationActivity extends BaseActivity
        implements FragmentDrawer.FragmentDrawerListener {

    //Defining Variables
    Fragment fragment = null;
    protected FrameLayout containerlayout, adminRootlayout;
    protected FragmentDrawer.FragmentDrawerListener drawerListener;
    private boolean show;
    private static final String TAG = "BaseNavigationActivity";
    private String previouslySelectedMenuItemTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_navigation);

        // Initializing Toolbar and setting it as the actionbar
        toolBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        searchImage = (ImageView) toolBarLayout.findViewById(R.id.action_serach);
        searchImage.setVisibility(View.VISIBLE);
        mTitle = (TextView) toolBarLayout.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.app_name));
        mTitle.setTextColor(getResources().getColor(R.color.white));
        customToolBar = (Toolbar) findViewById(R.id.toolbar_custom);
        customToolBar.setVisibility(View.GONE);
        customToolBar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_color));
        setSupportActionBar(customToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        FragmentDrawer drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        containerlayout = (FrameLayout) findViewById(R.id.container_body);
        drawerListener = this;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        String title = "";
        if (position != FragmentDrawer.getData().size()) {
            title = ((TextView) view.findViewById(R.id.nav_item_txt)).getText().toString();
            Log.d("title", title);
        }
        view.setSelected(true);
        displayView(position, title);
    }

    protected void displayView(int position, String title) {
        Log.d(TAG, " selected menu title " + CloudCommerceSessionData.getSessionDataInstance().getSelectedMenuTitle() + " : " + title);
        if ((CloudCommerceSessionData.getSessionDataInstance().getSelectedMenuTitle() == null) || (!CloudCommerceSessionData.getSessionDataInstance().getSelectedMenuTitle().equals(title))) {
            //previouslySelectedMenuItemTitle = title;
            CloudCommerceSessionData.getSessionDataInstance().setSelectedMenuTitle(title);
            Fragment fragment = null;
            // set the toolbar title
            mTitle.setText(title);
            if (title.equals(getResources().getString(R.string.menu_services))) {
                launchServicesActivity();
            } else if (title.equals(getResources().getString(R.string.menu_cart))) {
                launchCartActivity();
            } else if (title.equals(getResources().getString(R.string.menu_about))) {
                launchAboutActivity();
            } else if (title.equals(getResources().getString(R.string.menu_help))) {
                launchHelpActivity();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem serachItem = menu.findItem(R.id.action_search);/**/
        serachItem.setVisible(false);
        return true;
    }


    protected void setnavigationSelectionLayout(String title) {
        containerlayout.setVisibility(View.VISIBLE);
    }

    public void addCustomViewToToolBar(View view) {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mTitle.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        showOrHideOptionItems(false);
        customToolBar.setVisibility(View.VISIBLE);
        customToolBar.removeAllViews();
        customToolBar.addView(view);
    }

    public void removeCustomView(View view) {
        mTitle.setVisibility(View.VISIBLE);
        if (view != null) {
            customToolBar.setVisibility(View.GONE);
            customToolBar.removeView(view);
        }
        showOrHideOptionItems(true);
        toolbar.setVisibility(View.VISIBLE);
    }

    protected void showOrHideOptionItems(boolean show) {
        this.show = show;
        invalidateOptionsMenu();
    }

    private void clearAllUserData() {
        /*Intent in = new Intent(this, LoginACtivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();*/
    }

    private void launchServicesActivity() {

    }

    private void launchCartActivity() {

    }

    private void launchAboutActivity() {

    }

    private void launchHelpActivity() {

    }

}
