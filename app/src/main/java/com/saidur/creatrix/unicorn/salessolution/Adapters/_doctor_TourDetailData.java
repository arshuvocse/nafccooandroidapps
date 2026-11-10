package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.UtilityHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _doctor_TourDetailData extends RecyclerView.Adapter<_doctor_TourDetailData.BookViewHolder> {
    private Context context;
    private final List<TourPlanViewModel> nList;
    Rcv_TourPlanListener mListener;

    public _doctor_TourDetailData(List<TourPlanViewModel> nList, Context context, Rcv_TourPlanListener mListener) {
        this.nList = nList;
        this.context = context;
        this.mListener = mListener;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView doctorTxt;
        public TextView deleteClick;

        public BookViewHolder(View view) {
            super(view);
            doctorTxt = view.findViewById(R.id.doctorTxt);
            deleteClick = (TextView) view.findViewById(R.id.deleteClick);


        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zrv_doctor_tourdetail, parent, false);
        context = parent.getContext();
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        try {
            holder.doctorTxt.setText(nList.get(position).getDoctorName());

            if (nList.get(position).isFinalSubmit()) {
                holder.deleteClick.setVisibility(View.GONE);
            } else {
                String mystring = UtilityHelper._GetCurrentDate();
                //String dsd = nList.get(position).getTourPlanDate();
                Date cDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(mystring);
                Date dDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(nList.get(position).getTourPlanDate());

                Calendar crntDate = Calendar.getInstance();
                Calendar dbDate = Calendar.getInstance();
                assert cDate != null;
                crntDate.setTime(cDate);
                assert dDate != null;
                dbDate.setTime(dDate);
                if (dbDate.before(crntDate)) {
                    holder.deleteClick.setVisibility(View.GONE);
                } else {
                    holder.deleteClick.setVisibility(View.VISIBLE);
                }


            }

            holder.deleteClick.setOnClickListener(v -> {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Delete");
                builder1.setMessage("Are you sure want to delete this ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        (dialog, id) -> {
                            dialog.cancel();
                            try {
                                ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
                                Call<ResultInfo> call = service.DeleteTourPlanData(nList.get(holder.getAdapterPosition()).getDocTPDetailsId());
                                call.enqueue(new Callback<ResultInfo>() {
                                    @Override
                                    public void onResponse(@NonNull Call<ResultInfo> call, @NonNull Response<ResultInfo> response) {
                                        ResultInfo info = response.body();
                                        assert info != null;
                                        if (info.getSuccess()) {

                                            new AlertDialog.Builder(context)
                                                    .setTitle("Success")
                                                    .setMessage("Visit Plan Deleted Successfully")
                                                    .setPositiveButton("OK", (dialog1, which) -> {
                                                        dialog1.cancel();
                                                        mListener.ReloadCurrentActivity();
                                                    }).setCancelable(false).show();


                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<ResultInfo> call, @NonNull Throwable t) {
                                        ShowErrorMsg();
                                    }
                                });

                            } catch (Exception ex) {
                                String str = ex.toString();
                                Log.e("Exception", str);
                                ShowErrorMsg();
                            }

                        });

                builder1.setNegativeButton(
                        "No",
                        (dialog, id) -> dialog.cancel());

                AlertDialog alert11 = builder1.create();
                alert11.show();

            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public void ShowErrorMsg() {
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("Some Error Occurred. Please Try Again")
                .setPositiveButton("OK", (dialog, which) -> dialog.cancel()).setCancelable(false).show();
    }

    @Override
    public int getItemCount() {
        return nList.size();
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