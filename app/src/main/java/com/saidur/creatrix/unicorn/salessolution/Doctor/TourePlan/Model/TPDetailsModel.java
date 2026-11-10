package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model;


import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;

import java.util.List;

public class TPDetailsModel {
    int TourPlanId;
    String Comment;
    boolean IsMarketWise;
    boolean IsApproved;
    String MarketName;
    String CustomerName;
    String ShiftText;
    String TourTypeName;
    String TPName;
    String MonthValue;
    String YearValue;
    String DayValue;
    String TourPlanDate;
    String SerialNo;
    String IsFinalSubmit;
    List<ModelCustomer> aCustomerDAOList;

    public int getTourPlanId() {
        return TourPlanId;
    }

    public void setTourPlanId(int tourPlanId) {
        TourPlanId = tourPlanId;
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

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getShiftText() {
        return ShiftText;
    }

    public void setShiftText(String shiftText) {
        ShiftText = shiftText;
    }

    public String getTourTypeName() {
        return TourTypeName;
    }

    public void setTourTypeName(String tourTypeName) {
        TourTypeName = tourTypeName;
    }

    public String getTPName() {
        return TPName;
    }

    public void setTPName(String TPName) {
        this.TPName = TPName;
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

    public String getTourPlanDate() {
        return TourPlanDate;
    }

    public void setTourPlanDate(String tourPlanDate) {
        TourPlanDate = tourPlanDate;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getIsFinalSubmit() {
        return IsFinalSubmit;
    }

    public void setIsFinalSubmit(String isFinalSubmit) {
        IsFinalSubmit = isFinalSubmit;
    }

    public List<ModelCustomer> getaCustomerDAOList() {
        return aCustomerDAOList;
    }

    public void setaCustomerDAOList(List<ModelCustomer> aCustomerDAOList) {
        this.aCustomerDAOList = aCustomerDAOList;
    }
}
