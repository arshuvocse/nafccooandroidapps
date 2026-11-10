package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model;

public class TourDetailForTADA {
    String  MarketName;
    String  TerritoryName;
    String  SMName;
    String  TPName;
    //boolean  IsMarketWise;
    String  MName;
    String  TourType;
    int Id;
    int TourPurposeId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public String getTerritoryName() {
        return TerritoryName;
    }

    public void setTerritoryName(String territoryName) {
        TerritoryName = territoryName;
    }

    public String getSMName() {
        return SMName;
    }

    public void setSMName(String SMName) {
        this.SMName = SMName;
    }

    public String getTPName() {
        return TPName;
    }

    public void setTPName(String TPName) {
        this.TPName = TPName;
    }

//    public boolean isMarketWise() {
//        return IsMarketWise;
//    }
//
//    public void setMarketWise(boolean marketWise) {
//        IsMarketWise = marketWise;
//    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        this.MName = MName;
    }

    public String getTourType() {
        return TourType;
    }

    public void setTourType(String tourType) {
        TourType = tourType;
    }

    public int getTourPurposeId() {
        return TourPurposeId;
    }

    public void setTourPurposeId(int tourPurposeId) {
        TourPurposeId = tourPurposeId;
    }
}
