package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.cloudcommerce.app.activities.CartSummaryActivity;
import com.cloudcommerce.app.utils.Utils;


public class AddAddressFragment extends BaseFragment implements View.OnClickListener, EditText.OnFocusChangeListener {
    private EditText name, address, pincode, city, state, phoneNo, currentSelectedView;
    private Button continueBtn;
    private LinearLayout addAddressLyt;

    public AddAddressFragment() {
        // Required empty public constructor
    }

    public static AddAddressFragment newInstance() {
        AddAddressFragment fragment = new AddAddressFragment();
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
        View addAddressView = inflater.inflate(R.layout.fragment_add_address, container, false);
        initializeControls(addAddressView);
        setDataToControls();
        return addAddressView;
    }

    private void initializeControls(View addAddressView) {
        name = (EditText) addAddressView.findViewById(R.id.name);
        address = (EditText) addAddressView.findViewById(R.id.address);
        pincode = (EditText) addAddressView.findViewById(R.id.pincode);
        city = (EditText) addAddressView.findViewById(R.id.city);
        state = (EditText) addAddressView.findViewById(R.id.state);
        phoneNo = (EditText) addAddressView.findViewById(R.id.phone_no);
        continueBtn = (Button) addAddressView.findViewById(R.id.continue_btn);
        addAddressLyt = (LinearLayout) addAddressView.findViewById(R.id.add_address_layout);
        //add focus changed listener
        name.setOnFocusChangeListener(this);
        address.setOnFocusChangeListener(this);
        pincode.setOnFocusChangeListener(this);
        city.setOnFocusChangeListener(this);
        state.setOnFocusChangeListener(this);
        phoneNo.setOnFocusChangeListener(this);
        //add click listener
        continueBtn.setOnClickListener(this);
        addAddressLyt.setOnClickListener(this);
    }

    private void setDataToControls() {

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
                validateAddAddressData();
                break;
            case R.id.add_address_layout:
                //hide keyboard if user clicks anywhere on the screen
                hideKeyBoard(v);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        currentSelectedView = (EditText) v;
    }

    private void validateAddAddressData() {
        if (!TextUtils.isEmpty(name.getText().toString()) && (!TextUtils.isEmpty(address.getText().toString())) && (!TextUtils.isEmpty(pincode.getText().toString())) && (!TextUtils.isEmpty(city.getText().toString())) && (!TextUtils.isEmpty(state.getText().toString())) && (!TextUtils.isEmpty(phoneNo.getText().toString()))) {

            if ((phoneNo.getText().toString().trim().length() >= 10) && (pincode.getText().toString().trim().length() >= 6 && pincode.getText().toString().trim().length() <= 9)) {
                //send addaddress request
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                //send add address service
                //launch cart screen
                launchcartSummaryScreen();
            } else {
                if (phoneNo.getText().toString().trim().length() < 10)
                    phoneNo.setError(getResources().getString(R.string.valid_phone_number_msg));
                if (pincode.getText().toString().trim().length() < 6)
                    pincode.setError(getResources().getString(R.string.valid_pincode_msg));
            }
        } else {
            if (TextUtils.isEmpty(name.getText().toString())) {
                name.setError(getResources().getString(R.string.name_error_msg));
            }
            if (TextUtils.isEmpty(address.getText().toString())) {
                address.setError(getResources().getString(R.string.address_error_msg));
            }
            if (TextUtils.isEmpty(pincode.getText().toString())) {
                pincode.setError(getResources().getString(R.string.pincode_error_msg));
            }
            if (TextUtils.isEmpty(city.getText().toString())) {
                city.setError(getResources().getString(R.string.city_error_msg));
            }
            if (TextUtils.isEmpty(state.getText().toString())) {
                state.setError(getResources().getString(R.string.state_error_msg));
            }
            if (TextUtils.isEmpty(phoneNo.getText().toString())) {
                phoneNo.setError(getResources().getString(R.string.phone_no_error_msg));
            }
        }
    }

    public EditText getCurrentFocussedEditText() {
        return currentSelectedView;
    }

    private void launchcartSummaryScreen() {
        Intent intent = new Intent(getActivity(), CartSummaryActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
