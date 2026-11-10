package com.saidur.creatrix.unicorn.salessolution.Model.Order;
import java.util.List;

public class OrderMasterModel {
    int OrderId;
    int EmpId;
    String ComUnitId;
    String CustomerCode;
    String SubmittedDate;
    String CollectionDate;
    String DeliveryDate;
    String Remarks;
    String OrderType;
    Double TpPercentage;
    List<CampaignMaster2> campaignMasters;
    List<ModelProduct> OrderDetails = null;


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

    public String getComUnitId() {
        return ComUnitId;
    }

    public void setComUnitId(String comUnitId) {
        ComUnitId = comUnitId;
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

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
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

    public Double getTpPercentage() {
        return TpPercentage;
    }

    public void setTpPercentage(Double tpPercentage) {
        TpPercentage = tpPercentage;
    }

    public List<CampaignMaster2> getCampaignMasters() {
        return campaignMasters;
    }

    public void setCampaignMasters(List<CampaignMaster2> campaignMasters) {
        this.campaignMasters = campaignMasters;
    }

    public List<ModelProduct> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<ModelProduct> orderDetails) {
        OrderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "EmpId=" + EmpId +
                ", ComUnitId='" + ComUnitId + '\'' +
                ", CustomerCode='" + CustomerCode + '\'' +
                ", SubmittedDate='" + SubmittedDate + '\'' +
                ", CollectionDate='" + CollectionDate + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", OrderType='" + OrderType + '\'' +
                ", TpPercentage=" + TpPercentage +
                ", campaignMasters=" + campaignMasters +
                ", OrderDetails=" + OrderDetails;
    }
}
