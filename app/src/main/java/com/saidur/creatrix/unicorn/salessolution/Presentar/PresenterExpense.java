package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;

import com.saidur.creatrix.unicorn.salessolution.Interface.IExpenseClaim;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseClaimMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeMaster;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiSeedData;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterExpense implements IExpenseClaim.Presenter {

    IExpenseClaim.View view;
    Context context;
    public PresenterExpense(IExpenseClaim.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void GetExpenseType() {
        try{
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<List<ExpenseTypeMaster>> call = service.Get_ExpenseType();
            call.enqueue(new Callback<List<ExpenseTypeMaster>>() {
                @Override
                public void onResponse(Call<List<ExpenseTypeMaster>> call, Response<List<ExpenseTypeMaster>> response) {
                    view.onExpenseTypeGet(response.body());
                }
                @Override
                public void onFailure(Call<List<ExpenseTypeMaster>> call, Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        Log.e("TAG", "onFailure: ", t);
                    }else{
                        Log.e("TAG", "onFailure: ", t);
                    }
                }
            });

        }catch (Exception ex){
            Log.e("TAG", "onFailure: ", ex);
        }
    }

    @Override
    public void GetExpenseDetails(int id) {

        try{
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<List<ExpenseTypeDetails>> call = service.Get_ExpenseTypeDetails(id);
            call.enqueue(new Callback<List<ExpenseTypeDetails>>() {
                @Override
                public void onResponse(Call<List<ExpenseTypeDetails>> call, Response<List<ExpenseTypeDetails>> response) {
                    view.onExpenseTypeDetailsGet(response.body());

                }
                @Override
                public void onFailure(Call<List<ExpenseTypeDetails>> call, Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        Log.e("TAG", "onFailure: ", t);
                    }else{
                        Log.e("TAG", "onFailure: ", t);
                    }
                }
            });

        }catch (Exception ex){
            Log.e("TAG", "onFailure: ", ex);
        }

    }

    @Override
    public void SaveExpenseClaim(ExpenseClaimMaster aInfo) {
        ProgressDialog progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Saving.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try{
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<ResponseResultInfo> call = service.SaveExpenseClaim(aInfo);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {
                    progressDoalog.dismiss();
                    ResponseResultInfo info =response.body();
                    if(info !=null){
                        if(info.getSuccess() == true){
                            view.onSaveSuccess("Expense Claim Successfully Submitted");
                        }
                        else if(info.getValiCheck()==true)
                        {
                            view.onSaveError("You Already Claim Your Expense");
                        }

                    }else{
                        view.onSaveError("Some error occurred... Please try again");

                    }

                }
                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                    progressDoalog.dismiss();
                    if(t instanceof SocketTimeoutException){
                        view.onSaveError("Slow Internet Detected..Please try again");
                    }else{
                        view.onSaveError("Some error occurred..Please try again");
                    }
                }
            });

        }catch (Exception ex){
            progressDoalog.dismiss();
            String str = ex.toString();
            Log.e("Exception",str);
            view.onSaveError("Some error occurred..Please try again");

        }
    }
}
