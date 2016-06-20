package com.cloudcommerce.app.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloudcommerce.app.activities.GuestLoginActivity;
import com.cloudcommerce.app.activities.RegisterActivity;

/**
 * Created by Santosh on 11/6/2015.
 */
public class BaseFragment extends Fragment {

    protected String TAG = BaseFragment.class.getName();
    protected LayoutInflater inflater;
    public ProgressBar pgrBar;
    private Dialog errorDialog;
    InputMethodManager imm;
    protected Uri fileUri; // file url to store image
    protected TextView noDataSearchIcon, noDataMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getActivity().getBaseContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void hideKeyBoard(View view) {

        Log.v("hide keyboard", "hide keyboard");
        if (getActivity() != null && view != null) {
            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            Log.v("hide keyboard", "hide keyboard");
            if (view instanceof AutoCompleteTextView || view instanceof EditText)
                view.setFocusable(true);
        }

    }

    public void showKeyBoard(View view) {
        Log.v("show keyboard", "show keyboard");
        if (getActivity() != null && view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            if (view instanceof EditText)
                view.setFocusable(true);
        }
    }

    public SpannableString getUnderLinedText(String textToUnderline) {
        SpannableString spanString = new SpannableString(textToUnderline);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        return spanString;
    }
    public void loadGuestLoginScreen(){
        Intent guestLoginIntent = new Intent(getActivity(), GuestLoginActivity.class);
        startActivity(guestLoginIntent);
    }
}
