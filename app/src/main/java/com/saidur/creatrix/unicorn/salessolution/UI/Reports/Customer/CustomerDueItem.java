package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Customer;

public class CustomerDueItem {
    private final String customerName;
    private final String customerCode;
    private final String cellNo;
    private final String marketName;
    private final double dueAmount;

    public CustomerDueItem(String customerName, String customerCode, String cellNo, String marketName, double dueAmount) {
        this.customerName = customerName;
        this.customerCode = customerCode;
        this.cellNo = cellNo;
        this.marketName = marketName;
        this.dueAmount = dueAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getCellNo() {
        return cellNo;
    }

    public String getMarketName() {
        return marketName;
    }

    public double getDueAmount() {
        return dueAmount;
    }
}
