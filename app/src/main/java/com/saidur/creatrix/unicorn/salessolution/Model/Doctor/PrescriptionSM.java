package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

import android.net.Uri;

import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;

import java.util.List;

public class PrescriptionSM {
    int PrescripId;
    int DoctorId;
    String DoctorName;
    int SessionUser;
    String PrescriptionDate;
    int PrescriptionTypeId;
    String PrescTypeName;
    String ImageString;
    Uri MyImg;
    int ChemberId;
    String EntryTime;
    List<ModelProduct> aProList;
    DoctorListViewModel doclist;

    int PrescriptionId;
    String PrescriptionType;
    String ProductName;
    String Createdby;
    String ApprovalStatus;
    String EmpName;


    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public int getSessionUser() {
        return SessionUser;
    }

    public void setSessionUser(int sessionUser) {
        SessionUser = sessionUser;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getPrescriptionDate() {
        return PrescriptionDate;
    }

    public void setPrescriptionDate(String prescriptionDate) {
        PrescriptionDate = prescriptionDate;
    }

    public int getPrescriptionTypeId() {
        return PrescriptionTypeId;
    }

    public void setPrescriptionTypeId(int prescriptionTypeId) {
        PrescriptionTypeId = prescriptionTypeId;
    }

    public String getImageString() {
        return ImageString;
    }

    public void setImageString(String imageString) {
        ImageString = imageString;
    }

    public Uri getMyImg() {
        return MyImg;
    }

    public void setMyImg(Uri myImg) {
        MyImg = myImg;
    }

    public List<ModelProduct> getaProList() {
        return aProList;
    }

    public void setaProList(List<ModelProduct> aProList) {
        this.aProList = aProList;
    }

    public int getPrescripId() {
        return PrescripId;
    }

    public void setPrescripId(int prescripId) {
        PrescripId = prescripId;
    }

    public int getChemberId() {
        return ChemberId;
    }

    public void setChemberId(int chemberId) {
        ChemberId = chemberId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getPrescTypeName() {
        return PrescTypeName;
    }

    public void setPrescTypeName(String prescTypeName) {
        PrescTypeName = prescTypeName;
    }

    public String getEntryTime() {
        return EntryTime;
    }

    public void setEntryTime(String entryTime) {
        EntryTime = entryTime;
    }

    public int getPrescriptionId() {
        return PrescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        PrescriptionId = prescriptionId;
    }

    public String getPrescriptionType() {
        return PrescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        PrescriptionType = prescriptionType;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getCreatedby() {
        return Createdby;
    }

    public void setCreatedby(String createdby) {
        Createdby = createdby;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public DoctorListViewModel getDoclist() {
        return doclist;
    }

    public void setDoclist(DoctorListViewModel doclist) {
        this.doclist = doclist;
    }
/* @Override
    public String toString() {
        return "PrescriptionSM{" +
                "PrescripId=" + PrescripId +
                ", DoctorId=" + DoctorId +
                ", SessionUser=" + SessionUser +
                ", PrescriptionDate='" + PrescriptionDate + '\'' +
                ", PrescriptionTypeId=" + PrescriptionTypeId +
                '}';
    }*/
}
