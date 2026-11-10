package com.saidur.creatrix.unicorn.salessolution.Presentar;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saidur.creatrix.unicorn.salessolution.Database.DBMain;
import com.saidur.creatrix.unicorn.salessolution.Database.DBProductSQLiteHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelCompanyUnit;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiOrderProcess;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiProduct;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterProduct implements IProduct.Presenter {
    ProgressDialog progressDoalog;
    IProduct.View view;
    DBProductSQLiteHelper productSQLiteHelper;
    DBMain dbHelper;
    Context context;


    public PresenterProduct(IProduct.View view, Context context) {
        this.view = view;
        this.productSQLiteHelper = new DBProductSQLiteHelper(context);
        this.dbHelper = new DBMain(context);
        this.context = context;
    }
    @Override
    public void getProductsFromServer(int empId) {
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Synchronizing....");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try{
            ApiProduct service = RetrofitClientInstance.getRetrofitInstance().create(ApiProduct.class);
            Call<List<ModelProduct>> call = service.GetProductList(empId);
            call.enqueue(new Callback<List<ModelProduct>>() {
                @Override
                public void onResponse(Call<List<ModelProduct>> call, Response<List<ModelProduct>> response) {
                    progressDoalog.dismiss();
                    progressDoalog.dismiss();
                    InsertIntoProductTable(response.body());
                }
                @Override
                public void onFailure(Call<List<ModelProduct>> call, Throwable t) {
                    progressDoalog.dismiss();
                    if(t instanceof SocketTimeoutException){
                        view.OnError("Slow Network detected");
                    }else{
                        view.OnError("Some Error occured");

                    }


                }
            });

        }catch (Exception ex){
            view.OnError("Some Error occured");
            progressDoalog.dismiss();
        }

    }

    @Override
    public void getCompanyUnitFromServer(int empId) {
        try{
            ApiOrderProcess service = RetrofitClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<List<ModelCompanyUnit>> call = service.GetCompanyUnit(empId);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<List<ModelCompanyUnit>>() {
                @Override
                public void onResponse(Call<List<ModelCompanyUnit>> call, Response<List<ModelCompanyUnit>> response) {
                    InsertIntoCompanyUnitTable(response.body());
                }
                @Override
                public void onFailure(Call<List<ModelCompanyUnit>> call, Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        view.OnError("Slow Network detected");
                    }else{
                        view.OnError("Some Error occured - ComUnit");
                    }
                }
            });
        }catch (Exception ex){
            view.OnError("Some Error occured - ComUnit");
        }

    }

    @Override
    public void InsertIntoProductTable(List<ModelProduct> aProduct) {
        productSQLiteHelper.InsertIntoProductTable(aProduct);
    }

    public void InsertIntoCompanyUnitTable(List<ModelCompanyUnit> mList){
        try{
            String tableName = "tblCompanyUnit";
            int ComUnitId = 0;
            String ComUnitName = "";
            if(productSQLiteHelper.CheckDataInTable(tableName)){
                productSQLiteHelper._deleteAllRecordsFromaTable(tableName);
            }
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            for (int i=0;i<mList.size();i++){
                ModelCompanyUnit aInfo = mList.get(i);
                ComUnitId =aInfo.getComUnitId();
                ComUnitName =aInfo.getComUnitName();
                String insertQuery = "Insert into tblCompanyUnit(ComUnitId,ComUnitName) " +
                        "values('"+ComUnitId+"','"+ComUnitName+"')";

                database.execSQL(insertQuery);
            }
        }catch (Exception exception){
            Log.e("DBEX",exception.toString());
        }
    }

    @SuppressLint("Range")
    @Override
    public void getProductFromDB(int empId) {
        List<ModelProduct> products = new ArrayList<>();
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
                aInfo.setProductDes(cursor.getString(cursor.getColumnIndex("ProductDes")));
                aInfo.setPackSize(cursor.getString(cursor.getColumnIndex("PackSize")));
                aInfo.setUnitPrice(cursor.getDouble(cursor.getColumnIndex("UnitPrice")));
                aInfo.setVatAmountPerunit(cursor.getDouble(cursor.getColumnIndex("VatAmountPerunit")));
                aInfo.setVatPercentage(cursor.getDouble(cursor.getColumnIndex("VatPercentage")));
                aInfo.setQuotedPrice(cursor.getDouble(cursor.getColumnIndex("QuotedPrice")));
                aInfo.setCustomerMasterId(cursor.getInt(cursor.getColumnIndex("CustomerMasterId")));

                products.add(aInfo);
            }
        }
        view.onProductsGet(products);
    }

    @SuppressLint("Range")
    @Override
    public ArrayList<ModelCompanyUnit> getCompanyUnitFromDB() {
        ArrayList<ModelCompanyUnit> aList = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ModelCompanyUnit mCompanyUnit = new ModelCompanyUnit();
        mCompanyUnit.setComUnitId(0);
        mCompanyUnit.setComUnitName("Select Company Unit");
        aList.add(mCompanyUnit);

        String sqlQuery = "Select * from tblCompanyUnit";
        Cursor cursor;
        cursor = database.rawQuery(sqlQuery, null);
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                ModelCompanyUnit aInfo = new ModelCompanyUnit();
                aInfo.setComUnitId(cursor.getInt(cursor.getColumnIndex("ComUnitId")));
                aInfo.setComUnitName(cursor.getString(cursor.getColumnIndex("ComUnitName")));
                aList.add(aInfo);
            }
        }
        return aList;
    }


    @Override
    public void getRegularProducts(int empId) {
        try{
            ApiProduct service = RetrofitClientInstance.getRetrofitInstance().create(ApiProduct.class);
            Call<List<ModelProduct>> call = service.GetProductList(empId);
            call.enqueue(new Callback<List<ModelProduct>>() {
                @Override
                public void onResponse(Call<List<ModelProduct>> call, Response<List<ModelProduct>> response) {
                    view.onProductsGet(response.body());

                }
                @Override
                public void onFailure(Call<List<ModelProduct>> call, Throwable t) {

                    if(t instanceof SocketTimeoutException){
                        view.OnError("Slow Network detected");
                    }else{
                        view.OnError("Some Error occured");

                    }


                }
            });

        }catch (Exception ex){
            view.OnError("Some Error occured");

        }

    }


}
