package com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignCalModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.text.DecimalFormat;
import java.util.List;


public class Adapter_ordersummary extends RecyclerView.Adapter<Adapter_ordersummary.BookViewHolder> {
    private Context context;
   List<CampaignCalModel> aProductList;
    DecimalFormat decimalFormat = new DecimalFormat("##.00");
    public Adapter_ordersummary(List<CampaignCalModel> aProductList) {
        this.aProductList = aProductList;
    }
    @Override
    public Adapter_ordersummary.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_ordersummery, parent, false);
        context = parent.getContext();
        return new Adapter_ordersummary.BookViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(Adapter_ordersummary.BookViewHolder holder, int position) {
        holder.productName.setText(aProductList.get(position).getProductName());
        holder.unitPrice.setText(Double.toString(aProductList.get(position).getUnitPrice()));
        holder.quantity.setText(String.valueOf(aProductList.get(position).getQuantity()));
        holder.tp.setText(Double.toString(Double.parseDouble(decimalFormat.format(aProductList.get(position).getTotalPrice()))));
        holder.price.setText(Double.toString(Double.parseDouble(decimalFormat.format(aProductList.get(position).getNetAmount()))));
        if(String.valueOf(aProductList.get(position).getDiscountValue()) !=null){
            holder.discountTxt.setText(Double.toString(Double.parseDouble(decimalFormat.format(aProductList.get(position).getDiscountValue()))));
        }
        if(String.valueOf(aProductList.get(position).getDiscountPercentage()) !=null){
            holder.discountprcntTxt.setText(Double.toString(Double.parseDouble(decimalFormat.format(aProductList.get(position).getDiscountPercentage()))));
        }

    }

    @Override
    public int getItemCount() {
        return aProductList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView unitPrice;
        public TextView quantity;
        public TextView tp;
        public TextView price;
        public TextView discountTxt,discountprcntTxt;
        public BookViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.itemNameTxt);
            unitPrice = view.findViewById(R.id.unitPriceTxt);
            quantity = view.findViewById(R.id.quantityTxt);
            tp = view.findViewById(R.id.totalPriceTxt);
            price = view.findViewById(R.id.priceTxt);
            discountTxt = view.findViewById(R.id.discountTxt);
            discountprcntTxt = view.findViewById(R.id.discountprcntTxt);
        }
    }
}




