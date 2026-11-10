package com.saidur.creatrix.unicorn.salessolution.Model.FieldForce;

public class ModelRSM {
    int pk;
    int RSMEmpId;
    int RSMId;
    String EmpMasterCode;
    String EmpName;
    int RegionId;

    public int getPk() {
        return pk;
    }

    public int getRSMEmpId() {
        return RSMEmpId;
    }

    public void setRSMEmpId(int RSMEmpId) {
        this.RSMEmpId = RSMEmpId;
    }

    public int getRSMId() {
        return RSMId;
    }

    public void setRSMId(int RSMId) {
        this.RSMId = RSMId;
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

    public int getRegionId() {
        return RegionId;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }

    @Override
    public String toString() {
        return EmpMasterCode+":"+EmpName;
    }
}
