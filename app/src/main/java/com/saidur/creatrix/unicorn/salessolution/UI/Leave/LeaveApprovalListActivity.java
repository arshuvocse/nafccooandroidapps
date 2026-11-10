package com.saidur.creatrix.unicorn.salessolution.UI.Leave;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ILeaveApproval;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalAdapter;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalData;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalRQ;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterLeaveApproval;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityLeaveApprovalListBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopDetailsLeaveBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopRejectRemarksBinding;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LeaveApprovalListActivity extends AppCompatActivity implements ILeaveApproval.View,LeaveApprovalAdapter.ApprovalLitener{
    ActivityLeaveApprovalListBinding binding;
    PopRejectRemarksBinding rejectbinding;
    PopDetailsLeaveBinding detailsbinding;
    ComponentLoadingbarBinding lbinding;

    PresenterLeaveApproval presenter;
    BottomSheetDialog bsheetdlg;
    FilterMasterBinding ftm;
    DBCrudHelper dbCrudHelper;
    LeaveApprovalAdapter leaveAdapter;
    ModelSession session;
    String empid;

    String fromdat, todate, RoleType, params, tagT,tagA, tagR, tagN,Teriid, Areaid, Regionid, Groupid, status, selectedTyp = "";
    int RoleTypeId;
    List<ModelMBE> mbeList;
    int selectedMonth = 0, selectedyear = 0;

    //Approval material
    private int prev = 0;
    private int current = 0;
    private int next = 0;
    private int myrole = 0;
    String prev_roleType, next_roleType;
    Dialog drejectRemark,dDetails,pd_process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaveApprovalListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        dbCrudHelper=new DBCrudHelper(LeaveApprovalListActivity.this);
        presenter = new PresenterLeaveApproval(this, LeaveApprovalListActivity.this);
        rejectbinding = PopRejectRemarksBinding.inflate(getLayoutInflater());
        detailsbinding = PopDetailsLeaveBinding.inflate(getLayoutInflater());
        lbinding = ComponentLoadingbarBinding.inflate(getLayoutInflater());
        prepareLoadingBar();
        session = dbCrudHelper.getSession();
        empid = String.valueOf(session.getEmpInfoId());
        RoleTypeId = session.getRoleTypeId();
        RoleType = session.getRoleType();
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            dbCrudHelper = new DBCrudHelper(this);
            tagT = ".EmpTerrId=";
            tagA = ".EmpAreaId=";
            switch (RoleType) {
                case "MIO":
                    int tid = dbCrudHelper.getCurrentUserTeriId_SQLite();
                    Teriid = String.valueOf(tid);
                    params = "AND View_Webapi_EmployeeFieldForceInfo" + tagT + Teriid;
                    break;
                case "AM":
                    Areaid = String.valueOf(dbCrudHelper.getCurrentUserAreaId_SQLite());
                    params = "AND View_Webapi_EmployeeFieldForceInfo" + tagA + Areaid;
                    break;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Map<String, String> filter = new HashMap<>();
        filter.put("Role", RoleType);
        filter.put("AppStatus", "0");
        filter.put("FromDt", "");
        filter.put("ToDt", "");
        filter.put("EmpId", "");
        pd_process.show();
        pd_process.setCancelable(false);
        lbinding.tvText.setText("Loading...");
        presenter.getLeaveApprovalList(params, filter);

        binding.swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getLeaveApprovalList(params, filter);
                binding.swip.setRefreshing(false);
            }
        });
        openFilter();
        binding.atteFilter.setOnClickListener(v -> {
            bsheetdlg.show();
        });
    }
    private void prepareLoadingBar() {
        pd_process=new Dialog(LeaveApprovalListActivity.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;
    }
    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(LeaveApprovalListActivity.this);
        bsheetdlg.setContentView(ftm.getRoot());
        bsheetdlg.setCanceledOnTouchOutside(true);
        //bsheetdlg.show();
        switch (RoleType) {
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
        ftm.linearLayoutstatus.setVisibility(VISIBLE);

        List<String> emptype = new ArrayList<>();
        switch (RoleType) {
            case "MIO":
                emptype.add("Select");
                emptype.add("MBE");
                try {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(LeaveApprovalListActivity.this, android.R.layout.simple_spinner_item, emptype);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(LeaveApprovalListActivity.this, android.R.layout.simple_spinner_item, emptype);
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
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(LeaveApprovalListActivity.this, android.R.layout.simple_spinner_item, mbeList);
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
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(LeaveApprovalListActivity.this, android.R.layout.simple_spinner_item, mioList);
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(LeaveApprovalListActivity.this, android.R.layout.simple_spinner_item, asmList);
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
                        ModelMBE mbeempid;
                        mbeempid = (ModelMBE) ftm.spinnerApprovalMbe.getSelectedItem();
                        empid = String.valueOf(mbeempid.getMBEEmpId());
                        break;
                    case "ABM":
                        ModelMIO mioempid;
                        mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
                        empid = String.valueOf(mioempid.getMIOEmpId());
                        break;
                }

                status = (String) ftm.spinnerApprovalStatus.getSelectedItem();
                Map<String, String> filters = new HashMap<>();
                filters.put("Role", RoleType);
                if (status.equals("Select")) {
                    filters.put("AppStatus", "");
                } else {
                    switch (status) {
                        case "Pending":
                            status = "0";
                            break;
                        case "Approved":
                            status = "2";
                            break;
                        case "Rejected":
                            status = "3";
                            break;
                    }
                    filters.put("AppStatus", status);
                }
                filters.put("FromDt", "");
                filters.put("ToDt", "");
                if(empid==null)
                {
                    filters.put("EmpId","");
                }else {
                    filters.put("EmpId",empid);
                }
                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Loading...");
                presenter.getLeaveApprovalList(params, filters);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            bsheetdlg.cancel();
        });
        ftm.filterTopLayout.ivCancel.setOnClickListener(v -> {
            bsheetdlg.cancel();
        });
    }
    @Override
    public void onGetLeaveApprovalList(List<LeaveApprovalData> aList) {
        if (pd_process != null || pd_process.isShowing()) {
            pd_process.dismiss();
        }
        if (aList != null) {
            binding.count.setText(String.valueOf(aList.size()));
            leaveAdapter = new LeaveApprovalAdapter(LeaveApprovalListActivity.this, aList,this);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.rvLeavelist.setLayoutManager(mLayoutManager);
            binding.rvLeavelist.setItemAnimator(new DefaultItemAnimator());
            binding.rvLeavelist.setAdapter(leaveAdapter);
            binding.rvLeavelist.setItemAnimator(null);
            binding.rvLeavelist.scrollToPosition(0);
            leaveAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveSuccess(String message) {
        if (pd_process != null || pd_process.isShowing()) {
            pd_process.dismiss();
        }
        if(!message.isEmpty())
        {
            SnackBarManagement._success_CustomMessage(binding.getRoot(),message);
            Map<String, String> filter = new HashMap<>();
            filter.put("Role", RoleType);
            filter.put("AppStatus", "0");
            filter.put("FromDt", "");
            filter.put("ToDt", "");
            filter.put("EmpId", "");
            pd_process.show();
            pd_process.setCancelable(false);
            lbinding.tvText.setText("Loading...");
            presenter.getLeaveApprovalList(params, filter);
        }

    }

    @Override
    public void onError(String message) {
        if (pd_process != null || pd_process.isShowing()) {
            pd_process.dismiss();
        }
        if(!message.isEmpty())
        {
            SnackBarManagement._error_CustomMessage(binding.getRoot(),message);
            Map<String, String> filter = new HashMap<>();
            filter.put("Role", RoleType);
            filter.put("AppStatus", "0");
            filter.put("FromDt", "");
            filter.put("ToDt", "");
            filter.put("EmpId", "");
            pd_process.show();
            pd_process.setCancelable(false);
            lbinding.tvText.setText("Loading...");
            presenter.getLeaveApprovalList(params, filter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public void approveClick(LeaveApprovalData ap, int pos) {
        LeaveApprovalRQ req = new LeaveApprovalRQ();
        int step = ap.getStep();
        int fstep = step + 1;
        req.setLeaveApprovalId(0);
        req.setFromEmpId(Integer.parseInt(empid));
        req.setToEmpId(next);
        req.setTableId(ap.getLeaveApplicationId());
        req.setStatus("Verified");//Accepted==approve for Admin
        req.setType(ap.getType());
        req.setStep(fstep);
        req.setEntryByApp(String.valueOf(empid));
        String entrydate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());
        String entrytime = new SimpleDateFormat("hh:mm a", Locale.US).format(new Date());
        req.setEntryDateApp(entrydate);
        req.setEntryTimeApp(entrytime);
        req.setMenuId(1378);
        pd_process.show();
        pd_process.setCancelable(false);
        lbinding.tvText.setText("Processing...");
        presenter.SaveLeaveApproval(req);

    }

    @Override
    public void disApproveClick(LeaveApprovalData ap, int pos) {
        rejectDialog(ap,pos);
    }

    @Override
    public void viewClick(LeaveApprovalData ap, int pos) {
        detailsDialog(ap,pos);
    }

    private void rejectDialog(LeaveApprovalData ap, int pos) {
        drejectRemark = new Dialog(LeaveApprovalListActivity.this);
        drejectRemark.setContentView(rejectbinding.getRoot());
        drejectRemark.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        drejectRemark.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        drejectRemark.getWindow().getAttributes().gravity = Gravity.CENTER;
        drejectRemark.show();

        rejectbinding.btnSubmit.setOnClickListener(view -> {
           String remarks= rejectbinding.remarksTxt.getText().toString().trim();
            if (remarks.equals("")) {
                rejectbinding.remarksTxt.setError("Remarks Require");
                rejectbinding.remarksTxt.setFocusable(true);
            }
            else {
                LeaveApprovalRQ req = new LeaveApprovalRQ();

                //No step
               /* int step = aInfoData.getStep();
                int fstep = step + 1;*/
                req.setLeaveApprovalId(0);
                req.setFromEmpId(session.getEmpInfoId());
                req.setToEmpId(next);
                req.setTableId(ap.getLeaveApplicationId());
                req.setStatus("Rejected");//Accepted==approve for admin
                req.setType(ap.getType());
                req.setStep(1);
                req.setEntryByApp(String.valueOf(empid));
                String entrydate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());
                String entrytime = new SimpleDateFormat("hh:mm a", Locale.US).format(new Date());
                req.setEntryDateApp(entrydate);
                req.setEntryTimeApp(entrytime);
                req.setMenuId(1378);
                req.setComments(remarks);
                drejectRemark.dismiss();

                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Processing...");
                presenter.SaveLeaveApproval(req);
            }
        });
    }
    private void detailsDialog(LeaveApprovalData ap, int pos) {
        dDetails = new Dialog(LeaveApprovalListActivity.this);
        dDetails.setContentView(detailsbinding.getRoot());
        dDetails.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dDetails.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        dDetails.getWindow().getAttributes().gravity = Gravity.CENTER;
        dDetails.show();
        try {
            detailsbinding.prescEnameTv.setText(ap.getEmpName());
            detailsbinding.leaveDateTv.setText(ap.getEntryDate());
            detailsbinding.leaveDnameTv.setText(ap.getLeaveFromDate());
            detailsbinding.leaveReturnTv.setText(ap.getDateOfReturnsToDuty());
            detailsbinding.leaveTypeTv.setText(ap.getType());
            detailsbinding.leaveAddressTv.setText(ap.getLeaveAddress());
            detailsbinding.leaveContactTv.setText(ap.getEmergencyContactNo());
            detailsbinding.leaveReason.setText(ap.getReason());
            detailsbinding.leaveComment.setText(ap.getRemarks());

            if(ap.getImageString().equals(""))
            {

            }else {
                byte[] decodedString = Base64.decode(ap.getImageString(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                detailsbinding.prescImg.setImageBitmap(decodedByte);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        detailsbinding.ivClose.setOnClickListener(view -> {
            dDetails.dismiss();
        });
    }
}