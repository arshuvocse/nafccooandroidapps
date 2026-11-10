package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelTwwpList {
    String Empid;
    String DayName;
    ModelTMorning TwwpMorning_V;
    ModelTMorning TwwpEvening_V;

    public ModelTwwpList() {
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
}
