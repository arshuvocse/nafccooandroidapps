package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class OrderDetails {

    public int OrderDetailID ;
    public int OrderId ;
    public int ProductId ;
    public int Quantity ;
    public double UnitPrice ;
    public double TotalPrice ;
    public double UnitVatAmount ;
    public double TotalVatAmount ;
    public double NetAmount ;
    public String ProductName;
    public String TotalTradePrice;

    public String getTotalTradePrice() {
        return TotalTradePrice;
    }

    public void setTotalTradePrice(String totalTradePrice) {
        TotalTradePrice = totalTradePrice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getOrderDetailID() {
        return OrderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        OrderDetailID = orderDetailID;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getUnitVatAmount() {
        return UnitVatAmount;
    }

    public void setUnitVatAmount(double unitVatAmount) {
        UnitVatAmount = unitVatAmount;
    }

    public double getTotalVatAmount() {
        return TotalVatAmount;
    }

    public void setTotalVatAmount(double totalVatAmount) {
        TotalVatAmount = totalVatAmount;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }
}
