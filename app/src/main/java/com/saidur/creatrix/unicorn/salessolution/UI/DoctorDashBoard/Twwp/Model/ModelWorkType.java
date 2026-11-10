package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelWorkType {
     int WorkTypeId;
     String WorkType;

    public ModelWorkType() {
    }

    public int getWorkTypeId() {
        return WorkTypeId;
    }

    public void setWorkTypeId(int workTypeId) {
        WorkTypeId = workTypeId;
    }

    public String getWorkType() {
        return WorkType;
    }

    public void setWorkType(String workType) {
        WorkType = workType;
    }

    @Override
    public String toString() {
        return WorkType;
    }
}
