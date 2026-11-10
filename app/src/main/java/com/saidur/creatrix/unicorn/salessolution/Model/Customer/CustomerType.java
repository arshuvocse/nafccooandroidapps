package com.saidur.creatrix.unicorn.salessolution.Model.Customer;

public class CustomerType {
    int CustomerTypeId;
    String  CustomerType;

    public int getCustomerTypeId() {
        return CustomerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        CustomerTypeId = customerTypeId;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    @Override
    public String toString() {
        return CustomerType;
    }
}
