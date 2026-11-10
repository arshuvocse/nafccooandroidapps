package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model;

public class ModelWSApprovalMaster {
    String Empid;
    String masterId;
    String day;
    String date;
    String ApprovalStatus;
    String EmpMasterCode;
    String EmpName;
    String TerritoryName;
    String CreateAt;
    String TerritoryCode;
    ModelWSAMorning WSMorning_V;
    ModelWSAMorning WSEvening_V;

    public ModelWSApprovalMaster() {
    }

    public String getEmpid() {
        return Empid;
    }

    public void setEmpid(String empid) {
        Empid = empid;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public String getTerritoryCode() {
        return TerritoryCode;
    }

    public void setTerritoryCode(String territoryCode) {
        TerritoryCode = territoryCode;
    }

    public ModelWSAMorning getWSMorning_V() {
        return WSMorning_V;
    }

    public void setWSMorning_V(ModelWSAMorning WSMorning_V) {
        this.WSMorning_V = WSMorning_V;
    }

    public ModelWSAMorning getWSEvening_V() {
        return WSEvening_V;
    }

    public void setWSEvening_V(ModelWSAMorning WSEvening_V) {
        this.WSEvening_V = WSEvening_V;
    }
}
