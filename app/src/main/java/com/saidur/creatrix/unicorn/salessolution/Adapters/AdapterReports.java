package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelReportsMenu;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.ActivityDCPApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.ActivityDCRApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.PlaceOrderActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Attendance.ActivityRpt_Attendance;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Doctor.ActivityDoctorList;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Order.ReportOrderSummary;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Product.ReportProducts;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Rx.ReportRxActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.ReportSalesActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Stock.ReportStock;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Tagetvs.ReportTargetvsActivity;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityReportTargetvsBinding;

import java.util.List;

public class AdapterReports extends RecyclerView.Adapter<AdapterReports.SingleViewHolder> {

    private Context context;
    boolean isVisible;
    private List<ModelReportsMenu> reportsMenus;

    public AdapterReports(Context context, List<ModelReportsMenu> reportsMenus,boolean isVisible) {
        this.context = context;
        this.reportsMenus = reportsMenus;
        this.isVisible = isVisible;
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_reports, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
        ModelReportsMenu rm = reportsMenus.get(position);
        holder.menuName.setText(rm.getMenuName());
        holder.icon.setImageResource(rm.getIcon());
        if(!isVisible)
        {
            if(rm.getId()==10)
            {
                holder.cv.setVisibility(View.GONE);
            }
           /* if(rm.getId()==11)
            {
                holder.cv.setVisibility(View.GONE);
            }*/
        }


        holder.cv.setOnClickListener(view -> {
            switch (rm.getId()) {
                case 1:
                    Intent attendance = new Intent(context, ActivityRpt_Attendance.class);
                    context.startActivity(attendance);
                    break;
                case 2:
                    Toast.makeText(context, "Expanse Report", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(context, "DA Report", Toast.LENGTH_SHORT).show();
                 /*   Pair[] pair = new Pair[1];
                    pair[0] = new Pair<View, String>(holder.menuName, "titletrans");
                    Intent intent = new Intent(context, ReportOrderSummary.class);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
                        context.startActivity(intent, activityOptions.toBundle());
                    } else {
                        context.startActivity(intent);
                    }*/
                    break;
                case 4:
                    Intent repoorder = new Intent(context, ReportSalesActivity.class);
                    repoorder.putExtra("From", "Order");
                    context.startActivity(repoorder);
                    break;
                case 5:
                    Intent reposale = new Intent(context, ReportSalesActivity.class);
                    reposale.putExtra("From", "Sales");
                    context.startActivity(reposale);
                    break;
                case 6:
                    Intent rerx = new Intent(context, ReportRxActivity.class);
                    context.startActivity(rerx);
                    break;
                case 7:
                    Pair[] pair = new Pair[1];
                    pair[0] = new Pair<View, String>(holder.menuName, "titletrans");
                    Intent reproduct = new Intent(context, ReportProducts.class);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
                        context.startActivity(reproduct, activityOptions.toBundle());
                    } else {
                        context.startActivity(reproduct);
                    }
                    break;
                case 8:
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(holder.menuName, "titletrans");
                    Intent repostock = new Intent(context, ReportStock.class);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
                        context.startActivity(repostock, activityOptions.toBundle());
                    } else {
                        context.startActivity(repostock);
                    }
                    break;
                case 9:
                    Pair[] pairz = new Pair[1];
                    pairz[0] = new Pair<View, String>(holder.menuName, "titletrans");
                    Intent repodoc = new Intent(context, ActivityDoctorList.class);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairz);
                        context.startActivity(repodoc, activityOptions.toBundle());
                    } else {
                        context.startActivity(repodoc);
                    }
                    break;
                case 10:
                    Intent dcp = new Intent(context, ActivityDCPApproval.class);
                    context.startActivity(dcp);
                    break;
                case 11:
                    Intent dcr = new Intent(context, ActivityDCRApproval.class);
                    context.startActivity(dcr);
                    break;
                case 12:
                  /*  Intent ta = new Intent(context, ReportTargetvsActivity.class);
                    context.startActivity(ta);*/
                    break;
                case 13:
                    Toast.makeText(context, "Leave Report", Toast.LENGTH_SHORT).show();
                    break;
                case 14:
                    Toast.makeText(context, "Mileage Report", Toast.LENGTH_SHORT).show();
                    break;

            }
        });
    }

    @Override
    public int getItemCount() {
        return reportsMenus.size();
    }


    static class SingleViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView menuName;
        CardView cv;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            menuName = itemView.findViewById(R.id.menuName);
            cv = itemView.findViewById(R.id.cv_item);
        }

    }
}
