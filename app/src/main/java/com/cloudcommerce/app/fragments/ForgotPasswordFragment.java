package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.utils.Utils;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener, EditText.OnFocusChangeListener {
    private EditText email, currentSelectedView;
    private Button continueBtn;
    private LinearLayout forgotPwdLyt;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        View forgotPwdView = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        initializeViews(forgotPwdView);
        setDataToViews();
        return forgotPwdView;
    }

    private void initializeViews(View forgotPwdView) {
        email = (EditText) forgotPwdView.findViewById(R.id.guest_email);
        email.setOnFocusChangeListener(this);
        continueBtn = (Button) forgotPwdView.findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(this);
        forgotPwdLyt = (LinearLayout) forgotPwdView.findViewById(R.id.forgot_pwd_layout);
        forgotPwdLyt.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_btn:
                validateForgotPwdData();
                break;
            case R.id.forgot_pwd_layout:
                //hide keyboard if user clicks anywhere on the screen
                hideKeyBoard(v);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        currentSelectedView = (EditText) v;
    }

    public EditText getCurrentFocussedEditText() {
        return currentSelectedView;
    }

    private void validateForgotPwdData() {
        if (!TextUtils.isEmpty(email.getText().toString())) {
            //validate email
            if (Utils.isEmailValid(email.getText().toString())) {
                //send register request
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            } else {
                //show error msg saying to enter valid email
                email.setError(getResources().getString(R.string.valid_email_error_msg));
            }

        } else {
            email.setError(getResources().getString(R.string.email_error_msg));
        }
    }
}
