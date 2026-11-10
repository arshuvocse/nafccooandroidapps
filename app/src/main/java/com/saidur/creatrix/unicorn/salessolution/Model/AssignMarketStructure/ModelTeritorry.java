package com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure;

public class ModelTeritorry {
    int pk;
    int TerritoryId;
    String TerritoryName;
    int AreaId;

    public int getPk() {
        return pk;
    }

    public int getTerritoryId() {
        return TerritoryId;
    }

    public void setTerritoryId(int territoryId) {
        TerritoryId = territoryId;
    }

    public String getTerritoryName() {
        return TerritoryName;
    }

    public void setTerritoryName(String territoryName) {
        TerritoryName = territoryName;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    @Override
    public String toString() {
        return TerritoryName;
    }
}
