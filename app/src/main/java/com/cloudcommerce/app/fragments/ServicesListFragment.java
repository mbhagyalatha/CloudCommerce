package com.cloudcommerce.app.fragments;

import android.app.Activity;
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
import com.cloudcommerce.app.activities.ServiceDetailsActivity;
import com.cloudcommerce.app.adapters.SubServiceAdapter;
import com.cloudcommerce.app.datamodels.CategoryDataModel;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.SubCategoryDataModel;
import com.cloudcommerce.app.datamodels.SubCategoryListDataModel;
import com.cloudcommerce.app.interfaces.SubCategoryInterface;
import com.cloudcommerce.app.utils.AppConstants;

import java.util.List;

public class ServicesListFragment extends BaseFragment {
    private RecyclerView subServicesRecyclerView;
    private SubServiceAdapter subsServicesAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<SubCategoryListDataModel> subServicesList;
    private SubCategoryInterface subCategoryInterface;

    public ServicesListFragment() {
        // Required empty public constructor
    }

    public static ServicesListFragment newInstance() {
        ServicesListFragment fragment = new ServicesListFragment();
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
        View subserviceView = inflater.inflate(R.layout.fragment_services_list, container, false);
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
        //subServicesList = CloudCommerceApplication.getTestData().getSubServicesList();
        //set adapter
        //subsServicesAdapter = new SubServiceAdapter(getActivity(), subServicesList);
        //subServicesRecyclerView.setAdapter(subsServicesAdapter);
        //pull to refresh functionality
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //reload service items
                //subServicesList = CloudCommerceApplication.getTestData().getSubServicesList();
                //subsServicesAdapter.subServicesList = subServicesList;
                //subsServicesAdapter.notifyDataSetChanged();
                sendSubServiceCategoriesRequest();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        //adding touch listener to recyclerview to handle item click events
        subServicesRecyclerView.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), subServicesRecyclerView, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //navigate to SubServicesActivity
                launchServiceDescriptionScreen(position);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setDataToControls() {
        sendSubServiceCategoriesRequest();
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            subCategoryInterface = (SubCategoryInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        subCategoryInterface = null;
    }

    private void launchServiceDescriptionScreen(int position) {
        CloudCommerceSessionData.getSessionDataInstance().setSubCategoryListDataModel(subServicesList.get(position));
        Intent serviceDescIntent = new Intent(getActivity(), ServiceDetailsActivity.class);
        //send selected srevice
        serviceDescIntent.putExtra(AppConstants.SELECTED_SERVICE, subServicesList.get(position));
        startActivity(serviceDescIntent);
    }

    public void updateSubServiceCategories(SubCategoryDataModel subCategoryDataModel) {
        this.subServicesList = subCategoryDataModel.getSubCategoryListDataModels();
        subsServicesAdapter = new SubServiceAdapter(getActivity(), subServicesList);
        subServicesRecyclerView.setAdapter(subsServicesAdapter);
    }

    private void sendSubServiceCategoriesRequest() {
        subCategoryInterface.getSubCategoryList();
    }
}
