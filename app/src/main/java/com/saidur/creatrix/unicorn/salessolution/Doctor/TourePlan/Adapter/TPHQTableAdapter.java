package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.TPHQModel;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class TPHQTableAdapter extends RecyclerView.Adapter<TPHQTableAdapter.TPHQ> {
    Context c;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    List<TPHQModel> tphqModels;
    public TPHQTableAdapter(Context c, List<TPHQModel> tphqModels) {
        this.c = c;
        this.tphqModels = tphqModels;
    }

    @NonNull
    @Override
    public TPHQ onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_tphq_table, parent, false);
        c = parent.getContext();
        return new TPHQ(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TPHQ holder, int position) {
        TPHQModel dl = tphqModels.get(position);
        if (dl != null && tphqModels.size()>0) {
            ModelCustomer dll=new ModelCustomer();
            holder.name.setText(dl.getTourTypeName());
            holder.value.setText(dl.getBalance());
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return tphqModels.size();
    }
    public class TPHQ extends RecyclerView.ViewHolder {
        TextView name,value;
        public TPHQ(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.name);
            value = v.findViewById(R.id.value);
        }
    }
}

