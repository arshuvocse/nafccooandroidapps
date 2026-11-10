package com.saidur.creatrix.unicorn.salessolution.UI.Leave;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalData;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveVM;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;

import java.util.List;

public class AdapterLeaveList extends RecyclerView.Adapter<AdapterLeaveList.ViewHolder> {
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds

    private Activity activity;
    private List<LeaveVM> pList;

    public AdapterLeaveList(Activity activity, List<LeaveVM> pList) {
        this.activity = activity;
        this.pList = pList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_leave_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (pList != null) {
            LeaveVM la = pList.get(position);
            holder.createdate.setText(la.getCreatedAt());
            holder.leavetype.setText(la.getLeaveTypeName());
            holder.startdate.setText(la.getLeaveFromDate());
            holder.enddate.setText(la.getLeaveToDate());

            if (la.getApprovalStatus().equals("0")) {
                holder.leavestatus.setText("Pending");
                holder.leavestatus.setTextColor(Color.parseColor("#ff7400"));
            } else if (la.getApprovalStatus().equals("1")) {
                holder.leavestatus.setText("Verified");
                holder.leavestatus.setTextColor(Color.parseColor("#4169e1"));
            } else if (la.getApprovalStatus().equals("2")) {
                holder.leavestatus.setText("Approved");
                holder.leavestatus.setTextColor(Color.parseColor("#00b248"));
            } else if (la.getApprovalStatus().equals("3")) {
                holder.leavestatus.setText("Rejected");
                holder.leavestatus.setTextColor(Color.parseColor("#C12222"));
            }

        }


    }


    @Override
    public int getItemCount() {
        if (pList != null) {
            return pList.size();
        }
        return 0;
    }

    private void setFadeAnimation(View view) {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        view.startAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);
        //   view.startAnimation(set);
        view.startAnimation(animation);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView createdate,leavetype, startdate, enddate,leavestatus;
        CardView masterLayout;

        public ViewHolder(View view) {
            super(view);
            masterLayout = view.findViewById(R.id.masterLayout);
            createdate = (TextView) view.findViewById(R.id.createdate);
            leavetype = (TextView) view.findViewById(R.id.leavetype);
            startdate = (TextView) view.findViewById(R.id.startdate);
            enddate = (TextView) view.findViewById(R.id.enddate);
            leavestatus = (TextView) view.findViewById(R.id.leavestatus);

            setFadeAnimation(masterLayout);
        }

    }
}