package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IAttendance;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelAttendance;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiAttendance;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseButton;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterAttendance implements IAttendance.Presenter {
    IAttendance.View view;
    Context context;
    ProgressDialog progressDoalog;
    DBCrudHelper dbCrudHelper;

    public PresenterAttendance(IAttendance.View view, Context context) {
        this.view = view;
        this.context = context;
        dbCrudHelper = new DBCrudHelper(context);
    }

    @Override
    public void doSavePuncINInfo(ModelAttendance attendance, RelativeLayout masterLayout) {
        if (NetworkInformation.isConnected(context)) {
            progressDoalog = new ProgressDialog((Context) view);
            progressDoalog.setMessage("Punch In is Processing.... Please wait");
            progressDoalog.show();
            progressDoalog.setCanceledOnTouchOutside(false);
            try {
                ApiAttendance service = RetrofitClientInstance.getRetrofitInstance().create(ApiAttendance.class);
                Call<ResponseResultInfo> call = service.SavePunInfo(attendance);
                call.enqueue(new Callback<ResponseResultInfo>() {
                    @Override
                    public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {
                        progressDoalog.dismiss();
                        ResponseResultInfo info = response.body();
                        if (response.body() != null) {
                            assert info != null;
                            if (info.getSuccess()) {
                                if (attendance.getAttType() == 1) {
                                    view.onSuccess(info.getMsd() ,true, 1);
                                }
                                if (attendance.getAttType() == 2) {
                                    view.onSuccess(info.getMsd(),true, 2);
                                }
                            } else {
                                SnackBarManagement._warning_CustomMessage(masterLayout,"Already Attendance done");
                            }
                        } else {
                            Toast.makeText(context, "Attendance Null", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                        if ((progressDoalog != null) && progressDoalog.isShowing())
                        {
                            progressDoalog.dismiss();
                        }
                        if (t instanceof SocketTimeoutException) {
                            view.onError("Slow Connection Detected. Please try again", 1);
                        } else {
                            view.onError("Something went wrong... Please try again", 1);
                        }
                    }
                });

            } catch (Exception ex) {
                if ((progressDoalog != null) && progressDoalog.isShowing())
                {
                    progressDoalog.dismiss();
                }
                String str = ex.toString();
                Log.e("Exception", str);
                view.onError(ex.toString(), 1);
            }
        }
    }


    @Override
    public void button(int empid,String session) {
        if(NetworkInformation.isConnected(context)){
            try{
                ApiAttendance service = RetrofitClientInstance.getRetrofitInstance().create(ApiAttendance.class);
                Call<ResponseButton> call = service.GetButtonStatus(empid,session);
                call.enqueue(new Callback<ResponseButton>() {
                    @Override
                    public void onResponse(Call<ResponseButton> call, Response<ResponseButton> response) {
                        if(response.body()!=null)
                        {
                            ResponseButton info =response.body();
                            view.onButtonView(info);
                        }else {
                            view.onError("No response found",1);
                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseButton> call, Throwable t) {
                        if(t instanceof SocketTimeoutException){
                            view.onError("Slow Connection Detected. Please try again",1);
                        }else{
                            view.onError("Something went wrong... Please try again",1);
                        }
                    }
                });

            }catch (Exception ex){
                String str = ex.toString();
                Log.e("Exception",str);
                view.onError(str,1);
            }
        }
    }


}
