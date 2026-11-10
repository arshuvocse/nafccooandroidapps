package com.saidur.creatrix.unicorn.salessolution.Model.Order;

import java.util.List;

public class CampaignPostReq {

   List<CampOrderDetails> OrderDetails;
   List<CampaignMasters>campaignMasters;


    public List<CampOrderDetails> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<CampOrderDetails> orderDetails) {
        OrderDetails = orderDetails;
    }

    public List<CampaignMasters> getCampaignMasters() {
        return campaignMasters;
    }

    public void setCampaignMasters(List<CampaignMasters> campaignMasters) {
        this.campaignMasters = campaignMasters;
    }

    @Override
    public String toString() {
        return "CampaignPostReq{" +
                "OrderDetails=" + OrderDetails +
                ", campaignMasters=" + campaignMasters +
                '}';
    }
}
