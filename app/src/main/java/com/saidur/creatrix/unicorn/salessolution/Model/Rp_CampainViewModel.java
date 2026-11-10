package com.saidur.creatrix.unicorn.salessolution.Model;

import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignDetails;

import java.util.List;

public class Rp_CampainViewModel {
    private int CampgainMasterId;
    private String CampaignCode;
    private String CampaignName;
    private String CampaignDesc;
    private String FromDate;
    private String Todate;
    private String Type;
    List<CampaignDetails> CampaingDetails;

    public int getCampgainMasterId() {
        return CampgainMasterId;
    }

    public void setCampgainMasterId(int campgainMasterId) {
        CampgainMasterId = campgainMasterId;
    }

    public List<CampaignDetails> getCampaingDetails() {
        return CampaingDetails;
    }

    public void setCampaingDetails(List<CampaignDetails> campaingDetails) {
        CampaingDetails = campaingDetails;
    }

    public String getCampaignCode() {
        return CampaignCode;
    }

    public void setCampaignCode(String campaignCode) {
        CampaignCode = campaignCode;
    }

    public String getCampaignName() {
        return CampaignName;
    }

    public void setCampaignName(String campaignName) {
        CampaignName = campaignName;
    }

    public String getCampaignDesc() {
        return CampaignDesc;
    }

    public void setCampaignDesc(String campaignDesc) {
        CampaignDesc = campaignDesc;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getTodate() {
        return Todate;
    }

    public void setTodate(String todate) {
        Todate = todate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
