package com.saidur.creatrix.unicorn.salessolution.Model;


import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;

import java.util.List;

public class VisitplanModel {
    int VisitPlanId;
    String DoctorName;
    String DocTPDetailsId;
    int MonthValue;
    int YearValue;
    int DayValue;
    String TourPlanDate;
    boolean IsFinalSubmit;
    String Comment;
    boolean IsMarketWise;
    boolean IsApproved;

    int DocTPMaster;
    int EmpInfoId;
    int DoctorId;

    //String TourDate;


    List<DoctorListViewModel> docList;


    public VisitplanModel() {
    }

    public int getVisitPlanId() {
        return VisitPlanId;
    }

    public void setVisitPlanId(int visitPlanId) {
        VisitPlanId = visitPlanId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getDocTPDetailsId() {
        return DocTPDetailsId;
    }

    public void setDocTPDetailsId(String docTPDetailsId) {
        DocTPDetailsId = docTPDetailsId;
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

    public int getDayValue() {
        return DayValue;
    }

    public void setDayValue(int dayValue) {
        DayValue = dayValue;
    }

    public String getTourPlanDate() {
        return TourPlanDate;
    }

    public void setTourPlanDate(String tourPlanDate) {
        TourPlanDate = tourPlanDate;
    }

    public boolean isFinalSubmit() {
        return IsFinalSubmit;
    }

    public void setFinalSubmit(boolean finalSubmit) {
        IsFinalSubmit = finalSubmit;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public boolean isMarketWise() {
        return IsMarketWise;
    }

    public void setMarketWise(boolean marketWise) {
        IsMarketWise = marketWise;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

    public int getDocTPMaster() {
        return DocTPMaster;
    }

    public void setDocTPMaster(int docTPMaster) {
        DocTPMaster = docTPMaster;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

 /*   public String getTourDate() {
        return TourDate;
    }

    public void setTourDate(String tourDate) {
        TourDate = tourDate;
    }*/

    public List<DoctorListViewModel> getDocList() {
        return docList;
    }

    public void setDocList(List<DoctorListViewModel> docList) {
        this.docList = docList;
    }

    @Override
    public String toString() {
        return  DoctorName;
    }
}
