package com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure;

public class ModelArea {
    int pk;
    int AreaId;
    String AreaName;
    int RegionId;

    public int getPk() {
        return pk;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public int getRegionId() {
        return RegionId;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }

    @Override
    public String toString() {
        return  AreaName;
    }
}
