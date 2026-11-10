package com.saidur.creatrix.unicorn.salessolution.Model;

public class ShiftViewModel {
int ShiftId;
String ShiftText;

    @Override
    public String toString() {
        return ShiftText;
    }

    public int getShiftId() {
        return ShiftId;
    }

    public void setShiftId(int shiftId) {
        ShiftId = shiftId;
    }

    public String getShiftText() {
        return ShiftText;
    }

    public void setShiftText(String shiftText) {
        ShiftText = shiftText;
    }
}
