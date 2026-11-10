package com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan;

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
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TourPlanDetailsActivity;
import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;

public class TourPlanApproveAdapter extends RecyclerView.Adapter<TourPlanApproveAdapter.DVPH> {
    Context c;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<MonthDate> monthList;
    Rcv_TourPlanListener mListener;
    private List<TourPlanViewModel> aTpViewList = new ArrayList<>();

    public TourPlanApproveAdapter(Context c, List<MonthDate> monthList) {
        this.c = c;
        this.monthList = monthList;
    }
    @NonNull
    @Override
    public DVPH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_visit_plan_day_item, parent, false);
        c = parent.getContext();
        return new DVPH(v);
    }
    @Override
    public void onBindViewHolder(@NonNull DVPH holder, int position) {
        MonthDate data = monthList.get(position);
        if (data != null) {
            aTpViewList = data.getaTpViewList();
            holder.tv_day.setText(data.getDateName());
            holder.tv_date.setText(String.valueOf(data.getDateV()));
            if(data.getaTpViewList()!=null)
            {
                if(aTpViewList!=null)
                { String concatenatedTP = "";
                    String marketnameTP;
                    String purposeTP;
                    List<TourPlanViewModel> datas = aTpViewList;
                    for (int i= 0; i < datas.size(); i++) {

                        marketnameTP = datas.get(i).getMarketName();
                        purposeTP = datas.get(i).getTPName();
                        //concatenatedTP += datas.get(i).getMarketName();
                        concatenatedTP += marketnameTP+" : "+purposeTP;
                        if (i < datas.size() - 1) concatenatedTP += "\n";
                    }
                    holder.tv_docName.setText(concatenatedTP);
                }
                else {
                    holder.tv_docName.setText("-----");
                }
            }else { }
            holder.visititem.setOnClickListener(v -> {
                Pair[] pair = new Pair[2];
                pair[0] = new Pair<View,String>(holder.tv_day,"daytrans");
                pair[1] = new Pair<View,String>(holder.tv_date,"datetrans");
                Intent in = new Intent(c, TourPlanDetailsActivity.class);
                in.putExtra("Month", data.getMonthName());
                in.putExtra("Pos", String.valueOf(position));
                in.putExtra("Year", String.valueOf(data.getYearV()));
                in.putExtra("Date", String.valueOf(data.getDateV()));
                in.putExtra("Day", data.getDateName());
                in.putExtra("TDate", data.getDateValue());
                Gson gson=new Gson();
                String item=gson.toJson(data);
                in.putExtra("tpitemjson", item);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions activityOptions =  ActivityOptions.makeSceneTransitionAnimation((Activity) c,pair);
                    c.startActivity(in,activityOptions.toBundle());
                    ((Activity) c).finish();
                }else{
                    c.startActivity(in);
                    ((Activity) c).finish();
                }

               /* Fragment fragment = new VisitPlanListFragment();
                if (fragment != null) {
                    fragmentManager = ((AppCompatActivity)aca).getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).replace(R.id.fragmentContaineplanr, fragment).commit();
                }*/
            });
        }
    }
    @Override
    public int getItemCount() {
        if(monthList!=null)
        {
            return monthList.size();
        }else {
            return 0;
        }
    }
    public class DVPH extends RecyclerView.ViewHolder {
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
