package com.saidur.creatrix.unicorn.salessolution.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saidur.creatrix.unicorn.salessolution.Model.CustomerViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorCategory;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorContact;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDegree;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDesignation;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSpeciality;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelPracticeShift;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelGender;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPriority;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPromo;

import java.util.ArrayList;

public class DBDoctorHelper {
    DBMain dbMain;
    Context context;
    public DBDoctorHelper(Context context) {
        this.context = context;
        dbMain = new DBMain(context);
    }
    @SuppressLint("Range")
    public ArrayList<ModelBrand> getBrandListFromSQLite() {
        ArrayList<ModelBrand> brandList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String docQuery= "Select * from tblBrandInfo";
        try {
            Cursor cursor;
            cursor = database.rawQuery(docQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelBrand ddInfo = new ModelBrand();
                    ddInfo.setProductBrandId(cursor.getInt(cursor.getColumnIndex("ProductBrandId")));
                    ddInfo.setProductSQName(cursor.getString(cursor.getColumnIndex("ProductSQName")));
                    brandList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("DoctorDetails", exception.toString());
            exception.getMessage().toString();
        }
        return brandList;
    }
    @SuppressLint("Range")
    public ArrayList<ModelPromo> getPromoListFromSQLite() {
        ArrayList<ModelPromo> brandList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String docQuery= "Select * from tbl_BrandPromo";
        try {
            Cursor cursor;
            cursor = database.rawQuery(docQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelPromo ddInfo = new ModelPromo();
                    ddInfo.setBrandId(cursor.getInt(cursor.getColumnIndex("brandId")));
                    ddInfo.setPromoId(cursor.getInt(cursor.getColumnIndex("promoId")));
                    ddInfo.setPromoName(cursor.getString(cursor.getColumnIndex("promoName")));
                    brandList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("DoctorDetails", exception.toString());
            exception.getMessage().toString();
        }
        return brandList;
    }
    @SuppressLint("Range")
    public ArrayList<ModelPromo> getPromoByBrand(int id) {
        ArrayList<ModelPromo> brandList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String docQuery= "Select * from tbl_BrandPromo where brandId=" + id + " order by promoName asc";
        try {
            Cursor cursor;
            cursor = database.rawQuery(docQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelPromo ddInfo = new ModelPromo();
                    ddInfo.setBrandId(cursor.getInt(cursor.getColumnIndex("brandId")));
                    ddInfo.setPromoId(cursor.getInt(cursor.getColumnIndex("promoId")));
                    ddInfo.setPromoName(cursor.getString(cursor.getColumnIndex("promoName")));
                    brandList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("DoctorDetails", exception.toString());
            exception.getMessage().toString();
        }
        return brandList;
    }

    @SuppressLint("Range")
    public ArrayList<DoctorDesignation> getDesigListFromSQLite(int id) {

        ArrayList<DoctorDesignation> deigList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String desigQuery = "Select * from tblDoctor_Desig order by DesignationId desc";
        String oderMasterQuery = "Select * from tblDoctor_Desig Where DesignationId=" + id + "";
        try {
            Cursor cursor;

            if (id == 0 || String.valueOf(id).isEmpty()) {
                cursor = database.rawQuery(desigQuery, null);
            } else {
                cursor = database.rawQuery(oderMasterQuery, null);
            }

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DoctorDesignation ddInfo = new DoctorDesignation();
                    ddInfo.setDesignationId(cursor.getInt(cursor.getColumnIndex("DesignationId")));
                    ddInfo.setDesignationName(cursor.getString(cursor.getColumnIndex("DesignationName")));
                    deigList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("CampOrderDetails", exception.toString());
            exception.printStackTrace();
        }
        return deigList;
    }

    @SuppressLint("Range")
    public ArrayList<DoctorDegree> getDegreeListFromSQLite() {
        ArrayList<DoctorDegree> degList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String desigQuery = "Select * from tblDoctor_Degree";

        try {
            Cursor cursor;
            cursor = database.rawQuery(desigQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DoctorDegree ddInfo = new DoctorDegree();
                    ddInfo.setDegreeId(cursor.getInt(cursor.getColumnIndex("DegreeId")));
                    ddInfo.setDegreeName(cursor.getString(cursor.getColumnIndex("DegreeName")));
                    ddInfo.setDoctorTypeId(cursor.getInt(cursor.getColumnIndex("DoctorTypeId")));
                    degList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("CampOrderDetails", exception.toString());
            exception.printStackTrace();
        }
        return degList;
    }

    @SuppressLint("Range")
    public ArrayList<DoctorSpeciality> getSpecListFromSQLite() {

        ArrayList<DoctorSpeciality> spcList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String desigQuery = "Select * from tblDoctor_Speciality order by SpecialityId asc";

        try {
            Cursor cursor;
            cursor = database.rawQuery(desigQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DoctorSpeciality ddInfo = new DoctorSpeciality();
                    ddInfo.setSpecialityId(cursor.getInt(cursor.getColumnIndex("SpecialityId")));
                    ddInfo.setSpecialityName(cursor.getString(cursor.getColumnIndex("SpecialityName")));
                    spcList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("CampOrderDetails", exception.toString());
            exception.printStackTrace();
        }
        return spcList;
    }

    @SuppressLint("Range")
    public ArrayList<DoctorCategory> getDocCategoryListFromSQLite(int id) {

        ArrayList<DoctorCategory> doccateList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String desigQuery = "Select * from tblDoctorCategory order by CategoryId desc";
        String oderMasterQuery = "Select * from tblDoctorCategory Where CategoryId=" + id + "";
        try {
            Cursor cursor;
            if (id == 0 || String.valueOf(id).isEmpty()) {
                cursor = database.rawQuery(desigQuery, null);
            } else {
                cursor = database.rawQuery(oderMasterQuery, null);
            }
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DoctorCategory ddInfo = new DoctorCategory();
                    ddInfo.setCategoryId(cursor.getInt(cursor.getColumnIndex("CategoryId")));
                    ddInfo.setCategoryName(cursor.getString(cursor.getColumnIndex("CategoryName")));
                    doccateList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("CampOrderDetails", exception.toString());
            exception.printStackTrace();
        }
        return doccateList;
    }

    @SuppressLint("Range")
    public ArrayList<DoctorContact> getContactTypeListFromSQLite(int id) {
        ArrayList<DoctorContact> conTypeList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String desigQuery = "Select * from tblContact_Type order by ContactTypeId desc";
        String oderMasterQuery = "Select * from tblContact_Type Where ContactTypeId=" + id + "";
        try {
            Cursor cursor;
            if (id == 0 || String.valueOf(id).isEmpty()) {
                cursor = database.rawQuery(desigQuery, null);
            } else {
                cursor = database.rawQuery(oderMasterQuery, null);
            }
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DoctorContact ddInfo = new DoctorContact();
                    ddInfo.setContactTypeId(cursor.getInt(cursor.getColumnIndex("ContactTypeId")));
                    ddInfo.setContactType(cursor.getString(cursor.getColumnIndex("ContactType")));
                    conTypeList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("CampOrderDetails", exception.toString());
            exception.printStackTrace();
        }
        return conTypeList;
    }

    @SuppressLint("Range")
    public ArrayList<DoctorBrand> getDoctorBrandListFromSQLite(int doctorId) {

        ArrayList<DoctorBrand> brandList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String docQuery = "Select * from tblDoctorBrand where DoctorId=" + doctorId + "";

        try {
            Cursor cursor;
            cursor = database.rawQuery(docQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DoctorBrand ddInfo = new DoctorBrand();
                    ddInfo.setDoctorId(cursor.getInt(cursor.getColumnIndex("DoctorId")));
                    ddInfo.setBrandId(cursor.getInt(cursor.getColumnIndex("BrandId")));
                    ddInfo.setBrandName(cursor.getString(cursor.getColumnIndex("BrandName")));
                    brandList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("DoctorDetails", exception.toString());
            exception.printStackTrace();
        }
        return brandList;
    }

    @SuppressLint("Range")
    public ArrayList<ModelGender> getGenderListFromSQLite() {

        ArrayList<ModelGender> genderList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String docQuery = "Select * from tblGender";

        try {
            Cursor cursor;
            cursor = database.rawQuery(docQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelGender ddInfo = new ModelGender();
                    ddInfo.setGenderId(cursor.getInt(cursor.getColumnIndex("GenderId")));
                    ddInfo.setGenderName(cursor.getString(cursor.getColumnIndex("GenderName")));
                    genderList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("DoctorDetails", exception.toString());
            exception.printStackTrace();
        }
        return genderList;
    }

    @SuppressLint("Range")
    public ArrayList<ModelPriority> getPriorityFromSQLite() {
        ArrayList<ModelPriority> aList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String docQuery = "Select * from tblPriority";

        try {
            Cursor cursor;
            cursor = database.rawQuery(docQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelPriority ddInfo = new ModelPriority();
                    ddInfo.setPatientPriorityId(cursor.getInt(cursor.getColumnIndex("PriorityId")));
                    ddInfo.setPatientStartPoint(cursor.getInt(cursor.getColumnIndex("StartPoint")));
                    ddInfo.setPatientEndPoint(cursor.getInt(cursor.getColumnIndex("EndPoint")));
                    ddInfo.setPatientStatus(cursor.getString(cursor.getColumnIndex("Status")));
                    aList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("DoctorDetails", exception.toString());
            exception.printStackTrace();
        }
        return aList;
    }


    @SuppressLint("Range")
    public ArrayList<CustomerViewModel> getCustomerListByMarketIdFromSQLite(int marketId) {
        ArrayList<CustomerViewModel> customerList = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = dbMain.getReadableDatabase(); // ✅ readOnly is enough
            String query = "SELECT CustomerMasterId, CustomerCode, CustomerName, CustomerCell " +
                    "FROM tblCustomerInfo " +
                    "WHERE MarketId = ? " +
                    "ORDER BY CustomerName ASC";

            cursor = database.rawQuery(query, new String[]{String.valueOf(marketId)}); // ✅ parameterized (SQL injection safe)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CustomerViewModel customer = new CustomerViewModel();
                    customer.setCustomerMasterId(cursor.getInt(cursor.getColumnIndex("CustomerMasterId")));

                    // ✅ Combine fields for display, but keep name separately
                    String code = cursor.getString(cursor.getColumnIndex("CustomerCode"));
                    String name = cursor.getString(cursor.getColumnIndex("CustomerName"));
                    String cell = cursor.getString(cursor.getColumnIndex("CustomerCell"));

                    customer.setCustomerName(code + " : " + name );
                    customer.setCustomerCode(code);
                    customer.setCustomerCell(cell);
                    customer.setChecked(false); // initialize selection state

                    customerList.add(customer);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("getCustomerListByMarketId", "Error loading customer list", e);
        } finally {
            if (cursor != null) cursor.close();
            if (database != null && database.isOpen()) database.close(); // ✅ Always close
        }

        return customerList;
    }

    @SuppressLint("Range")
    public ArrayList<ModelPracticeShift> getPracticeShiftFromSQLite() {
        ArrayList<ModelPracticeShift> aList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String docQuery = "Select * from tblPracticeShift";

        try {
            Cursor cursor;
            cursor = database.rawQuery(docQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ModelPracticeShift ddInfo = new ModelPracticeShift();
                    ddInfo.setPracticeShiftId(cursor.getInt(cursor.getColumnIndex("ShiftId")));
                    ddInfo.setPracticeShift(cursor.getString(cursor.getColumnIndex("Shift")));
                    aList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("DoctorDetails", exception.toString());
            exception.printStackTrace();
        }
        return aList;
    }

    @SuppressLint("Range")
    public ArrayList<DoctorListViewModel> getDoctorListFromSQLite() {

        ArrayList<DoctorListViewModel> docList = new ArrayList<>();
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String docQuery = "Select * from tblDoctorInfo";

        try {
            Cursor cursor;
            cursor = database.rawQuery(docQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DoctorListViewModel ddInfo = new DoctorListViewModel();
                    ddInfo.setDoctorId(cursor.getInt(cursor.getColumnIndex("DoctorId")));
                    ddInfo.setDoctorCode(cursor.getString(cursor.getColumnIndex("DoctorCode")));
                    ddInfo.setDoctorName(cursor.getString(cursor.getColumnIndex("DoctorName")));
                    ddInfo.setGender(cursor.getString(cursor.getColumnIndex("Gender")));
                    ddInfo.setDocContact(cursor.getString(cursor.getColumnIndex("DocContact")));
                    ddInfo.setDoctorPatientStatus(cursor.getString(cursor.getColumnIndex("DoctorPatientStatus")));
                    ddInfo.setDoctorAddress(cursor.getString(cursor.getColumnIndex("DoctorAddress")));
                    ddInfo.setMarketName(cursor.getString(cursor.getColumnIndex("MarketName")));
                    docList.add(ddInfo);
                }
            }

        } catch (Exception exception) {
            Log.e("DoctorDetails", exception.toString());
            exception.printStackTrace();
        }
        return docList;
    }
}
