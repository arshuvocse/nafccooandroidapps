package com.saidur.creatrix.unicorn.salessolution.Model;

public class TourPurposeViewModel {
    int TPId;
    String TPName;
    int IsMarketVisit;
    int IsOtherVisit;

    public int getIsMarketVisit() {
        return IsMarketVisit;
    }

    public void setIsMarketVisit(int isMarketVisit) {
        IsMarketVisit = isMarketVisit;
    }

    public int getIsOtherVisit() {
        return IsOtherVisit;
    }

    public void setIsOtherVisit(int isOtherVisit) {
        IsOtherVisit = isOtherVisit;
    }

    @Override
    public String toString() {
        return TPName;
    }

    public int getTPId() {
        return TPId;
    }

    public void setTPId(int TPId) {
        this.TPId = TPId;
    }

    public String getTPName() {
        return TPName;
    }

    public void setTPName(String TPName) {
        this.TPName = TPName;
    }
}
