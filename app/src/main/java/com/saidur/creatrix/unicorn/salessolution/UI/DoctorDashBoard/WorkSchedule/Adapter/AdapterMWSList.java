package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseTwwpCopyModel;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSData;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSMorning;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ResponseWSData;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.OnEditWSListener;

import java.util.List;


public class AdapterMWSList extends RecyclerView.Adapter<AdapterMWSList.VH> {
    Context context;
    List<ResponseWSData> dList;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    DBCrudHelper dbCrudHelper;
    OnUpdateWSListener onupdateListener;

    public AdapterMWSList(Context context, List<ResponseWSData> cList, OnUpdateWSListener onupdateListener) {
        this.context = context;
        this.dList = cList;
        this.onupdateListener = onupdateListener;
        dbCrudHelper = new DBCrudHelper(context);
    }

    @NonNull
    @Override
    public AdapterMWSList.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_wsmio_list, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMWSList.VH holder, int position) {
        ResponseWSData data = dList.get(holder.getAdapterPosition());
        ModelWSData morning = data.getMorningList();
        ModelWSData evening = data.getEveningList();


        holder.tv_day.setText(data.getDay());
        holder.tv_date.setText(data.getDate());

        if(data.getAmendmentStatus()!=null)
        {
            holder.tv_status.setText(data.getApprovalStatus()+" ("+data.getAmendmentStatus()+" )");
        }
        holder.tv_status.setText(data.getApprovalStatus());

        if (morning != null) {
            holder.tv_mttype.setText(morning.getTourType());
            holder.tv_mwtype.setText(morning.getWorkType());
            holder.tv_mdday.setText(morning.getDeliveryDayName());
            holder.tv_mteritorry.setText(morning.getTerritory());

            holder.tv_mtravel.setText(morning.getTravelBy());
            holder.tv_mstime.setText(morning.getStartTime());
            holder.tv_mspoint.setText(morning.getStartPoint());
            holder.tv_mepoint.setText(morning.getEndPoint());
            holder.tv_metime.setText(morning.getEndTime());
        }
        if (evening != null) {
            holder.tv_ettype.setText(evening.getTourType());
            holder.tv_ewtype.setText(evening.getWorkType());
            holder.tv_edday.setText(evening.getDeliveryDayName());
            holder.tv_eteritorry.setText(evening.getTerritory());

            holder.tv_etravel.setText(evening.getTravelBy());
            holder.tv_estime.setText(evening.getStartTime());
            holder.tv_espoint.setText(evening.getStartPoint());
            holder.tv_eepoint.setText(evening.getEndPoint());
            holder.tv_eetime.setText(evening.getEndTime());
        }

        if(data.getApprovalStatus().equals("Pending"))
        {

            holder.iv_edit.setVisibility(View.GONE);
            holder.btn_amend.setVisibility(View.GONE);
        }
        else if(data.getApprovalStatus().equals("Revert"))
        {
            holder.lllay.setBackgroundColor(Color.parseColor("#FF9825"));
            holder.iv_edit.setVisibility(View.VISIBLE);
            holder.btn_amend.setVisibility(View.GONE);
        }else if(data.getApprovalStatus().equals("Rejected"))
        {
            holder.lllay.setBackgroundColor(Color.parseColor("#C12222"));
            holder.iv_edit.setVisibility(View.GONE);
            holder.btn_amend.setVisibility(View.GONE);
        }
        else
        {
            holder.iv_edit.setVisibility(View.GONE);
        }
        holder.iv_edit.setOnClickListener(view -> {
            onupdateListener.onUpdate(data,holder.getAdapterPosition(),1);
        });
        holder.btn_amend.setOnClickListener(view -> {
            onupdateListener.onUpdate(data,holder.getAdapterPosition(),2);
        });
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return dList != null ? dList.size() : 0;
    }


    public static class VH extends RecyclerView.ViewHolder {
        TextView tv_day, tv_date, tv_mttype, tv_ettype, tv_mwtype, tv_ewtype, tv_mdday, tv_edday, tv_mteritorry, tv_eteritorry,
          tv_mtravel, tv_etravel, tv_mstime, tv_estime, tv_mspoint, tv_espoint,
                tv_mepoint, tv_eepoint, tv_metime, tv_eetime, tv_status;


        ImageView iv_edit;
        LinearLayout btn_amend,lllay;

        public VH(@NonNull View v) {
            super(v);

            tv_day = (TextView) v.findViewById(R.id.tv_day);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            tv_mttype = (TextView) v.findViewById(R.id.tv_mttype);
            tv_ettype = (TextView) v.findViewById(R.id.tv_ettype);
            tv_mwtype = (TextView) v.findViewById(R.id.tv_mwtype);
            tv_ewtype = (TextView) v.findViewById(R.id.tv_ewtype);

            tv_mdday = (TextView) v.findViewById(R.id.tv_mdday);
            tv_edday = (TextView) v.findViewById(R.id.tv_edday);

            tv_mteritorry = (TextView) v.findViewById(R.id.tv_mteritorry);
            tv_eteritorry = (TextView) v.findViewById(R.id.tv_eteritorry);

            tv_mtravel = (TextView) v.findViewById(R.id.tv_mtravel);
            tv_etravel = (TextView) v.findViewById(R.id.tv_etravel);
            tv_mstime = (TextView) v.findViewById(R.id.tv_mstime);
            tv_estime = (TextView) v.findViewById(R.id.tv_estime);
            tv_mspoint = (TextView) v.findViewById(R.id.tv_mspoint);
            tv_espoint = (TextView) v.findViewById(R.id.tv_espoint);
            tv_mepoint = (TextView) v.findViewById(R.id.tv_mepoint);
            tv_eepoint = (TextView) v.findViewById(R.id.tv_eepoint);
            tv_metime = (TextView) v.findViewById(R.id.tv_metime);
            tv_eetime = (TextView) v.findViewById(R.id.tv_eetime);
            tv_status = (TextView) v.findViewById(R.id.tv_status);
            iv_edit = (ImageView) v.findViewById(R.id.iv_edit);
            btn_amend = (LinearLayout) v.findViewById(R.id.btn_amend);
            lllay = (LinearLayout) v.findViewById(R.id.lllay);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public interface OnUpdateWSListener {
        void onUpdate(ResponseWSData data, int pos,int ammend);
    }
}
