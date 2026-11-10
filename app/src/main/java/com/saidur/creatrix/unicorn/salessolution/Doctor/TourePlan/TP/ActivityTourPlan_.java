package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TP;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanMasterViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.TourApiCall;
import com.saidur.creatrix.unicorn.salessolution.NormalAdapter.TourStatusBottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Service.Constants;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.AddCustomerActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTourPlan2Binding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTourPlanBinding;

import java.time.LocalDate;
import java.net.SocketTimeoutException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTourPlan_ extends AppCompatActivity implements ITP.View,TourStatusBottomSheetDialog.BottomSheetListener,
        TPDetailsBottomSheetDialog.TPDetailsListener{
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ActivityTourPlanBinding binding;
    PresenterTP presenterTP;
    ModelSession session;
    int empId;
    String roleType;
    ProgressDialog pd;
    TPAdapter mAdapter;
    int currMonth = 0, currDay = 0, currYear = 0;
    private int tourCount = 0;
    private int initCount = 0;
    boolean isFinalSubmit=false;
    DBCrudHelper dbCrudHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTourPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper = new DBCrudHelper(ActivityTourPlan_.this);
        session=dbCrudHelper.getSession();
          empId = session.getEmpInfoId();
          roleType = session.getRoleType();
        presenterTP = new PresenterTP(this, ActivityTourPlan_.this);
        pd = new ProgressDialog(ActivityTourPlan_.this);
//        session = new SessionManagement(getApplicationContext());
//        HashMap<String, String> user = session.getUserDetails();
//        empId = Integer.parseInt(user.get(SessionManagement.KEY_EmpId));
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int month = 0;
        int year = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            month = localDate.getMonthValue();
            year = localDate.getYear();
        }
        currMonth = month;
        currYear = year;
        getMaster(month, year, empId);

        binding.statusClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monName = monthNameArray[currMonth-1];

                Bundle args = new Bundle();
                args.putInt("empId", empId);
                args.putInt("monthValue", currMonth);
                args.putInt("year", currYear);
                args.putInt("tourCount", tourCount);
                args.putString("monthTxt", monName);

                TourStatusBottomSheetDialog bottomSheetDialog = new TourStatusBottomSheetDialog();
                bottomSheetDialog.setArguments(args);
                bottomSheetDialog.show(getSupportFragmentManager(), "TourBottomSheetStatus");
            }
        });
        initCalnder();
    }

    private void openActivity(String formattedDate, String month, String year) {
        Intent in = new Intent(this, ActivityTPDetails.class);
        in.putExtra("formattedDate", formattedDate);
        in.putExtra("Month", month);
        in.putExtra("Year", year);
        startActivity(in);
    }

    private void initCalnder() {
        final Calendar nextYear = Calendar.getInstance();

        nextYear.add(Calendar.YEAR, 10);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -10);

        binding.calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2021, 1, 1))
                .setMaximumDate(CalendarDay.from(2030, 12, 30))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        binding.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            String dates = String.valueOf(date.getYear()) + "-" + String.valueOf(date.getMonth()) + "-" +String.valueOf(date.getDay());
            String datez = String.valueOf(date.getMonth()) + "-" + String.valueOf(date.getDay()) + "-" + String.valueOf(date.getYear());
            currMonth = date.getMonth();
            currYear = date.getYear();
            currDay = date.getDay();
            if(!isFinalSubmit)
            {
                openTPDetails(dates,datez, String.valueOf(date.getMonth()), String.valueOf(date.getYear()),isFinalSubmit);
            }
        });
        binding.calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                currMonth = date.getMonth();
                currYear = date.getYear();
                currDay = date.getDay();
                getMaster(date.getMonth(), date.getYear(), empId);
            }
        });
    }

    private void openTPDetails(String dates, String datez, String mon, String yr, boolean isFinalSubmit) {
        Constants.From="ActivityTourPlan_";

        Bundle args = new Bundle();
        args.putString("formattedDate", dates);
        args.putString("datez", datez);
        args.putString("Month", String.valueOf(mon));
        args.putString("Year", String.valueOf(yr));
        args.putBoolean("isFinalSubmit", isFinalSubmit);

        //TPDetailsBottomSheetDialog tpbd = new TPDetailsBottomSheetDialog();
        TPDetailsBottomSheetDialog tpbd =  TPDetailsBottomSheetDialog.newInstance();
        tpbd.setArguments(args);
        tpbd.show(getSupportFragmentManager(), tpbd.getTag());

    }

    public void getMaster(int month, int year, int empId) {
        pd.setMessage("Please wait...");
        pd.show();
        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<List<TourPlanMasterViewModel>> call = service.GetTourPlanMaster(month, year, empId);
            call.enqueue(new Callback<List<TourPlanMasterViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<TourPlanMasterViewModel>> call, @NonNull Response<List<TourPlanMasterViewModel>> response) {

                    if (response.body().size() > 0) {
                        SetMasterView(response.body(), month, year, empId);

                        System.out.println(response.body().toString());
                        Log.d("MScreen", "onResponse: " + response.body().toString());
                        Log.d("MScreen2", "Response Dta ".toString());
                    } else {
                        isFinalSubmit = false;
                        presenterTP.getTourPlanDataByEmpId(month, year, empId);
                        Toast.makeText(ActivityTourPlan_.this, "Not Found", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<TourPlanMasterViewModel>> call, @NonNull Throwable t) {
                    if(pd!=null || pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                        Toast.makeText(ActivityTourPlan_.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(ActivityTourPlan_.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }

    }

    public void SetMasterView(List<TourPlanMasterViewModel> aList, int month, int year, int empId) {
        try {
            TourPlanMasterViewModel tpMaster = new TourPlanMasterViewModel();
            tpMaster = aList.get(0);
            if (tpMaster != null) {
                if (tpMaster.isFinalSubmit() == false) {
                    isFinalSubmit = false;
                    presenterTP.getTourPlanDataByEmpId(month, year, empId);
                    mAdapter.notifyDataSetChanged();
                } else {
                    if (tpMaster.getApprovalStatus().equals("0")) {
                        isFinalSubmit = false;
                    }
                    if (tpMaster.getApprovalStatus().equals("1")) {
                        isFinalSubmit = true;
                    }
                    if (tpMaster.getApprovalStatus().equals("2")) {
                        isFinalSubmit = true;
                    }
                    if (tpMaster.getApprovalStatus().equals("3")) {
                        isFinalSubmit = false;
                    }
                    presenterTP.getTourPlanDataByEmpId(month, year, empId);
                }
            }


        } catch (Exception exception) {
        }
    }

    @Override
    public void OnTourPlanDataGet(List<TourPlanViewModel> aList) {
        tourCount = aList.size();
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        if (aList.size() == 0 || aList == null) {
            binding.noData.setVisibility(View.VISIBLE);
        } else {
            binding.noData.setVisibility(View.GONE);
        }
        mAdapter = new TPAdapter(ActivityTourPlan_.this, aList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityTourPlan_.this);
        binding.rvPlanlist.setLayoutManager(mLayoutManager);
        binding.rvPlanlist.setItemAnimator(new DefaultItemAnimator());
        binding.rvPlanlist.setAdapter(mAdapter);
        binding.rvPlanlist.setItemAnimator(null);
        binding.rvPlanlist.scrollToPosition(0);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void OnTourPlanDailyDataGet(List<TourPlanViewModel> aList,boolean a) {
    }

    @Override
    public void OnFailour(String msg) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        if (msg.equals("recyclerViewDaylistGone")) {
            binding.rvPlanlist.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnSuccessTPPDay(String msg) {
    }

    @Override
    public void FinalSubmitClick(int month, int year, int empId, String remarks) {
        try {
            pd = new ProgressDialog(ActivityTourPlan_.this);
            pd.setMessage("Final Submit Processing....");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<ResultInfo> call = service.UpdateTourFinalSubmit(empId, month, year, remarks);
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResultInfo> call, @NonNull Response<ResultInfo> response) {
                    if(pd!=null ||pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    ResultInfo info = response.body();
                    if (info.getSuccess() == true) {
                        new AlertDialog.Builder(ActivityTourPlan_.this)
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
                    if(pd!=null ||pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                        SnackBarManagement._error_CustomMessage(binding.masterLayout, "Slow Internet Connection. Please try again");
                    } else {
                        SnackBarManagement._error_CustomMessage(binding.masterLayout, "Some error occurred. Please try again");
                    }
                }
            });

        } catch (Exception ex) {
            if(pd!=null ||pd.isShowing())
            {
                pd.dismiss();
            }
            SnackBarManagement._error_CustomMessage(binding.masterLayout, "Some error occurred.Please try again");
        }
    }

    @Override
    public void DataSaved(int month, int year, int empId, String remarks) {
        getMaster(month, year, empId);
    }
}