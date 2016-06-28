package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.adapters.AddressesAdapter;
import com.cloudcommerce.app.adapters.SubServiceAdapter;
import com.cloudcommerce.app.datamodels.Address;

import java.util.List;

public class SelectAddressFragment extends BaseFragment implements View.OnClickListener {
    private RadioButton radioBtn;
    private RecyclerView addressesRecyclerview;
    private List<Address> addressesList;
    private AddressesAdapter addressesAdapter;

    public SelectAddressFragment() {
        // Required empty public constructor
    }

    public static SelectAddressFragment newInstance() {
        SelectAddressFragment fragment = new SelectAddressFragment();
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
        View selectAddressView = inflater.inflate(R.layout.fragment_select_address, container, false);
        initializeControls(selectAddressView);
        return selectAddressView;
    }

    private void initializeControls(View selectAddressView) {
        addressesRecyclerview = (RecyclerView) selectAddressView.findViewById(R.id.addresses_recyclerview);
        //layout manager to position its items
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        addressesRecyclerview.setLayoutManager(llm);
        addressesList = CloudCommerceApplication.getTestData().getAddressList();
        //set adapter
        addressesAdapter = new AddressesAdapter(getActivity(), addressesList);
        addressesAdapter.setListener(this);
        addressesRecyclerview.setAdapter(addressesAdapter);
       /* radioBtn = (RadioButton) selectAddressView.findViewById(R.id.radio_btn);

        radioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean checked = ((RadioButton) v).isChecked();
                String isBtnChecked = v.getTag().toString();
                Log.d(TAG, "OnClickListener called " + isBtnChecked);
                if (isBtnChecked.equals("Checked")) {
                    radioBtn.setChecked(false);
                    radioBtn.setTag("UnChecked");
                } else {
                    radioBtn.setChecked(true);
                    radioBtn.setTag("Checked");
                }
            }
        });*/
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
            case R.id.radio_btn:
                radioBtn = (RadioButton) v;
                String isBtnChecked = v.getTag().toString();
                Log.d(TAG, "OnClickListener called " + isBtnChecked);
                if (isBtnChecked.equals("Checked")) {
                    radioBtn.setChecked(false);
                    radioBtn.setTag("UnChecked");
                } else {
                    radioBtn.setChecked(true);
                    radioBtn.setTag("Checked");
                }
                break;
        }
    }
}
