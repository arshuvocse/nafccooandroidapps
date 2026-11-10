package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Rx;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.icu.lang.UCharacter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterReportRxDetails;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRxDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.OrderTracking.OrderTrackingActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentRxDetailsBinding;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRxDetails extends Fragment implements AdapterReportRxDetails.onClickImg {

    FragmentRxDetailsBinding binding;
    AdapterReportRxDetails adapter;
    Dialog dialog;
    String empid;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    BottomSheetDialog bsheetdlg;

    FilterMasterBinding ftm;
    String role, empId,emp_id;
    int selectedMonth, selectedyear;
    String selectedTyp = "";
    List<ModelMBE> mbeList;
    public FragmentRxDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_rx_details, container, false);
        binding = FragmentRxDetailsBinding.inflate(getLayoutInflater());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        dbCrudHelper = new DBCrudHelper(requireActivity());
        session = dbCrudHelper.getSession();
        role = session.getEmpRole();
        empid = String.valueOf(session.getEmpInfoId());

        Map<String, String> filter = new HashMap<>();
        filter.put("EmpId", empid);
        filter.put("frmDate", UtilityHelper._GetCurrentDate());
        filter.put("toDate", UtilityHelper._GetCurrentDate());
        callApi(filter);
        binding.swip.setOnRefreshListener(() -> {
            Map<String, String> filters = new HashMap<>();
            filters.put("EmpId", empid);
            filters.put("frmDate", UtilityHelper._GetCurrentDate());
            filters.put("toDate", UtilityHelper._GetCurrentDate());
            callApi(filters);
            binding.swip.setRefreshing(false);
        });
        openFilter();
        binding.btnFilter.setOnClickListener(view -> {
            bsheetdlg.show();
        });
        return binding.getRoot();
    }

    private void callApi(Map<String, String> filter) {
        if (NetworkInformation.isConnected(getActivity())) {
            ProgressDialog pd = new ProgressDialog(requireContext());
            pd.setMessage("Preparing Report...");
            pd.show();
            try {
                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<ModelRxDetails>> call = service.GetRxDetails(filter);
                call.enqueue(new Callback<List<ModelRxDetails>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelRxDetails>> call, @NonNull Response<List<ModelRxDetails>> response) {
                        Log.d("TAG", "onResponse: " + response.body());
                        System.out.println("resp: " + response.body());
                        if (response.body() != null && response.body().size() > 0) {
                            if(pd!=null || pd.isShowing())
                            {
                                pd.dismiss();
                            }
                            List<ModelRxDetails> approval = response.body();
                            System.out.println("Response :" + approval);
                            onSuccessReport(approval);

                        } else {
                            if(pd!=null || pd.isShowing())
                            {
                                pd.dismiss();
                            }
                            onSuccessReport(null);
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelRxDetails>> call, @NonNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            if(pd!=null || pd.isShowing())
                            {
                                pd.dismiss();
                            }
                            onError("Slow Connection Detected. Please try again", 1);
                        } else {
                            if(pd!=null || pd.isShowing())
                            {
                                pd.dismiss();
                            }
                            onError("Something went wrong... Please try again", 1);
                        }
                    }
                });

            } catch (Exception ex) {
                if(pd!=null || pd.isShowing())
                {
                    pd.dismiss();
                }
                String str = ex.toString();
                Log.e("Exception", str);
                onError(ex.toString(), 1);
            }
        } else {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "No Internet");
        }
    }
    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(getActivity());
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
        ftm.filterMonthYearLayout.getRoot().setVisibility(VISIBLE);

        List<String> emptype = new ArrayList<>();
        switch (role) {
            case "MIO":
                emptype.add("Select");
                emptype.add("MBE");
                try {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, emptype);
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, emptype);
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
                ArrayAdapter<String> dataAdapterrsm = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, emptype);
                dataAdapterrsm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.spinnerEmployeeType.setAdapter(dataAdapterrsm);
                break;
            case "NSM":
                emptype.add("Select");
                emptype.add("MBE");
                emptype.add("ABM");
                emptype.add("RBM");
                emptype.add("Cluster Head");
                ArrayAdapter<String> dataAdapternsm = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, emptype);
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
                                ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mbeList);
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
                                ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mioList);
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, asmList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalAsm.setAdapter(dataAdapter);

                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "No AM Founded!!");
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
                                ArrayAdapter<ModelRSM> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, rsmList);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ftm.spinnerApprovalRsm.setAdapter(dataAdapter);
                            } else {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "No AM Founded!!");
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
        ftm.filterMonthYearLayout.ivDatePickerFromDate.setVisibility(VISIBLE);
        ftm.filterMonthYearLayout.ivDatePickerToDate.setVisibility(VISIBLE);
        ftm.filterMonthYearLayout.ivDatePickerFromDate.setOnClickListener(v -> UtilityHelper._datePickerDialogeForDates(ftm.filterMonthYearLayout.txtFromDate, requireActivity()));
        ftm.filterMonthYearLayout.ivDatePickerToDate.setOnClickListener(v -> {
            if (ftm.filterMonthYearLayout.txtFromDate.getText().toString().isEmpty()) {
                SnackBarManagement._warning_CustomMessage(ftm.filterMaster, "From Date is Require");
            } else {
                UtilityHelper._datePickerDialogeForDates(ftm.filterMonthYearLayout.txtToDate, requireActivity());
            }
        });
        ftm.llmonthyear.setVisibility(GONE);


        ftm.filterTopLayout.btnDonefilter.setOnClickListener(v -> {
            try {
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
                        ModelMBE mbeempid = (ModelMBE) ftm.spinnerApprovalMbe.getSelectedItem();
                        //empId = String.valueOf(mbeempid.getMBEEmpId());
                        emp_id = String.valueOf(mbeempid.getMBEEmpId());
                        break;
                    case "ABM":
                        ModelMIO mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
                        emp_id = String.valueOf(mioempid.getMIOEmpId());
                        break;
                    case "RBM":
                        ModelASM amempid = (ModelASM) ftm.spinnerApprovalAsm.getSelectedItem();
                        emp_id = String.valueOf(amempid.getASMEmpId());
                        break;
                    case "Cluster Head":
                        ModelRSM dzsmempid = (ModelRSM) ftm.spinnerApprovalRsm.getSelectedItem();
                        emp_id = String.valueOf(dzsmempid.getRSMEmpId());
                        break;
                }
                if (TextUtils.isEmpty(ftm.filterMonthYearLayout.txtFromDate.getText().toString())) {
                    ftm.filterMonthYearLayout.txtFromDate.setError("From Date can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(ftm.filterMonthYearLayout.txtToDate.getText().toString())) {
                    ftm.filterMonthYearLayout.txtToDate.setError("To Date can't be empty");
                    return;
                }

                Map<String, String> filters = new HashMap<>();
                if(TextUtils.isEmpty(emp_id))
                {
                    filters.put("EmpId", empid);
                }else {
                    filters.put("EmpId", emp_id);
                }
                filters.put("frmDate", ftm.filterMonthYearLayout.txtFromDate.getText().toString().trim());
                filters.put("toDate", ftm.filterMonthYearLayout.txtToDate.getText().toString().trim());

                Gson gson = new Gson();
                String data = gson.toJson(filters);
                System.out.println("rxdetails : "+data);
                callApi(filters);

            } catch (Exception exception) {

            }
            bsheetdlg.cancel();
        });
        ftm.filterTopLayout.ivCancel.setOnClickListener(v -> {
            bsheetdlg.cancel();
        });
    }





    private void onError(String s, int i) {
        SnackBarManagement._error_CustomMessage(binding.getRoot(), s);
    }

    private void onSuccessReport(List<ModelRxDetails> rlist) {
        adapter = new AdapterReportRxDetails(rlist, getContext(),this);
        binding.rvRxdetails.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rvRxdetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onImageClick(String mg) {
        dialog=new Dialog(requireActivity(),android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        dialog.setContentView(R.layout.view_image);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        ImageViewZoom iv=dialog.findViewById(R.id.iv_image_details);
        ImageView iv_close=dialog.findViewById(R.id.iv_close);



        Glide.with(requireContext())
                .load(mg)
                .fitCenter()
                //.placeholder(R.drawable.loading_spinner)
                .into(iv);
        iv_close.setOnClickListener(view -> dialog.dismiss());
    }
}