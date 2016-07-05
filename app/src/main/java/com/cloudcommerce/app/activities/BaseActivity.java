package com.cloudcommerce.app.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudcommerce.app.R;

public class BaseActivity extends AppCompatActivity {
    protected FrameLayout toolBarLayout;
    protected Toolbar toolbar, customToolBar;
    protected ImageView searchImage, cancelSearchText;
    protected TextView mTitle;
    public View customToolBarView;
    public EditText searchText;
    private InputMethodManager imm;
    private boolean show;
    protected LayoutInflater inflater;
    private int request_code;

    public String mPersonName;
    public String mImageUrl;
    public String mEmailAddress, fromScreen;
    private static final String TAG = "BaseActivity";
    public ProgressDialog progressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getBaseContext());
        try {
            setColorToStatusBar();
            progressDlg = new ProgressDialog(this, R.style.dialogTheme);
            progressDlg.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDlg.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setColorToStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(getResources().getColor(R.color.statusbar_bg_color));
        }
    }

    protected void loadFragment(Fragment destFragment, int id, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            if (fragmentManager != null) {
                fragmentManager.popBackStack();
                if (fragmentManager.beginTransaction() != null)
                    fragmentManager.beginTransaction().replace(id, destFragment, tag).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (fragmentManager.beginTransaction() != null)
                fragmentManager.beginTransaction().replace(id, destFragment, tag).commit();
        }
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

    public void hideKeyBoard(View view) {

        Log.v("hide keyboard", "hide keyboard");
        if (this != null && view != null) {
            imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            Log.v("hide keyboard", "hide keyboard");
            if (view instanceof AutoCompleteTextView)
                view.setFocusable(true);
        }

    }

    public void showKeyBoard(View view) {
        Log.v("show keyboard", "show keyboard");
        if (this != null && view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            if (view instanceof EditText)
                view.setFocusable(true);
        }
    }

    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Log.d(TAG, "OnResume called");
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Log.d(TAG, "OnStart called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG, "OnStop called");
    }

    protected void showProgressDialog() {
        progressDlg.show();
        progressDlg.setContentView(R.layout.progress_bar);
    }

    public void cancelProgressDialog() {
        if (progressDlg != null) {
            if (progressDlg.isShowing()) {
                progressDlg.dismiss();
            }
        }
    }

    public void showErrorDialog(final String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton(getResources().getString(R.string.ok_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //cancel dialog
                dialog.dismiss();
            }
        });
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }
    public void showErrorDialog(final String message, String title,final int code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton(getResources().getString(R.string.ok_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //cancel dialog
                dialog.dismiss();
                if(code == 118){
                    removeStack();
                }
            }
        });
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    private void removeStack() {
        Intent i = new Intent(BaseActivity.this, HomeActivity.class);
        // set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
