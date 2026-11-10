package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Attendance;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterReportAttendance;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelReportAttendance;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityRptAttendanceBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityRpt_Attendance extends AppCompatActivity {
    ActivityRptAttendanceBinding binding;
    AdapterReportAttendance adapter;
    DBCrudHelper dbCrudHelper;
    ModelSession session;
    String empId,role;
    ProgressDialog pd;
    BottomSheetDialog bsheetdlg;
    FilterMasterBinding ftm;
    int selectedMonth, selectedyear;
    String selectedTyp = "";
    List<ModelMBE> mbeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRptAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());

        dbCrudHelper = new DBCrudHelper(ActivityRpt_Attendance.this);
        session = dbCrudHelper.getSession();
        empId = String.valueOf(session.getEmpInfoId());
        role = session.getEmpRole();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        HashMap<String, String> filter = new HashMap<>();
        filter.put("empId", empId);
        filter.put("Month", String.valueOf(month + 1));
        filter.put("Year", String.valueOf(year));
        LoadSelfData(filter);
        binding.swipAttenreport.setOnRefreshListener(() -> {
            HashMap<String, String> filters = new HashMap<>();
            filters.put("empId", empId);
            filters.put("Month", String.valueOf(month + 1));
            filters.put("Year", String.valueOf(year));
            LoadSelfData(filters);
            binding.swipAttenreport.setRefreshing(false);
        });
        openFilter();
        binding.cfilter.setOnClickListener(v -> {
            bsheetdlg.show();
        });
    }

    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(ActivityRpt_Attendance.this);
        bsheetdlg.setContentView(ftm.getRoot());
        bsheetdlg.setCanceledOnTouchOutside(true);

        switch (role) {
            case "MBE":
                ftm.linearLayoutemp.setVisibility(GONE);
                break;
            case "MIO":
            case "AM":
            case "DZSM":
            case "NSM":
                ftm.linearLayoutemp.setVisibility(VISIBLE);
                break;
        }
        ftm.filterStatusLayout.getRoot().setVisibility(GONE);
        ftm.filterTypeLayout.tilDoctype.setVisibility(View.GONE);
        ftm.filterTypeLayout.tilProgtypetype.setVisibility(View.GONE);
        ftm.filterMonthYearLayout.getRoot().setVisibility(GONE);

        List<String> emptype = new ArrayList<>();
        switch (role) {
            case "MIO":
                emptype.add("Select");
                emptype.add("MBE");
                try {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityRpt_Attendance.this, android.R.layout.simple_spinner_item, emptype);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ftm.spinnerEmployeeType.setAdapter(dataAdapter);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case "AM":
                emptype.add("Select");
                emptype.add("MBE");
                emptype.add("ABM");
                try {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityRpt_Attendance.this, android.R.layout.simple_spinner_item, emptype);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ftm.spinnerEmployeeType.setAdapter(dataAdapter);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case "DZSM":
                emptype.add("Select");
                emptype.add("MBE");
                emptype.add("ABM");
                emptype.add("RBM");
                ArrayAdapter<String> dataAdapterrsm = new ArrayAdapter<>(ActivityRpt_Attendance.this, android.R.layout.simple_spinner_item, emptype);
                dataAdapterrsm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.spinnerEmployeeType.setAdapter(dataAdapterrsm);
                break;
            case "NSM":
                emptype.add("Select");
                emptype.add("MBE");
                emptype.add("ABM");
                emptype.add("RBM");
                emptype.add("Cluster Head");
                ArrayAdapter<String> dataAdapternsm = new ArrayAdapter<>(ActivityRpt_Attendance.this, android.R.layout.simple_spinner_item, emptype);
                dataAdapternsm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.spinnerEmployeeType.setAdapter(dataAdapternsm);
                break;
        }
        ftm.spinnerEmployeeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTyp = String.valueOf(ftm.spinnerEmployeeType.getSelectedItem());
                switch (selectedTyp) {
                    case "Select":
                        try {
                            ftm.mbelay.setVisibility(GONE);
                            ftm.miolay.setVisibility(View.GONE);
                            ftm.asmlay.setVisibility(View.GONE);
                            ftm.rsmlay.setVisibility(View.GONE);

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case "MBE":
                        try {
                            ftm.mbelay.setVisibility(VISIBLE);
                            ftm.miolay.setVisibility(View.GONE);
                            ftm.asmlay.setVisibility(View.GONE);
                            ftm.rsmlay.setVisibility(View.GONE);
                            try {
                                mbeList = dbCrudHelper.getMBEList_SQLite();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            if (mbeList != null) {
                                System.out.println("mlist " + mbeList);
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(ActivityRpt_Attendance.this, android.R.layout.simple_spinner_item, mbeList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalMbe.setAdapter(dataAdapter);
                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "MBE Not Found!!");
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case "ABM":
                        try {
                            ftm.miolay.setVisibility(View.VISIBLE);
                            ftm.mbelay.setVisibility(View.GONE);
                            ftm.asmlay.setVisibility(View.GONE);
                            ftm.rsmlay.setVisibility(View.GONE);
                            List<ModelMIO> mioList = null;
                            try {
                                mioList = dbCrudHelper.getMIOList_SQLite();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            if (mioList != null) {
                                System.out.println("mlist " + mioList);
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(ActivityRpt_Attendance.this, android.R.layout.simple_spinner_item, mioList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalMio.setAdapter(dataAdapter);
                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "No MIO Found!!");
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case "RBM":
                        try {
                            ftm.asmlay.setVisibility(View.VISIBLE);
                            ftm.mbelay.setVisibility(View.GONE);
                            ftm.miolay.setVisibility(View.GONE);
                            ftm.rsmlay.setVisibility(View.GONE);
                            List<ModelASM> asmList = null;
                            try {
                                asmList = dbCrudHelper.getASMList_SQLite();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            if (asmList != null) {
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(ActivityRpt_Attendance.this, android.R.layout.simple_spinner_item, asmList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalAsm.setAdapter(dataAdapter);

                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "No ABM Founded!!");
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case "Cluster Head":
                        try {
                            ftm.rsmlay.setVisibility(View.VISIBLE);
                            ftm.mbelay.setVisibility(View.GONE);
                            ftm.miolay.setVisibility(View.GONE);
                            ftm.asmlay.setVisibility(View.GONE);
                            List<ModelRSM> rsmList = null;
                            try {
                                rsmList = dbCrudHelper.getRSMList_SQLite();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            if (rsmList != null) {
                                ArrayAdapter<ModelRSM> dataAdapter = new ArrayAdapter<>(ActivityRpt_Attendance.this, android.R.layout.simple_spinner_item, rsmList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalRsm.setAdapter(dataAdapter);
                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "No RBM Founded!!");
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ftm.filterMonthYearLayout.ivDatePickerFromDate.setVisibility(GONE);
        ftm.filterMonthYearLayout.ivDatePickerToDate.setVisibility(GONE);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        selectedyear = year;
        int month = c.get(Calendar.MONTH);
        selectedMonth = month;
        ftm.llmonthyear.setVisibility(VISIBLE);
        ftm.ivmypicker.setOnClickListener(v -> MonthPicker());
        ftm.montyer.setText(UtilityHelper.monthNameArrayFull[month] + ',' + year);
        ftm.filterTopLayout.btnDonefilter.setOnClickListener(v -> {
            try {
                switch (selectedTyp) {
                    case "Select":
                        empId = String.valueOf(session.getEmpInfoId());
                        break;
                    case "MBE":
                        ModelMBE mbeempid;
                        mbeempid = (ModelMBE) ftm.spinnerApprovalMbe.getSelectedItem();
                        empId = String.valueOf(mbeempid.getMBEEmpId());
                        break;
                    case "ABM":
                        ModelMIO mioempid;
                        mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
                        empId = String.valueOf(mioempid.getMIOEmpId());
                        break;
                    case "RBM":
                        ModelASM amempid = (ModelASM) ftm.spinnerApprovalAsm.getSelectedItem();
                        empId = String.valueOf(amempid.getASMEmpId());
                        break;
                    case "Cluster Head":
                        ModelRSM dzsmempid = (ModelRSM) ftm.spinnerApprovalRsm.getSelectedItem();
                        empId = String.valueOf(dzsmempid.getRSMEmpId());
                        break;
                }
                HashMap<String, String> filters = new HashMap<>();
                filters.put("empId", empId);
                filters.put("Month", String.valueOf(selectedMonth + 1));
                filters.put("Year", String.valueOf(selectedyear));
                Gson gson = new Gson();
                String data = gson.toJson(filters);
                System.out.println("Atten Report : "+data);
                LoadSelfData(filters);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            bsheetdlg.cancel();
        });
        ftm.filterTopLayout.ivCancel.setOnClickListener(v -> {
            bsheetdlg.cancel();
        });
    }

    public void MonthPicker() {
        int yearSelected;
        int monthSelected;
        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);
        MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                .getInstance(monthSelected, yearSelected);
        dialogFragment.show(ActivityRpt_Attendance.this.getSupportFragmentManager(), null);
        dialogFragment.setOnDateSetListener((year, monthOfYear) -> {
            ftm.montyer.setText("");
            ftm.montyer.setText(UtilityHelper.monthNameArrayFull[monthOfYear] + ',' + year);
            selectedMonth = monthOfYear;
            selectedyear = year;
        });
    }

    private void LoadSelfData(HashMap<String, String> filter) {
        if (NetworkInformation.isConnected(ActivityRpt_Attendance.this)) {
            try {
                pd = new ProgressDialog(ActivityRpt_Attendance.this);
                pd.setMessage("Report Loading....");
                pd.show();
                pd.setCanceledOnTouchOutside(false);

                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<ModelReportAttendance>> call = service.GetAttendanceReport(filter);
                call.enqueue(new Callback<List<ModelReportAttendance>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelReportAttendance>> call, @NonNull Response<List<ModelReportAttendance>> response) {
                        if (pd != null || pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.body() != null) {

                            List<ModelReportAttendance> data = response.body();
                            LoadRV(data);
                        } else {
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Report Data Not Found!!");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelReportAttendance>> call, @NonNull Throwable t) {
                        if (pd != null || pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (t instanceof SocketTimeoutException) {
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Slow Connection Detected. Please try again");
                        }
                    }
                });

            } catch (Exception ex) {
                if (pd != null || pd.isShowing()) {
                    pd.dismiss();
                }
                String str = ex.toString();
                Log.e("Exception", str);
            }
        } else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet");
        }
    }
    private void LoadRV(List<ModelReportAttendance> data) {
        if (data != null) {
            adapter = new AdapterReportAttendance(data, ActivityRpt_Attendance.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityRpt_Attendance.this);
            binding.rvAttendance.setLayoutManager(mLayoutManager);
            binding.rvAttendance.setItemAnimator(new DefaultItemAnimator());
            binding.rvAttendance.setAdapter(adapter);
            binding.rvAttendance.setItemAnimator(null);
            binding.rvAttendance.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        }
    }
}