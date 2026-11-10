package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBMain;
import com.saidur.creatrix.unicorn.salessolution.Database.DBSyncHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ICustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCustomerCall;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterCustomerList implements ICustomer.Presenter {
    ICustomer.View view;
    ProgressDialog progressDoalog;
    DBMain dbMain;
    DBCrudHelper dbCrudHelper;
    DBSyncHelper dbSyncHelper;
    public PresenterCustomerList(ICustomer.View view, Context context) {
        this.view = view;
        this.dbMain = new DBMain(context);
        this.dbCrudHelper = new DBCrudHelper(context);
        this.dbSyncHelper = new DBSyncHelper(context);
    }

    @Override
    public void GetCustomerByUser(int empId) {
        progressDoalog = new ProgressDialog((Context) view);
        progressDoalog.setMessage("Customer is Loading....");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try{
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<List<ModelCustomer>> call = service.GetCustomerByUser(empId);
            call.enqueue(new Callback<List<ModelCustomer>>() {
                @Override
                public void onResponse(Call<List<ModelCustomer>> call, Response<List<ModelCustomer>> response) {
                   boolean isTrue = dbSyncHelper.InsertCustomerList(response.body());
                    if(isTrue)
                    {
                        progressDoalog.dismiss();
                        view.OnSuccess(response.body());
                    }

                  // crudHelper.InsertCustomerInfo_SQLite(response.body());

                }
                @Override
                public void onFailure(Call<List<ModelCustomer>> call, Throwable t) {
                    progressDoalog.dismiss();
                    if(t instanceof SocketTimeoutException){
                        view.OnError(1);
                    }else{
                        view.OnError(2);
                    }


                }
            });

        }catch (Exception ex){
            progressDoalog.dismiss();
            view.OnError(3);
        }

    }

    @Override
    public void GetCustomerReport(int empId) {
      /*  try{
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<List<CustomerARModel>> call = service.GetCustomerApproveRejLists(empId);
            call.enqueue(new Callback<List<CustomerARModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<CustomerARModel>> call, @NonNull Response<List<CustomerARModel>> response) {
                    if(response.body()!=null)
                    {
                       // boolean isInserted=false;
                       // crudHelper._deleteAllRecordsFromaTable("tblCustomerReport");
                        boolean isDeleted= crudHelper._deleteAllRecordsFromaTableBolean("tblCustomerReport");
                        if(isDeleted)
                        {
                            boolean isInserted= crudHelper.InsertCustomerReport_SQLite(response.body());
                            if(isInserted)
                            {
                                view.OnSuccessCustomerReport(crudHelper.getCustomerReport_SQLite("Approved"));
                            }else {
                                view.OnSuccessCustomerReport(response.body());
                            }
                        }

                    }else {
                        view.OnSuccessCustomerReport(null);
                    }



                }
                @Override
                public void onFailure(Call<List<CustomerARModel>> call, Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        view.OnErrorReport("Slow Connection Detected");
                    }else{
                        view.OnErrorReport("Loading Failed!!Try Again");
                    }


                }
            });
        }catch (Exception ex){

        }*/
    }


}
