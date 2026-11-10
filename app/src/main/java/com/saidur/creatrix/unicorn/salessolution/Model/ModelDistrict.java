package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelDistrict {
    int DistrictId;
    String DistrictName;
    int DivisionId;

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public int getDivisionId() {
        return DivisionId;
    }

    public void setDivisionId(int divisionId) {
        DivisionId = divisionId;
    }

    @Override
    public String toString() {
        return DistrictName;
    }
}
