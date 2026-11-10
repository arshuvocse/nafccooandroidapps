package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

import java.util.List;

public class ModelTwwpApprovalMaster {
    int TWWWPMasterId;
    String ApprovalStatus;
    String EmpMasterCode;
    String createAt;
    String EmpName;
    String TerritoryName;
    List<ModelTwwpApprovalList> Twwp_App;

    public ModelTwwpApprovalMaster() {
    }

    public int getTWWWPMasterId() {
        return TWWWPMasterId;
    }

    public void setTWWWPMasterId(int TWWWPMasterId) {
        this.TWWWPMasterId = TWWWPMasterId;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public String getEmpMasterCode() {
        return EmpMasterCode;
    }

    public void setEmpMasterCode(String empMasterCode) {
        EmpMasterCode = empMasterCode;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getTerritoryName() {
        return TerritoryName;
    }

    public void setTerritoryName(String territoryName) {
        TerritoryName = territoryName;
    }

    public List<ModelTwwpApprovalList> getTwwp_App() {
        return Twwp_App;
    }

    public void setTwwp_App(List<ModelTwwpApprovalList> twwp_App) {
        Twwp_App = twwp_App;
    }
}
