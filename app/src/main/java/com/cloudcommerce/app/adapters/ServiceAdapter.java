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
import com.cloudcommerce.app.datamodels.ServiceDataModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by bhagya on 6/21/2015.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {
    public List<ServiceDataModel> servicesList = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public ServiceAdapter(Context context, List<ServiceDataModel> servicesList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.servicesList = servicesList;
    }

    @Override
    public ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.service_card_lyt, parent, false);
        ServiceHolder holder = new ServiceHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ServiceHolder holder, int position) {
        ServiceDataModel serviceItem = servicesList.get(position);
        holder.serviceName.setText(serviceItem.getServiceName());
        holder.serviceOfferInfo.setText(serviceItem.getServiceOfferInfo());
        Log.d("Service Info", " " + serviceItem.getServiceName() + " : " + serviceItem.getServiceOfferInfo());
        //set image to imageview using glide

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public void remove(int position) {
        servicesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, servicesList.size());
        notifyDataSetChanged();
    }

    class ServiceHolder extends RecyclerView.ViewHolder {
        TextView serviceName, serviceOfferInfo;
        ImageView serviceImage;


        public ServiceHolder(View itemView) {
            super(itemView);
            serviceName = (TextView) itemView.findViewById(R.id.service_name);
            serviceOfferInfo = (TextView) itemView.findViewById(R.id.price_off_on_service);
            serviceImage = (ImageView) itemView.findViewById(R.id.service_image);
        }
    }
}
