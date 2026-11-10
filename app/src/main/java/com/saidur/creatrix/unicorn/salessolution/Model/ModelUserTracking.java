package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelUserTracking {
    int EmpInfoId;
    String LatValue;
    String LongValue;
    String AddressName;
    String TimeStr;
    String TrackDate;

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
    }

    public String getLatValue() {
        return LatValue;
    }

    public void setLatValue(String latValue) {
        LatValue = latValue;
    }

    public String getLongValue() {
        return LongValue;
    }

    public void setLongValue(String longValue) {
        LongValue = longValue;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public String getTimeStr() {
        return TimeStr;
    }

    public void setTimeStr(String timeStr) {
        TimeStr = timeStr;
    }

    public String getTrackDate() {
        return TrackDate;
    }

    public void setTrackDate(String trackDate) {
        TrackDate = trackDate;
    }
}
