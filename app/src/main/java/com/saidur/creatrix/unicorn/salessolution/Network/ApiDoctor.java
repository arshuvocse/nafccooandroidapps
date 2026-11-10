package com.saidur.creatrix.unicorn.salessolution.Network;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorCategory;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorContact;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDegree;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDesignation;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSM;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSpeciality;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRx;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPromo;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelResponseInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanMasterViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourTypeViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.VisitPlanMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.VisitplanModel;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponsePotential;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItemSubmit;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDcpMaster;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model.ModelDCRBody;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model.ModelPlanned;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMSave;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpMaster;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpSave;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiDoctor {

    @GET("/api/Doctor/GetDoctorVisitPlanMaster")
        // Call<List<TourPlanMasterViewModel>> GetDoctorVisitPlanMaster(@Query("month") int month, @Query("year") int year, @Query("empId") int empId);
    Call<List<VisitPlanMaster>> GetDoctorVisitPlanMaster(@Query("month") int month, @Query("year") int year, @Query("empId") int empId);

    @GET("/api/Doctor/GetVisitType")
    Call<List<TourTypeViewModel>> GetVisitType();
    @POST("/api/Doctor/SaveDoctorVisitPlan")
    Call<ResultInfo> SaveDoctorVisitPlan(@Body List<VisitplanModel> aInfo);
    //Product
    @GET("/api/Product")
    Call<List<ModelProduct>> GetProductList(@Query("empId") int empId);
    @GET("/api/Doctor/GetDoctorTourPlanDetail")
    Call<List<TourPlanViewModel>> GetDoctorTourPlanData(@Query("month") int month, @Query("year") int year, @Query("empId") int empId);
    @GET("/api/TourPlan/GetTourPlanMasterData")
    Call<List<TourPlanMasterViewModel>> GetTourPlanMaster(@Query("month") int month, @Query("year") int year, @Query("empId") int empId);

    @POST("/api/Doctor/UpdateDoctorTourPlan")
    Call<ResultInfo> UpdateDoctorTourPlan(@Query("empId") int empId, @Query("month") int month, @Query("year") int year, @Query("remarks") String remarks);

    //Rx submit
    @POST("api/Doctor/SavePrescription")
    Call<ResponseResultInfo> submitRx(@Body ModelRx modelRx);
    @POST("/api/TourPlan/DelTourPlan")
    Call<ResultInfo> DeleteTourPlanData(@Query("id") int id);

    @GET("/api/Doctor/GetDoctorBrand")
    Call<List<ModelBrand>> GetBrand();

    @GET("/api/Doctor/GetBrandWisePromo")
    Call<List<ModelPromo>> GetPromo();

    @POST("/api/Doctor/SaveDoctorInfo")
    Call<ResponseResultInfo> SaveDoctor(@Body DoctorSM aDoc);

    @GET("/api/SeedData/GetPotentialStatus")
    Call<ResponsePotential> GetPotential(@Query("NoofPatient") String NoofPatient,@Query("NoofRX") String NoofRX);

    @GET("/api/Doctor/GetDoctorList")
    Call<List<DoctorListViewModel>> GetDoctorList(@Query("empId") int empId);
    @GET("/api/Doctor/GetDoctorEdtMode")
    Call <DoctorSM> GetDoctorEdtMode(@Query("doctorId") int doctorId);


    //GetPlannedDoctorList
    @GET("/api/Doctor/GetPlannedDoctorList")
    Call<List<DoctorListViewModel>> GetPlannedDoctorList(@QueryMap HashMap<String,String> data);


    @POST("/api/Doctor/SaveDCR")
    Call<ResponseResultInfo> SaveDCR(@Body ModelDCRBody dcrbody);


    @GET("/api/Doctor/GeDoctorCategory")
    Call<List<DoctorCategory>> GetDoctorCategory();

    @GET("/api/Doctor/GetDoctorContactType")
    Call<List<DoctorContact>> GetDoctorContactType();
    @GET("/api/Doctor/GetDoctorDesignation")
    Call<List<DoctorDesignation>> GetDoctorDesignation();

    @GET("/api/Doctor/GetDoctorSpeciality")
    Call<List<DoctorSpeciality>> GetDoctorSpeciality();

    @GET("/api/Doctor/GetDoctorDegree")
    Call<List<DoctorDegree>> GetDoctorDegree();


    //Save DCP
    @POST("/api/Doctor/SaveDCPInfo")
    Call<ResponseResultInfo> SaveDCP(@Body ModelDCPItemSubmit dcp);

    //Save Twwp
    @POST("/api/TourPlan/SaveTWWP")
    Call<ResponseResultInfo> SaveTwwp(@Body ModelTwwpSave twwpSave);

    //Save Twwp ABM/RBM
    @POST("/api/TourPlan/SaveWorkScheduleABM")
    Call<ResponseResultInfo> SaveTwwpABM(@Body ModelTwwpABMSave twwpSave);

    @POST("/api/Doctor/DeleteDCP")
    Call<ResponseResultInfo> DeleteDCPDOC(@Query("DCPDetailId") String DCPDetailId);

    //Twwp final submit
    @POST("/api/TourPlan/TwwpFinalSubmit")
    Call<ResponseResultInfo> TwwpFinalSubmit(@Query("empId") String empId);

    //Twwp Mastar
    @GET("/api/TourPlan/GetTwwpMaster")
    Call<ModelTwwpMaster> GetTwwpMaster(@Query("empId") int empId);
    //Get Twwp List
    @GET("/api/TourPlan/GetTwwp")
    Call<List<ModelTwwpList>> GetTwwpList(@Query("empId") int empId);

    //Get WS ABM List
    @GET("/api/TourPlan/GetWorkScheduleABM")
    Call<List<ModelTwwpABMList>> GetWSABMList(@QueryMap HashMap<String,String> data);


    @GET("/api/Doctor/GetDCPList")
    Call<List<ModelDCPList>> GetDCPList(@QueryMap HashMap<String,String> data);

    @POST("/api/Doctor/DCPFinalSubmit")
    Call<ResponseResultInfo> DcpFinalSubmit(@Query("empId") int empId, @Query("month") int month, @Query("year") int year, @Query("remarks") String remarks);

    @GET("/api/Doctor/GetDCPMaster")
    Call<ModelDcpMaster> GetTDcpMasterData(@Query("month") int month, @Query("year") int year, @Query("empId") int empId);

}
