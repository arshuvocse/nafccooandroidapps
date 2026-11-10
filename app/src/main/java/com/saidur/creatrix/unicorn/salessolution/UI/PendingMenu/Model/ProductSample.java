package com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model;

public class ProductSample {
    public int pk ;
    public int ProductId ;
    public String ProductName ;
    public String ProductCode ;
    public int Quantity;

    public ProductSample() {
    }

    public ProductSample(int productId, String productName, String productCode, int quantity) {
        ProductId = productId;
        ProductName = productName;
        ProductCode = productCode;
        Quantity = quantity;
    }

    public int getPk() {
        return pk;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return  ProductName;
    }
}
