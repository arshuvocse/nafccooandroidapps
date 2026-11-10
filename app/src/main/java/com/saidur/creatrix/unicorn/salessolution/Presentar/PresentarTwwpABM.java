package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITwwp;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITwwpABM;


public class PresentarTwwpABM implements ITwwpABM.Presenter {
    ITwwpABM.View view;
    Context context;
    DBCrudHelper dbCrudHelper;
    public PresentarTwwpABM(Context context, ITwwpABM.View view) {
        this.context = context;
        this.view = view;
        dbCrudHelper=new DBCrudHelper(context);
    }


    @Override
    public void getDeliveryDay() {
        try {
            view.viewDeliveryDay(dbCrudHelper.getDeliveryDayList_SQLite());

        } catch (Exception ex) {
        }
    }

    @Override
    public void getTourType() {
        try {
            view.viewTourType(dbCrudHelper.getStationTypeListFromSQLite());

        } catch (Exception ex) {
        }
    }

    @Override
    public void getWorkType() {
        try {
            view.viewWorkType(dbCrudHelper.getWorkTypeList_SQLite());
        } catch (Exception ex) {
        }
    }

    @Override
    public void getTransport() {
        try {
            view.viewTransport(dbCrudHelper.getTransportList_SQLite());
        } catch (Exception ex) {
        }
    }

    @Override
    public void getTeritorry() {
        try {
            view.viewTeritorry(dbCrudHelper.getTerritoryByIdList_SQLite(0));
        } catch (Exception ex) {
        }
    }

    @Override
    public void getSTeritorry(int id) {
        try {
            view.viewSTeritorry(dbCrudHelper.getSubTerritoryByIdList_SQLite(id));
        } catch (Exception ex) {
        }
    }
}
