package com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model;

public class TimeValidationResponse {

    private boolean IsValid;
    private String Message;

    // Getters and setters
    public boolean isValid() {
        return IsValid;
    }

    public void setValid(boolean isValid) {
        IsValid = isValid;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
