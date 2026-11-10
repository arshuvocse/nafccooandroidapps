package com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ATourPlanDtlsDAO {
    @SerializedName("CustomerMasterId")
    @Expose
    private Integer customerMasterId;
    @SerializedName("MarketId")
    @Expose
    private Integer marketId;
    @SerializedName("TourPlanDate")
    @Expose
    private String tourPlanDate;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("MarketName")
    @Expose
    private String marketName;
    @SerializedName("TPId")
    @Expose
    private Integer tPId;
    @SerializedName("TPName")
    @Expose
    private String tPName;

    @SerializedName("MarketNameEnd")
    @Expose
    private String MarketNameEnd;
    @SerializedName("OtherMarketName")
    @Expose
    private String OtherMarketName;

    public String getMarketNameEnd() {
        return MarketNameEnd;
    }

    public void setMarketNameEnd(String marketNameEnd) {
        MarketNameEnd = marketNameEnd;
    }

    public String getOtherMarketName() {
        return OtherMarketName;
    }

    public void setOtherMarketName(String otherMarketName) {
        OtherMarketName = otherMarketName;
    }

    public String getVisitType() {
        return VisitType;
    }

    public void setVisitType(String visitType) {
        VisitType = visitType;
    }

    @SerializedName("VisitType")
    @Expose
    private String VisitType;

    public Integer getCustomerMasterId() {
        return customerMasterId;
    }

    public void setCustomerMasterId(Integer customerMasterId) {
        this.customerMasterId = customerMasterId;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public String getTourPlanDate() {
        return tourPlanDate;
    }

    public void setTourPlanDate(String tourPlanDate) {
        this.tourPlanDate = tourPlanDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Integer getTPId() {
        return tPId;
    }

    public void setTPId(Integer tPId) {
        this.tPId = tPId;
    }

    public String getTPName() {
        return tPName;
    }

    public void setTPName(String tPName) {
        this.tPName = tPName;
    }

}
