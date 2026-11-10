package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Stock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Rp_StockViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Stock.Adapter.AdapterStock;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityReportStockBinding;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportStock extends AppCompatActivity {
    ActivityReportStockBinding binding;
    private AdapterStock mAdapter;
    ProgressDialog pd;
    DBCrudHelper dbCrudHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(view -> finish());
        dbCrudHelper = new DBCrudHelper(ReportStock.this);
        ModelSession session=dbCrudHelper.getSession();
        int empId = session.getEmpInfoId();
        String who=getIntent().getStringExtra("menu");
        if(who.equals("ProductStock"))
        {
            binding.toolbarTitle.setText("Saleable Stock Information");
            GetStockData(empId,"Main");
        }
        if(who.equals("PromoStock"))
        {
            binding.toolbarTitle.setText("Promo Stock Information");
            GetStockData(empId,"Sample");
        }
    }

    //ShuvoStock
    public void GetStockData(int empId, String main) {
        pd = new ProgressDialog(ReportStock.this);
        pd.setIcon(R.drawable.ic_board);
        pd.setMessage("Stock is Loading....");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        try {
            ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
            Call<List<Rp_StockViewModel>> call = service.GetStockData(empId, main);
            call.enqueue(new Callback<List<Rp_StockViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<Rp_StockViewModel>> call, @NonNull Response<List<Rp_StockViewModel>> response) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    LoadinView(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<List<Rp_StockViewModel>> call, @NonNull Throwable t) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                        ErrorView("Slow Connection Detected");
                    } else {
                        ErrorView("Some Error Occurred");
                    }
                }
            });

        } catch (Exception ex) {
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
            }
            ErrorView("Some Error Occurred");
        }

    }
    public void LoadinView(List<Rp_StockViewModel> aList) {
        if (aList != null) {
            mAdapter = new AdapterStock(aList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(mAdapter);
            binding.recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            binding.recyclerView.setItemAnimator(null);
            binding.recyclerView.scrollToPosition(0);
            mAdapter.notifyDataSetChanged();
        }

    }
    public void ErrorView(String msg) {
        SnackBarManagement._error_CustomMessage(binding.getRoot(), msg);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}