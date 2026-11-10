package com.saidur.creatrix.unicorn.salessolution.Model;

public class MarketViewModel {
    private  int MarketId;
    private  String MarketCode;
    private  String MarketName;
    private  String Description;

    @Override
    public String toString() {
        return MarketName;
    }

    public int getMarketId() {
        return MarketId;
    }

    public void setMarketId(int marketId) {
        MarketId = marketId;
    }

    public String getMarketCode() {
        return MarketCode;
    }

    public void setMarketCode(String marketCode) {
        MarketCode = marketCode;
    }

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
