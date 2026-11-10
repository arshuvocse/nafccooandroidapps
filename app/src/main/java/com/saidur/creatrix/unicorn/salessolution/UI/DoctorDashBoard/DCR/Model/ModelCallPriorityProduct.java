package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelPromo;

import java.util.List;

public class ModelCallPriorityProduct {
    String priority;
    int BrandId;
    String promoName;
    List<ModelPromoId> BrandWisePromoList;
    public ModelCallPriorityProduct() {
    }

    public ModelCallPriorityProduct(String priority, int BrandId, List<ModelPromoId> BrandWisePromoList) {
        this.priority = priority;
        this.BrandId = BrandId;
        this.BrandWisePromoList = BrandWisePromoList;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public List<ModelPromoId> getBrandWisePromoList() {
        return BrandWisePromoList;
    }

    public void setBrandWisePromoList(List<ModelPromoId> brandWisePromoList) {
        BrandWisePromoList = brandWisePromoList;
    }
}
