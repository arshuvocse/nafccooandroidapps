package com.saidur.creatrix.unicorn.salessolution.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelProviderType;

import java.util.ArrayList;

public class DBCustomerHelper {
    DBMain dbMain;
    Context context;
    //SessionManagement sessionManagement;

    public DBCustomerHelper(Context context) {
        this.context = context;
        dbMain = new DBMain(context);
    }

    @SuppressLint("Range")
    public ArrayList<ModelProviderType> getProviderTypeListForCustFromSQLite(int id, int fos) {
        // forCustomer
        ArrayList<ModelProviderType> typeList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String oderMasterQuerynll = "Select * from tblProvider_Type Where forCustomer =" + fos + "";
        String oderMasterQuery = "Select * from tblProvider_Type Where ProviderTypeId=" + id + "";
        try {
            Cursor cursor;
            if (id == 0 || String.valueOf(id).isEmpty()) {
                //  cursor = database.rawQuery(oderMasterQuerynll, null);
                cursor = database.rawQuery(oderMasterQuerynll, null);
            } else {
                cursor = database.rawQuery(oderMasterQuery, null);
            }
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelProviderType ddInfo = new ModelProviderType();
                    ddInfo.setProviderTypeId(cursor.getInt(cursor.getColumnIndex("ProviderTypeId")));
                    ddInfo.setProviderType(cursor.getString(cursor.getColumnIndex("ProviderType")));
                    typeList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("CampOrderDetails", exception.toString());
            exception.printStackTrace();
        }
        return typeList;
    }
}
