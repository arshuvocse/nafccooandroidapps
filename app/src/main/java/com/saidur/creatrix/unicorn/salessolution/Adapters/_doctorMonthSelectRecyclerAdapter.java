package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Interface.ITourplan;
import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.DoctorTourPlanMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.CustomDialog;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SessionManagement;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _doctorMonthSelectRecyclerAdapter extends RecyclerView.Adapter<_doctorMonthSelectRecyclerAdapter.BookViewHolder> implements ITourplan.View {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<MonthDate> aList;
    Rcv_TourPlanListener mListener;
    String[] listItemProduct_Sample;
    boolean[] checkedItems_Sample;
    Activity ac;

    List<DoctorListViewModel> aProductListList_Sample = new ArrayList<>();

    public _doctorMonthSelectRecyclerAdapter(List<MonthDate> aLista, Rcv_TourPlanListener mListener,Activity ac) {
        this.aList = aLista;
        this.mListener = mListener;
        this.ac = ac;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.z_reclyview_month_days, parent, false);
        context = parent.getContext();
        return new BookViewHolder(itemView);
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView dateName;
        public TextView datevalue;
        public ImageView plusBtn;
        public RecyclerView recyclerView;

        public BookViewHolder(View view) {
            super(view);


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


        holder.dateName.setText(aList.get(position).getMonthName());

        if (aList.get(position).isFinalSubmit()) {
            holder.plusBtn.setVisibility(View.GONE);
        }
        holder.plusBtn.setOnClickListener(v -> {
            MonthDate ord = aList.get(holder.getAdapterPosition());
            String tourDate = aList.get(holder.getAdapterPosition()).getDateValue();


            SessionManagement session = new SessionManagement(context);
            HashMap<String, String> user = session.getUserDetails();
            int empId = Integer.parseInt(user.get(SessionManagement.KEY_EmpId));
//                ImageView closeBtn = popupView.findViewById(R.id.closeBtn);
//                closeBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.cancel();
//                    }
//                });


            LoadDoctor(empId, tourDate);


        });

        //nested recycler

        if (aList.get(position).getaTpViewList().size() > 0) {
            _doctor_TourDetailData dpter;
            dpter = new _doctor_TourDetailData(aList.get(position).getaTpViewList(), context, mListener);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerView.addItemDecoration(new DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL));
            holder.recyclerView.setAdapter(dpter);
            dpter.notifyDataSetChanged();
        }

        setFadeAnimation(holder.itemView);
    }

    public void ShowErrorMsg() {
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("Some Error Occurred. Please Try Again")
                .setPositiveButton("OK", (dialog, which) -> dialog.cancel()).setCancelable(false).show();
    }


    @Override
    public int getItemCount() {
        return aList.size();
    }


    @Override
    public void OnTourPlanDataGet(List<TourPlanViewModel> aList) {

    }

    @Override
    public void OnTourPlanDailyDataGet(List<MonthDate> aList) {

    }

    @Override
    public void OnArreangList(List<MonthDate> aMondateList, boolean is_Entry, List<TourPlanViewModel> aTpLIst) {

    }

    @Override
    public void OnFailour(String msg) {

    }

    @Override
    public void OnSuccessTPPDay(String msg) {

    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    // Data laod
    public void LoadDoctor(int empId, String tourDate) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorListViewModel>> call = service.GetDoctorList(empId);
            call.enqueue(new Callback<List<DoctorListViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<DoctorListViewModel>> call, @NonNull Response<List<DoctorListViewModel>> response) {
                    SetDoctor(response.body(), tourDate, empId);
                }

                @Override
                public void onFailure(@NonNull Call<List<DoctorListViewModel>> call, @NonNull Throwable t) {
                }
            });
        } catch (Exception ex) {
        }
    }
    // Data Set
    public void SetDoctor(List<DoctorListViewModel> aList, String tourDate, int empId) {
        CustomDialog cdd=new CustomDialog(ac);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.90);
        cdd.getWindow().setLayout(width, height);

        cdd.show();
        ArrayList<Integer> mUserItems_Sample = new ArrayList<>();
        if (aList != null) {
            listItemProduct_Sample = new String[aList.size()];
            for (int i = 0; i < aList.size(); i++) {
                listItemProduct_Sample[i] = aList.get(i).getDoctorName();
            }
            checkedItems_Sample = new boolean[listItemProduct_Sample.length];
            /*CustomDialog cdd=new CustomDialog(ac);
            cdd.show();*/


            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            mBuilder.setTitle("Select Doctor");

            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            //View dialogView = inflater.inflate(R.layout._searchbar_custom, null);
            View v=ac.getLayoutInflater().inflate(R.layout._searchbar_custom, null);
          //  EditText editText = (EditText) dialogView.findViewById(R.id.search_viewc);
            //EditText vi = (EditText) dialogView.findViewById(R.id.search_viewc);
            mBuilder.setView(v);
            // mBuilder.setView(R.layout._searchbar_custom);
            mBuilder.setMultiChoiceItems(listItemProduct_Sample, checkedItems_Sample, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                    if (isChecked) {
                        mUserItems_Sample.add(position);
                    } else {
                        mUserItems_Sample.remove((Integer.valueOf(position)));
                    }
                }
            });
            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton("Done", (dialogInterface, which) -> {
                String item = "";
                aProductListList_Sample.clear();
                try {
                    if (mUserItems_Sample != null) {
                        for (int i = 0; i < mUserItems_Sample.size(); i++) {
                            int pos = mUserItems_Sample.get(i);
                            aProductListList_Sample.add(aList.get(pos));
                        }
                        SaveDoctorTourPlan(empId, tourDate, aProductListList_Sample);
                    }
                } catch (Exception ex) {
                    throw ex;
                }
            });
            //mBuilder.setView(R.layout.searchable_list_dialog);
            //mBuilder.setView(R.layout.custom_multi_select);
          //  mBuilder.setView(R.layout.common_dialog);
         //   mBuilder.setView(R.layout._searchbar_custom);
            mBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }
    }
    public void SetDoctor2(List<DoctorListViewModel> aList, String tourDate, int empId) {

        ArrayList<Integer> mUserItems_Sample = new ArrayList<>();
        if (aList != null) {
            listItemProduct_Sample = new String[aList.size()];
            for (int i = 0; i < aList.size(); i++) {
                listItemProduct_Sample[i] = aList.get(i).getDoctorName();
            }
            checkedItems_Sample = new boolean[listItemProduct_Sample.length];
           /* Dialog d = new Dialog(context);
            d.setContentView(R.layout.common_dialog);*/
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            mBuilder.setTitle("Select Doctor");
            // mBuilder.setView(R.layout.common_dialog);
            mBuilder.setMultiChoiceItems(listItemProduct_Sample, checkedItems_Sample, (dialogInterface, position, isChecked) -> {
                if (isChecked) {
                    mUserItems_Sample.add(position);
                } else {
                    mUserItems_Sample.remove((Integer.valueOf(position)));
                }
            });
            mBuilder.setCancelable(false);
            mBuilder.setView(R.layout.searchable_list_dialog);
            mBuilder.setPositiveButton("Done", (dialogInterface, which) -> {
                String item = "";
                aProductListList_Sample.clear();
                try {
                    if (mUserItems_Sample != null) {
                        for (int i = 0; i < mUserItems_Sample.size(); i++) {
                            int pos = mUserItems_Sample.get(i);
                            aProductListList_Sample.add(aList.get(pos));
                        }
                        SaveDoctorTourPlan(empId, tourDate, aProductListList_Sample);
                    }
                } catch (Exception ex) {
                    throw ex;
                }
            });

            mBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }


    }
    public void SaveDoctorTourPlan(int empId, String tourDate, List<DoctorListViewModel> aList) {
        List<DoctorTourPlanMaster> aInfo = new ArrayList<>();
        if (aList != null) {
            for (int i = 0; i < aList.size(); i++) {
                DoctorTourPlanMaster planMaster = new DoctorTourPlanMaster();
                planMaster.setDoctorId(aList.get(i).getDoctorId());
                planMaster.setEmpInfoId(empId);
                planMaster.setTourDate(tourDate);
                aInfo.add(planMaster);
            }

            FinalSave(aInfo);

        }
    }
    public void FinalSave(List<DoctorTourPlanMaster> aInfo) {
        //TODO:Tour Plan Save Api same data multiple time save problem
        ProgressDialog progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Saving.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
  /*      try {
            ApiDoctorCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctorCall.class);
            Call<ResultInfo> call = service.SaveDoctorTourPlan(aInfo);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                    progressDoalog.dismiss();
                    ResultInfo info = response.body();
                    if (info.getSuccess() == true) {

                        new androidx.appcompat.app.AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Doctor Tour Plan Added Successfully")
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
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        ShowErrorMsg();
                    } else {
                        ShowErrorMsg();
                    }
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            String str = ex.toString();
            Log.e("Exception", str);
            ShowErrorMsg();
        }*/


    }


}


