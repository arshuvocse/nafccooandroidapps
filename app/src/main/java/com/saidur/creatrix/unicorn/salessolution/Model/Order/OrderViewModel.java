package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class OrderViewModel {
    private String GrossValue;
    private String MIOName;
    private String OrderStatus;
    private String OrderCode;
    private String CustomerName;
    private int OrderId;
    private String SubmissionDate;
    private String OrderType;
    private String CreatedBy;
    private String CreatedAt;
    private String CustomerType;
    private String ApprovalStatus;


    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getGrossValue() {
        return GrossValue;
    }

    public void setGrossValue(String grossValue) {
        GrossValue = grossValue;
    }

    public String getMIOName() {
        return MIOName;
    }

    public void setMIOName(String MIOName) {
        this.MIOName = MIOName;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getSubmissionDate() {
        return SubmissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        SubmissionDate = submissionDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }
}
