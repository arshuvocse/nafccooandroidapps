package com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model;

public class UserByRole {
    int pk;
    int EmpInfoId;
    String EmpName;
    String EmpMasterCode;
    int UserRoleID;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getUserRoleID() {
        return UserRoleID;
    }

    public void setUserRoleID(int userRoleID) {
        UserRoleID = userRoleID;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
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
}
