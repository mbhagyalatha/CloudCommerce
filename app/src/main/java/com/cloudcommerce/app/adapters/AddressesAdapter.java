package com.cloudcommerce.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cloudcommerce.app.R;
import com.cloudcommerce.app.datamodels.CloudCommerceSessionData;
import com.cloudcommerce.app.datamodels.UserAddresses;

import java.util.Collections;
import java.util.List;

/**
 * Created by bhagya on 6/21/2015.
 */
public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.AddressHolder> {
    private static final String TAG = "AddressesAdapter";
    public List<UserAddresses> addressesList = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener listener;
    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    public AddressesAdapter(Context context, List<UserAddresses> addressesList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.addressesList = addressesList;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.address_item, parent, false);
        AddressHolder holder = new AddressHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AddressHolder holder, final int position) {
        UserAddresses addressItem = addressesList.get(position);
        holder.userName.setText(addressItem.getName());
        holder.address.setText(addressItem.getStreet() + "\n" + addressItem.getCity() + ", " + addressItem.getState() + "\n" + addressItem.getPhone_number());
        //set image to imageview using glide

        holder.addrSelectionRadioBtn.setChecked(addressesList.get(position).isSelected());
        holder.addrSelectionRadioBtn.setTag(new Integer(position));
        //for default check in first item
        if(position == 0 && addressesList.get(0).isSelected() && holder.addrSelectionRadioBtn.isChecked())
        {
            lastChecked = holder.addrSelectionRadioBtn;
            lastCheckedPos = 0;
        }

        holder.addrSelectionRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton cb = (RadioButton)v;
                int clickedPos=((Integer)cb.getTag()).intValue();

                if (cb.isChecked()) {
                    if (lastChecked != null) {
                        lastChecked.setChecked(false);
                        addressesList.get(lastCheckedPos).setSelected(false);
                    }
                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                } else
                    lastChecked = null;
                addressesList.get(clickedPos).setSelected(cb.isChecked());
                CloudCommerceSessionData.isaddressSelected = true;
                CloudCommerceSessionData.userAddresses = addressesList.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressesList.size();
    }


    class AddressHolder extends RecyclerView.ViewHolder {
        TextView userName, address;
        ImageView editAddress;
        RadioButton addrSelectionRadioBtn;


        public AddressHolder(View itemView) {
            super(itemView);
            addrSelectionRadioBtn = (RadioButton) itemView.findViewById(R.id.radio_btn);
            userName = (TextView) itemView.findViewById(R.id.username);
            address = (TextView) itemView.findViewById(R.id.address);
            editAddress = (ImageView) itemView.findViewById(R.id.edit_address);
            addrSelectionRadioBtn.setOnClickListener(listener);
           /* addrSelectionRadioBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //boolean checked = ((RadioButton) v).isChecked();
                    String isBtnChecked = v.getTag().toString();
                    Log.d(TAG, "OnClickListener called " + isBtnChecked);
                    if (isBtnChecked.equals("Checked")) {
                        addrSelectionRadioBtn.setChecked(false);
                        addrSelectionRadioBtn.setTag("UnChecked");
                    } else {
                        addrSelectionRadioBtn.setChecked(true);
                        addrSelectionRadioBtn.setTag("Checked");
                    }
                }
            });*/
        }
    }
}
