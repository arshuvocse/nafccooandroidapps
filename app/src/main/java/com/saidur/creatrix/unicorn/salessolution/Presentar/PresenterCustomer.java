package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCustomerHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ICustomerAdd;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.CustomerSvModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCustomerCall;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterCustomer implements ICustomerAdd.Presenter {
    ICustomerAdd.View view;
    Context context;
    DBCrudHelper dbCrudHelper;
    DBCustomerHelper dbCustomer;
    public PresenterCustomer(ICustomerAdd.View view, Context context) {
        this.view = view;
        this.context = context;
        dbCrudHelper=new DBCrudHelper(context);
        dbCustomer = new DBCustomerHelper(context);
    }

    @Override
    public void GetProgramType() {
       /* try {

            view.onProgramType(dbDoctor.getProgramTypeListFromSQLite(0));
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/
    }

    @Override
    public void GetProviderType() {
        try {
            view.onProviderType(dbCustomer.getProviderTypeListForCustFromSQLite(0,1));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void GetSMCType() {
       /* try {
            view.onSMCType(dbDoctor.getSMCTypeListForCustFromSQLite(0,1));
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/
    }

    @Override
    public void GetCustomerType() {

        try{
           /* apiSeedDataCall service = RetrofitClientInstance.getRetrofitInstance().create(apiSeedDataCall.class);
            Call<List<CustomerType>> call = service.GetCustomerType();
            call.enqueue(new Callback<List<CustomerType>>() {
                @Override
                public void onResponse(Call<List<CustomerType>> call, Response<List<CustomerType>> response) {
                    view.onCustomerTypeReceived(response.body());
                }
                @Override
                public void onFailure(Call<List<CustomerType>> call, Throwable t) {
                    if(t instanceof SocketTimeoutException){
                    }else{
                    }
                }
            });*/

          //  view.onCustomerTypeReceived(dbCrudHelper.getCustTypeListFromSQLite());
        }catch (Exception ex){
        }

    }

    @Override
    public void GetStationType() {
       /* try{
            apiSeedDataCall service = RetrofitClientInstance.getRetrofitInstance().create(apiSeedDataCall.class);
            Call<List<StationType>> call = service.GetStation();
            call.enqueue(new Callback<List<StationType>>() {
                @Override
                public void onResponse(Call<List<StationType>> call, Response<List<StationType>> response) {
                    view.onStationReceived(response.body());
                }
                @Override
                public void onFailure(Call<List<StationType>> call, Throwable t) {
                    if(t instanceof SocketTimeoutException){
                    }else{
                    }
                }
            });

        }catch (Exception ex){
        }*/

    }

    @Override
    public void SaveCustomer(CustomerSvModel aInfo, String who) {
        ProgressDialog progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Customer is Saving.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try{
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<ResponseResultInfo> call = service.SaveCustomer(aInfo);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {

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
                            view.onSubmitError(info.getMsd());
                        }
                    }else{
                        progressDoalog.dismiss();
                        view.onSubmitError("Some error occurred... Please try again");

                    }
                    /*if(info !=null){
                        if(info.getSuccess() == true){
                            view.onSubmitSuccess("Customer Saved Successfully");

                        }else{
                            view.onSubmitError("Some error occurred..Please try again");
                        }

                    }else{
                        view.onSubmitError("Some error occurred..Please try again");

                    }*/

                }
                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
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
        }


    }
}
