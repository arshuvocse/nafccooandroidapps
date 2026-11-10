package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model;

import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMMorning;

public class ModelWSMBESave {

    String empId;
    String MasterId;
    ModelTwwpABMMorning WCABMMorning;
    ModelTwwpABMMorning WCABMEvening;
    String Type;
    String date;
    public ModelWSMBESave() {
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getMasterId() {
        return MasterId;
    }

    public void setMasterId(String masterId) {
        MasterId = masterId;
    }

    public ModelTwwpABMMorning getWCABMMorning() {
        return WCABMMorning;
    }

    public void setWCABMMorning(ModelTwwpABMMorning WCABMMorning) {
        this.WCABMMorning = WCABMMorning;
    }

    public ModelTwwpABMMorning getWCABMEvening() {
        return WCABMEvening;
    }

    public void setWCABMEvening(ModelTwwpABMMorning WCABMEvening) {
        this.WCABMEvening = WCABMEvening;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
