package com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure;

public class ModelSubTeritorry {
    int pk;
    int SubTerritoryId;
    String SubTerritoryName;
    int TerritoryId;
    public int getPk() {
        return pk;
    }

    public int getSubTerritoryId() {
        return SubTerritoryId;
    }

    public void setSubTerritoryId(int subTerritoryId) {
        SubTerritoryId = subTerritoryId;
    }

    public String getSubTerritoryName() {
        return SubTerritoryName;
    }

    public void setSubTerritoryName(String subTerritoryName) {
        SubTerritoryName = subTerritoryName;
    }

    public int getTerritoryId() {
        return TerritoryId;
    }

    public void setTerritoryId(int territoryId) {
        TerritoryId = territoryId;
    }

    @Override
    public String toString() {
        return SubTerritoryName;
    }
}
