package com.saidur.creatrix.unicorn.salessolution.Network;


import com.saidur.creatrix.unicorn.salessolution.Model.ModelAttendance;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelReportAttendance;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseButton;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiAttendance {
    @Headers({"Accept: application/json"})
    @POST("/api/Attendance/SavePunch")
    Call<ResponseResultInfo> SavePunInfo(@Body ModelAttendance aInfo);

    @GET("/api/CusDemo/GetAttendanceData")
    Call<List<ModelReportAttendance>> GetAttendanceData(@Query("empId") int empId, @Query("startDate") String startDate, @Query("endDate") String endDate);


    @POST("/api/Attendance/GetPunchInOutInfo")
    Call<ResponseButton> GetButtonStatus(@Query("EmpInfoId") int EmpInfoId,@Query("WorkSession") String WorkSession);

}
