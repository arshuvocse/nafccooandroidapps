package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCRApproveList;
import java.util.List;


public class AdapterDcrApproval extends RecyclerView.Adapter<AdapterDcrApproval.VH> {
    Context context;
    List<ModelDCRApproveList> dList;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds


    public AdapterDcrApproval(Context context, List<ModelDCRApproveList> cList) {
        this.context = context;
        this.dList = cList;
    }

    @NonNull
    @Override
    public AdapterDcrApproval.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_dcr_approval, parent, false);
        return new AdapterDcrApproval.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDcrApproval.VH holder, int position) {
        ModelDCRApproveList dcp = dList.get(holder.getAdapterPosition());
        holder.date.setText(dcp.getDcrDate());
        holder.entryby.setText(dcp.getEntryBy());
        holder.tv_planned.setText(dcp.getPlanned());


        if(dcp.getDoctorName().equals(""))
        {
           holder.lldrname.setVisibility(View.GONE);
        }else {
            holder.tv_drname.setText(dcp.getDoctorName());
        }
        if(dcp.getCallTime().equals(""))
        {
            holder.llcalltime.setVisibility(View.GONE);
        }else {
            holder.tv_calltime.setText(dcp.getCallTime());
        }
        if(dcp.getCallType().equals(""))
        {
            holder.llcalltype.setVisibility(View.GONE);
        }else {
            holder.tv_calltype.setText(dcp.getCallType());
        }
        if(dcp.getCallWith().equals(""))
        {
            holder.llcallrole.setVisibility(View.GONE);
        }else {
            holder.tv_callrole.setText(dcp.getCallWith());
        }
        if(dcp.getDoctorType().equals(""))
        {
            holder.lldrtype.setVisibility(View.GONE);
        }else {
            holder.tv_drtype.setText(dcp.getDoctorType());
        }
        if(dcp.getProductPriority().equals(""))
        {
            holder.llproductpriority.setVisibility(View.GONE);
        }else {
            holder.tv_productpriority.setText(dcp.getProductPriority());
        }
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return dList != null ? dList.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView date,tv_planned, entryby, tv_drname,tv_calltime,tv_calltype,tv_callrole,tv_drtype,
                tv_productpriority;

        LinearLayout lldrname,llcalltime,llcalltype,llcallrole,lldrtype,llproductpriority;

        public VH(@NonNull View v) {
            super(v);
            date = (TextView) v.findViewById(R.id.date);
            tv_planned = (TextView) v.findViewById(R.id.tv_planned);
            entryby = (TextView) v.findViewById(R.id.entryby);
            tv_drname = (TextView) v.findViewById(R.id.tv_drname);
            tv_calltime = (TextView) v.findViewById(R.id.tv_calltime);
            tv_calltype = (TextView) v.findViewById(R.id.tv_calltype);
            tv_callrole = (TextView) v.findViewById(R.id.tv_callrole);
            tv_drtype = (TextView) v.findViewById(R.id.tv_drtype);
            tv_productpriority = (TextView) v.findViewById(R.id.tv_productpriority);


            lldrname = (LinearLayout) v.findViewById(R.id.lldrname);
            llcalltime = (LinearLayout) v.findViewById(R.id.llcalltime);
            llcalltype = (LinearLayout) v.findViewById(R.id.llcalltype);
            llcallrole = (LinearLayout) v.findViewById(R.id.llcallrole);
            lldrtype = (LinearLayout) v.findViewById(R.id.lldrtype);
            llproductpriority = (LinearLayout) v.findViewById(R.id.llproductpriority);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

}
