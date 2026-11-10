package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Rx;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterReportRxDetails;
import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterReportRxSummay;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRxDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRxSummary;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterProduct;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.PlaceOrderActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentRxDetailsBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentRxSummaryBinding;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRxSummary extends Fragment implements IProduct.View {
    FragmentRxSummaryBinding binding;
    DBCrudHelper dbCrudHelper;
    ModelSession session;
    String empid,emp_id;
    AdapterReportRxSummay adapter;
    BottomSheetDialog bsheetdlg;
    BottomSheetBehavior bottomSheetBehavior;
    FilterMasterBinding ftm;
    String role, empId;
    String selectedTyp = "";
    List<ModelMBE> mbeList;
    int pid = 0;
    IProduct.Presenter presenter;
    ArrayList<ModelProduct> productArrayList;

    public FragmentRxSummary() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRxSummaryBinding.inflate(getLayoutInflater());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        presenter = new PresenterProduct(this, requireActivity());

        dbCrudHelper = new DBCrudHelper(requireActivity());
        session = dbCrudHelper.getSession();
        empid = String.valueOf(session.getEmpInfoId());
        role = session.getEmpRole();
        presenter.getProductFromDB(session.getEmpInfoId());


        Map<String, String> filter = new HashMap<>();
        filter.put("EmpId", empid);
        filter.put("frmDate", UtilityHelper._GetCurrentDate());
        filter.put("toDate", UtilityHelper._GetCurrentDate());
        filter.put("ProductId", "");
        callApi(filter);
        binding.swip.setOnRefreshListener(() -> {
            Map<String, String> filters = new HashMap<>();
            filters.put("EmpId", empid);
            filters.put("frmDate", UtilityHelper._GetCurrentDate());
            filters.put("toDate", UtilityHelper._GetCurrentDate());
            filters.put("ProductId", "");
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
                Call<List<ModelRxSummary>> call = service.GetRxSummary(filter);
                call.enqueue(new Callback<List<ModelRxSummary>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelRxSummary>> call, @NonNull Response<List<ModelRxSummary>> response) {
                        Log.d("TAG", "onResponse: " + response.body());
                        System.out.println("resp: " + response.body());
                        if (response.body() != null && response.body().size() > 0) {
                            if (pd != null || pd.isShowing()) {
                                pd.dismiss();
                            }
                            List<ModelRxSummary> approval = response.body();
                            System.out.println("Response :" + approval);
                            onSuccessReport(approval);

                        } else {
                            if (pd != null || pd.isShowing()) {
                                pd.dismiss();
                            }
                            onSuccessReport(null);
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelRxSummary>> call, @NonNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            if (pd != null || pd.isShowing()) {
                                pd.dismiss();
                            }
                            onError("Slow Connection Detected. Please try again", 1);
                        }
                    }
                });

            } catch (Exception ex) {
                if (pd != null || pd.isShowing()) {
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
        ftm.filterMaster.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
      /*  BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) ftm.getRoot().getParent());
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        ftm.filterMaster.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);*/
       // mBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels/2);
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
        ftm.filterProductLayout.setVisibility(VISIBLE);

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
        ftm.searchProducttxt.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            // ca = new CampaignGetReq();
            ModelProduct selected = (ModelProduct) arg0.getAdapter().getItem(arg2);
            pid = selected.getProductId();
        });

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
                filters.put("ProductId", String.valueOf(pid));

                Gson gson = new Gson();
                String data = gson.toJson(filters);
                System.out.println("rxdetails : " + data);
                callApi(filters);

            } catch (Exception exception) {
                exception.printStackTrace();
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

    private void onSuccessReport(List<ModelRxSummary> rlist) {
        adapter = new AdapterReportRxSummay(rlist, requireActivity());
        binding.rvRxsummary.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rvRxsummary.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnError(String message) {

    }

    @Override
    public void onProductsGet(List<ModelProduct> aList) {
        if (aList != null) {
            productArrayList = (ArrayList<ModelProduct>) aList;
            ArrayAdapter<ModelProduct> adapter = new ArrayAdapter<>(
                    requireActivity(), android.R.layout.simple_spinner_dropdown_item, productArrayList);

            ftm.searchProducttxt.setAdapter(adapter);
            ftm.searchProducttxt.setThreshold(1);//will start working from first character
        }
    }
}