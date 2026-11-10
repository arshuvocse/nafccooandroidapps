package com.saidur.creatrix.unicorn.salessolution.Model;

public class TourPlanPostModel {
     int TourPlanId;
     int TerritoryId;
     int MarketId;
     int SMId;
     int ShiftId;
     int TourTypeId;
     int CustomerMasterId;
     int TPId;
     String Comment;
     String TourPlanDate;
     int EmpInfoId;
     boolean IsMarketWise;


    public int getCustomerMasterId() {
        return CustomerMasterId;
    }

    public void setCustomerMasterId(int customerMasterId) {
        CustomerMasterId = customerMasterId;
    }

    public int getTourPlanId() {
        return TourPlanId;
    }

    public void setTourPlanId(int tourPlanId) {
        TourPlanId = tourPlanId;
    }

    public int getTerritoryId() {
        return TerritoryId;
    }

    public void setTerritoryId(int territoryId) {
        TerritoryId = territoryId;
    }

    public int getMarketId() {
        return MarketId;
    }

    public void setMarketId(int marketId) {
        MarketId = marketId;
    }

    public int getSMId() {
        return SMId;
    }

    public void setSMId(int SMId) {
        this.SMId = SMId;
    }

    public int getShiftId() {
        return ShiftId;
    }

    public void setShiftId(int shiftId) {
        ShiftId = shiftId;
    }

    public int getTourTypeId() {
        return TourTypeId;
    }

    public void setTourTypeId(int tourTypeId) {
        TourTypeId = tourTypeId;
    }

    public int getTPId() {
        return TPId;
    }

    public void setTPId(int TPId) {
        this.TPId = TPId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getTourPlanDate() {
        return TourPlanDate;
    }

    public void setTourPlanDate(String tourPlanDate) {
        TourPlanDate = tourPlanDate;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
    }

    public boolean isMarketWise() {
        return IsMarketWise;
    }

    public void setMarketWise(boolean marketWise) {
        IsMarketWise = marketWise;
    }
}
