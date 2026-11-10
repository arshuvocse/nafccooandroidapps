package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;


import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBMain;
import com.saidur.creatrix.unicorn.salessolution.Interface.IOrderManagement;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelResponseInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CordinateUpdate;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMasterModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMasterNew;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiOrderProcess;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitOrderClientInstance;

import java.net.SocketTimeoutException;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterOrderManagement implements IOrderManagement.Presenter {
    IOrderManagement.View view;
    ProgressDialog progressDoalog;
    DBMain dbHelperMain;
    DBCrudHelper dbCrudHelper;
    Context context;

    public PresenterOrderManagement(IOrderManagement.View view, Context context) {
        this.view = view;
        this.context = context;
        dbHelperMain = new DBMain(context);
        dbCrudHelper = new DBCrudHelper(context);
    }

    @Override
    public void makeOrder(OrderMaster aOrder) {
        progressDoalog = new ProgressDialog((Context) view);
        progressDoalog.setMessage("Requirement is Processing.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
       /* try {
            ApiOrderProcess service = RetrofitClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            //  Call<ResultInfo> call = service.MakeOrder(aOrder);
            Call<ResultInfo> call = service.MakeSampleOrder(aOrder);
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResultInfo> call, @NonNull Response<ResultInfo> response) {
                    progressDoalog.dismiss();
                    try {
                        ResultInfo info = response.body();
                        Log.e("Error", response.body().toString());
                        String st = "false";
                        if (info.getSuccess() == true) {
                            view.onOrderSuccess("This Order has been Submitted Successfully","who");
                        }else {
                            view.onOrderError("Order Not Submitted..Try Again");
                        }
                    } catch (Exception exception) {
                        view.onOrderError("Something went wrong... Please try again");
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResultInfo> call, @NonNull Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        view.onOrderError("Slow Connection Detected. Please try again");
                    } else {
                        view.onOrderError("Something went wrong... Please try again");
                    }
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            String str = ex.toString();
            Log.e("Exception", str);
            view.onOrderError(ex.toString());
        }*/

    }
    @SuppressLint("Range")
    public void draftOrderWithSynced(OrderMaster aOrder) {
        SQLiteDatabase database = dbHelperMain.getWritableDatabase();
        int orderId = 0;
        String MioCode = "";
        String CustomerCode = "";
        String CustomerName = "";
        String SubmissionDate = "";
        try {

            String insertQuery = "Insert into tblOrderMaster(EmpId,ComUnitId,MIOCode,CustomerCode,CustomerName,SubmissionDate,CollectionDate,Status) " +
                    "values('" + aOrder.getEmpId() + "','" + aOrder.getComunitId() + "','" + aOrder.getMioCode() + "','" + aOrder.getCustomerCode() + "','" + aOrder.getCustomerName() + "','" + aOrder.getSubmittedDate() + "','" + aOrder.getCollectionDate() + "','synced')";
            database.execSQL(insertQuery);
            Cursor cursor = database.rawQuery("SELECT OrderIdLocal FROM tblOrderMaster ORDER BY OrderIdLocal DESC LIMIT 1", null);

            int id = 0;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    id = cursor.getInt(cursor.getColumnIndex("OrderIdLocal"));
                }
            }
            int ProductId = 0;
            String ProductCode = "";
            String ProductName = "";
            int Quantity = 0;
            String PackSize = "";
            String UnitPrice = "";
            String VatPercentage = "";
            String VatAmountPerunit = "";

            for (int i = 0; i < aOrder.getOrderDetails().size(); i++) {
                ModelProduct aInfo = aOrder.getOrderDetails().get(i);
                ProductId = aInfo.getProductId();
                ProductName = aInfo.getProductName();
                Quantity = aInfo.getQuantity();
                UnitPrice = aInfo.getUnitPrice().toString();
                VatPercentage = aInfo.getVatPercentage().toString();
                String insertDetal = "Insert into tblOrderDetails(OrderIdLocal,ProductId,ProductName,Quantity,UnitPrice,VatPercentage) " +
                        "values('" + id + "','" + ProductId + "','" + ProductName + "','" + Quantity + "','" + UnitPrice + "','" + VatPercentage + "')";
                database.execSQL(insertDetal);
                view.onOrderSuccess("This Order has been Submitted Successfully","Draft");
            }

            database.close();
        } catch (Exception exception) {
            database.close();
            view.onOrderError("Some Error Occurred Submitting Current Order.. Please try again after some times");
            Log.e("EX", exception.toString());

        }
    }

    @SuppressLint("Range")
    @Override
    public void draftOrder(OrderMaster aOrder) {
        SQLiteDatabase database = dbHelperMain.getWritableDatabase();
        int orderId = 0;
        String MioCode = "";
        String CustomerCode = "";
        String CustomerName = "";
        String SubmissionDate = "";
        try {
            String insertQuery = "Insert into tblOrderMaster(EmpId,ComUnitId,MIOCode,CustomerCode,CustomerMasterId,CustomerName,CustomerAdress,SubmissionDate,CollectionDate,DeliveryDate,Remarks) " +
                    "values('" + aOrder.getEmpId() + "','" + aOrder.getComunitId() + "','" + aOrder.getMioCode() + "','" + aOrder.getCustomer().getCustomerCode() + "','" + aOrder.getCustomer().getCustomerMasterId() + "','" + aOrder.getCustomer().getCustomerName() + "','" + aOrder.getCustomer().getAddress() + "','" + aOrder.getSubmittedDate() + "','" + aOrder.getCollectionDate() + "','" + aOrder.getDeliveryDate() + "','" + aOrder.getRemarks() + "')";
            database.execSQL(insertQuery);
            Cursor cursor = database.rawQuery("SELECT OrderIdLocal FROM tblOrderMaster ORDER BY OrderIdLocal DESC LIMIT 1", null);
            int id = 0;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    id = cursor.getInt(cursor.getColumnIndex("OrderIdLocal"));
                }
            }
            int ProductId = 0;
            String ProductCode = "";
            String ProductName = "";
            int Quantity = 0;
            String PackSize = "";
            String UnitPrice = "";
            String VatPercentage = "";
            String VatAmountPerunit = "";
            for (int i = 0; i < aOrder.getOrderDetails().size(); i++) {
                ModelProduct aInfo = aOrder.getOrderDetails().get(i);
                ProductId = aInfo.getProductId();
                ProductName = aInfo.getProductName();
                Quantity = aInfo.getQuantity();
                UnitPrice = aInfo.getUnitPrice().toString();
                VatPercentage = aInfo.getVatPercentage().toString();
                String insertDetal = "Insert into tblOrderDetails(OrderIdLocal,ProductId,ProductName,Quantity,UnitPrice,VatPercentage) " +
                        "values('" + id + "','" + ProductId + "','" + ProductName + "','" + Quantity + "','" + UnitPrice + "','" + VatPercentage + "')";
                database.execSQL(insertDetal);
                // view.onOrderSuccess("This Order has been Drafted Successfully");
                view.onOrderDraftSuccess("This Order has been Drafted Successfully");
            }
            database.close();
        } catch (Exception exception) {
            database.close();
            view.onOrderError("Some Error Occurred Drafting Current Order.. Please try again after some times");
            Log.e("EX", exception.toString());

        }


    }

    @Override
    public void SyncOrder(OrderMaster aOrder, int localOrderId) {
        progressDoalog = new ProgressDialog((Context) view);
        progressDoalog.setMessage("Order is Processing.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try {
            ApiOrderProcess service = RetrofitClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<ResponseResultInfo> call = service.MakeOrder(aOrder);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {
                    progressDoalog.dismiss();
                    ResponseResultInfo info = response.body();
                    Log.e("Error", response.body().toString());
                    String st = "false";
                    if (info.getSuccess() == true) {
                        st = "Order Has Been Synced Successfully";
                        dbCrudHelper.UpdateOrderSynchInfo(localOrderId);
                        view.onOrderSuccess(st,"Sync");
                    }
                }

                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        view.onOrderError("Slow Connection Detected. Please try again");
                    } else {
                        view.onOrderError("Something went wrong... Please try again");
                    }
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            String str = ex.toString();
            Log.e("Exception", str);
            view.onOrderError(ex.toString());
        }
    }

    @Override
    public void UpdateCustomerLocation(CordinateUpdate location) {
        progressDoalog = new ProgressDialog((Context) view);
        progressDoalog.setMessage("Updating Location.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try {
            ApiOrderProcess service = RetrofitOrderClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<ModelResponseInfo> call = service.UpdateLocation(location);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<ModelResponseInfo>() {
                @Override
                public void onResponse(Call<ModelResponseInfo> call, Response<ModelResponseInfo> response) {
                    progressDoalog.dismiss();
                    try {
                        ModelResponseInfo info = response.body();
                        if (info != null) {
                            if (info.getMessage().equals("Success")) {
                                view.onGenericSuccess("Customer location updated");
                            }
                        } else {
                            view.onGenericError("Something went wrong... Please try again");

                        }

                    } catch (Exception exception) {
                        view.onGenericError("Something went wrong... Please try again");
                    }

                }

                @Override
                public void onFailure(Call<ModelResponseInfo> call, Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        view.onGenericError("Slow Connection Detected. Please try again");
                    } else {
                        view.onGenericError("Something went wrong... Please try again");
                    }
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            view.onGenericError(ex.toString());
        }

    }

    @Override
    public void makeOrder2(OrderMasterModel aOrder,String Who) {

        progressDoalog = new ProgressDialog((Context) view);
        progressDoalog.setMessage("Order is Processing.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try {
            ApiOrderProcess service = RetrofitOrderClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<ResponseResultInfo> call = service.SubmitOrder2(aOrder);

            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {
                    try {
                        ResponseResultInfo info = response.body();
                        Log.e("Error", response.body().toString());
                        String st = "false";
                        if (info.getSuccess() == true) {
                            progressDoalog.dismiss();
                            view.onOrderSuccess("This Order has been Submitted Successfully",Who);
                        }else {
                            progressDoalog.dismiss();
                            // view.onOrderError("Something went wrong... Please try again");
                            view.onOrderError(info.getErrormessagenew());
                        }
                    } catch (Exception exception) {
                        progressDoalog.dismiss();
                        view.onOrderError("Something went wrong... Please try again");
                    }

                }

                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        view.onOrderError("Slow Connection Detected. Please try again");
                    } else {
                        view.onOrderError("Something went wrong... Please try again");
                    }
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            String str = ex.toString();
            Log.e("Exception", str);
            view.onOrderError(ex.toString());
        }

    }

    @Override
    public void draftOrder2(OrderMasterNew aOrder) {
        SQLiteDatabase database = dbHelperMain.getWritableDatabase();
        int orderId = 0;
        String MioCode = "";
        String CustomerCode = "";
        String CustomerName = "";
        String SubmissionDate = "";
    }


}
