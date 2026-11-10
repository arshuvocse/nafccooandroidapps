package com.saidur.creatrix.unicorn.salessolution.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.saidur.creatrix.unicorn.salessolution.Adapters._doctorMonthSelectRecyclerAdapter;
import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanMasterViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.NormalAdapter.DoctorTourStatusBottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SessionManagement;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorTourPlanActivity extends AppCompatActivity implements Rcv_TourPlanListener, DoctorTourStatusBottomSheetDialog.BottomSheetListener {
    private static final String TAG = "DoctorTourPlanActivity";
    String yearList[] = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ImageView imgaeView;
    private RecyclerView recyclerView;
     _doctorMonthSelectRecyclerAdapter mAdapter;
    ProgressDialog progressDoalog;
    SessionManagement session;
    public TourPlanMasterViewModel aTpMasterData = new TourPlanMasterViewModel();
    private int tourCount = 0;
    private int initCount = 0;
    private int lastPosition;
    private int posdy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_tour_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_custom);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);

        Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);

        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(dataAdapter);
        int yearPos = dataAdapter.getPosition(String.valueOf(year));
        yearSpinner.setSelection(yearPos);

        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        int empId = Integer.parseInt(user.get(SessionManagement.KEY_EmpId));


        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthNameArray);
        dataAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(dataAdapterMonth);
        String monName = monthNameArray[month];
        int monthPos = dataAdapterMonth.getPosition(monName);
        monthSpinner.setSelection(monthPos);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<MonthDate> aMondateList = new ArrayList<>();
                int yearV = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                int monthV = i + 1;
                aMondateList = printDatesInMonth(yearV, i);
                GetTourPlanDataFromServer(aMondateList, monthV, yearV, empId);
                initCount++;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (initCount > 0) {
                    List<MonthDate> aMondateList = new ArrayList<>();
                    int monthV = monthSpinner.getSelectedItemPosition();
                    int yearV = Integer.parseInt(yearList[position].toString());
                    aMondateList = printDatesInMonth(yearV, (monthV + 1));
                    GetTourPlanDataFromServer(aMondateList, (monthV + 1), yearV, empId);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextView statusClick = findViewById(R.id.statusClick);
        statusClick.setOnClickListener(v -> {
            int monthValue = monthSpinner.getSelectedItemPosition();
            String monthTxt = monthSpinner.getSelectedItem().toString();
            Integer year1 = Integer.parseInt(yearSpinner.getSelectedItem().toString());
            Bundle args = new Bundle();
            args.putInt("empId", empId);
            args.putInt("monthValue", (monthValue + 1));
            args.putInt("year", year1);
            args.putInt("tourCount", tourCount);
            args.putString("monthTxt", monthTxt);


            DoctorTourStatusBottomSheetDialog bottomSheetDialog = new DoctorTourStatusBottomSheetDialog();
            bottomSheetDialog.setArguments(args);
            bottomSheetDialog.show(getSupportFragmentManager(), "TourBottomSheetStatus");
        });
    }


    public void SetInRecyclerview(List<MonthDate> aMondateList) {

        mAdapter = new _doctorMonthSelectRecyclerAdapter(aMondateList, this,this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter.notifyDataSetChanged();

        //retrieve last position on start
       /* SharedPreferences getPrefs = this.getSharedPreferences("position", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = getPrefs.edit();
        editor.commit();
        posdy = getPrefs.getInt("lastPos", 0);
        recyclerView.scrollToPosition(posdy);*/

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //lastPosition = layoutManager.findLastVisibleItemPosition();
             //   Toast.makeText(DoctorTourPlanActivity.this, "Last "+lastPosition, Toast.LENGTH_SHORT).show();
                Toast.makeText(DoctorTourPlanActivity.this, "Fast "+layoutManager.findFirstVisibleItemPosition(), Toast.LENGTH_SHORT).show();
                Toast.makeText(DoctorTourPlanActivity.this, "dy "+dy, Toast.LENGTH_SHORT).show();
                posdy=dy;
            }
        });

    }
    public List<MonthDate> printDatesInMonth(int year, int month) {
        List<MonthDate> aMondateList = new ArrayList<>();
        SimpleDateFormat dateV = new SimpleDateFormat("dd", Locale.US);
        SimpleDateFormat nameV = new SimpleDateFormat("EEE", Locale.US);
        SimpleDateFormat fmt2 = new SimpleDateFormat("EEE, dd MMM", Locale.US);
        SimpleDateFormat fmt2d = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < daysInMonth; i++) {
            MonthDate monthDate = new MonthDate();
            monthDate.setMonthName(fmt2.format(cal.getTime()));
            monthDate.setDateValue(fmt2d.format(cal.getTime()));
            monthDate.setDateV(Integer.parseInt(dateV.format(cal.getTime())));
            monthDate.setMonthV(month + 1);
            monthDate.setYearV(year);
            aMondateList.add(monthDate);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return aMondateList;

    }
    public void GetTourPlanDataFromServer(List<MonthDate> aMondateList, int month, int year, int empId) {

        progressDoalog = new ProgressDialog(DoctorTourPlanActivity.this);
        progressDoalog.setMessage("Loading.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<TourPlanViewModel>> call = service.GetDoctorTourPlanData(month, year, empId);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<List<TourPlanViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<TourPlanViewModel>> call, @NonNull Response<List<TourPlanViewModel>> response) {
                    progressDoalog.dismiss();
                    ArrangeList(aMondateList, response.body());
                }

                @Override
                public void onFailure(@NonNull Call<List<TourPlanViewModel>> call, @NonNull Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                    } else {

                    }

                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
        }

    }
    public void ArrangeList(List<MonthDate> aMondateList, List<TourPlanViewModel> aTpLIst) {

        if (aTpLIst != null) {
            tourCount = aTpLIst.size();

            List<MonthDate> aSetList = new ArrayList<>();
            for (int i = 0; i < aMondateList.size(); i++) {

                List<TourPlanViewModel> aB = new ArrayList<>();
                for (int j = 0; j < aTpLIst.size(); j++) {
                    String baseDate = aMondateList.get(i).getDateValue();
                    String apiDate = aTpLIst.get(j).getTourPlanDate();
                    Toast.makeText(this, "baseDate "+baseDate, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "apiDate "+apiDate, Toast.LENGTH_SHORT).show();
                    aMondateList.get(i).setFinalSubmit(aTpLIst.get(j).isFinalSubmit());
                    if (baseDate.equals(apiDate)) {
                        aB.add(aTpLIst.get(j));

                    }
                }

                aSetList.add(aMondateList.get(i));
                aSetList.get(i).setaTpViewList(aB);
            }

            SetInRecyclerview(aSetList);


        } else {
            SetInRecyclerview(aMondateList);
        }


    }
    @Override
    public void ReloadCurrentActivity() {
      /*  SharedPreferences getPrefs = this.getSharedPreferences("position", this.MODE_PRIVATE);
        SharedPreferences.Editor e = getPrefs.edit();
        e.commit();
        e.putInt("lastPos", posdy);
        e.apply();*/
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
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
            progressDoalog = new ProgressDialog(DoctorTourPlanActivity.this);
            progressDoalog.setMessage("Final Submit Processing....");
            progressDoalog.show();
            progressDoalog.setCanceledOnTouchOutside(false);
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResultInfo> call = service.UpdateDoctorTourPlan(empId, month, year, remarks);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                    progressDoalog.dismiss();
                    ResultInfo info = response.body();
                    if (info.getSuccess() == true) {

                        new AlertDialog.Builder(DoctorTourPlanActivity.this)
                                .setTitle("Success")
                                .setMessage("Final Doctor Visit Plan submission done for selected month")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent mIntent = getIntent();
                                        finish();
                                        startActivity(mIntent);
                                    }

                                }).setCancelable(false).show();

                    }
                }

                @Override
                public void onFailure(Call<ResultInfo> call, Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        Toast.makeText(DoctorTourPlanActivity.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DoctorTourPlanActivity.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();

                    }
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            Toast.makeText(DoctorTourPlanActivity.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save position in sharedpreferenses on destroy
        // SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        /*SharedPreferences getPrefs = this.getSharedPreferences("position", this.MODE_PRIVATE);
        SharedPreferences.Editor e = getPrefs.edit();
        e.commit();
        e.putInt("lastPos", posdy);
        e.apply();*/
    }

}