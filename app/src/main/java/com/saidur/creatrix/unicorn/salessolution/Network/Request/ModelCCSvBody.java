package com.saidur.creatrix.unicorn.salessolution.Network.Request;

public class ModelCCSvBody {
    int CustomerMasterId;
    int empId;
    String ChemistCallDate;
    boolean IsGivenOrder;
    String LatVal;
    String LongVal;
    int ReasonId;
    String Comments;
    String CallTime;

    public String getCallTime() {
        return CallTime;
    }

    public void setCallTime(String callTime) {
        CallTime = callTime;
    }

    String Address;

    public ModelCCSvBody() {
    }

    public int getCustomerMasterId() {
        return CustomerMasterId;
    }

    public void setCustomerMasterId(int customerMasterId) {
        CustomerMasterId = customerMasterId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getChemistCallDate() {
        return ChemistCallDate;
    }

    public void setChemistCallDate(String chemistCallDate) {
        ChemistCallDate = chemistCallDate;
    }

    public boolean isGivenOrder() {
        return IsGivenOrder;
    }

    public void setGivenOrder(boolean givenOrder) {
        IsGivenOrder = givenOrder;
    }

    public String getLatVal() {
        return LatVal;
    }

    public void setLatVal(String latVal) {
        LatVal = latVal;
    }

    public String getLongVal() {
        return LongVal;
    }

    public void setLongVal(String longVal) {
        LongVal = longVal;
    }

    public int getReasonId() {
        return ReasonId;
    }

    public void setReasonId(int reasonId) {
        ReasonId = reasonId;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
