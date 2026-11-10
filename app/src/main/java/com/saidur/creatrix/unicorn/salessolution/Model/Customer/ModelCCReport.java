package com.saidur.creatrix.unicorn.salessolution.Model.Customer;

public class ModelCCReport {
    String CustomerName;
    String ChemistCallDate;
    String OrderStatus;
    String ChemistCallReason;
    String Comments;

    public ModelCCReport() {
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getChemistCallDate() {
        return ChemistCallDate;
    }

    public void setChemistCallDate(String chemistCallDate) {
        ChemistCallDate = chemistCallDate;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getChemistCallReason() {
        return ChemistCallReason;
    }

    public void setChemistCallReason(String chemistCallReason) {
        ChemistCallReason = chemistCallReason;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }
}
