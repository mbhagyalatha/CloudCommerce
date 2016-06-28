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
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.utils.Utils;

public class GuestLoginFragment extends BaseFragment implements EditText.OnFocusChangeListener, View.OnClickListener {
    private EditText guestUserName, guestEmail, guestPassword, currentSelectedView;
    private Button continueBtn;
    private LinearLayout guestLoginLayout;

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
        View guestLoginView = inflater.inflate(R.layout.fragment_guest_login, container, false);
        initializeViews(guestLoginView);
        setDataToViews();
        return guestLoginView;
    }

    private void initializeViews(View guestLoginView) {
        guestUserName = (EditText) guestLoginView.findViewById(R.id.guest_username);
        guestEmail = (EditText) guestLoginView.findViewById(R.id.guest_email);
        guestPassword = (EditText) guestLoginView.findViewById(R.id.guest_pwd);
        guestUserName.setOnFocusChangeListener(this);
        guestEmail.setOnFocusChangeListener(this);
        guestPassword.setOnFocusChangeListener(this);
        continueBtn = (Button) guestLoginView.findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(this);
        guestLoginLayout = (LinearLayout) guestLoginView.findViewById(R.id.guest_login_layout);
        guestLoginLayout.setOnClickListener(this);
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
                validateGuestLoginData();
                break;
            case R.id.guest_login_layout:
                //hide keyboard if user clicks anywhere on the screen
                hideKeyBoard(v);
                break;
        }
    }

    private void validateGuestLoginData() {
        if (!TextUtils.isEmpty(guestUserName.getText().toString()) && (!TextUtils.isEmpty(guestEmail.getText().toString())) && (!TextUtils.isEmpty(guestPassword.getText().toString()))) {
            //validate email
            if (Utils.isEmailValid(guestEmail.getText().toString())) {
                //send register request
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            } else {
                //show error msg saying to enter valid email
                guestEmail.setError(getResources().getString(R.string.valid_email_error_msg));
            }

        } else {
            if (TextUtils.isEmpty(guestUserName.getText().toString())) {
                guestUserName.setError(getResources().getString(R.string.name_hint_txt));
            }
            if (TextUtils.isEmpty(guestEmail.getText().toString())) {
                guestEmail.setError(getResources().getString(R.string.email_error_msg));
            }
            if (TextUtils.isEmpty(guestPassword.getText().toString())) {
                guestPassword.setError(getResources().getString(R.string.password_error_msg));
            }
        }
    }

    public EditText getCurrentFocussedEditText() {
        return currentSelectedView;
    }
}
