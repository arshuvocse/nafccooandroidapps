package com.saidur.creatrix.unicorn.salessolution.Model;

public class SubMarket {
    private int SMId;
    private String SMName;

    @Override
    public String toString() {
        return SMName;
    }

    public int getSMId() {
        return SMId;
    }

    public void setSMId(int SMId) {
        this.SMId = SMId;
    }

    public String getSMName() {
        return SMName;
    }

    public void setSMName(String SMName) {
        this.SMName = SMName;
    }
}
