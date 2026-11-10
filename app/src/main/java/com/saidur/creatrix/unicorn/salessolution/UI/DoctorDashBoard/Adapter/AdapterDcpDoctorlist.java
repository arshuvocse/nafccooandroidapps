package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItem;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.ActivityDCR;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterDcpDoctorlist extends RecyclerView.Adapter<AdapterDcpDoctorlist.VH> {
    Context context;
    List<ModelDCPItem> dList;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    boolean canEntry = true;
    String dcpdate;
    DcpDocClick dcpDocClick;
    DBCrudHelper dbCrudHelper;
    ModelSession session;

    public AdapterDcpDoctorlist(Context context, List<ModelDCPItem> dList, String dcpdate, DcpDocClick dcpDocClick) {
        this.context = context;
        this.dList = dList;
        this.dcpdate = dcpdate;
        this.dcpDocClick = dcpDocClick;
        dbCrudHelper=new DBCrudHelper(context);

    }

    @NonNull
    @Override
    public AdapterDcpDoctorlist.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_doctor, parent, false);
        return new AdapterDcpDoctorlist.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDcpDoctorlist.VH holder, @SuppressLint("RecyclerView") int position) {
        if (dList == null || position >= dList.size()) return;
        ModelDCPItem dcp = dList.get(holder.getAdapterPosition());
        if (dcp == null) return;
        session = dbCrudHelper.getSession();
        String roleType = (session != null && session.getRoleType() != null) ? session.getRoleType() : "";
        boolean isEligibleRole = "MBE".equalsIgnoreCase(roleType) || "MIO".equalsIgnoreCase(roleType) || "AM".equalsIgnoreCase(roleType);

        if (dcp.isCalled()) {
            holder.tv_call.setVisibility(View.GONE);
            holder.tv_call_status.setVisibility(View.VISIBLE);
        } else {
            if (isEligibleRole) {
                holder.tv_call.setVisibility(View.VISIBLE);
                holder.tv_call_status.setVisibility(View.GONE);
            } else {
                holder.tv_call.setVisibility(View.GONE);
                holder.tv_call_status.setVisibility(View.VISIBLE);
            }
        }
        holder.doctorname.setText(dcp.getDoctorName() != null ? dcp.getDoctorName() : "");
        holder.worksession.setText(dcp.getWorksession() != null ? dcp.getWorksession() : "");
        holder.tourtype.setText(dcp.getTourType() != null ? dcp.getTourType() : "");

        String mcurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        if (!TextUtils.isEmpty(dcpdate)) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date currentDate = dateFormat.parse(mcurrentDate);
                Date targetDcpDate = dateFormat.parse(dcpdate);
                if (targetDcpDate != null && currentDate != null && targetDcpDate.after(currentDate)) {
                    canEntry = true;
                } else {
                    canEntry = false;
                }
            } catch (Exception e) {
                canEntry = false;
                e.printStackTrace();
            }
        } else {
            canEntry = false;
        }

        if (!canEntry) {
            holder.lltop.setOnClickListener(null);
        } else {
            holder.lltop.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    alertDelete(dcp.getDCPDetailId(), position);
                    return true;
                }
            });
        }

        holder.tv_call.setOnClickListener(view -> {
            if (dcpDocClick != null) {
                dcpDocClick.onDcpDocClick(dcpdate, dcp, dList);
            }
        });
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return dList != null ? dList.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView doctorname, worksession, tourtype, tv_call,tv_call_status;
        LinearLayout lltop;

        public VH(@NonNull View v) {
            super(v);
            doctorname = (TextView) v.findViewById(R.id.doctorname);
            worksession = (TextView) v.findViewById(R.id.worksession);
            tourtype = (TextView) v.findViewById(R.id.tourtype);
            tv_call = (TextView) v.findViewById(R.id.tv_call);
            tv_call_status = (TextView) v.findViewById(R.id.tv_call_status);
            lltop = (LinearLayout) v.findViewById(R.id.lltop);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public interface DcpDocClick {
        void onDcpDocClick(String dcpdate,ModelDCPItem dcp, List<ModelDCPItem> dList);
    }

    private void alertDelete(String dcpDetailId, int poss) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Alert!!!")
                .setMessage("Really want to Deleted Doctor?")
                .setPositiveButton("OK", (dialog, which) -> {
                    try {
                        ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
                        Call<ResponseResultInfo> call = service.DeleteDCPDOC(dcpDetailId);
                        call.enqueue(new Callback<ResponseResultInfo>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                                if (response.body() != null) {
                                    dList.remove(poss);
                                    notifyItemRemoved(poss);
                                    dialogSuccess();

                                } else {

                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                                if (t instanceof SocketTimeoutException) {
                                } else {
                                }
                            }
                        });

                    } catch (Exception ex) {
                    }
                    dialog.cancel();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .setCancelable(false).show();
    }

    private void dialogSuccess() {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Alert!!!")
                .setMessage("Doctor Deleted")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                }).setCancelable(false).show();
    }
}
