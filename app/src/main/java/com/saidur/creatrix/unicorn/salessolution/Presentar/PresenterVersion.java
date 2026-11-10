package com.saidur.creatrix.unicorn.salessolution.Presentar;



import com.saidur.creatrix.unicorn.salessolution.Interface.IVersionUpdate;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelAppVersion;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiSeedData;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PresenterVersion implements IVersionUpdate.Presenter {
    IVersionUpdate.View versionview;

    public PresenterVersion(IVersionUpdate.View versionview) {

        this.versionview=versionview;
    }

    @Override
    public void GetActiveVersion() {
        try{
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<ModelAppVersion> call = service.GetAppVersionInfo();
            call.enqueue(new Callback<ModelAppVersion>() {
                @Override
                public void onResponse(Call<ModelAppVersion> call, Response<ModelAppVersion> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        versionview.onVersionGet(response.body());
                    } else {
                        versionview.onVersionCheckFailed();
                    }
                }

                @Override
                public void onFailure(Call<ModelAppVersion> call, Throwable t) {
                    versionview.onVersionCheckFailed();
                }
            });
        }catch (Exception ex){
            versionview.onVersionCheckFailed();
        }
    }
}
