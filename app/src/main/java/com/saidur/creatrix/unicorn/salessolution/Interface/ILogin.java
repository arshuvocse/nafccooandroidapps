package com.saidur.creatrix.unicorn.salessolution.Interface;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelUser;

import java.util.HashMap;

public interface ILogin {
    interface View {
        void OnSuccess(int userId, String userName, String empMasterco, String loginName, String password, int empId, String userCo, int IsImeiMatch, String versionName, String role, int roleTypeid, String roleType, int isAccepted, int isForward, String DesigName);
        void OnError(String message);
        void OnLoginSuccess(ModelSession user);
        //void OnError(String message);
        // void onVersionGet(String VersionName);
    }
    interface Presenter {
        void doLogin(String userId, String password, String imeiNo, String token, String Device, String AppVersion, String OS, String OS_Version);
        void hitLogin(HashMap<String,String> hashMap);
        // void GetActiveVersion();
    }
}
