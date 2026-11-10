package com.saidur.creatrix.unicorn.salessolution.UI.Dar_N_coaching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterDar;
import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterDsr;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketStracture;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDAR;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDSR;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelRemarks;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterMarketStructure;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.AddCustomerActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Customer.ActivityCustCall;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDarBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDar extends AppCompatActivity implements IMarketStracture.View,AdapterDsr.remarksList{
    ActivityDarBinding binding;
    ProgressDialog pd;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    IMarketStracture.Presenter mkpresenter;
    String role;
    String teri;
    String seletedSTeritoryId,seletedTeritoryId;
    List<ModelRemarks> rList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pd=new ProgressDialog(ActivityDar.this);
        dbCrudHelper = new DBCrudHelper(ActivityDar.this);
        session = dbCrudHelper.getSession();
        role = session.getEmpRole();
        mkpresenter = new PresenterMarketStructure(this, ActivityDar.this);
        binding.toolbarCustom.setNavigationOnClickListener(view -> {
            finish();
        });
        mkpresenter.GetGroupLocal();
        mkpresenter.GetRegionLocal(0);
        mkpresenter.GetAreaLocal(0);
        mkpresenter.GetTeritoryLocal(0);
        mkpresenter.GetSTeritoryLocal(0);
        try {
            switch (session.getRoleType()) {
                case "MIO":
                    mkpresenter.GetSTeritoryLocal(0);
                    binding.spinnerTeri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ModelSubTeritorry steri = (ModelSubTeritorry) parent.getSelectedItem();
                            teri = String.valueOf(steri.getSubTerritoryId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case "AM":
                case "DZSM":
                case "NSM":
                    mkpresenter.GetTeritoryLocal(0);
                    binding.spinnerTeri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ModelTeritorry teris = (ModelTeritorry) parent.getSelectedItem();
                            teri = String.valueOf(teris.getTerritoryId());
                            //mkpresenter.GetSTeritoryLocal(seletedTeritoryId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
            }


        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        binding.ivCal.setOnClickListener(v -> UtilityHelper._datePickerDialogeForDates(binding.tvDate, ActivityDar.this));


        binding.ivBtnSearch.setOnClickListener(v -> {
            if(TextUtils.isEmpty(binding.tvDate.getText().toString()))
            {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(),"Select Date!!");
            }else {
                HashMap<String, String> map = new HashMap<>();
                map.put("empid", String.valueOf(session.getEmpInfoId()));
                map.put("teri_id", teri);
                map.put("date", binding.tvDate.getText().toString());
                callDarApi(map);
                callDSRApi(map);
            }

        });
        binding.btnSubmit.setOnClickListener(v -> {
            saveDsrApi(rList);
        });

    }

    private void callDarApi(HashMap<String, String> map) {
        if (NetworkInformation.isConnected(ActivityDar.this)) {
            pd.setTitle("Loading...");
            pd.show();
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(true);

            try {
                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<ModelDAR>> call = service.GetDAR(map);
                call.enqueue(new Callback<List<ModelDAR>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelDAR>> call, @NonNull Response<List<ModelDAR>> response) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.body() != null && response.body().size() > 0) {
                            LoadDar(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelDAR>> call, @NonNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                        }
                    }
                });
            } catch (Exception ex) {
            }
        } else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet");
        }

    }
    public void LoadDar(List<ModelDAR> data) {
        AdapterDar adapterDar=new AdapterDar(ActivityDar.this,data);
        binding.rvDar.setLayoutManager(new LinearLayoutManager(ActivityDar.this, RecyclerView.VERTICAL, false));
        binding.rvDar.setAdapter(adapterDar);
        adapterDar.notifyDataSetChanged();

    }

    private void callDSRApi(HashMap<String, String> map) {
        if (NetworkInformation.isConnected(ActivityDar.this)) {
            pd.setTitle("Loading...");
            pd.show();
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(true);

            try {
                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<ModelDSR>> call = service.GetDSR(map);
                call.enqueue(new Callback<List<ModelDSR>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ModelDSR>> call, @NonNull Response<List<ModelDSR>> response) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.body() != null && response.body().size() > 0) {
                            LoadDSR(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ModelDSR>> call, @NonNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            //viewBindings.docTxt.setText("Slow Network detected");
                        } else {
                            // viewBindings.docTxt.setText("Some Error occured");

                        }


                    }
                });
            } catch (Exception ex) {
            }
        } else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet");
        }

    }
    public void LoadDSR(List<ModelDSR> data) {
        AdapterDsr adapterDsr=new AdapterDsr(ActivityDar.this,data,this,binding.tvDate.getText().toString());
        binding.rvDsr.setLayoutManager(new LinearLayoutManager(ActivityDar.this, RecyclerView.VERTICAL, false));
        binding.rvDsr.setAdapter(adapterDsr);
        adapterDsr.notifyDataSetChanged();

    }

    @Override
    public void vGroup(List<ModelGroup> groupList) {
        try {
            if (groupList != null) {
                ArrayAdapter<ModelGroup> dataAdapter = new ArrayAdapter<>(ActivityDar.this, android.R.layout.simple_spinner_item, groupList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //binding.groupSpinner.setAdapter(dataAdapter);

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vRegion(List<ModelRegion> regionList) {
        try {
            if (regionList != null) {
                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(ActivityDar.this, android.R.layout.simple_spinner_item, regionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //binding.regionSpinner.setAdapter(dataAdapter);

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vArea(List<ModelArea> areaList) {
        try {
            if (areaList != null) {
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(ActivityDar.this, android.R.layout.simple_spinner_item, areaList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //binding.areaSpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vTeritory(List<ModelTeritorry> teritoryList) {
        try {
            if (teritoryList != null) {
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(ActivityDar.this, android.R.layout.simple_spinner_item, teritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerTeri.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void vSTeritory(List<ModelSubTeritorry> steritoryList) {
       //String steri = gson.toJson(steritoryList);
       // System.out.println(steri);
        try {
            if (steritoryList != null) {
                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(ActivityDar.this, android.R.layout.simple_spinner_item, steritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerTeri.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vMarket(List<ModelMarket> marketList) {

    }
    @Override
    public void getRemarks(int empid, int teri_id, String date, String Reason) {
        rList.add(new ModelRemarks(empid,teri_id,date,Reason));
        //rList.add(pos,new ModelRemarks(empid,teri_id,date,Reason));


    }

    private void saveDsrApi(List<ModelRemarks> dar) {
        if (NetworkInformation.isConnected(ActivityDar.this)) {
            pd.setTitle("Loading...");
            pd.show();
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(true);

            try {
                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<ResponseResultInfo> call = service.SaveDAR(dar);
                call.enqueue(new Callback<ResponseResultInfo>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.isSuccessful()) {
                         SnackBarManagement._success_CustomMessage(binding.getRoot(),"Successfully Submitted");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                        }
                    }
                });
            } catch (Exception ex) {
            }
        } else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet");
        }

    }
}