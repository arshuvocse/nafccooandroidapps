package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRxDetails {
    @SerializedName("BusinessUnit")
    @Expose
    private String businessUnit;
    @SerializedName("SalesLine")
    @Expose
    private String salesLine;
    @SerializedName("Cluster")
    @Expose
    private String cluster;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("Team")
    @Expose
    private String team;
    @SerializedName("Territory")
    @Expose
    private String territory;
    @SerializedName("EmployeeID")
    @Expose
    private String employeeID;
    @SerializedName("EmployeeName")
    @Expose
    private String employeeName;
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("OtherDoctor")
    @Expose
    private String otherDoctor;
    @SerializedName("Dateofsubmission")
    @Expose
    private String dateofsubmission;
    @SerializedName("DateofRx")
    @Expose
    private String dateofRx;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("RXImage")
    @Expose
    private String rXImage;

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getSalesLine() {
        return salesLine;
    }

    public void setSalesLine(String salesLine) {
        this.salesLine = salesLine;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getOtherDoctor() {
        return otherDoctor;
    }

    public void setOtherDoctor(String otherDoctor) {
        this.otherDoctor = otherDoctor;
    }

    public String getDateofsubmission() {
        return dateofsubmission;
    }

    public void setDateofsubmission(String dateofsubmission) {
        this.dateofsubmission = dateofsubmission;
    }

    public String getDateofRx() {
        return dateofRx;
    }

    public void setDateofRx(String dateofRx) {
        this.dateofRx = dateofRx;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRXImage() {
        return rXImage;
    }

    public void setRXImage(String rXImage) {
        this.rXImage = rXImage;
    }
}
