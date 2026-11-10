package com.saidur.creatrix.unicorn.salessolution.Network;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelResponseInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignCalModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignGetReq;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignMasterNew;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignPostReq;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CordinateUpdate;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelCompanyUnit;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelOrderTrackigMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMasterModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.QuotedPrice;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiOrderProcess {
    @Headers({"Accept: application/json"})
    @GET("/api/QuotedPrice/GetQuotedPrice")
    Call<List<QuotedPrice>> GetQuotedPrice();

    @GET("/api/Campaign/GetCurrentCampaign")
    Call<List<CampaignMasterNew>> GetBonusCampaign(@Query("customerId") int  customerId);


    @POST("/api/Campaign/GetCustomerWiseCampaign")
    Call<List<CampaignModel>> GetCampaign(@Body List<CampaignGetReq> campaignGetReq);

    @POST("/api/Campaign/GetOrderProductWiseCampaign")
    Call<List<CampaignCalModel>> GetCampaignProductWise(@Body CampaignPostReq campaignPosttReq);

    @POST("/api/Location/UpdateCustomerLocation")
    Call<ModelResponseInfo> UpdateLocation(@Body CordinateUpdate location);

    @POST("/api/OrderProcess/MakeOrder")
   // Call<ResultInfo> SubmitOrder2(@Body OrderMasterNew aOrder);
    Call<ResponseResultInfo> SubmitOrder2(@Body OrderMasterModel aOrder);

    @GET("/api/OrderProcess/MakeOrder")
    Call<ResponseResultInfo> SubmitOrder(@Body OrderMaster aOrder);

    @POST("/api/OrderManagement")
    Call<ResponseResultInfo> MakeOrder(@Body OrderMaster aInfo);

    @GET("/api/OrderManagement")
    Call<List<ModelCompanyUnit>> GetCompanyUnit(@Query("empId") int empId);

    @GET("/api/CusDemo/GetMioOrderList")
    Call<List<ModelOrderTrackigMaster>>GetMio_OrderList(@QueryMap Map<String,String> mapparam);

    @GET("/api/CusDemo/ApproveOrderMio")
    Call<ResponseResultInfo> ApproveOrder_fromMio(@Query("orderId") int orderId);

    @GET("/api/CusDemo/RejectOrderMio")
    Call<ResponseResultInfo> RejectOrder_fromMio(@Query("orderId") int orderId);

    // For Customer Demo
    @GET("/api/CusDemo/GetOrderInfo")
    Call<OrderMaster> GetOrder_CustomerST(@Query("orderId") int orderId);
  /*  @GET("/api/Order/GetOrder_Applog")
    Call<List<OrderApprovalData>> GetOrderApproval(@Query("pram") String pram, @QueryMap Map<String,String> mapparam);
    @POST("/api/Order/SaveOrder_Applog")
    Call<ResponseResultInfo> SaveOrderApproval(@Body OrderApprovalSaveBody aOrder);*/


 /*   //Report
    @GET("/api/Reports/GetCWSR")
    Call<CWSaleReportModel> GetCustWiseOrderReport(@QueryMap Map<String,String> mapparam);
    @GET("/api/Reports/GetPWSR")
    Call<PWSaleReportModel> GetProductWiseOrderReport(@QueryMap Map<String,String> mapparam);*/

}
