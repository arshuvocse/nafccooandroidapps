package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model;

import java.util.List;

public class ModelDCPList {
    String DCPMasterId;
    String MonthValue;
    String YearValue;
    String DayValue;
    String DCPDate;
    String DayNameStr;
    boolean IsFinalSubmit;
    List<ModelDCPItem> DCPListDtl;



    public ModelDCPList() {
    }

    public String getDCPMasterId() {
        return DCPMasterId;
    }

    public void setDCPMasterId(String DCPMasterId) {
        this.DCPMasterId = DCPMasterId;
    }

    public String getMonthValue() {
        return MonthValue;
    }

    public void setMonthValue(String monthValue) {
        MonthValue = monthValue;
    }

    public String getYearValue() {
        return YearValue;
    }

    public void setYearValue(String yearValue) {
        YearValue = yearValue;
    }

    public String getDayValue() {
        return DayValue;
    }

    public void setDayValue(String dayValue) {
        DayValue = dayValue;
    }

    public String getDCPDate() {
        return DCPDate;
    }

    public void setDCPDate(String DCPDate) {
        this.DCPDate = DCPDate;
    }

    public String getDayNameStr() {
        return DayNameStr;
    }

    public void setDayNameStr(String dayNameStr) {
        DayNameStr = dayNameStr;
    }

    public boolean isFinalSubmit() {
        return IsFinalSubmit;
    }

    public void setFinalSubmit(boolean finalSubmit) {
        IsFinalSubmit = finalSubmit;
    }

    public List<ModelDCPItem> getDCPListDtl() {
        return DCPListDtl;
    }

    public void setDCPListDtl(List<ModelDCPItem> DCPListDtl) {
        this.DCPListDtl = DCPListDtl;
    }
}
