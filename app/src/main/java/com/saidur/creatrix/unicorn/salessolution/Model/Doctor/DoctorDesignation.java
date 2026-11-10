package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

public class DoctorDesignation {
    int pk;
    int DesignationId;
    String  DesignationName;


    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getDesignationId() {
        return DesignationId;
    }

    public void setDesignationId(int designationId) {
        DesignationId = designationId;
    }

    public String getDesignationName() {
        return DesignationName;
    }

    public void setDesignationName(String designationName) {
        DesignationName = designationName;
    }



    @Override
    public String toString() {
        return DesignationName;
    }
}
