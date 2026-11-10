package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Tagetvs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelTarget;
import com.saidur.creatrix.unicorn.salessolution.R;
import java.util.List;

public class AdapterAchive extends RecyclerView.Adapter<AdapterAchive.BookViewHolder> {
    private final Context context;
    private final static int FADE_DURATION = 700; //FADE_DURATION in milliseconds
    private final List<ModelTarget> aList;

    public AdapterAchive(List<ModelTarget> aList, Context context) {
        this.aList = aList;
        this.context = context;
    }
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_targetvs_report, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        ModelTarget at=aList.get(position);
        holder.tv_product.setText(at.getProductName());
        holder.tv_targetvalue.setText(at.getTargetValue());
        holder.tv_achive_value.setText(at.getSalesValue());
        holder.tv_achive_prcnt.setText(at.getAchivPer());

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_intop);
        animation.setStartOffset(30 * position);//Provide delay here
        holder.itemView.startAnimation(animation);
    }
    @Override
    public int getItemCount() {
        if(aList!=null && aList.size()>0)
        {
            return aList.size();
        }
        return 0;
    }
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_product,tv_targetvalue,tv_achive_value,tv_achive_prcnt;
        public BookViewHolder(View view) {
            super(view);
            tv_product = view.findViewById(R.id.tv_product);
            tv_targetvalue = view.findViewById(R.id.tv_targetvalue);
            tv_achive_value = view.findViewById(R.id.tv_achive_value);
            tv_achive_prcnt = view.findViewById(R.id.tv_achive_prcnt);
        }
    }



}