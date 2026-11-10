package com.saidur.creatrix.unicorn.salessolution.Model.Leave;

public class LeaveVM {
    int LeaveApplicationId;
    String UserName;
    String LeaveTypeName;
    String LeaveFromDate;
    String LeaveToDate;
    String CreatedAt;
    int Days;
    String Reason;
    String ApprovalStatus;

    public int getLeaveApplicationId() {
        return LeaveApplicationId;
    }

    public void setLeaveApplicationId(int leaveApplicationId) {
        LeaveApplicationId = leaveApplicationId;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLeaveTypeName() {
        return LeaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        LeaveTypeName = leaveTypeName;
    }

    public String getLeaveFromDate() {
        return LeaveFromDate;
    }

    public void setLeaveFromDate(String leaveFromDate) {
        LeaveFromDate = leaveFromDate;
    }

    public String getLeaveToDate() {
        return LeaveToDate;
    }

    public void setLeaveToDate(String leaveToDate) {
        LeaveToDate = leaveToDate;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }
}
