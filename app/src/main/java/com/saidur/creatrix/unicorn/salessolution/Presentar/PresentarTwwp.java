package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITwwp;


public class PresentarTwwp implements ITwwp.Presenter {
    ITwwp.View view;
    Context context;
    DBCrudHelper dbCrudHelper;
    public PresentarTwwp(Context context, ITwwp.View view) {
        this.context = context;
        this.view = view;
        dbCrudHelper=new DBCrudHelper(context);
    }

    @Override
    public void getDay() {
        try {
            view.viewDay(dbCrudHelper.getDayList_SQLite());

        } catch (Exception ex) {
        }
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
}
