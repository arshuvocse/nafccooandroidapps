package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule;

import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseTwwpCopyModel;

import java.util.List;

public interface OnEditWSListener {
    void onMTeriNameEditFocusChange(int position,String value,List<ResponseTwwpCopyModel> dList);
    void onETeriNameEditFocusChange(int position,String value);
    void onMPlaceEditFocusChange(int position,String value);
    void onEPlaceEditFocusChange(int position,String value);
}
