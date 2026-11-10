package com.saidur.creatrix.unicorn.salessolution.Network;

import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.UI.CategoryRequest.CatReqModels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCategoryRequest {
    @GET("/api/Customer/GetCategoryRequestSummary")
    Call<List<CatReqModels.Summary>> getSummary(@Query("empId") int empId);

    @GET("/api/Customer/GetCategoryRequestCustomers")
    Call<List<CatReqModels.Customer>> getCustomers(@Query("empId") int empId);

    @POST("/api/Customer/SaveCategoryRequest")
    Call<ResponseResultInfo> save(@Body CatReqModels.SaveBody body);

    @GET("/api/Customer/GetCategoryApprovalList")
    Call<List<CatReqModels.Approval>> getApprovalList(@Query("empId") int empId, @Query("status") String status);

    @POST("/api/Customer/SaveCategoryApproval")
    Call<List<CatReqModels.ApproveResult>> approve(@Body CatReqModels.ApproveBody body);
}
