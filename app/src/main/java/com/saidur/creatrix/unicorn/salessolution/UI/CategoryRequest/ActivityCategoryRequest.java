package com.saidur.creatrix.unicorn.salessolution.UI.CategoryRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCategoryRequest;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityCategoryRequestBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.RvCategoryRequestItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Screen 1: MBE's customer list, category limits and "Apply For" dropdown. */
public class ActivityCategoryRequest extends AppCompatActivity {
    private ActivityCategoryRequestBinding binding;
    private ApiCategoryRequest api;
    private int empId;
    private final List<CatReqModels.Summary> categories = new ArrayList<>();
    private final List<CatReqModels.Customer> allCustomers = new ArrayList<>();
    private final List<CatReqModels.Customer> visible = new ArrayList<>();
    private final Adapter adapter = new Adapter();
    private ProgressDialog pd;

    private final ActivityResultLauncher<Intent> uploadLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    SnackBarManagement._success_CustomMessage(binding.getRoot(), "Request submitted.");
                    load();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        empId = new DBCrudHelper(this).getSession().getEmpInfoId();
        api = RetrofitClientInstance.getRetrofitInstance().create(ApiCategoryRequest.class);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);

        binding.header.tvTitle.setText("Category Request");
        binding.header.ivBack.setOnClickListener(v -> finish());
        binding.rvCustomers.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCustomers.setAdapter(adapter);
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) { applyFilter(); }
            @Override public void afterTextChanged(Editable s) {}
        });
        load();
    }

    private void load() {
        if (!NetworkInformation.isConnected(this)) {
            SnackBarManagement.NoInternetSnackbar(binding.getRoot());
            return;
        }
        pd.show();
        api.getSummary(empId).enqueue(new Callback<List<CatReqModels.Summary>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatReqModels.Summary>> call, @NonNull Response<List<CatReqModels.Summary>> response) {
                categories.clear();
                if (response.body() != null) categories.addAll(response.body());
                showSummary();
                loadCustomers();
            }

            @Override
            public void onFailure(@NonNull Call<List<CatReqModels.Summary>> call, @NonNull Throwable t) {
                pd.dismiss();
                SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.api_error));
            }
        });
    }

    private void loadCustomers() {
        api.getCustomers(empId).enqueue(new Callback<List<CatReqModels.Customer>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatReqModels.Customer>> call, @NonNull Response<List<CatReqModels.Customer>> response) {
                pd.dismiss();
                allCustomers.clear();
                if (response.body() != null) allCustomers.addAll(response.body());
                applyFilter();
            }

            @Override
            public void onFailure(@NonNull Call<List<CatReqModels.Customer>> call, @NonNull Throwable t) {
                pd.dismiss();
                SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.api_error));
            }
        });
    }

    private void showSummary() {
        binding.llSummary.removeAllViews();
        for (CatReqModels.Summary s : categories) {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            lp.setMargins(4, 0, 4, 0);
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(8, 14, 8, 14);
            tv.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            String limit = s.RequestLimit == null || s.RequestLimit == 0 ? "No limit" : String.valueOf(s.RequestLimit);
            tv.setText(HtmlCompat.fromHtml("<b><font color='#005A59'>" + s.CustomerType + "</font></b><br>"
                    + s.UsedCount + " / " + limit, HtmlCompat.FROM_HTML_MODE_LEGACY));
            binding.llSummary.addView(tv);
        }
    }

    private void applyFilter() {
        String q = binding.etSearch.getText().toString().trim().toLowerCase(Locale.US);
        visible.clear();
        for (CatReqModels.Customer c : allCustomers) {
            if (q.isEmpty() || (c.CustomerName + " " + c.CustomerCode + " " + c.CellNo + " " + c.Market)
                    .toLowerCase(Locale.US).contains(q)) {
                visible.add(c);
            }
        }
        binding.header.tvCount.setText("Count : " + visible.size());
        binding.tvEmpty.setVisibility(visible.isEmpty() ? View.VISIBLE : View.GONE);
        adapter.notifyDataSetChanged();
    }

    private void onRequest(CatReqModels.Customer c, CatReqModels.Summary s) {
        if (c.CustomerTypeId != null && c.CustomerTypeId == s.CustomerTypeId) {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Customer is already " + s.CustomerType + ".");
            return;
        }
        if (s.isFull()) {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), s.CustomerType + " limit is full.");
            return;
        }
        Intent i = new Intent(this, ActivityCategoryDocUpload.class);
        i.putExtra("customer", c);
        i.putExtra("typeId", s.CustomerTypeId);
        i.putExtra("typeName", s.CustomerType);
        uploadLauncher.launch(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    static String details(CatReqModels.Customer c) {
        return "<b>Code :</b> " + nz(c.CustomerCode) + "<br><b>Address :</b> " + nz(c.Address)
                + "<br><b>Market :</b> " + nz(c.Market) + "<br><b>Customer Type :</b> " + nz(c.CustomerType)
                + "<br><b>Mobile :</b> " + nz(c.CellNo);
    }

    static String nz(String s) {
        return TextUtils.isEmpty(s) ? "" : TextUtils.htmlEncode(s);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.VH> {
        class VH extends RecyclerView.ViewHolder {
            final RvCategoryRequestItemBinding b;
            VH(RvCategoryRequestItemBinding b) { super(b.getRoot()); this.b = b; }
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(RvCategoryRequestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            CatReqModels.Customer c = visible.get(position);
            h.b.tvName.setText(c.CustomerName);
            h.b.tvDetails.setText(HtmlCompat.fromHtml(details(c), HtmlCompat.FROM_HTML_MODE_LEGACY));

            boolean pending = !TextUtils.isEmpty(c.PendingType);
            h.b.tvPending.setVisibility(pending ? View.VISIBLE : View.GONE);
            h.b.llApply.setVisibility(pending ? View.GONE : View.VISIBLE);
            if (pending) {
                h.b.tvPending.setText("Pending approval : " + c.PendingType);
                return;
            }

            List<Object> items = new ArrayList<>();
            items.add("Select");
            items.addAll(categories);
            h.b.spCategory.setAdapter(new ArrayAdapter<>(h.itemView.getContext(), android.R.layout.simple_spinner_dropdown_item, items));
            h.b.btnRequest.setEnabled(false);
            h.b.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    h.b.btnRequest.setEnabled(pos > 0);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
            h.b.btnRequest.setOnClickListener(v -> {
                Object sel = h.b.spCategory.getSelectedItem();
                if (sel instanceof CatReqModels.Summary) onRequest(c, (CatReqModels.Summary) sel);
            });
        }

        @Override
        public int getItemCount() {
            return visible.size();
        }
    }
}
