package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketStracture;


public class MarketStructurePresenter implements IMarketStracture.Presenter {
    IMarketStracture.View view;
    Context context;
    DBCrudHelper dbCrudHelper;


    public MarketStructurePresenter(IMarketStracture.View view, Context context) {
        this.view = view;
        this.context = context;
        dbCrudHelper=new DBCrudHelper(context);
    }


    @Override
    public void GetGroupLocal() {
        try {
            view.vGroup(dbCrudHelper.getGroupByIdList_SQLite());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetRegionLocal(int grpid) {
        try {
            view.vRegion(dbCrudHelper.getRegionByIdList_SQLite(grpid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetAreaLocal(int regid) {
        try {
            view.vArea(dbCrudHelper.getAreaByIdList_SQLite(regid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetTeritoryLocal(int areaid) {
        try {
            view.vTeritory(dbCrudHelper.getTerritoryByIdList_SQLite(areaid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetSTeritoryLocal(int terid) {
        try {
            view.vSTeritory(dbCrudHelper.getSubTerritoryByIdList_SQLite(terid));
          //  view.vSTeritory(dbCrudHelper.getSubTerritoryByIdList_SQLite(terid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetMarketLocal(int sterid) {
        try {
            view.vMarket(dbCrudHelper.getMarketByIdList_SQLite(sterid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
