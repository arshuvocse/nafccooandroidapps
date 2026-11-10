package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterDcpApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCPApproveList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCPApprovePost;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.ActivityDCP;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPList;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Adapter.AdapterSalesReportProduct;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Adapter.AdapterSalesReportSlider;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model.PWSData;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model.PWSaleReportModel;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.ReportSalesActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDcpapprovalBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDcrapprovalBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopRejectRemarksBinding;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDCPApproval extends AppCompatActivity {
    ActivityDcpapprovalBinding binding;
    PopRejectRemarksBinding rejectbinding;

    FilterMasterBinding ftm;
    DBCrudHelper dbCrudHelper;
    ModelSession session;
    AdapterDcpApproval approveAdapter;
    ProgressDialog pd;
    String params, role;
    String tagST,tagT, STeriid,tagA,Teriid, Areaid;
    String selectedTyp = "";
    int selectedMonth = 0, selectedyear = 0;
    String RoleType, empId,emp_id;
    List<ModelMBE> mbeList;
    BottomSheetDialog bsheetdlg;
    Dialog drejectRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDcpapprovalBinding.inflate(getLayoutInflater());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        rejectbinding = PopRejectRemarksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());

        dbCrudHelper = new DBCrudHelper(ActivityDCPApproval.this);
        session = dbCrudHelper.getSession();
        empId = session != null ? String.valueOf(session.getEmpInfoId()) : "";
        role = (session != null && session.getEmpRole() != null) ? session.getEmpRole().trim() : "";
        RoleType = (session != null && session.getRoleType() != null) ? session.getRoleType().trim() : "";
        openFilter();
        tagST = ".SubTerritoryId=";
        tagT = ".EmpTerrId=";
        tagA = ".EmpAreaId=";
        params = "";
        switch (role.toUpperCase(java.util.Locale.US)) {
            case "MBE":
                STeriid = String.valueOf(dbCrudHelper.getCurrentUserSTeriId_SQLite());
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagST + STeriid;
                break;
            case "MIO":
                Teriid = String.valueOf(dbCrudHelper.getCurrentUserTeriId_SQLite());
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagT + Teriid;
                break;
            case "AM":
                Areaid = String.valueOf(dbCrudHelper.getCurrentUserAreaId_SQLite());
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagA + Areaid;
                break;
            default:
                params = "";
                break;
        }

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        HashMap<String, String> data = new HashMap<>();
        data.put("Role", role);
        data.put("empId", empId);
        data.put("AppStatus", "");
        data.put("Month", String.valueOf(month + 1));
        data.put("Year", String.valueOf(year));
        LoadData(params, data);
        binding.ivFilter.setOnClickListener(v -> {
            bsheetdlg.show();
        });
        rejectDialog();
    }

    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(ActivityDCPApproval.this);
        bsheetdlg.setContentView(ftm.getRoot());
        bsheetdlg.setCanceledOnTouchOutside(true);
        //bsheetdlg.show();
        switch (role.toUpperCase(java.util.Locale.US)) {
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
        switch (role.toUpperCase(java.util.Locale.US)) {
            case "MIO":
                emptype.add("Select");
                emptype.add("MBE");
                try {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityDCPApproval.this, android.R.layout.simple_spinner_item, emptype);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityDCPApproval.this, android.R.layout.simple_spinner_item, emptype);
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
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(ActivityDCPApproval.this, android.R.layout.simple_spinner_item, mbeList);
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
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(ActivityDCPApproval.this, android.R.layout.simple_spinner_item, mioList);
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(ActivityDCPApproval.this, android.R.layout.simple_spinner_item, asmList);
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
                        if (ftm.spinnerApprovalMbe.getSelectedItem() instanceof ModelMBE) {
                            ModelMBE mbeempid = (ModelMBE) ftm.spinnerApprovalMbe.getSelectedItem();
                            emp_id = String.valueOf(mbeempid.getMBEEmpId());
                        }
                        break;
                    case "ABM":
                        if (ftm.spinnerApprovalMio.getSelectedItem() instanceof ModelMIO) {
                            ModelMIO mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
                            emp_id = String.valueOf(mioempid.getMIOEmpId());
                        }
                        break;
                }

                HashMap<String, String> filterz = new HashMap<>();
                filterz.put("Role", role);
                if (TextUtils.isEmpty(emp_id)) {
                    filterz.put("empId", empId);
                } else {
                    filterz.put("empId", emp_id);
                }
                filterz.put("AppStatus", "");
                filterz.put("Month", String.valueOf(selectedMonth + 1));
                filterz.put("Year", String.valueOf(selectedyear));

                System.out.println(filterz);
                LoadData(params, filterz);

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
        dialogFragment.show(getSupportFragmentManager(), null);
        dialogFragment.setOnDateSetListener((year, monthOfYear) -> {
            ftm.montyer.setText("");
            ftm.montyer.setText(UtilityHelper.monthNameArrayFull[monthOfYear] + ',' + year);
            selectedMonth = monthOfYear;
            selectedyear = year;
        });
    }

    private void LoadData(String param, HashMap<String, String> filter) {
        if (NetworkInformation.isConnected(ActivityDCPApproval.this)) {
            binding.rvData.setVisibility(VISIBLE);
            binding.llNo.setVisibility(GONE);
            try {
                if (pd == null) {
                    pd = new ProgressDialog(ActivityDCPApproval.this);
                }
                pd.setMessage("Report Loading....");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<ModelDCPApproveList>> call = service.GetDCPApprovalList(param, filter);
                call.enqueue(new Callback<List<ModelDCPApproveList>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelDCPApproveList>> call, @NonNull Response<List<ModelDCPApproveList>> response) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                            binding.rvData.setVisibility(VISIBLE);
                            binding.llNo.setVisibility(GONE);
                            List<ModelDCPApproveList> rp = response.body();
                            LoadRV(rp);
                        } else {
                            binding.rvData.setVisibility(GONE);
                            binding.llNo.setVisibility(VISIBLE);
                            binding.ivNo.setImageResource(R.drawable.src_nodata);
                            binding.tvNo.setText("Data Not Found!!");
                            binding.toolbarCount.setText("(0)");
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Report Data Not Found!!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelDCPApproveList>> call, @NonNull Throwable t) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        binding.rvData.setVisibility(GONE);
                        binding.llNo.setVisibility(VISIBLE);
                        binding.toolbarCount.setText("(0)");
                        if (t instanceof SocketTimeoutException) {
                            binding.ivNo.setImageResource(R.drawable.src_nointernet);
                            binding.tvNo.setText("Slow Connection Detected");
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Slow Connection Detected. Please try again");
                        } else {
                            binding.ivNo.setImageResource(R.drawable.src_nodata);
                            binding.tvNo.setText("Failed to load data");
                            SnackBarManagement._error_CustomMessage(binding.getRoot(), "Error: " + (t.getMessage() != null ? t.getMessage() : "Unknown error"));
                        }
                    }
                });

            } catch (Exception ex) {
                String str = ex.toString();
                Log.e("Exception", str);
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                binding.rvData.setVisibility(GONE);
                binding.llNo.setVisibility(VISIBLE);
                binding.ivNo.setImageResource(R.drawable.src_nodata);
                binding.tvNo.setText("Exception occurred");
            }
        } else {
            binding.rvData.setVisibility(GONE);
            binding.llNo.setVisibility(VISIBLE);
            binding.ivNo.setImageResource(R.drawable.src_nointernet);
            binding.tvNo.setText("No Internet!!");
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet");
        }
    }

    private void LoadRV(List<ModelDCPApproveList> data) {
        if (data != null) {
            if (data.size() > 0) {
                binding.toolbarCount.setText(" (" + String.valueOf(data.size()) + ")");
            }

            approveAdapter = new AdapterDcpApproval(ActivityDCPApproval.this, data);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityDCPApproval.this);
            binding.rvData.setLayoutManager(mLayoutManager);
            binding.rvData.setItemAnimator(new DefaultItemAnimator());
            binding.rvData.setAdapter(approveAdapter);
            binding.rvData.setItemAnimator(null);
            binding.rvData.scrollToPosition(0);
            approveAdapter.notifyDataSetChanged();
        }
    }


   /* private void CallApproveApi(ModelDCPApprovePost post) {
        if (NetworkInformation.isConnected(ActivityDCPApproval.this)) {
            try {
                pd = new ProgressDialog(ActivityDCPApproval.this);
                pd.setMessage("Report Loading....");
                pd.show();
                pd.setCanceledOnTouchOutside(false);

                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<ResponseResultInfo> call = service.SaveDCPApprove(post);
                call.enqueue(new Callback<ResponseResultInfo>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                        if(pd!=null||pd.isShowing())
                        {
                            pd.dismiss();
                        }
                        ResponseResultInfo info = response.body();
                        if (info.getSuccess()) {
                            new AlertDialog.Builder(ActivityDCPApproval.this)
                                    .setTitle("Approval")
                                    .setMessage("Success")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        HashMap<String, String> datam = new HashMap<>();
                                        datam.put("Role", role);
                                        datam.put("empId", "");
                                        datam.put("AppStatus", String.valueOf(0));
                                        datam.put("Month", String.valueOf(selectedMonth));
                                        datam.put("Year", String.valueOf(selectedyear));
                                        LoadData(params, datam);
                                        rejectbinding.remarksTxt.setText("");
                                        dialog.cancel();
                                    }).setCancelable(false).show();

                        } else {
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), info.getMsd());
                        }


                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                        if(pd!=null||pd.isShowing())
                        {
                            pd.dismiss();
                        }
                        SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Failed!!");
                    }
                });

            } catch (Exception ex) {
                String str = ex.toString();
                Log.e("Exception", str);
            }
        } else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet");
        }
    }*/

    private void rejectDialog() {
        drejectRemarks = new Dialog(ActivityDCPApproval.this);
        drejectRemarks.setContentView(rejectbinding.getRoot());
        drejectRemarks.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        drejectRemarks.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        drejectRemarks.getWindow().getAttributes().gravity = Gravity.CENTER;
    }
}