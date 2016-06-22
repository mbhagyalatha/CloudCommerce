package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.adapters.SubServiceAdapter;

public class SubServicesFragment extends BaseFragment {
    private RecyclerView subServicesRecyclerView;
    private SubServiceAdapter subsServicesAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public SubServicesFragment() {
        // Required empty public constructor
    }

    public static SubServicesFragment newInstance() {
        SubServicesFragment fragment = new SubServicesFragment();
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
        View subserviceView = inflater.inflate(R.layout.fragment_sub_services, container, false);
        initializeControls(subserviceView);
        setDataToControls();
        return subserviceView;
    }

    private void initializeControls(View subServicesView) {
        subServicesRecyclerView = (RecyclerView) subServicesView.findViewById(R.id.sub_services_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) subServicesView.findViewById(R.id.swipeRefreshLayout);
        //layout manager to position its items
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        subServicesRecyclerView.setLayoutManager(llm);
        //set adapter
        subsServicesAdapter = new SubServiceAdapter(getActivity(), CloudCommerceApplication.getTestData().getSubServicesList());
        subServicesRecyclerView.setAdapter(subsServicesAdapter);
        //pull to refresh functionality
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //reload service items
                subsServicesAdapter.subServicesList = CloudCommerceApplication.getTestData().getSubServicesList();
                subsServicesAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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

}
