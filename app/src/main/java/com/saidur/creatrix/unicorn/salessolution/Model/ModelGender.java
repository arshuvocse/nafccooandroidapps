package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelGender {
     int GenderId;
     String GenderName;

    public int getGenderId() {
        return GenderId;
    }

    public void setGenderId(int genderId) {
        GenderId = genderId;
    }

    public String getGenderName() {
        return GenderName;
    }

    public void setGenderName(String genderName) {
        GenderName = genderName;
    }

    @Override
    public String toString() {
        return  GenderName;
    }
}
