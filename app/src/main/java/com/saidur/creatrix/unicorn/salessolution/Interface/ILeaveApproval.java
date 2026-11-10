package com.saidur.creatrix.unicorn.salessolution.Interface;



import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalData;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveApprovalRQ;

import java.util.List;
import java.util.Map;

public interface ILeaveApproval {
    interface Presenter{
        void getLeaveApprovalList(String pram, Map<String,String> mapparam);
        void SaveLeaveApproval(LeaveApprovalRQ aInfo);
    }
    interface  View{
        void onGetLeaveApprovalList(List<LeaveApprovalData> aList);
        void onSaveSuccess(String message);
        void onError(String message);
    }
}