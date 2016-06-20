package com.cloudcommerce.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.cloudcommerce.app.CloudCommerceApplication;
import com.cloudcommerce.app.R;
import com.cloudcommerce.app.adapters.NavigationDrawerAdapter;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.NavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhagya .
 */
public class FragmentDrawer extends BaseFragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView;
    private View nav_header_container;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private static ArrayList<NavigationItem> navigationItemsArrayList;
    private String notification;
    private TextView userName, userEmail;
    private ImageView userImg, arrowImage;
    private TextView[] textViewArray;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavigationItem> getData() {

        return navigationItemsArrayList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentNavigationItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        nav_header_container = (View) layout.findViewById(R.id.nav_header_container);
        nav_header_container.setBackgroundColor(getResources().getColor(R.color.navigation_header_bg_color));

        userName = (TextView) layout.findViewById(R.id.username);
        userEmail = (TextView) layout.findViewById(R.id.email_text);

        TextView[] textViewArray = {userName, userEmail};

        setAppData(layout);

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new NavigationDrawerAdapter(getActivity(), getData(), getResources().getString(R.string.menu_services));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        setUserData(textViewArray);
        nav_header_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close navigation drawer
                mDrawerLayout.closeDrawer(containerView);
                //show home screen if user is logged in -- TODO
            }
        });
        return layout;
    }

    private void setAppData(View view) {
        TextView version = (TextView) view.findViewById(R.id.t_version);
        version.setText("Version: " + CloudCommerceApplication.version);
        /*if (!CloudCommerceApplication.environment.equalsIgnoreCase("prod")) {
            TextView env = (TextView) view.findViewById(R.id.t_environment);
            env.setText("Environment: " + CloudCommerceApplication.environment);
        }*/
    }

    private void setUserData(TextView[] textViewArray) {
     /*TODO need to show logged in user details*/
       /* currentUser = CloudCommerceSessionData.getSessionDataInstance().getUserData();
        //get logged in user data from shared prefernces
        if (currentUser != null) {
            arrowImage.setVisibility(View.VISIBLE);
            userName.setText(currentUser.getUserName());
            userEmail.setText(currentUser.getEmail());
        } else {
            arrowImage.setVisibility(View.GONE);
            userName.setText("");
            userEmail.setText("");
        }
        for (TextView textView : textViewArray)
            textView.setTextColor(getResources().getColor(R.color.white));
*/
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //update the navigation header data(points,username and image)
                TextView[] textViewArray = {userName, userEmail};
                setUserData(textViewArray);
                getCurrentNavigationItems();
                adapter.data = getData();
                //update notification count
                adapter.notifyDataSetChanged();
                hideKeyBoard(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    private void getCurrentNavigationItems() {
        navigationItemsArrayList = new ArrayList<>();
        //  preparing navigation drawer items
        NavigationItem navigationFeedItems = new NavigationItem();
        navigationFeedItems.setName(getResources().getString(R.string.menu_services));
        navigationFeedItems.setIcon(R.drawable.ic_menu_service);
        navigationItemsArrayList.add(navigationFeedItems);

        NavigationItem navigationRewardItems = new NavigationItem();
        navigationRewardItems.setName(getResources().getString(R.string.menu_cart));
        navigationRewardItems.setIcon(R.drawable.ic_menu_cart);
        navigationItemsArrayList.add(navigationRewardItems);

        NavigationItem navigationSearchUsersItems = new NavigationItem();
        navigationSearchUsersItems.setName(getResources().getString(R.string.menu_about));
        navigationSearchUsersItems.setIcon(R.drawable.ic_menu_about);
        navigationItemsArrayList.add(navigationSearchUsersItems);

        NavigationItem navigationNotificationsItems = new NavigationItem();
        navigationNotificationsItems.setName(getResources().getString(R.string.menu_help));
        navigationNotificationsItems.setIcon(R.drawable.ic_menu_help);
        navigationItemsArrayList.add(navigationNotificationsItems);
    }
}
