package com.saidur.creatrix.unicorn.salessolution.Interface;


import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;

import java.util.List;

public interface ICustomer {
    interface Presenter{
        void GetCustomerByUser(int empId);
        void GetCustomerReport(int empId);

    }

    interface View{
        void OnSuccess(List<ModelCustomer> aList);
       // void OnSuccessCustomerReport(List<CustomerARModel> aList);
        void  OnError(int type);
        void  OnErrorReport(String msg);
    }
}
