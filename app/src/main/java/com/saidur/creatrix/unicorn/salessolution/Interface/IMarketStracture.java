package com.saidur.creatrix.unicorn.salessolution.Interface;

import android.content.Context;


import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;

import java.util.List;

public interface IMarketStracture {
    interface  Presenter{
        void GetGroupLocal();
        void GetRegionLocal(int grpid);
        void GetAreaLocal(int regid);
        void GetTeritoryLocal(int areaid);
        void GetSTeritoryLocal(int terid);
        void GetMarketLocal(int sterid);
    }
    interface View{
        void vGroup(List<ModelGroup> groupList);
        void vRegion(List<ModelRegion> regionList);
        void vArea(List<ModelArea> areaList);
        void vTeritory(List<ModelTeritorry> teritoryList);
        void vSTeritory(List<ModelSubTeritorry> steritoryList);
        void vMarket(List<ModelMarket> marketList);

    }

}
