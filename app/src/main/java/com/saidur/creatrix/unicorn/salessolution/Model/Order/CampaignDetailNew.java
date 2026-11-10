package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class CampaignDetailNew {
    public int campaignDetailId;
    public double minAmount;
    public double maxAmount;
    public int productId;
    public int quantity;
    public int bonusProductId;
    public int bonusQuantity;
    public String typeName;
    public String codeName;
    public String campaignName;
    public double discountPercentage;
    public double discountAmount;


    public int getCampaignDetailId() {
        return campaignDetailId;
    }

    public void setCampaignDetailId(int campaignDetailId) {
        this.campaignDetailId = campaignDetailId;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBonusProductId() {
        return bonusProductId;
    }

    public void setBonusProductId(int bonusProductId) {
        this.bonusProductId = bonusProductId;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public void setBonusQuantity(int bonusQuantity) {
        this.bonusQuantity = bonusQuantity;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }
}
