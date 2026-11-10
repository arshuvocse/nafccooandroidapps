package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class CampaignCalModel {
    int orderDetailID;
    int orderId;
    int productId;
    int quantity;
    double unitPrice;
    double totalPrice;
    double unitVatAmount;
    double vatPercentage;
    double totalVatAmount;
    double netAmount;
    String productName;
    boolean isGiftProduct = false;
    boolean isCampaignProduct = false;
    double discountPercentage;
    double discountValue;
    String campaingName;
    String campaignType;
    int campaignMasterId;
    int customerId;
    int customerTypeId;

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUnitVatAmount() {
        return unitVatAmount;
    }

    public void setUnitVatAmount(double unitVatAmount) {
        this.unitVatAmount = unitVatAmount;
    }

    public double getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public double getTotalVatAmount() {
        return totalVatAmount;
    }

    public void setTotalVatAmount(double totalVatAmount) {
        this.totalVatAmount = totalVatAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isGiftProduct() {
        return isGiftProduct;
    }

    public void setGiftProduct(boolean giftProduct) {
        isGiftProduct = giftProduct;
    }

    public boolean isCampaignProduct() {
        return isCampaignProduct;
    }

    public void setCampaignProduct(boolean campaignProduct) {
        isCampaignProduct = campaignProduct;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public String getCampaingName() {
        return campaingName;
    }

    public void setCampaingName(String campaingName) {
        this.campaingName = campaingName;
    }

    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }

    public int getCampaignMasterId() {
        return campaignMasterId;
    }

    public void setCampaignMasterId(int campaignMasterId) {
        this.campaignMasterId = campaignMasterId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    @Override
    public String toString() {
        return "CampaignCalModel{" +
                "productName='" + productName + '\'' +
                '}';
    }
}

