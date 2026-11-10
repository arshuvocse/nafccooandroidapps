package com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelCCReport;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelCustomerCallReason;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;


public class CCrAdapter extends RecyclerView.Adapter<CCrAdapter.VH> {
    Context context;
    List<ModelCCReport> dataList;

    public CCrAdapter(Context context, List<ModelCCReport> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CCrAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_cc_report, parent, false);
        return new CCrAdapter.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CCrAdapter.VH holder, int position) {
        ModelCCReport data = dataList.get(holder.getAdapterPosition());
        holder.tv_name.setText(data.getCustomerName());
        holder.tv_o_status.setText(data.getOrderStatus());
        holder.tv_tome_date.setText(data.getChemistCallDate());
        holder.tv_reason.setText(data.getChemistCallReason());
    }

    @Override
    public int getItemCount() {
        if (dataList != null || dataList.size() > 0) {
            return dataList.size();
        } else {
            return 0;
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tv_name,tv_o_status,tv_tome_date,tv_reason;

        public VH(@NonNull View v) {
            super(v);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_o_status = (TextView) v.findViewById(R.id.tv_o_status);
            tv_tome_date = (TextView) v.findViewById(R.id.tv_tome_date);
            tv_reason = (TextView) v.findViewById(R.id.tv_reason);
        }
    }

}
