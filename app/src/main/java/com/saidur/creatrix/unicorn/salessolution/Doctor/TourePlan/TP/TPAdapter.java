package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TP;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.List;

public class TPAdapter extends RecyclerView.Adapter<TPAdapter.DVPH> {
    Context c;
    Activity activity;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    List<TourPlanViewModel> aList;

    public TPAdapter(Activity activity, List<TourPlanViewModel> aList) {
        this.activity = activity;
        this.aList = aList;
    }
    @NonNull
    @Override
    public DVPH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_plan_day_item, parent, false);
        return new DVPH(v);
    }
    @Override
    public void onBindViewHolder(@NonNull DVPH holder, int position) {
        TourPlanViewModel data = aList.get(position);
        SimpleDateFormat dateV = new SimpleDateFormat("dd", Locale.US);
        SimpleDateFormat dayName = new SimpleDateFormat("EEE", Locale.US);
        SimpleDateFormat mdates = new SimpleDateFormat("dd MMM", Locale.US);
        SimpleDateFormat fmt2 = new SimpleDateFormat("EEE, dd MMM", Locale.US);
        SimpleDateFormat inpdate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = null;
            String day = null;
            String mdate = null;

            try {
                date = inpdate.parse(data.getTourPlanDate());
                day = dayName.format(date);
                mdate = mdates.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        if (data != null) {
            holder.tv_day.setText(day);
            holder.tv_date.setText(mdate);
                if(aList!=null)
                {
                    holder.tv_docName.setText(data.getDetails());
                }
                else {
                    holder.tv_docName.setText("-----");
                }
        }
    }
    @Override
    public int getItemCount() {
        if(aList.size()>0)
        {
            return aList.size();
        }else {
            return 0;
        }
    }
    public static class DVPH extends RecyclerView.ViewHolder {
        TextView tv_day, tv_date, tv_docName;
        CardView visititem;
        public DVPH(@NonNull View v) {
            super(v);
            tv_day = v.findViewById(R.id.tv_day);
            tv_date = v.findViewById(R.id.tv_date);
            tv_docName = v.findViewById(R.id.tv_docName);
            visititem = v.findViewById(R.id.visititem);
        }
    }
}
