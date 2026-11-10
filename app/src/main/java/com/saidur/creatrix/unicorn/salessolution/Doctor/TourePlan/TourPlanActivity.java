package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.ActiveListener;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITourplan;
import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanMasterViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.TourApiCall;
import com.saidur.creatrix.unicorn.salessolution.NormalAdapter.TourStatusBottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Presentar.TourPlanPresenter;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.Service.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SessionManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTourPlan2Binding;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourPlanActivity extends AppCompatActivity implements Rcv_TourPlanListener, ITourplan.View, TourStatusBottomSheetDialog.BottomSheetListener {
    String yearList[] = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ActivityTourPlan2Binding binding;
    ActiveListener activeListener;
    TourPlanPresenter tppresenter;
    // private _monthdate_Adapter mAdapter;
    private TourPlanListAdapter mAdapter;
    ProgressDialog progressDoalog;
    SessionManagement session;
    public TourPlanMasterViewModel aTpMasterData = new TourPlanMasterViewModel();
    private int tourCount = 0;
    private int initCount = 0;
    boolean is_Entry = false;

    int yearPos,monthPos,empId;
    int selectedYearpos = 0, selectedMonPos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTourPlan2Binding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_tour_plan2);
        setContentView(binding.getRoot());
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        empId = Integer.parseInt(user.get(SessionManagement.KEY_EmpId));
        tppresenter = new TourPlanPresenter(this, TourPlanActivity.this);
        progressDoalog = new ProgressDialog(TourPlanActivity.this);
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.refresh.setOnClickListener(view ->CallMasterApi(empId));
        CallMasterApi(empId);
        binding.statusClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int monthValue = binding.monthSpinner.getSelectedItemPosition();
                String monthTxt = binding.monthSpinner.getSelectedItem().toString();
                Integer year = Integer.parseInt(binding.yearSpinner.getSelectedItem().toString());
                Bundle args = new Bundle();
                args.putInt("empId", empId);
                args.putInt("monthValue", (monthValue + 1));
                args.putInt("year", year);
                args.putInt("tourCount", tourCount);
                args.putString("monthTxt", monthTxt);

                TourStatusBottomSheetDialog bottomSheetDialog = new TourStatusBottomSheetDialog();
                bottomSheetDialog.setArguments(args);
                bottomSheetDialog.show(getSupportFragmentManager(), "TourBottomSheetStatus");
            }
        });
    }

    private void CallMasterApi(int empid) {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        progressDoalog.setMessage("Tour Plan Loading....");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpinner.setAdapter(dataAdapter);
        int yearPos = dataAdapter.getPosition(String.valueOf(year));
        selectedYearpos=yearPos;
        binding.yearSpinner.setSelection(yearPos);
        /*   if(Constants.current_selected_year_pos==-1)
        {
            yearPos = dataAdapter.getPosition(String.valueOf(year));
            Constants.current_selected_year_pos=yearPos;

        }else {
            yearPos=Constants.current_selected_year_pos;
        }
        binding.yearSpinner.setSelection(yearPos);*/

        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthNameArray);
        dataAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthSpinner.setAdapter(dataAdapterMonth);
        String monName = monthNameArray[month];
        int monthPos = dataAdapterMonth.getPosition(monName);
        binding.monthSpinner.setSelection(monthPos);

       /*  if(Constants.current_selected_month_pos==-1)
        {
            monthPos = dataAdapterMonth.getPosition(monName);
            Constants.current_selected_month_pos=monthPos;
        }else {
            monthPos=Constants.current_selected_month_pos;
        }*/
        binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // List<MonthDate> aMondateList = new ArrayList<>();
                List<MonthDate> aMondateList= new ArrayList<>();
                int yearV = Integer.parseInt(binding.yearSpinner.getSelectedItem().toString());
                int monthV = i + 1;
                selectedMonPos=i;
                aMondateList = printDatesInMonth(yearV, i);
                Constants.current_selected_month=monthV;
                Constants.current_selected_year=yearV;
                Constants.current_selected_month_pos=i;
                GetTourPlanMasterData(monthV, yearV, empid, aMondateList, is_Entry);
                //   tppresenter.getTourPlanDataByEmpId(aMondateList, finalsubmit, monthV, yearV, empId);
                initCount++;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        binding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initCount > 0) {
                    selectedYearpos=position;
                    int monthV = binding.monthSpinner.getSelectedItemPosition();
                    int yearV = Integer.parseInt(yearList[position].toString());
                    List<MonthDate> aMondateList= printDatesInMonth(yearV, (monthV + 1));
                    Constants.current_selected_month=monthV+1;
                    Constants.current_selected_year=yearV;
                    Constants.current_selected_year_pos=position;
                    GetTourPlanMasterData((monthV+1), yearV, empid, aMondateList, is_Entry);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void SetInRecyclerview(List<MonthDate> aMondateList, boolean is_Entry) {
        if(progressDoalog!=null||progressDoalog.isShowing())
        {
            progressDoalog.dismiss();
        }
        mAdapter = new TourPlanListAdapter(TourPlanActivity.this, aMondateList, is_Entry);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TourPlanActivity.this);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setItemAnimator(null);
        binding.recyclerView.scrollToPosition(0);
        mAdapter.notifyDataSetChanged();

    }
    public List<MonthDate> printDatesInMonth(int year, int month) {
        List<MonthDate> aMondateList = new ArrayList<>();
        SimpleDateFormat dateV = new SimpleDateFormat("dd", Locale.US);
        SimpleDateFormat nameV = new SimpleDateFormat("EEE", Locale.US);
        SimpleDateFormat fmt2 = new SimpleDateFormat("EEE, dd MMM", Locale.US);
        SimpleDateFormat fmtdate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < daysInMonth; i++) {
            MonthDate monthDate = new MonthDate();
            monthDate.setMonthName(fmt2.format(cal.getTime()));
            monthDate.setDateValue(fmtdate.format(cal.getTime()));
            monthDate.setDateV(Integer.parseInt(dateV.format(cal.getTime())));
            monthDate.setDateName(nameV.format(cal.getTime()));
            monthDate.setMonthV(month + 1);
            monthDate.setYearV(year);
            aMondateList.add(monthDate);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return aMondateList;

    }
    @Override
    public void ReloadCurrentActivity() {
       /* Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);*/
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
    @Override
    public void FinalSubmitClick(int month, int year, int empId, String remarks) {
        try {
            progressDoalog = new ProgressDialog(TourPlanActivity.this);
            progressDoalog.setMessage("Final Submit Processing....");
            progressDoalog.show();
            progressDoalog.setCanceledOnTouchOutside(false);
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<ResultInfo> call = service.UpdateTourFinalSubmit(empId, month, year, remarks);
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResultInfo> call, @NonNull Response<ResultInfo> response) {
                    if(progressDoalog!=null ||progressDoalog.isShowing())
                    {
                        progressDoalog.dismiss();
                    }
                    ResultInfo info = response.body();
                    if (info.getSuccess() == true) {
                        new AlertDialog.Builder(TourPlanActivity.this)
                                .setTitle("Success")
                                .setMessage("Final Tour Plan submission done for selected month")
                                .setPositiveButton("OK", (dialog, which) -> {
                                   /* Intent mIntent = getIntent();
                                    finish();
                                    startActivity(mIntent);*/
                                    finish();
                                }).setCancelable(false).show();

                    } else {
                        SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Slow Network");
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResultInfo> call, @NonNull Throwable t) {
                    if(progressDoalog!=null ||progressDoalog.isShowing())
                    {
                        progressDoalog.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                        SnackBarManagement._error_CustomMessage(binding.masterLayout, "Slow Internet Connection. Please try again");
                    } else {
                        SnackBarManagement._error_CustomMessage(binding.masterLayout, "Some error occurred. Please try again");
                    }
                }
            });

        } catch (Exception ex) {
            if(progressDoalog!=null ||progressDoalog.isShowing())
            {
                progressDoalog.dismiss();
            }
            SnackBarManagement._error_CustomMessage(binding.masterLayout, "Some error occurred.Please try again");
        }
    }
    @Override
    public void OnTourPlanDataGet(List<TourPlanViewModel> aList) {

    }

    @Override
    public void OnTourPlanDailyDataGet(List<MonthDate> aList) {

    }

    @Override
    public void OnArreangList(List<MonthDate> aMondateList, boolean is_Entry, List<TourPlanViewModel> aTpLIst) {
        binding.recyclerView.setVisibility(View.VISIBLE);
        if (aTpLIst != null) {
            tourCount = aTpLIst.size();
            List<MonthDate> aSetList = new ArrayList<>();
            for (int i = 0; i < aMondateList.size(); i++) {
                List<TourPlanViewModel> aB = new ArrayList<>();
                for (int j = 0; j < aTpLIst.size(); j++) {
                    String baseDate = aMondateList.get(i).getDateValue();
                    String apiDate = aTpLIst.get(j).getTourPlanDate();
                    aMondateList.get(i).setFinalSubmit(aTpLIst.get(j).isFinalSubmit());
                    if (baseDate.equals(apiDate)) {
                        aB.add(aTpLIst.get(j));
                    }
                }
                aSetList.add(aMondateList.get(i));
                aSetList.get(i).setaTpViewList(aB);
            }
            SetInRecyclerview(aSetList, is_Entry);
        } else {
            SetInRecyclerview(aMondateList, is_Entry);
        }
    }

    @Override
    public void OnFailour(String msg) {
        if(progressDoalog!=null||progressDoalog.isShowing())
        {
            progressDoalog.dismiss();
        }
        if (msg.equals("recyclerViewDaylistGone")) {
            binding.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnSuccessTPPDay(String msg) {

    }
    public void GetTourPlanMasterData(int month, int year, int empId, List<MonthDate> aMondateList, boolean is_Entry) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<TourPlanMasterViewModel>> call = service.GetTourPlanMaster(month, year, empId);
            call.enqueue(new Callback<List<TourPlanMasterViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<TourPlanMasterViewModel>> call, @NonNull Response<List<TourPlanMasterViewModel>> response) {

                    if (response.body().size() > 0) {
                        SetinView(response.body(), aMondateList, month, year, empId, is_Entry);

                        System.out.println(response.body().toString());
                        Log.d("MScreen", "onResponse: "+response.body().toString());
                        Log.d("MScreen2", "Response Dta ".toString());
                    } else {
                        boolean is_Entrya=true;
                        tppresenter.getTourPlanDataByEmpId(aMondateList, is_Entrya, month, year, empId);
                        Toast.makeText(TourPlanActivity.this,"Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<TourPlanMasterViewModel>> call, @NonNull Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        Toast.makeText(TourPlanActivity.this,t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                    }
                }
            });

        } catch (Exception ex) {
        }
    }
    public void SetinView(List<TourPlanMasterViewModel> aList, List<MonthDate> aMondateList, int month, int year, int empId, boolean is_Entry) {
        try {
           boolean is_Entrynew=false;
            TourPlanMasterViewModel tpMaster = new TourPlanMasterViewModel();
            tpMaster = aList.get(0);
            if (tpMaster != null) {
                if (tpMaster.isFinalSubmit() == false) {
                    is_Entrynew = true;

                    tppresenter.getTourPlanDataByEmpId(aMondateList, is_Entrynew, month, year, empId);
                    //  mAdapter.notifyDataSetChanged();
                } else {
                    if (tpMaster.getApprovalStatus().equals("0")) {
                        is_Entrynew = false;
                    }
                    if (tpMaster.getApprovalStatus().equals("2")) {
                        is_Entrynew = false;
                    }
                    if (tpMaster.getApprovalStatus().equals("3")) {
                        is_Entrynew = true;
                    }
                    tppresenter.getTourPlanDataByEmpId(aMondateList, is_Entrynew, month, year, empId);
                }
            }

          /*  if (aList != null && aList.size() > 0) {
                tpMaster = aList.get(0);
                if (tpMaster != null) {
                    if (tpMaster.isFinalSubmit() == false) {
                        is_Entry = true;
                        //  mAdapter.notifyDataSetChanged();

                    } else {
                        if (tpMaster.getApprovalStatus().equals("0")) {
                            is_Entry = false;
                        }
                        if (tpMaster.getApprovalStatus().equals("2")) {
                            is_Entry = false;
                        }
                        if (tpMaster.getApprovalStatus().equals("3")) {
                            is_Entry = true;
                        }
                    }
                }

            } else {
              *//*  is_Entry = true;
                tppresenter.getTourPlanDataByEmpId(aMondateList, is_Entry, month, year, empId);*//*
                //mAdapter.notifyDataSetChanged();
            }*/

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Constants.current_selected_year_pos=-1;
        Constants.current_selected_month_pos=-1;
      //  Toast.makeText(this, "On Destroy", Toast.LENGTH_SHORT).show();
    }




    @Override
    protected void onRestart() {
        super.onRestart();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpinner.setAdapter(dataAdapter);
        //int yearPos = dataAdapter.getPosition(String.valueOf(Constants.current_selected_year));
        binding.yearSpinner.setSelection(selectedYearpos);

        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthNameArray);
        dataAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthSpinner.setAdapter(dataAdapterMonth);
        binding.monthSpinner.setSelection(selectedMonPos);

        binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // List<MonthDate> aMondateList = new ArrayList<>();
                List<MonthDate> aMondateList= new ArrayList<>();
                int yearV = Integer.parseInt(binding.yearSpinner.getSelectedItem().toString());
                int monthV = i + 1;

                aMondateList = printDatesInMonth(yearV, i);
                Constants.current_selected_month=monthV;
                Constants.current_selected_year=yearV;
                Constants.current_selected_month_pos=i;
                GetTourPlanMasterData(monthV, yearV, empId, aMondateList, is_Entry);
                //   tppresenter.getTourPlanDataByEmpId(aMondateList, finalsubmit, monthV, yearV, empId);
                initCount++;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        binding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initCount > 0) {

                    int monthV = binding.monthSpinner.getSelectedItemPosition();
                    int yearV = Integer.parseInt(yearList[position].toString());
                    List<MonthDate> aMondateList= printDatesInMonth(yearV, (monthV + 1));
                    Constants.current_selected_month=monthV+1;
                    Constants.current_selected_year=yearV;
                    Constants.current_selected_year_pos=position;
                    GetTourPlanMasterData((monthV+1), yearV, empId, aMondateList, is_Entry);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}