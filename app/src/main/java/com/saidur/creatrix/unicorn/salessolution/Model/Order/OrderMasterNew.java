package com.saidur.creatrix.unicorn.salessolution.Model.Order;

import java.util.List;

public class OrderMasterNew {
    public int OrderId;
    public int EmpId;
    public int ComunitId;
    public String CustomerCode;
    public String SubmittedDate;
    public String CollectionDate;
    public String Remarks;
    public String OrderType;
    public double TpPercentage;
    public List<CampaignMasters> campaignMasters;
    public List<ModelProduct> OrderDetails;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getEmpId() {
        return EmpId;
    }

    public void setEmpId(int empId) {
        EmpId = empId;
    }

    public int getComunitId() {
        return ComunitId;
    }

    public void setComunitId(int comunitId) {
        ComunitId = comunitId;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getSubmittedDate() {
        return SubmittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        SubmittedDate = submittedDate;
    }

    public String getCollectionDate() {
        return CollectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        CollectionDate = collectionDate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public double getTpPercentage() {
        return TpPercentage;
    }

    public void setTpPercentage(double tpPercentage) {
        TpPercentage = tpPercentage;
    }

    public List<ModelProduct> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<ModelProduct> orderDetails) {
        OrderDetails = orderDetails;
    }

    public List<CampaignMasters> getCampaignMasters() {
        return campaignMasters;
    }

    public void setCampaignMasters(List<CampaignMasters> campaignMasters) {
        this.campaignMasters = campaignMasters;
    }
}
