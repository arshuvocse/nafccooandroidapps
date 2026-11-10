package com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.DepoStockModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Rp_StockViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterDepoStock extends RecyclerView.Adapter<AdapterDepoStock.DVH> {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<Rp_StockViewModel> depoStockList;


    public AdapterDepoStock(List<Rp_StockViewModel> depoStockList, Context context) {
        this.depoStockList = depoStockList;
        this.context = context;
    }

    @Override
    public DVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_depostock, parent, false);
        context = parent.getContext();
        return new DVH(itemView);
    }

    @Override
    public void onBindViewHolder(DVH holder, int position) {
        Rp_StockViewModel ds = depoStockList.get(position);
        holder.stockProductName.setText(ds.getProductName());
        holder.stockProductCode.setText(ds.getProductCode());
        holder.stockProductQty.setText(String.valueOf(ds.getStockQty()));
    }

    @Override
    public int getItemCount()
    {
        if(depoStockList!=null)
        {
            return depoStockList.size();
        }
        else {
            return 0;
        }

    }

    public static class DVH extends RecyclerView.ViewHolder {
        public TextView stockProductName,stockProductCode,stockProductQty;

        public LinearLayout clickID;

        public DVH(View view) {
            super(view);
            stockProductName = (TextView) view.findViewById(R.id.stockProductName);
            stockProductCode = (TextView) view.findViewById(R.id.stockProductCode);
            stockProductQty = (TextView) view.findViewById(R.id.stockProductQty);
        }
    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
