package com.saidur.creatrix.unicorn.salessolution.Model;

public class DoctorTourPlanMaster {
    int DoctorId;
    int EmpInfoId;
    String  TourDate;
    int TPMaster;
    int MonthValue;
    int YearValue;



    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
    }

    public String getTourDate() {
        return TourDate;
    }

    public void setTourDate(String tourDate) {
        TourDate = tourDate;
    }

    public int getTPMaster() {
        return TPMaster;
    }

    public void setTPMaster(int TPMaster) {
        this.TPMaster = TPMaster;
    }

    public int getMonthValue() {
        return MonthValue;
    }

    public void setMonthValue(int monthValue) {
        MonthValue = monthValue;
    }

    public int getYearValue() {
        return YearValue;
    }

    public void setYearValue(int yearValue) {
        YearValue = yearValue;
    }
}
