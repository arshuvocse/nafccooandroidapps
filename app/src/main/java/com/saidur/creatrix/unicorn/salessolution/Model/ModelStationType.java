package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelStationType {
    int StationTypeId;
    String  StationTypeName;

    public int getStationTypeId() {
        return StationTypeId;
    }

    public void setStationTypeId(int stationTypeId) {
        StationTypeId = stationTypeId;
    }

    public String getStationTypeName() {
        return StationTypeName;
    }

    public void setStationTypeName(String stationTypeName) {
        StationTypeName = stationTypeName;
    }

    @Override
    public String toString() {
        return StationTypeName;
    }
}
