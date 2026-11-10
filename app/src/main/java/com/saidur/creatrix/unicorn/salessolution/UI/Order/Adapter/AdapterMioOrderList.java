package com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.OrderTracking.OrderTrackingActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.OrderTracking.OrderTrackingDetailsActivity;

import java.util.List;

public class AdapterMioOrderList extends RecyclerView.Adapter<AdapterMioOrderList.BookViewHolder> {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<OrderViewModel> aCustomerList;


    public AdapterMioOrderList(List<OrderViewModel> customerList) {
        this.aCustomerList = customerList;
    }

    @Override
    public AdapterMioOrderList.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_mio_orderlist, parent, false);
        context = parent.getContext();
        return new AdapterMioOrderList.BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMioOrderList.BookViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (aCustomerList!=null)
        {
            OrderViewModel ad = aCustomerList.get(position);
          //  if(aCustomerList.get(position).getCustomerType().equals(""))
            holder.customertype.setText(aCustomerList.get(position).getCustomerType());
            holder.customername.setText(aCustomerList.get(position).getCustomerName());
            holder.orderstatus.setText(aCustomerList.get(position).getOrderStatus());
            holder.orderid.setText(aCustomerList.get(position).getOrderCode());
            holder.grosstxt.setText(aCustomerList.get(position).getGrossValue());
            holder.mioname.setText(aCustomerList.get(position).getOrderType());
            holder.createdAt.setText(aCustomerList.get(position).getCreatedAt());
            holder.createdby.setText(aCustomerList.get(position).getCreatedBy());
            holder.status.setText(aCustomerList.get(position).getApprovalStatus());

            if (aCustomerList.get(position).getOrderStatus() != null) {

                if (aCustomerList.get(position).getOrderStatus().equals("Invoiced")) {
                    holder.statusUD.setBackgroundColor(Color.parseColor("#03A858"));
                } else if (aCustomerList.get(position).getOrderStatus().equals("Pending")) {
                    holder.statusUD.setBackgroundColor(Color.parseColor("#FFC107"));
                } else if (aCustomerList.get(position).getOrderStatus().equals("Invoice Pending")) {
                    holder.statusUD.setBackgroundColor(Color.parseColor("#673AB7"));
                } else if (aCustomerList.get(position).getOrderStatus().equals("Approved")) {
                    holder.statusUD.setBackgroundColor(Color.parseColor("#2196F3"));
                } else if (aCustomerList.get(position).getOrderStatus().equals("Payment Completed")) {
                    holder.statusUD.setBackgroundColor(Color.parseColor("#FF00E676"));
                } else if (aCustomerList.get(position).getOrderStatus().equals("Delivery Completed")) {
                    holder.statusUD.setBackgroundColor(Color.parseColor("#00B0FF"));
                }
            }
            holder.clickID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderViewModel ord;
                    ord = aCustomerList.get(position);
                    Intent intent = new Intent(context, OrderTrackingDetailsActivity.class);
                    int orderId = ord.getOrderId();
//                Gson gson = new Gson();
//                String myJson = gson.toJson(ord);
                    intent.putExtra("myOrderId", orderId);
                    intent.putExtra("from", "OrderList");
                    intent.putExtra("nId", 0);
                    context.startActivity(intent);


                }
            });
        }

        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        if(aCustomerList!=null)
        {
            return aCustomerList.size();
        }
        return 0;
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView orderid,orderstatus,customername,grosstxt,mioname,createdAt,createdby,customertype,status;
        public LinearLayout clickID,statusUD;

        public BookViewHolder(View view) {
            super(view);

            customertype = (TextView) view.findViewById(R.id.customertype);
            orderid = (TextView) view.findViewById(R.id.orderid);
            orderstatus = (TextView) view.findViewById(R.id.orderstatus);
            customername = (TextView) view.findViewById(R.id.customername);
            grosstxt = (TextView) view.findViewById(R.id.grosstxt);
            mioname = (TextView) view.findViewById(R.id.mioname);
            clickID = (LinearLayout) view.findViewById(R.id.clickID);
            statusUD = (LinearLayout) view.findViewById(R.id.statusUD);
            createdAt = (TextView) view.findViewById(R.id.createdAt);
            createdby = (TextView) view.findViewById(R.id.createdby);
            status = (TextView) view.findViewById(R.id.status);


        }
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}


