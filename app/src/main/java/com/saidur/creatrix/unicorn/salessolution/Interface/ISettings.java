package com.saidur.creatrix.unicorn.salessolution.Interface;

public interface ISettings {
    interface Presenter{

        void doCustomerSync(int empId);
        void doProductSync(int empId);
        void doDoctorSync(int empId);


    }
    interface View{
        void onSuccess(String Message);
        void onError(String Message);
        void onCustomerSync(String Message);
        void onProductSync(String Message);
        void onDoctorSync(String Message,boolean a);
    }
}

