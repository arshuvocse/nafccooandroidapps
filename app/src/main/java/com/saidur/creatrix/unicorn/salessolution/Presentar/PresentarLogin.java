package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;

import androidx.annotation.NonNull;

import com.saidur.creatrix.unicorn.salessolution.Interface.ILogin;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelUser;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiLogin;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresentarLogin implements ILogin.Presenter {
    ILogin.View view;
    Context context;

    public PresentarLogin(Context context, ILogin.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void doLogin(String userId, String password, String imeiNo, String token, String Device, String AppVersion, String OS, String OS_Version) {
        ApiLogin service = RetrofitClientInstance.getRetrofitInstance().create(ApiLogin.class);
        Call<ModelUser> call = service.LoginRequest(userId,password,imeiNo,token,Device,AppVersion,OS,OS_Version);
        call.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                ModelUser user =response.body();
                if(user == null ){
                    view.OnError("User Getting Null.Please try again or contact with Admin Department!");
                }
                else if(user.getUserId() == 0){
                    if(!user.getTwoDeviceMsg().equals("")){
                        view.OnError("Maximum Two Device Allowed!!");
                    }
                    else
                    {
                        view.OnError("You Login Name and Password do not match");
                    }

                }else{
                    int usid = user.getUserId();
                    int EmpInfoId = user.getEmpInfoId();
                    String userName = user.getUserName();
                    String empMasterCo=user.getEmpMasterCode();
                    String loginName = user.getLoginName();
                    String passwrd = user.getPassword();
                    String userCo = user.getUserCo();
                    String versionName = user.getVersionName();
                    String designation=user.getDesigName();
                    int IsImeiMatch = user.getIsImeiMatched();
                    String emprole = user.getEmpRole();

                    //Convert bool to int
                    int isAccepted = user.getisApprove() ? 1 : 0;
                    int isForward = user.getisForward() ? 1 : 0;
                    //Toast.makeText(context, emprole, Toast.LENGTH_SHORT).show();
                    System.out.println(emprole);
                    view.OnSuccess(usid,userName,empMasterCo,loginName,passwrd,EmpInfoId,userCo,IsImeiMatch,versionName,emprole,user.getRoleTypeId(), user.getRoleType(),isAccepted,isForward,designation);
                }

            }
            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    view.OnError("Slow Connection Detected. Please try again");
                }else{
                    view.OnError("Something went wrong... Please try again");
                }
            }
        });
    }

    @Override
    public void hitLogin(HashMap<String, String> hashMap) {
        ApiLogin service = RetrofitClientInstance.getRetrofitInstance().create(ApiLogin.class);
        Call<ModelUser> call = service.getLoginRequest(hashMap);
        call.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                ModelUser user =response.body();
                if(user == null ){
                    view.OnError("User Getting Null.Please try again or contact with Admin Department!");
                }
                else if(user.getUserId() == 0){
                    if(!user.getTwoDeviceMsg().equals("")){
                        view.OnError("Maximum Two Device Allowed!!");
                    }
                    else
                    {
                        view.OnError("You Login Name and Password do not match");
                    }

                }else{
                    ModelSession session=new ModelSession();
                    session.setEmpInfoId(response.body().getEmpInfoId());
                    session.setUserName(response.body().getUserName());
                    session.setEmpMasterCode(response.body().getEmpMasterCode());
                    session.setUserType(response.body().getUserType());
                    session.setLoginName(response.body().getLoginName());
                    session.setUserCo(response.body().getUserCo());
                    session.setVersionName(response.body().getVersionName());
                    session.setEmpRole(response.body().getEmpRole());
                    session.setRoleTypeId(response.body().getRoleTypeId());
                    session.setRoleType(response.body().getRoleType());
                    session.setDesigName(response.body().getDesigName());
                    session.setDevice(response.body().getDevice());


                    view.OnLoginSuccess(session);
                }

            }
            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    view.OnError("Slow Connection Detected. Please try again");
                }else{
                    view.OnError("Something went wrong... Please try again");
                }
            }
        });
    }
}
