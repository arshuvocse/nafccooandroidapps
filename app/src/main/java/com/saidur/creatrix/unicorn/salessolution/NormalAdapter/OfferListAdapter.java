package com.saidur.creatrix.unicorn.salessolution.NormalAdapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignDetails;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;

public class OfferListAdapter implements ListAdapter {
    List<CampaignDetails> cdList;
    Context context;

    public OfferListAdapter(Context context, List<CampaignDetails> cdList) {
        this.cdList=cdList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return cdList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        CampaignDetails data=cdList.get(position);
        if(v==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            v=layoutInflater.inflate(R.layout.lv_dcrbrand, null);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView tittle=v.findViewById(R.id.dcrbrand);
            tittle.setText(data.getProductName()+data.getQty());

        }
        return v;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return cdList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
