package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRxDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRxSummary;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;
import java.util.Locale;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;

public class AdapterReportRxSummay extends RecyclerView.Adapter<AdapterReportRxSummay.BookViewHolder> {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<ModelRxSummary> aList;

    public AdapterReportRxSummay(List<ModelRxSummary> nList, Context context) {
        this.aList = nList;
        this.context = context;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_pName, tv_nofDoctor,tv_nofRx;
        public BookViewHolder(View view) {
            super(view);
            tv_pName = (TextView) view.findViewById(R.id.tv_pName);
            tv_nofDoctor = (TextView) view.findViewById(R.id.tv_nofDoctor);
            tv_nofRx = (TextView) view.findViewById(R.id.tv_nofRx);
        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_rxsummery, parent, false);
        context = parent.getContext();
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (aList != null) {
            try {
                ModelRxSummary rd = aList.get(position);
                holder.tv_pName.setText(rd.getProductName());
                holder.tv_nofDoctor.setText(rd.getNumberofDoctor());
                holder.tv_nofRx.setText(rd.getTotalNumerofRx());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        animation.setStartOffset(30 * position);//Provide delay here
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        if(aList!=null)
        {
            return aList.size();
        }
        return 0;
    }

}
