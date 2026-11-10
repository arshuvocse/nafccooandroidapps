package com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure;

public class ModelMarket {
    int pk;
    int MarketId;
    String MarketName;
    int SubTerritoryId;

    public int getPk() {
        return pk;
    }

    public int getMarketId() {
        return MarketId;
    }

    public String getMarketName() {
        return MarketName;
    }

    public int getSubTerritoryId() {
        return SubTerritoryId;
    }

    public void setMarketId(int marketId) {
        MarketId = marketId;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public void setSubTerritoryId(int subTerritoryId) {
        SubTerritoryId = subTerritoryId;
    }

    @Override
    public String toString() {
        return MarketName;
    }
}
