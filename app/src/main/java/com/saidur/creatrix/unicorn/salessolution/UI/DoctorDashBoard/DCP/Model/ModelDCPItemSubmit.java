package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model;

import java.util.List;

public class ModelDCPItemSubmit {
    String DoctorId;
    String WorkSession;
    String empId;
    String DCPDate;
    //List<ModelDCPDate> dateList;

    public ModelDCPItemSubmit() {
    }

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }


    public String getWorkSession() {
        return WorkSession;
    }

    public void setWorkSession(String workSession) {
        WorkSession = workSession;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDCPDate() {
        return DCPDate;
    }

    public void setDCPDate(String DCPDate) {
        this.DCPDate = DCPDate;
    }
/*    public List<ModelDCPDate> getDateList() {
        return dateList;
    }

    public void setDateList(List<ModelDCPDate> dateList) {
        this.dateList = dateList;
    }*/
}
