package com.saidur.creatrix.unicorn.salessolution.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.saidur.creatrix.unicorn.salessolution.Model.ModelDistrict;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDivision;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelThana;

import java.util.ArrayList;
import java.util.List;

public class DBDDTUHelper {
    DBMain dbHelperMain;
    Context context;
    ModelSession session;

    public DBDDTUHelper(Context context) {
        this.context = context;
        dbHelperMain = new DBMain(context);
    }

    @SuppressLint("Range")
    public List<ModelDivision> getDivList_SQLite() {

        ArrayList<ModelDivision> divList = new ArrayList<>();
        SQLiteDatabase database = dbHelperMain.getWritableDatabase();
        String oderMasterQuery = "Select * from tblDivisionInfo";

        try {
            Cursor cursor;
            cursor = database.rawQuery(oderMasterQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelDivision divInfo = new ModelDivision();
                    divInfo.setDivisionId(cursor.getInt(cursor.getColumnIndex("DivisionId")));
                    divInfo.setDivisionName(cursor.getString(cursor.getColumnIndex("DivisionName")));
                    divList.add(divInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("MIO", exception.toString());
            exception.printStackTrace();
        }
        return divList;
    }

   // @IntRange(from = 0)
    //int getColumnIndex;
    @SuppressLint("Range")
    public List<ModelDistrict> getDisByIdList_SQLite(int divid) {
        List<ModelDistrict> disList = new ArrayList<>();
        SQLiteDatabase database = dbHelperMain.getWritableDatabase();
        String oderMasterQuerynll = "Select * from tblDistrictInfo";
        String oderMasterQuery = "Select * from tblDistrictInfo Where DivisionId=" + divid + "";

        try {
            Cursor cursor;
            if (String.valueOf(divid).isEmpty()) {
                cursor = database.rawQuery(oderMasterQuerynll, null);
            } else {
                cursor = database.rawQuery(oderMasterQuery, null);
            }
            // cursor = database.rawQuery(oderMasterQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelDistrict disInfo = new ModelDistrict();
                    disInfo.setDistrictId(cursor.getInt(cursor.getColumnIndex("DistrictId")));
                    disInfo.setDistrictName(cursor.getString(cursor.getColumnIndex("DistrictName")));
                    disList.add(disInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("MIO", exception.toString());
            exception.printStackTrace();
        }
        return disList;
    }

    @SuppressLint("Range")
    public List<ModelThana> getThanaByIdList_SQLite(int disid) {
        List<ModelThana> thanaList = new ArrayList<>();
        SQLiteDatabase database = dbHelperMain.getWritableDatabase();
        String oderMasterQuerynll = "Select * from tblThanaInfo";
        String oderMasterQuery = "Select * from tblThanaInfo Where district_id=" + disid + "";

        try {
            Cursor cursor;
            if (String.valueOf(disid).isEmpty()) {
                cursor = database.rawQuery(oderMasterQuerynll, null);
            } else {
                cursor = database.rawQuery(oderMasterQuery, null);
            }
            // cursor = database.rawQuery(oderMasterQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelThana thanaInfo = new ModelThana();
                    thanaInfo.setThanaId(cursor.getInt(cursor.getColumnIndex("ThanaId")));
                    thanaInfo.setThanaName(cursor.getString(cursor.getColumnIndex("ThanaName")));
                    thanaList.add(thanaInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("MIO", exception.toString());
            exception.printStackTrace();
        }
        return thanaList;
    }
}
