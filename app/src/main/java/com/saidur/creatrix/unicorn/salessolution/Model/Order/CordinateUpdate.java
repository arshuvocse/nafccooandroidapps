package com.saidur.creatrix.unicorn.salessolution.Model.Order;

public class CordinateUpdate {
    public String latitudeValue;
    public String longitudeValue;
    public String streetaddress;
    public int id;
    public int empId;

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String streetaddress) {
        this.streetaddress = streetaddress;
    }

    public String getLatitudeValue() {
        return latitudeValue;
    }

    public void setLatitudeValue(String latitudeValue) {
        this.latitudeValue = latitudeValue;
    }

    public String getLongitudeValue() {
        return longitudeValue;
    }

    public void setLongitudeValue(String longitudeValue) {
        this.longitudeValue = longitudeValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }
}
