package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model;

public class ModelPlanned {
    int id;
    String name;

    public ModelPlanned() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
