package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model;


import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.Market;

import java.util.List;

public class TourPlanInfo  {

    private int TourPlanId;
    private int MarketId;
    private int MarketIdEnd;
    private int TPId;
    private String TourPlanDate;
    private int EmpInfoId;
    private int VisitedWithEmpInfoId;
    private int SerialNo;
    private String ApprovedBy;
    private String IsMorning;
    private String IsEvening;
    private String Starttime;
    private String Endtime;
    private String IsMarketVisit;
    private String IsOtherVisit;
    private String CreatedBy;
    private String CreatedDate;
    private String UpdateBy;
    private String UpdateDate;
    private String ApprovedDate;
    private List<ModelMarket> aVisitedMarketList;

    public int getTourPlanId() {
        return TourPlanId;
    }

    public void setTourPlanId(int tourPlanId) {
        TourPlanId = tourPlanId;
    }

    public int getMarketId() {
        return MarketId;
    }

    public void setMarketId(int marketId) {
        MarketId = marketId;
    }

    public int getMarketIdEnd() {
        return MarketIdEnd;
    }

    public void setMarketIdEnd(int marketIdEnd) {
        MarketIdEnd = marketIdEnd;
    }

    public int getTPId() {
        return TPId;
    }

    public void setTPId(int TPId) {
        this.TPId = TPId;
    }

    public String getTourPlanDate() {
        return TourPlanDate;
    }

    public void setTourPlanDate(String tourPlanDate) {
        TourPlanDate = tourPlanDate;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
    }

    public int getVisitedWithEmpInfoId() {
        return VisitedWithEmpInfoId;
    }

    public void setVisitedWithEmpInfoId(int visitedWithEmpInfoId) {
        VisitedWithEmpInfoId = visitedWithEmpInfoId;
    }

    public int getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(int serialNo) {
        SerialNo = serialNo;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
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

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public String getEndtime() {
        return Endtime;
    }

    public void setEndtime(String endtime) {
        Endtime = endtime;
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

    public String getCreatedBy() {
        return CreatedBy;
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

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public void setApprovedDate(String approvedDate) {
        ApprovedDate = approvedDate;
    }

    public List<ModelMarket> getaVisitedMarketList() {
        return aVisitedMarketList;
    }

    public void setaVisitedMarketList(List<ModelMarket> aVisitedMarketList) {
        this.aVisitedMarketList = aVisitedMarketList;
    }
}
