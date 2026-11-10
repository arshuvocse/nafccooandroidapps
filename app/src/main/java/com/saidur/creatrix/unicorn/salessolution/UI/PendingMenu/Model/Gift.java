package com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model;

public class Gift {
    int pk;
    int ProductId;
    String ProductCode;
    String ProductName;
    int Quantity;
    int Position;



    public Gift() {
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

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return ProductName;
    }
}

