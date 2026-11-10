package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model;


import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;

import java.util.List;

public class TourPlanReq {
    List<TourPlanViewModel> aTourPlanInfo;

    public List<TourPlanViewModel> getaTourPlanInfo() {
        return aTourPlanInfo;
    }

    public void setaTourPlanInfo(List<TourPlanViewModel> aTourPlanInfo) {
        this.aTourPlanInfo = aTourPlanInfo;
    }
}
