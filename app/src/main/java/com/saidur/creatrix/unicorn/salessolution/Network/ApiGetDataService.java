package com.saidur.creatrix.unicorn.salessolution.Network;


import com.saidur.creatrix.unicorn.salessolution.Model.ModelUserTracking;
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

public interface ApiGetDataService {
    @Headers({"Accept: application/json"})
    @POST("/api/User")
    Call<ResponseResultInfo> SaveTracking(@Body ModelUserTracking aInfo);

   /* @POST("/api/Order/SaveDoctorrequirement")
    Call<ResultInfo> MakeSampleOrder(@Body OrderMaster aInfo);
    //For Notice Done
    @GET("/api/Notice")
    Call<List<Notice>> GetUpdatedNotice(@Query("empId") int empId);

    @GET("/api/Notice/UpdateNoticeBoardReadByEmpId")
    Call<Void> postSeenNotice(@Query("id") int id,@Query("EmpId") int EmpId,@Query("AppSeenDate") String AppSeenDate);

    //For Training
    @GET("/api/Training")
    Call<List<Training>> GetTraining(@Query("empId") int empId);
    @GET("/api/Training/UpdateTrainingReadByEmpId")
    Call<Void> postSeenTraining(@Query("id") int id,@Query("EmpId") int EmpId);

    // For Customer Demo
    @GET("/api/CusDemo/GetOrderInfo")
    Call<OrderMaster> GetOrder_CustomerST(@Query("orderId") int orderId);

    @GET("/api/CusDemo")
    Call<List<OrderViewModel>> GetCustomer_OrderList(@Query("customerCode") String customerCode);

    @GET("/api/CusDemo/GetMioOrderList")
    Call<List<OrderTrackigMaster>>GetMio_OrderList(@QueryMap Map<String,String> mapparam);

    @GET("/api/CusDemo/ApproveOrderMio")
    Call<ResultInfo> ApproveOrder_fromMio(@Query("orderId") int orderId);

    @GET("/api/CusDemo/RejectOrderMio")
    Call<ResultInfo> RejectOrder_fromMio(@Query("orderId") int orderId);


    //

    @GET("/api/Order/GetMioDashboardQuickSummery")
    Call<Dashboard_SummeryVM> GetMioDashboardSummeryData(@Query("empId") int empId, @Query("currenDate") String currenDate);

    @GET("/api/CusDemo/GetAttendanceData")
    Call<List<Report_AttendanceViewModel>> GetAttendanceData(@Query("empId") int empId,@Query("startDate") String startDate,@Query("endDate") String endDate);

    @POST("/api/AppPrimary")
    Call<AppVersionModel> GetAppVersionInfo();


    @GET("/api/Notification")
    Call<Integer> GetNotificationCount(@Query("id") int id);

    @GET("/api/Notification/GetNotification")
    Call<List<NotificationViewModel>> GetNotification(@Query("empId") int empId);

    @GET("/api/Notification/UpdateNotificationRead")
    Call<ResultInfo> UpdateNotificationIsRead(@Query("id") int id);




*/
}
