package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

import java.util.List;

public class DoctorListViewModel {
    int DoctorId;
    String DoctorCode;
    String DoctorName;
    String Gender;
    String DoctorPatientStatus;
    String DoctorAddress;
    String DocContact;
    int DocTPDetailsId;
    List<DoctorBrand> doctorBrand;
    String GroupId;
    String RegionId;
    String AreaId;
    String TerritoryId;
    String SubTerritoryId;
    String MarketId;
    String MarketName;

    public int getDocTPDetailsId() {
        return DocTPDetailsId;
    }

    public void setDocTPDetailsId(int docTPDetailsId) {
        DocTPDetailsId = docTPDetailsId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getDoctorCode() {
        return DoctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        DoctorCode = doctorCode;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDoctorPatientStatus() {
        return DoctorPatientStatus;
    }

    public void setDoctorPatientStatus(String doctorPatientStatus) {
        DoctorPatientStatus = doctorPatientStatus;
    }

    public String getDoctorAddress() {
        return DoctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        DoctorAddress = doctorAddress;
    }

    public String getDocContact() {
        return DocContact;
    }

    public void setDocContact(String docContact) {
        DocContact = docContact;
    }



    public List<DoctorBrand> getDoctorBrand() {
        return doctorBrand;
    }

    public void setDoctorBrand(List<DoctorBrand> doctorBrand) {
        this.doctorBrand = doctorBrand;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public String getAreaId() {
        return AreaId;
    }

    public void setAreaId(String areaId) {
        AreaId = areaId;
    }

    public String getTerritoryId() {
        return TerritoryId;
    }

    public void setTerritoryId(String territoryId) {
        TerritoryId = territoryId;
    }

    public String getSubTerritoryId() {
        return SubTerritoryId;
    }

    public void setSubTerritoryId(String subTerritoryId) {
        SubTerritoryId = subTerritoryId;
    }

    public String getMarketId() {
        return MarketId;
    }

    public void setMarketId(String marketId) {
        MarketId = marketId;
    }

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    @Override
    public String toString() {
        return DoctorName;
    }

}
