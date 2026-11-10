package com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.DeleteListeners;
import com.saidur.creatrix.unicorn.salessolution.Interface.NotifyListener;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterPendingCounter;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.PlaceOrderActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;

import java.text.DecimalFormat;
import java.util.List;


public class AdapterDraftOrder extends RecyclerView.Adapter<AdapterDraftOrder.dvh> {
    private Context context;
    private Activity activity;
    private final DecimalFormat df3 = new DecimalFormat("#.###");
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    //private List<OrderViewModel> aCustomerList;
    private List<OrderMaster> aOrderList;
    public DBCrudHelper dbCrudHelper;
    DeleteListeners deleteListener;
    NotifyListener notifyListener;
    PresenterPendingCounter presenter;
    public AdapterDraftOrder(Context context, Activity activity, NotifyListener notifyListener, DeleteListeners deleteListener) {
        this.context = context;
        this.activity = activity;
        this.notifyListener = notifyListener;
        this.deleteListener = deleteListener;

    }
    public AdapterDraftOrder(List<OrderMaster> aOrderList, DeleteListeners deleteListener) {
        this.aOrderList = aOrderList;
        this.dbCrudHelper = dbCrudHelper;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public dvh onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_draftorder, parent, false);
        context = parent.getContext();
        return new dvh(itemView);
    }
    @Override
    public void onBindViewHolder(dvh holder, int position) {
        OrderMaster ad = aOrderList.get(position);
        holder.custName.setText(ad.getCustomer().getCustomerName());
        holder.createdDate.setText(ad.getCollectionDate());
        holder.deliveryDate.setText(ad.getDeliveryDate());

        int i;
        double totaltp = 0.00;
        double totalVat = 0.00;
        double totalFinal = 0.00;
        for(i=0;i<ad.getOrderDetails().size();i++)
        {
            //TODO:Price Calculation
            ModelProduct product=new ModelProduct();
            product=ad.getOrderDetails().get(i);

            int totalQn = product.getQuantity();
            double unitPrice = product.getUnitPrice();
            double vatAmaount = product.getVatPercentage();
            double TP = (unitPrice * totalQn);
            double totalvat = (vatAmaount * totalQn);

            totaltp = totaltp + TP;
            totalVat = totalVat + totalvat;
            totalFinal = (totaltp + totalVat);
        }
        holder.price.setText(df3.format(totalFinal));
      //  holder.price.setText(String.valueOf(Math.round(totalFinal)));
        holder.clickID.setOnLongClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage("Are you sure wants to delete the Item ?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(

                    "Delete",
                    (dialog, id) -> {
                        try {
                            //Todo:When Delete any product
                            dbCrudHelper=new DBCrudHelper(context);
                            dbCrudHelper.DeleteOldOrder_OrderTable_SQLite(ad.getOrderIdLocal());
                            aOrderList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            presenter=new PresenterPendingCounter(activity);
                            presenter.totalOrder();
                            presenter.totalOrderMaster();
                            notifyDataSetChanged();
                            dialog.cancel();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    });
            builder1.setNegativeButton(
                    "Cancel",
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();
            return true;
        });

        holder.clickID.setOnClickListener(v -> {
            Constants.WHO="DraftOrderAdapter";
            OrderMaster ord;
            ord = aOrderList.get(holder.getAdapterPosition());
            ModelCustomer cst;
            cst=ord.getCustomer();
            Intent intent = new Intent(context, PlaceOrderActivity.class);//
            Gson gson = new Gson();
            String myJson = gson.toJson(cst);
            String orderDetails = gson.toJson(ord);

            intent.putExtra("myjson", myJson);
            intent.putExtra("orderDetails", orderDetails);
            context.startActivity(intent);


        });

          setFadeAnimation(holder.clickID);
    }

    @Override
    public int getItemCount() {
        return aOrderList.size();
    }


    public static class dvh extends RecyclerView.ViewHolder {
        public TextView custName,createdDate,deliveryDate,price;
        public CardView clickID;
        public dvh(View view) {
            super(view);
            custName = (TextView) view.findViewById(R.id.custName);
            createdDate = (TextView) view.findViewById(R.id.createdDate);
            deliveryDate = (TextView) view.findViewById(R.id.deliveryDate);
            price = (TextView) view.findViewById(R.id.price);
            clickID = (CardView) view.findViewById(R.id.clickID);
        }
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


}
