package com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure;

public class ModelRegion {
    int pk;
    int RegionId;
    String RegionName;
    int GroupId;

    public int getRegionId() {
        return RegionId;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public int getPk() {
        return pk;
    }

    @Override
    public String toString() {
        return  RegionName;
    }
}
