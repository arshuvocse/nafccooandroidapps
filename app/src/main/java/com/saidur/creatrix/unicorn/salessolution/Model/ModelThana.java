package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelThana {
    int ThanaId;
    String ThanaName;
    int district_id;

    public int getThanaId() {
        return ThanaId;
    }

    public void setThanaId(int thanaId) {
        ThanaId = thanaId;
    }

    public String getThanaName() {
        return ThanaName;
    }

    public void setThanaName(String thanaName) {
        ThanaName = thanaName;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    @Override
    public String toString() {
        return ThanaName;
    }
}
