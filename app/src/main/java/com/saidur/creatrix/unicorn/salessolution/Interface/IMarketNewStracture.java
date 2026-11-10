package com.saidur.creatrix.unicorn.salessolution.Interface;


import android.graphics.Region;


import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;

import java.util.List;

public interface IMarketNewStracture {
    interface  Presenter{
        void GetGroupLocal();
        void GetRegionLocalEdit(int grpid);
        void GetRegionLocal(int grpid);
        void GetRegionLocalEnd(int grpid);
        void GetRegionLocalEndEdit(int grpid);
        void GetAreaLocal(int regid);
        void GetAreaLocalEdit(int regid);
        void GetAreaLocalEnd(int regid);
        void GetAreaLocalEndEdit(int regid);
        void GetTeritoryLocal(int areaid);
        void GetTeritoryLocalEdit(int areaid);
        void GetTeritoryLocalEditEnd(int areaid);
        void GetTeritoryLocalEditEndEdit(int areaid);
        void GetTeritoryLocalEnd(int areaid);
        void GetSTeritoryLocal(int terid);
        void GetSTeritoryLocalEdit(int terid);
        void GetSTeritoryLocalEditEnd(int terid);
        void GetSTeritoryLocalEnd(int terid);
        void GetMarketLocal(int sterid);
        void GetMarketLocalEdit(int sterid);
        void GetMarketLocalEditEnd(int sterid);
        void GetMarketLocalEnd(int sterid);
        void GetMarketLocalEndEdit(int sterid);
    }
    interface View{
        void vGroup(List<ModelGroup> groupList);
        void vRegion(List<ModelRegion> regionList);
        void vRegionEdit(List<ModelRegion> regionList);
        void vRegionEnd(List<ModelRegion> regionList);
        void vRegionEndEdit(List<ModelRegion> regionList);
        void vArea(List<ModelArea> areaList);
        void vAreaEnd(List<ModelArea> areaList);
        void vAreaEndEdit(List<ModelArea> areaList);
        void vTeritory(List<ModelTeritorry> teritoryList);
        void vTeritoryEnd(List<ModelTeritorry> teritoryList);
        void vTeritoryEndEdit(List<ModelTeritorry> teritoryList);
        void vSTeritory(List<ModelSubTeritorry> steritoryList);
        void vSTeritoryEnd(List<ModelSubTeritorry> steritoryList);
        void vSTeritoryEndEdit(List<ModelSubTeritorry> steritoryList);
        void vMarket(List<ModelMarket> marketList);
        void vMarketEnd(List<ModelMarket> marketList);
        void vMarketEndEdit(List<ModelMarket> marketList);

    }

}
