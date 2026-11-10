package com.saidur.creatrix.unicorn.salessolution.Model;



import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;

import java.util.List;

public class TourPlanViewModel {
    int TourPlanId;
    int DocTPDetailsId;
    String Comment;
    boolean IsMarketWise;
    boolean IsApproved;
    int SMId;
    int GroupId;
    int RegionId;
    int AreaId;
    int TerritoryId;
    int SubTerritoryId;
    int MarketId;
      int MarketIdEnd;
    String GroupName;
    String RegionName;
    String AreaName;
    String TerritoryName;
    String SubTerritoryName;
    String MarketName;
    String MarketNameEnd;
    int CustomerMasterId;
    String CustomerName;

    public String getMarketNameEnd() {
        return MarketNameEnd;
    }

    public void setMarketNameEnd(String marketNameEnd) {
        MarketNameEnd = marketNameEnd;
    }

    String DoctorName;
    String ShiftText;
    String TourTypeName;
    int TPId;
    String TPName;
    int MonthValue;
    int YearValue;
    int DayValue;
    int EmpInfoId;
    String TourPlanDate;
    String Objective;

    public String getObjective() {
        return Objective;
    }

    public void setObjective(String objective) {
        Objective = objective;
    }

    boolean IsFinalSubmit;
    int SerialNo;
    List<ModelCustomer> aCustomerMasterList;
    String CreatedBy;
    String CreatedDate;
    String UpdateBy;
    String UpdateDate;
    String ApprovedBy;
    String ApprovedDate;
    String details;
    String ApprovalStatus;

    String IsMorning;
    String IsEvening;
    String IsStartTime;
    String Starttime;
    String IsEndtime;
    String Endtime;

    private String IsMarketVisit;
    private String IsOtherVisit;
    String VisitedWithEmpName;

    private int GroupIdEnd;
    private int RegionIdEnd;
    private int AreaIdEnd;

    public int getGroupIdEnd() {
        return GroupIdEnd;
    }

    public void setGroupIdEnd(int groupIdEnd) {
        GroupIdEnd = groupIdEnd;
    }

    public int getRegionIdEnd() {
        return RegionIdEnd;
    }

    public void setRegionIdEnd(int regionIdEnd) {
        RegionIdEnd = regionIdEnd;
    }

    public int getAreaIdEnd() {
        return AreaIdEnd;
    }

    public void setAreaIdEnd(int areaIdEnd) {
        AreaIdEnd = areaIdEnd;
    }

    public int getTerritoryIdEnd() {
        return TerritoryIdEnd;
    }

    public void setTerritoryIdEnd(int territoryIdEnd) {
        TerritoryIdEnd = territoryIdEnd;
    }

    public int getSubTerritoryIdEnd() {
        return SubTerritoryIdEnd;
    }

    public void setSubTerritoryIdEnd(int subTerritoryIdEnd) {
        SubTerritoryIdEnd = subTerritoryIdEnd;
    }

    private int TerritoryIdEnd;
    private int SubTerritoryIdEnd;


    public int getMarketIdEnd() {
        return MarketIdEnd;
    }

    public void setMarketIdEnd(int marketIdEnd) {
        MarketIdEnd = marketIdEnd;
    }

    public String getIsMarketVisit() {
        return IsMarketVisit;
    }

    public void setIsMarketVisit(String isMarketVisit) {
        IsMarketVisit = isMarketVisit;
    }

    public String getIsOtherVisit() {
        return IsOtherVisit;
    }

    public void setIsOtherVisit(String isOtherVisit) {
        IsOtherVisit = isOtherVisit;
    }

    public List<ModelMarket> getaVisitedMarketList() {
        return aVisitedMarketList;
    }

    public void setaVisitedMarketList(List<ModelMarket> aVisitedMarketList) {
        this.aVisitedMarketList = aVisitedMarketList;
    }

    int VisitedWithEmpInfoId;
    private List<ModelMarket> aVisitedMarketList;
    public String getVisitedWithEmpName() {
        return VisitedWithEmpName;
    }

    public void setVisitedWithEmpName(String visitedWithEmpName) {
        VisitedWithEmpName = visitedWithEmpName;
    }

