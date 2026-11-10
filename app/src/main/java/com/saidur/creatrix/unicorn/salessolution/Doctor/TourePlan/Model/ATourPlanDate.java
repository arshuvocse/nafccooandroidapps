package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.ATourPlanDtlsDAO;

import java.util.List;

public class ATourPlanDate {
    @SerializedName("DayValue")
    @Expose
    private String dayValue;
    @SerializedName("DayName")
    @Expose
    private String dayName;
    @SerializedName("aTourPlanDtlsDAO")
    @Expose
    private List<ATourPlanDtlsDAO> aTourPlanDtlsDAO = null;

    public String getDayValue() {
        return dayValue;
    }

    public void setDayValue(String dayValue) {
        this.dayValue = dayValue;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<ATourPlanDtlsDAO> getaTourPlanDtlsDAO() {
        return aTourPlanDtlsDAO;
    }

    public void setaTourPlanDtlsDAO(List<ATourPlanDtlsDAO> aTourPlanDtlsDAO) {
        this.aTourPlanDtlsDAO = aTourPlanDtlsDAO;
    }
}
