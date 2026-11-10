package com.saidur.creatrix.unicorn.salessolution.UI.CategoryRequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCategoryRequest;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityCategoryApprovalBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.RvCategoryApprovalItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Screen 3: approver's Pending / Approved / Rejected tabs. */
public class ActivityCategoryApproval extends AppCompatActivity {
    private static final String[] STATUS = {"Pending", "Approved", "Rejected"};

    private ActivityCategoryApprovalBinding binding;
    private ApiCategoryRequest api;
    private int empId;
    private String status = STATUS[0];
    private final List<CatReqModels.Approval> items = new ArrayList<>();
    private final Adapter adapter = new Adapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryApprovalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        empId = new DBCrudHelper(this).getSession().getEmpInfoId();
        api = RetrofitClientInstance.getRetrofitInstance().create(ApiCategoryRequest.class);

        binding.header.tvTitle.setText("Category Approval");
        binding.header.ivBack.setOnClickListener(v -> finish());
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.setAdapter(adapter);
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                status = STATUS[tab.getPosition()];
                load();
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) { load(); }
        });
        load();
    }

    private void load() {
        if (!NetworkInformation.isConnected(this)) {
            SnackBarManagement.NoInternetSnackbar(binding.getRoot());
            return;
        }
        final String forStatus = status;
        ProgressDialog pd = ProgressDialog.show(this, null, "Loading...", true, false);
        api.getApprovalList(empId, forStatus).enqueue(new Callback<List<CatReqModels.Approval>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatReqModels.Approval>> call, @NonNull Response<List<CatReqModels.Approval>> response) {
                pd.dismiss();
                if (!forStatus.equals(status)) return; // user switched tab meanwhile
                items.clear();
                if (response.body() != null) items.addAll(response.body());
                else SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.api_error));
                show();
            }

            @Override
            public void onFailure(@NonNull Call<List<CatReqModels.Approval>> call, @NonNull Throwable t) {
                pd.dismiss();
                SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.api_error));
            }
        });
    }

    private void show() {
        binding.header.tvCount.setText("Count : " + items.size());
        binding.tvEmpty.setText("No " + status.toLowerCase(Locale.US) + " request.");
        binding.tvEmpty.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
        adapter.notifyDataSetChanged();
    }

    private void confirm(CatReqModels.Approval a, String action) {
        EditText et = new EditText(this);
        et.setHint(action.equals("Rejected") ? "Reason (required)" : "Comment (optional)");
        AlertDialog dlg = new AlertDialog.Builder(this)
                .setTitle((action.equals("Rejected") ? "Reject " : "Approve ") + a.CustomerName + "?")
                .setMessage(a.FromType + " → " + a.ToType)
                .setView(et)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(action.equals("Rejected") ? "Reject" : "Approve", null)
                .create();
        dlg.setOnShowListener(d -> dlg.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String comment = et.getText().toString().trim();
            if (action.equals("Rejected") && comment.isEmpty()) {
                et.setError("Reason is required");
                return;
            }
            dlg.dismiss();
            save(a, action, comment);
        }));
        dlg.show();
    }

    private void save(CatReqModels.Approval a, String action, String comment) {
        if (!NetworkInformation.isConnected(this)) {
            SnackBarManagement.NoInternetSnackbar(binding.getRoot());
            return;
        }
        CatReqModels.ApproveBody body = new CatReqModels.ApproveBody();
        body.RequestId = a.RequestId;
        body.EmpId = empId;
        body.Status = action;
        body.Comments = comment;
        ProgressDialog pd = ProgressDialog.show(this, null, "Saving...", true, false);
        api.approve(body).enqueue(new Callback<List<CatReqModels.ApproveResult>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatReqModels.ApproveResult>> call, @NonNull Response<List<CatReqModels.ApproveResult>> response) {
                pd.dismiss();
                List<CatReqModels.ApproveResult> r = response.body();
                if (r == null || r.isEmpty()) {
                    SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.api_error));
                } else if (r.get(0).Success == 1) {
                    SnackBarManagement._success_CustomMessage(binding.getRoot(), r.get(0).Msg);
                    load();
                } else {
                    SnackBarManagement._warning_CustomMessage(binding.getRoot(), r.get(0).Msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CatReqModels.ApproveResult>> call, @NonNull Throwable t) {
                pd.dismiss();
                SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.api_error));
            }
        });
    }

    private void bindDoc(ImageView iv, String url) {
        Glide.with(this).load(url).placeholder(R.drawable.ic_camera).error(R.drawable.ic_camera).into(iv);
        iv.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(url)) startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.VH> {
        class VH extends RecyclerView.ViewHolder {
            final RvCategoryApprovalItemBinding b;
            VH(RvCategoryApprovalItemBinding b) { super(b.getRoot()); this.b = b; }
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(RvCategoryApprovalItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            CatReqModels.Approval a = items.get(position);
            h.b.tvName.setText(a.CustomerName);
            h.b.tvDetails.setText(HtmlCompat.fromHtml(
                    "<b>Code :</b> " + ActivityCategoryRequest.nz(a.CustomerCode)
                            + "<br><b>Market :</b> " + ActivityCategoryRequest.nz(a.Market)
                            + "<br><b>Mobile :</b> " + ActivityCategoryRequest.nz(a.CellNo)
                            + "<br><b>Category :</b> " + ActivityCategoryRequest.nz(a.FromType)
                            + " → <b><font color='#005A59'>" + ActivityCategoryRequest.nz(a.ToType) + "</font></b>"
                            + "<br><b>Requested By :</b> " + ActivityCategoryRequest.nz(a.RequestBy)
                            + " (" + ActivityCategoryRequest.nz(a.RequestDate) + ")"
                            + "<br><b>Status :</b> " + ActivityCategoryRequest.nz(a.Status),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
            bindDoc(h.b.ivShop, a.ShopPhotoUrl);
            bindDoc(h.b.ivNid, a.NIDUrl);
            bindDoc(h.b.ivTrade, a.TradeLicenseUrl);
            bindDoc(h.b.ivDrug, a.DrugLicenseUrl);
            h.b.llActions.setVisibility(STATUS[0].equals(status) ? View.VISIBLE : View.GONE);
            h.b.btnApprove.setOnClickListener(v -> confirm(a, "Approved"));
            h.b.btnReject.setOnClickListener(v -> confirm(a, "Rejected"));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
