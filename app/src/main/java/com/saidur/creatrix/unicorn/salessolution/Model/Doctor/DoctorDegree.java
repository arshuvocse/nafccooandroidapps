package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

public class DoctorDegree {
    int DegreeId;
    String DegreeName;
    int DoctorTypeId;

    public int getDegreeId() {
        return DegreeId;
    }

    public void setDegreeId(int degreeId) {
        DegreeId = degreeId;
    }

    public String getDegreeName() {
        return DegreeName;
    }

    public void setDegreeName(String degreeName) {
        DegreeName = degreeName;
    }

    public int getDoctorTypeId() {
        return DoctorTypeId;
    }

    public void setDoctorTypeId(int doctorTypeId) {
        DoctorTypeId = doctorTypeId;
    }

    @Override
    public String toString() {
        return DegreeName;
    }
}


