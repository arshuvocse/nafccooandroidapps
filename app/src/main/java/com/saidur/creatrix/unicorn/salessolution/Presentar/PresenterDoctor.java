package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;
import android.util.Log;


import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBSyncHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IDoctor;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSM;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterDoctor implements IDoctor.Presenter {
    IDoctor.View view;
    Context context;
    DBDoctorHelper dbDoctorHelper;
    DBSyncHelper syncDbHelper;

    public PresenterDoctor(IDoctor.View view, Context context) {
        this.view = view;
        this.context = context;
        dbDoctorHelper = new DBDoctorHelper(context);
        syncDbHelper = new DBSyncHelper(context);
    }


    @Override
    public void GetDegree(int doctypeId) {
        try {
            view.onDegreeGet(dbDoctorHelper.getDegreeListFromSQLite());

        } catch (Exception ex) {
        }
    }

    @Override
    public void GetSpeciality() {
        try {
            view.onSpecialityGet(dbDoctorHelper.getSpecListFromSQLite());

        } catch (Exception ex) {
        }
    }

    @Override
    public void GetDoctorDesignation(int id) {
        try {
            view.onDoctorDesignationGet(dbDoctorHelper.getDesigListFromSQLite(id));
        } catch (Exception ex) {
        }
    }

    @Override
    public void GetDoctorGender() {
        try {
            view.onDoctorGenderGet(dbDoctorHelper.getGenderListFromSQLite());
        } catch (Exception ex) {
        }
    }

    @Override
    public void GetDoctorPracticeShift() {
        try {
            view.onDoctorPracticeGet(dbDoctorHelper.getPracticeShiftFromSQLite());
        } catch (Exception ex) {
        }
    }

    @Override
    public void getCustomerListByMarketId(int MarketId) {
        try {
            view.onCustomerListByMarketIdGet(dbDoctorHelper.getCustomerListByMarketIdFromSQLite(MarketId));
        } catch (Exception ex) {
        }
    }

    @Override
    public void GetContactType(int id) {
        try {
            view.onContactTypeGet(dbDoctorHelper.getContactTypeListFromSQLite(id));
        } catch (Exception ex) {
        }
    }

    @Override
    public void GetBrand() {
        try {
            view.onBrandGet(dbDoctorHelper.getBrandListFromSQLite());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetPriority() {
        try {
            view.onPriorityGet(dbDoctorHelper.getPriorityFromSQLite());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetDoctorCategory(int id) {
        try {
            view.onDocCategoryGet(dbDoctorHelper.getDocCategoryListFromSQLite(id));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void SaveDoctor(DoctorSM doctorSM, String type) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResponseResultInfo> call = service.SaveDoctor(doctorSM);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {
                    ResponseResultInfo info = response.body();
                    if (info != null) {
                        if (info.getSuccess()) {

                            if(type.equals("Submit"))
                            {
                                view.onSubmitSuccess("Submit");
                            }
                            if(type.equals("Update"))
                            {
                                view.onSubmitSuccess("Update");
                            }

                        } else {
                            view.onSubmitError(info.getMsd());
                        }

                    } else {
                        view.onSubmitError("Response Not Found!!!");

                    }

                }

                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        view.onSubmitError("Slow Internet Detected..Please try again");
                    } else {
                        view.onSubmitError("Some error occurred..Please try again");
                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            view.onSubmitError("Some error occurred..Please try again");

        }

    }
}