    public int getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(int serialNo) {
        SerialNo = serialNo;
    }

    public List<ModelCustomer> getaCustomerMasterList() {
        return aCustomerMasterList;
    }

    public void setaCustomerMasterList(List<ModelCustomer> aCustomerMasterList) {
        this.aCustomerMasterList = aCustomerMasterList;
    }

    public int getDocTPDetailsId() {
        return DocTPDetailsId;
    }

    public int getMarketId() {
        return MarketId;
    }

    public int getCustomerMasterId() {
        return CustomerMasterId;
    }

    public void setCustomerMasterId(int customerMasterId) {
        CustomerMasterId = customerMasterId;
    }

    public void setMarketId(int marketId) {
        MarketId = marketId;
    }

    public void setDocTPDetailsId(int docTPDetailsId) {
        DocTPDetailsId = docTPDetailsId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public boolean isFinalSubmit() {
        return IsFinalSubmit;
    }

    public void setFinalSubmit(boolean finalSubmit) {
        IsFinalSubmit = finalSubmit;
    }

    public String getTourPlanDate() {
        return TourPlanDate;
    }

    public void setTourPlanDate(String tourPlanDate) {
        TourPlanDate = tourPlanDate;
    }

    public int getTourPlanId() {
        return TourPlanId;
    }

    public void setTourPlanId(int tourPlanId) {
        TourPlanId = tourPlanId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public boolean isMarketWise() {
        return IsMarketWise;
    }

    public void setMarketWise(boolean marketWise) {
        IsMarketWise = marketWise;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getShiftText() {
        return ShiftText;
    }

    public void setShiftText(String shiftText) {
        ShiftText = shiftText;
    }

    public String getTourTypeName() {
        return TourTypeName;
    }

    public void setTourTypeName(String tourTypeName) {
        TourTypeName = tourTypeName;
    }

    public String getTPName() {
        return TPName;
    }

    public void setTPName(String TPName) {
        this.TPName = TPName;
    }

    public int getMonthValue() {
        return MonthValue;
    }

    public void setMonthValue(int monthValue) {
        MonthValue = monthValue;
    }

    public int getYearValue() {
        return YearValue;
    }

    public void setYearValue(int yearValue) {
        YearValue = yearValue;
    }

    public int getDayValue() {
        return DayValue;
    }

    public void setDayValue(int dayValue) {
        DayValue = dayValue;
    }

    public int getSMId() {
        return SMId;
    }

    public void setSMId(int SMId) {
        this.SMId = SMId;
    }

    public int getTPId() {
        return TPId;
    }

    public void setTPId(int TPId) {
        this.TPId = TPId;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public int getVisitedWithEmpInfoId() {
        return VisitedWithEmpInfoId;
    }

    public void setVisitedWithEmpInfoId(int visitedWithEmpInfoId) {
        VisitedWithEmpInfoId = visitedWithEmpInfoId;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public void setApprovedDate(String approvedDate) {
        ApprovedDate = approvedDate;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public int getRegionId() {
        return RegionId;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
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

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getTerritoryName() {
        return TerritoryName;
    }

    public void setTerritoryName(String territoryName) {
        TerritoryName = territoryName;
    }

    public String getSubTerritoryName() {
        return SubTerritoryName;
    }

    public void setSubTerritoryName(String subTerritoryName) {
        SubTerritoryName = subTerritoryName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public String getIsMorning() {
        return IsMorning;
    }

    public void setIsMorning(String isMorning) {
        IsMorning = isMorning;
    }

    public String getIsEvening() {
        return IsEvening;
    }

    public void setIsEvening(String isEvening) {
        IsEvening = isEvening;
    }

    public String getIsStartTime() {
        return IsStartTime;
    }

    public void setIsStartTime(String isStartTime) {
        IsStartTime = isStartTime;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public String getIsEndtime() {
        return IsEndtime;
    }

    public void setIsEndtime(String isEndtime) {
        IsEndtime = isEndtime;
    }

    public String getEndtime() {
        return Endtime;
    }

    public void setEndtime(String endtime) {
        Endtime = endtime;
    }
}


