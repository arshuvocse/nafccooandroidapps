package com.saidur.creatrix.unicorn.salessolution.Interface;

import android.widget.RelativeLayout;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelAttendance;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseButton;


public interface IAttendance {
    interface  Presenter{
        void doSavePuncINInfo(ModelAttendance attendance, RelativeLayout masterLayout);
        void button(int empid,String session);

    }
    interface View{
        void onSuccess(String message,boolean status,int type);
        void onError(String message,int type);
        void onButtonView(ResponseButton buttonRP);
    }
}
