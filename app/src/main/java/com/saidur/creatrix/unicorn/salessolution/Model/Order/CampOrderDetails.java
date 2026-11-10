package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class CampOrderDetails {
    public int ProductId;
    public String ProductName;
    public int Quantity;
    public double UnitPrice;
    public double TotalPrice;
    public double UnitVatAmount;
    public double VatPercentage;
    public double TotalVatAmount;
    public double NetAmount;
    boolean IsCampaignProduct= false;
    public double DiscountPercentage;
    public double DiscountValue;
    public int CampaignMasterId;
    public int CustomerId;
    public int CustomerTypeId;

    public CampOrderDetails() {
    }

    public CampOrderDetails(int productId, String productName, int quantity, double unitPrice, double totalPrice, double unitVatAmount, double vatPercentage, double totalVatAmount, double netAmount, boolean isCampaignProduct, double discountPercentage, double discountValue, int campaignMasterId, int customerId, int customerTypeId) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        UnitPrice = unitPrice;
        TotalPrice = totalPrice;
        UnitVatAmount = unitVatAmount;
        VatPercentage = vatPercentage;
        TotalVatAmount = totalVatAmount;
        NetAmount = netAmount;
        IsCampaignProduct = isCampaignProduct;
        DiscountPercentage = discountPercentage;
        DiscountValue = discountValue;
        CampaignMasterId = campaignMasterId;
        CustomerId = customerId;
        CustomerTypeId = customerTypeId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getUnitVatAmount() {
        return UnitVatAmount;
    }

    public void setUnitVatAmount(double unitVatAmount) {
        UnitVatAmount = unitVatAmount;
    }

    public double getVatPercentage() {
        return VatPercentage;
    }

    public void setVatPercentage(double vatPercentage) {
        VatPercentage = vatPercentage;
    }

    public double getTotalVatAmount() {
        return TotalVatAmount;
    }

    public void setTotalVatAmount(double totalVatAmount) {
        TotalVatAmount = totalVatAmount;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }

    public boolean isCampaignProduct() {
        return IsCampaignProduct;
    }

    public void setCampaignProduct(boolean campaignProduct) {
        IsCampaignProduct = campaignProduct;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public double getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(double discountValue) {
        DiscountValue = discountValue;
    }

    public int getCampaignMasterId() {
        return CampaignMasterId;
    }

    public void setCampaignMasterId(int campaignMasterId) {
        CampaignMasterId = campaignMasterId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getCustomerTypeId() {
        return CustomerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        CustomerTypeId = customerTypeId;
    }

    @Override
    public String toString() {
        return ProductName;
    }
}
