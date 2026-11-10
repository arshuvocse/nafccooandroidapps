package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.Rp_StockViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterStock extends RecyclerView.Adapter<AdapterStock.VH> {

    private final List<Rp_StockViewModel> data;

    public AdapterStock(List<Rp_StockViewModel> data) {
        this.data = (data == null) ? new ArrayList<>() : new ArrayList<>(data);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock_row, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Rp_StockViewModel m = data.get(position);

        // TODO: change these to your model's getters
        String name = safe(m.getProductName());      // e.g. Product / Brand name
       // String pack = safe(m.getPackSize());      // e.g. "10x10"
        String qty  = String.valueOf(m.getStockQty()); // e.g. 25.0

        h.tvName.setText(name);
       // h.tvPack.setText(pack);
        h.tvQty.setText(qty);
    }

    @Override
    public int getItemCount() { return data.size(); }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPack, tvQty;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPack = itemView.findViewById(R.id.tvPack);
            tvQty  = itemView.findViewById(R.id.tvQty);
        }
    }

    private String safe(String s) { return s == null ? "" : s; }
}