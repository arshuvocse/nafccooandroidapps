package com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure;

public class ModelGroup {
    int pk;
    int GroupId;
    String GroupName;

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    @Override
    public String toString() {
        return  GroupName;
    }
}
