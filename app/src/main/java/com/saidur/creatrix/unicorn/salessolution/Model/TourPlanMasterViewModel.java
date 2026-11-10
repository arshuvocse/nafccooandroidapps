package com.saidur.creatrix.unicorn.salessolution.Model;


import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.TPHQModel;

import java.util.List;

public class TourPlanMasterViewModel {
    int TPMaster;
    int MonthValue;
    int YearValue;
    int EmpInfoId;
    int ApprovedBy;
    String ApprovedDate;
    String FinalSubmitRemarks;
    boolean IsFinalSubmit;
    String ApprovalStatus;
    String ApprovalRemarks;
    List<TPHQModel> aTourTypeDAOList;

    public int getTPMaster() {
        return TPMaster;
    }

    public void setTPMaster(int TPMaster) {
        this.TPMaster = TPMaster;
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

    public String getApprovalRemarks() {
        return ApprovalRemarks;
    }

    public void setApprovalRemarks(String approvalRemarks) {
        ApprovalRemarks = approvalRemarks;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
    }

    public int getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(int approvedBy) {
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

    public List<TPHQModel> getaTourTypeDAOList() {
        return aTourTypeDAOList;
    }

    public void setaTourTypeDAOList(List<TPHQModel> aTourTypeDAOList) {
        this.aTourTypeDAOList = aTourTypeDAOList;
    }
}
