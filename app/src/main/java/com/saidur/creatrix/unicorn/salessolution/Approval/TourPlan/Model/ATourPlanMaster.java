package com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ATourPlanMaster {
    @SerializedName("TPMaster")
    @Expose
    private Integer tPMaster;
    @SerializedName("EmpInfoId")
    @Expose
    private Integer empInfoId;
    @SerializedName("MonthValue")
    @Expose
    private String monthValue;
    @SerializedName("YearValue")
    @Expose
    private String yearValue;
    @SerializedName("MonthName")
    @Expose
    private String monthName;
    @SerializedName("ApprovalStatus")
    @Expose
    private String approvalStatus;
    @SerializedName("ExHQ")
    @Expose
    private String ExHQ;
    @SerializedName("HQ")
    @Expose
    private String HQ;
    @SerializedName("OS")
    @Expose
    private String OS;
    @SerializedName("IsFinalSubmit")
    @Expose
    private Boolean isFinalSubmit;
    @SerializedName("aTourPlanDate")
    @Expose
    private List<ATourPlanDate> aTourPlanDate = null;

    public Integer getTPMaster() {
        return tPMaster;
    }

    public void setTPMaster(Integer tPMaster) {
        this.tPMaster = tPMaster;
    }

    public Integer getEmpInfoId() {
        return empInfoId;
    }

    public void setEmpInfoId(Integer empInfoId) {
        this.empInfoId = empInfoId;
    }

    public String getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(String monthValue) {
        this.monthValue = monthValue;
    }

    public String getYearValue() {
        return yearValue;
    }

    public void setYearValue(String yearValue) {
        this.yearValue = yearValue;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Boolean getIsFinalSubmit() {
        return isFinalSubmit;
    }

    public void setIsFinalSubmit(Boolean isFinalSubmit) {
        this.isFinalSubmit = isFinalSubmit;
    }

    public List<ATourPlanDate> getaTourPlanDate() {
        return aTourPlanDate;
    }

    public void setaTourPlanDate(List<ATourPlanDate> aTourPlanDate) {
        this.aTourPlanDate = aTourPlanDate;
    }

    public String getExHQ() {
        return ExHQ;
    }

    public void setExHQ(String exHQ) {
        ExHQ = exHQ;
    }

    public String getHQ() {
        return HQ;
    }

    public void setHQ(String HQ) {
        this.HQ = HQ;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }
}
