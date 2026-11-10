package com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.PlaceOrderActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.VH> {
    Context context;
    List<ModelCustomer> cList;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    public CustomerAdapter(Context context, List<ModelCustomer> cList) {
        this.context = context;
        this.cList = cList;
    }

    @NonNull
    @Override
    public CustomerAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_customer_order, parent, false);
        return new CustomerAdapter.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.VH holder, int position) {
        ModelCustomer customer = cList.get(holder.getAdapterPosition());
        holder.customerName.setText(customer.getCustomerName());
        holder.customer_code_txt.setText(customer.getCustomerCode());
        holder.customer_type_txt.setText(customer.getCustomerType());
        holder.customer_adress_txt.setText(customer.getAddress());
        holder.customer_market_txt.setText(customer.getMarketName());
        holder.mobile_txt.setText(customer.getCellNo());
        holder.mobile_txt.setOnClickListener(v -> {
            //Intent callIntent=new Intent(Intent.ACTION_CALL);
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            callIntent.setData(Uri.parse("tel:" + customer.getCellNo()));
            context.startActivity(callIntent);
        });
        holder.cv_item.setOnClickListener(v -> {
            ModelCustomer sCustomer;
            Constants.WHO="OrderMainAdapter";
            sCustomer = cList.get(position);
            Pair[] pair = new Pair[2];
            pair[0] = new Pair<View, String>(holder.profile_image, "imagetrans");
            pair[1] = new Pair<View, String>(holder.customerName, "titletrans");
                Intent intent = new Intent(context, PlaceOrderActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(sCustomer);
                intent.putExtra("myjson", myJson);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
                    context.startActivity(intent, activityOptions.toBundle());
                } else {
                    context.startActivity(intent);
                }

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
        TextView customerName, customer_code_txt, customer_adress_txt, customer_market_txt, customer_type_txt, mobile_txt,tv_call;
        CardView cv_item;
        CircleImageView profile_image;

        public VH(@NonNull View v) {
            super(v);
            customerName = (TextView) v.findViewById(R.id.customerName);
            customer_code_txt = (TextView) v.findViewById(R.id.customer_code_txt);
            customer_adress_txt = (TextView) v.findViewById(R.id.customer_adress_txt);
            customer_market_txt = (TextView) v.findViewById(R.id.customer_market_txt);
            customer_type_txt = (TextView) v.findViewById(R.id.customer_type_txt);
            mobile_txt = (TextView) v.findViewById(R.id.mobile_txt);
            tv_call = (TextView) v.findViewById(R.id.tv_call);
            cv_item = (CardView) v.findViewById(R.id.cv_item);
            profile_image = (CircleImageView) v.findViewById(R.id.profile_image);
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

}
