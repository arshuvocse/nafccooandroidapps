package com.saidur.creatrix.unicorn.salessolution.UI.Order.Campaign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Rp_CampainViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Campaign.Adapter.AdapterCampaignData;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityCampaignBinding;

import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignActivity extends AppCompatActivity {
    ActivityCampaignBinding binding;
    ProgressDialog pd;
    AdapterCampaignData mAdapter;
    DBCrudHelper dbCrudHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCampaignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper=new DBCrudHelper(CampaignActivity.this);
        binding.toolbarCustom.setNavigationOnClickListener(view -> finish());
        ModelSession session = dbCrudHelper.getSession();
        GetCampaignData(session.getEmpInfoId());
        binding.swipCamp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipCamp.setRefreshing(false);
                GetCampaignData(session.getEmpInfoId());
            }
        });
        binding.attRefresh.setOnClickListener(view -> {
            GetCampaignData(session.getEmpInfoId());
        });
    }

    public void GetCampaignData(int empId) {
        pd = new ProgressDialog(CampaignActivity.this);
        pd.setIcon(R.drawable.ic_campaign);
        pd.setMessage("Campaign is Loading....");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        try {
            ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
            Call<List<Rp_CampainViewModel>> call = service.GetCampaingData(empId);
            call.enqueue(new Callback<List<Rp_CampainViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<Rp_CampainViewModel>> call, @NonNull Response<List<Rp_CampainViewModel>> response) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    LoadinView(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<List<Rp_CampainViewModel>> call, @NonNull Throwable t) {
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

    public void LoadinView(List<Rp_CampainViewModel> aList) {
        if (aList != null) {
            mAdapter = new AdapterCampaignData(aList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(mAdapter);
            binding.recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            binding.recyclerView.setItemAnimator(null);
            binding.recyclerView.scrollToPosition(0);
            mAdapter.notifyDataSetChanged();
        }else {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(),"Campaign Not Found!!");
        }
    }

    public void ErrorView(String msg) {
        SnackBarManagement._error_CustomMessage(binding.getRoot(),msg);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}