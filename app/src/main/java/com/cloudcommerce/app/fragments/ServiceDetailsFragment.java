package com.cloudcommerce.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.activities.LoginActivity;
import com.cloudcommerce.app.activities.SelectAddressActivity;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.SubCategoryListDataModel;
import com.cloudcommerce.app.datamodels.UserDataModel;
import com.cloudcommerce.app.utils.AppConstants;

public class ServiceDetailsFragment extends BaseFragment implements View.OnClickListener {
    private SubCategoryListDataModel selectedService;
    TextView serviceName, serviceDesc, serviceCharge;
    ImageView serviceImage;
    Button addToCartBtn, orderNowBtn;


    public ServiceDetailsFragment() {
        // Required empty public constructor
    }

    public static ServiceDetailsFragment newInstance() {
        ServiceDetailsFragment fragment = new ServiceDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedService = (SubCategoryListDataModel) bundle.getSerializable(AppConstants.SELECTED_SERVICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View serviceDescView = inflater.inflate(R.layout.fragment_service_details, container, false);
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
        serviceName.setText(selectedService.getSub_cat_name());
        serviceDesc.setText(selectedService.getSub_description());
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
                    launchAddressSelectionScreen();
                } else {
                    //user is not logged in. show login page.
                    launchloginScreen();
                }
                break;
        }
    }
    private void launchloginScreen() {
        Intent serviceDescIntent = new Intent(getActivity(), LoginActivity.class);
        serviceDescIntent.putExtra(AppConstants.LOGIN_FROM_SCREEN, AppConstants.LOGIN_FROM_SERVICE_DETAILS);
        startActivityForResult(serviceDescIntent, AppConstants.LOGIN_FROM_SERVICE_DETAILS_REQ_ID);
    }

    private void launchAddressSelectionScreen() {
        Intent serviceDescIntent = new Intent(getActivity(), SelectAddressActivity.class);
        startActivity(serviceDescIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG," onActivityResult called");
        if (requestCode == AppConstants.LOGIN_FROM_SERVICE_DETAILS_REQ_ID && resultCode == Activity.RESULT_OK) {
            //launch address selection screen
            launchAddressSelectionScreen();
        }
    }
}
