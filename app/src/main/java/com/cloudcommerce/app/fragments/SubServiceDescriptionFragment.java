package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.activities.LoginActivity;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.SubServiceDataModel;
import com.cloudcommerce.app.datamodels.UserDataModel;
import com.cloudcommerce.app.utils.AppConstants;

public class SubServiceDescriptionFragment extends BaseFragment implements View.OnClickListener {
    private SubServiceDataModel selectedService;
    TextView serviceName, serviceDesc, serviceCharge;
    ImageView serviceImage;
    Button addToCartBtn, orderNowBtn;


    public SubServiceDescriptionFragment() {
        // Required empty public constructor
    }

    public static SubServiceDescriptionFragment newInstance() {
        SubServiceDescriptionFragment fragment = new SubServiceDescriptionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedService = (SubServiceDataModel) bundle.getSerializable(AppConstants.SELECTED_SERVICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View serviceDescView = inflater.inflate(R.layout.fragment_sub_service_description, container, false);
        initializeControls(serviceDescView);
        setDataToControls();
        return serviceDescView;
    }

    private void initializeControls(View serviceDescView) {
        serviceName = (TextView) serviceDescView.findViewById(R.id.service_desc_name);
        serviceDesc = (TextView) serviceDescView.findViewById(R.id.service_description);
        serviceImage = (ImageView) serviceDescView.findViewById(R.id.service_desc_image);
        serviceCharge = (TextView) serviceDescView.findViewById(R.id.service_charge);
        addToCartBtn = (Button) serviceDescView.findViewById(R.id.add_to_cart_btn);
        orderNowBtn = (Button) serviceDescView.findViewById(R.id.order_now_btn);
        addToCartBtn.setOnClickListener(this);
        orderNowBtn.setOnClickListener(this);
    }

    private void setDataToControls() {
        serviceName.setText(selectedService.getSubServiceName());
        serviceDesc.setText(selectedService.getSubServiceDesc());
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
            case R.id.add_to_cart_btn:
                break;
            case R.id.order_now_btn:
                //check weather user is logged in or not.If already logged in show address selection page if not show login page
                UserDataModel user = CloudCommerceSessionData.getSessionDataInstance().getUserData();
                if (user != null) {
                    //show address selection page
                } else {
                    //user is not logged in. show login page.
                    launchloginScreenScreen();
                }
                break;
        }
    }

    private void launchloginScreenScreen() {
        Intent serviceDescIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(serviceDescIntent);
    }
}
