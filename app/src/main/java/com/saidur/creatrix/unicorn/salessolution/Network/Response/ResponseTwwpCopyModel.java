package com.saidur.creatrix.unicorn.salessolution.Network.Response;

import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSMorning;

import java.util.List;

public class ResponseTwwpCopyModel {
    String Empid;
    String masterId;
    String day;
    String date;
    ModelWSMorning morningList;
    ModelWSMorning eveningList;

    public ResponseTwwpCopyModel() {
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

    public ModelWSMorning getMorningList() {
        return morningList;
    }

    public void setMorningList(ModelWSMorning morningList) {
        this.morningList = morningList;
    }

    public ModelWSMorning getEveningList() {
        return eveningList;
    }

    public void setEveningList(ModelWSMorning eveningList) {
        this.eveningList = eveningList;
    }
}
