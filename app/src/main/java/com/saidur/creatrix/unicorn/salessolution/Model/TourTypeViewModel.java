package com.saidur.creatrix.unicorn.salessolution.Model;

public class TourTypeViewModel {
    int pk;
    int TourTypeId;
    String TourTypeName;

    @Override
    public String toString() {
        return TourTypeName;
    }

    public int getTourTypeId() {
        return TourTypeId;
    }

    public void setTourTypeId(int tourTypeId) {
        TourTypeId = tourTypeId;
    }

    public String getTourTypeName() {
        return TourTypeName;
    }

    public void setTourTypeName(String tourTypeName) {
        TourTypeName = tourTypeName;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }
}
