package com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;

import java.util.List;

public class DcrSM {

    int DcrId;
    int DoctorId;
    String DoctorName;
    String DcrDate;
    String EntryTime;
    int TourPlanTypeId;
    int DocTPDetailsId;
    int ChamberId;
    String Remarks;
    String SessionUser;
    int VisitTypeId;
    String VisitTypeName;
    int IsNonEffectiveReason;
    int ReasonId;
    String EntryDate_Apps;

    DoctorListViewModel doclist;
    List<Gift> giftList;
    List<ProductSample> sampleList;
    List<DoctorBrand> doctorBrand;
    List<UserByRole> aempList;

    String Latitude;
    String Longitude ;
    String StreetAddress;


    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }

    public int getDcrId() {
        return DcrId;
    }

    public void setDcrId(int dcrId) {
        DcrId = dcrId;
    }

    public int getVisitTypeId() {
        return VisitTypeId;
    }

    public void setVisitTypeId(int visitTypeId) {
        VisitTypeId = visitTypeId;
    }

    public String getVisitTypeName() {
        return VisitTypeName;
    }

    public void setVisitTypeName(String visitTypeName) {
        VisitTypeName = visitTypeName;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getEntryTime() {
        return EntryTime;
    }

    public void setEntryTime(String entryTime) {
        EntryTime = entryTime;
    }

    public int getDocTPDetailsId() {
        return DocTPDetailsId;
    }

    public void setDocTPDetailsId(int docTPDetailsId) {
        DocTPDetailsId = docTPDetailsId;
    }

    public List<UserByRole> getAempList() {
        return aempList;
    }

    public void setAempList(List<UserByRole> aempList) {
        this.aempList = aempList;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getDcrDate() {
        return DcrDate;
    }

    public void setDcrDate(String dcrDate) {
        DcrDate = dcrDate;
    }

    public int getTourPlanTypeId() {
        return TourPlanTypeId;
    }

    public void setTourPlanTypeId(int tourPlanTypeId) {
        TourPlanTypeId = tourPlanTypeId;
    }

    public int getChamberId() {
        return ChamberId;
    }

    public void setChamberId(int chamberId) {
        ChamberId = chamberId;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getSessionUser() {
        return SessionUser;
    }

    public void setSessionUser(String sessionUser) {
        SessionUser = sessionUser;
    }

    public int getIsNonEffectiveReason() {
        return IsNonEffectiveReason;
    }

    public void setIsNonEffectiveReason(int isNonEffectiveReason) {
        IsNonEffectiveReason = isNonEffectiveReason;
    }

    public int getReasonId() {
        return ReasonId;
    }

    public void setReasonId(int reasonId) {
        ReasonId = reasonId;
    }

    public List<Gift> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<Gift> giftList) {
        this.giftList = giftList;
    }

    public List<ProductSample> getSampleList() {
        return sampleList;
    }

    public void setSampleList(List<ProductSample> sampleList) {
        this.sampleList = sampleList;
    }

    public String getEntryDate_Apps() {
        return EntryDate_Apps;
    }

    public void setEntryDate_Apps(String entryDate_Apps) {
        EntryDate_Apps = entryDate_Apps;
    }

    public DoctorListViewModel getDoclist() {
        return doclist;
    }

    public void setDoclist(DoctorListViewModel doclist) {
        this.doclist = doclist;
    }

    public List<DoctorBrand> getDoctorBrand() {
        return doctorBrand;
    }

    public void setDoctorBrand(List<DoctorBrand> doctorBrand) {
        this.doctorBrand = doctorBrand;
    }

    @Override
    public String toString() {
        return DoctorName + DcrDate + EntryTime ;
    }
}
