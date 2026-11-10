package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelPromo;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterPromos extends RecyclerView.Adapter<AdapterPromos.SingleViewHolder> {
    List<ModelPromo> pList;
    Context context;
    PromoDltListener plistener;

    public AdapterPromos(Context context, List<ModelPromo> pList, PromoDltListener plistener) {
        this.pList = pList;
        this.context = context;
        this.plistener = plistener;
    }

    /*public void setBrand(List<ModelBrand> bList)
    {
        this.bList=new ArrayList<>();
        this.bList=bList;
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override

    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_promo, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
        holder.bind(pList, pList.get(position), position, plistener);
     /*   ModelBrand rm = bList.get(position);
        holder.brand.setText(rm.getProductSQName());
       *//* DoctorBrand rm = bList.get(position);
        holder.brand.setText(rm.getBrandName());
        holder.iv_select.setVisibility(rm.);*//*
        holder.iv_select.setVisibility(rm.isChecked() ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(view -> {
            rm.setChecked(!rm.isChecked());
            holder.iv_select.setVisibility(rm.isChecked() ? View.VISIBLE : View.GONE);
        });*/
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }


    static class SingleViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView tv_name;
        ConstraintLayout masterLayout;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            masterLayout = itemView.findViewById(R.id.masterLayout);
        }

        void bind(List<ModelPromo> pList, ModelPromo brands, int position, PromoDltListener plistener) {
            tv_name.setText(brands.getPromoName());
            icon.setImageResource(brands.getIcon());
            masterLayout.setOnLongClickListener(view -> {
                plistener.onPromoDLTClick(brands);
                pList.remove(position);


               /* brands.setChecked(!brands.isChecked());
                iv_select.setVisibility(brands.isChecked() ? View.VISIBLE : View.GONE);*/
                return true;
            });
        }

    }

    //Get All Brand
    public List<ModelPromo> getAll() {
        return pList;
    }


    public interface PromoDltListener {
        void onPromoDLTClick(ModelPromo promo);
    }
}
