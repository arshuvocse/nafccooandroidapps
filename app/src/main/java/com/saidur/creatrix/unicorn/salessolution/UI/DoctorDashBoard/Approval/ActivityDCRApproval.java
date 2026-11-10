package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterDcrApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCPApprovePost;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCRApproveList;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
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

public class ActivityDCRApproval extends AppCompatActivity{
    ActivityDcrapprovalBinding binding;
    FilterMasterBinding ftm;
    DBCrudHelper dbCrudHelper;
    ModelSession session;
    AdapterDcrApproval approveAdapter;
    ProgressDialog pd;
    String params, role;
    String tagST,tagT, tagA,STeriid,Teriid, Areaid;
    String selectedTyp = "";
    int selectedMonth = 0, selectedyear = 0;
    String RoleType, empId,emp_id;
    List<ModelMBE> mbeList;
    BottomSheetDialog bsheetdlg;
    Dialog drejectRemarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDcrapprovalBinding.inflate(getLayoutInflater());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbCrudHelper = new DBCrudHelper(ActivityDCRApproval.this);
        session = dbCrudHelper.getSession();
        empId = String.valueOf(dbCrudHelper.getSession().getEmpInfoId());
        role = session.getEmpRole();
        RoleType = session.getRoleType();
        openFilter();
        tagST = ".SubTerritoryId=";
        tagT = ".EmpTerrId=";
        tagA = ".EmpAreaId=";
        switch (role) {
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
        }

      /*  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            selectedMonth = localDate.getMonthValue();
            selectedyear = localDate.getYear();
        }*/

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        HashMap<String, String> data = new HashMap<>();
        data.put("Role", role);
        data.put("empId", "");
        data.put("AppStatus", "");
        data.put("Month", String.valueOf(month+1));
        data.put("Year", String.valueOf(year));
        LoadData(params, data);

        binding.ivFilter.setOnClickListener(v -> {
            bsheetdlg.show();
        });

    }
    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(ActivityDCRApproval.this);
        bsheetdlg.setContentView(ftm.getRoot());
        bsheetdlg.setCanceledOnTouchOutside(true);
        //bsheetdlg.show();
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityDCRApproval.this, android.R.layout.simple_spinner_item, emptype);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityDCRApproval.this, android.R.layout.simple_spinner_item, emptype);
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
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(ActivityDCRApproval.this, android.R.layout.simple_spinner_item, mbeList);
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
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(ActivityDCRApproval.this, android.R.layout.simple_spinner_item, mioList);
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(ActivityDCRApproval.this, android.R.layout.simple_spinner_item, asmList);
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
                        ModelMBE mbeempid = (ModelMBE) ftm.spinnerApprovalMbe.getSelectedItem();
                        emp_id = String.valueOf(mbeempid.getMBEEmpId());
                        break;
                    case "ABM":
                        ModelMIO mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
                        emp_id = String.valueOf(mioempid.getMIOEmpId());
                        break;
                }

                HashMap<String, String> filterz = new HashMap<>();
                filterz.put("Role", role);
                if(TextUtils.isEmpty(emp_id))
                {
                    filterz.put("empId", empId);
                }else {
                    filterz.put("empId", emp_id);
                }
                filterz.put("AppStatus", "");
                filterz.put("Month", String.valueOf(selectedMonth+1));
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
        if (NetworkInformation.isConnected(ActivityDCRApproval.this)) {
            binding.rvData.setVisibility(VISIBLE);
            binding.llNo.setVisibility(GONE);
            try {
                pd = new ProgressDialog(ActivityDCRApproval.this);
                pd.setMessage("Report Loading....");
                pd.show();
                pd.setCanceledOnTouchOutside(false);

                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<ModelDCRApproveList>> call = service.GetDCRApprovalList(param, filter);
                call.enqueue(new Callback<List<ModelDCRApproveList>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelDCRApproveList>> call, @NonNull Response<List<ModelDCRApproveList>> response) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.body() != null && response.body().size()>0) {
                            binding.rvData.setVisibility(VISIBLE);
                            binding.llNo.setVisibility(GONE);
                            List<ModelDCRApproveList> rp = response.body();
                            LoadRV(rp);
                        } else {
                            binding.rvData.setVisibility(GONE);
                            binding.llNo.setVisibility(VISIBLE);
                            binding.ivNo.setImageResource(R.drawable.src_nodata);
                            binding.tvNo.setText("Data Not Found!!");
                            binding.toolbarCount.setText("(0)");
                           // LoadRV(null);
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Report Data Not Found!!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelDCRApproveList>> call, @NonNull Throwable t) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
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
            binding.rvData.setVisibility(GONE);
            binding.llNo.setVisibility(VISIBLE);
            //binding.ivNo.setBackgroundResource(R.drawable.src_nodata);
            binding.ivNo.setImageResource(R.drawable.src_nointernet);
            binding.tvNo.setText("No Internet!!");
        }
    }

    private void LoadRV(List<ModelDCRApproveList> data) {
        if (data != null) {
            binding.toolbarCount.setText(" ("+String.valueOf(data.size())+")");
            approveAdapter = new AdapterDcrApproval(ActivityDCRApproval.this, data);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityDCRApproval.this);
            binding.rvData.setLayoutManager(mLayoutManager);
            binding.rvData.setItemAnimator(new DefaultItemAnimator());
            binding.rvData.setAdapter(approveAdapter);
            binding.rvData.setItemAnimator(null);
            binding.rvData.scrollToPosition(0);
            approveAdapter.notifyDataSetChanged();
        }

    }

}