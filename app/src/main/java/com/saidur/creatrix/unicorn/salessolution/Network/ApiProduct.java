package com.saidur.creatrix.unicorn.salessolution.Network;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model.Gift;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiProduct {
    //Product
    @GET("/api/Product")
    Call<List<ModelProduct>> GetProductList(@Query("empId") int empId);

    //Gift,Product,Sample
    @GET("/api/Doctor/GetProductForDcrByType")
    Call<List<Gift>> GetGiftProduct(@Query("empId") int empId, @Query("type") String type);
}
