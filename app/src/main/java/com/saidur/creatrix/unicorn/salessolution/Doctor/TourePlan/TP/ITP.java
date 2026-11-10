package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TP;



import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.TourPlanReq;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;

import java.util.List;

public interface ITP {
        interface  Presenter{
            void getTourPlanDailyByEmpId(int month, int year, int empId,String date,boolean isFinalSubmit);
            void getTourPlanDataByEmpId(int month, int year, int empId);
            void saveTourPlanPerdayByEmpId(TourPlanReq tppmList);

        }
        interface View {
            void OnTourPlanDataGet(List<TourPlanViewModel> aList);
            void OnTourPlanDailyDataGet(List<TourPlanViewModel> aList,boolean isa);
            void OnFailour(String msg);

            void OnSuccessTPPDay(String msg);
        }
}
