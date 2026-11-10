package com.saidur.creatrix.unicorn.salessolution.Interface;

public interface ISyncMaster {
    interface  Presenter{
        void callNSM(String empcode, String emproleempid);
        void callDZSM(String empcode, String emprole);
        void callAM(String empcode, String emprole);
        void callMIO(String empcode, String emprole);
        void callMBE(String empcode, String emprole);
        //Area
        void callGroup(String empcode, String emprole);
        void callZone(String empcode, String emprole);
        void callArea(String empcode, String emprole);
        void callTeritory(String empcode, String emprole);
        void callSTeritory(String empcode, String emprole);
        void callMarket(String empcode, String emprole);

        void callDivision();
        void callDistrict();
        void callThana();

        //getCustomerInfo
        void callCustomer(int empid);
        void callCustomerStation(int empid);
        //DoctorDashBoard
        void callDoctor(int empid);
        void callDegree(int doctypeId);
        void callSpeciality();
        void callDoctorDesignation();
        void callGender();
        void callPracticeShift();
        void callContactType();
        void callDoctorCategory(int empid);
        void callBrand(int empid);
        void callPromo(int empid);
        void callPriority();
        void callDay();
        void callDeliveryDay();



        //getRoleWiseUser
        void cllUserRole(int empid);
        void cllUserByRole(int empid);
        //Product
        void callProduct(int empid);
        void cllProductSample(int empid);
        void callProductGift(int empid);
        void callQuotedPrice(int empid);
       // getTypeInfo
        void cllDoctorType(int empId, int year);
        void callCustomerType(int empId, int year);
        void cllExpenseType(int empId, int year);
        void cllLeaveType(int empId, int year);
        void cllPrescriptionType(int empId, int year);
        void cllNonEffectivereason(int empId, int year);
        void callTransportList();
        void cllTourPurpose(int empId, int year);
        void cllVisitType(int empId, int year);
        void callProviderType();
        void callWorkType();

    }
    interface View{
        void onGetNSM(String a,boolean t);
        void onGetDZSM(String a,boolean t);
        void onGetAM(String a,boolean t);
        void onGetMIO(String a,boolean t);
        void onGetMBE(String a,boolean t);

        void onGetGroup(String a,boolean t);
        void onGetZone(String a,boolean t);
        void onGetArea(String a,boolean t);
        void onGetTeritory(String a,boolean t);
        void onGetSTeritory(String a,boolean t);
        void onGetMarket(String a,boolean t);

        void onGetCustomer(String a,boolean t);
        void onGetCustomerStation(String a,boolean t);

        //Doctor
        void onGetDoctor(String a,boolean t);
        void onGetDegree(String a,boolean t);
        void onGetSpeciality(String a,boolean t);
        void onGetDoctorDesignation(String a,boolean t);
        void onGetGender(String a,boolean t);
        void onGetPracticeShift(String a,boolean t);
        void onGetBrand(String a,boolean t);
        void onGetPromo(String a,boolean t);
        void onGetPriority(String a,boolean t);
        void onGetDoctorCategory(String a,boolean t);


        void onGetUserRole(String a,boolean t);
        void onGetUserByRole(String a,boolean t);
        //Product
        void onGetProduct(String a,boolean t);
        void onGetProductSample(String a,boolean t);
        void onGetProductGift(String a,boolean t);
        void onGetQuotedPrice(String a,boolean t);
        // getTypeInfo
        void onGetDoctorType(String a,boolean t);
        void onGetCustomerType(String a,boolean t);
        void onGetDoctorContactType(String a,boolean t);
        void onGetExpenseType(String a,boolean t);
        void onGetLeaveType(String a,boolean t);
        void onGetPrescriptionType(String a,boolean t);
        void onGetNonEffectivereason(String a,boolean t);
        void onGetTransportList(String a,boolean t);
        void onGetTourPurpose(String a,boolean t);
        void onGetVisitType(String a,boolean t);

        void onGetDivision(String a,boolean t);
        void onGetDistrict(String a,boolean t);
        void onGetThana(String a,boolean t);
        void onGetProviderType(String a,boolean t);
        void onGetDay(String a,boolean t);
        void onGetDeliveryDay(String a,boolean t);
        void onGetWorkType(String a,boolean t);

    }
}
