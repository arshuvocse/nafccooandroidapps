package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelTwwpApprovalList {
    String Empid;
    String DayName;
    int TWWWPMasterId;
    String Date;
    String TerritoryName;
    String TerritoryCode;
    String ApprovalStatus;
    String EmpMasterCode;
    String EmpName;

    ModelTMorning TwwpMorning_V;
    ModelTMorning TwwpEvening_V;

    public ModelTwwpApprovalList() {
    }

    public String getEmpid() {
        return Empid;
    }

    public void setEmpid(String empid) {
        Empid = empid;
    }

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String dayName) {
        DayName = dayName;
    }

    public ModelTMorning getTwwpMorning_V() {
        return TwwpMorning_V;
    }

    public void setTwwpMorning_V(ModelTMorning twwpMorning_V) {
        TwwpMorning_V = twwpMorning_V;
    }

    public ModelTMorning getTwwpEvening_V() {
        return TwwpEvening_V;
    }

    public void setTwwpEvening_V(ModelTMorning twwpEvening_V) {
        TwwpEvening_V = twwpEvening_V;
    }

    public int getTWWWPMasterId() {
        return TWWWPMasterId;
    }

    public void setTWWWPMasterId(int TWWWPMasterId) {
        this.TWWWPMasterId = TWWWPMasterId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTerritoryName() {
        return TerritoryName;
    }

    public void setTerritoryName(String territoryName) {
        TerritoryName = territoryName;
    }

    public String getTerritoryCode() {
        return TerritoryCode;
    }

    public void setTerritoryCode(String territoryCode) {
        TerritoryCode = territoryCode;
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
}
