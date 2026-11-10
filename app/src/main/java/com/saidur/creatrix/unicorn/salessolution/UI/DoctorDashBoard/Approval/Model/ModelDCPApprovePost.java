package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model;

public class ModelDCPApprovePost {
    String empId;
    String DCPMasterId;
    String approvalStatus;
    String Remarks;
    String RoleType;

    public ModelDCPApprovePost() {
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDCPMasterId() {
        return DCPMasterId;
    }

    public void setDCPMasterId(String DCPMasterId) {
        this.DCPMasterId = DCPMasterId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getRoleType() {
        return RoleType;
    }

    public void setRoleType(String roleType) {
        RoleType = roleType;
    }
}
