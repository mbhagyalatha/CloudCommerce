package com.cloudcommerce.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.NavigationItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by bhagya
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    public List<NavigationItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private String inbox;

    public NavigationDrawerAdapter(Context context, List<NavigationItem> data, String inbox) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.inbox = inbox;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavigationItem currentNavigationItem = data.get(position);
        holder.title.setText(currentNavigationItem.getName());
        holder.title.setTextColor(context.getResources().getColor(R.color.white));
        holder.image.setImageResource(currentNavigationItem.getIcon());
        holder.seperatorLine.setBackgroundColor(context.getResources().getColor(R.color.menu_items_divide_color));

        // TODO: Add notification count based on webservice response and notification visibility.
      /*  if (currentNavigationItem.getName().equals(inbox)) {
            holder.notificationLyt.setVisibility(View.VISIBLE);
           holder.notificationCount.setText("3");
        } else {
            holder.notificationLyt.setVisibility(View.GONE);
        }*/

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, notificationCount;
        ImageView image;
        FrameLayout notificationLyt;
        View seperatorLine;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.nav_item_txt);
            image = (ImageView) itemView.findViewById(R.id.nav_item_img);
            notificationLyt = (FrameLayout) itemView.findViewById(R.id.inbox_unread_msgs_count);
            notificationCount = (TextView) itemView.findViewById(R.id.inbox_unread_msg_count);
            seperatorLine = (View)itemView.findViewById(R.id.draweritem_seperator);
        }
    }
}
