package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

import com.saidur.creatrix.unicorn.salessolution.Model.CustomerViewModel;

import java.util.List;

public class DoctorSM {
    int DoctorId;
    String DoctorName;
    String DegreeType;

    public String getDegreeType() {
        return DegreeType;
    }

    public void setDegreeType(String degreeType) {
        DegreeType = degreeType;
    }

    String Remarks;
    int DivisionId;
    int DistrictId;
    int ThanaId;
    String DegreeStr;
    String BrandStr;
    String SpecialityStr;
    String DoctorTypeStr;
    String InstitutionSTr;
    int DoctorTypeId;
    int SessionUser;
    int SubMarketId;
    int MarketId;
    int TerritoryId;
    int SubTerritoryId;
    String UnionName;
    String BMDCRegistration;
    String Gender;

    public String getWeeklyvisitday() {
        return Weeklyvisitday;
    }

    public void setWeeklyvisitday(String weeklyvisitday) {
        Weeklyvisitday = weeklyvisitday;
    }

    String Weeklyvisitday;



    String Location;
    String DoctorAddress;
    String NoofPatient;
    String NoofRx;
    String DoctorPatientStatus;
    String NoofCallPerMonth;
    String VisitingCardImg;
    int DoctorCategoryId;
    int StationTypeId;
    int DesignationId;
    String TourTypeId;

    List<DoctorContact> aDoctorContactDAO = null;
    List<DoctorSpecial> aDoctorSpecialDAO = null;
    List<ModelPracticeShift> PracticeList = null;


    public List<CustomerViewModel> getCustomerList() {
        return CustomerList;
    }

    public void setCustomerList(List<CustomerViewModel> customerList) {
        CustomerList = customerList;
    }

    List<CustomerViewModel> CustomerList = null;
    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public int getDivisionId() {
        return DivisionId;
    }

    public void setDivisionId(int divisionId) {
        DivisionId = divisionId;
    }

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }

    public int getThanaId() {
        return ThanaId;
    }

    public void setThanaId(int thanaId) {
        ThanaId = thanaId;
    }

    public String getDegreeStr() {
        return DegreeStr;
    }

    public void setDegreeStr(String degreeStr) {
        DegreeStr = degreeStr;
    }

    public String getBrandStr() {
        return BrandStr;
    }

    public void setBrandStr(String brandStr) {
        BrandStr = brandStr;
    }

    public String getSpecialityStr() {
        return SpecialityStr;
    }

    public void setSpecialityStr(String specialityStr) {
        SpecialityStr = specialityStr;
    }

    public String getDoctorTypeStr() {
        return DoctorTypeStr;
    }

    public void setDoctorTypeStr(String doctorTypeStr) {
        DoctorTypeStr = doctorTypeStr;
    }

    public String getInstitutionSTr() {
        return InstitutionSTr;
    }

    public void setInstitutionSTr(String institutionSTr) {
        InstitutionSTr = institutionSTr;
    }

    public int getDoctorTypeId() {
        return DoctorTypeId;
    }

    public void setDoctorTypeId(int doctorTypeId) {
        DoctorTypeId = doctorTypeId;
    }

    public int getSessionUser() {
        return SessionUser;
    }

    public void setSessionUser(int sessionUser) {
        SessionUser = sessionUser;
    }

    public int getSubMarketId() {
        return SubMarketId;
    }

    public void setSubMarketId(int subMarketId) {
        SubMarketId = subMarketId;
    }

    public int getMarketId() {
        return MarketId;
    }

    public void setMarketId(int marketId) {
        MarketId = marketId;
    }

    public int getTerritoryId() {
        return TerritoryId;
    }

    public void setTerritoryId(int territoryId) {
        TerritoryId = territoryId;
    }

    public int getSubTerritoryId() {
        return SubTerritoryId;
    }

    public void setSubTerritoryId(int subTerritoryId) {
        SubTerritoryId = subTerritoryId;
    }

    public String getUnionName() {
        return UnionName;
    }

    public void setUnionName(String unionName) {
        UnionName = unionName;
    }

    public String getBMDCRegistration() {
        return BMDCRegistration;
    }

    public void setBMDCRegistration(String BMDCRegistration) {
        this.BMDCRegistration = BMDCRegistration;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDoctorAddress() {
        return DoctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        DoctorAddress = doctorAddress;
    }

    public String getNoofPatient() {
        return NoofPatient;
    }

    public void setNoofPatient(String noofPatient) {
        NoofPatient = noofPatient;
    }

    public String getDoctorPatientStatus() {
        return DoctorPatientStatus;
    }

    public void setDoctorPatientStatus(String doctorPatientStatus) {
        DoctorPatientStatus = doctorPatientStatus;
    }

    public String getNoofCallPerMonth() {
        return NoofCallPerMonth;
    }

    public void setNoofCallPerMonth(String noofCallPerMonth) {
        NoofCallPerMonth = noofCallPerMonth;
    }

    public String getVisitingCardImg() {
        return VisitingCardImg;
    }

    public void setVisitingCardImg(String visitingCardImg) {
        VisitingCardImg = visitingCardImg;
    }

    public int getDoctorCategoryId() {
        return DoctorCategoryId;
    }

    public void setDoctorCategoryId(int doctorCategoryId) {
        DoctorCategoryId = doctorCategoryId;
    }

    public int getStationTypeId() {
        return StationTypeId;
    }

    public void setStationTypeId(int stationTypeId) {
        StationTypeId = stationTypeId;
    }

    public int getDesignationId() {
        return DesignationId;
    }

    public void setDesignationId(int designationId) {
        DesignationId = designationId;
    }

    public List<DoctorContact> getaDoctorContactDAO() {
        return aDoctorContactDAO;
    }

    public void setaDoctorContactDAO(List<DoctorContact> aDoctorContactDAO) {
        this.aDoctorContactDAO = aDoctorContactDAO;
    }

    public List<DoctorSpecial> getaDoctorSpecialDAO() {
        return aDoctorSpecialDAO;
    }

    public void setaDoctorSpecialDAO(List<DoctorSpecial> aDoctorSpecialDAO) {
        this.aDoctorSpecialDAO = aDoctorSpecialDAO;
    }

    public List<ModelPracticeShift> getPracticeList() {
        return PracticeList;
    }

    public void setPracticeList(List<ModelPracticeShift> practiceList) {
        PracticeList = practiceList;
    }

    public String getNoofRx() {
        return NoofRx;
    }

    public void setNoofRx(String noofRx) {
        NoofRx = noofRx;
    }

    public String getTourTypeId() {
        return TourTypeId;
    }

    public void setTourTypeId(String tourTypeId) {
        TourTypeId = tourTypeId;
    }
}
