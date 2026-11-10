package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toolbar;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterTwwplist;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpMaster;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTwwpBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.DialogFinalSubmitBinding;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTwwp extends AppCompatActivity {
    ActivityTwwpBinding binding;
    DialogFinalSubmitBinding dbinding;
    ComponentLoadingbarBinding lbinding;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    ProgressDialog pd;
    AdapterTwwplist adapter;
    Dialog finalsubmit,pd_process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTwwpBinding.inflate(getLayoutInflater());
        lbinding=ComponentLoadingbarBinding.inflate(getLayoutInflater());
        dbinding=DialogFinalSubmitBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        prepareDailog();
        dbCrudHelper = new DBCrudHelper(ActivityTwwp.this);
        session = dbCrudHelper.getSession();
        callTwwpMaster(session.getEmpInfoId());
        getTwwpList(session.getEmpInfoId());

        binding.status.setOnClickListener(view -> {
            finalsubmit.show();
        });
        dbinding.btnCancel.setOnClickListener(view -> {
            finalsubmit.dismiss();
        });
        dbinding.btnGoto.setOnClickListener(view -> {

            if(NetworkInformation.isConnected(ActivityTwwp.this)){
                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Processing...");
                callFinalSubmit(session.getEmpInfoId());
            }else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(),"No Internet");
            }
        });
        binding.addTwwp.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString("From", "ActivityTwwp");
            DialogFragment dialog = TwwpDialogFragment.newInstance();
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "ActivityTwwp");
            ((TwwpDialogFragment)dialog).setCallback(new TwwpDialogFragment.callbackHitApi() {
                @Override
                public void onCallBackHit(String value) {
                    getTwwpList(session.getEmpInfoId());
                }
            });
        });
    }

    private void callTwwpMaster(int empInfoId) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ModelTwwpMaster> call = service.GetTwwpMaster(empInfoId);
            call.enqueue(new Callback<ModelTwwpMaster>() {
                @Override
                public void onResponse(@NonNull Call<ModelTwwpMaster> call, @NonNull Response<ModelTwwpMaster> response) {

                    if (response.body()!=null) {

                        LoadMasterView(response.body());

                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(),"Response Null");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ModelTwwpMaster> call, @NonNull Throwable t) {

                    if (t instanceof SocketTimeoutException) {

                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);

        }
    }

    private void LoadMasterView(ModelTwwpMaster body) {


            if(body.isFinalSubmit()==false){
                dbinding.tvMsg.setText("Final Submit Not Done Yet!!!");
                dbinding.btnGoto.setVisibility(View.VISIBLE);

            }else{

                if(body.getApprovalStatus()==0){
                    dbinding.tvMsg.setText("Final Submit Done - Approval Pending");
                    dbinding.btnGoto.setVisibility(View.GONE);
                    binding.addTwwp.setVisibility(View.GONE);
                }
                if(body.getApprovalStatus()==2){
                    dbinding.tvMsg.setText("TWWP Is Approved");
                    dbinding.btnGoto.setVisibility(View.GONE);
                    binding.addTwwp.setVisibility(View.GONE);
                }
                if(body.getApprovalStatus()==3){
                    dbinding.tvMsg.setText("TWWP Is DisApprove");
                    dbinding.btnGoto.setVisibility(View.VISIBLE);
                    binding.addTwwp.setVisibility(View.VISIBLE);
                }
            }
        }


    private void callFinalSubmit(int empInfoId) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResponseResultInfo> call = service.TwwpFinalSubmit(String.valueOf(empInfoId));
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {

                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }

                    if (response.body().isSuccess) {
                        if (finalsubmit != null || finalsubmit.isShowing()) {
                            finalsubmit.dismiss();
                        }
                        callTwwpMaster(session.getEmpInfoId());
                        SnackBarManagement._error_CustomMessage(binding.getRoot(),"Final Submit Done");

                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(),"Response Null");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (finalsubmit != null || finalsubmit.isShowing()) {
                        finalsubmit.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {

                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

    private void prepareDailog() {
        finalsubmit=new Dialog(ActivityTwwp.this);
        finalsubmit.setContentView(dbinding.getRoot());
        finalsubmit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        finalsubmit.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        finalsubmit.getWindow().getAttributes().gravity = Gravity.CENTER;

        pd_process=new Dialog(ActivityTwwp.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    private void getTwwpList(int id) {
        pd = new ProgressDialog(ActivityTwwp.this);
        pd.setMessage("Loading....");
        pd.show();
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<ModelTwwpList>> call = service.GetTwwpList(id);
            call.enqueue(new Callback<List<ModelTwwpList>>() {
                @Override
                public void onResponse(@NonNull Call<List<ModelTwwpList>> call, @NonNull Response<List<ModelTwwpList>> response) {

                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.body() != null) {

                        LoadRV(response.body());

                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(),"Response Null");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<ModelTwwpList>> call, @NonNull Throwable t) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {

                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

    private void LoadRV(List<ModelTwwpList> data) {
        adapter = new AdapterTwwplist(ActivityTwwp.this, data);
        binding.rvTwwplist.setLayoutManager(new LinearLayoutManager(ActivityTwwp.this, RecyclerView.VERTICAL, false));
        binding.rvTwwplist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}