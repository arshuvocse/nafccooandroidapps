package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelCustomer {
    public int CustomerMasterId;
    public String CustomerCode ;
    public int CategoryId ;
    public String CustomerName ;
    public String Address ;
    public String CellNo ;
    public String MarketId ;
    public String Addrees2 ;
    public String City ;
    public String ConPerson ;
    public String ShippingCond ;
    public String MarketCode ;
    public String MarketName ;
    public String MIACode ;
    public String MIAName ;
    public String AreaCode ;
    public String DisCode ;
    public String FEName ;
    public String ComUnitCode ;
    public String ComUnitName ;
    public String RegionCode ;
    public String DZSMName ;
    public String TermOfPayment ;
    public String CustomerCodeOld ;
    // String (not java.util.Date): Gson's default Date adapter parses using the JVM's
    // default locale/timezone, so an untyped java.util.Date field here can throw
    // JsonSyntaxException -- or silently misparse -- on devices with a non-US locale.
    public String UploadDate ;
    public boolean ExcelUpload ;
    public boolean FixedCustomer ;
    public int CustomerCheck ;
    public String Note;
    public String Type ;
    public int ComUnitId ;
    public boolean IsActive ;
    public String InActiveDate ;
    public String CustomerStation ;
    public String Division ;
    public String District ;
    public String Thana ;
    public String Upazila ;
    public String CustomerType ;
    public String CreditLimit ;
    public String DueAmount ;
    public String Balance ;
    public String Route ;
    String GroupId;
    String RegionId;
    String AreaId;
    String TerritoryId;
    String SubTerritoryId;

    public ModelCustomer() {
    }

    public ModelCustomer(int customerMasterId, String customerCode, String customerName, String address) {
        CustomerMasterId = customerMasterId;
        CustomerCode = customerCode;
        CustomerName = customerName;
        Address = address;
    }

    public int getCustomerMasterId() {
        return CustomerMasterId;
    }

    public void setCustomerMasterId(int customerMasterId) {
        CustomerMasterId = customerMasterId;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCellNo() {
        return CellNo;
    }

    public void setCellNo(String cellNo) {
        CellNo = cellNo;
    }

    public String getMarketId() {
        return MarketId;
    }

    public void setMarketId(String marketId) {
        MarketId = marketId;
    }

    public String getAddrees2() {
        return Addrees2;
    }

    public void setAddrees2(String addrees2) {
        Addrees2 = addrees2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public boolean isFixedCustomer() {
        return FixedCustomer;
    }

    public void setFixedCustomer(boolean fixedCustomer) {
        FixedCustomer = fixedCustomer;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getInActiveDate() {
        return InActiveDate;
    }

    public void setInActiveDate(String inActiveDate) {
        InActiveDate = inActiveDate;
    }

    public String getCustomerStation() {
        return CustomerStation;
    }

    public void setCustomerStation(String customerStation) {
        CustomerStation = customerStation;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getThana() {
        return Thana;
    }

    public void setThana(String thana) {
        Thana = thana;
    }

    public String getUpazila() {
        return Upazila;
    }

    public void setUpazila(String upazila) {
        Upazila = upazila;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getCreditLimit() {
        return CreditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        CreditLimit = creditLimit;
    }

    public String getDueAmount() {
        return DueAmount;
    }

    public void setDueAmount(String dueAmount) {
        DueAmount = dueAmount;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
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

    @Override
    public String toString() {
        return  CustomerName;
    }



    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getCustomerCheck() {
        return CustomerCheck;
    }

    public void setCustomerCheck(int customerCheck) {
        CustomerCheck = customerCheck;
    }

    public String getConPerson() {
        return ConPerson;
    }

    public void setConPerson(String conPerson) {
        ConPerson = conPerson;
    }

    public String getShippingCond() {
        return ShippingCond;
    }

    public void setShippingCond(String shippingCond) {
        ShippingCond = shippingCond;
    }

    public String getMarketCode() {
        return MarketCode;
    }

    public void setMarketCode(String marketCode) {
        MarketCode = marketCode;
    }

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public String getMIACode() {
        return MIACode;
    }

    public void setMIACode(String MIACode) {
        this.MIACode = MIACode;
    }

    public String getMIAName() {
        return MIAName;
    }

    public void setMIAName(String MIAName) {
        this.MIAName = MIAName;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getDisCode() {
        return DisCode;
    }

    public void setDisCode(String disCode) {
        DisCode = disCode;
    }

    public String getFEName() {
        return FEName;
    }

    public void setFEName(String FEName) {
        this.FEName = FEName;
    }

    public String getComUnitCode() {
        return ComUnitCode;
    }

    public void setComUnitCode(String comUnitCode) {
        ComUnitCode = comUnitCode;
    }

    public String getComUnitName() {
        return ComUnitName;
    }

    public void setComUnitName(String comUnitName) {
        ComUnitName = comUnitName;
    }

    public String getRegionCode() {
        return RegionCode;
    }

    public void setRegionCode(String regionCode) {
        RegionCode = regionCode;
    }

    public String getDZSMName() {
        return DZSMName;
    }

    public void setDZSMName(String DZSMName) {
        this.DZSMName = DZSMName;
    }

    public String getTermOfPayment() {
        return TermOfPayment;
    }

    public void setTermOfPayment(String termOfPayment) {
        TermOfPayment = termOfPayment;
    }

    public String getCustomerCodeOld() {
        return CustomerCodeOld;
    }

    public void setCustomerCodeOld(String customerCodeOld) {
        CustomerCodeOld = customerCodeOld;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String uploadDate) {
        UploadDate = uploadDate;
    }

    public boolean isExcelUpload() {
        return ExcelUpload;
    }

    public void setExcelUpload(boolean excelUpload) {
        ExcelUpload = excelUpload;
    }

    public int getComUnitId() {
        return ComUnitId;
    }

    public void setComUnitId(int comUnitId) {
        ComUnitId = comUnitId;
    }
}
