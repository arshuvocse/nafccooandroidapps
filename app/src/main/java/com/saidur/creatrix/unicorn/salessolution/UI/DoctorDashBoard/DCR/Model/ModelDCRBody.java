package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model;

import java.util.List;

public class ModelDCRBody {
    String empId;
    String planned;
    String date;
    String drId;
    String callTime;
    String callType;
    String drcallType;
    String callWith;
    int DCPDetailId;
    List<ModelCallPriorityProduct> callpriorityProducts;

    public ModelDCRBody() {
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPlanned() {
        return planned;
    }

    public void setPlanned(String planned) {
        this.planned = planned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }


    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getDrcallType() {
        return drcallType;
    }

    public void setDrcallType(String drcallType) {
        this.drcallType = drcallType;
    }

    public String getCallWith() {
        return callWith;
    }

    public void setCallWith(String callWith) {
        this.callWith = callWith;
    }

    public List<ModelCallPriorityProduct> getCallpriorityProducts() {
        return callpriorityProducts;
    }

    public void setCallpriorityProducts(List<ModelCallPriorityProduct> callpriorityProducts) {
        this.callpriorityProducts = callpriorityProducts;
    }

    public int getDCPDetailId() {
        return DCPDetailId;
    }

    public void setDCPDetailId(int DCPDetailId) {
        this.DCPDetailId = DCPDetailId;
    }
}
