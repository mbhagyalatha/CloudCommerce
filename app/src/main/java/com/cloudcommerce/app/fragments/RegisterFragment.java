package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudcommerce.app.R;

public class RegisterFragment extends BaseFragment implements EditText.OnFocusChangeListener, View.OnClickListener {
    private EditText username, registerEmail, registerPassword, currentSelectedView;
    private Button loginBtn, registerBtn;
    private TextView loginAsGuest;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        View registerView = inflater.inflate(R.layout.fragment_register, container, false);
        initializeViews(registerView);
        setDataToViews();
        return registerView;
    }

    private void initializeViews(View registerView) {
        username = (EditText) registerView.findViewById(R.id.username);
        registerEmail = (EditText) registerView.findViewById(R.id.register_email);
        registerPassword = (EditText) registerView.findViewById(R.id.register_pwd);
        username.setOnFocusChangeListener(this);
        registerEmail.setOnFocusChangeListener(this);
        registerPassword.setOnFocusChangeListener(this);
        loginBtn = (Button) registerView.findViewById(R.id.login_btn);
        registerBtn = (Button) registerView.findViewById(R.id.register_btn);
        loginAsGuest = (TextView) registerView.findViewById(R.id.login_as_guest_txt);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        loginAsGuest.setOnClickListener(this);
    }

    private void setDataToViews() {
        loginAsGuest.setText(getUnderLinedText(getResources().getString(R.string.login_as_guest_txt)));
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //finish the activity and go back to login screen
                getActivity().finish();
                break;
            case R.id.register_btn:
                break;
            case R.id.login_as_guest_txt:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        currentSelectedView = (EditText) v;
    }
}
