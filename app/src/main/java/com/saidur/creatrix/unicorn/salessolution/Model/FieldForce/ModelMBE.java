package com.saidur.creatrix.unicorn.salessolution.Model.FieldForce;

public class ModelMBE {
    int MBEInfoId;
    int MBEEmpId;
    int SubTerritoryId;
    String EmpName;
    String EmpMasterCode;

    public int getMBEInfoId() {
        return MBEInfoId;
    }

    public void setMBEInfoId(int MBEInfoId) {
        this.MBEInfoId = MBEInfoId;
    }

    public int getMBEEmpId() {
        return MBEEmpId;
    }

    public void setMBEEmpId(int MBEEmpId) {
        this.MBEEmpId = MBEEmpId;
    }

    public int getSubTerritoryId() {
        return SubTerritoryId;
    }

    public void setSubTerritoryId(int subTerritoryId) {
        SubTerritoryId = subTerritoryId;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getEmpMasterCode() {
        return EmpMasterCode;
    }

    public void setEmpMasterCode(String empMasterCode) {
        EmpMasterCode = empMasterCode;
    }

    @Override
    public String toString() {
       return EmpMasterCode+":"+EmpName;
    }
}
