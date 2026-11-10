package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDCPItem {
    @SerializedName("DoctorId")
    @Expose
    private String DoctorId;

    @SerializedName("isCalled")
    @Expose
    private boolean isCalled;

    @SerializedName("SL")
    @Expose
    private String SL;

    @SerializedName("worksession")
    @Expose
    private String worksession;

    @SerializedName("tourType")
    @Expose
    private String tourType;

    @SerializedName("DCPDetailId")
    @Expose
    private String DCPDetailId;

    @SerializedName("DoctorName")
    @Expose
    private String DoctorName;

    public ModelDCPItem() {
    }

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public boolean isCalled() {
        return isCalled;
    }

    public void setCalled(boolean called) {
        isCalled = called;
    }

    public String getSL() {
        return SL;
    }

    public void setSL(String SL) {
        this.SL = SL;
    }

    public String getWorksession() {
        return worksession;
    }

    public void setWorksession(String worksession) {
        this.worksession = worksession;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getDCPDetailId() {
        return DCPDetailId;
    }

    public void setDCPDetailId(String DCPDetailId) {
        this.DCPDetailId = DCPDetailId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }
}
