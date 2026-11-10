package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelPriority {
    int PatientPriorityId;
    int PatientStartPoint;
    int PatientEndPoint;
    String PatientStatus;

    public ModelPriority() {
    }

    public int getPatientPriorityId() {
        return PatientPriorityId;
    }

    public void setPatientPriorityId(int patientPriorityId) {
        PatientPriorityId = patientPriorityId;
    }

    public int getPatientStartPoint() {
        return PatientStartPoint;
    }

    public void setPatientStartPoint(int patientStartPoint) {
        PatientStartPoint = patientStartPoint;
    }

    public int getPatientEndPoint() {
        return PatientEndPoint;
    }

    public void setPatientEndPoint(int patientEndPoint) {
        PatientEndPoint = patientEndPoint;
    }

    public String getPatientStatus() {
        return PatientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        PatientStatus = patientStatus;
    }
}
