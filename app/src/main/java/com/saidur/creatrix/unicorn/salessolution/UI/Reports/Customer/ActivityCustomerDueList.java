package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Customer;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityCustomerDueListBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCustomerDueList extends AppCompatActivity {
    private final List<CustomerDueItem> allDueCustomers = new ArrayList<>();
    private final List<CustomerDueItem> visibleCustomers = new ArrayList<>();
    private final DecimalFormat amountFormat =
            new DecimalFormat("#,##0.##", DecimalFormatSymbols.getInstance(Locale.US));

    private ActivityCustomerDueListBinding binding;
    private FilterMasterBinding ftm;
    private CustomerDueListAdapter adapter;
    private DBCrudHelper dbCrudHelper;
    private ComponentLoadingbarBinding lbinding;
    private Dialog pd_process;
    private BottomSheetDialog bsheetdlg;
    private ModelSession session;
    private int empId;
    private int appliedTargetEmpId;
    private String role;
    private String appliedEmployeeType = "Select";
    private String selectedTyp = "";
    private String emp_id = "";
    private int selectedMonth = 0;
    private int selectedyear = 0;
    private List<ModelMBE> mbeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDueListBinding.inflate(getLayoutInflater());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbCrudHelper = new DBCrudHelper(this);
        session = dbCrudHelper.getSession();
        empId = session.getEmpInfoId();
        appliedTargetEmpId = empId;
        role = session.getEmpRole();

        adapter = new CustomerDueListAdapter(this);
        binding.rvDueList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDueList.setAdapter(adapter);

        prepareLoadingBar();
        openFilter();

        binding.ivBack.setOnClickListener(view -> finish());
        binding.ivFilter.setOnClickListener(view -> bsheetdlg.show());

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilter(s == null ? "" : s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loadCustomerDueData(buildCustomerDueFilter(appliedTargetEmpId));
    }

    private void prepareLoadingBar() {
        pd_process = new Dialog(this);
        pd_process.requestWindowFeature(Window.FEATURE_NO_TITLE);
        lbinding = ComponentLoadingbarBinding.inflate(getLayoutInflater());
        pd_process.setContentView(lbinding.getRoot());
        if (pd_process.getWindow() != null) {
            pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        pd_process.setCancelable(false);
    }

    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(this);
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
            default:
                ftm.linearLayoutemp.setVisibility(GONE);
                break;
        }

        ftm.filterStatusLayout.getRoot().setVisibility(GONE);
        ftm.filterTypeLayout.tilDoctype.setVisibility(GONE);
        ftm.filterTypeLayout.tilProgtypetype.setVisibility(GONE);
        ftm.filterMonthYearLayout.getRoot().setVisibility(GONE);
        ftm.filterMarketLayout.getRoot().setVisibility(GONE);
        ftm.llChooser.setVisibility(GONE);

        List<String> emptype = new ArrayList<>();
        switch (role) {
            case "MIO":
                emptype.add("Select");
                emptype.add("MBE");
                break;
            case "AM":
                emptype.add("Select");
                emptype.add("MBE");
                emptype.add("ABM");
                break;
        }

        if (!emptype.isEmpty()) {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    emptype
            );
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ftm.spinnerEmployeeType.setAdapter(dataAdapter);
        }

        ftm.spinnerEmployeeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTyp = String.valueOf(ftm.spinnerEmployeeType.getSelectedItem());
                emp_id = "";
                resetEmployeeSelectionViews();

                switch (selectedTyp) {
                    case "MBE":
                        ftm.mbelay.setVisibility(VISIBLE);
                        try {
                            mbeList = dbCrudHelper.getMBEList_SQLite();
                        } catch (Exception exception) {
                            mbeList = null;
                        }
                        if (mbeList != null && !mbeList.isEmpty()) {
                            ArrayAdapter<ModelMBE> dataAdapter = new ArrayAdapter<>(
                                    ActivityCustomerDueList.this,
                                    android.R.layout.simple_spinner_item,
                                    mbeList
                            );
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            ftm.spinnerApprovalMbe.setAdapter(dataAdapter);
                            selectMbeSpinnerValue(ftm.spinnerApprovalMbe, mbeList, appliedTargetEmpId);
                        } else {
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "MBE Not Found!!");
                        }
                        break;

                    case "ABM":
                        ftm.miolay.setVisibility(VISIBLE);
                        List<ModelMIO> mioList;
                        try {
                            mioList = dbCrudHelper.getMIOList_SQLite();
                        } catch (Exception exception) {
                            mioList = null;
                        }
                        if (mioList != null && !mioList.isEmpty()) {
                            ArrayAdapter<ModelMIO> dataAdapter = new ArrayAdapter<>(
                                    ActivityCustomerDueList.this,
                                    android.R.layout.simple_spinner_item,
                                    mioList
                            );
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            ftm.spinnerApprovalMio.setAdapter(dataAdapter);
                            selectMioSpinnerValue(ftm.spinnerApprovalMio, mioList, appliedTargetEmpId);
                        } else {
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "ABM Not Found!!");
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Calendar c = Calendar.getInstance();
        selectedyear = c.get(Calendar.YEAR);
        selectedMonth = c.get(Calendar.MONTH);
        ftm.llmonthyear.setVisibility(VISIBLE);
        ftm.ivmypicker.setOnClickListener(v -> MonthPicker());
        ftm.montyer.setText(UtilityHelper.monthNameArrayFull[selectedMonth] + ',' + selectedyear);

        int selectedTypeIndex = emptype.indexOf(appliedEmployeeType);
        if (selectedTypeIndex >= 0) {
            ftm.spinnerEmployeeType.setSelection(selectedTypeIndex);
        }

        ftm.filterTopLayout.btnDonefilter.setOnClickListener(v -> {
            try {
                switch (selectedTyp) {
                    case "MBE":
                        ModelMBE mbeempid = (ModelMBE) ftm.spinnerApprovalMbe.getSelectedItem();
                        if (mbeempid != null) {
                            emp_id = String.valueOf(mbeempid.getMBEEmpId());
                        }
                        break;
                    case "ABM":
                        ModelMIO mioempid = (ModelMIO) ftm.spinnerApprovalMio.getSelectedItem();
                        if (mioempid != null) {
                            emp_id = String.valueOf(mioempid.getMIOEmpId());
                        }
                        break;
                    default:
                        emp_id = "";
                        break;
                }

                appliedEmployeeType = TextUtils.isEmpty(selectedTyp) ? "Select" : selectedTyp;
                appliedTargetEmpId = TextUtils.isEmpty(emp_id) ? empId : Integer.parseInt(emp_id);
                loadCustomerDueData(buildCustomerDueFilter(appliedTargetEmpId));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            bsheetdlg.cancel();
        });

        ftm.filterTopLayout.ivCancel.setOnClickListener(v -> bsheetdlg.cancel());
    }

    private void resetEmployeeSelectionViews() {
        ftm.mbelay.setVisibility(GONE);
        ftm.miolay.setVisibility(GONE);
        ftm.asmlay.setVisibility(GONE);
        ftm.rsmlay.setVisibility(GONE);
    }

    private HashMap<String, String> buildCustomerDueFilter(int targetEmpId) {
        return buildCustomerDueFilter(targetEmpId, selectedMonth + 1, selectedyear);
    }

    private HashMap<String, String> buildCustomerDueFilter(int targetEmpId, int month, int year) {
        HashMap<String, String> filter = new HashMap<>();
        filter.put("empId", String.valueOf(targetEmpId));
        filter.put("Month", String.valueOf(month));
        filter.put("Year", String.valueOf(year));
        return filter;
    }

    private void loadCustomerDueData(HashMap<String, String> filter) {
        if (NetworkInformation.isConnected(this)) {
            pd_process.show();
            lbinding.tvText.setText("Loading customer due list...");
            callApiGetCustomerDue(filter);
        } else {
            SnackBarManagement.NoInternetSnackbar(binding.getRoot());
        }
    }

    private void callApiGetCustomerDue(HashMap<String, String> filter) {
        ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
        Call<List<ModelCustomer>> call = service.GetCustomerDueList(filter);
        call.enqueue(new Callback<List<ModelCustomer>>() {
            @Override
            public void onResponse(Call<List<ModelCustomer>> call, Response<List<ModelCustomer>> response) {
                pd_process.dismiss();
                allDueCustomers.clear();

                if (response.isSuccessful() && response.body() != null) {
                    List<ModelCustomer> customers = response.body();
                    for (ModelCustomer customer : customers) {
                        double dueAmount = parseAmount(customer.getDueAmount());
                        if (dueAmount == 0d && !TextUtils.isEmpty(customer.getBalance())) {
                            dueAmount = parseAmount(customer.getBalance());
                        }

                        allDueCustomers.add(new CustomerDueItem(
                                safeText(customer.getCustomerName()),
                                safeText(customer.getCustomerCode()),
                                safeText(customer.getCellNo()),
                                safeText(customer.getMarketName()),
                                dueAmount
                        ));
                    }
                } else {
                    SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.customer_due_load_error));
                }

                Collections.sort(allDueCustomers,
                        (first, second) -> Double.compare(second.getDueAmount(), first.getDueAmount()));

                applyFilter(binding.etSearch.getText() == null ? "" : binding.etSearch.getText().toString());
            }

            @Override
            public void onFailure(Call<List<ModelCustomer>> call, Throwable t) {
                pd_process.dismiss();
                SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.api_error));
            }
        });
    }

    private void applyFilter(String query) {
        visibleCustomers.clear();

        for (CustomerDueItem item : allDueCustomers) {
            if (matchesQuery(item, query)) {
                visibleCustomers.add(item);
            }
        }

        adapter.submitList(visibleCustomers);
        updateSummary();
        updateEmptyState(query);
    }

    private boolean matchesQuery(CustomerDueItem item, String query) {
        if (TextUtils.isEmpty(query)) {
            return true;
        }

        String normalizedQuery = query.trim().toLowerCase(Locale.US);
        return item.getCustomerName().toLowerCase(Locale.US).contains(normalizedQuery)
                || item.getCustomerCode().toLowerCase(Locale.US).contains(normalizedQuery)
                || item.getCellNo().toLowerCase(Locale.US).contains(normalizedQuery)
                || item.getMarketName().toLowerCase(Locale.US).contains(normalizedQuery);
    }

    private void updateSummary() {
        double totalDue = 0d;
        for (CustomerDueItem item : visibleCustomers) {
            totalDue += item.getDueAmount();
        }

        binding.tvTotalCustomersValue.setText(String.valueOf(visibleCustomers.size()));
        binding.tvTotalDueValue.setText(getString(
                R.string.customer_due_money_format,
                amountFormat.format(totalDue)
        ));
    }

    private void updateEmptyState(String query) {
        boolean isEmpty = visibleCustomers.isEmpty();
        binding.emptyState.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        binding.rvDueList.setVisibility(isEmpty ? View.GONE : View.VISIBLE);

        if (TextUtils.isEmpty(query)) {
            binding.tvEmptyMessage.setText(appliedTargetEmpId != empId
                    ? R.string.customer_due_no_filter_match
                    : R.string.customer_due_empty);
        } else {
            binding.tvEmptyMessage.setText(R.string.customer_due_no_match);
        }
    }

    private void MonthPicker() {
        Calendar calendar = Calendar.getInstance();
        int yearSelected = calendar.get(Calendar.YEAR);
        int monthSelected = calendar.get(Calendar.MONTH);
        MonthYearPickerDialogFragment dialogFragment =
                MonthYearPickerDialogFragment.getInstance(monthSelected, yearSelected);
        dialogFragment.show(getSupportFragmentManager(), null);

        dialogFragment.setOnDateSetListener((year, monthOfYear) -> {
            ftm.montyer.setText(UtilityHelper.monthNameArrayFull[monthOfYear] + ',' + year);
            selectedMonth = monthOfYear;
            selectedyear = year;
        });
    }

    private void selectMbeSpinnerValue(Spinner spinner, List<ModelMBE> employees, int selectedEmpId) {
        if (!"MBE".equals(appliedEmployeeType)) {
            return;
        }

        for (int index = 0; index < employees.size(); index++) {
            if (employees.get(index).getMBEEmpId() == selectedEmpId) {
                spinner.setSelection(index);
                return;
            }
        }
    }

    private void selectMioSpinnerValue(Spinner spinner, List<ModelMIO> employees, int selectedEmpId) {
        if (!"ABM".equals(appliedEmployeeType)) {
            return;
        }

        for (int index = 0; index < employees.size(); index++) {
            if (employees.get(index).getMIOEmpId() == selectedEmpId) {
                spinner.setSelection(index);
                return;
            }
        }
    }

    private String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private double parseAmount(String rawValue) {
        if (TextUtils.isEmpty(rawValue)) {
            return 0d;
        }

        String normalizedValue = rawValue
                .replace(",", "")
                .replaceAll("[^0-9.-]", "");

        if (TextUtils.isEmpty(normalizedValue)
                || ".".equals(normalizedValue)
                || "-".equals(normalizedValue)
                || "-.".equals(normalizedValue)) {
            return 0d;
        }

        try {
            return Double.parseDouble(normalizedValue);
        } catch (NumberFormatException exception) {
            return 0d;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
