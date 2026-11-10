package com.saidur.creatrix.unicorn.salessolution.Network;


import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelPracticeShift;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelNSM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDistrict;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDivision;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelGender;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPriority;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelThana;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDeliveryDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTransport;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWorkType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface ApiMasterSync {
    @Headers({"Accept: application/json"})
    @POST("/api/StructureSync/LastSynchronizationInfo")
    Call<ResponseResultInfo> LastSync(@Body int EmpInfoId);
    @GET("/api/StructureSync/GetFieldForceMarket")
    Call<List<ModelMarket>> getMarketByUser(@Query("empcode") String empcode,
                                            @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceSubTerritory")
    Call<List<ModelSubTeritorry>> getSubTeritoryByUser(@Query("empcode") String empcode,
                                                       @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceTerritory")
    Call<List<ModelTeritorry>> getTeritoryByUser(@Query("empcode") String empcode,
                                                 @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceArea")
    Call<List<ModelArea>> getAreaByUser(@Query("empcode") String empcode,
                                        @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceRegion")
    Call<List<ModelRegion>> getRegionByUser(@Query("empcode") String empcode,
                                            @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceGroup")
    Call<List<ModelGroup>> getGroupByUser(@Query("empcode") String empcode,
                                          @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceMBE")
    Call<List<ModelMBE>> getMBE(@Query("empcode") String empcode,
                                @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceMio")
    Call<List<ModelMIO>> getMIO(@Query("empcode") String empcode,
                                @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceAsm")
    Call<List<ModelASM>> getASM(@Query("empcode") String empcode,
                                @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceRsm")
    Call<List<ModelRSM>> getRSM(@Query("empcode") String empcode,
                                @Query("emprole") String emprole);
    @GET("/api/StructureSync/GetFieldForceNsm")
    Call<List<ModelNSM>> getNSM(@Query("empcode") String empcode,
                                @Query("emprole") String emprole);

    @GET("/api/SeedData/GetDivisionAll")
    Call<List<ModelDivision>> getDivision();
    @GET("/api/SeedData/GetDistrict")
    Call<List<ModelDistrict>> getDistrict();
    @GET("/api/SeedData/GetThana")
    Call<List<ModelThana>> getThana();

    @GET("/api/SeedData/GetPracticeShift")
    Call<List<ModelPracticeShift>> getPracticeShift();
    @GET("/api/SeedData/GetGender")
    Call<List<ModelGender>> getGender();
    @GET("/api/SeedData/GetPatientPriority")
    Call<List<ModelPriority>> getPriority();

    @GET("/api/SeedData/DayName")
    Call<List<ModelDay>> getDay();
    @GET("/api/SeedData/DeliveryDayName")
    Call<List<ModelDeliveryDay>> getDeliveryDay();
    @GET("/api/SeedData/WorkType")
    Call<List<ModelWorkType>> getWorkType();
    @GET("/api/Calculation/GetTransportList")
    Call<List<ModelTransport>> GetTransportList();
}
