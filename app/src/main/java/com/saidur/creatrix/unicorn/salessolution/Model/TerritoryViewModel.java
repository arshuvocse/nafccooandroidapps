package com.saidur.creatrix.unicorn.salessolution.Model;

public class TerritoryViewModel {
    private int TerritoryId;
    private String TerritoryName;
    private String TerritoryCode;


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

    public String getTerritoryCode() {
        return TerritoryCode;
    }

    public void setTerritoryCode(String territoryCode) {
        TerritoryCode = territoryCode;
    }

    @Override
    public String toString() {
        return TerritoryName;
    }
}
