package com.saidur.creatrix.unicorn.salessolution.Network;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.AppSetting;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {


    //private static final String BASE_URL = AppSetting.Base_Url_Test;
    private static final String BASE_URL = AppSetting.Base_Url_updatedW;
    private static Retrofit retrofit = null;



    public static Retrofit getRetrofitInstance() {
        OkHttpClient clients =new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10,TimeUnit.MINUTES)
                .readTimeout(10,TimeUnit.MINUTES)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clients)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}