package com.saidur.creatrix.unicorn.salessolution.Network.Request;

public class ModelLeaveSvBody {
    int LeaveApplicationId;
    int LeaveBalanceId;
    String StartDate;
    String EndDate;
    String Reason;
    int EmpId;
    String DateOfReturnsToDuty;
    String LeaveAddress;
    String EmergencyContactNo;
    String Comments;
    String ImageBase64String;


    public int getLeaveApplicationId() {
        return LeaveApplicationId;
    }

    public void setLeaveApplicationId(int leaveApplicationId) {
        LeaveApplicationId = leaveApplicationId;
    }

    public int getLeaveBalanceId() {
        return LeaveBalanceId;
    }

    public void setLeaveBalanceId(int leaveBalanceId) {
        LeaveBalanceId = leaveBalanceId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public int getEmpId() {
        return EmpId;
    }

    public void setEmpId(int empId) {
        EmpId = empId;
    }

    public String getDateOfReturnsToDuty() {
        return DateOfReturnsToDuty;
    }

    public void setDateOfReturnsToDuty(String dateOfReturnsToDuty) {
        DateOfReturnsToDuty = dateOfReturnsToDuty;
    }

    public String getLeaveAddress() {
        return LeaveAddress;
    }

    public void setLeaveAddress(String leaveAddress) {
        LeaveAddress = leaveAddress;
    }

    public String getEmergencyContactNo() {
        return EmergencyContactNo;
    }

    public void setEmergencyContactNo(String emergencyContactNo) {
        EmergencyContactNo = emergencyContactNo;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getImageBase64String() {
        return ImageBase64String;
    }

    public void setImageBase64String(String imageBase64String) {
        ImageBase64String = imageBase64String;
    }
}
