package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudcommerce.app.R;

public class CartSummaryFragment extends BaseFragment {
    private TextView serviceCharge;

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

    }

    private void setDataToControls() {
        serviceCharge.setText(getResources().getString(R.string.rupee_symbol) + " " + "230.00");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
