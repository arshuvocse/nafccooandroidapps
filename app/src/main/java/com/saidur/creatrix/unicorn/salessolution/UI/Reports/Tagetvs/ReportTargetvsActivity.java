package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Tagetvs;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toolbar;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelTarget;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Tagetvs.Adapter.AdapterAchive;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityReportTargetvsBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportTargetvsActivity extends AppCompatActivity {
    ActivityReportTargetvsBinding binding;
    ComponentLoadingbarBinding lbinding;
    boolean isValueSelected = true, isQtySelected = false;
    Dialog pd_process;
    int selectedMonth, selectedyear;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    BottomSheetDialog bsheetdlg;
    String role, empId;
    FilterMasterBinding ftm;
    String selectedTyp = "";
    List<ModelMBE> mbeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportTargetvsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(view -> {
            finish();
        });
        lbinding = ComponentLoadingbarBinding.inflate(getLayoutInflater());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        dbCrudHelper = new DBCrudHelper(ReportTargetvsActivity.this);
        session = dbCrudHelper.getSession();
        empId = String.valueOf(session.getEmpInfoId());
        role = session.getRoleType();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        pd_process = new Dialog(ReportTargetvsActivity.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;

        binding.montyer.setText(UtilityHelper.monthNameArrayFull[month] + '-' + year);
        HashMap<String, String> map = new HashMap<>();
        map.put("empId", String.valueOf(session.getEmpInfoId()));
        map.put("month", String.valueOf(month + 1));
        map.put("year", String.valueOf(year));
        map.put("Type", "Value");
        System.out.println(map);
        pd_process.show();
        pd_process.setCancelable(false);
        callapi(map);

        binding.btnValue.setOnClickListener(view -> {
            binding.btnValue.setBackgroundResource(R.drawable.shape_rect);
            binding.btnQty.setBackgroundResource(0);
            binding.tvAchiveValue.setText("Sales Value");
            binding.btnQty.setTextColor(Color.parseColor("#000000"));
            binding.btnValue.setTextColor(Color.parseColor("#FFFFFF"));
            isValueSelected = true;
            isQtySelected = false;

            binding.montyer.setText(UtilityHelper.monthNameArrayFull[month] + '-' + year);
            pd_process.show();
            pd_process.setCancelable(false);
            HashMap<String, String> mapValue = new HashMap<>();
            mapValue.put("empId", String.valueOf(session.getEmpInfoId()));
            mapValue.put("month", String.valueOf(month + 1));
            mapValue.put("year", String.valueOf(year));
            mapValue.put("Type", "Value");

            System.out.println(mapValue);
            callapi(mapValue);
        });
        binding.btnQty.setOnClickListener(view -> {
            binding.btnQty.setBackgroundResource(R.drawable.shape_rect);
            binding.btnValue.setBackgroundResource(0);
            binding.tvAchiveValue.setText("Sales Qty");
            binding.btnQty.setTextColor(Color.parseColor("#FFFFFF"));
            binding.btnValue.setTextColor(Color.parseColor("#000000"));
            isValueSelected = false;
            isQtySelected = true;

            binding.montyer.setText(UtilityHelper.monthNameArrayFull[month] + '-' + year);

            pd_process.show();
            pd_process.setCancelable(false);

            HashMap<String, String> mapQty = new HashMap<>();
            mapQty.put("empId", String.valueOf(session.getEmpInfoId()));
            mapQty.put("month", String.valueOf(month + 1));
            mapQty.put("year", String.valueOf(year));
            mapQty.put("Type", "Qty");

            System.out.println(mapQty);
            callapi(mapQty);
        });


        binding.atteFilter.setOnClickListener(v -> {
            openFilter();
        });
    }

    public void callapi(HashMap<String, String> filters) {
        if (NetworkInformation.isConnected(ReportTargetvsActivity.this)) {
            try {
                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<ModelTarget>> call = service.GetTargetReport(filters);
                call.enqueue(new Callback<List<ModelTarget>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelTarget>> call, @NonNull Response<List<ModelTarget>> response) {
                        if (pd_process != null && pd_process.isShowing()) {
                            pd_process.dismiss();
                        }
                        if (response.body() != null) {
                            LoadRV(response.body());
                        } else {
                            LoadRV(null);
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Report Data Not Found!!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelTarget>> call, @NonNull Throwable t) {
                        if (pd_process != null && pd_process.isShowing()) {
                            pd_process.dismiss();
                        }
                        if (t instanceof SocketTimeoutException) {
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Slow Connection Detected. Please try again");
                        }
                    }
                });

            } catch (Exception ex) {
                String str = ex.toString();
                Log.e("Exception", str);
            }
        } else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet");
        }
    }

    private void LoadRV(List<ModelTarget> data) {
        AdapterAchive srpAdapter = new AdapterAchive(data, ReportTargetvsActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ReportTargetvsActivity.this);
        binding.rvAchiv.setLayoutManager(mLayoutManager);
        binding.rvAchiv.setItemAnimator(new DefaultItemAnimator());
        binding.rvAchiv.setAdapter(srpAdapter);
        binding.rvAchiv.setItemAnimator(null);
        binding.rvAchiv.scrollToPosition(0);
        srpAdapter.notifyDataSetChanged();
    }

    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(ReportTargetvsActivity.this);
        bsheetdlg.setContentView(ftm.getRoot());
        bsheetdlg.setCanceledOnTouchOutside(true);
        bsheetdlg.show();
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ReportTargetvsActivity.this, android.R.layout.simple_spinner_item, emptype);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ReportTargetvsActivity.this, android.R.layout.simple_spinner_item, emptype);
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
                ArrayAdapter<String> dataAdapterrsm = new ArrayAdapter<>(ReportTargetvsActivity.this, android.R.layout.simple_spinner_item, emptype);
                dataAdapterrsm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.spinnerEmployeeType.setAdapter(dataAdapterrsm);
                break;
            case "NSM":
                emptype.add("Select");
                emptype.add("MBE");
                emptype.add("ABM");
                emptype.add("RBM");
                emptype.add("Cluster Head");
                ArrayAdapter<String> dataAdapternsm = new ArrayAdapter<>(ReportTargetvsActivity.this, android.R.layout.simple_spinner_item, emptype);
                dataAdapternsm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.spinnerEmployeeType.setAdapter(dataAdapternsm);
                break;
        }
        ftm.spinnerEmployeeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTyp = String.valueOf(ftm.spinnerEmployeeType.getSelectedItem());
                switch (selectedTyp) {
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
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(ReportTargetvsActivity.this, android.R.layout.simple_spinner_item, mbeList);
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
                            ftm.mbelay.setVisibility(View.VISIBLE);
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
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(ReportTargetvsActivity.this, android.R.layout.simple_spinner_item, mioList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalMio.setAdapter(dataAdapter);
                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "ABM Not Found!!");
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(ReportTargetvsActivity.this, android.R.layout.simple_spinner_item, asmList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalAsm.setAdapter(dataAdapter);

                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "RBM Not Found!!");
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
                                ArrayAdapter<ModelRSM> dataAdapter = new ArrayAdapter<>(ReportTargetvsActivity.this, android.R.layout.simple_spinner_item, rsmList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalRsm.setAdapter(dataAdapter);
                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Cluster Head Not Found!!");
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
                filters.put("month", String.valueOf(selectedMonth + 1));
                filters.put("year", String.valueOf(selectedyear));
                if (isValueSelected) {
                    filters.put("Type", "Value");
                } else if (isQtySelected) {
                    filters.put("Type", "Qty");
                } else {
                    filters.put("Type", "Value");
                }
                System.out.println(filters);
                pd_process.show();
                pd_process.setCancelable(false);
                callapi(filters);
                bsheetdlg.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
           // bsheetdlg.cancel();
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
        dialogFragment.show(ReportTargetvsActivity.this.getSupportFragmentManager(), null);

        dialogFragment.setOnDateSetListener((year, monthOfYear) -> {
            ftm.montyer.setText("");
            ftm.montyer.setText(UtilityHelper.monthNameArrayFull[monthOfYear] + ',' + year);
            binding.montyer.setText(UtilityHelper.monthNameArrayFull[monthOfYear] + ',' + year);
            selectedMonth = monthOfYear;
            selectedyear = year;
        });
    }
}