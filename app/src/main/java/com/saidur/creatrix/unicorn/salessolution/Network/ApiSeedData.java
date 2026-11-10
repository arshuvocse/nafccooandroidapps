package com.saidur.creatrix.unicorn.salessolution.Network;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelCustomerCallReason;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ApproveExpRQ;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseClaimMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalData;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalRQ;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveTypeInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveVM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelAppVersion;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.DepoStockModel;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelLeaveSvBody;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseLeaveList;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseTwwpCopyModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiSeedData {
    @Headers({"Accept: application/json"})
    @POST("/api/AppPrimary")
    Call<ModelAppVersion> GetAppVersionInfo();
    //Stock
   /* @GET("/api/SeedData/GetDCStoreStockList")
    Call<List<DepoStockModel>> GetDepoStock(@Query("CustomerMasterId") String CustomerMasterId);*/

    @GET("/api/Calculation/Get_ExpenseType")
    Call<List<ExpenseTypeMaster>> Get_ExpenseType();

    @GET("/api/Calculation/Get_ExpenseTypeDetails")
    Call<List<ExpenseTypeDetails>> Get_ExpenseTypeDetails(@Query("id") int id);

    @POST("/api/Calculation/SaveExpenseClaim")
    Call<ResponseResultInfo> SaveExpenseClaim(@Body ExpenseClaimMaster aInfo);



    @GET("/api/Leave/GetLeaveType")
    Call<List<LeaveTypeInfo>> GetLeaveType(@Query("empId") int empId, @Query("year") int year);

    @POST("/api/Leave/SaveLeave")
    Call<ResponseResultInfo> SaveLeave(@Body ModelLeaveSvBody aInfo);

    @GET("/api/Leave/GetLeaveRecords")
    Call<ResponseLeaveList> GetLeaveRecords(@QueryMap HashMap<String,String> map);
  //  Call<List<LeaveVM>> GetLeaveRecordsz(@Query("empId") int empId, @Query("Month") String Month, @Query("year") int year);


    //Approval Leave
    @GET("/api/Leave/GetLeave_AppLog")
    Call<List<LeaveApprovalData>> GetLeaveListApproval(@Query("pram") String pram, @QueryMap Map<String, String> mapparam);

    @POST("/api/Leave/SaveLeave_AppLog")
    Call<ResponseResultInfo> SaveLeaveApproval(@Body LeaveApprovalRQ aInfo);

    //Copy Twwp
    @GET("/api/TourPlan/GetWorkSchedule")
    Call<List<ResponseTwwpCopyModel>> GetTwwpForWS(@QueryMap Map<String, String> mapparam);

    @GET("/api/SeedData/GetChemistCallReason")
    Call<List<ModelCustomerCallReason>> GetCCReason();

}
