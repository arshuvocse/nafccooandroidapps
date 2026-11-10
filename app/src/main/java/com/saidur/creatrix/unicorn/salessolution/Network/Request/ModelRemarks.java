package com.saidur.creatrix.unicorn.salessolution.Network.Request;

public class ModelRemarks {
    int empid;
    int teri_id;
    String date;
    String Reason;

    public ModelRemarks(int empid, int teri_id, String date, String reason) {
        this.empid = empid;
        this.teri_id = teri_id;
        this.date = date;
        Reason = reason;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public int getTeri_id() {
        return teri_id;
    }

    public void setTeri_id(int teri_id) {
        this.teri_id = teri_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
}
