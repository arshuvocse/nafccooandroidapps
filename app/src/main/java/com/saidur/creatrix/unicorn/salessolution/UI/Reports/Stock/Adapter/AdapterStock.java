package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Stock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Model.Rp_StockViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterStock extends RecyclerView.Adapter<AdapterStock.BookViewHolder> {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<Rp_StockViewModel> aList;
    int lastposition = -1;

    public AdapterStock(List<Rp_StockViewModel> nList) {
        this.aList = nList;
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView productQuantity;
        public BookViewHolder(View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.productName);
            productQuantity = (TextView) view.findViewById(R.id.productQuantity);
        }
    }

    @Override
    public AdapterStock.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_stock_list, parent, false);
        context = parent.getContext();
        return new AdapterStock.BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterStock.BookViewHolder holder, int position) {


        holder.productName.setText(aList.get(position).getProductName());
        holder.productQuantity.setText(aList.get(position).getStockQty());
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        animation.setStartOffset(30 * position);//Provide delay here
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }


    private void setFadeAnimation(View view) {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        view.startAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);

        view.startAnimation(animation);
    }


}