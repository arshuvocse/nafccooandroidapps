package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.datatransport.Transport;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBSyncHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ISyncMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelProviderType;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorCategory;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorContact;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDegree;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDesignation;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSpeciality;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelPracticeShift;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelNSM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDistrict;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDivision;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelGender;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPriority;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPromo;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelThana;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.QuotedPrice;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPurposeViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourTypeViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCustomerCall;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiMasterSync;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiOrderProcess;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiProduct;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitOrderClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.TourApiCall;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDeliveryDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTransport;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWorkType;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model.Gift;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class PresenterSyncMaster implements ISyncMaster.Presenter {
    ISyncMaster.View view;
    Context context;
    DBCrudHelper dbCrudHelper;
    DBSyncHelper dbSyncHelper;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    public PresenterSyncMaster(ISyncMaster.View view, Context context) {
        this.view = view;
        this.context = context;
        dbCrudHelper = new DBCrudHelper(context);
        dbSyncHelper = new DBSyncHelper(context);
    }

    private interface DbInsertOperation<T> {
        boolean insert(List<T> items);
    }

    private interface SyncNotifier {
        void notify(String message, boolean success);
    }

    private void postResult(SyncNotifier notifier, boolean success) {
        mainHandler.post(() -> notifier.notify(success ? "Done" : "Error", success));
    }

    private <T> void runInsertAsync(List<T> items, DbInsertOperation<T> insertOperation, SyncNotifier notifier) {
        dbExecutor.execute(() -> {
            boolean isTrue = false;
            try {
                isTrue = insertOperation.insert(items);
            } catch (Exception ex) {
                Log.e("SyncInsert", "Insert failed", ex);
            }
            postResult(notifier, isTrue);
        });
    }


    @Override
    public void callNSM(String empcode, String emproleempid) {
        //NSM
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelNSM>> call = service.getNSM(empcode, emproleempid);
            call.enqueue(new Callback<List<ModelNSM>>() {
                @Override
                public void onResponse(Call<List<ModelNSM>> call, Response<List<ModelNSM>> response) {
                    List<ModelNSM> teritories = response.body();
                    if (teritories != null) {
                        runInsertAsync(teritories, dbSyncHelper::InsertNSMInfo, view::onGetNSM);
                    } else {
                        postResult(view::onGetNSM, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelNSM>> call, Throwable t) {
                    postResult(view::onGetNSM, false);
                }
            });
        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callDZSM(String empcode, String emprole) {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelRSM>> call = service.getRSM(empcode, emprole);
            call.enqueue(new Callback<List<ModelRSM>>() {
                @Override
                public void onResponse(Call<List<ModelRSM>> call, Response<List<ModelRSM>> response) {
                    List<ModelRSM> rsm = response.body();
                    if (rsm != null) {
                        runInsertAsync(rsm, dbSyncHelper::InsertRSMInfo, view::onGetDZSM);
                    } else {
                        postResult(view::onGetDZSM, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelRSM>> call, Throwable t) {
                    postResult(view::onGetDZSM, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callAM(String empcode, String emprole) {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelASM>> call = service.getASM(empcode, emprole);
            call.enqueue(new Callback<List<ModelASM>>() {
                @Override
                public void onResponse(Call<List<ModelASM>> call, Response<List<ModelASM>> response) {
                    List<ModelASM> rsm = response.body();
                    if (rsm != null) {
                        runInsertAsync(rsm, dbSyncHelper::InsertASMInfo, view::onGetAM);
                    } else {
                        postResult(view::onGetAM, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelASM>> call, Throwable t) {
                    postResult(view::onGetAM, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callMIO(String empcode, String emprole) {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelMIO>> call = service.getMIO(empcode, emprole);
            call.enqueue(new Callback<List<ModelMIO>>() {
                @Override
                public void onResponse(Call<List<ModelMIO>> call, Response<List<ModelMIO>> response) {
                    List<ModelMIO> mio = response.body();
                    if (mio != null) {
                        runInsertAsync(mio, dbSyncHelper::InsertMIOInfo, view::onGetMIO);
                    } else {
                        postResult(view::onGetMIO, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelMIO>> call, Throwable t) {
                    postResult(view::onGetMIO, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callMBE(String empcode, String emprole) {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelMBE>> call = service.getMBE(empcode, emprole);
            call.enqueue(new Callback<List<ModelMBE>>() {
                @Override
                public void onResponse(Call<List<ModelMBE>> call, Response<List<ModelMBE>> response) {
                    List<ModelMBE> mbe = response.body();
                    if (mbe != null) {
                        runInsertAsync(mbe, dbSyncHelper::InsertMBEInfo, view::onGetMBE);
                    } else {
                        postResult(view::onGetMBE, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelMBE>> call, Throwable t) {
                    postResult(view::onGetMBE, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callGroup(String empcode, String emprole) {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelGroup>> call = service.getGroupByUser(empcode, emprole);
            call.enqueue(new Callback<List<ModelGroup>>() {
                @Override
                public void onResponse(Call<List<ModelGroup>> call, Response<List<ModelGroup>> response) {
                    List<ModelGroup> group = response.body();
                    if (group != null) {
                        runInsertAsync(group, dbSyncHelper::InsertGroup, view::onGetGroup);
                    } else {
                        postResult(view::onGetGroup, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelGroup>> call, Throwable t) {
                    postResult(view::onGetGroup, false);
                }
            });

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage().toString());
            Log.e("DBEX", ex.toString());
        }
    }

    @Override
    public void callZone(String empcode, String emprole) {
        //Region
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelRegion>> call = service.getRegionByUser(empcode, emprole);
            call.enqueue(new Callback<List<ModelRegion>>() {
                @Override
                public void onResponse(Call<List<ModelRegion>> call, Response<List<ModelRegion>> response) {
                    List<ModelRegion> region = response.body();
                    if (region != null) {
                        runInsertAsync(region, dbSyncHelper::InsertRegion, view::onGetZone);
                    } else {
                        postResult(view::onGetZone, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelRegion>> call, Throwable t) {
                    postResult(view::onGetZone, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callArea(String empcode, String emprole) {
        //Area
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelArea>> call = service.getAreaByUser(empcode, emprole);
            call.enqueue(new Callback<List<ModelArea>>() {
                @Override
                public void onResponse(Call<List<ModelArea>> call, Response<List<ModelArea>> response) {
                    List<ModelArea> area = response.body();
                    if (area != null) {
                        runInsertAsync(area, dbSyncHelper::InsertArea, view::onGetArea);
                    } else {
                        postResult(view::onGetArea, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelArea>> call, Throwable t) {
                    postResult(view::onGetArea, false);
                }
            });

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
    }

    @Override
    public void callTeritory(String empcode, String emprole) {
    //Teritorry
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelTeritorry>> call = service.getTeritoryByUser(empcode, emprole);
            call.enqueue(new Callback<List<ModelTeritorry>>() {
                @Override
                public void onResponse(Call<List<ModelTeritorry>> call, Response<List<ModelTeritorry>> response) {
                    List<ModelTeritorry> teritories = response.body();
                    if (teritories != null) {
                        runInsertAsync(teritories, dbSyncHelper::InsertTeritorry, view::onGetTeritory);
                    } else {
                        postResult(view::onGetTeritory, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelTeritorry>> call, Throwable t) {
                    postResult(view::onGetTeritory, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callSTeritory(String empcode, String emprole) {
        //Subteritorry
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelSubTeritorry>> call = service.getSubTeritoryByUser(empcode, emprole);
            call.enqueue(new Callback<List<ModelSubTeritorry>>() {
                @Override
                public void onResponse(Call<List<ModelSubTeritorry>> call, Response<List<ModelSubTeritorry>> response) {
                    List<ModelSubTeritorry> subTeritorries = response.body();
                    if (subTeritorries != null) {
                        runInsertAsync(subTeritorries, dbSyncHelper::InsertSubTeritorry, view::onGetSTeritory);
                    } else {
                        postResult(view::onGetSTeritory, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelSubTeritorry>> call, Throwable t) {
                    postResult(view::onGetSTeritory, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callMarket(String empcode, String emprole) {
        //Market
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelMarket>> call = service.getMarketByUser(empcode, emprole);
            call.enqueue(new Callback<List<ModelMarket>>() {
                @Override
                public void onResponse(Call<List<ModelMarket>> call, Response<List<ModelMarket>> response) {
                    List<ModelMarket> markets = response.body();
                    if (markets != null) {
                        runInsertAsync(markets, dbSyncHelper::InsertMarket, view::onGetMarket);
                    } else {
                        postResult(view::onGetMarket, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelMarket>> call, Throwable t) {
                    postResult(view::onGetMarket, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callDivision() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelDivision>> call = service.getDivision();
            call.enqueue(new Callback<List<ModelDivision>>() {
                @Override
                public void onResponse(Call<List<ModelDivision>> call, Response<List<ModelDivision>> response) {
                    List<ModelDivision> markets = response.body();
                    if (markets != null) {
                        runInsertAsync(markets, dbSyncHelper::InsertDivision, view::onGetDivision);
                    } else {
                        postResult(view::onGetDivision, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelDivision>> call, Throwable t) {
                    postResult(view::onGetDivision, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callDistrict() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelDistrict>> call = service.getDistrict();
            call.enqueue(new Callback<List<ModelDistrict>>() {
                @Override
                public void onResponse(Call<List<ModelDistrict>> call, Response<List<ModelDistrict>> response) {
                    List<ModelDistrict> markets = response.body();
                    if (markets != null) {
                        runInsertAsync(markets, dbSyncHelper::InsertDistrict, view::onGetDistrict);
                    } else {
                        postResult(view::onGetDistrict, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelDistrict>> call, Throwable t) {
                    postResult(view::onGetDistrict, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callThana() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelThana>> call = service.getThana();
            call.enqueue(new Callback<List<ModelThana>>() {
                @Override
                public void onResponse(Call<List<ModelThana>> call, Response<List<ModelThana>> response) {
                    List<ModelThana> markets = response.body();
                    if (markets != null) {
                        runInsertAsync(markets, dbSyncHelper::InsertThana, view::onGetThana);
                    } else {
                        postResult(view::onGetThana, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelThana>> call, Throwable t) {
                    postResult(view::onGetThana, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callCustomer(int empid) {
        try {
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<List<ModelCustomer>> call = service.GetCustomerByUser(empid);
            call.enqueue(new Callback<List<ModelCustomer>>() {
                @Override
                public void onResponse(Call<List<ModelCustomer>> call, Response<List<ModelCustomer>> response) {
                    List<ModelCustomer> customers = response.body();
                    if (customers != null &&customers.size()>0) {
                        runInsertAsync(customers, dbSyncHelper::InsertCustomerList, view::onGetCustomer);
                    } else {
                        postResult(view::onGetCustomer, true);
                    }
                   // Constants.LastSyncTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                }

                @Override
                public void onFailure(Call<List<ModelCustomer>> call, Throwable t) {
                    postResult(view::onGetCustomer, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callCustomerStation(int empid) {
        try {
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<List<ModelStationType>> call = service.GetStation();
            call.enqueue(new Callback<List<ModelStationType>>() {
                @Override
                public void onResponse(Call<List<ModelStationType>> call, Response<List<ModelStationType>> response) {
                    List<ModelStationType> station = response.body();
                    if (station != null) {
                        runInsertAsync(station, dbSyncHelper::InsertCustomerStation, view::onGetCustomerStation);
                    } else {
                        postResult(view::onGetCustomerStation, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelStationType>> call, Throwable t) {
                    postResult(view::onGetCustomerStation, false);
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void callDoctor(int empid) {
        try {
            //All Doctor Api call
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorListViewModel>> call = service.GetDoctorList(empid);
            call.enqueue(new Callback<List<DoctorListViewModel>>() {
                @Override
                public void onResponse(Call<List<DoctorListViewModel>> call, Response<List<DoctorListViewModel>> response) {
                    List<DoctorListViewModel> doctors = response.body();
                    if (doctors != null) {
                        runInsertAsync(doctors, dbSyncHelper::InsertDoctorList, view::onGetDoctor);
                    } else {
                        postResult(view::onGetDoctor, true);
                    }
                }

                @Override
                public void onFailure(Call<List<DoctorListViewModel>> call, Throwable t) {
                    postResult(view::onGetDoctor, false);
                }
            });
        } catch (Exception ex) {
        }
    }

    @Override
    public void callDegree(int doctypeId) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorDegree>> call = service.GetDoctorDegree();
            call.enqueue(new Callback<List<DoctorDegree>>() {
                @Override
                public void onResponse(Call<List<DoctorDegree>> call, Response<List<DoctorDegree>> response) {
                    List<DoctorDegree> docdeg = response.body();
                    if (docdeg != null) {
                        runInsertAsync(docdeg, dbSyncHelper::InsertDoctorDegree, view::onGetDegree);
                    } else {
                        postResult(view::onGetDegree, true);
                    }
                }

                @Override
                public void onFailure(Call<List<DoctorDegree>> call, Throwable t) {
                    postResult(view::onGetDegree, false);
                }
            });
        } catch (Exception ex) {
        }
    }
    @Override
    public void callSpeciality() {
        //Speciality
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorSpeciality>> call = service.GetDoctorSpeciality();
            call.enqueue(new Callback<List<DoctorSpeciality>>() {
                @Override
                public void onResponse(Call<List<DoctorSpeciality>> call, Response<List<DoctorSpeciality>> response) {
                    List<DoctorSpeciality> docSpc = response.body();
                    if (docSpc != null) {
                        runInsertAsync(docSpc, dbSyncHelper::InsertDoctorSpecility, view::onGetSpeciality);
                    } else {
                        postResult(view::onGetSpeciality, true);
                    }
                }

                @Override
                public void onFailure(Call<List<DoctorSpeciality>> call, Throwable t) {
                    postResult(view::onGetSpeciality, false);
                }
            });

        } catch (Exception ex) {
        }
    }
    @Override
    public void callDoctorDesignation() {
        //Designation
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorDesignation>> call = service.GetDoctorDesignation();
            call.enqueue(new Callback<List<DoctorDesignation>>() {
                @Override
                public void onResponse(Call<List<DoctorDesignation>> call, Response<List<DoctorDesignation>> response) {
                    List<DoctorDesignation> docDesig = response.body();
                    if (docDesig != null) {
                        runInsertAsync(docDesig, dbSyncHelper::InsertDoctorDesig, view::onGetDoctorDesignation);
                    } else {
                        postResult(view::onGetDoctorDesignation, true);
                    }
                }

                @Override
                public void onFailure(Call<List<DoctorDesignation>> call, Throwable t) {
                    postResult(view::onGetDoctorDesignation, false);
                }
            });

        } catch (Exception ex) {
        }
    }



    @Override
    public void callContactType() {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorContact>> call = service.GetDoctorContactType();
            call.enqueue(new Callback<List<DoctorContact>>() {
                @Override
                public void onResponse(Call<List<DoctorContact>> call, Response<List<DoctorContact>> response) {
                    List<DoctorContact> contatyps = response.body();
                    if (contatyps != null) {
                        runInsertAsync(contatyps, dbSyncHelper::InsertContType, view::onGetDoctorContactType);
                    } else {
                        postResult(view::onGetDoctorContactType, true);
                    }
                }

                @Override
                public void onFailure(Call<List<DoctorContact>> call, Throwable t) {
                    postResult(view::onGetDoctorContactType, false);
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void callDoctorCategory(int empid) {
        //Doc Category
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorCategory>> call = service.GetDoctorCategory();
            call.enqueue(new Callback<List<DoctorCategory>>() {
                @Override
                public void onResponse(Call<List<DoctorCategory>> call, Response<List<DoctorCategory>> response) {
                    List<DoctorCategory> docChm = response.body();
                    if (docChm != null) {
                        runInsertAsync(docChm, dbSyncHelper::InsertDoctorCategory, view::onGetDoctorCategory);
                    } else {
                        postResult(view::onGetDoctorCategory, true);
                    }
                }

                @Override
                public void onFailure(Call<List<DoctorCategory>> call, Throwable t) {
                    postResult(view::onGetDoctorCategory, false);
                }
            });

        } catch (Exception ex) {
        }
    }



    @Override
    public void callBrand(int empid) {
        //brand
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<ModelBrand>> call = service.GetBrand();
            call.enqueue(new Callback<List<ModelBrand>>() {
                @Override
                public void onResponse(Call<List<ModelBrand>> call, Response<List<ModelBrand>> response) {
                    List<ModelBrand> docChm = response.body();
                    if (docChm != null) {
                        runInsertAsync(docChm, dbSyncHelper::InsertBrand, view::onGetBrand);
                    } else {
                        postResult(view::onGetBrand, true);
                    }
                }
                @Override
                public void onFailure(Call<List<ModelBrand>> call, Throwable t) {
                    postResult(view::onGetBrand, false);
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void callPromo(int empid) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<ModelPromo>> call = service.GetPromo();
            call.enqueue(new Callback<List<ModelPromo>>() {
                @Override
                public void onResponse(Call<List<ModelPromo>> call, Response<List<ModelPromo>> response) {
                    List<ModelPromo> docChm = response.body();
                    if (docChm != null) {
                        runInsertAsync(docChm, dbSyncHelper::InsertPromo, view::onGetPromo);
                    } else {
                        postResult(view::onGetPromo, true);
                    }
                }
                @Override
                public void onFailure(Call<List<ModelPromo>> call, Throwable t) {
                    postResult(view::onGetPromo, false);
                }
            });

        } catch (Exception ex) {
        }
    }


    @Override
    public void callPriority() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelPriority>> call = service.getPriority();
            call.enqueue(new Callback<List<ModelPriority>>() {
                @Override
                public void onResponse(Call<List<ModelPriority>> call, Response<List<ModelPriority>> response) {
                    List<ModelPriority> prio = response.body();
                    if (prio != null) {
                        boolean isTrue = dbSyncHelper.InsertPatientPriority(prio);
                        if (isTrue) {
                            view.onGetPriority("Done",true);
                        } else {
                            view.onGetPriority("Error",false);
                        }
                    }else {
                        view.onGetPriority("Done",true);
                    }
                }
                @Override
                public void onFailure(Call<List<ModelPriority>> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                    } else {
                    }
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void callDay() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelDay>> call = service.getDay();
            call.enqueue(new Callback<List<ModelDay>>() {
                @Override
                public void onResponse(Call<List<ModelDay>> call, Response<List<ModelDay>> response) {
                    List<ModelDay> day = response.body();
                    if (day != null) {
                        runInsertAsync(day, dbSyncHelper::InsertDay, view::onGetDay);
                    } else {
                        postResult(view::onGetDay, true);
                    }
                }
                @Override
                public void onFailure(Call<List<ModelDay>> call, Throwable t) {
                    postResult(view::onGetDay, false);
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void callDeliveryDay() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelDeliveryDay>> call = service.getDeliveryDay();
            call.enqueue(new Callback<List<ModelDeliveryDay>>() {
                @Override
                public void onResponse(Call<List<ModelDeliveryDay>> call, Response<List<ModelDeliveryDay>> response) {
                    List<ModelDeliveryDay> dday = response.body();
                    if (dday != null) {
                        runInsertAsync(dday, dbSyncHelper::InsertDeliveryDay, view::onGetDeliveryDay);
                    } else {
                        postResult(view::onGetDeliveryDay, true);
                    }
                }
                @Override
                public void onFailure(Call<List<ModelDeliveryDay>> call, Throwable t) {
                    postResult(view::onGetDeliveryDay, false);
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void callGender() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelGender>> call = service.getGender();
            call.enqueue(new Callback<List<ModelGender>>() {
                @Override
                public void onResponse(Call<List<ModelGender>> call, Response<List<ModelGender>> response) {
                    List<ModelGender> gender = response.body();
                    if (gender != null) {
                        runInsertAsync(gender, dbSyncHelper::InsertGender, view::onGetGender);
                    } else {
                        postResult(view::onGetGender, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelGender>> call, Throwable t) {
                    postResult(view::onGetGender, false);
                }
            });

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage().toString());
            Log.e("DBEX", ex.toString());
        }
    }

    @Override
    public void callPracticeShift() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelPracticeShift>> call = service.getPracticeShift();
            call.enqueue(new Callback<List<ModelPracticeShift>>() {
                @Override
                public void onResponse(Call<List<ModelPracticeShift>> call, Response<List<ModelPracticeShift>> response) {
                    List<ModelPracticeShift> shift = response.body();
                    if (shift != null) {
                        runInsertAsync(shift, dbSyncHelper::InsertPracticeShift, view::onGetPracticeShift);
                    } else {
                        postResult(view::onGetPracticeShift, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelPracticeShift>> call, Throwable t) {
                    postResult(view::onGetPracticeShift, false);
                }
            });

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage().toString());
            Log.e("DBEX", ex.toString());
        }
    }

    @Override
    public void cllUserRole(int empid) {

    }

    @Override
    public void cllUserByRole(int empid) {

    }

    @Override
    public void callProduct(int empid) {
        //product
        try {
            ApiProduct service = RetrofitClientInstance.getRetrofitInstance().create(ApiProduct.class);
            Call<List<ModelProduct>> call = service.GetProductList(empid);
            call.enqueue(new Callback<List<ModelProduct>>() {
                @Override
                public void onResponse(Call<List<ModelProduct>> call, Response<List<ModelProduct>> response) {
                    List<ModelProduct> productList = response.body();
                    if (productList != null) {
                        runInsertAsync(productList, dbSyncHelper::InsertProducts, view::onGetProduct);
                    } else {
                        postResult(view::onGetProduct, true);
                    }

                }

                @Override
                public void onFailure(Call<List<ModelProduct>> call, Throwable t) {
                    postResult(view::onGetProduct, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void cllProductSample(int empid) {

    }

    @Override
    public void callProductGift(int empid) {
        try {
            ApiProduct service = RetrofitClientInstance.getRetrofitInstance().create(ApiProduct.class);
            Call<List<Gift>> call = service.GetGiftProduct(empid, "Gift");

            call.enqueue(new Callback<List<Gift>>() {
                @Override
                public void onResponse(Call<List<Gift>> call, Response<List<Gift>> response) {
                    List<Gift> giftList = response.body();
                    if (giftList != null) {
                        runInsertAsync(giftList, dbSyncHelper::InsertProductGift, view::onGetProductGift);
                    } else {
                        postResult(view::onGetProductGift, true);
                    }
                }

                @Override
                public void onFailure(Call<List<Gift>> call, Throwable t) {
                    postResult(view::onGetProductGift, false);
                }
            });

        } catch (Exception ex) {
        }
    }



    @Override
    public void callQuotedPrice(int empid) {
//Quoted Price
        try {
            ApiOrderProcess service = RetrofitOrderClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<List<QuotedPrice>> call = service.GetQuotedPrice();
            call.enqueue(new Callback<List<QuotedPrice>>() {
                @Override
                public void onResponse(Call<List<QuotedPrice>> call, Response<List<QuotedPrice>> response) {
                    List<QuotedPrice> aList = response.body();
                    if (aList != null) {
                        runInsertAsync(aList, dbSyncHelper::InsertQuotedPriceInfo_SQLite, view::onGetQuotedPrice);
                    } else {
                        postResult(view::onGetQuotedPrice, true);
                    }
                }

                @Override
                public void onFailure(Call<List<QuotedPrice>> call, Throwable t) {
                    postResult(view::onGetQuotedPrice, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void cllDoctorType(int empId, int year) {

    }

    @Override
    public void callCustomerType(int empId, int year) {

    }



    @Override
    public void cllExpenseType(int empId, int year) {

    }

    @Override
    public void cllLeaveType(int empId, int year) {

    }

    @Override
    public void cllPrescriptionType(int empId, int year) {

    }

    @Override
    public void cllNonEffectivereason(int empId, int year) {

    }

    @Override
    public void callTransportList() {
        //TransportList
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelTransport>> call = service.GetTransportList();
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<List<ModelTransport>>() {
                @Override
                public void onResponse(Call<List<ModelTransport>> call, Response<List<ModelTransport>> response) {
                    List<ModelTransport> transportList = response.body();
                    if (transportList != null) {
                        runInsertAsync(transportList, dbSyncHelper::InsertTransport, view::onGetTransportList);
                    } else {
                        postResult(view::onGetTransportList, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelTransport>> call, Throwable t) {
                    postResult(view::onGetTransportList, false);
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void cllTourPurpose(int empId, int year) {
        //Tour Purpose
        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<List<TourPurposeViewModel>> call = service.GetPurpose(empId);
            call.enqueue(new Callback<List<TourPurposeViewModel>>() {
                @Override
                public void onResponse(Call<List<TourPurposeViewModel>> call, Response<List<TourPurposeViewModel>> response) {
                    List<TourPurposeViewModel> tourPurposeList = response.body();
                    if (tourPurposeList != null) {
                        runInsertAsync(tourPurposeList, dbSyncHelper::InsertTourPlanPurpose, view::onGetTourPurpose);
                    } else {
                        postResult(view::onGetTourPurpose, true);
                    }

                }

                @Override
                public void onFailure(Call<List<TourPurposeViewModel>> call, Throwable t) {
                    postResult(view::onGetTourPurpose, false);
                }
            });

        } catch (Exception ex) {
        }
    }

    @Override
    public void cllVisitType(int empId, int year) {
        //Visit Type
        try {
            ApiDoctor  service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<TourTypeViewModel>> call = service.GetVisitType();
            call.enqueue(new Callback<List<TourTypeViewModel>>() {
                @Override
                public void onResponse(Call<List<TourTypeViewModel>> call, Response<List<TourTypeViewModel>> response) {
                    List<TourTypeViewModel> visitType = response.body();
                    if (visitType != null) {
                        runInsertAsync(visitType, dbSyncHelper::InsertVisitType, view::onGetVisitType);
                    } else {
                        postResult(view::onGetVisitType, true);
                    }
                }

                @Override
                public void onFailure(Call<List<TourTypeViewModel>> call, Throwable t) {
                    postResult(view::onGetVisitType, false);
                }
            });

        } catch (Exception ex) {
        }
    }
    @Override
    public void callProviderType() {
        try {
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<List<ModelProviderType>> call = service.GetProviderType();
            call.enqueue(new Callback<List<ModelProviderType>>() {
                @Override
                public void onResponse(Call<List<ModelProviderType>> call, Response<List<ModelProviderType>> response) {
                    List<ModelProviderType> providerType = response.body();
                    if (providerType != null) {
                        runInsertAsync(providerType, dbSyncHelper::InsertProviderType, view::onGetProviderType);
                    } else {
                        postResult(view::onGetProviderType, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelProviderType>> call, Throwable t) {
                    postResult(view::onGetProviderType, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

    @Override
    public void callWorkType() {
        try {
            ApiMasterSync service = RetrofitClientInstance.getRetrofitInstance().create(ApiMasterSync.class);
            Call<List<ModelWorkType>> call = service.getWorkType();
            call.enqueue(new Callback<List<ModelWorkType>>() {
                @Override
                public void onResponse(Call<List<ModelWorkType>> call, Response<List<ModelWorkType>> response) {
                    List<ModelWorkType> workType = response.body();
                    if (workType != null) {
                        runInsertAsync(workType, dbSyncHelper::InsertWorkType, view::onGetWorkType);
                    } else {
                        postResult(view::onGetWorkType, true);
                    }
                }

                @Override
                public void onFailure(Call<List<ModelWorkType>> call, Throwable t) {
                    postResult(view::onGetWorkType, false);
                }
            });

        } catch (Exception ex) {
            Log.e("DBEX", ex.toString());
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }


}
