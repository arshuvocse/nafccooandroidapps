package com.saidur.creatrix.unicorn.salessolution.Model.MarketStructure.AssignEmpTable;

public class NSM {
    int pk;
    int NSMEmpId;
    int NSMId;
    String EmpMasterCode;
    String EmpName;
    int GroupId;

    public int getPk() {
        return pk;
    }

    public int getNSMEmpId() {
        return NSMEmpId;
    }

    public void setNSMEmpId(int NSMEmpId) {
        this.NSMEmpId = NSMEmpId;
    }

    public int getNSMId() {
        return NSMId;
    }

    public void setNSMId(int NSMId) {
        this.NSMId = NSMId;
    }

    public String getEmpMasterCode() {
        return EmpMasterCode;
    }

    public void setEmpMasterCode(String empMasterCode) {
        EmpMasterCode = empMasterCode;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }
}
