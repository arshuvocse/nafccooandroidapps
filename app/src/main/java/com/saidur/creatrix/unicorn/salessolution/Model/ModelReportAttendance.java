package com.saidur.creatrix.unicorn.salessolution.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelReportAttendance {
    @SerializedName("ordVal")
    @Expose
    private Integer ordVal;
    @SerializedName("WorkSession")
    @Expose
    private String workSession;
    @SerializedName("MI_TourType")
    @Expose
    private String mITourType;
    @SerializedName("MI_WorkPlace")
    @Expose
    private String mIWorkPlace;
    @SerializedName("InAddress")
    @Expose
    private String inAddress;
    @SerializedName("ImagePreName")
    @Expose
    private String imagePreName;
    @SerializedName("PInLat")
    @Expose
    private String pInLat;
    @SerializedName("PInLog")
    @Expose
    private String pInLog;
    @SerializedName("RoleName")
    @Expose
    private String roleName;
    @SerializedName("DesigName")
    @Expose
    private String desigName;
    @SerializedName("EmpMasterCode")
    @Expose
    private String empMasterCode;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("AttendanceDate")
    @Expose
    private String attendanceDate;
    @SerializedName("PunchInTime")
    @Expose
    private String punchInTime;
    @SerializedName("PunchOutTime")
    @Expose
    private String punchOutTime;
    @SerializedName("OutLat")
    @Expose
    private String outLat;
    @SerializedName("OutLog")
    @Expose
    private String outLog;
    @SerializedName("AttStatus")
    @Expose
    private Boolean attStatus;
    @SerializedName("InStatus")
    @Expose
    private String inStatus;
    @SerializedName("OutStatus")
    @Expose
    private String outStatus;
    @SerializedName("OutAttAddress")
    @Expose
    private String outAttAddress;
    @SerializedName("Out_WorkPlace")
    @Expose
    private String outWorkPlace;

    public Integer getOrdVal() {
        return ordVal;
    }

    public void setOrdVal(Integer ordVal) {
        this.ordVal = ordVal;
    }

    public String getWorkSession() {
        return workSession;
    }

    public void setWorkSession(String workSession) {
        this.workSession = workSession;
    }

    public String getMITourType() {
        return mITourType;
    }

    public void setMITourType(String mITourType) {
        this.mITourType = mITourType;
    }

    public String getMIWorkPlace() {
        return mIWorkPlace;
    }

    public void setMIWorkPlace(String mIWorkPlace) {
        this.mIWorkPlace = mIWorkPlace;
    }

    public String getInAddress() {
        return inAddress;
    }

    public void setInAddress(String inAddress) {
        this.inAddress = inAddress;
    }

    public String getImagePreName() {
        return imagePreName;
    }

    public void setImagePreName(String imagePreName) {
        this.imagePreName = imagePreName;
    }

    public String getPInLat() {
        return pInLat;
    }

    public void setPInLat(String pInLat) {
        this.pInLat = pInLat;
    }

    public String getPInLog() {
        return pInLog;
    }

    public void setPInLog(String pInLog) {
        this.pInLog = pInLog;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDesigName() {
        return desigName;
    }

    public void setDesigName(String desigName) {
        this.desigName = desigName;
    }

    public String getEmpMasterCode() {
        return empMasterCode;
    }

    public void setEmpMasterCode(String empMasterCode) {
        this.empMasterCode = empMasterCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getPunchInTime() {
        return punchInTime;
    }

    public void setPunchInTime(String punchInTime) {
        this.punchInTime = punchInTime;
    }

    public String getPunchOutTime() {
        return punchOutTime;
    }

    public void setPunchOutTime(String punchOutTime) {
        this.punchOutTime = punchOutTime;
    }

    public String getOutLat() {
        return outLat;
    }

    public void setOutLat(String outLat) {
        this.outLat = outLat;
    }

    public String getOutLog() {
        return outLog;
    }

    public void setOutLog(String outLog) {
        this.outLog = outLog;
    }

    public Boolean getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(Boolean attStatus) {
        this.attStatus = attStatus;
    }

    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public String getOutAttAddress() {
        return outAttAddress;
    }

    public void setOutAttAddress(String outAttAddress) {
        this.outAttAddress = outAttAddress;
    }

    public String getOutWorkPlace() {
        return outWorkPlace;
    }

    public void setOutWorkPlace(String outWorkPlace) {
        this.outWorkPlace = outWorkPlace;
    }

}
