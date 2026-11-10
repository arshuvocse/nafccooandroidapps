package com.saidur.creatrix.unicorn.salessolution.Model.Customer;

public class ProgramType {
    int pk;
    int ProgramTypeId;
    String  ProgramType;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getProgramTypeId() {
        return ProgramTypeId;
    }

    public void setProgramTypeId(int programTypeId) {
        ProgramTypeId = programTypeId;
    }

    public String getProgramType() {
        return ProgramType;
    }

    public void setProgramType(String programType) {
        ProgramType = programType;
    }


    @Override
    public String toString() {
        return ProgramType;
    }
}
