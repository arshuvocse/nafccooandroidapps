package com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.R;


import java.util.ArrayList;
import java.util.List;


public class CustomerCallAdapter extends RecyclerView.Adapter<CustomerCallAdapter.VH> {
    Context context;
    List<ModelCustomer> cList;
    Click_call click_call;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    public CustomerCallAdapter(Context context, List<ModelCustomer> cList, Click_call click_call) {
        this.context = context;
        this.cList = cList;
        this.click_call = click_call;
    }

    @NonNull
    @Override
    public CustomerCallAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_cc_item, parent, false);
        return new CustomerCallAdapter.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerCallAdapter.VH holder, int position) {
        ModelCustomer customer = cList.get(holder.getAdapterPosition());
        holder.tv_name.setText("Name : "+customer.getCustomerName());
        holder.tv_code.setText("Code : "+customer.getCustomerCode());
        holder.tv_address.setText("Address : "+customer.getAddress());
        holder.tv_market.setText("Market : "+customer.getMarketName());


        holder.tv_btncall.setOnClickListener(view -> {
            click_call.call_click(holder.getAdapterPosition(),customer);
        });
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        if (cList != null || cList.size() > 0) {
            return cList.size();
        } else {
            return 0;
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tv_name,tv_code, tv_address, tv_market,tv_btncall;
        public VH(@NonNull View v) {
            super(v);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_code = (TextView) v.findViewById(R.id.tv_code);
            tv_address = (TextView) v.findViewById(R.id.tv_address);
            tv_market = (TextView) v.findViewById(R.id.tv_market);
            tv_btncall = (TextView) v.findViewById(R.id.tv_btncall);
        }
    }

    public void filterList(ArrayList<ModelCustomer> aList) {
        cList = aList;
        notifyDataSetChanged();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    public interface Click_call{
        void call_click(int pos,ModelCustomer customer);
    }
}
