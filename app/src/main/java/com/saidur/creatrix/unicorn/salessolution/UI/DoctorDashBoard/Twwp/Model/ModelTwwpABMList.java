package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelTwwpABMList {
    String Empid;
    String DayName;
    String DayVal;
    String MonthVal;
    String YearVal;
    ModelWSABM WSMorning_V;
    ModelWSABM WSEvening_V;

    public ModelTwwpABMList() {
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

    public String getDayVal() {
        return DayVal;
    }

    public void setDayVal(String dayVal) {
        DayVal = dayVal;
    }

    public String getMonthVal() {
        return MonthVal;
    }

    public void setMonthVal(String monthVal) {
        MonthVal = monthVal;
    }

    public String getYearVal() {
        return YearVal;
    }

    public void setYearVal(String yearVal) {
        YearVal = yearVal;
    }

    public ModelWSABM getWSMorning_V() {
        return WSMorning_V;
    }

    public void setWSMorning_V(ModelWSABM WSMorning_V) {
        this.WSMorning_V = WSMorning_V;
    }

    public ModelWSABM getWSEvening_V() {
        return WSEvening_V;
    }

    public void setWSEvening_V(ModelWSABM WSEvening_V) {
        this.WSEvening_V = WSEvening_V;
    }
}
