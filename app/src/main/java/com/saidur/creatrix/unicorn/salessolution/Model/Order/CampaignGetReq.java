package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class CampaignGetReq {
    int CustomerId;
    int ProductId;
    double UnitPrice;
    int TotalPrice;
    int Qty;
    boolean IsApplied=false;


    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public boolean isApplied() {
        return IsApplied;
    }

    public void setApplied(boolean applied) {
        IsApplied = applied;
    }
}
