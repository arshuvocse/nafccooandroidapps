package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Product.Adapter.AdapterProducts;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityReportProductsBinding;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportProducts extends AppCompatActivity {
    ActivityReportProductsBinding binding;
    private AdapterProducts mAdapter;
    ProgressDialog progressDoalog;
    List<ModelProduct> aProList = new ArrayList<>();
    EditText searchEd;
    DBCrudHelper dbCrudHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReportProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(v ->finish());
        dbCrudHelper=new DBCrudHelper(ReportProducts.this);
        ModelSession user = dbCrudHelper.getSession();
        int empId = user.getEmpInfoId();
        getProductsFromServer(empId);


        searchEd = findViewById(R.id.searchEd);
        searchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
    }
    public void LoadinView(List<ModelProduct> aList){
        aProList.clear();
        aProList = aList;
        mAdapter = new AdapterProducts(aProList);
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

    public  void ErrorView(String msg){
        Toast.makeText(ReportProducts.this,msg,Toast.LENGTH_LONG).show();
    }

    private void filter(String productName){
        List<ModelProduct> arrayList = new ArrayList<>();
        for (ModelProduct item : aProList){
            if((item.getProductName().toLowerCase().contains(productName.toLowerCase()) || (item.getProductCode().contains(productName)))){
                arrayList.add(item);
            }
        }

        mAdapter.filterList(arrayList);

    }

    public void getProductsFromServer(int empId) {
        progressDoalog = new ProgressDialog(ReportProducts.this);
        progressDoalog.setMessage("Product is loading....");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try{
            ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
            Call<List<ModelProduct>> call = service.GetProductList(empId);
            call.enqueue(new Callback<List<ModelProduct>>() {
                @Override
                public void onResponse(@NonNull Call<List<ModelProduct>> call, @NonNull Response<List<ModelProduct>> response) {
                    progressDoalog.dismiss();
                    LoadinView(response.body());

                }
                @Override
                public void onFailure(@NonNull Call<List<ModelProduct>> call, @NonNull Throwable t) {
                    progressDoalog.dismiss();
                    if(t instanceof SocketTimeoutException){
                        ErrorView("Slow Network detected");
                    }else{
                        ErrorView("Some Error occured");

                    }


                }
            });

        }catch (Exception ex){
            ErrorView("Some Error occured");
            progressDoalog.dismiss();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}