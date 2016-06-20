package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudcommerce.app.R;

public class GuestLoginFragment extends BaseFragment implements EditText.OnFocusChangeListener, View.OnClickListener {
    private EditText guestUserName, guestEmail, guestPassword, currentSelectedView;
    private Button continueBtn;

    public GuestLoginFragment() {
        // Required empty public constructor
    }

    public static GuestLoginFragment newInstance() {
        GuestLoginFragment fragment = new GuestLoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View registerView = inflater.inflate(R.layout.fragment_guest_login, container, false);
        return registerView;
    }

    private void initializeViews(View registerView) {
        guestUserName = (EditText) registerView.findViewById(R.id.guest_username);
        guestEmail = (EditText) registerView.findViewById(R.id.guest_email);
        guestPassword = (EditText) registerView.findViewById(R.id.guest_pwd);
        guestUserName.setOnFocusChangeListener(this);
        guestEmail.setOnFocusChangeListener(this);
        guestPassword.setOnFocusChangeListener(this);
        continueBtn = (Button) registerView.findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(this);
    }

    private void setDataToViews() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        currentSelectedView = (EditText) v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_btn:
                break;
        }
    }
}
