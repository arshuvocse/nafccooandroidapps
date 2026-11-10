package com.saidur.creatrix.unicorn.salessolution.Interface;


import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRx;

import java.util.List;

public interface IPrescription {
    interface Presenter{
        void getRxReport(int empId);
        void submitRx(ModelRx modelRx);

    }

    interface View{
        void  OnSuccessRx(boolean b);
        void  OnGetRxList(List<ModelRx> aList);
        void  OnError(int type);
        void  OnErrorReport(String msg);
    }
}
