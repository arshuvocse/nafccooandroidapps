package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class ModelProduct implements Cloneable {
    public int ProductId;
    public int CustomerMasterId;
    public String ProductName;
    public String ProductCode;
    public String ProductDes;
    public String PackSize;
    public Double UnitPrice;
    public Double QuotedPrice;
    public Double VatPercentage;
    public Double VatAmountPerunit;
    public int Quantity;

    public Double tp;
    public Double price;
    public Double DiscountValue;

    public String TotalVatAmount;
    public String NetAmount;

    public boolean IsBonusRow;
    public boolean IsDiscountNotAllowed;

    public boolean IsGiftProduct;
    public boolean IsCampaignProduct = false;
    public double DiscountPercentage;
    public String CampaingName;
    public String CampaignType;

    int CampaignMasterId;
    int CustomerId;
    int CustomerTypeId;
    boolean isChecked;

    public ModelProduct(int productId, String productName, Double unitPrice, Double vatPercentage, int quantity, String productCode) {
        ProductId = productId;
        ProductName = productName;
        UnitPrice = unitPrice;
        VatPercentage = vatPercentage;
        Quantity = quantity;
        ProductCode = productCode;
    }


    public ModelProduct() {

    }

    public ModelProduct(int productId) {
        ProductId = productId;
    }

    @Override
    public ModelProduct clone() {
        ModelProduct clone = null;
        try {
            clone = (ModelProduct) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // won't happen
        }
        return clone;

    }


    public boolean isCampaignProduct() {
        return IsCampaignProduct;
    }

    public void setCampaignProduct(boolean campaignProduct) {
        IsCampaignProduct = campaignProduct;
    }

    public boolean isGiftProduct() {
        return IsGiftProduct;
    }

    public void setGiftProduct(boolean giftProduct) {
        IsGiftProduct = giftProduct;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public String getCampaingName() {
        return CampaingName;
    }

    public void setCampaingName(String campaingName) {
        CampaingName = campaingName;
    }

    public String getCampaignType() {
        return CampaignType;
    }

    public void setCampaignType(String campaignType) {
        CampaignType = campaignType;
    }

    public boolean isDiscountNotAllowed() {
        return IsDiscountNotAllowed;
    }

    public void setDiscountNotAllowed(boolean discountNotAllowed) {
        IsDiscountNotAllowed = discountNotAllowed;
    }

    public boolean isBonusRow() {
        return IsBonusRow;
    }

    public void setBonusRow(boolean bonusRow) {
        IsBonusRow = bonusRow;
    }


    public Double getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(Double discountValue) {
        DiscountValue = discountValue;
    }

    public int getCustomerMasterId() {
        return CustomerMasterId;
    }

    public void setCustomerMasterId(int customerMasterId) {
        CustomerMasterId = customerMasterId;
    }

    public Double getQuotedPrice() {
        return QuotedPrice;
    }

    public void setQuotedPrice(Double quotedPrice) {
        QuotedPrice = quotedPrice;
    }

    public String getTotalVatAmount() {
        return TotalVatAmount;
    }

    public void setTotalVatAmount(String totalVatAmount) {
        TotalVatAmount = totalVatAmount;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }


    public Double getTp() {
        return tp;
    }

    public void setTp(Double tp) {
        this.tp = tp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
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

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductDes() {
        return ProductDes;
    }

    public void setProductDes(String productDes) {
        ProductDes = productDes;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public Double getVatPercentage() {
        return VatPercentage;
    }

    public void setVatPercentage(Double vatPercentage) {
        VatPercentage = vatPercentage;
    }

    public Double getVatAmountPerunit() {
        return VatAmountPerunit;
    }

    public void setVatAmountPerunit(Double vatAmountPerunit) {
        VatAmountPerunit = vatAmountPerunit;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return ProductName;/* "Product{" +
                "ProductId=" + ProductId +
                ", CustomerMasterId=" + CustomerMasterId +
                ", ProductName='" + ProductName + '\'' +
                ", ProductCode='" + ProductCode + '\'' +
                ", UnitPrice=" + UnitPrice +
                ", VatPercentage=" + VatPercentage +
                ", VatAmountPerunit=" + VatAmountPerunit +
                ", Quantity=" + Quantity +
                ", tp=" + tp +
                ", price=" + price +
                ", DiscountValue=" + DiscountValue +
                ", TotalVatAmount='" + TotalVatAmount + '\'' +
                ", NetAmount='" + NetAmount + '\'' +
                ", IsCampaignProduct=" + IsCampaignProduct +
                ", DiscountPercentage=" + DiscountPercentage +
                ", CampaignType='" + CampaignType + '\'' +
                ", CampaignMasterId=" + CampaignMasterId +
                ", CustomerId=" + CustomerId +
                ", CustomerTypeId=" + CustomerTypeId +
                '}';
   */
    }
}
