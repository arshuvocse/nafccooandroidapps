package com.saidur.creatrix.unicorn.salessolution.Model.Customer;

public class ModelProviderType {
    Integer ProviderTypeId;
    String ProviderType;
    Integer forCustomer;
    Integer forDoctor;

    public Integer getProviderTypeId() {
        return ProviderTypeId;
    }

    public void setProviderTypeId(Integer providerTypeId) {
        ProviderTypeId = providerTypeId;
    }

    public String getProviderType() {
        return ProviderType;
    }

    public void setProviderType(String providerType) {
        ProviderType = providerType;
    }

    public Integer getForCustomer() {
        return forCustomer;
    }

    public void setForCustomer(Integer forCustomer) {
        this.forCustomer = forCustomer;
    }

    public Integer getForDoctor() {
        return forDoctor;
    }

    public void setForDoctor(Integer forDoctor) {
        this.forDoctor = forDoctor;
    }

    @Override
    public String toString() {
        return  ProviderType;
    }
}
