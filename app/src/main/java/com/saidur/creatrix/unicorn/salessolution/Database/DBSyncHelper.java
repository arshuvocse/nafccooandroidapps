package com.saidur.creatrix.unicorn.salessolution.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelProviderType;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorCategory;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorContact;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDegree;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDesignation;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSpeciality;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelPracticeShift;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelASM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMBE;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelMIO;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelNSM;
import com.saidur.creatrix.unicorn.salessolution.Model.FieldForce.ModelRSM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDistrict;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDivision;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelGender;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPriority;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPromo;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelThana;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.QuotedPrice;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPurposeViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourTypeViewModel;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDeliveryDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTransport;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWorkType;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model.Gift;

import java.util.List;

public class DBSyncHelper {
    DBMain dbMain;
    Context context;
    DBCrudHelper dbCrudHelper;
    //SessionManagement sessionManagement;

    public DBSyncHelper(Context context) {
        this.context = context;
        dbMain = new DBMain(context);
        dbCrudHelper = new DBCrudHelper(context);
    }

    //TODO:Insert Master DATA
    public boolean InsertGroup(List<ModelGroup> gList) {
        boolean isTrue = false;
        String tableName = "tbl_Group";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelGroup m : gList) {
            ContentValues cv = new ContentValues();
            cv.put("GroupId", m.getGroupId());
            cv.put("GroupName", m.getGroupName().replace("'", "''"));
            database.insert(tableName, null, cv);
            isTrue = true;
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }

