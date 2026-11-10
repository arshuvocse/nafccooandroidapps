package com.saidur.creatrix.unicorn.salessolution.Network;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelUser;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiLogin {
    @Headers({"Accept: application/json"})
    @GET("/api/User")
    Call<ModelUser> LoginRequest(@Query("UserName") String username, @Query("password") String password, @Query("Imei") String Imei, @Query("DeviceToken") String DeviceToken, @Query("Device") String Device, @Query("AppVersion") String AppVersion, @Query("OS") String OS, @Query("OS_Version") String OS_Version);

    @GET("/api/User")
    Call<ModelUser> getLoginRequest(@QueryMap HashMap<String,String> hashMap);

    @POST("/api/Reports/ChangePassword")
    Call<ResponseResultInfo> ChangePass(@QueryMap Map<String,String> mapparam);
}
