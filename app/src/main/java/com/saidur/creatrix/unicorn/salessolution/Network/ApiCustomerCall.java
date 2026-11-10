package com.saidur.creatrix.unicorn.salessolution.Network;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.CustomerSvModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelCCReport;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelProviderType;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelCCSvBody;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiCustomerCall {
    @Headers({"Accept: application/json"})
    @GET("/api/Customer")
    Call<List<ModelCustomer>> GetCustomerByUser(@Query("empId") int empId);

    @POST("/api/Customer/SaveCustomer")
    Call<ResponseResultInfo> SaveCustomer(@Body CustomerSvModel aInfo);

    @GET("/api/SeedData/GetStation")
    Call<List<ModelStationType>> GetStation();

    //Provider
    @GET("/api/Doctor/GetProviderType")
    Call<List<ModelProviderType>> GetProviderType();


    @POST("/api/Customer/UpdateCustomerProvider")
    //Call<ResultInfo> UpdateCustomerProviderType(@Field("CustomerId") int CustomerId,@Field("EmpId") int EmpId,@Field("ProviderTypeId") int ProviderTypeId);
    Call<ResponseResultInfo> UpdateCustomerProviderType(@QueryMap HashMap<String,String> data);

    @POST("/api/Doctor/UpdateDoctorProvider")
    Call<ResponseResultInfo> UpdateDoctorProviderType(@QueryMap HashMap<String,String> data);

    @POST("/api/Customer/SaveChemistCall")
    Call<ResponseResultInfo> saveCC(@Body ModelCCSvBody data);

    @GET("/api/Customer/GetChemistCall")
    Call<List<ModelCCReport>> getCCReport(@QueryMap HashMap<String,String> data);


}