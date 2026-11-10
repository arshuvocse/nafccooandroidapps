package com.saidur.creatrix.unicorn.salessolution.Model.Order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepoStockModel {
    @SerializedName("StockQty")
    @Expose
    private String stockQty;
    @SerializedName("ProductCode")
    @Expose
    private String productCode;
    @SerializedName("ProductName")
    @Expose
    private String productName;

    public DepoStockModel() {
    }

    public String getStockQty() {
        return stockQty;
    }

    public void setStockQty(String stockQty) {
        this.stockQty = stockQty;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    /*String StockQty;
    String ProductCode;
    String ProductName;
    public String getStockQty() {
        return StockQty;
    }

    public void setStockQty(String stockQty) {
        StockQty = stockQty;
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
    }*/

}
