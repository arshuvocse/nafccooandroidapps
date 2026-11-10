package com.saidur.creatrix.unicorn.salessolution.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model.Gift;

import java.util.ArrayList;
import java.util.List;

public class DBProductSQLiteHelper {
    Context context;
    DBMain dbHelper;
    public DBProductSQLiteHelper(Context context) {
        this.context = context;
        dbHelper = new DBMain(context);
    }

    public boolean isProductTblExist() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM sqlite_master WHERE name ='tbl_ProductInfo' and type='table';", null);
        int count = cursor.getCount();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void CreateUserTable() {
        try {
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            String tblProductInfo = "Create Table if not exists tbl_ProductInfo (ProductId Integer primary key, " +
                    "ProductName varchar(150),ProductCode varchar(50),ProductDes varchar(150),"+
                    "PackSize varchar(150),UnitPrice varchar(50),VatPercentage varchar(50),VatAmountPerunit varchar(50),QuotedPrice varchar(50),CustomerMasterId Integer)";


            database.execSQL(tblProductInfo);
//            Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Some error occured. Please contact with authority", Toast.LENGTH_SHORT).show();
        }
    }
    public void InsertIntoProductTable(List<ModelProduct> aProduct) {

     /*   try{
            int ProductId = 0;
            String ProductCode = "";
            String ProductName = "";
            String ProductDes = "";
            String PackSize = "";
            String UnitPrice = "";
            String VatPercentage = "";
            String VatAmountPerunit = "";

            String  QuotedPrice= "";
            int CustomerMasterId = 0;

            if(!isProductTblExist()){
                CreateUserTable();
            }
            if(CheckDataExistinTable()){
                _deleteAllProductsRecords();
            }

            SQLiteDatabase database = dbHelper.getWritableDatabase();
                for (int i=0;i<aProduct.size();i++){
                    Product aInfo = aProduct.get(i);
                    ProductId =aInfo.getProductId();
                    ProductName =aInfo.getProductName();
                    ProductCode =aInfo.getProductCode();
                    ProductDes =aInfo.getProductDes();
                    PackSize =aInfo.getPackSize();
                    UnitPrice = aInfo.getUnitPrice().toString();
                    VatPercentage =aInfo.getVatPercentage().toString();
                    VatAmountPerunit =aInfo.getVatAmountPerunit().toString();
                    QuotedPrice =aInfo.getQuotedPrice().toString();
                    CustomerMasterId =aInfo.getCustomerMasterId();


                    String insertQuery = "Insert into tbl_ProductInfo(ProductId,ProductName,ProductCode,ProductDes,PackSize,UnitPrice,VatPercentage,VatAmountPerunit,QuotedPrice,CustomerMasterId) " +
                            "values('"+ProductId+"','"+ProductName+"','"+ProductCode+"','"+ProductDes+"','"+PackSize+"','"+UnitPrice+"','"+VatPercentage+"','"+VatAmountPerunit+"','"+QuotedPrice+"','"+CustomerMasterId+"')";

                 database.execSQL(insertQuery);
                }




        }catch (Exception exception){
            Log.e("DBEX",exception.toString());
        }*/


        try{
            String tableName = "tbl_ProductInfo";
            _deleteAllRecordsFromaTable(tableName);

            SQLiteDatabase databases = dbHelper.getWritableDatabase();
            for (int i=0;i<aProduct.size();i++){
                ModelProduct aInfo = aProduct.get(i);
                String insertQuery = "Insert into tbl_ProductInfo(ProductId,ProductName,ProductCode,ProductDes,PackSize,UnitPrice,VatPercentage,VatAmountPerunit,QuotedPrice,CustomerMasterId) " +
                        "values('"+aInfo.getProductId()+"','"+aInfo.getProductName().replace("'","''")+"','"+aInfo.getProductCode()+"','"+aInfo.getProductDes().replace("'","''")+"','"+aInfo.getPackSize()+"','"+aInfo.getUnitPrice()+"','"+aInfo.getVatPercentage()+"','"+aInfo.getVatAmountPerunit()+"','"+aInfo.getQuotedPrice()+"','"+aInfo.getCustomerMasterId()+"')";
                databases.execSQL(insertQuery);
            }
        }catch (Exception exception){
            Log.e("DBEX",exception.toString());
        }


    }


    public void _deleteAllProductsRecords() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            String query  = "delete from tbl_ProductInfo;";
            database.execSQL(query);
        }catch (Exception ex){
        }
    }


    public boolean CheckDataExistinTable() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_ProductInfo",null);
        int count = cursor.getCount();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    public void  _deleteAllRecordsFromaTable(String tableName) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            String query  = "delete from '"+ tableName +"';";
            database.execSQL(query);

        }catch (Exception ex){
            Log.e("recordDelError", ex.toString());
        }
    }


    public boolean CheckDataInTable(String tableName) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM '"+ tableName +"'",null);
        int count = cursor.getCount();
        if (count > 0) {
            return true;
        } else {
            return false;
        }


    }

    @SuppressLint("Range")
    public ArrayList<ModelProduct> getProductFromDB() {
        ArrayList<ModelProduct> productList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sqlQuery = "Select * from tbl_ProductInfo";
        Cursor cursor;
        cursor = database.rawQuery(sqlQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ModelProduct aInfo = new ModelProduct();
                aInfo.setProductId(cursor.getInt(cursor.getColumnIndex("ProductId")));
                aInfo.setProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
                aInfo.setProductCode(cursor.getString(cursor.getColumnIndex("ProductCode")));

                productList.add(aInfo);
            }
        }
        return productList;
    }
    @SuppressLint("Range")
    public ArrayList<Gift> getProductGiftFromDB() {
        ArrayList<Gift> giftList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sqlQuery = "Select * from tbl_ProductGiftInfo";
        Cursor cursor;
        cursor = database.rawQuery(sqlQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Gift aInfo = new Gift();
                aInfo.setProductId(cursor.getInt(cursor.getColumnIndex("ProductId")));
                aInfo.setProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
                aInfo.setProductCode(cursor.getString(cursor.getColumnIndex("ProductCode")));

                giftList.add(aInfo);


            }
        }
        return giftList;
    }

}
