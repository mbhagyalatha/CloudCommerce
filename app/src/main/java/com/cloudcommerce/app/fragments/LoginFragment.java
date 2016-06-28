package com.cloudcommerce.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.activities.ForgotpasswordActivity;
import com.cloudcommerce.app.activities.RegisterActivity;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.UserDataModel;
import com.cloudcommerce.app.utils.AppConstants;
import com.cloudcommerce.app.utils.Utils;

public class LoginFragment extends BaseFragment implements EditText.OnFocusChangeListener, View.OnClickListener {
    private EditText loginEmail, loginPassword, currentSelectedView;
    private Button loginBtn, registerNowBtn;
    private TextView forgotPwd, loginAsGuest;
    private LinearLayout loginLyt;
    private static String fromScreen;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = fragment.getArguments();
        if (bundle != null && bundle.containsKey(AppConstants.LOGIN_FROM_SCREEN)) {
            fromScreen = bundle.getString(AppConstants.LOGIN_FROM_SCREEN);
        }
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
        loginLyt = (LinearLayout) loginView.findViewById(R.id.login_layout);
        loginBtn.setOnClickListener(this);
        registerNowBtn.setOnClickListener(this);
        forgotPwd.setOnClickListener(this);
        loginAsGuest.setOnClickListener(this);
        loginLyt.setOnClickListener(this);
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
                validateGuestLoginData();
                break;
            case R.id.register_btn:
                loadRegisterScreen();
                break;
            case R.id.forgot_pwd:
                loadForgotPasswordScreen();
                break;
            case R.id.login_as_guest_txt:
                loadGuestLoginScreen();
                break;
            case R.id.login_layout:
                //hide keyboard if user clicks anywhere on the screen
                hideKeyBoard(v);
                break;
        }
    }

    private void loadRegisterScreen() {
        Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void loadForgotPasswordScreen() {
        Intent forgotPasswordIntent = new Intent(getActivity(), ForgotpasswordActivity.class);
        startActivity(forgotPasswordIntent);
    }

    public EditText getCurrentFocussedEditText() {
        return currentSelectedView;
    }

    private void validateGuestLoginData() {
        if ((!TextUtils.isEmpty(loginEmail.getText().toString())) && (!TextUtils.isEmpty(loginPassword.getText().toString()))) {
            //validate email
            if (Utils.isEmailValid(loginEmail.getText().toString())) {
                //send login request and in its suceess save logged in user data in shared preferences
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                UserDataModel user = new UserDataModel();
                user.setEmail(loginEmail.getText().toString());
                CloudCommerceSessionData.getSessionDataInstance().setUserJsonData(user);
                //close keyboard
                hideKeyBoard(currentSelectedView);
                sendResultBack(fromScreen);
            } else {
                //show error msg saying to enter valid email
                loginEmail.setError(getResources().getString(R.string.valid_email_error_msg));
            }

        } else {
            if (TextUtils.isEmpty(loginEmail.getText().toString())) {
                loginEmail.setError(getResources().getString(R.string.email_error_msg));
            }
            if (TextUtils.isEmpty(loginPassword.getText().toString())) {
                loginPassword.setError(getResources().getString(R.string.password_error_msg));
            }
        }
    }

    private void sendResultBack(String fromScreen) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.LOGIN_FROM_SCREEN, fromScreen);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }


}
