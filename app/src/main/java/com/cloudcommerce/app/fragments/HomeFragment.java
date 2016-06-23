package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.activities.ServicesListActivity;
import com.cloudcommerce.app.adapters.ServiceAdapter;
import com.cloudcommerce.app.datamodels.ServiceDataModel;
import com.cloudcommerce.app.utils.AppConstants;

import java.util.List;

public class HomeFragment extends BaseFragment {
    private RecyclerView servicesRecyclerView;
    private ServiceAdapter serviceAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ServiceDataModel> servicesList;


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
        servicesList = CloudCommerceApplication.getTestData().getServicesList();
        serviceAdapter = new ServiceAdapter(getActivity(), servicesList);
        servicesRecyclerView.setAdapter(serviceAdapter);
        //pull to refresh functionality
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //reload service items
                servicesList = CloudCommerceApplication.getTestData().getServicesList();
                serviceAdapter.servicesList = servicesList;
                serviceAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        //adding touch listener to recyclerview to handle item click events
        servicesRecyclerView.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), servicesRecyclerView, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //navigate to SubServicesActivity
                launchSubservicesScreen(servicesList.get(position).getServiceName());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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

    private void launchSubservicesScreen(String serviceName) {
        Intent subServicesIntent = new Intent(getActivity(), ServicesListActivity.class);
        subServicesIntent.putExtra(AppConstants.SERVICENAME,serviceName);
        startActivity(subServicesIntent);
    }
}
