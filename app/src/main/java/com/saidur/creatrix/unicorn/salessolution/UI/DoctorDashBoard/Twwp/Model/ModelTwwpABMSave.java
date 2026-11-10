package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelTwwpABMSave {

    String empId;
    String MasterId;
    ModelTwwpABMMorning WCABMMorning;
    ModelTwwpABMMorning WCABMEvening;


    public ModelTwwpABMSave() {
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


}
