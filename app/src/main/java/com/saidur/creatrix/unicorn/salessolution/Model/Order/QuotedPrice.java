package com.saidur.creatrix.unicorn.salessolution.Model.Order;


public class QuotedPrice {
    public String description;
    public int quotedPriceDetailId;
    public String policy;
    public int customerMasterId;
    public String activeFromDate;
    public String activeToDate;
    public int productId;
    public String unitPrice;
    public String vat;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuotedPriceDetailId() {
        return quotedPriceDetailId;
    }

    public void setQuotedPriceDetailId(int quotedPriceDetailId) {
        this.quotedPriceDetailId = quotedPriceDetailId;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public int getCustomerMasterId() {
        return customerMasterId;
    }

    public void setCustomerMasterId(int customerMasterId) {
        this.customerMasterId = customerMasterId;
    }

    public String getActiveFromDate() {
        return activeFromDate;
    }

    public void setActiveFromDate(String activeFromDate) {
        this.activeFromDate = activeFromDate;
    }

    public String getActiveToDate() {
        return activeToDate;
    }

    public void setActiveToDate(String activeToDate) {
        this.activeToDate = activeToDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }
}
