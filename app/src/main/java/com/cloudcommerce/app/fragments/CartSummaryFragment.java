package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcommerce.app.R;

public class CartSummaryFragment extends BaseFragment {

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
        return cartSummaryView;
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
