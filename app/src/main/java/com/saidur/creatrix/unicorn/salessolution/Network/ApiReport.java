package com.saidur.creatrix.unicorn.salessolution.Network;

import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRxDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRxSummary;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDAR;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDSR;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelReportAttendance;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelTarget;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.Rp_CampainViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Rp_StockViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Rp_TargetAcchivment;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelRemarks;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelTwwpApprovalSaveBody;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseTwwpCopyModel;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCPApproveList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCPApprovePost;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCRApproveList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMSave;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpApprovalList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpApprovalMaster;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSApprovalMaster;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSApprovalSvBody;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSMBESave;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ResponseWSData;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model.CWSaleReportModel;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model.PWSaleReportModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiReport {
    @Headers({"Accept: application/json"})

    //Attendance
    @GET("/api/Reports/GetAttandenceReport")
    Call<List<ModelReportAttendance>> GetAttendanceReport(@QueryMap Map<String, String> mapparam);
    Call<List<ModelReportAttendance>> GetAttendanceReport(@Query("empId") int empId, @Query("Month") String month, @Query("Year") String year);

    @GET("/api/Reports/GetProductStock")
    Call<List<Rp_StockViewModel>> GetStockData(@Query("empId") int empId, @Query("type") String type);

    @GET("/api/Reports/GetCampaingData")
    Call<List<Rp_CampainViewModel>> GetCampaingData(@Query("empId") int empId);

    @GET("/api/Reports/GetTargetvsAcchive")
    Call<List<Rp_TargetAcchivment>> GetTarvsAcchi(@Query("empId") int empId, @Query("month") int month, @Query("year") int year);

    @GET("/api/Reports/GetCWSR")
    Call<CWSaleReportModel> GetCustWiseOrderReport(@QueryMap Map<String, String> mapparam);

    @GET("/api/Reports/GetPWSR")
    Call<PWSaleReportModel> GetProductWiseOrderReport(@QueryMap Map<String, String> mapparam);

    //Product
    @GET("/api/Product")
    Call<List<ModelProduct>> GetProductList(@Query("empId") int empId);
   /* @GET("/api/Reports/GetEmployeeWiseTotalCount")
    Call<EmpTotalModel> GetEmpTotal(@Query("empId") String empId, @Query("Date") String date);*/

    //Rx
    @GET("/api/Reports/GetPrescriptionReport_One")
    Call<List<ModelRxDetails>> GetRxDetails(@QueryMap Map<String, String> mapparam);

    @GET("/api/Reports/GetPrescriptionReport_Two")
    Call<List<ModelRxSummary>> GetRxSummary(@QueryMap Map<String, String> mapparam);

    @GET("/api/Doctor/GetDCP_Applog")
    Call<List<ModelDCPApproveList>> GetDCPApprovalList(@Query("pram") String pram, @QueryMap Map<String, String> mapparam);

    @POST("/api/Doctor/SaveDCP_Applog")
    Call<ResponseResultInfo> SaveDCPApprove(@Body ModelDCPApprovePost post);

    @GET("/api/Doctor/GetDCR_Applog")
    Call<List<ModelDCRApproveList>> GetDCRApprovalList(@Query("pram") String pram, @QueryMap Map<String, String> mapparam);

    @POST("/api/Doctor/SaveDCR_Applog")
    Call<ResponseResultInfo> SaveDCRApprove(@Body ModelDCPApprovePost post);

    //Twwp applog
    @GET("/api/TourPlan/GetTwwp_Applog")
    Call<List<ModelTwwpApprovalMaster>> GetTwwpApprovalList(@Query("pram") String pram, @QueryMap Map<String, String> mapparam);

    @POST("/api/TourPlan/SaveTwwp_Applog")
    Call<ResponseResultInfo> SaveTwwpApprove(@Body ModelTwwpApprovalSaveBody post);

    //WS
    @GET("/api/TourPlan/GetWorkScheduleAfterSave")
    Call<List<ResponseWSData>> GetWSlList(@QueryMap Map<String, String> mapparam);
    @POST("/api/TourPlan/SaveWorkSchedule")
    Call<ResponseResultInfo> SaveWS(@Body List<ResponseTwwpCopyModel> saveDataList);

    @GET("/api/TourPlan/GetWS_Applog")
    Call<List<ModelWSApprovalMaster>> GetWSApprovalList(@Query("pram") String pram, @QueryMap Map<String, String> mapparam);
    @POST("/api/TourPlan/SaveWS_Applog")
    Call<ResponseResultInfo> SaveWSApprove(@Body ModelWSApprovalSvBody post);

    @POST("/api/TourPlan/UpdateWorkSchedule")
    Call<ResponseResultInfo> SaveWSUpdate(@Body ModelWSMBESave twwpSave);


    @GET("/api/Reports/GetMonthlyTarvsAchiv")
    Call<List<ModelTarget>> GetTargetReport(@QueryMap Map<String, String> mapparam);


    @GET("/api/Doctor/GetDar")
    Call<List<ModelDAR>> GetDAR(@QueryMap Map<String, String> mapparam);
    @GET("/api/Doctor/GetDailySalesReport")
    Call<List<ModelDSR>> GetDSR(@QueryMap Map<String, String> mapparam);

    @POST("/api/Doctor/SaveDailySalesReport")
    Call<ResponseResultInfo> SaveDAR(@Body List<ModelRemarks> darSave);

    @GET("/api/Customer/GetCustomerDueList")
    Call<List<ModelCustomer>> GetCustomerDueList(@QueryMap Map<String, String> mapparam);

}
