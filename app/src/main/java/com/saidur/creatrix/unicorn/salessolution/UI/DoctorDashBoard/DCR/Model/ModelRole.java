package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model;

public class ModelRole {
    int id;
    String role;
    boolean ischecked;

    public ModelRole() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    @Override
    public String toString() {
        return role;
    }
}
