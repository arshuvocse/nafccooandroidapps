package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule;


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
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Adapter.AdapterWSApprovalMaster;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSApprovalMaster;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSApprovalSvBody;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityWsapprovalBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopRejectRemarksBinding;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityWSApproval extends AppCompatActivity implements AdapterWSApprovalMaster.WSClick{
ActivityWsapprovalBinding binding;

    AdapterWSApprovalMaster adapter;
    DBCrudHelper dbCrudHelper;
    ModelSession session;
    FilterMasterBinding ftm;
    PopRejectRemarksBinding rejectbinding;
    ComponentLoadingbarBinding lbinding;
    ProgressDialog pd;
    Dialog drejectRemark,pd_process;
    String params, role;
    String tagT, tagA,Teriid, Areaid;
    String selectedTyp = "";
    int selectedMonth = 0, selectedyear = 0;
    String RoleType, empId,emp_id;
    List<ModelMBE> mbeList;
    BottomSheetDialog bsheetdlg;
    String whoz="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWsapprovalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        rejectbinding = PopRejectRemarksBinding.inflate(getLayoutInflater());
        lbinding = ComponentLoadingbarBinding.inflate(getLayoutInflater());
        binding.toolbarCustom.setNavigationOnClickListener(view -> {
            finish();
        });

        String who=getIntent().getStringExtra("Who");
        if(who.equals("Report"))
        {
            binding.toolbarTitle.setText("Work Schedule View");
            whoz=who;
        }

        prepareLoadingBar();
        dbCrudHelper = new DBCrudHelper(ActivityWSApproval.this);
        session = dbCrudHelper.getSession();
        empId = String.valueOf(dbCrudHelper.getSession().getEmpInfoId());
        role = session.getEmpRole();
        RoleType = session.getRoleType();
        openFilter();
        tagT = ".EmpTerrId=";
        tagA = ".EmpAreaId=";
        switch (role) {
            case "MIO":
                Teriid = String.valueOf(dbCrudHelper.getCurrentUserTeriId_SQLite());
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagT + Teriid;
                break;
            case "AM":
                Areaid = String.valueOf(dbCrudHelper.getCurrentUserAreaId_SQLite());
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagA + Areaid;
                break;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
           // selectedMonth = localDate.getMonthValue();
            //selectedyear = localDate.getYear();
        }
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);


        HashMap<String, String> data = new HashMap<>();
        data.put("Role", role);
        data.put("EmpId", "");
        data.put("AppStatus", "");
        data.put("FromDt", "");
        data.put("ToDt", "");
        data.put("MonthValue", String.valueOf(month+1));//String.valueOf(selectedMonth)
        data.put("YearValue",String.valueOf(year));//String.valueOf(selectedyear)
        LoadData(params, data);

        binding.ivFilter.setOnClickListener(v -> {
            bsheetdlg.show();
        });
    }

    private void prepareLoadingBar() {
        pd_process=new Dialog(ActivityWSApproval.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;

        drejectRemark = new Dialog(ActivityWSApproval.this);
        drejectRemark.setContentView(rejectbinding.getRoot());
        drejectRemark.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        drejectRemark.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        drejectRemark.getWindow().getAttributes().gravity = Gravity.CENTER;
    }
    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(ActivityWSApproval.this);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityWSApproval.this, android.R.layout.simple_spinner_item, emptype);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityWSApproval.this, android.R.layout.simple_spinner_item, emptype);
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
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(ActivityWSApproval.this, android.R.layout.simple_spinner_item, mbeList);
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
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(ActivityWSApproval.this, android.R.layout.simple_spinner_item, mioList);
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(ActivityWSApproval.this, android.R.layout.simple_spinner_item, asmList);
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
        ftm.filterMonthYearLayout.getRoot().setVisibility(GONE);

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
                    filterz.put("EmpId", empId);
                }else {
                    filterz.put("EmpId", emp_id);
                }
                filterz.put("AppStatus", "");
                filterz.put("FromDt", "");
                filterz.put("ToDt", "");
                filterz.put("MonthValue", String.valueOf(selectedMonth+1));
                filterz.put("YearValue",String.valueOf(selectedyear));
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

    private void LoadData(String param, HashMap<String, String> filter) {
        if (NetworkInformation.isConnected(ActivityWSApproval.this)) {
            binding.rvWslist.setVisibility(VISIBLE);
            binding.llNo.setVisibility(GONE);
            try {
                pd_process.show();
                pd_process.setCancelable(true);
                pd_process.setCanceledOnTouchOutside(true);
                lbinding.tvText.setText("Loading...");

                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<ModelWSApprovalMaster>> call = service.GetWSApprovalList(param, filter);
                call.enqueue(new Callback<List<ModelWSApprovalMaster>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelWSApprovalMaster>> call, @NonNull Response<List<ModelWSApprovalMaster>> response) {
                        if (pd_process != null && pd_process.isShowing()) {
                            pd_process.dismiss();
                        }
                        if (response.body() != null && response.body().size()>0) {
                            binding.rvWslist.setVisibility(VISIBLE);
                            binding.llNo.setVisibility(GONE);

                            List<ModelWSApprovalMaster> rp = response.body();
                            LoadRV(rp,whoz);
                        } else {
                            binding.rvWslist.setVisibility(GONE);
                            binding.llNo.setVisibility(VISIBLE);
                            binding.ivNo.setImageResource(R.drawable.src_nodata);
                            binding.tvNo.setText("Data Not Found!!");
                            binding.toolbarCount.setText("(0)");
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Report Data Not Found!!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelWSApprovalMaster>> call, @NonNull Throwable t) {
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
            binding.rvWslist.setVisibility(GONE);
            binding.llNo.setVisibility(VISIBLE);
            binding.ivNo.setImageResource(R.drawable.src_nointernet);
            binding.tvNo.setText("No Internet!!");
        }
    }
    private void LoadRV(List<ModelWSApprovalMaster> data, String whoz) {
        if (data != null) {
            if(data.size()>0)
            {
                binding.toolbarCount.setText(" ("+String.valueOf(data.size())+")");
            }else {
                binding.toolbarCount.setText("(0)");
            }

            adapter = new AdapterWSApprovalMaster(this, data,whoz, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.rvWslist.setLayoutManager(mLayoutManager);
            binding.rvWslist.setItemAnimator(new DefaultItemAnimator());
            binding.rvWslist.setAdapter(adapter);
            binding.rvWslist.setItemAnimator(null);
            binding.rvWslist.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onApproveClick(int mid,int type) {

        if(type==1)
        {

            ModelWSApprovalSvBody body=new ModelWSApprovalSvBody();
            body.setMbeWSApprovalLogId(0);
            String entrydate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());
            body.setDate(entrydate);
            body.setFromEmpId(0);
            body.setToEmpId(session.getEmpInfoId());
            body.setTableId(mid);
            body.setStatus("Accepted");
            body.setComments("");
            body.setStep(2);
            body.setRoleTypeId(session.getRoleTypeId());
            body.setToRoleTypeId(session.getRoleTypeId());
            body.setMenuId(377);
            if(NetworkInformation.isConnected(ActivityWSApproval.this))
            {
                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Processing...");
                callApprove(body);
            }else {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(),"No Internet!!");
            }

        }
        if(type==3)
        {

            ModelWSApprovalSvBody body=new ModelWSApprovalSvBody();
            body.setMbeWSApprovalLogId(0);
            String entrydate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());
            body.setDate(entrydate);
            body.setFromEmpId(0);
            body.setToEmpId(session.getEmpInfoId());
            body.setTableId(mid);
            body.setStatus("Revert");
            body.setComments("");
            body.setStep(2);
            body.setRoleTypeId(session.getRoleTypeId());
            body.setToRoleTypeId(session.getRoleTypeId());
            body.setMenuId(377);
            if(NetworkInformation.isConnected(ActivityWSApproval.this))
            {
                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Processing...");
                callApprove(body);
            }else {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(),"No Internet!!");
            }

        }
        if(type==2)
        {
            rejectDialog(mid);
        }
    }

    private void callApprove(ModelWSApprovalSvBody body) {
        try {
            ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
            Call<ResponseResultInfo> call = service.SaveWSApprove(body);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if(response!=null && response.body().isSuccess)
                    {
                        HashMap<String, String> data = new HashMap<>();
                        data.put("Role", role);
                        data.put("EmpId", "");
                        data.put("AppStatus", "");
                        data.put("FromDt", "");
                        data.put("ToDt", "");
                        data.put("MonthValue", "");//String.valueOf(selectedMonth)
                        data.put("YearValue","");//String.valueOf(selectedyear)
                        LoadData(params, data);
                        SnackBarManagement._success_CustomMessage(binding.getRoot(),"Submitted");
                    }
                    else {
                        SnackBarManagement._success_CustomMessage(binding.getRoot(),response.body().getMsd());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                    } else {
                    }
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void rejectDialog(int mid) {

        drejectRemark.show();

        rejectbinding.btnSubmit.setOnClickListener(view -> {
            String remarks= rejectbinding.remarksTxt.getText().toString().trim();
            if (remarks.equals("")) {
                rejectbinding.remarksTxt.setError("Remarks Require");
                rejectbinding.remarksTxt.setFocusable(true);
            }
            else {

                ModelWSApprovalSvBody body=new ModelWSApprovalSvBody();
                body.setMbeWSApprovalLogId(0);
                String entrydate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());
                body.setDate(entrydate);
                body.setFromEmpId(0);
                body.setToEmpId(session.getEmpInfoId());
                body.setTableId(mid);
                body.setStatus("Rejected");
                body.setComments(remarks);
                body.setStep(2);
                body.setRoleTypeId(session.getRoleTypeId());
                body.setToRoleTypeId(session.getRoleTypeId());
                body.setMenuId(377);
                drejectRemark.dismiss();


                if(NetworkInformation.isConnected(ActivityWSApproval.this))
                {
                    pd_process.show();
                    pd_process.setCancelable(false);
                    lbinding.tvText.setText("Processing...");
                    callApprove(body);
                }else {
                    SnackBarManagement._warning_CustomMessage(binding.getRoot(),"No Internet!!");
                }
            }
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
}