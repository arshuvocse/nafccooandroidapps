package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelTwwpSave {

    String empId;
    ModelTwwpMorning TwwpMorning;
    ModelTwwpMorning TwwpEvening;

    public ModelTwwpSave() {
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public ModelTwwpMorning getTwwpMorning() {
        return TwwpMorning;
    }

    public void setTwwpMorning(ModelTwwpMorning twwpMorning) {
        TwwpMorning = twwpMorning;
    }

    public ModelTwwpMorning getTwwpEvening() {
        return TwwpEvening;
    }

    public void setTwwpEvening(ModelTwwpMorning twwpEvening) {
        TwwpEvening = twwpEvening;
    }
}
