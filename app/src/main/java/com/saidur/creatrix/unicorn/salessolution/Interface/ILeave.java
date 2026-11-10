package com.saidur.creatrix.unicorn.salessolution.Interface;


import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveTypeInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveVM;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelLeaveSvBody;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseLeaveList;

import java.util.HashMap;
import java.util.List;

public interface ILeave {
    interface Presenter{
        void GetLeaveTyep(int empId,int year);
        void SaveLeave(ModelLeaveSvBody aInfo);
        void GetLeaveRecords(HashMap<String,String> map);
    }

    interface View{
        void onLeaveTypeGet(List<LeaveTypeInfo> aList);
        void onSaveSuccess(String message);
        void onSaveError(String message);
        void onLeaveRecordsGet(ResponseLeaveList ff);
    }
}
