package com.saidur.creatrix.unicorn.salessolution.Model.Leave;

public class LeaveTypeInfo {
    String LeaveTypeName;
    int  LeaveBalanceId;
    int  YearlyLeaveBalance;

    public String getLeaveTypeName() {
        return LeaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        LeaveTypeName = leaveTypeName;
    }

    public int getLeaveBalanceId() {
        return LeaveBalanceId;
    }

    public void setLeaveBalanceId(int leaveBalanceId) {
        LeaveBalanceId = leaveBalanceId;
    }

    public int getYearlyLeaveBalance() {
        return YearlyLeaveBalance;
    }

    public void setYearlyLeaveBalance(int yearlyLeaveBalance) {
        YearlyLeaveBalance = yearlyLeaveBalance;
    }

    @Override
    public String toString() {
        return LeaveTypeName+':'+YearlyLeaveBalance;
    }
}

