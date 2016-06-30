package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.interfaces.LoginInterface;
import com.cloudcommerce.app.interfaces.ServiceConfirmationInterface;

public class CartSummaryFragment extends BaseFragment implements View.OnClickListener{
    private TextView serviceCharge,username,street,city_state,phoneneumber;
    private Button cart_continue_btn;
    private ServiceConfirmationInterface serviceConfirmationInterface;

    public CartSummaryFragment() {
        // Required empty public constructor
    }

    public static CartSummaryFragment newInstance() {
        CartSummaryFragment fragment = new CartSummaryFragment();
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
        View cartSummaryView = inflater.inflate(R.layout.fragment_cart_summary, container, false);
        initialiseControls(cartSummaryView);
        setDataToControls();
        return cartSummaryView;
    }

    private void initialiseControls(View cartSummaryView) {
        serviceCharge = (TextView) cartSummaryView.findViewById(R.id.summary_service_price);
        username = (TextView) cartSummaryView.findViewById(R.id.username);
        street = (TextView) cartSummaryView.findViewById(R.id.street);
        city_state = (TextView) cartSummaryView.findViewById(R.id.city_state);
        phoneneumber = (TextView) cartSummaryView.findViewById(R.id.phone_number);
        cart_continue_btn=(Button)cartSummaryView.findViewById(R.id.cart_continue_btn);
        cart_continue_btn.setOnClickListener(this);
    }

    private void setDataToControls() {
        serviceCharge.setText(getResources().getString(R.string.rupee_symbol) + " " + "230.00");
        username.setText(CloudCommerceSessionData.getSessionDataInstance().userAddresses.getName());
        street.setText(CloudCommerceSessionData.getSessionDataInstance().userAddresses.getStreet());
        city_state.setText(CloudCommerceSessionData.getSessionDataInstance().userAddresses.getCity()+","+CloudCommerceSessionData.getSessionDataInstance().userAddresses.getState());
        phoneneumber.setText(CloudCommerceSessionData.getSessionDataInstance().userAddresses.getPhone_number());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            serviceConfirmationInterface = (ServiceConfirmationInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        serviceConfirmationInterface=null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_continue_btn:
                 getServiceConfirmation();
                 break;
            }
        }

    private void getServiceConfirmation() {
        serviceConfirmationInterface.serviceConfirmation(CloudCommerceSessionData.getSessionDataInstance().getUserid(),
                CloudCommerceSessionData.getSessionDataInstance().userAddresses.getUseraddresssid().getUser_address_id(),"1");
    }
}
