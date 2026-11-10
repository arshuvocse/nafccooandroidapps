package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Customer;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelCCReport;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCustomerCall;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter.CCrAdapter;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter.CcReasonAdapter;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.ChemistCallActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.ActivityDCPApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.ActivityDCRApproval;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityChemistCallBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityCustCallBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCustCall extends AppCompatActivity {
    ActivityCustCallBinding binding;
    ComponentLoadingbarBinding lbinding;
    Dialog pd_process;
    BottomSheetDialog bsheetdlg;
    FilterMasterBinding ftm;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    String empId,emp_id,role,selectedTyp = "";
    List<ModelMBE> mbeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        lbinding= ComponentLoadingbarBinding.inflate(getLayoutInflater());
        binding.toolbarCustom.setNavigationOnClickListener(view -> {
            finish();
        });
        dbCrudHelper=new DBCrudHelper(ActivityCustCall.this);
        session=dbCrudHelper.getSession();
        role = session.getEmpRole();
        empId=String.valueOf(session.getEmpInfoId());
        prepareLoadingBar();
        HashMap<String,String> body=new HashMap<>();
        String defaultFrom= UtilityHelper._GetCurrentDate();
        String defaultTo= UtilityHelper._GetCurrentDate();
        body.put("empId",String.valueOf(session.getEmpInfoId()));
        body.put("frmDate",defaultFrom);
        body.put("toDate",defaultTo);
        updateDateRange(defaultFrom, defaultTo);

        //api/Customer/GetChemistCall?empId=151&frmDate=04-Mar-2023&toDate=04-Mar-2023

        if (NetworkInformation.isConnected(ActivityCustCall.this)) {
            pd_process.show();
            pd_process.setCancelable(true);
            lbinding.tvText.setText("Loading...");
            callApi(body);
        }
        openFilter();
        binding.ivFilter.setOnClickListener(view -> {
            bsheetdlg.show();
        });
        binding.tvFromDate.setOnClickListener(v -> UtilityHelper._datePickerDialogeForDates(binding.tvFromDate,ActivityCustCall.this));
        binding.tvToDate.setOnClickListener(v -> UtilityHelper._datePickerDialogeForDates(binding.tvToDate,ActivityCustCall.this));
        binding.btnApplyDateFilter.setOnClickListener(v -> applyInlineFilter());
    }
    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(ActivityCustCall.this);
        bsheetdlg.setContentView(ftm.getRoot());
        bsheetdlg.setCanceledOnTouchOutside(true);
        ftm.filterMonthYearLayout.txtFromDate.setText(binding.tvFromDate.getText());
        ftm.filterMonthYearLayout.txtToDate.setText(binding.tvToDate.getText());
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
        ftm.filterMonthYearLayout.getRoot().setVisibility(VISIBLE);

        ftm.filterMonthYearLayout.ivDatePickerFromDate.setVisibility(VISIBLE);
        ftm.filterMonthYearLayout.ivDatePickerToDate.setVisibility(VISIBLE);

        ftm.filterMonthYearLayout.ivDatePickerFromDate.setOnClickListener(v->{
            UtilityHelper._datePickerDialogeForDates(ftm.filterMonthYearLayout.txtFromDate,ActivityCustCall.this);
        });
        ftm.filterMonthYearLayout.ivDatePickerToDate.setOnClickListener(v->{
            UtilityHelper._datePickerDialogeForDates(ftm.filterMonthYearLayout.txtToDate,ActivityCustCall.this);
        });

        List<String> emptype = new ArrayList<>();
        switch (role) {
            case "MIO":
                emptype.add("Select");
                emptype.add("MBE");
                try {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityCustCall.this, android.R.layout.simple_spinner_item, emptype);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityCustCall.this, android.R.layout.simple_spinner_item, emptype);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ftm.spinnerEmployeeType.setAdapter(dataAdapter);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
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
                        }catch (Exception e){}
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
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(ActivityCustCall.this, android.R.layout.simple_spinner_item, mbeList);
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
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(ActivityCustCall.this, android.R.layout.simple_spinner_item, mioList);
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(ActivityCustCall.this, android.R.layout.simple_spinner_item, asmList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalAsm.setAdapter(dataAdapter);

                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "RBM Not Found!!");
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
        ftm.filterTopLayout.btnDonefilter.setOnClickListener(v -> {
            try {
                switch (selectedTyp) {
                    case "MBE":
                        ModelMBE mbeempid = (ModelMBE) ftm.spinnerApprovalMbe.getSelectedItem();
                        emp_id = String.valueOf(mbeempid.getMBEEmpId());
                        break;
                    case "ABM":
                        ModelMIO mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
                        emp_id = String.valueOf(mioempid.getMIOEmpId());

                        break;
                }
                HashMap<String,String> bodyz=new HashMap<>();
                //bodyz.put("empId",String.valueOf(empId));
                if(TextUtils.isEmpty(emp_id))
                {
                    bodyz.put("empId", empId);
                }else {
                    bodyz.put("empId", emp_id);
                }

                String fromDate = ftm.filterMonthYearLayout.txtFromDate.getText().toString().trim();
                String toDate = ftm.filterMonthYearLayout.txtToDate.getText().toString().trim();
                bodyz.put("frmDate",fromDate);
                bodyz.put("toDate",toDate);
                updateDateRange(fromDate, toDate);
                if (NetworkInformation.isConnected(ActivityCustCall.this)) {
                    pd_process.show();
                    pd_process.setCancelable(true);
                    lbinding.tvText.setText("Loading...");
                    callApi(bodyz);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            bsheetdlg.cancel();
        });
        ftm.filterTopLayout.ivCancel.setOnClickListener(v -> {
            bsheetdlg.cancel();
        });
    }
    private void callApi(HashMap<String, String> body) {
        try {
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<List<ModelCCReport>> call = service.getCCReport(body);
            call.enqueue(new Callback<List<ModelCCReport>>() {
                @Override
                public void onResponse(@NonNull Call<List<ModelCCReport>> call, @NonNull Response<List<ModelCCReport>> response) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (response.isSuccessful()) {
                        LoadData(response.body());

                    }else {
                        binding.toolbarCount.setText("(0)");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<ModelCCReport>> call, @NonNull Throwable t) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    SnackBarManagement._error_CustomMessage(binding.getRoot(),t.getMessage());
                }
            });

        } catch (Exception ex) {
        }
    }
    private void LoadData(List<ModelCCReport> body) {
        if(body!=null && body.size()>0)
        {
            binding.toolbarCount.setText("("+body.size()+")");
        }else {
            binding.toolbarCount.setText("0");
        }
        CCrAdapter adapterz = new CCrAdapter(ActivityCustCall.this,body);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.rvCcr.setLayoutManager(mLayoutManager);
        binding.rvCcr.setAdapter(adapterz);
        adapterz.notifyDataSetChanged();
    }
    private void prepareLoadingBar() {
        pd_process=new Dialog(ActivityCustCall.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    private void updateDateRange(String fromDate, String toDate) {
        binding.dateRangeContainer.setVisibility(VISIBLE);
        binding.tvFromDate.setText(TextUtils.isEmpty(fromDate) ? "-" : fromDate);
        binding.tvToDate.setText(TextUtils.isEmpty(toDate) ? "-" : toDate);
    }

    private void applyInlineFilter() {
        String fromDate = sanitizeDateText(binding.tvFromDate.getText().toString());
        String toDate = sanitizeDateText(binding.tvToDate.getText().toString());
        HashMap<String,String> body=new HashMap<>();
        String targetEmp = TextUtils.isEmpty(emp_id) ? empId : emp_id;
        body.put("empId", targetEmp);
        body.put("frmDate", fromDate);
        body.put("toDate", toDate);
        updateDateRange(fromDate, toDate);
        if (NetworkInformation.isConnected(ActivityCustCall.this)) {
            pd_process.show();
            pd_process.setCancelable(true);
            lbinding.tvText.setText("Loading...");
            callApi(body);
        } else {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "No internet connection");
        }
    }

    private String sanitizeDateText(String raw) {
        if (TextUtils.isEmpty(raw)) return "";
        String val = raw.trim();
        if (val.equals("-")) return "";
        return val;
    }
}
