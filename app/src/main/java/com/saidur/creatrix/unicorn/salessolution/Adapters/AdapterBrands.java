package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterBrands extends RecyclerView.Adapter<AdapterBrands.SingleViewHolder> {
    List<ModelProduct> bList;
    Context context;
    BrandSelectListener blistener;

    public AdapterBrands(Context context, List<ModelProduct> bList) {
        this.bList = bList;
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.rv_brand_item, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
        holder.bind(bList.get(position));
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
        return bList.size();
    }


    static class SingleViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_select;
        TextView brand;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_select = itemView.findViewById(R.id.iv_select);
            brand = itemView.findViewById(R.id.brand);
        }

        void bind(ModelProduct brands) {
            brand.setText(brands.getProductName());
            iv_select.setVisibility(brands.isChecked() ? View.VISIBLE : View.GONE);
            itemView.setOnClickListener(view -> {
                brands.setChecked(!brands.isChecked());
                iv_select.setVisibility(brands.isChecked() ? View.VISIBLE : View.GONE);
            });
        }

    }
    //Get All Brand
    public List<ModelProduct> getAll() {
        return bList;
    }
    //Get Selected when btn click
    public List<ModelProduct> getSelected()
    {
        List<ModelProduct> selected = new ArrayList<>();
        for(int i = 0;i<bList.size();i++)
        {
            if (bList.get(i).isChecked())
            {
                selected.add(bList.get(i));
            }
        }
        return selected;
    }
    public void filterList(ArrayList<ModelProduct> arrayList) {
        bList = arrayList;
        notifyDataSetChanged();
    }

    public interface BrandSelectListener {
        void onBrandSelect(List<ModelProduct> blist);
    }
}
