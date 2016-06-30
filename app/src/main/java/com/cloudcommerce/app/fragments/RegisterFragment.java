package com.cloudcommerce.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.interfaces.HomeInterface;
import com.cloudcommerce.app.interfaces.RegisterInterface;
import com.cloudcommerce.app.utils.Utils;

public class RegisterFragment extends BaseFragment implements EditText.OnFocusChangeListener, View.OnClickListener {
    private EditText firstName, registerEmail, lastname, currentSelectedView;
    private Button loginBtn, registerBtn;
    private TextView loginAsGuest;
    private LinearLayout registerLayout;
    private RegisterInterface registerInterface;

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
        firstName = (EditText) registerView.findViewById(R.id.firstname);
        registerEmail = (EditText) registerView.findViewById(R.id.register_email);
        lastname = (EditText) registerView.findViewById(R.id.lastname);
        firstName.setOnFocusChangeListener(this);
        registerEmail.setOnFocusChangeListener(this);
        lastname.setOnFocusChangeListener(this);
        loginBtn = (Button) registerView.findViewById(R.id.login_btn);
        registerBtn = (Button) registerView.findViewById(R.id.register_btn);
        loginAsGuest = (TextView) registerView.findViewById(R.id.login_as_guest_txt);
        registerLayout = (LinearLayout)registerView.findViewById(R.id.register_layout);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        loginAsGuest.setOnClickListener(this);
        registerLayout.setOnClickListener(this);
    }

    private void setDataToViews() {
        loginAsGuest.setText(getUnderLinedText(getResources().getString(R.string.login_as_guest_txt)));
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            registerInterface = (RegisterInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        registerInterface=null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //finish the activity and go back to login screen
                getActivity().finish();
                break;
            case R.id.register_btn:
                validateRegisterData();
                break;
            case R.id.login_as_guest_txt:
                loadGuestLoginScreen();
                break;
            case R.id.register_layout:
                //hide keyboard if user clicks anywhere on the screen
                hideKeyBoard(v);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        currentSelectedView = (EditText) v;
    }

    private void validateRegisterData() {
        if (!TextUtils.isEmpty(firstName.getText().toString()) && (!TextUtils.isEmpty(registerEmail.getText().toString())) && (!TextUtils.isEmpty(lastname.getText().toString()))) {
            //validate email
            if (Utils.isEmailValid(registerEmail.getText().toString())) {
                //send register request
                sendRegistrationRequest();
                //Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            } else {
                //show error msg saying to enter valid email
                registerEmail.setError(getResources().getString(R.string.valid_email_error_msg));
            }

        } else {
            if (TextUtils.isEmpty(firstName.getText().toString())) {
                firstName.setError(getResources().getString(R.string.first_name_error_msg));
            }
            if (TextUtils.isEmpty(lastname.getText().toString())) {
                lastname.setError(getResources().getString(R.string.last_name_error_msg));
            }
            if (TextUtils.isEmpty(registerEmail.getText().toString())) {
                registerEmail.setError(getResources().getString(R.string.email_error_msg));
            }
        }
    }

    private void sendRegistrationRequest() {
        registerInterface.sendRegistrationRequest(firstName.getText().toString(),lastname.getText().toString(),registerEmail.getText().toString());
    }

    public EditText getCurrentFocussedEditText() {
        return currentSelectedView;
    }
}
