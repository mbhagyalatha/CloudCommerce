package com.cloudcommerce.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.SubServiceDataModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by bhagya on 6/21/2015.
 */
public class SubServiceAdapter extends RecyclerView.Adapter<SubServiceAdapter.ServiceHolder> {
    public List<SubServiceDataModel> subServicesList = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public SubServiceAdapter(Context context, List<SubServiceDataModel> subServicesList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.subServicesList = subServicesList;
    }

    @Override
    public ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.service_list_card_lyt, parent, false);
        ServiceHolder holder = new ServiceHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ServiceHolder holder, int position) {
        SubServiceDataModel subServiceItem = subServicesList.get(position);
        holder.subServiceName.setText(subServiceItem.getSubServiceName());
        holder.subServiceDesc.setText(subServiceItem.getSubServiceDesc());
        Log.d("sub Service Info", " " + subServiceItem.getSubServiceName());
        //set image to imageview using glide

    }

    @Override
    public int getItemCount() {
        return subServicesList.size();
    }


    class ServiceHolder extends RecyclerView.ViewHolder {
        TextView subServiceName, subServiceDesc;
        ImageView subServiceImage;


        public ServiceHolder(View itemView) {
            super(itemView);
            subServiceName = (TextView) itemView.findViewById(R.id.sub_service_name);
            subServiceDesc = (TextView) itemView.findViewById(R.id.sub_service_des);
            subServiceImage = (ImageView) itemView.findViewById(R.id.sub_service_image);
        }
    }
}
