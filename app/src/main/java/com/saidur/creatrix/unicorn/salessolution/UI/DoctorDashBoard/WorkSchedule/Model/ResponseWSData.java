package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model;

public class ResponseWSData {
    String Empid;
    String masterId;
    String day;
    String date;
    String ApprovalStatus;
    String AmendmentStatus;
    ModelWSData morningList;
    ModelWSData eveningList;

    public ResponseWSData() {
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

    public ModelWSData getMorningList() {
        return morningList;
    }

    public void setMorningList(ModelWSData morningList) {
        this.morningList = morningList;
    }

    public ModelWSData getEveningList() {
        return eveningList;
    }

    public void setEveningList(ModelWSData eveningList) {
        this.eveningList = eveningList;
    }

    public String getAmendmentStatus() {
        return AmendmentStatus;
    }

    public void setAmendmentStatus(String amendmentStatus) {
        AmendmentStatus = amendmentStatus;
    }
}
