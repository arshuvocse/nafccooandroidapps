package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;

import androidx.annotation.NonNull;

import com.saidur.creatrix.unicorn.salessolution.Interface.ILeaveApproval;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalData;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalRQ;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiSeedData;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterLeaveApproval implements ILeaveApproval.Presenter {
    ILeaveApproval.View view;
    Context context;
    public PresenterLeaveApproval(ILeaveApproval.View view, Context context) {
        this.view = view;
        this.context = context;
    }
    @Override
    public void getLeaveApprovalList(String pram, Map<String, String> mapparam) {
        ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
        Call<List<LeaveApprovalData>> calls = service.GetLeaveListApproval(pram,mapparam);
        calls.enqueue(new Callback<List<LeaveApprovalData>>() {
            @Override
            public void onResponse(Call<List<LeaveApprovalData>> call, Response<List<LeaveApprovalData>> response) {
                if(response!=null)
                {
                    List<LeaveApprovalData> data= response.body();
                    view.onGetLeaveApprovalList(data);
                }
                else {
                    view.onError("Leave Data Not Found");
                }
            }

            @Override
            public void onFailure(Call<List<LeaveApprovalData>> call, Throwable t) {

            }
        });
    }
    @Override
    public void SaveLeaveApproval(LeaveApprovalRQ aInfo) {
        try {
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<ResponseResultInfo> call = service.SaveLeaveApproval(aInfo);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {
                    if(response!=null && response.body().isSuccess)
                    {
                        view.onSaveSuccess("Verified");
                    }
                    else {
                        view.onSaveSuccess("Not Verified");
                    }
                }

                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                    } else {
                    }
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

