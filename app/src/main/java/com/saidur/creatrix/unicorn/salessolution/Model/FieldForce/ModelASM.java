package com.saidur.creatrix.unicorn.salessolution.Model.FieldForce;

public class ModelASM {
    int pk;
    int ASMEmpId;
    int ASMId;
    String EmpMasterCode;
    String EmpName;
    int AreaId;

    public int getPk() {
        return pk;
    }

    public int getASMEmpId() {
        return ASMEmpId;
    }

    public void setASMEmpId(int ASMEmpId) {
        this.ASMEmpId = ASMEmpId;
    }

    public int getASMId() {
        return ASMId;
    }

    public void setASMId(int ASMId) {
        this.ASMId = ASMId;
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

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    @Override
    public String toString() {
        return EmpMasterCode+":"+EmpName;
    }
}
