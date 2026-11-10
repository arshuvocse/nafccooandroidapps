package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

public class ModelPracticeShift {
    int PracticeShiftId;
    String PracticeShift;
    String Time;

    public ModelPracticeShift(String practiceShift, String time) {
        PracticeShift = practiceShift;
        Time = time;
    }

    public ModelPracticeShift(int practiceShiftId, String time) {
        PracticeShiftId = practiceShiftId;
        Time = time;
    }

    public ModelPracticeShift() {
    }

    public int getPracticeShiftId() {
        return PracticeShiftId;
    }

    public void setPracticeShiftId(int practiceShiftId) {
        PracticeShiftId = practiceShiftId;
    }

    public String getPracticeShift() {
        return PracticeShift;
    }

    public void setPracticeShift(String practiceShift) {
        PracticeShift = practiceShift;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    @Override
    public String toString() {
        return PracticeShift;
    }
}
