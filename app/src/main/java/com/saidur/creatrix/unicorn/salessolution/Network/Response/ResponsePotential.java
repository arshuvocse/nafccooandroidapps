package com.saidur.creatrix.unicorn.salessolution.Network.Response;

public class ResponsePotential {
    String PotentialStatus;
    String Color;

    public ResponsePotential(String potentialStatus, String color) {
        PotentialStatus = potentialStatus;
        Color = color;
    }

    public ResponsePotential() {
    }

    public String getPotentialStatus() {
        return PotentialStatus;
    }

    public void setPotentialStatus(String potentialStatus) {
        PotentialStatus = potentialStatus;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
