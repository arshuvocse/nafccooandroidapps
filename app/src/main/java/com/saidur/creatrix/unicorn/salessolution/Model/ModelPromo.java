package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelPromo {
    int brandId;
    int promoId;
    String promoName;
    int icon;

    public ModelPromo(int promoId, String promoName, int icon) {
        this.promoId = promoId;
        this.promoName = promoName;
        this.icon = icon;
    }

    public ModelPromo(int promoId) {
        this.promoId = promoId;
    }

    public ModelPromo() {
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
