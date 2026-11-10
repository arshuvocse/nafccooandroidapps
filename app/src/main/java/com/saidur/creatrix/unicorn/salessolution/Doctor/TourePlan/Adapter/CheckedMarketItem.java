package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter;


import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.Market;

import java.util.List;

public interface CheckedMarketItem {
    void ckdItemMarketName(List<ModelMarket> st, int Pos);
    void unckdItemMarketName(List<ModelMarket> st,int Pos);
}
