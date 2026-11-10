package com.saidur.creatrix.unicorn.salessolution.Model;



public class CustomerViewModel {

    private int CustomerMasterId;
    private String CustomerName;
    private String Address;
    private String CustomerCode;

    public boolean isChecked() {
        return checked;
    }


    @Override public String toString() { return CustomerName; }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean checked;

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getCustomerCell() {
        return CustomerCell;
    }

    public void setCustomerCell(String customerCell) {
        CustomerCell = customerCell;
    }

    private String CustomerCell;


    public int getCustomerMasterId() {
        return CustomerMasterId;
    }

    public void setCustomerMasterId(int customerMasterId) {
        CustomerMasterId = customerMasterId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
