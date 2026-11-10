package com.saidur.creatrix.unicorn.salessolution.Model;

public class VisitPlanMaster {

    int DocTPMaster;
    int MonthValue;
    int YearValue;
    int EmpInfoId;
    boolean IsFinalSubmit;
    String ApprovalStatus;
    String ApprovedBy;
    String ApprovedDate;
    String FinalSubmitRemarks;
    String ApprovalRemarks;

    public int getDocTPMaster() {
        return DocTPMaster;
    }

    public void setDocTPMaster(int docTPMaster) {
        DocTPMaster = docTPMaster;
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

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public void setApprovedDate(String approvedDate) {
        ApprovedDate = approvedDate;
    }

    public String getFinalSubmitRemarks() {
        return FinalSubmitRemarks;
    }

    public void setFinalSubmitRemarks(String finalSubmitRemarks) {
        FinalSubmitRemarks = finalSubmitRemarks;
    }

    public String getApprovalRemarks() {
        return ApprovalRemarks;
    }

    public void setApprovalRemarks(String approvalRemarks) {
        ApprovalRemarks = approvalRemarks;
    }
}
