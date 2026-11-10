package com.saidur.creatrix.unicorn.salessolution.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBMain extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = DatabaseInfomation.getDatabaseName();
    private static final int DATABASE_VERSION = DatabaseInfomation.getDatabaseVersion();
    private Context context;

    public DBMain(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Division Table
        String tblSession = "Create Table if not exists tblSession" +
                "(UserId Integer,EmpInfoId Integer,UserName varchar(500),EmpMasterCode varchar(500),LoginName varchar(500),UserCo varchar(500),EmpRole varchar(500),RoleTypeId Integer,RoleType varchar(500),DesigName varchar(500),Device varchar(500))";

        //Table Main Structure
        String tbl_Group = "Create Table if not exists tbl_Group (pk Integer primary key, " +
                "GroupId Integer,GroupName varchar(500))";
        String tblRegion = "Create Table if not exists tblRegion (pk Integer primary key, " +
                "RegionId Integer,RegionName varchar(500),GroupId Integer)";
        String tblArea = "Create Table if not exists tblArea (pk Integer primary key, " +
                "AreaId Integer,AreaName varchar(500),RegionId Integer)";
        String tblTerritory = "Create Table if not exists tblTerritory (pk Integer primary key, " +
                "TerritoryId Integer,TerritoryName varchar(500),AreaId Integer)";
        String tblSubTerritory = "Create Table if not exists tblSubTerritory (pk Integer primary key, " +
                "SubTerritoryId Integer,SubTerritoryName varchar(500),TerritoryId Integer)";
        String tblMarket = "Create Table if not exists tblMarket (pk Integer primary key, " +
                "MarketId Integer,MarketName varchar(500),SubTerritoryId Integer)";
        //Table AssignEmp
        String tblNSMInfo = "Create Table if not exists tblNSMInfo (pk Integer primary key, " +
                "NSMEmpId Integer,NSMId Integer,EmpMasterCode varchar(500),EmpName varchar(500)," +
                "GroupId Integer)";
        String tblRSMInfo = "Create Table if not exists tblRSMInfo (pk Integer primary key, " +
                "RSMEmpId Integer,RSMId Integer,EmpMasterCode varchar(500),EmpName varchar(500)," +
                "RegionId Integer)";
        String tblASMInfo = "Create Table if not exists tblASMInfo (pk Integer primary key, " +
                "ASMEmpId Integer,ASMId Integer,EmpMasterCode varchar(500),EmpName varchar(500),AreaId Integer)";

        String tblMIOInfo = "Create Table if not exists tblMIOInfo (pk Integer primary key, " +
                "MIOEmpId Integer,MIOId Integer,EmpMasterCode varchar(500),EmpName varchar(500)," +
                "TerritoryId Integer)";

        String tblMBEInfo = "Create Table if not exists tblMBEInfo (pk Integer primary key, " +
                "MBEInfoId Integer,MBEEmpId Integer,EmpMasterCode varchar(500),EmpName varchar(500)," +
                "SubTerritoryId Integer)";


        //Product Variant
        String tbl_ProductInfo = "Create Table if not exists tbl_ProductInfo (ProductId Integer primary key, " +
                "ProductName varchar(500),ProductCode varchar(500),ProductDes varchar(500)," +
                "PackSize varchar(500),UnitPrice varchar(500),QuotedPrice varchar(500),VatPercentage varchar(500),VatAmountPerunit varchar(500),CustomerMasterId Integer)";

        String tbl_ProductSampleInfo = "Create Table if not exists tbl_ProductSampleInfo (pk Integer primary key," +
                "ProductId Integer,ProductName varchar(500),ProductCode varchar(500))";

        String tbl_ProductGiftInfo = "Create Table if not exists tbl_ProductGiftInfo (pk Integer primary key AUTOINCREMENT,ProductId Integer,ProductName varchar(500),ProductCode varchar(500))";
        String tbl_BrandPromo = "Create Table if not exists tbl_BrandPromo (brandId Integer,promoId Integer,promoName varchar(500))";

        //new Table dcr
        String tblDcrInfo = "Create Table if not exists tblDcrInfo (DcrId Integer primary key AUTOINCREMENT," +
                "DoctorId Integer,DoctorName varchar(500),DocContact varchar(500),DoctorTypeName varchar(500),ChemberName varchar(500),ProgramTypeName varchar(500),SessionUser Integer,DcrDate varchar(500),EntryTime varchar(500),VisitTypeId Integer,VisitTypeName varchar(500),ChemberId Integer,Remarks varchar(500))";
        /* List<Product> aProList;*/
        String tbl_UserRole = "Create Table if not exists tbl_UserRole (pk Integer primary key AUTOINCREMENT,UserRoleID Integer,RoleName varchar(500))";
        String tbl_UserByRole = "Create Table if not exists tbl_UserByRole (pk Integer primary key AUTOINCREMENT,EmpInfoId Integer,EmpName varchar(500),EmpMasterCode varchar(500),UserRoleID Integer)";

        String tblDcrVisitedwith = "Create Table if not exists tblDcrVisitedwith (DcrVisitedId Integer primary key AUTOINCREMENT,DcrId Integer, " +
                "EmpInfoId Integer,EmpName varchar(500),EmpMasterCode varchar(500),DoctorId Integer)";

        String tblDcrSample = "Create Table if not exists tblDcrSample (DcrSampleId Integer primary key AUTOINCREMENT,DcrId Integer, " +
                "ProductId Integer,ProductName varchar(500),Quantity Integer,DoctorId Integer)";
        String tblDcrGift = "Create Table if not exists tblDcrGift(DcrGiftId Integer primary key AUTOINCREMENT,DcrId Integer, " +
                "ProductId Integer,ProductName varchar(500),Quantity Integer,Position Integer,DoctorId Integer)";
        String tblDcrBrand = "Create Table if not exists tblDcrBrand(DcrBrandId Integer primary key AUTOINCREMENT,DcrId Integer,BrandId Integer,BrandName varchar(500),DoctorId Integer)";


        String tblNonEffectiveReason = "Create Table if not exists tblNonEffectiveReason" +
                "(ReasonId Integer,ReasonName varchar(500))";
        //new Table prescription
        String tblPrescriptionInfo = "Create Table if not exists tblPrescriptionInfo (PrescripId Integer primary key AUTOINCREMENT," +
                "DoctorId Integer,DoctorName varchar(500),DocContact varchar(500),DoctorTypeName varchar(500),ChemberName varchar(500),ProgramTypeName varchar(500),SessionUser Integer,PrescriptionDate varchar(500),EntryTime varchar(500),PrescriptionTypeId Integer,PrescTypeName varchar(500),ChemberId Integer,ImageString varchar(500))";
        /* List<Product> aProList;*/
        String tblPrescriptionMaster = "Create Table if not exists tblPrescriptionMaster (PrescripProductId Integer primary key AUTOINCREMENT,PrescripId Integer, " +
                "ProductId Integer,ProductName varchar(500),DoctorId Integer)";

        String tblPrescrip_Type = "Create Table if not exists tblPrescrip_Type" +
                "(PrescriptionTypeId Integer,PrescriptionType varchar(500))";

        //Division Table
        String tblDivisionInfo = "Create Table if not exists tblDivisionInfo" +
                "(DivisionId Integer,DivisionName varchar(500))";
        //District -need DivisionId
        String tblDistrictInfo = "Create Table if not exists tblDistrictInfo" +
                "(DistrictId Integer,DistrictName varchar(500),DivisionId Integer)";

        //Thana -need DistrictId
        String tblThanaInfo = "Create Table if not exists tblThanaInfo" +
                "(ThanaId Integer,ThanaName varchar(500),district_id Integer)";

        String tblDay = "Create Table if not exists tblDay" +
                "(DayNameId Integer,DayName varchar(500))";
        String tblDeliveryDay = "Create Table if not exists tblDeliveryDay" +
                "(DeliveryDayNameId Integer,DeliveryDayName varchar(500))";

        String tblWorkType = "Create Table if not exists tblWorkType" +
                "(WorkTypeId Integer,WorkType varchar(500))";

        String tblGender = "Create Table if not exists tblGender" +
                "(GenderId Integer,GenderName varchar(500))";
        String tblPriority = "Create Table if not exists tblPriority" +
                "(PriorityId Integer,StartPoint Integer,EndPoint Integer,Status varchar(500))";
        String tblPracticeShift = "Create Table if not exists tblPracticeShift" +
                "(ShiftId Integer,Shift varchar(500))";

        String tblDoctorReport = "Create Table if not exists tblDoctorReport(createdAt varchar(500), " +
                "DoctorCode varchar(500),MarketName varchar(500),ActionStatus varchar(500)," +
                "WaitingRole varchar(500),WatingEmployee varchar(500),GroupId varchar(500),RegionId varchar(500),AreaId varchar(500),TerritoryId varchar(500),SubTerritoryId varchar(500),MarketId varchar(500))";

        //DoctorDashBoard Table

        String tblDoctorInfo = "Create Table if not exists tblDoctorInfo" +
                "(DoctorId Integer,DoctorCode varchar(500),DoctorName varchar(500),Gender varchar(500),DocContact varchar(500),DoctorPatientStatus varchar(500),DoctorAddress varchar(500),GroupId varchar(500),RegionId varchar(500),AreaId varchar(500),TerritoryId varchar(500),SubTerritoryId varchar(500),MarketId varchar(500),MarketName varchar(500))";

        String tblDoctorBrand = "Create Table if not exists tblDoctorBrand" +
                "(pk Integer primary key AUTOINCREMENT,BrandId Integer,BrandName varchar(500),DoctorId Integer)";

        //DoctorDashBoard Designation
        String tblDoctor_Desig = "Create Table if not exists tblDoctor_Desig" +
                "(DesignationId Integer,DesignationName varchar(500))";
        //DoctorDashBoard Degree
        String tblDoctor_Degree = "Create Table if not exists tblDoctor_Degree" +
                "(DegreeId Integer,DegreeName varchar(500),DoctorTypeId Integer)";

        String tblVisit_Type = "Create Table if not exists tblVisit_Type (pk Integer primary key,TourTypeId Integer,TourTypeName varchar(500))";
        //DoctorDashBoard Speciality
        String tblDoctor_Speciality = "Create Table if not exists tblDoctor_Speciality" +
                "(SpecialityId Integer,SpecialityName varchar(500))";

        //DoctorDashBoard Institution
        String tblDoctor_Institution = "Create Table if not exists tblDoctor_Institution" +
                "(InstitutionId Integer,Institution varchar(500))";
        //DoctorDashBoard ChembarType
        String tblDoctor_Chembar = "Create Table if not exists tblDoctor_Chembar" +
                "(ChamberTypeId Integer,ChamberTypeName varchar(500))";
        //DoctorDashBoard ChembarName
        String tblDoctor_ChembarName = "Create Table if not exists tblDoctor_ChembarName" +
                "(ChemberId Integer,ChemberName varchar(500),DoctorId Integer)";

        //Doctor_Type
        String tblDoctor_Type = "Create Table if not exists tblDoctor_Type" +
                "(DoctorTypeId Integer,DoctorTypeName varchar(500))";

        String tblDoctor_Specialday = "Create Table if not exists tblDoctor_Specialday" +
                "(SpecialDayId Integer,SpecialDay varchar(500))";

        //DoctorDashBoard program type
        String tblProgram_Type = "Create Table if not exists tblProgram_Type" +
                "(ProgramTypeId Integer,ProgramType varchar(500))";
        //DoctorDashBoard Brand
        String tblBrandInfo = "Create Table if not exists tblBrandInfo" +
                "(ProductBrandId Integer,ProductSQName varchar(500),MaxValue Integer)";

        //DoctorDashBoard Category
        String tblDoctorCategory = "Create Table if not exists tblDoctorCategory" +
                "(CategoryId Integer,CategoryName varchar(500))";

        //Tour plan purpose
        String tblTourPlanPurpose = "Create Table if not exists tblTourPlanPurpose" +
                "(TPId Integer,TPName varchar(500), IsMarketVisit INTEGER, IsOtherVisit INTEGER)";

        //Customer Type
        String tblCustomer_Type = "Create Table if not exists tblCustomer_Type" +
                "(CustomerTypeId Integer,CustomerType varchar(500))";
        //Contact Type
        String tblContact_Type = "Create Table if not exists tblContact_Type" +
                "(ContactTypeId Integer,ContactType varchar(500))";
        //Expense Type
        String tblExpense_Type = "Create Table if not exists tblExpense_Type" +
                "(ExpenseTypeId Integer,ExpenseTypeName varchar(500))";
        //Leave Type
        String tblLeave_Type = "Create Table if not exists tblLeave_Type" +
                "(LeaveBalanceId Integer,LeaveTypeName varchar(500),YearlyLeaveBalance Integer)";
        //Teritory
        String tblTeritory = "Create Table if not exists tblTeritory" +
                "(TerritoryId Integer,TerritoryName varchar(500),TerritoryCode varchar(500))";
        //Transport
        String tblTransportInfo = "Create Table if not exists tblTransportInfo" +
                "(TransportId Integer,TransportName varchar(500))";


        //Provider type new
        String tblProvider_Type = "Create Table if not exists tblProvider_Type" +
                "(ProviderTypeId Integer,ProviderType varchar(500),forCustomer Integer,forDoctor Integer)"; //1=doctor,0=Customer
        //Customer Station
        String tblCustomer_Station = "Create Table if not exists tblCustomer_Station" +
                "(StationTypeId Integer,StationTypeName varchar(500))";

        String tblInitTable = "Create Table if not exists tblInitTable" +
                "(IsFirstSyncDone Integer,Date varchar(500),Time varchar(500))";

        String tblInitTableLoadBackup = "Create Table if not exists tblInitTableLoadBackup" +
                "(IsFirstLoadBackupDone Integer)";

        String tblOrderMaster = "Create Table if not exists tblOrderMaster (OrderIdLocal Integer primary key AUTOINCREMENT,OrderId Integer,EmpId Integer,ComUnitId Integer," +
                "OrderCode varchar(500),MIOCode varchar(500),CustomerCode varchar(500)," +
                "CustomerMasterId Integer,CustomerName varchar(500),CustomerAdress varchar(500),SubmissionDate varchar(500),Status varchar(500),CollectionDate varchar(500),DeliveryDate varchar(500),Remarks varchar(500))";
        //Order For Main
        String tblOrderDetails = "Create Table if not exists tblOrderDetails (OrderDetLocal Integer primary key autoincrement, " +
                "OrderIdLocal Integer,OrderDetailId Integer,ProductId Integer,ProductName varchar(500)," +
                "Quantity Integer,UnitPrice varchar(500),VatPercentage varchar(500),VatAmountPerunit varchar(500))";
      /*  //Order For sample
        String tblOrderSampleMaster = "Create Table if not exists tblOrderSampleMaster (pk Integer primary key AUTOINCREMENT,OrderId Integer,EmpId Integer,EmpName varchar(550),DoctorId Integer," +
                "DoctorCode varchar(150),DoctorName varchar(50),ChamberAddress varchar(150)," +
                "CreatedBy varchar(150),CreatedDate varchar(50),Remarks varchar(50),Remarks varchar(550),IsPending Integer)";
        String tblOrderSampleDetail = "Create Table if not exists tblOrderSampleDetail (pk Integer primary key AUTOINCREMENT, " +
                "ODSampleId Integer,OrderId Integer,ProductId Integer,ProductName varchar(150),ProductCode varchar(150),Quantity Integer,IsConfirm Integer)";
*/
        String tblCompanyUnit = "Create Table if not exists tblCompanyUnit (ComUnitId Integer primary key,ComUnitName varchar(500))";

        String tblCustomerInfo = "Create Table if not exists tblCustomerInfo(CustomerMasterId Integer, " +
                "CustomerName varchar(500),CustomerCode varchar(500),CustomerAdress varchar(500)," +
                "CustomerType varchar(500),CustomerCell varchar(500),CustomerBalance varchar(500),CustomerCreditlimit varchar(500),Market varchar(500),GroupId varchar(500),RegionId varchar(500),AreaId varchar(500),TerritoryId varchar(500),SubTerritoryId varchar(500),MarketId varchar(500),Note varchar(500),CustomerCheck Integer)";


     //TODO:Customer Report mio wise
        String tblCustomerReport = "Create Table if not exists tblCustomerReport(CustomerName varchar(500), " +
                "MarketName varchar(500),ActionStatus varchar(500),CellNo varchar(500)," +
                "OwnerName varchar(500),Address varchar(500),ImageBase64String varchar(500),ProgramTypeName varchar(500),WatingEmployee varchar(500),WaitingRole varchar(500),GroupId varchar(500),RegionId varchar(500),AreaId varchar(500),TerritoryId varchar(500),SubTerritoryId varchar(500),MarketId varchar(500),CustomerStatus varchar(500),BtnupdateInfo Integer,GroupName varchar(500),RegionName varchar(500),AreaName varchar(500),TerritoryName varchar(500),SubTerritoryName varchar(500))";

        String tblLoginProfile = "Create Table if not exists tblLoginProfile (UserId Integer,UserName varchar(500),Password varchar(500),EmpMasterCode varchar(500),LoginName varchar(500),empId Integer,UserCo varchar(500),EmpRole varchar(500),RoleTypeId Integer,RoleType varchar(500),IsApprove Integer,IsForward Integer,DesigName varchar(500))";
        String tblAttendance = "Create Table if not exists tblAttendance " +
                "(EmpId Integer,PInTime varchar(500),PInLat varchar(500),PInLong varchar(500),AttendanceDate varchar(500),POutTime varchar(500),POutLat varchar(500),POutLong varchar(500),completeStatus nvarchar(500),AttImg varchar(500))";

        String tblQuotedPrice = "Create Table if not exists tblQuotedPrice" +
                "(quotedPriceDetailId Integer,description varchar(500),policy varchar(500),customerMasterId Integer,activeFromDate varchar(500),activeToDate varchar(500),productId Integer,unitPrice varchar(500),vat varchar(500))";

        String tblprescimg = "Create Table if not exists tblprescimg" +
                "(prescimg_id Integer,prescimg varchar(500))";
        String tblmileageimg = "Create Table if not exists tblmileageimg" +
                "(mileageimg_id Integer,mileageimg varchar(500))";
        String tblexpensimg = "Create Table if not exists tblexpensimg" +
                "(expenseimg_id Integer,expenseimg varchar(500))";
        String tbldaimg = "Create Table if not exists tbldaimg" +
                "(daimg_id Integer,daimg varchar(500))";
        try {
            //Market Structure Main
            sqLiteDatabase.execSQL(tblSession);
            sqLiteDatabase.execSQL(tbl_Group);
            sqLiteDatabase.execSQL(tblRegion);
            sqLiteDatabase.execSQL(tblArea);
            sqLiteDatabase.execSQL(tblTerritory);
            sqLiteDatabase.execSQL(tblSubTerritory);
            sqLiteDatabase.execSQL(tblMarket);
            //Structure wise AssignEmp
            sqLiteDatabase.execSQL(tblNSMInfo);
            sqLiteDatabase.execSQL(tblRSMInfo);
            sqLiteDatabase.execSQL(tblASMInfo);
            sqLiteDatabase.execSQL(tblMIOInfo);
            sqLiteDatabase.execSQL(tblMBEInfo);

            sqLiteDatabase.execSQL(tblInitTable);
            sqLiteDatabase.execSQL(tbl_ProductInfo);
            sqLiteDatabase.execSQL(tbl_ProductSampleInfo);
            sqLiteDatabase.execSQL(tbl_ProductGiftInfo);
            sqLiteDatabase.execSQL(tblOrderMaster);
            sqLiteDatabase.execSQL(tblOrderDetails);


            sqLiteDatabase.execSQL(tblCompanyUnit);
            sqLiteDatabase.execSQL(tblCustomerInfo);
            sqLiteDatabase.execSQL(tbl_BrandPromo);
            sqLiteDatabase.execSQL(tblDcrInfo);
            sqLiteDatabase.execSQL(tbl_UserRole);
            sqLiteDatabase.execSQL(tbl_UserByRole);
            sqLiteDatabase.execSQL(tblDcrVisitedwith);
            sqLiteDatabase.execSQL(tblDcrSample);
            sqLiteDatabase.execSQL(tblDcrGift);
            sqLiteDatabase.execSQL(tblDcrBrand);
            sqLiteDatabase.execSQL(tblNonEffectiveReason);

            sqLiteDatabase.execSQL(tblPrescriptionInfo);
            sqLiteDatabase.execSQL(tblPrescriptionMaster);
            sqLiteDatabase.execSQL(tblPrescrip_Type);

            sqLiteDatabase.execSQL(tblCustomerReport);

            sqLiteDatabase.execSQL(tblLoginProfile);
            sqLiteDatabase.execSQL(tblAttendance);

            sqLiteDatabase.execSQL(tblDivisionInfo);
            sqLiteDatabase.execSQL(tblDistrictInfo);
            sqLiteDatabase.execSQL(tblThanaInfo);
            sqLiteDatabase.execSQL(tblDay);
            sqLiteDatabase.execSQL(tblDeliveryDay);
            sqLiteDatabase.execSQL(tblWorkType);
            sqLiteDatabase.execSQL(tblGender);
            sqLiteDatabase.execSQL(tblPriority);
            sqLiteDatabase.execSQL(tblPracticeShift);
            sqLiteDatabase.execSQL(tblDoctorReport);
            sqLiteDatabase.execSQL(tblDoctorInfo);
            sqLiteDatabase.execSQL(tblDoctorBrand);
            sqLiteDatabase.execSQL(tblDoctor_Desig);
            sqLiteDatabase.execSQL(tblDoctor_Degree);
            sqLiteDatabase.execSQL(tblVisit_Type);
            sqLiteDatabase.execSQL(tblDoctor_Speciality);
            sqLiteDatabase.execSQL(tblDoctor_Institution);
            sqLiteDatabase.execSQL(tblDoctor_Chembar);
            sqLiteDatabase.execSQL(tblDoctor_ChembarName);
            sqLiteDatabase.execSQL(tblDoctor_Type);
            sqLiteDatabase.execSQL(tblDoctor_Specialday);
            sqLiteDatabase.execSQL(tblBrandInfo);
            sqLiteDatabase.execSQL(tblDoctorCategory);
            sqLiteDatabase.execSQL(tblTourPlanPurpose);

            sqLiteDatabase.execSQL(tblProgram_Type);
            sqLiteDatabase.execSQL(tblCustomer_Type);
            sqLiteDatabase.execSQL(tblContact_Type);
            sqLiteDatabase.execSQL(tblExpense_Type);
            sqLiteDatabase.execSQL(tblLeave_Type);
            sqLiteDatabase.execSQL(tblTeritory);
            sqLiteDatabase.execSQL(tblTransportInfo);
            sqLiteDatabase.execSQL(tblProvider_Type);
            sqLiteDatabase.execSQL(tblCustomer_Station);
            sqLiteDatabase.execSQL(tblQuotedPrice);

            sqLiteDatabase.execSQL(tblprescimg);
            sqLiteDatabase.execSQL(tblexpensimg);
            sqLiteDatabase.execSQL(tblmileageimg);
            sqLiteDatabase.execSQL(tbldaimg);

            sqLiteDatabase.execSQL(tblInitTableLoadBackup);
        } catch (Exception e) {
            Log.e("SQLiteDBHelper", e.toString());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //version 1
        try {
            db.execSQL("ALTER TABLE tblOrderMaster ADD COLUMN Remarks varchar(500)");
        } catch (Exception e) {
            e.printStackTrace();
        }
       // Toast.makeText(context, "OnUpdate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

   /* public boolean InsertToOrderTable(OrderMaster aOrder) {
        SQLiteDatabase database = this.getWritableDatabase();

        return true;
    }*/
}
