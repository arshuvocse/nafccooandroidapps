package com.saidur.creatrix.unicorn.salessolution.Interface;



import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.TourPlanReq;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;

import java.util.List;

public interface ITourplan {
    interface  Presenter{
        void getTourPlanDailyByEmpId(int month, int year, int empId,String date);
        void getTourPlanDataByEmpId(List<MonthDate> aMondateList, boolean is_Entry, int month, int year, int empId);
        void saveTourPlanPerdayByEmpId(TourPlanReq tppmList);

    }
    interface View{
        //void OnTourPlanDataGet(List<TPDetailsModel> aList);
        void OnTourPlanDataGet(List<TourPlanViewModel> aList);
        void OnTourPlanDailyDataGet(List<MonthDate> aList);
        void OnArreangList(List<MonthDate> aMondateList,boolean is_Entry, List<TourPlanViewModel> aTpLIst);
        void OnFailour(String msg);
        void OnSuccessTPPDay(String msg);

    }
}
