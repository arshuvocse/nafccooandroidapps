package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

public class ModelBrand {
    private Integer ProductBrandId;
    private Integer IngridentsId;
    private String ProductSQName;
    private int MaxValue;
    boolean isChecked;

    public ModelBrand() {
    }

    public Integer getProductBrandId() {
        return ProductBrandId;
    }

    public void setProductBrandId(Integer productBrandId) {
        ProductBrandId = productBrandId;
    }

    public Integer getIngridentsId() {
        return IngridentsId;
    }

    public void setIngridentsId(Integer ingridentsId) {
        IngridentsId = ingridentsId;
    }

    public String getProductSQName() {
        return ProductSQName;
    }

    public void setProductSQName(String productSQName) {
        ProductSQName = productSQName;
    }

    public int getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(int maxValue) {
        MaxValue = maxValue;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return ProductSQName;
    }
}
