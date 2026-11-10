package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;


import com.saidur.creatrix.unicorn.salessolution.Interface.ILeave;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveTypeInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiSeedData;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelLeaveSvBody;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseLeaveList;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterLeave implements ILeave.Presenter {
    ILeave.View view;
    Context context;


    public PresenterLeave(ILeave.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void GetLeaveTyep(int empId, int year) {
        try{
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<List<LeaveTypeInfo>> call = service.GetLeaveType(empId,year);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<List<LeaveTypeInfo>>() {
                @Override
                public void onResponse(Call<List<LeaveTypeInfo>> call, Response<List<LeaveTypeInfo>> response) {
                    view.onLeaveTypeGet(response.body());

                }
                @Override
                public void onFailure(Call<List<LeaveTypeInfo>> call, Throwable t) {
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
    public void SaveLeave(ModelLeaveSvBody aInfo) {

        try{
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<ResponseResultInfo> call = service.SaveLeave(aInfo);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {
                    ResponseResultInfo info =response.body();
                    if(info !=null){
                        if(info.getSuccess()){
                            view.onSaveSuccess(info.getMsd());
                        }else{
                            view.onSaveError(info.getErrorMessage());
                        }
                    }else{
                        view.onSaveError(info.getMsd());
                    }
                }
                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        view.onSaveError("Slow Internet Detected..Please try again");
                    }else{
                        view.onSaveError("Some error occurred..Please try again");
                    }
                }
            });

        }catch (Exception ex){

            String str = ex.toString();
            Log.e("Exception",str);
            view.onSaveError("Some error occurred..Please try again");

        }

    }


    @Override
    public void GetLeaveRecords(HashMap<String,String> map) {
        try{
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<ResponseLeaveList> call = service.GetLeaveRecords(map);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<ResponseLeaveList>() {
                @Override
                public void onResponse(Call<ResponseLeaveList> call, Response<ResponseLeaveList> response) {
                    view.onLeaveRecordsGet(response.body());
                }
                @Override
                public void onFailure(Call<ResponseLeaveList> call, Throwable t) {
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

}