    public boolean InsertRegion(List<ModelRegion> rList) {
        boolean isTrue = false;
        String tableName = "tblRegion";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelRegion m : rList) {
            ContentValues cv = new ContentValues();
            cv.put("RegionId", m.getRegionId());
            cv.put("RegionName", m.getRegionName().replace("'", "''"));
            cv.put("GroupId", m.getGroupId());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();

        return isTrue;
    }
    public boolean InsertArea(List<ModelArea> arList) {
        boolean isTrue = false;
        String tableName = "tblArea";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelArea m : arList) {
            ContentValues cv = new ContentValues();
            cv.put("AreaId", m.getAreaId());
            cv.put("AreaName", m.getAreaName().replace("'", "''"));
            cv.put("RegionId", m.getRegionId());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertTeritorry(List<ModelTeritorry> tList) {
        boolean isTrue = false;
        String tableName = "tblTerritory";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelTeritorry m : tList) {
            ContentValues cv = new ContentValues();
            cv.put("TerritoryId", m.getTerritoryId());
            cv.put("TerritoryName", m.getTerritoryName().replace("'", "''"));
            cv.put("AreaId", m.getAreaId());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();

        /*try {

            String tableName = "tblTerritory";
            _deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbHelperMain.getWritableDatabase();
            for (int i = 0; i < tList.size(); i++) {
                Teritorry tInfo = tList.get(i);
                String insertQuery = "Insert into tblTerritory(TerritoryId,TerritoryName,AreaId) " +
                        //  "values('" + tInfo.getTerritoryId() + "','" + tInfo.getTerritoryName() + "','" + tInfo.getAreaId() + "')";
                        "values('" + tInfo.getTerritoryId() + "','" + tInfo.getTerritoryName().replace("'", "''") + "','" + tInfo.getAreaId() + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            // isTrue = false;
            Log.e("DBEX", exception.toString());

        }*/
        return isTrue;
    }
    public boolean InsertSubTeritorry(List<ModelSubTeritorry> stList) {
        boolean isTrue = false;
        String tableName = "tblSubTerritory";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelSubTeritorry m : stList) {
            ContentValues cv = new ContentValues();
            cv.put("SubTerritoryId", m.getSubTerritoryId());
            cv.put("SubTerritoryName", m.getSubTerritoryName().replace("'", "''"));
            cv.put("TerritoryId", m.getTerritoryId());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertDivision(List<ModelDivision> mList) {
        boolean isTrue = false;
        String tableName = "tblDivisionInfo";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelDivision m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("DivisionId", m.getDivisionId());
            cv.put("DivisionName", m.getDivisionName().replace("'", "''"));
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertDistrict(List<ModelDistrict> mList) {
        boolean isTrue = false;
        String tableName = "tblDistrictInfo";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelDistrict m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("DistrictId", m.getDistrictId());
            cv.put("DistrictName", m.getDistrictName().replace("'", "''"));
            cv.put("DivisionId", m.getDivisionId());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertThana(List<ModelThana> mList) {
        boolean isTrue = false;
        String tableName = "tblThanaInfo";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelThana m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("ThanaId", m.getThanaId());
            cv.put("ThanaName", m.getThanaName().replace("'", "''"));
            cv.put("district_id", m.getDistrict_id());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertGender(List<ModelGender> mList) {
        boolean isTrue = false;
        String tableName = "tblGender";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelGender m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("GenderId", m.getGenderId());
            cv.put("GenderName", m.getGenderName().replace("'", "''"));
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertMarket(List<ModelMarket> mList) {
        boolean isTrue = false;
        String tableName = "tblMarket";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelMarket m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("MarketId", m.getMarketId());
            cv.put("MarketName", m.getMarketName().replace("'", "''"));
            cv.put("SubTerritoryId", m.getSubTerritoryId());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    //TODO:Insert MasterEmp
    public boolean InsertNSMInfo(List<ModelNSM> nList) {
        boolean isTrue = true;
        try {

            String tableName = "tblNSMInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < nList.size(); i++) {
                ModelNSM nInfo = nList.get(i);
                String insertQuery = "Insert into tblNSMInfo(NSMEmpId,NSMId,EmpMasterCode,EmpName,GroupId) " +
                        "values('" + nInfo.getNSMEmpId() + "','" + nInfo.getNSMId() + "','" + nInfo.getEmpMasterCode() + "','" + nInfo.getEmpName() + "','" + nInfo.getGroupId() + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            // isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertRSMInfo(List<ModelRSM> rList) {
        boolean isTrue = true;
        try {

            String tableName = "tblRSMInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < rList.size(); i++) {
                ModelRSM nInfo = rList.get(i);
                String insertQuery = "Insert into tblRSMInfo(RSMEmpId,RSMId,EmpMasterCode,EmpName,RegionId) " +
                        "values('" + nInfo.getRSMEmpId() + "','" + nInfo.getRSMId() + "','" + nInfo.getEmpMasterCode() + "','" + nInfo.getEmpName().replace("'", "''") + "','" + nInfo.getRegionId() + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            // isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertASMInfo(List<ModelASM> aList) {
        boolean isTrue = true;
        try {

            String tableName = "tblASMInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                ModelASM nInfo = aList.get(i);
                String insertQuery = "Insert into tblASMInfo(ASMEmpId,ASMId,EmpMasterCode,EmpName,AreaId) " +
                        "values('" + nInfo.getASMEmpId() + "','" + nInfo.getASMId() + "','" + nInfo.getEmpMasterCode() + "','" + nInfo.getEmpName() + "','" + nInfo.getAreaId() + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            // isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertMIOInfo(List<ModelMIO> mList) {
        boolean isTrue = true;
        try {
            String tableName = "tblMIOInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < mList.size(); i++) {
                ModelMIO nInfo = mList.get(i);
                String insertQuery = "Insert into tblMIOInfo(MIOEmpId,MIOId,EmpMasterCode,EmpName,TerritoryId) " +
                        "values('" + nInfo.getMIOEmpId() + "','" + nInfo.getMIOId() + "','" + nInfo.getEmpMasterCode() + "','" + nInfo.getEmpName() + "','" + nInfo.getTerritoryId() + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            // isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertMBEInfo(List<ModelMBE> mList) {
        boolean isTrue = true;
        try {
            String tableName = "tblMBEInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < mList.size(); i++) {
                ModelMBE nInfo = mList.get(i);
                String insertQuery = "Insert into tblMBEInfo(MBEInfoId,MBEEmpId,EmpMasterCode,EmpName,SubTerritoryId) " +
                        "values('" + nInfo.getMBEInfoId() + "','" + nInfo.getMBEEmpId() + "','" + nInfo.getEmpMasterCode() + "','" + nInfo.getEmpName() + "','" + nInfo.getSubTerritoryId() + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            // isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    //Initial Sync
    public boolean Insert_InitTable(String today, String todaytime) {
        try {
            String tableName = "tblInitTable";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();

            String insertQuery = "Insert into tblInitTable(IsFirstSyncDone,Date,Time) " +
                    "values(1,'" + today + "','" + todaytime + "')";
            database.execSQL(insertQuery);
            database.close();
        } catch (Exception exception) {
            Log.e("DBEX", exception.toString());
        }
        return true;
    }
    public boolean InsertCustomerList(List<ModelCustomer> aList) {
        boolean isTrue = false;
        try {
            String tableName = "tblCustomerInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);

            SQLiteDatabase database = dbMain.getWritableDatabase();
            String CustomerName = "";
            String CustomerAddress = "";
            String CustomerType = "";
            String MarketName = "";
            for (int i = 0; i < aList.size(); i++) {
                ModelCustomer aInfo = aList.get(i);
                try {
                    if (aInfo.getCustomerName().equals("")) {
                        CustomerName = "";
                    } else {
                        CustomerName = aInfo.getCustomerName().replace("'", "''");
                    }
                } catch (Exception exception) {
                    //exception.printStackTrace();
                }
                try {
                    if (aInfo.getAddress().equals("")) {
                        CustomerAddress = "";
                    } else {
                        CustomerAddress = aInfo.getAddress().replace("'", "''");
                    }
                } catch (Exception exception) {
                    //exception.printStackTrace();
                }
                try {
                    if (aInfo.getCustomerType().equals("")) {
                        CustomerType = "";
                    } else {
                        CustomerType = aInfo.getCustomerType().replace("'", "''");
                    }
                } catch (Exception exception) {
                    //exception.printStackTrace();
                }
                try {
                    if (aInfo.getMarketName().equals("")) {
                        MarketName = "";
                    } else {
                        MarketName = aInfo.getMarketName().replace("'", "''");
                    }
                } catch (Exception exception) {
                    //exception.printStackTrace();
                }

                String insertQuery = "Insert into tblCustomerInfo(CustomerMasterId,CustomerName,CustomerCode,CustomerAdress,CustomerType," +
                        "CustomerCell,CustomerBalance,CustomerCreditlimit,Market,GroupId,RegionId,AreaId,TerritoryId,SubTerritoryId,MarketId,Note,CustomerCheck) " +
                        "values('" + aInfo.getCustomerMasterId() + "','" + CustomerName + "','" + aInfo.getCustomerCode() + "','" + CustomerAddress + "'," +
                        "'" + CustomerType + "','" + aInfo.getCellNo() + "','" + aInfo.getDueAmount() + "','" + aInfo.getCreditLimit() + "','" + MarketName + "','" + aInfo.getGroupId() + "','" + aInfo.getRegionId() + "','" + aInfo.getAreaId() + "','" + aInfo.getTerritoryId() + "','" + aInfo.getSubTerritoryId() + "','" + aInfo.getMarketId() + "','" + aInfo.getNote() + "','" + aInfo.getCustomerCheck() + "')";
                database.execSQL(insertQuery);

            }
            isTrue = true;
            database.close();
        } catch (Exception exception) {
            Log.e("DBEX", exception.toString());
        }
        return isTrue;
    }

    public boolean InsertCustomerStation(List<ModelStationType> aList) {
        boolean isTrue = false;
        try {

            String tableName = "tblCustomer_Station";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                ModelStationType aInfo = aList.get(i);
                String insertQuery = "Insert into tblCustomer_Station(StationTypeId,StationTypeName) " +
                        "values('" + aInfo.getStationTypeId() + "','" + aInfo.getStationTypeName().replace("'", "''") + "')";
                database.execSQL(insertQuery);
                isTrue = true;
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertProducts(List<ModelProduct> aList) {
        boolean isTrue = true;
        try {
            String tableName = "tbl_ProductInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);

            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                ModelProduct aInfo = aList.get(i);
                String insertQuery = "Insert into tbl_ProductInfo(ProductId,ProductName,ProductCode,ProductDes,PackSize,UnitPrice,QuotedPrice,VatPercentage,VatAmountPerunit,CustomerMasterId) " +
                        "values('" + aInfo.getProductId() + "','" + aInfo.getProductName().replace("'", "''") + "','" + aInfo.getProductCode() + "','" + aInfo.getProductDes().replace("'", "''") + "','" + aInfo.getPackSize() + "','" + aInfo.getUnitPrice() + "','" + aInfo.getQuotedPrice() + "','" + aInfo.getVatPercentage() + "','" + aInfo.getVatAmountPerunit() + "','" + aInfo.getCustomerMasterId() + "')";
                database.execSQL(insertQuery);
            }
        } catch (Exception exception) {
            Log.e("DBEX", exception.toString());
        }
        return isTrue;
    }
    public boolean InsertQuotedPriceInfo_SQLite(List<QuotedPrice> mList) {
        boolean isTrue = false;
        try {
            String tableName = "tblQuotedPrice";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < mList.size(); i++) {
                QuotedPrice aInfo = mList.get(i);
                String insertQuery = "Insert into tblQuotedPrice(description,quotedPriceDetailId,policy,customerMasterId,activeFromDate,activeToDate,productId,unitPrice,vat) " +
                        "values('" + aInfo.getDescription() + "','" + aInfo.getQuotedPriceDetailId() + "','" + aInfo.getPolicy() + "','" + aInfo.getCustomerMasterId() + "','" + aInfo.getActiveFromDate() + "','" + aInfo.getActiveToDate() + "'," +
                        "'" + aInfo.getProductId() + "','" + aInfo.getUnitPrice() + "','" + aInfo.getVat() + "')";
                database.execSQL(insertQuery);
                /*if(i==1143)
                {
                    String data="gt";
                    break;
                }*/
                //isTrue = true;
            }
            isTrue = true;
            database.close();
        } catch (Exception exception) {

            Log.e("DBEX", exception.getLocalizedMessage().toString());
            isTrue = false;
        }
        return isTrue;
    }
    public boolean InsertProviderType(List<ModelProviderType> aList) {
        boolean isTrue = false;
        try {

            String tableName = "tblProvider_Type";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                ModelProviderType aInfo = aList.get(i);
                String insertQuery = "Insert into tblProvider_Type(ProviderTypeId,ProviderType,forCustomer,forDoctor) " +
                        "values('" + aInfo.getProviderTypeId() + "','" + aInfo.getProviderType().replace("'", "''") + "','" + aInfo.getForCustomer() + "','" + aInfo.getForDoctor() + "')";
                database.execSQL(insertQuery);
                isTrue = true;
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }

    //TODO:Doctor section Insert
    public boolean InsertDoctorList(List<DoctorListViewModel> aList) {
        boolean isTrue = false;
        try {

            dbCrudHelper._deleteAllRecordsFromaTable("tblDoctorBrand");
            dbCrudHelper._deleteAllRecordsFromaTable("tblDoctorInfo");

            SQLiteDatabase database = dbMain.getWritableDatabase();
            String Address = "";
            String MarketName = "";


            for (int i = 0; i < aList.size(); i++) {
                DoctorListViewModel aInfo = aList.get(i);
                try {
                    if (aInfo.getDoctorAddress().equals("")) {
                        Address = "";
                    } else {
                        Address = aInfo.getDoctorAddress().replace("'", "''");
                    }

                    if (aInfo.getMarketName().equals("")) {
                        MarketName = "";
                    } else {
                        MarketName = aInfo.getMarketName().replace("'", "''");
                    }

                } catch (Exception exception) {

                    //exception.printStackTrace();
                }
                String insertQuery = "Insert into tblDoctorInfo(DoctorId,DoctorCode,DoctorName,Gender,DocContact,DoctorPatientStatus,DoctorAddress,GroupId,RegionId,AreaId,TerritoryId,SubTerritoryId,MarketId,MarketName)" +
                        "values('" + aInfo.getDoctorId() + "','" + aInfo.getDoctorCode() + "','" + aInfo.getDoctorName().replace("'", "''") + "'," +
                        "'" + aInfo.getGender() + "','" + aInfo.getDocContact() + "','" + aInfo.getDoctorPatientStatus() + "','" + Address + "','" + aInfo.getGroupId() + "','" + aInfo.getRegionId() + "','" + aInfo.getAreaId() + "','" + aInfo.getTerritoryId() + "','" + aInfo.getSubTerritoryId()
                        + "','" + aInfo.getMarketId() + "','" + MarketName + "')";
                database.execSQL(insertQuery);
                String brandname = "";
                for (int j = 0; j < aList.get(i).getDoctorBrand().size(); j++) {
                    DoctorBrand dbi = aList.get(i).getDoctorBrand().get(j);
                    try {
                        if (dbi.getBrandName().equals("")) {
                            brandname = dbi.getBrandName();
                        } else {
                            brandname = dbi.getBrandName().replace("'", "''");
                        }
                    } catch (Exception exception) {

                    }
                    String insertBrand = "Insert into tblDoctorBrand(BrandId,BrandName,DoctorId) " +
                            "values('" + dbi.getBrandId() + "','" + brandname/*dbi.getBrandName().replace("'","''")*/ + "','" + dbi.getDoctorId() + "')";
                    database.execSQL(insertBrand);
                    isTrue=true;
                }
           /*      database.setTransactionSuccessful();
                database.endTransaction();
                database.close();*/

            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertDoctorDesig(List<DoctorDesignation> aList) {
        boolean isTrue = true;
        try {

            dbCrudHelper._deleteAllRecordsFromaTable("tblDoctor_Desig");

            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                DoctorDesignation aInfo = aList.get(i);
                String insertQuery = "Insert into tblDoctor_Desig(DesignationId,DesignationName) " +
                        "values('" + aInfo.getDesignationId() + "','" + aInfo.getDesignationName().replace("'", "''") + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertDoctorDegree(List<DoctorDegree> aList) {
        boolean isTrue = true;
        try {

            String tableName = "tblDoctor_Degree";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                DoctorDegree aInfo = aList.get(i);
                String insertQuery = "Insert into tblDoctor_Degree(DegreeId,DegreeName,DoctorTypeId) " +
                        "values('" + aInfo.getDegreeId() + "','" + aInfo.getDegreeName().replace("'", "''") + "','" + aInfo.getDoctorTypeId() + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertDoctorSpecility(List<DoctorSpeciality> aList) {
        boolean isTrue = true;
        try {

            String tableName = "tblDoctor_Speciality";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                DoctorSpeciality aInfo = aList.get(i);
                String insertQuery = "Insert into tblDoctor_Speciality(SpecialityId,SpecialityName) " +
                        "values('" + aInfo.getSpecialityId() + "','" + aInfo.getSpecialityName().replace("'", "''") + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertBrand(List<ModelBrand> aList) {
        boolean isTrue = false;
        try {
            String tableName = "tblBrandInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                ModelBrand nInfo = aList.get(i);
                String insertQuery = "Insert into tblBrandInfo(ProductBrandId,ProductSQName,MaxValue) " +
                        "values('" + nInfo.getProductBrandId() + "','" + nInfo.getProductSQName().replace("'", "''") + "','" + nInfo.getMaxValue() + "')";
                database.execSQL(insertQuery);
                isTrue = true;
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertPromo(List<ModelPromo> aList) {
        boolean isTrue = false;
        try {
            String tableName = "tbl_BrandPromo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                ModelPromo nInfo = aList.get(i);
                String insertQuery = "Insert into tbl_BrandPromo(brandId,promoId,promoName) " +
                        "values('" + nInfo.getBrandId() + "','" + nInfo.getPromoId() + "','" + nInfo.getPromoName().replace("'", "''") + "')";
                database.execSQL(insertQuery);
                isTrue = true;
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertDoctorCategory(List<DoctorCategory> aList) {
        boolean isTrue = true;
        try {

            String tableName = "tblDoctorCategory";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                DoctorCategory nInfo = aList.get(i);
                String insertQuery = "Insert into tblDoctorCategory(CategoryId,CategoryName) " +
                        "values('" + nInfo.getCategoryId() + "','" + nInfo.getCategoryName().replace("'", "''") + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertPatientPriority(List<ModelPriority> mList) {
        boolean isTrue = false;
        String tableName = "tblPriority";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelPriority m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("PriorityId", m.getPatientPriorityId());
            cv.put("StartPoint", m.getPatientStartPoint());
            cv.put("EndPoint", m.getPatientEndPoint());
            cv.put("Status", m.getPatientStatus().replace("'", "''"));
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertPracticeShift(List<ModelPracticeShift> mList) {
        boolean isTrue = false;
        String tableName = "tblPracticeShift";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelPracticeShift m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("ShiftId", m.getPracticeShiftId());
            cv.put("Shift", m.getPracticeShift().replace("'", "''"));
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertContType(List<DoctorContact> aList) {
        boolean isTrue = true;
        try {

            String tableName = "tblContact_Type";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                DoctorContact aInfo = aList.get(i);
                String insertQuery = "Insert into tblContact_Type(ContactTypeId,ContactType) " +
                        "values('" + aInfo.getContactTypeId() + "','" + aInfo.getContactType() + "')";
                database.execSQL(insertQuery);
            }

        } catch (Exception exception) {
            isTrue = false;
            Log.e("DBEX", exception.toString());

        }
        return isTrue;
    }
    public boolean InsertDay(List<ModelDay> mList) {
        boolean isTrue = false;
        String tableName = "tblDay";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        for (ModelDay m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("DayNameId", m.getDayNameValueId());
            cv.put("DayName", m.getDayNameValue());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertDeliveryDay(List<ModelDeliveryDay> mList) {
        boolean isTrue = false;
        String tableName = "tblDeliveryDay";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }

        for (ModelDeliveryDay m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("DeliveryDayNameId", m.getDeliveryDayNameId());
            cv.put("DeliveryDayName", m.getDeliveryDayName());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertWorkType(List<ModelWorkType> mList) {
        boolean isTrue = false;
        String tableName = "tblWorkType";
        SQLiteDatabase database = dbMain.getWritableDatabase();
        database.beginTransaction();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);
        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }

        for (ModelWorkType m : mList) {
            ContentValues cv = new ContentValues();
            cv.put("WorkTypeId", m.getWorkTypeId());
            cv.put("WorkType", m.getWorkType());
            database.insert(tableName, null, cv);
        }
        isTrue = true;
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return isTrue;
    }
    public boolean InsertProductGift(List<Gift> aProduct) {
        boolean isTrue = false;
        try {
            String tableName = "tbl_ProductGiftInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);

            SQLiteDatabase databases = dbMain.getWritableDatabase();
            for (int i = 0; i < aProduct.size(); i++) {
                Gift aInfo = aProduct.get(i);
                String insertQuery = "Insert into tbl_ProductGiftInfo(ProductId,ProductName,ProductCode) " +
                        "values('" + aInfo.getProductId() + "','" + aInfo.getProductName().replace("'", "''") + "','" + aInfo.getProductCode() + "')";
                databases.execSQL(insertQuery);
                isTrue = true;
            }
            databases.close();
        } catch (Exception exception) {
            Log.e("DBEX", exception.toString());
        }

        return isTrue;
    }


    public boolean InsertTourPlanPurpose(List<TourPurposeViewModel> aTpp) {
        boolean isTrue = true;
        try {
            String tableName = "tblTourPlanPurpose";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);

            SQLiteDatabase databases = dbMain.getWritableDatabase();
            for (int i = 0; i < aTpp.size(); i++) {
                TourPurposeViewModel aInfo = aTpp.get(i);
                String insertQuery = "Insert into tblTourPlanPurpose(TPId,TPName,IsMarketVisit,IsOtherVisit) " +
                        "values('" + aInfo.getTPId() + "','" + aInfo.getTPName().replace("'", "''") + "','" + aInfo.getIsMarketVisit() + "','" + aInfo.getIsOtherVisit() + "')";
                databases.execSQL(insertQuery);
            }
        } catch (Exception exception) {
            Log.e("DBEX", exception.toString());
        }

        return isTrue;
    }

    public boolean InsertVisitType(List<TourTypeViewModel> aTpp) {
        boolean isTrue = true;
        try {
            String tableName = "tblVisit_Type";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);

            SQLiteDatabase databases = dbMain.getWritableDatabase();
            for (int i = 0; i < aTpp.size(); i++) {
                TourTypeViewModel aInfo = aTpp.get(i);
                String insertQuery = "Insert into tblVisit_Type(TourTypeId,TourTypeName) " +
                        "values('" + aInfo.getTourTypeId() + "','" + aInfo.getTourTypeName().replace("'", "''") + "')";
                databases.execSQL(insertQuery);
            }
        } catch (Exception exception) {
            Log.e("DBEX", exception.toString());
        }

        return isTrue;
    }
    public boolean InsertTransport(List<ModelTransport> aList) {
        boolean isTrue = true;
        try {
            String tableName = "tblTransportInfo";
            dbCrudHelper._deleteAllRecordsFromaTable(tableName);

            SQLiteDatabase database = dbMain.getWritableDatabase();
            for (int i = 0; i < aList.size(); i++) {
                ModelTransport aInfo = aList.get(i);
                String insertQuery = "Insert into tblTransportInfo(TransportId,TransportName) " +
                        "values('" + aInfo.getTransportId() + "','" + aInfo.getTransportName().replace("'", "''") + "')";
                database.execSQL(insertQuery);
            }
        } catch (Exception exception) {
            Log.e("DBEX", exception.toString());
        }
        return isTrue;
    }
}
