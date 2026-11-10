package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelTwwpMaster {
    int TWWWPMasterId;
    int EmpInfoId;
    boolean IsFinalSubmit;
    int ApprovalStatus;

    public ModelTwwpMaster() {
    }

    public int getTWWWPMasterId() {
        return TWWWPMasterId;
    }

    public void setTWWWPMasterId(int TWWWPMasterId) {
        this.TWWWPMasterId = TWWWPMasterId;
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

    public int getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        ApprovalStatus = approvalStatus;
    }
}
