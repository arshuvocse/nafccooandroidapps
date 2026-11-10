package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCustomerHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IPrescription;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRx;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterPrescription implements IPrescription.Presenter {
    IPrescription.View view;
    Context context;
    DBCrudHelper dbCrudHelper;
    DBCustomerHelper dbCustomer;
    public PresenterPrescription(IPrescription.View view, Context context) {
        this.view = view;
        this.context = context;
        dbCrudHelper=new DBCrudHelper(context);
        dbCustomer = new DBCustomerHelper(context);
    }

    @Override
    public void getRxReport(int empId) {
      /*  try{
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<ResponseResultInfo> call = service.SaveCustomer(aInfo);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {

                    ResponseResultInfo info =response.body();
                    if(info !=null){
                        if(info.getSuccess() == true){
                            progressDoalog.dismiss();
                            view.onSubmitSuccess("Customer Successfully Submitted",who);
                        }
                        else if(info.getValiCheck()==true)
                        {
                            progressDoalog.dismiss();
                            view.onSubmitError("This Customer Already Exist");
                        }
                        else{
                            progressDoalog.dismiss();
                            view.onSubmitError("Some error occurred... Please try again");
                        }
                    }else{
                        progressDoalog.dismiss();
                        view.onSubmitError("Some error occurred... Please try again");

                    }
                    *//*if(info !=null){
                        if(info.getSuccess() == true){
                            view.onSubmitSuccess("Customer Saved Successfully");

                        }else{
                            view.onSubmitError("Some error occurred..Please try again");
                        }

                    }else{
                        view.onSubmitError("Some error occurred..Please try again");

                    }*//*

                }
                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    progressDoalog.dismiss();
                    if(t instanceof SocketTimeoutException){
                        progressDoalog.dismiss();
                        view.onSubmitError("Slow Internet Detected..Please try again");
                    }else{
                        progressDoalog.dismiss();
                        view.onSubmitError("Some error occurred..Please try again");
                    }
                }
            });
        }catch (Exception ex){
            progressDoalog.dismiss();
            String str = ex.toString();
            Log.e("Exception",str);
            view.onSubmitError("Some error occurred..Please try again");
        }*/
    }

    @Override
    public void submitRx(ModelRx modelRx) {

        try{
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResponseResultInfo> call = service.submitRx(modelRx);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {

                    ResponseResultInfo info =response.body();
                    if(info !=null){
                        if(info.getSuccess() == true){
                            view.OnSuccessRx(true);
                        }
                        else{
                            view.OnSuccessRx(false);
                        }
                    }else{
                        view.OnErrorReport("No response found");
                    }


                }
                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        view.OnErrorReport("Slow Internet Detected..Please try again");
                    }else{
                        view.OnErrorReport("Request Failed!!");
                    }
                }
            });
        }catch (Exception ex){
            String str = ex.toString();
            Log.e("Exception",str);
            view.OnErrorReport("Exception Occur : "+str);
        }
    }
}
