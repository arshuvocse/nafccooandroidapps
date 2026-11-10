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

import com.saidur.creatrix.unicorn.salessolution.Model.ModelDAR;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDar extends RecyclerView.Adapter<AdapterDar.SingleViewHolder> {
    List<ModelDAR> bList;
    Context context;


    public AdapterDar(Context context, List<ModelDAR> bList) {
        this.bList = bList;
        this.context = context;
    }


    @NonNull
    @Override

    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_dar, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
       ModelDAR dar=bList.get(position);
       holder.title.setText(dar.getTitle());
       holder.morning.setText(dar.getMorning());
       holder.evening.setText(dar.getEvening());
    }

    @Override
    public int getItemCount() {
        return bList.size();
    }


    static class SingleViewHolder extends RecyclerView.ViewHolder {
        TextView title,morning,evening;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            morning = itemView.findViewById(R.id.tv_morning);
            evening = itemView.findViewById(R.id.tv_evening);
        }



    }

}
