package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;


import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBMain;
import com.saidur.creatrix.unicorn.salessolution.Database.DBSyncHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ISettings;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCustomerCall;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterSettings implements ISettings.Presenter {
    ISettings.View view;
    ProgressDialog progressDoalog;
    DBMain dbHelper;
    DBCrudHelper crudHelper;
    DBSyncHelper syncDbHelper;
    Context context;
    //ProductSQLiteHelper productSQLiteHelper;
    boolean syncdone=false;
    boolean isMIODone, isASMDone, isRSMDone, isNSMDone;
    boolean isGroupDone;
    boolean isRegionDone;
    boolean isAreaDone;
    boolean isTeritorryDone;
    boolean isSubTeritorryDone;
    boolean isMarketDone;

    public PresenterSettings(ISettings.View view, Context context) {
        this.view = view;
        this.dbHelper = new DBMain(context);
        this.crudHelper = new DBCrudHelper(context);
        this.syncDbHelper = new DBSyncHelper(context);
        this.context = context;
       // this.productSQLiteHelper = new ProductSQLiteHelper(context);
    }
    public void doCustomerSync(int empId) {
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Customer Syncing.... Please Wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try {
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<List<ModelCustomer>> call = service.GetCustomerByUser(empId);
            call.enqueue(new Callback<List<ModelCustomer>>() {
                @Override
                public void onResponse(Call<List<ModelCustomer>> call, Response<List<ModelCustomer>> response) {
                    try {
                       // syncDbHelper.InsertCustomerList(response.body());
                        List<ModelCustomer> custList = response.body();
                        if (custList != null) {
                            boolean isTrue = syncDbHelper.InsertCustomerList(custList);
                            if (isTrue) {
                                progressDoalog.dismiss();
                                view.onCustomerSync("Customer Sync is Successful");

                            } else {
                                progressDoalog.dismiss();
                                view.onError("Not Inserting Data To DB");
                            }
                        }
                        // crudHelper.InsertCustomerInfo_SQLite(response.body());
                        //view.onCustomerSync("Done");
                        Constants.LastSyncTime = new SimpleDateFormat("hh:mm a", Locale.US).format(new Date());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<ModelCustomer>> call, Throwable t) {
                    progressDoalog.dismiss();
                    view.onError("Customer Syncing Failed,Some Error Occurred");
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            //view.onError("Some Error Occurred Syncing Customer ");
            view.onError("Please Try Again,Get an exception");
        }

    }

    @Override
    public void doProductSync(int empId) {
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Product Synchronizing....");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
      /*  try {
            ProductApi service = RetrofitClientInstance.getRetrofitInstance().create(ProductApi.class);
            Call<List<Product>> call = service.GetProductList(empId);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    List<Product> productList = response.body();
                    if (productList != null) {
                        boolean isTrue = syncDbHelper.InsertProducts(productList);
                        if (isTrue) {
                            progressDoalog.dismiss();
                            view.onProductSync("Product Sync is Successful");

                        } else {
                            progressDoalog.dismiss();
                            view.onProductSync("Product Sync is Successful");
                        }
                    }
                    //productSQLiteHelper.InsertIntoProductTable(response.body());
                    // crudHelper.InsertProductInfo_SQLite(response.body());

                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        view.onError("Some Error Occurred Syncing Product ");
                    } else {
                        view.onError("Some Error occured");

                    }


                }
            });

        } catch (Exception ex) {
        }*/
    }

    @Override
    public void doDoctorSync(int empId) {
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Doctor Synchronizing....");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try {
            //All DoctorDashBoard Api call
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorListViewModel>> call = service.GetDoctorList(empId);
            call.enqueue(new Callback<List<DoctorListViewModel>>() {
                @Override
                public void onResponse(Call<List<DoctorListViewModel>> call, Response<List<DoctorListViewModel>> response) {
                    List<DoctorListViewModel> doctors = response.body();
                    if (doctors != null) {
                        boolean isTrue = syncDbHelper.InsertDoctorList(doctors);
                        progressDoalog.dismiss();
                        if (isTrue) {
                            view.onDoctorSync("Doctor Sync is Successful",true);
                        } else {
                            //DoctorInfoGetView("Error");
                            view.onDoctorSync("Slow Network detected",false);
                        }
                    }else {
                        view.onDoctorSync("DoctorDashBoard Sync is Successful",true);
                    }

                }
                @Override
                public void onFailure(Call<List<DoctorListViewModel>> call, Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        view.onError("Some Error occured");
                    } else {
                        view.onError("Some Error occured");
                    }
                }
            });
        } catch (Exception ex) {
        }
    }

}
