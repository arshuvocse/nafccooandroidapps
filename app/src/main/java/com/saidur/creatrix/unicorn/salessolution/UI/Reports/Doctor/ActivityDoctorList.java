package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Doctor;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.DoctorAdapter;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.ActivityDCPApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.ActivityTwwpApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Doctor.Adapter.AdapterDoctorReport;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDoctorListBinding;
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

public class ActivityDoctorList extends AppCompatActivity {
ActivityDoctorListBinding binding;
    DBDoctorHelper dbDoctorHelper;
    DBCrudHelper dbCrudHelper;
    AdapterDoctorReport adapter;
    ModelSession session;
    String selectedTyp = "", role,RoleType,empId,emp_Id;
    BottomSheetDialog bsheetdlg;
    FilterMasterBinding ftm;
    List<ModelMBE> mbeList;
    ComponentLoadingbarBinding lbinding;
    Dialog pd_process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDoctorListBinding.inflate(getLayoutInflater());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        lbinding = ComponentLoadingbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbDoctorHelper = new DBDoctorHelper(ActivityDoctorList.this);
        dbCrudHelper = new DBCrudHelper(ActivityDoctorList.this);
        session=dbCrudHelper.getSession();
        empId=String.valueOf(session.getEmpInfoId());
        role = session.getEmpRole();
        RoleType = session.getRoleType();
        openFilter();
        prepareLoadingBar();
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      /*  String who=getIntent().getStringExtra("Who");
        if(who.equals("Report"))
        {
            binding.ivFilter.setVisibility(View.VISIBLE);
            callApi(Integer.parseInt(empId));
        }else {
            List<DoctorListViewModel> data =dbDoctorHelper.getDoctorListFromSQLite();
            LoadDoctor(data);
        }*/
        switch (role) {
            case "MBE":
                binding.ivFilter.setVisibility(GONE);
                List<DoctorListViewModel> data =dbDoctorHelper.getDoctorListFromSQLite();
                LoadDoctor(data);
                break;
            case "MIO":
            case "AM":
            case "DZSM":
            case "NSM":
                binding.ivFilter.setVisibility(View.VISIBLE);
                callApi(Integer.parseInt(empId));
                break;
        }
        binding.ivFilter.setOnClickListener(v -> {
            bsheetdlg.show();
        });
    }

    private void callApi(int empInfoId) {
        if(NetworkInformation.isConnected(ActivityDoctorList.this))
        {
            binding.rvDoctorlist.setVisibility(VISIBLE);
            binding.llNo.setVisibility(GONE);


            pd_process.show();
            pd_process.setCancelable(true);
            pd_process.setCanceledOnTouchOutside(true);
            lbinding.tvText.setText("Loading...");
            try {
                ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
                Call<List<DoctorListViewModel>> call = service.GetDoctorList(empInfoId);
                call.enqueue(new Callback<List<DoctorListViewModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<DoctorListViewModel>> call, @NonNull Response<List<DoctorListViewModel>> response) {
                        if (pd_process != null && pd_process.isShowing()) {
                            pd_process.dismiss();
                        }
                        binding.llNo.setVisibility(GONE);
                        List<DoctorListViewModel> doctors = response.body();
                        if (doctors != null && doctors.size()>0) {
                            binding.rvDoctorlist.setVisibility(VISIBLE);
                            binding.llNo.setVisibility(GONE);
                            LoadDoctor(doctors);
                        }else {
                            binding.rvDoctorlist.setVisibility(GONE);
                            binding.llNo.setVisibility(VISIBLE);
                            binding.ivNo.setImageResource(R.drawable.src_nodata);
                            binding.tvNo.setText("Data Not Found!!");
                            binding.toolbarCount.setText("(0)");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<DoctorListViewModel>> call, @NonNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            if (pd_process != null && pd_process.isShowing()) {
                                pd_process.dismiss();
                            }
                            //viewBindings.docTxt.setText("Slow Network detected");
                        } else {
                            // viewBindings.docTxt.setText("Some Error occured");

                        }


                    }
                });
            } catch (Exception ex) {
            }
        }else {
            binding.rvDoctorlist.setVisibility(GONE);
            binding.llNo.setVisibility(VISIBLE);
            binding.ivNo.setImageResource(R.drawable.src_nointernet);
            binding.tvNo.setText("No Internet!!");
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet");
        }

    }
    private void LoadDoctor(List<DoctorListViewModel> docList) {
        if(docList.size()>0)
        {
            binding.toolbarCount.setText(" ("+String.valueOf(docList.size())+")");
        }else {
            binding.toolbarCount.setText("(0)");
        }

        adapter = new AdapterDoctorReport(ActivityDoctorList.this, docList);
        binding.rvDoctorlist.setLayoutManager(new LinearLayoutManager(ActivityDoctorList.this, RecyclerView.VERTICAL, false));
        binding.rvDoctorlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void prepareLoadingBar() {
        pd_process=new Dialog(ActivityDoctorList.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;

    }
    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(ActivityDoctorList.this);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityDoctorList.this, android.R.layout.simple_spinner_item, emptype);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ActivityDoctorList.this, android.R.layout.simple_spinner_item, emptype);
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
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(ActivityDoctorList.this, android.R.layout.simple_spinner_item, mbeList);
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
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(ActivityDoctorList.this, android.R.layout.simple_spinner_item, mioList);
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(ActivityDoctorList.this, android.R.layout.simple_spinner_item, asmList);
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
        ftm.llmonthyear.setVisibility(GONE);



        ftm.filterTopLayout.btnDonefilter.setOnClickListener(v -> {
            try {
                switch (selectedTyp) {
                    case "MBE":
                        ModelMBE mbeempid;
                        mbeempid = (ModelMBE) ftm.spinnerApprovalMbe.getSelectedItem();
                        emp_Id = String.valueOf(mbeempid.getMBEEmpId());
                        break;
                    case "ABM":
                        ModelMIO mioempid;
                        mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
                        emp_Id = String.valueOf(mioempid.getMIOEmpId());

                        break;
                }

                /*HashMap<String, String> filterz = new HashMap<>();
                filterz.put("Role", role);
                filterz.put("empId", empId);
                filterz.put("AppStatus", "");
                filterz.put("Month", String.valueOf(selectedMonth));
                filterz.put("Year", String.valueOf(selectedyear));*/
                if(TextUtils.isEmpty(emp_Id))
                {
                    callApi(Integer.parseInt(empId));
                }else {
                    callApi(Integer.parseInt(emp_Id));
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
}