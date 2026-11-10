package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model;

public class ModelWSApprovalSvBody {
    int MbeWSApprovalLogId;
    String Date;
    int FromEmpId;
    int ToEmpId;
    int TableId;
    String Status;
    String Comments;
    String Type;
    int Step;
    int RoleTypeId;
    int ToRoleTypeId;
    int MenuId;

    public ModelWSApprovalSvBody() {
    }

    public int getMbeWSApprovalLogId() {
        return MbeWSApprovalLogId;
    }

    public void setMbeWSApprovalLogId(int mbeWSApprovalLogId) {
        MbeWSApprovalLogId = mbeWSApprovalLogId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getFromEmpId() {
        return FromEmpId;
    }

    public void setFromEmpId(int fromEmpId) {
        FromEmpId = fromEmpId;
    }

    public int getToEmpId() {
        return ToEmpId;
    }

    public void setToEmpId(int toEmpId) {
        ToEmpId = toEmpId;
    }

    public int getTableId() {
        return TableId;
    }

    public void setTableId(int tableId) {
        TableId = tableId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getStep() {
        return Step;
    }

    public void setStep(int step) {
        Step = step;
    }

    public int getRoleTypeId() {
        return RoleTypeId;
    }

    public void setRoleTypeId(int roleTypeId) {
        RoleTypeId = roleTypeId;
    }

    public int getToRoleTypeId() {
        return ToRoleTypeId;
    }

    public void setToRoleTypeId(int toRoleTypeId) {
        ToRoleTypeId = toRoleTypeId;
    }

    public int getMenuId() {
        return MenuId;
    }

    public void setMenuId(int menuId) {
        MenuId = menuId;
    }
}
