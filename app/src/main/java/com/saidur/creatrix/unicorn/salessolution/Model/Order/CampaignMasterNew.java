package com.saidur.creatrix.unicorn.salessolution.Model.Order;

import java.util.List;

public class CampaignMasterNew {
    public  int campgainMasterId;
    public  int productLineID;
    public  int campainTypeId;
    public  int customerTypeId;
    public  double amount;
    public  double maxAmount;
    public  boolean isTradePolicy;
    public  int productQty;
    public  int bonusProductId;
    public  String campaignName;
    public  String codeName;
    public List<CampaignDetailNew> campDetail;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
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

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        codeName = codeName;
    }

    public List<CampaignDetailNew> getCampDetail() {
        return campDetail;
    }

    public void setCampDetail(List<CampaignDetailNew> campDetail) {
        this.campDetail = campDetail;
    }
}
