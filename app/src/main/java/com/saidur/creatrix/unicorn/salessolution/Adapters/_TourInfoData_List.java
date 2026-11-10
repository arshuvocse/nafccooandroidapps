package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.ATourPlanDtlsDAO;
import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _TourInfoData_List extends RecyclerView.Adapter<_TourInfoData_List.BookViewHolder> {
    private Context context;
    Activity activity;
    private List<TourPlanViewModel> nList;
    private List<ATourPlanDtlsDAO> TPList;
    Rcv_TourPlanListener mListener;
    public _TourInfoData_List(List<TourPlanViewModel> nList,Context context,Rcv_TourPlanListener mListener) {
        this.nList = nList;
        this.context = context;
        this.mListener = mListener;
    }
    public _TourInfoData_List(List<ATourPlanDtlsDAO> TPList, Activity activity, Rcv_TourPlanListener mListener) {
        this.TPList = TPList;
        this.context = activity;
        this.mListener = mListener;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView marketTxt;
        public TextView customerTxt;
        public TextView shiftTxt;
        public TextView typeTxt;
        public TextView puposeTxt;
        public TextView VisitTypeTxt;
        public TextView marketEndTxt;
        public TextView commentTxt;
        public TextView deleteClick;
        public LinearLayout customerSection,end_sec,strt_sec;
        public BookViewHolder(View view) {
            super(view);
            marketTxt = (TextView) view.findViewById(R.id.marketTxt);
            customerTxt = (TextView) view.findViewById(R.id.customerTxt);
            marketEndTxt = (TextView) view.findViewById(R.id.marketEndTxt);
          //  shiftTxt = (TextView) view.findViewById(R.id.shiftTxt);
            typeTxt = (TextView) view.findViewById(R.id.typeTxt);
            puposeTxt = (TextView) view.findViewById(R.id.puposeTxt);
            VisitTypeTxt = (TextView) view.findViewById(R.id.VisitTypeTxt);
           // commentTxt = (TextView) view.findViewById(R.id.commentTxt);
            deleteClick = (TextView) view.findViewById(R.id.deleteClick);
            customerSection = (LinearLayout) view.findViewById(R.id.customerSection);
            end_sec = (LinearLayout) view.findViewById(R.id.end_sec);
            strt_sec = (LinearLayout) view.findViewById(R.id.strt_sec);

        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zrv_tourplan_datewisedetail, parent, false);
        context = parent.getContext();
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {

        ATourPlanDtlsDAO tpDetails= TPList.get(position);
        holder.marketTxt.setText(tpDetails.getMarketName());
        holder.puposeTxt.setText(tpDetails.getTPName());
        holder.VisitTypeTxt.setText(tpDetails.getVisitType());
        holder.customerTxt.setText(tpDetails.getOtherMarketName());
        holder.marketEndTxt.setText(tpDetails.getMarketNameEnd());

        if (tpDetails.getVisitType().trim().equals("Other Visit")) {
            holder.strt_sec.setVisibility(View.GONE);
            holder.end_sec.setVisibility(View.GONE);
            holder.customerSection.setVisibility(View.GONE);
        }


      /*  holder.marketTxt.setText(nList.get(position).getMarketName());
        if(nList.get(position).isMarketWise()){
            holder.customerSection.setVisibility(View.GONE);

        }else{
            holder.customerSection.setVisibility(View.VISIBLE);
            holder.customerTxt.setText(nList.get(position).getCustomerName());
        }

        holder.shiftTxt.setText(nList.get(position).getShiftText());
        holder.typeTxt.setText(nList.get(position).getTourTypeName());
        holder.puposeTxt.setText(nList.get(position).getTPName());
        holder.commentTxt.setText(nList.get(position).getComment());*/


       /* if(nList.get(position).isFinalSubmit()){
            holder.deleteClick.setVisibility(View.GONE);
        }else{
            try {
                String mystring = UtilityHelper._GetCurrentDate();
                String dsd = nList.get(position).getTourPlanDate();
                Date cDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(mystring);
                Date dDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(nList.get(position).getTourPlanDate());

                Calendar crntDate = Calendar.getInstance();
                Calendar dbDate = Calendar.getInstance();
                crntDate.setTime(cDate);
                dbDate.setTime(dDate);
                if(dbDate.before(crntDate)){
                    holder.deleteClick.setVisibility(View.GONE);
                }else{
                    holder.deleteClick.setVisibility(View.VISIBLE);
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }


        }
        holder.deleteClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Delete");
                builder1.setMessage("Are you sure want to delete this ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                try{
                                    TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
                                    Call<ResultInfo> call = service.DeleteTourPlanData(nList.get(position).getTourPlanId());
                                    call.enqueue(new Callback<ResultInfo>() {
                                        @Override
                                        public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                                            ResultInfo info =response.body();
                                            if(info.getSuccess() == true){

                                                new androidx.appcompat.app.AlertDialog.Builder(context)
                                                        .setTitle("Success")
                                                        .setMessage("Tour Plan Deleted Successfully")
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.cancel();
                                                                mListener.ReloadCurrentActivity();
                                                            }

                                                        }).setCancelable(false).show();


                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ResultInfo> call, Throwable t) {
                                            if(t instanceof SocketTimeoutException){
                                                ShowErrorMsg();
                                            }else{
                                                ShowErrorMsg();
                                            }
                                        }
                                    });

                                }catch (Exception ex){
                                    String str = ex.toString();
                                    Log.e("Exception",str);
                                    ShowErrorMsg();
                                }

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });*/

    }

    public void ShowErrorMsg(){
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("Some Error Occurred. Please Try Again")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }

                }).setCancelable(false).show();
    }

    @Override
    public int getItemCount() {
        return TPList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}