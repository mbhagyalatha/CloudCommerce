package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.adapters.ServiceAdapter;

public class HomeFragment extends BaseFragment {
    private RecyclerView servicesRecyclerView;
    private ServiceAdapter serviceAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View servicesView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeControls(servicesView);
        setDataToControls();
        return servicesView;
    }

    private void initializeControls(View servicesView) {
        servicesRecyclerView = (RecyclerView) servicesView.findViewById(R.id.services_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) servicesView.findViewById(R.id.swipeRefreshLayout);
        //layout manager to position its items
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        servicesRecyclerView.setLayoutManager(llm);
        //set adapter
        serviceAdapter = new ServiceAdapter(getActivity(), CloudCommerceApplication.getTestData().getServicesList());
        servicesRecyclerView.setAdapter(serviceAdapter);
        //pull to refresh functionality
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //reload service items
                serviceAdapter.servicesList = CloudCommerceApplication.getTestData().getServicesList();
                serviceAdapter.notifyDataSetChanged();
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
