package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import com.saidur.creatrix.unicorn.salessolution.Interface.Rcv_TourPlanListener;
import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.TourApiCall;
import com.saidur.creatrix.unicorn.salessolution.NormalAdapter.TourStatusBottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.Service.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTourePlanAcBinding;

import java.net.SocketTimeoutException;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourePlanAC extends AppCompatActivity implements Rcv_TourPlanListener, TourStatusBottomSheetDialog.BottomSheetListener {
    ActivityTourePlanAcBinding binding;
    FragmentManager fragmentManager;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toure_plan_ac);

        Constants.WHO="TourePlanAC";
      /*  if (savedInstanceState==null){
            fragmentManager = getSupportFragmentManager();
            TourePlanListFragment tplFragment = new TourePlanListFragment();
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left).replace(R.id.fragmentContaineplant,tplFragment).commit();

        }*/
    }

    @Override
    public void ReloadCurrentActivity() {

    }

    @Override
    public void FinalSubmitClick(int month, int year, int empId, String remarks) {
        try{
            progressDoalog = new ProgressDialog(TourePlanAC.this);
            progressDoalog.setMessage("Final Submit Processing....");
            progressDoalog.show();
            progressDoalog.setCanceledOnTouchOutside(false);
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<ResultInfo> call = service.UpdateTourFinalSubmit(empId,month,year,remarks);
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                    progressDoalog.dismiss();
                    ResultInfo info =response.body();
                    if(info.getSuccess() == true){
                        new AlertDialog.Builder(TourePlanAC.this)
                                .setTitle("Success")
                                .setMessage("Final Tour Plan submission done for selected month")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent mIntent = getIntent();
                                        finish();
                                        startActivity(mIntent);
                                    }
                                }).setCancelable(false).show();
                    }
                }
                @Override
                public void onFailure(Call<ResultInfo> call, Throwable t) {
                    progressDoalog.dismiss();
                    if(t instanceof SocketTimeoutException){
                        SnackBarManagement._error_CustomMessage(binding.master,"Some error occurred. Please try again");
                    }else{
                        SnackBarManagement._error_CustomMessage(binding.master,"Some error occurred. Please try again");
                    }
                }
            });

        }catch (Exception ex){
            progressDoalog.dismiss();
            SnackBarManagement._error_CustomMessage(binding.master,"Some error occurred. Please try again");
            //Toast.makeText(TourePlanAC.this,"Some error occurred. Please try again",Toast.LENGTH_LONG).show();

        }
    }


}