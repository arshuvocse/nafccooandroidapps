package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.ActivityTwwpAprrovalDetails;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpApprovalMaster;

import java.util.List;


public class AdapterTwwpApprovalMaster extends RecyclerView.Adapter<AdapterTwwpApprovalMaster.VH> {
    Activity context;
    List<ModelTwwpApprovalMaster> tList;
    TwwpClick twwpClick;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    String whoz;

    public AdapterTwwpApprovalMaster(Activity context, List<ModelTwwpApprovalMaster> tList, String whoz, TwwpClick twwpClick) {
        this.context = context;
        this.tList = tList;
        this.twwpClick = twwpClick;
        this.whoz = whoz;
    }

    @NonNull
    @Override
    public AdapterTwwpApprovalMaster.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_twwp_approval_master, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTwwpApprovalMaster.VH holder, int position) {
        ModelTwwpApprovalMaster data = tList.get(holder.getAdapterPosition());


        holder.tv_emp.setText(data.getEmpName());
        holder.tv_code.setText(data.getEmpMasterCode());
        holder.tv_teri.setText(data.getTerritoryName());
        holder.tv_created.setText(data.getCreateAt());

        if(whoz.equals("Report"))
        {
            holder.ll_approve.setVisibility(View.GONE);
            if (data.getApprovalStatus().equals("0")) {
                holder.tv_status.setText("Pending");
                holder.tv_status.setTextColor(Color.parseColor("#ff7400"));
            }  else if (data.getApprovalStatus().equals("2")) {
                holder.tv_status.setText("Approved");
                holder.tv_status.setTextColor(Color.parseColor("#00b248"));
            } else if (data.getApprovalStatus().equals("3")) {
                holder.tv_status.setText("Rejected");
                holder.tv_status.setTextColor(Color.parseColor("#C12222"));
            }

        }else {
            holder.ll_approve.setVisibility(View.VISIBLE);
            if (data.getApprovalStatus().equals("0")) {
                holder.tv_status.setText("Pending");
                holder.tv_status.setTextColor(Color.parseColor("#ff7400"));

                holder.tv_approve.setVisibility(View.VISIBLE);
                holder.tv_disApprove.setVisibility(View.VISIBLE);
            }  else if (data.getApprovalStatus().equals("2")) {
                holder.tv_status.setText("Approved");
                holder.tv_status.setTextColor(Color.parseColor("#00b248"));
                holder.tv_approve.setVisibility(View.GONE);
                holder.tv_disApprove.setVisibility(View.VISIBLE);

            } else if (data.getApprovalStatus().equals("3")) {
                holder.tv_status.setText("Rejected");
                holder.tv_status.setTextColor(Color.parseColor("#C12222"));
                holder.tv_approve.setVisibility(View.VISIBLE);
                holder.tv_disApprove.setVisibility(View.GONE);
            }

            holder.tv_approve.setOnClickListener(view -> {
                twwpClick.onApproveClick(data.getTWWWPMasterId(),1);
            });
            holder.tv_disApprove.setOnClickListener(view -> {
                twwpClick.onApproveClick(data.getTWWWPMasterId(),2);
            });

        }
        holder.cardClickdv.setOnClickListener(view -> {
            Gson gson = new Gson();
            String datas = gson.toJson(data);
            Intent in = new Intent(context, ActivityTwwpAprrovalDetails.class);
            in.putExtra("Data", datas);
            in.putExtra("Who", whoz);
            context.startActivity(in);
            context.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        });
        setFadeAnimation(holder.itemView);

    }

    @Override
    public int getItemCount() {
        if (tList != null || tList.size() > 0) {
            return tList.size();
        } else {
            return 0;
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tv_emp, tv_code, tv_teri, tv_created, tv_status,tv_approve,tv_disApprove;
        CardView cardClickdv;
        LinearLayout ll_approve;

        public VH(@NonNull View v) {
            super(v);
            tv_emp = (TextView) v.findViewById(R.id.tv_emp);
            tv_code = (TextView) v.findViewById(R.id.tv_code);
            tv_teri = (TextView) v.findViewById(R.id.tv_teri);
            tv_created = (TextView) v.findViewById(R.id.tv_created);
            tv_status = (TextView) v.findViewById(R.id.tv_status);
            tv_approve = (TextView) v.findViewById(R.id.tv_approve);
            tv_disApprove = (TextView) v.findViewById(R.id.tv_disApprove);

            cardClickdv = (CardView) v.findViewById(R.id.cardClickdv);
            ll_approve = (LinearLayout) v.findViewById(R.id.ll_approve);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public interface TwwpClick {
        void onApproveClick(int mid,int type);
    }
}
