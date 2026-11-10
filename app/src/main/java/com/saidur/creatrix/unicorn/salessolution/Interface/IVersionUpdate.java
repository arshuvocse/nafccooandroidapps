package com.saidur.creatrix.unicorn.salessolution.Interface;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelAppVersion;

public interface IVersionUpdate {
    interface View {
        void onVersionGet(ModelAppVersion appVersion);
        void onVersionCheckFailed();
    }
    interface Presenter {
        void GetActiveVersion();
    }
}
