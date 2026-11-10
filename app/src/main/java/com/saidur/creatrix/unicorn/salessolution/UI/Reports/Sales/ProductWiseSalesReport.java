package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Adapter.AdapterSalesReportProduct;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model.PWSData;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model.PWSaleReportModel;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentProductWiseSalesReportBinding;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductWiseSalesReport extends Fragment {
    FragmentProductWiseSalesReportBinding binding;
    AdapterSalesReportProduct srpAdapter;
    ProgressDialog progressDoalog;
    BottomSheetDialog bsheetdlg;
    FilterMasterBinding ftm;
    String role, empId, emp_id, today;
    int selectedGrpId, selectedZoneId, selectedAreaId, selectedTeriId, selectedSTeriId, selectedMarketId;
    int selectedMonth, selectedyear;
    ModelSession session;
    String selectedTyp = "";
    DBCrudHelper dbCrudHelper;
    List<ModelMBE> mbeList;

    public ProductWiseSalesReport() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductWiseSalesReportBinding.inflate(getLayoutInflater());
        //return inflater.inflate(R.layout.fragment_customer_wise_sales_report, container, false);
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        dbCrudHelper = new DBCrudHelper(getActivity());
        session = dbCrudHelper.getSession();
        empId = String.valueOf(session.getEmpInfoId());
        role = session.getEmpRole();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        HashMap<String, String> filter = new HashMap<>();
        filter.put("Role", role);
        filter.put("empId", empId);
        filter.put("Month", String.valueOf(month + 1));
        filter.put("Year", String.valueOf(year));
        switch (Constants.From) {
            case "Sales":
                filter.put("type", "Sales");
                break;
            case "Order":
                filter.put("type", "Order");
                break;
        }
        LoadData(filter);


        binding.cfilter.setOnClickListener(v -> {
            // bsheetdlg.show();
            openFilter();
        });
        return binding.getRoot();
    }

    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(getActivity());
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
                        } catch (Exception e) {
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
                                ArrayAdapter<ModelASM> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, asmList);
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
                                ArrayAdapter<ModelRSM> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, rsmList);
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
                        emp_id = String.valueOf(mbeempid.getMBEEmpId());
                        break;
                    case "ABM":
                        ModelMIO mioempid;
                        mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
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

                HashMap<String, String> filters = new HashMap<>();
                filters.put("Role", role);
                if(TextUtils.isEmpty(emp_id))
                {
                    filters.put("empId", empId);
                }else {
                    filters.put("empId", emp_id);
                }
                filters.put("Month", String.valueOf(selectedMonth + 1));
                filters.put("Year", String.valueOf(selectedyear));
                switch (Constants.From) {
                    case "Sales":
                        filters.put("type", "Sales");
                        break;
                    case "Order":
                        filters.put("type", "Order");
                        break;
                }
                System.out.println(filters);
                LoadData(filters);

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
        dialogFragment.show(getActivity().getSupportFragmentManager(), null);

        dialogFragment.setOnDateSetListener((year, monthOfYear) -> {
            ftm.montyer.setText("");
            ftm.montyer.setText(UtilityHelper.monthNameArrayFull[monthOfYear] + ',' + year);

            selectedMonth = monthOfYear;
            selectedyear = year;
        });
    }

    private void LoadData(HashMap<String, String> filter) {
        if (NetworkInformation.isConnected(requireActivity())) {
            try {
                progressDoalog = new ProgressDialog(getActivity());
                progressDoalog.setMessage("Report Loading....");
                progressDoalog.show();
                progressDoalog.setCanceledOnTouchOutside(false);

                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<PWSaleReportModel> call = service.GetProductWiseOrderReport(filter);
                call.enqueue(new Callback<PWSaleReportModel>() {
                    @Override
                    public void onResponse(@NonNull Call<PWSaleReportModel> call, @NonNull Response<PWSaleReportModel> response) {
                        if (progressDoalog != null && progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        if (response.body() != null) {
                            PWSaleReportModel rp = response.body();
                            List<PWSData> data = rp.getPwsDataList();
                            LoadRV(data);
                            LoadView(rp);
                        } else {
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Report Data Not Found!!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PWSaleReportModel> call, @NonNull Throwable t) {
                        if (progressDoalog != null && progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
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

    private void LoadView(PWSaleReportModel rp) {
        binding.totalAmount.setText(rp.getTotal_amount());
        binding.totalQty.setText(rp.getTotal_qty());
        binding.totalOt.setText(rp.getTotal_ot());
    }

    private void LoadRV(List<PWSData> data) {
        if (data != null) {
            srpAdapter = new AdapterSalesReportProduct(data, getContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(srpAdapter);
            binding.recyclerView.setItemAnimator(null);
            binding.recyclerView.scrollToPosition(0);
            srpAdapter.notifyDataSetChanged();
        }
    }
}