package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.activities.RegisterActivity;

public class LoginFragment extends BaseFragment implements EditText.OnFocusChangeListener, View.OnClickListener {
    private EditText loginEmail, loginPassword, currentSelectedView;
    private Button loginBtn, registerNowBtn;
    private TextView forgotPwd, loginAsGuest;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View loginView = inflater.inflate(R.layout.fragment_login, container, false);
        initializeViews(loginView);
        setDataToViews();
        return loginView;
    }

    private void initializeViews(View loginView) {
        loginEmail = (EditText) loginView.findViewById(R.id.login_email);
        loginPassword = (EditText) loginView.findViewById(R.id.login_pwd);
        loginEmail.setOnFocusChangeListener(this);
        loginPassword.setOnFocusChangeListener(this);
        loginBtn = (Button) loginView.findViewById(R.id.login_btn);
        registerNowBtn = (Button) loginView.findViewById(R.id.register_btn);
        forgotPwd = (TextView) loginView.findViewById(R.id.forgot_pwd);
        loginAsGuest = (TextView) loginView.findViewById(R.id.login_as_guest_txt);
        loginBtn.setOnClickListener(this);
        registerNowBtn.setOnClickListener(this);
        forgotPwd.setOnClickListener(this);
        loginAsGuest.setOnClickListener(this);
    }

    private void setDataToViews() {
        forgotPwd.setText(getUnderLinedText(getResources().getString(R.string.forgot_pwd_txt)));
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
    public void onFocusChange(View v, boolean hasFocus) {
        currentSelectedView = (EditText) v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                break;
            case R.id.register_btn:
                loadRegisterScreen();
                break;
            case R.id.forgot_pwd:
                break;
            case R.id.login_as_guest_txt:
                break;
        }
    }
    private void loadRegisterScreen(){
        Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(registerIntent);
    }
}
