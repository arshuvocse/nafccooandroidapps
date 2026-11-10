package com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.TPApprovalSaveBody;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.TourPlanApprovalData;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.Team;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITPApproval;
import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.Presentar.TPApprovalPresenter;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SessionManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTourPlanApprovalBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.LayoutTphqTableBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class TourPlanApprovalActivity extends AppCompatActivity implements ITPApproval.View, Rcv_TourPlanListener {
    ProgressDialog pd;
    String yearList[] = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    TourPlanApprovalAdapter mAdapter;
    ActivityTourPlanApprovalBinding binding;
    SessionManagement session;
    BottomSheetDialog bsheetdlg;
    LayoutTphqTableBinding tphqbinding;
    int empId;
    private int tourCount = 0;
    private int initCount = 0;

    Dialog popComment;
    private int prev = 0;
    private int current = 0;
    private int next = 0;
    private int myrole = 0;
    DBCrudHelper dbCrudHelper;
    int RoleTypeId, empid;
    String prev_roleType, next_roleType;
    Button submitCmnt;
    Map<String, String> filter;
    String fromdat, todate, RoleType, params, tagA, tagR, tagN, Areaid, Regionid, Groupid, selectedTyp = "";
    Gson gson = new Gson();
    Team team;
    TPApprovalPresenter presenter;
    List<TourPlanApprovalData> dataList;
    String hq = "0";
    String xhq = "0";
    String os = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTourPlanApprovalBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_tour_plan_approval);
        setContentView(binding.getRoot());
        tphqbinding = LayoutTphqTableBinding.inflate(getLayoutInflater());
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pd=new ProgressDialog(TourPlanApprovalActivity.this);
        presenter = new TPApprovalPresenter(this, TourPlanApprovalActivity.this);
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        empid = Integer.parseInt(Objects.requireNonNull(user.get(SessionManagement.KEY_EmpId)));
        RoleType = user.get(SessionManagement.KEY_EmpRoleType);
        RoleTypeId = Integer.parseInt(Objects.requireNonNull(user.get(SessionManagement.KEY_EmpRoleTypeId)));

        team = gson.fromJson(getIntent().getStringExtra("teamdat"), Team.class);
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpinner.setAdapter(dataAdapter);
        int yearPos = dataAdapter.getPosition(String.valueOf(year));
        binding.yearSpinner.setSelection(yearPos);
        try {
            tagA = ".EmpAreaId=";
            tagR = ".EmpRegionId=";
            tagN = ".EmpGroupId=";
            dbCrudHelper = new DBCrudHelper(this);
            switch (RoleType) {
                case "AM":
                    Areaid = String.valueOf(dbCrudHelper.getCurrentUserAreaId_SQLite());
                    params = "AND View_Webapi_EmployeeFieldForceInfo" + tagA + Areaid;
                    break;
                case "DZSM":
                    Regionid = String.valueOf(dbCrudHelper.getCurrentUserRegionId_SQLite());
                    params = "AND View_Webapi_EmployeeFieldForceInfo" + tagR + Regionid;
                    break;
                case "NSM":
                    Groupid = String.valueOf(dbCrudHelper.getCurrentUserGroupId_SQLite());
                    params = "AND View_Webapi_EmployeeFieldForceInfo" + tagN + Groupid;
                    break;
                case "Admin":
                    params = "";
                    break;
            }


        } catch (Exception exception) {
          //  exception.printStackTrace();
        }

        initCommentPop();
        //  binding.approveLayout.btnReject.setOnClickListener(v -> {
        binding.btnReject.setOnClickListener(v -> {
            popComment.show();
        });

        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthNameArray);
        dataAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthSpinner.setAdapter(dataAdapterMonth);

        String monName = monthNameArray[month];
        int monthPos = dataAdapterMonth.getPosition(monName);
        binding.monthSpinner.setSelection(monthPos);
        binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // List<MonthDate> aMondateList = new ArrayList<>();
                int yearV = Integer.parseInt(binding.yearSpinner.getSelectedItem().toString());
                int monthV = i + 1;
                //  aMondateList = printDatesInMonth(yearV, i);
                HashMap<String, String> filter = new HashMap<>();
                filter.put("Role", RoleType);
                filter.put("AppStatus", "");
                filter.put("FromDt", "");
                filter.put("ToDt", "");
                filter.put("EmpId", String.valueOf(team.getEmpInfoId()));
                filter.put("MonthValue", String.valueOf(monthV));
                filter.put("YearValue", String.valueOf(yearV));
                hitApi(filter);
                //initCount++;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        binding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initCount > 0) {
                    //  List<MonthDate> aMondateList = new ArrayList<>();
                    int monthV = binding.monthSpinner.getSelectedItemPosition();
                    int yearV = Integer.parseInt(yearList[position].toString());
                    // aMondateList = printDatesInMonth(yearV, (monthV + 1));
                   HashMap<String ,String > filter = new HashMap<>();
                    filter.put("Role", RoleType);
                    filter.put("AppStatus", "");
                    filter.put("FromDt", "");
                    filter.put("ToDt", "");
                    filter.put("EmpId", String.valueOf(team.getEmpInfoId()));
                    filter.put("MonthValue", String.valueOf((monthV + 1)));
                    filter.put("YearValue", String.valueOf(yearV));
                    hitApi(filter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        openDetails();
        binding.statusClick.setOnClickListener(v -> bsheetdlg.show());
    }

    private void hitApi(HashMap<String, String> filter) {
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        presenter.getTPApprovalList(params,filter);
    }

    private void openDetails() {
        tphqbinding = LayoutTphqTableBinding.inflate(getLayoutInflater());
        bsheetdlg = new BottomSheetDialog(TourPlanApprovalActivity.this);
        bsheetdlg.setContentView(tphqbinding.getRoot());
        // bsheetdlg.setContentView(R.layout.filter_master);
        bsheetdlg.setCanceledOnTouchOutside(true);
        tphqbinding.hqvalue.setText(hq);
        tphqbinding.xhqvalue.setText(xhq);
        tphqbinding.osvalue.setText(os);
    }


    @Override
    public void ReloadCurrentActivity() {
    }

    @Override
    public void onGetTPApprovalList(List<TourPlanApprovalData> aList) {
        if(pd!=null||pd.isShowing())
        {
            pd.dismiss();
        }
        if (aList != null) {
            dataList = aList;
            binding.rvTourplan.setVisibility(View.VISIBLE);

            if (RoleTypeId == 2) {
                prev = aList.get(0).getMIOEmpId();
                prev_roleType = "MIO";
                current = aList.get(0).getASMEMPId();
                next = aList.get(0).getRSMEMPId();
                next_roleType = "AM";
                myrole = 2;
            }
            if (RoleTypeId == 3) {
                prev = aList.get(0).getASMEMPId();
                prev_roleType = "AM";
                current = aList.get(0).getRSMEMPId();
                next = aList.get(0).getNSMEMPId();
                next_roleType = "DZSM";
                myrole = 3;
            }
            if (RoleTypeId == 4) {
                prev = aList.get(0).getRSMEMPId();
                prev_roleType = "DZSM";
                current = aList.get(0).getNSMEMPId();
                next_roleType = "ADMIN";
                next = 0;
                myrole = 4;
            }
            if (RoleTypeId == 5) {
                myrole = 5;
            }

            tphqbinding.hqvalue.setText(aList.get(0).getaTourPlanMaster().getHQ());
            tphqbinding.xhqvalue.setText(aList.get(0).getaTourPlanMaster().getExHQ());
            tphqbinding.osvalue.setText(aList.get(0).getaTourPlanMaster().getOS());

            hq = aList.get(0).getaTourPlanMaster().getHQ();
            xhq = aList.get(0).getaTourPlanMaster().getExHQ();
            os = aList.get(0).getaTourPlanMaster().getOS();
            mAdapter = new TourPlanApprovalAdapter(aList.get(0).getaTourPlanMaster().getaTourPlanDate(), TourPlanApprovalActivity.this, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.rvTourplan.setLayoutManager(mLayoutManager);
            binding.rvTourplan.setItemAnimator(new DefaultItemAnimator());
            binding.rvTourplan.setAdapter(mAdapter);
            binding.rvTourplan.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            binding.rvTourplan.setItemAnimator(null);
            binding.rvTourplan.scrollToPosition(0);
            mAdapter.notifyDataSetChanged();
            if (prev == current) {
                if (aList.get(0).getRoleTypeId() == RoleTypeId) {
                    binding.linearLayout5.setVisibility(View.GONE);
                    binding.btnApprove.setVisibility(View.GONE);
                    binding.btnReject.setVisibility(View.GONE);

                    binding.warnToast.setVisibility(View.VISIBLE);
                    binding.warnToast.setText("Waiting For Final Approval");
                    binding.warnToast.setBackgroundResource(R.drawable.shape_approved);
                } else {
                    //binding.approveLayout.btnApprove.setVisibility(View.VISIBLE);
                    binding.linearLayout5.setVisibility(View.VISIBLE);
                }
            } else {
                if (aList.get(0).getToRoleTypeId() == RoleTypeId) {
                    binding.linearLayout5.setVisibility(View.VISIBLE);
                    binding.btnApprove.setVisibility(View.VISIBLE);
                    binding.btnReject.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout5.setVisibility(View.GONE);
                    binding.btnApprove.setVisibility(View.GONE);
                    binding.btnReject.setVisibility(View.GONE);
                    if (aList.get(0).getRoleTypeId() >= RoleTypeId) {
                        binding.warnToast.setVisibility(View.VISIBLE);
                        binding.warnToast.setText("Waiting For Final Approval");
                        // binding.approveLayout.approvemaster.setVisibility(View.GONE);
                        binding.warnToast.setBackgroundResource(R.drawable.shape_approved);
                    } else {
                        binding.warnToast.setVisibility(View.VISIBLE);
                        binding.warnToast.setText("Need To Approved By " + prev_roleType);
                        //binding.approveLayout.approvemaster.setVisibility(View.GONE);
                        binding.warnToast.setBackgroundResource(R.drawable.shape_pending);
                    }
                }

            }
            if (aList.get(0).getStatus().equals("Accepted")) {
                binding.warnToast.setVisibility(View.GONE);
                binding.linearLayout5.setVisibility(View.VISIBLE);
                binding.btnApprove.setVisibility(View.GONE);
                binding.btnReject.setVisibility(View.VISIBLE);
            }
            binding.btnApprove.setOnClickListener(v -> {
                TPApprovalSaveBody req = new TPApprovalSaveBody();
                int step = aList.get(0).getStep();
                int fstep = step + 1;
                req.setTourPlanApprovalId(0);
                req.setFromEmpId(empid);
                req.setToEmpId(next);
                req.setTableId(aList.get(0).getTPMaster());
                req.setStatus("Verified");//Accepted==approve for Admin
                req.setType(aList.get(0).getType());
                req.setStep(fstep);
                req.setEntryByApp(empid);
                String entrydate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());
                String entrytime = new SimpleDateFormat("hh:mm a", Locale.US).format(new Date());
                req.setEntryDateApp(entrydate);
                req.setEntryTimeApp(entrytime);
                req.setMenuId(371);
                presenter.SaveTPApproval(req);
            });
        } else {
            SnackBarManagement._warning_CustomMessage(binding.masterLayout, "No Tour Found");
        }
    }

    @Override
    public void onSaveSuccess(String message) {
        if (message.equals("Verified")) {
            new AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            session = new SessionManagement(getApplicationContext());
                            ((AlertDialog) dialog).getButton(which).setVisibility(View.INVISIBLE);
                            onBackPressed();
                            finish();
                        }
                    }).setCancelable(false).show();
        }
    }

    @Override
    public void onError(String message) {
        if(pd!=null||pd.isShowing())
        {
            pd.dismiss();
        }
        binding.rvTourplan.setVisibility(View.GONE);
        binding.warnToast.setVisibility(View.GONE);
        binding.btnApprove.setVisibility(View.GONE);
        binding.btnReject.setVisibility(View.GONE);
        SnackBarManagement._warning_CustomMessage(binding.masterLayout, message);
    }

    public void initCommentPop() {
        popComment = new Dialog(TourPlanApprovalActivity.this);
        popComment.setContentView(R.layout.pop_comment);
        //popAddQty.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popComment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popComment.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popComment.getWindow().getAttributes().gravity = Gravity.CENTER;


        final EditText userComment = (EditText) popComment.findViewById(R.id.remarksTxt);
        userComment.requestFocus();
        submitCmnt = popComment.findViewById(R.id.psubmitBnt);
        submitCmnt.setOnClickListener(v1 -> {
            if (userComment.getText().toString().equals("")) {
                userComment.setError("Comment Must");
                userComment.setFocusable(true);
            } else {
                TPApprovalSaveBody req = new TPApprovalSaveBody();

                int step = dataList.get(0).getStep();
                int fstep = step + 1;
                req.setTourPlanApprovalId(0);
                req.setFromEmpId(empid);
                req.setToEmpId(next);
                req.setTableId(dataList.get(0).getTPMaster());
                req.setStatus("Rejected");//Accepted==approve for admin
                req.setType(dataList.get(0).getType());
                req.setStep(fstep);
                req.setEntryByApp(empid);
                String entrydate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());
                String entrytime = new SimpleDateFormat("hh:mm a", Locale.US).format(new Date());
                req.setEntryDateApp(entrydate);
                req.setEntryTimeApp(entrytime);
                req.setMenuId(371);
                req.setComments(userComment.getText().toString());
                presenter.SaveTPApproval(req);

                SnackBarManagement._success_CustomMessage(v1, "Rejected");
                popComment.dismiss();
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }
}