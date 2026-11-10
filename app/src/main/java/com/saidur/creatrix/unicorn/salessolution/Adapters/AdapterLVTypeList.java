package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignDetails;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ModelLVType;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterLVTypeList implements ListAdapter {
    List<ModelLVType> cdList;
    Context context;

    public AdapterLVTypeList(Context context, List<ModelLVType> cdList) {
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
        ModelLVType data=cdList.get(position);
        if(v==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            v=layoutInflater.inflate(R.layout.lv_lv_type, null);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView tittle=v.findViewById(R.id.lv_type);
            TextView value=v.findViewById(R.id.lv_value);
            tittle.setText(data.getType() +": ");
            value.setText(data.getValue());

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
