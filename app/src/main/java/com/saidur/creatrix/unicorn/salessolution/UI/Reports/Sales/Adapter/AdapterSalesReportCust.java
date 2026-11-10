package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model.CWSData;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterSalesReportCust extends RecyclerView.Adapter<AdapterSalesReportCust.dvh> {
    private Context context;
    private Activity activity;
    private DecimalFormat df3 = new DecimalFormat("#.###");
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<CWSData> aOrderList;
    public AdapterSalesReportCust(List<CWSData> aOrderList, Context context) {
        this.aOrderList = aOrderList;
        this.context = context;
    }

    @Override
    public dvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_customerwise_order, parent, false);
        context = parent.getContext();
        return new dvh(itemView);
    }
    @Override
    public void onBindViewHolder(dvh holder, int position) {
        CWSData ad = aOrderList.get(position);
        holder.rv_sn.setText(ad.getSN());
        holder.rv_name.setText(ad.getCustomer_name());
        holder.rv_amount.setText(ad.getOrdered_value());
        holder.rv_qty.setText(ad.getOrdered_qty());
    }

    @Override
    public int getItemCount() {
        return aOrderList.size();
    }


    public static class dvh extends RecyclerView.ViewHolder {
        public TextView rv_name,rv_qty,rv_amount,rv_sn;
        public dvh(View view) {
            super(view);
            rv_sn = (TextView) view.findViewById(R.id.rv_sn);
            rv_name = (TextView) view.findViewById(R.id.rv_name);
            rv_qty = (TextView) view.findViewById(R.id.rv_qty);
            rv_amount = (TextView) view.findViewById(R.id.rv_amount);
        }
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


}
