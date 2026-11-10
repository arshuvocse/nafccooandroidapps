package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.content.Context;

import com.saidur.creatrix.unicorn.salessolution.Database.DBDDTUHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IDDT;


public class PresentarDDT implements IDDT.Presenter {
    IDDT.View view;
    Context context;
    DBDDTUHelper dbddtuHelper;
    public PresentarDDT(Context context, IDDT.View view) {
        this.context = context;
        this.view = view;
        dbddtuHelper=new DBDDTUHelper(context);
    }

    @Override
    public void getDivision() {
        try {
            view.DivisionList(dbddtuHelper.getDivList_SQLite());

        } catch (Exception ex) {
        }
    }

    @Override
    public void getDistrict(int divId) {
        try {
            view.DistrictList(dbddtuHelper.getDisByIdList_SQLite(divId));

        } catch (Exception ex) {
        }
    }

    @Override
    public void getThana(int disId) {
        try {
            view.ThanaList(dbddtuHelper.getThanaByIdList_SQLite(disId));

        } catch (Exception ex) {
        }
    }
}
