package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelTarget {
    String TargetValue;
    String SalesValue;
    String AchivPer;
    String ProductName;

    public ModelTarget() {
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getTargetValue() {
        return TargetValue;
    }
    public void setTargetValue(String targetValue) {
        TargetValue = targetValue;
    }

    public String getSalesValue() {
        return SalesValue;
    }

    public void setSalesValue(String salesValue) {
        SalesValue = salesValue;
    }

    public String getAchivPer() {
        return AchivPer;
    }

    public void setAchivPer(String achivPer) {
        AchivPer = achivPer;
    }
}
