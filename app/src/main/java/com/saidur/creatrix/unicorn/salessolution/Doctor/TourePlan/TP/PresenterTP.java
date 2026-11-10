package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TP;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.TourPlanReq;

import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import com.saidur.creatrix.unicorn.salessolution.Network.TourApiCall;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.ToastManagment;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterTP implements ITP.Presenter {
    Context context;
    DBCrudHelper dbCrudHelper;
    ITP.View view;
    public PresenterTP(ITP.View view, Context context) {
        this.view = view;
        this.context = context;
        dbCrudHelper = new DBCrudHelper(context);
    }
    @Override
    public void getTourPlanDailyByEmpId(int month, int year, int empId,String date,boolean isFinalSubmit) {
        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<List<TourPlanViewModel>> call = service.GetTourPlanDataDetail(month, year, empId,date);
            call.enqueue(new Callback<List<TourPlanViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<TourPlanViewModel>> call, @NonNull Response<List<TourPlanViewModel>> response) {
                   view.OnTourPlanDailyDataGet(response.body(),isFinalSubmit);
                }

                @Override
                public void onFailure(@NonNull Call<List<TourPlanViewModel>> call, @NonNull Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        ToastManagment.SlowNetworkToast(context);
                        view.OnFailour("recyclerViewDaylistGone");

                    } else {
                        ToastManagment.GetLongToast(context, "Some error occurred");
                        view.OnFailour("recyclerViewDaylistGone");
                    }
                }
            });

        } catch (Exception ex) {
            ToastManagment.GetLongToast(context, "Some error occurred");
            view.OnFailour("recyclerViewDaylistGone");

        }
    }
    @Override
    public void getTourPlanDataByEmpId(int month, int year, int empId) {
        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<List<TourPlanViewModel>> call = service.GetTourPlanData(month, year, empId);
            call.enqueue(new Callback<List<TourPlanViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<TourPlanViewModel>> call, @NonNull Response<List<TourPlanViewModel>> response) {
                    if (response.body() != null) {
                        view.OnTourPlanDataGet(response.body());
                    } else {}
                }
                @Override
                public void onFailure(@NonNull Call<List<TourPlanViewModel>> call, @NonNull Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        ToastManagment.SlowNetworkToast(context);
                        view.OnFailour("recyclerViewDaylistGone");
                    } else {
                        /*  if(progressDoalog!=null||progressDoalog.isShowing())
                        {
                            progressDoalog.dismiss();
                        }*/
                        ToastManagment.GetLongToast(context, "Some error occurred");
                        view.OnFailour("recyclerViewDaylistGone");
                    }
                }
            });

        } catch (Exception ex) {
            /*if(progressDoalog!=null ||progressDoalog.isShowing())
            {
                progressDoalog.dismiss();
            }*/
            ToastManagment.GetLongToast(context, "Some error occurred,TourPlanData Exception : "+ex.getLocalizedMessage().toString());
            view.OnFailour("recyclerViewDaylistGone");

        }
    }
    @Override
    public void saveTourPlanPerdayByEmpId(TourPlanReq tpPost) {
        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<ResultInfo> call = service.SaveTourPlanperday(tpPost);
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResultInfo> call, @NonNull Response<ResultInfo> response) {
                    ResultInfo info = response.body();


                    System.out.println(info);
                    if (info.getSuccess() == true) {
                        view.OnSuccessTPPDay(info.getMsd());
                    } else {
                        view.OnFailour(info.getMsd());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultInfo> call, @NonNull Throwable t) {
                    //progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        // ShowErrorMsg();
                    } else {
                        //ShowErrorMsg();
                    }
                }
            });

        } catch (Exception ex) {
            //progressDoalog.dismiss();
            String str = ex.toString();
            Log.e("Exception", str);
            // ShowErrorMsg();
        }
    }
}

