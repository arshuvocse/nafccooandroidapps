package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model;

import androidx.annotation.Keep;

@Keep public class DWSPTotals {
    String TotalTarget;
    String TotalFcb;
    String TotalGeneral;
    String TotalCampaign;
    String TotalExtra1;
    String TotalExtra2;
    boolean IsFinalSubmit;
    String ApprovalStatus;
    int CheckInfo;

    public String getTotalTarget() {
        return TotalTarget;
    }

    public void setTotalTarget(String totalTarget) {
        TotalTarget = totalTarget;
    }

    public String getTotalFcb() {
        return TotalFcb;
    }

    public void setTotalFcb(String totalFcb) {
        TotalFcb = totalFcb;
    }

    public String getTotalGeneral() {
        return TotalGeneral;
    }

    public void setTotalGeneral(String totalGeneral) {
        TotalGeneral = totalGeneral;
    }

    public String getTotalCampaign() {
        return TotalCampaign;
    }

    public void setTotalCampaign(String totalCampaign) {
        TotalCampaign = totalCampaign;
    }

    public String getTotalExtra1() {
        return TotalExtra1;
    }

    public void setTotalExtra1(String totalExtra1) {
        TotalExtra1 = totalExtra1;
    }

    public String getTotalExtra2() {
        return TotalExtra2;
    }

    public void setTotalExtra2(String totalExtra2) {
        TotalExtra2 = totalExtra2;
    }

    public boolean isFinalSubmit() {
        return IsFinalSubmit;
    }

    public void setFinalSubmit(boolean finalSubmit) {
        IsFinalSubmit = finalSubmit;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public int getCheckInfo() {
        return CheckInfo;
    }

    public void setCheckInfo(int checkInfo) {
        CheckInfo = checkInfo;
    }
}
