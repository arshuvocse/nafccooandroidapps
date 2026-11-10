package com.saidur.creatrix.unicorn.salessolution.Model.MarketStructure;

public class EmpInfoListModels {  int pk;
    int MIOEmpId;
    int MIOId;
    String EmpMasterCode;
    String EmpName;
    int TerritoryId;

    public int getPk() {
        return pk;
    }

    public int getMIOEmpId() {
        return MIOEmpId;
    }

    public void setMIOEmpId(int MIOEmpId) {
        this.MIOEmpId = MIOEmpId;
    }

    public int getMIOId() {
        return MIOId;
    }

    public void setMIOId(int MIOId) {
        this.MIOId = MIOId;
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

    public int getTerritoryId() {
        return TerritoryId;
    }

    public void setTerritoryId(int territoryId) {
        TerritoryId = territoryId;
    }

    @Override
    public String toString() {
        return  EmpName;
    }
}
