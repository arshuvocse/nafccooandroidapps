package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelDivision {
    int DivisionId;
    String DivisionName;

    public int getDivisionId() {
        return DivisionId;
    }

    public void setDivisionId(int divisionId) {
        DivisionId = divisionId;
    }

    public String getDivisionName() {
        return DivisionName;
    }

    public void setDivisionName(String divisionName) {
        DivisionName = divisionName;
    }

    @Override
    public String toString() {
        return DivisionName;
    }
}
