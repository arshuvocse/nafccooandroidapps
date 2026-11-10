package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketNewStracture;


public class MarketNewStructurePresenter implements IMarketNewStracture.Presenter {
    IMarketNewStracture.View view;
    Context context;
    DBCrudHelper dbCrudHelper;


    public MarketNewStructurePresenter(IMarketNewStracture.View view, Context context) {
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
    public void GetRegionLocalEdit(int rgnId) {
        try {
            view.vRegionEdit(dbCrudHelper.getRegionByIdList_SQLiteEdit(rgnId));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetRegionLocalEnd(int grpid) {
        try {
            view.vRegionEnd(dbCrudHelper.getRegionByIdList_SQLite(grpid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
@Override
    public void GetRegionLocalEndEdit(int grpid) {
        try {
            view.vRegionEndEdit(dbCrudHelper.getRegionByIdList_SQLiteEdit(grpid));
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
    public void GetAreaLocalEdit(int regid) {
        try {
            view.vArea(dbCrudHelper.getAreaByIdList_SQLiteEdit(regid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
@Override
    public void GetAreaLocalEnd(int regid) {
        try {
            view.vAreaEnd(dbCrudHelper.getAreaByIdList_SQLite(regid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

@Override
    public void GetAreaLocalEndEdit(int regid) {
        try {
            view.vAreaEndEdit(dbCrudHelper.getAreaByIdList_SQLiteEdit(regid));
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
    public void GetTeritoryLocalEdit(int areaid) {
        try {
            view.vTeritory(dbCrudHelper.getTerritoryByIdList_SQLiteEdit(areaid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetTeritoryLocalEditEnd(int areaid) {
        try {
            view.vTeritory(dbCrudHelper.getTerritoryByIdList_SQLiteEdit(areaid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetTeritoryLocalEditEndEdit(int areaid) {
        try {
            view.vTeritoryEndEdit(dbCrudHelper.getTerritoryByIdList_SQLiteEdit(areaid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetTeritoryLocalEnd(int areaid) {
        try {
            view.vTeritoryEnd(dbCrudHelper.getTerritoryByIdList_SQLite(areaid));
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
    public void GetSTeritoryLocalEdit(int terid) {
        try {
            view.vSTeritory(dbCrudHelper.getSubTerritoryByIdList_SQLiteEdit(terid));
          //  view.vSTeritory(dbCrudHelper.getSubTerritoryByIdList_SQLite(terid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetSTeritoryLocalEditEnd(int terid) {
        try {
            view.vSTeritoryEndEdit(dbCrudHelper.getSubTerritoryByIdList_SQLiteEdit(terid));
          //  view.vSTeritory(dbCrudHelper.getSubTerritoryByIdList_SQLite(terid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetSTeritoryLocalEnd(int terid) {
        try {
            view.vSTeritoryEnd(dbCrudHelper.getSubTerritoryByIdList_SQLite(terid));
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

    @Override
    public void GetMarketLocalEdit(int sterid) {
        try {
            view.vMarket(dbCrudHelper.getMarketByIdList_SQLiteEdit(sterid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void GetMarketLocalEditEnd(int sterid) {
        try {
            view.vMarket(dbCrudHelper.getMarketByIdList_SQLiteEdit(sterid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetMarketLocalEnd(int sterid) {
        try {
            view.vMarketEnd(dbCrudHelper.getMarketByIdList_SQLite(sterid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void GetMarketLocalEndEdit(int sterid) {
        try {
            view.vMarketEndEdit(dbCrudHelper.getMarketByIdList_SQLiteEdit(sterid));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
