package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model;

import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorBrand;

import java.util.List;

public class DoctorListViewModel {
    int DoctorId;
    String DoctorCode;
    String DoctorName;
    String ChamberAddress;
    String ChemberName;
    String DocContact;
    int DocTPDetailsId;
    String DoctorTypeName;
    String ProgramTypeName;
    int ProgramTypeId;
    int DoctorTypeId;
    int SMCTypeId;
    String SMCType;
    List<DoctorBrand> doctorBrand;
   String GroupId;
   String RegionId;
   String AreaId;
   String TerritoryId;
   String SubTerritoryId;
   String MarketId;
   String MarketName;
   String MarketCode;
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

    public String getChamberAddress() {
        return ChamberAddress;
    }

    public void setChamberAddress(String chamberAddress) {
        ChamberAddress = chamberAddress;
    }

    public String getDocContact() {
        return DocContact;
    }

    public void setDocContact(String docContact) {
        DocContact = docContact;
    }

    public String getDoctorTypeName() {
        return DoctorTypeName;
    }

    public void setDoctorTypeName(String doctorTypeName) {
        DoctorTypeName = doctorTypeName;
    }

    public String getProgramTypeName() {
        return ProgramTypeName;
    }

    public void setProgramTypeName(String programTypeName) {
        ProgramTypeName = programTypeName;
    }

    public String getChemberName() {
        return ChemberName;
    }

    public void setChemberName(String chemberName) {
        ChemberName = chemberName;
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

    public int getProgramTypeId() {
        return ProgramTypeId;
    }

    public void setProgramTypeId(int programTypeId) {
        ProgramTypeId = programTypeId;
    }

    public int getDoctorTypeId() {
        return DoctorTypeId;
    }

    public void setDoctorTypeId(int doctorTypeId) {
        DoctorTypeId = doctorTypeId;
    }

    public int getSMCTypeId() {
        return SMCTypeId;
    }

    public void setSMCTypeId(int SMCTypeId) {
        this.SMCTypeId = SMCTypeId;
    }

    public String getSMCType() {
        return SMCType;
    }

    public void setSMCType(String SMCType) {
        this.SMCType = SMCType;
    }

    public String getMarketCode() {
        return MarketCode;
    }

    public void setMarketCode(String marketCode) {
        MarketCode = marketCode;
    }

    @Override
    public String toString() {
        return DoctorName;
    }

}
