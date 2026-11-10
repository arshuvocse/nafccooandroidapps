package com.saidur.creatrix.unicorn.salessolution.Model.Order;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;

import java.util.List;

public class OrderMaster {

    public int OrderId ;
    public int EmpId ;
    public int ComunitId ;
    public int OrderIdLocal ;
    public String OrderCode ;
    public String MioCode ;

    public int CustomerMasterId;
    public String CustomerCode ;
    public String CustomerName ;

    public int CategoryId ;
    public String Address ;
    public String CellNo ;

    public String SubmittedDate ;
    public String CollectionDate ;
    public String DeliveryDate ;
    public String Status;
    public String Remarks;
    public String OrderType;

    public String IsFromCustomer;
    public String MIOName;
    public String GrossValue;
    public  String SubmDate;
    public int IsPending;
    public int IsConfirm;
    public int IsInvoiced;
    public int IsPayment;
    public int IsDelivered;

    //for sample order
    public int DoctorId;



    public ModelCustomer customer ;

    public List<OrderDetails> OrderDetailsMain ;
    public List<ModelProduct> OrderDetails ;


    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

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

    public int getOrderIdLocal() {
        return OrderIdLocal;
    }

    public void setOrderIdLocal(int orderIdLocal) {
        OrderIdLocal = orderIdLocal;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getMioCode() {
        return MioCode;
    }

    public void setMioCode(String mioCode) {
        MioCode = mioCode;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    public String getIsFromCustomer() {
        return IsFromCustomer;
    }

    public void setIsFromCustomer(String isFromCustomer) {
        IsFromCustomer = isFromCustomer;
    }

    public String getMIOName() {
        return MIOName;
    }

    public void setMIOName(String MIOName) {
        this.MIOName = MIOName;
    }

    public String getGrossValue() {
        return GrossValue;
    }

    public void setGrossValue(String grossValue) {
        GrossValue = grossValue;
    }

    public String getSubmDate() {
        return SubmDate;
    }

    public void setSubmDate(String submDate) {
        SubmDate = submDate;
    }

    public int getIsPending() {
        return IsPending;
    }

    public void setIsPending(int isPending) {
        IsPending = isPending;
    }

    public int getIsConfirm() {
        return IsConfirm;
    }

    public void setIsConfirm(int isConfirm) {
        IsConfirm = isConfirm;
    }

    public int getIsInvoiced() {
        return IsInvoiced;
    }

    public void setIsInvoiced(int isInvoiced) {
        IsInvoiced = isInvoiced;
    }

    public int getIsPayment() {
        return IsPayment;
    }

    public void setIsPayment(int isPayment) {
        IsPayment = isPayment;
    }

    public int getIsDelivered() {
        return IsDelivered;
    }

    public void setIsDelivered(int isDelivered) {
        IsDelivered = isDelivered;
    }

    public List<OrderDetails> getOrderDetailsMain() {
        return OrderDetailsMain;
    }

    public void setOrderDetailsMain(List<OrderDetails> orderDetailsMain) {
        OrderDetailsMain = orderDetailsMain;
    }

    public List<ModelProduct> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<ModelProduct> orderDetails) {
        OrderDetails = orderDetails;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public ModelCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(ModelCustomer customer) {
        this.customer = customer;
    }
}
