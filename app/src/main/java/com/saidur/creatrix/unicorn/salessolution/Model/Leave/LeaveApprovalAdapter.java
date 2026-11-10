package com.saidur.creatrix.unicorn.salessolution.Model.Leave;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;

import java.util.List;

public class LeaveApprovalAdapter extends RecyclerView.Adapter<LeaveApprovalAdapter.ViewHolder> {
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private Context context;
    private Activity activity;
    private List<LeaveApprovalData> pList;
    ApprovalLitener approvalLitener;

    public LeaveApprovalAdapter(Activity activity, List<LeaveApprovalData> pList,ApprovalLitener approvalLitener) {
        this.context = context;
        this.activity = activity;
        this.pList = pList;
        this.approvalLitener = approvalLitener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_leave_approval_list, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (pList != null) {
            LeaveApprovalData la = pList.get(position);
            holder.empName.setText(la.getEmpName());
            holder.createdat.setText(la.getEntryDate());
            holder.typeTxt.setText(la.getType());
            holder.dateTxt.setText(la.getLeaveFromDate());
            if (la.getApprovalStatus().equals("0")) {
                holder.status.setText("Pending");
                holder.status.setTextColor(Color.parseColor("#ff7400"));
                holder.lay_approval.setVisibility(View.VISIBLE);

            } else if (la.getApprovalStatus().equals("2")) {
                holder.status.setText("Approved");
                holder.status.setTextColor(Color.parseColor("#00b248"));
                holder.lay_approval.setVisibility(View.GONE);
            } else if (la.getApprovalStatus().equals("3")) {
                holder.status.setText("Rejected");
                holder.status.setTextColor(Color.parseColor("#C12222"));
                holder.lay_approval.setVisibility(View.GONE);
            }
            holder.btn_approve.setOnClickListener(view -> {
              approvalLitener.approveClick(la,holder.getAdapterPosition());
            });
            holder.btn_disapprove.setOnClickListener(view -> {
                approvalLitener.disApproveClick(la,holder.getAdapterPosition());
            });

            holder.llmain.setOnClickListener(v -> {
                approvalLitener.viewClick(la,holder.getAdapterPosition());
             /*   Intent intent = new Intent(activity, LeaveApprovalViewActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(la);
                intent.putExtra("myjson", myJson);
                activity.startActivity(intent);
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);*/

            });
        }


        else {
            SnackBarManagement._warning_CustomMessage(holder.bacground, "Prescription Not Found");
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

        public TextView empName,createdat, typeTxt, dateTxt,status,btn_approve,btn_disapprove;
        ConstraintLayout bacground;
        LinearLayout lay_approval,llmain;

        public ViewHolder(View view) {
            super(view);
            bacground = view.findViewById(R.id.bacground);
            empName = (TextView) view.findViewById(R.id.empname);
            createdat = (TextView) view.findViewById(R.id.createdat);
            typeTxt = (TextView) view.findViewById(R.id.typeTxt);
            dateTxt = (TextView) view.findViewById(R.id.dateTxt);
            status = (TextView) view.findViewById(R.id.status);
            lay_approval = (LinearLayout) view.findViewById(R.id.lay_approval);
            llmain = (LinearLayout) view.findViewById(R.id.llmain);
            btn_approve = (TextView) view.findViewById(R.id.btn_approve);
            btn_disapprove = (TextView) view.findViewById(R.id.btn_disapprove);

            setFadeAnimation(bacground);
        }

    }

    public interface ApprovalLitener{
        void approveClick(LeaveApprovalData ap,int pos);
        void disApproveClick(LeaveApprovalData ap,int pos);
        void viewClick(LeaveApprovalData ap,int pos);
    }
}