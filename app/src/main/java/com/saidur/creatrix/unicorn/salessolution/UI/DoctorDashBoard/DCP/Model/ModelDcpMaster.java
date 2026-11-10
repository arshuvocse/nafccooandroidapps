package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model;

public class ModelDcpMaster {
    int DCPMasterId;
    int MonthValue;
    int YearValue;
    int EmpInfoId;
    boolean IsFinalSubmit;
    String ApprovalStatus;
    String FinalSubmitRemarks;
    String CoutPlan;

    public ModelDcpMaster() {
    }

    public int getDCPMasterId() {
        return DCPMasterId;
    }

    public void setDCPMasterId(int DCPMasterId) {
        this.DCPMasterId = DCPMasterId;
    }

    public int getMonthValue() {
        return MonthValue;
    }

    public void setMonthValue(int monthValue) {
        MonthValue = monthValue;
    }

    public int getYearValue() {
        return YearValue;
    }

    public void setYearValue(int yearValue) {
        YearValue = yearValue;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
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


    public String getFinalSubmitRemarks() {
        return FinalSubmitRemarks;
    }

    public void setFinalSubmitRemarks(String finalSubmitRemarks) {
        FinalSubmitRemarks = finalSubmitRemarks;
    }

    public String getCoutPlan() {
        return CoutPlan;
    }

    public void setCoutPlan(String coutPlan) {
        CoutPlan = coutPlan;
    }
}
