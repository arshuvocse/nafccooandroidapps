package com.saidur.creatrix.unicorn.salessolution.Network.Response;

import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveVM;

import java.util.List;

public class ResponseLeaveList {
    List<ModelLVType>LeaveTypeList;
    List<LeaveVM> LeaveList;

    public ResponseLeaveList() {
    }

    public List<ModelLVType> getLeaveTypeList() {
        return LeaveTypeList;
    }

    public void setLeaveTypeList(List<ModelLVType> leaveTypeList) {
        LeaveTypeList = leaveTypeList;
    }

    public List<LeaveVM> getLeaveList() {
        return LeaveList;
    }

    public void setLeaveList(List<LeaveVM> leaveList) {
        LeaveList = leaveList;
    }
}
