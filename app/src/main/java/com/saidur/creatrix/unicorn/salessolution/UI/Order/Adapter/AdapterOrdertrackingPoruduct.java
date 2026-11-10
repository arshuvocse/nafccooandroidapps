package com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterOrdertrackingPoruduct extends RecyclerView.Adapter<AdapterOrdertrackingPoruduct.BookViewHolder> {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<ModelProduct> aList;


    public AdapterOrdertrackingPoruduct(List<ModelProduct> aList) {
        this.aList = aList;
    }

    @Override
    public AdapterOrdertrackingPoruduct.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_order_details_product, parent, false);
        context = parent.getContext();
        return new AdapterOrdertrackingPoruduct.BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterOrdertrackingPoruduct.BookViewHolder holder, int position) {



        holder.product_name_txt.setText(aList.get(position).getProductName());
        holder.quantity_txt.setText(String.valueOf(aList.get(position).getQuantity()));
        holder.price_txt.setText(String.valueOf(aList.get(position).getUnitPrice()));
        holder.total_vat_txt.setText(String.valueOf(aList.get(position).getTotalVatAmount()));
        holder.total_amt_txt.setText(String.valueOf(aList.get(position).getNetAmount()));
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name_txt;
        public TextView quantity_txt;
        public TextView price_txt;
        public TextView total_vat_txt;
        public TextView total_amt_txt;


        public BookViewHolder(View view) {
            super(view);


            product_name_txt = (TextView) view.findViewById(R.id.product_name_txt);
            quantity_txt = (TextView) view.findViewById(R.id.quantity_txt);
            price_txt = (TextView) view.findViewById(R.id.price_txt);
            total_vat_txt = (TextView) view.findViewById(R.id.total_vat_txt);
            total_amt_txt = (TextView) view.findViewById(R.id.total_amt_txt);



        }
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


}