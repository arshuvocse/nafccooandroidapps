package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class CampaignModel {
    int campgainMasterId;
    int productLineID;
    String campaignName;
    String campaignDesc;
    String codeName;
    String fromDate;
    String todate;
    int campainTypeId;
    int customerTypeId;
    int amount;
    int maxAmount;
    boolean isTradePolicy;
    int productQty;
    int bonusProductId;
    String campDetail;

    public int getCampgainMasterId() {
        return campgainMasterId;
    }

    public void setCampgainMasterId(int campgainMasterId) {
        this.campgainMasterId = campgainMasterId;
    }

    public int getProductLineID() {
        return productLineID;
    }

    public void setProductLineID(int productLineID) {
        this.productLineID = productLineID;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCampaignDesc() {
        return campaignDesc;
    }

    public void setCampaignDesc(String campaignDesc) {
        this.campaignDesc = campaignDesc;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public int getCampainTypeId() {
        return campainTypeId;
    }

    public void setCampainTypeId(int campainTypeId) {
        this.campainTypeId = campainTypeId;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public boolean isTradePolicy() {
        return isTradePolicy;
    }

    public void setTradePolicy(boolean tradePolicy) {
        isTradePolicy = tradePolicy;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public int getBonusProductId() {
        return bonusProductId;
    }

    public void setBonusProductId(int bonusProductId) {
        this.bonusProductId = bonusProductId;
    }

    public String getCampDetail() {
        return campDetail;
    }

    public void setCampDetail(String campDetail) {
        this.campDetail = campDetail;
    }

    @Override
    public String toString() {
        return campaignName;
    }
}
