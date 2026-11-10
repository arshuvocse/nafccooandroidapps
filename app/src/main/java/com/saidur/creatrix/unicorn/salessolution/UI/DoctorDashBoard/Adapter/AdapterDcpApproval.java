package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCPApproveList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItem;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPList;

import java.util.List;


public class AdapterDcpApproval extends RecyclerView.Adapter<AdapterDcpApproval.VH> implements AdapterDcpDoctorlist.DcpDocClick {
    Context context;
    List<ModelDCPApproveList> dList;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds


    public AdapterDcpApproval(Context context, List<ModelDCPApproveList> cList) {
        this.context = context;
        this.dList = cList;
    }

    @NonNull
    @Override
    public AdapterDcpApproval.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_dcp_approval, parent, false);
        return new AdapterDcpApproval.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDcpApproval.VH holder, int position) {
        ModelDCPApproveList dcp = dList.get(holder.getAdapterPosition());
        holder.date.setText(dcp.getDCPDate() != null ? dcp.getDCPDate() : "");
        holder.entryby.setText(dcp.getEntryBy() != null ? dcp.getEntryBy() : "");

        List<ModelDCPItem> docDetails = dcp.getDCPListDtl() != null ? dcp.getDCPListDtl() : new java.util.ArrayList<>();
        AdapterDcpDoctorlist dapter = new AdapterDcpDoctorlist(context, docDetails, dcp.getDCPDate(), this::onDcpDocClick);
        holder.doclist.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        holder.doclist.setAdapter(dapter);
        holder.doclist.setEnabled(false);
        dapter.notifyDataSetChanged();

        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return dList != null ? dList.size() : 0;
    }


    @Override
    public void onDcpDocClick(String dcpdate, ModelDCPItem dcp, List<ModelDCPItem> dList) {

    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView date, entryby, doctorname, worksession, tourtype,btn_approve,btn_reject,approvalstatus;
        RecyclerView doclist;

        public VH(@NonNull View v) {
            super(v);
            date = (TextView) v.findViewById(R.id.date);
            entryby = (TextView) v.findViewById(R.id.entryby);
            doctorname = (TextView) v.findViewById(R.id.doctorname);
            worksession = (TextView) v.findViewById(R.id.worksession);
            tourtype = (TextView) v.findViewById(R.id.tourtype);
            doclist = (RecyclerView) v.findViewById(R.id.doclist);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

}
