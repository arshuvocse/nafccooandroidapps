package com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Adapters._TourInfoData_List;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.ATourPlanDate;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.ATourPlanDtlsDAO;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.ATourPlanMaster;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.TourPlanApprovalData;
import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class TourPlanApprovalAdapter extends RecyclerView.Adapter<TourPlanApprovalAdapter.BookViewHolder> /*implements ITourplan.View */ {
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    Rcv_TourPlanListener mListener;
    private TourPlanApprovalData tpdata;
    private ATourPlanMaster tpM;
    private ATourPlanDate tpdate;
    private ATourPlanDtlsDAO tpdtl;
    List<ATourPlanDate> tpListdata;
    Activity activity;

   /* public TourPlanApprovalAdapter(TourPlanApprovalData tpdata, Activity activity, Rcv_TourPlanListener mListener) {
        this.tpdata = tpdata;
        this.activity = activity;
        this.mListener = mListener;
    }*/

    public TourPlanApprovalAdapter(List<ATourPlanDate> tpListdata, Activity activity, Rcv_TourPlanListener mListener) {
        this.tpListdata = tpListdata;
        this.activity = activity;
        this.mListener = mListener;
    }


    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.z_reclyview_month_days, parent, false);
        // .inflate(R.layout.doctor_visit_plan_day_item, parent, false);
        return new BookViewHolder(itemView);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView dateName, tv_day, tv_date;
        public TextView datevalue;
        public ImageView plusBtn;
        public RecyclerView recyclerView;

        public BookViewHolder(View view) {
            super(view);
            tv_day = (TextView) view.findViewById(R.id.tv_day);
            tv_date = (TextView) view.findViewById(R.id.tv_date);

            dateName = (TextView) view.findViewById(R.id.dateName);
            plusBtn = (ImageView) view.findViewById(R.id.plusBtn);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_rc);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        ATourPlanDate data = tpListdata.get(position);
        holder.dateName.setText(data.getDayValue() + "\n" + data.getDayName());

        //nested recycler
        if (data.getaTourPlanDtlsDAO().size() > 0) {
            _TourInfoData_List dpter;
            dpter = new _TourInfoData_List(data.getaTourPlanDtlsDAO(), activity, mListener);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerView.addItemDecoration(new DividerItemDecoration(activity,
                    DividerItemDecoration.VERTICAL));
            holder.recyclerView.setAdapter(dpter);
            dpter.notifyDataSetChanged();
        }
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        if(tpListdata.size()>0)
        {
            return tpListdata.size();
        }else return 0;

    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

}


