package com.saidur.creatrix.unicorn.salessolution.UI.Sync;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBSyncHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ISyncMaster;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelUser;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterSyncMaster;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivitySyncFromServerBinding;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class SyncFromServerActivity extends AppCompatActivity implements ISyncMaster.View {
    private static final int TOTAL_SYNC_TASKS = 23;
    private static final Set<String> REQUIRED_SYNC_TASKS = new HashSet<>(Arrays.asList(
            "NSM", "DZSM", "AM", "MIO", "MBE",
            "Group", "Zone", "Area", "Territory", "Sub Territory", "Market",
            "Customer", "Customer Station", "Provider Type",
            "Brand", "Promo", "Product", "Quoted Price", "Product Gift",
            "Day", "Delivery Day", "Work Type", "Transport"
    ));
    ActivitySyncFromServerBinding binding;
    PresenterSyncMaster presenterSyncMaster;
    int prog = 0;
    DBSyncHelper dbSyncHelper;
    DBCrudHelper dbCrudHelper;
    boolean isGroupDone = false;
    boolean isRegionDone = false;
    boolean isAreaDone = false;
    boolean isTeritorryDone = false;
    boolean isSubTeritorryDone = false;
    boolean isMarketDone = false;
    boolean isMarketStructureDone = false;

    boolean isFieldforceDone = false;
    boolean isNSMDone = false;
    boolean isRSMDone = false;
    boolean isASMDone = false;
    boolean isMIODone = false;
    boolean isMBEDone = false;


    boolean isCustomerList = false;
    boolean isCustStationDone = false;
    boolean isProviderType = false;
    boolean isProductDone = false;
    boolean isGiftProductDone = false;
    boolean isPromoDone = false;
    boolean isQuotedpriceDone = false;
    boolean isBrandDone = false;
    boolean isDoctorDone = false;

    boolean isDayDone = false;
    boolean isDeliveryDayDone = false;
    boolean isWorkTypeDone = false;
    boolean isTransportDone = false;


    String CurrentYear;
    String empcode;
    String emprole;
    ProgressDialog pd;
    int empId, year;
    ModelSession session;
    boolean customerDone, doctorDone, productDone, typeDone,seedDone;
    private final Set<String> completedTasks = new LinkedHashSet<>();
    private final Set<String> failedTasks = new LinkedHashSet<>();
    private boolean syncFinalized = false;
    private boolean syncFailureShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySyncFromServerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenterSyncMaster = new PresenterSyncMaster(this, SyncFromServerActivity.this);
        dbSyncHelper = new DBSyncHelper(SyncFromServerActivity.this);
        dbCrudHelper = new DBCrudHelper(SyncFromServerActivity.this);
        ModelSession session = dbCrudHelper.getSession();
        if (!NetworkInformation.isConnected(this)) {
            NoInternet();
        } else {
            year = Integer.parseInt(CurrentYear = new SimpleDateFormat("yyyy", Locale.US).format(new Date()));
            callRunSync(session.getEmpInfoId(), year, session.getEmpMasterCode(), session.getEmpRole());
        }
        updateProgressBar();
    }

    private void callRunSync(int empId, int year, String empcode, String emprole) {
     /*   pd = new ProgressDialog(SyncFromServerActivity.this);
        pd.setMessage("Please wait Synchronizing....");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();*/
        presenterSyncMaster.callNSM(empcode, emprole);
        presenterSyncMaster.callDZSM(empcode, emprole);
        presenterSyncMaster.callAM(empcode, emprole);
        presenterSyncMaster.callMIO(empcode, emprole);
        presenterSyncMaster.callMBE(empcode, emprole);
        presenterSyncMaster.callGroup(empcode, emprole);
        presenterSyncMaster.callZone(empcode, emprole);
        presenterSyncMaster.callArea(empcode, emprole);
        presenterSyncMaster.callTeritory(empcode, emprole);
        presenterSyncMaster.callSTeritory(empcode, emprole);
        presenterSyncMaster.callMarket(empcode, emprole);

        presenterSyncMaster.callDivision();
        presenterSyncMaster.callDistrict();
        presenterSyncMaster.callThana();

        presenterSyncMaster.callDoctor(empId);
        presenterSyncMaster.callDegree(empId);
        presenterSyncMaster.callSpeciality();
        presenterSyncMaster.callDoctorDesignation();
        presenterSyncMaster.callGender();
        presenterSyncMaster.callPracticeShift();

        presenterSyncMaster.callContactType();
        presenterSyncMaster.callBrand(empId);
        presenterSyncMaster.callPromo(empId);
        presenterSyncMaster.callDoctorCategory(empId);

        presenterSyncMaster.callProviderType();
        presenterSyncMaster.callCustomer(empId);
        presenterSyncMaster.callCustomerStation(empId);


        presenterSyncMaster.callProduct(empId);
        presenterSyncMaster.callQuotedPrice(empId);
        presenterSyncMaster.callProductGift(empId);
        presenterSyncMaster.callDay();
        presenterSyncMaster.callDeliveryDay();
        presenterSyncMaster.callWorkType();
        presenterSyncMaster.callTransportList();

        presenterSyncMaster.cllTourPurpose(empId, year);
        presenterSyncMaster.cllVisitType(empId, year);

    }

    private void updateProgressBar() {
        int displayProgress = prog;
        if (!completedTasks.isEmpty()) {
            displayProgress = Math.min(100, (completedTasks.size() * 100) / TOTAL_SYNC_TASKS);
        }
        binding.splashProgress.setProgress(displayProgress);
        binding.progtext.setText(String.valueOf(displayProgress + "%"));
    }

    private void hitMain() {
        if (syncFinalized) {
            return;
        }
        if (completedTasks.size() < TOTAL_SYNC_TASKS) {
            return;
        }
        if (!failedTasks.isEmpty()) {
            showSyncFailedDialog();
            return;
        }
        syncFinalized = true;
        AllSyncDone();
    }

    private void markSyncStep(String taskName, boolean success) {
        if (!REQUIRED_SYNC_TASKS.contains(taskName)) {
            return;
        }
        if (completedTasks.contains(taskName)) {
            if (!success) {
                failedTasks.add(taskName);
            }
            return;
        }
        completedTasks.add(taskName);
        if (!success) {
            failedTasks.add(taskName);
        }
        prog = Math.min(100, (completedTasks.size() * 100) / TOTAL_SYNC_TASKS);
        updateProgressBar();
        if (completedTasks.size() == TOTAL_SYNC_TASKS) {
            hitMain();
        }
    }

    private void showSyncFailedDialog() {
        if (syncFailureShown || isFinishing()) {
            return;
        }
        syncFailureShown = true;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SyncFromServerActivity.this);
        alertDialogBuilder.setIcon(R.drawable.ic_box);
        alertDialogBuilder.setTitle("Sync Failed");
        alertDialogBuilder.setMessage(buildFailedTaskMessage());
        alertDialogBuilder.setPositiveButton("Retry",
                (dialog, which) -> {
                    finish();
                    startActivity(getIntent());
                });
        alertDialogBuilder.setNegativeButton("Close",
                (dialog, which) -> finish());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private String buildFailedTaskMessage() {
        if (failedTasks.isEmpty()) {
            return "Some sync items did not finish. Please try again.";
        }
        StringBuilder builder = new StringBuilder("Please retry sync.\nFailed items: ");
        boolean first = true;
        for (String task : failedTasks) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(task);
            first = false;
        }
        return builder.toString();
    }

    @Override
    public void onGetNSM(String a, boolean t) {
        isNSMDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isNSMDone && isRSMDone && isASMDone
                && isMIODone && isMBEDone) {
            isFieldforceDone = true;
            hitMain();
            binding.masterAlldoneTxt.setText("----Complete");
        }
        markSyncStep("NSM", t);
    }
    @Override
    public void onGetDZSM(String a, boolean t) {
        isRSMDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isNSMDone && isRSMDone && isASMDone
                && isMIODone && isMBEDone) {
            isFieldforceDone = true;
            hitMain();
            binding.masterAlldoneTxt.setText("----Complete");
        }
        markSyncStep("DZSM", t);
    }
    @Override
    public void onGetAM(String a, boolean t) {
        isASMDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isNSMDone && isRSMDone && isASMDone
                && isMIODone && isMBEDone) {
            isFieldforceDone = true;
            hitMain();
            binding.masterAlldoneTxt.setText("----Complete");
        }
        markSyncStep("AM", t);
    }
    @Override
    public void onGetMIO(String a, boolean t) {
        isMIODone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isNSMDone && isRSMDone && isASMDone
                && isMIODone && isMBEDone) {
            isFieldforceDone = true;
            hitMain();
            binding.masterAlldoneTxt.setText("----Complete");
        }
        markSyncStep("MIO", t);
    }
    @Override
    public void onGetMBE(String a, boolean t) {
        isMBEDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isNSMDone && isRSMDone && isASMDone
                && isMIODone && isMBEDone) {
            isFieldforceDone = true;
            hitMain();
            binding.masterAlldoneTxt.setText("----Complete");
        }
        markSyncStep("MBE", t);
    }
    @Override
    public void onGetGroup(String a, boolean t) {
        binding.groupTxt.setText(a);
        isGroupDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isGroupDone && isRegionDone && isAreaDone
                && isTeritorryDone && isSubTeritorryDone && isMarketDone) {
            isMarketStructureDone = true;
            hitMain();
            binding.areaAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Group", t);
    }
    @Override
    public void onGetZone(String a, boolean t) {
        binding.regionTxt.setText(a);
        isRegionDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isGroupDone && isRegionDone && isAreaDone
                && isTeritorryDone && isSubTeritorryDone && isMarketDone) {
            isMarketStructureDone = true;
            hitMain();
            binding.areaAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Zone", t);
    }
    @Override
    public void onGetArea(String a, boolean t) {
        binding.areaTxt.setText(a);
        isAreaDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isGroupDone && isRegionDone && isAreaDone
                && isTeritorryDone && isSubTeritorryDone && isMarketDone) {
            isMarketStructureDone = true;
            hitMain();
            binding.areaAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Area", t);
    }
    @Override
    public void onGetTeritory(String a, boolean t) {
        binding.teritoryTxt.setText(a);
        isTeritorryDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isGroupDone && isRegionDone && isAreaDone
                && isTeritorryDone && isSubTeritorryDone && isMarketDone) {
            isMarketStructureDone = true;
            hitMain();
            binding.areaAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Territory", t);
    }
    @Override
    public void onGetSTeritory(String a, boolean t) {
        binding.steritoryTxt.setText(a);
        isSubTeritorryDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isGroupDone && isRegionDone && isAreaDone
                && isTeritorryDone && isSubTeritorryDone && isMarketDone) {
            isMarketStructureDone = true;
            hitMain();
            binding.areaAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Sub Territory", t);
    }
    @Override
    public void onGetMarket(String a, boolean t) {
        binding.marketTxt.setText(a);
        isMarketDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isGroupDone && isRegionDone && isAreaDone
                && isTeritorryDone && isSubTeritorryDone && isMarketDone) {
            isMarketStructureDone = true;
            hitMain();
            binding.areaAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Market", t);
    }
    @Override
    public void onGetCustomer(String a, boolean t) {
        binding.custsTxt.setText(a);
        isCustomerList = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isCustomerList && isCustStationDone && isProviderType) {
            customerDone = true;
            binding.custAlldoneTxt.setText("----Complete");
            hitMain();
        }
        markSyncStep("Customer", t);
    }
    @Override
    public void onGetCustomerStation(String a, boolean t) {
        binding.custstationTxt.setText(a);
        isCustStationDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isCustomerList && isCustStationDone && isProviderType) {
            customerDone = true;
            binding.custAlldoneTxt.setText("----Complete");
            hitMain();
        }
        markSyncStep("Customer Station", t);
    }
    @Override
    public void onGetDoctor(String a, boolean t) {
        isDoctorDone = t;
        System.out.println("1.DoctorDone"+isDoctorDone);

        if (isDoctorDone /*&& isDesignationDone && isDoctorDegreeDone
                && isDoctorSpecilityDone && isDoctorSpecilDAyDone && isDoctorInstitutionDone && isChamberType &&
                isChamberName && isBrand && isDocCategory*/ /*&& isProgramType*/) {
            doctorDone=true;
            //viewBindings.docAlldoneTxt.setText("----Complete");
            hitMain();
        }
        markSyncStep("Doctor", t);
    }
    @Override
    public void onGetDegree(String a, boolean t) {
        markSyncStep("Degree", t);
    }
    @Override
    public void onGetSpeciality(String a, boolean t) {
        markSyncStep("Speciality", t);
    }
    @Override
    public void onGetDoctorDesignation(String a, boolean t) {
        markSyncStep("Doctor Designation", t);
    }
    @Override
    public void onGetBrand(String a, boolean t) {
        isBrandDone = t;
        binding.brandTxt.setText(a);
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isBrandDone && isPromoDone && isProductDone && isQuotedpriceDone && isGiftProductDone) {
            productDone = true;
            hitMain();
            binding.productAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Brand", t);
    }

    @Override
    public void onGetPromo(String a, boolean t) {
        isPromoDone = t;
        if (isBrandDone && isPromoDone && isProductDone && isQuotedpriceDone && isGiftProductDone) {
            productDone = true;
            hitMain();
            binding.productAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Promo", t);
    }

    @Override
    public void onGetPriority(String a, boolean t) {

    }
    @Override
    public void onGetDoctorCategory(String a, boolean t) {
        markSyncStep("Doctor Category", t);
    }
    @Override
    public void onGetGender(String a, boolean t) {
        markSyncStep("Gender", t);
    }
    @Override
    public void onGetPracticeShift(String a, boolean t) {
        markSyncStep("Practice Shift", t);
    }
    @Override
    public void onGetUserRole(String a, boolean t) {

    }
    @Override
    public void onGetUserByRole(String a, boolean t) {

    }
    @Override
    public void onGetProduct(String a, boolean t) {
        isProductDone = t;
        binding.productsTxt.setText(a);
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isBrandDone && isPromoDone && isProductDone && isQuotedpriceDone && isGiftProductDone) {
            productDone = true;
            hitMain();
            binding.productAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Product", t);
    }
    @Override
    public void onGetProductSample(String a, boolean t) {

    }
    @Override
    public void onGetProductGift(String a, boolean t) {
        isGiftProductDone= t;
        if (isBrandDone && isPromoDone && isProductDone && isQuotedpriceDone && isGiftProductDone) {
            productDone = true;
            hitMain();
            binding.productAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Product Gift", t);
    }
    @Override
    public void onGetQuotedPrice(String a, boolean t) {
        isQuotedpriceDone = t;
        binding.qpriceTxt.setText(a);
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isBrandDone && isPromoDone && isProductDone && isQuotedpriceDone && isGiftProductDone) {
            productDone = true;
            hitMain();
            binding.productAlldoneTxt.setText("----Complete");
        }
        markSyncStep("Quoted Price", t);
    }
    @Override
    public void onGetDoctorType(String a, boolean t) {

    }

    @Override
    public void onGetCustomerType(String a, boolean t) {

    }

    @Override
    public void onGetDoctorContactType(String a, boolean t) {
        markSyncStep("Contact Type", t);
    }

    @Override
    public void onGetExpenseType(String a, boolean t) {

    }

    @Override
    public void onGetLeaveType(String a, boolean t) {

    }

    @Override
    public void onGetPrescriptionType(String a, boolean t) {

    }

    @Override
    public void onGetNonEffectivereason(String a, boolean t) {

    }

    @Override
    public void onGetTransportList(String a, boolean t) {
        isTransportDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 1;
                updateProgressBar();
            }
        }
        if (isDayDone && isDeliveryDayDone && isWorkTypeDone && isTransportDone) {
            seedDone = true;
            hitMain();
        }
        markSyncStep("Transport", t);
    }

    @Override
    public void onGetTourPurpose(String a, boolean t) {
        markSyncStep("Tour Purpose", t);
    }

    @Override
    public void onGetVisitType(String a, boolean t) {
        markSyncStep("Visit Type", t);
    }

    @Override
    public void onGetDivision(String a, boolean t) {
        markSyncStep("Division", t);
    }

    @Override
    public void onGetDistrict(String a, boolean t) {
        markSyncStep("District", t);
    }

    @Override
    public void onGetThana(String a, boolean t) {
        markSyncStep("Thana", t);
    }

    @Override
    public void onGetProviderType(String a, boolean t) {
        binding.progtypeTxt.setText(a);
        isProviderType = t;
        if (t) {
            if (prog <= 95) {
                prog += 5;
                updateProgressBar();
            }
        }
        if (isCustomerList && isCustStationDone && isProviderType) {
            customerDone = true;
            binding.custAlldoneTxt.setText("----Complete");
            hitMain();
        }
        markSyncStep("Provider Type", t);
    }

    @Override
    public void onGetDay(String a, boolean t) {
        isDayDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 1;
                updateProgressBar();
            }
        }
        if (isDayDone && isDeliveryDayDone && isWorkTypeDone && isTransportDone) {
            seedDone = true;
            hitMain();
        }
        markSyncStep("Day", t);
    }

    @Override
    public void onGetDeliveryDay(String a, boolean t) {
        isDeliveryDayDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 1;
                updateProgressBar();
            }
        }
        if (isDayDone && isDeliveryDayDone && isWorkTypeDone && isTransportDone)  {
            seedDone = true;
            hitMain();
        }
        markSyncStep("Delivery Day", t);
    }

    @Override
    public void onGetWorkType(String a, boolean t) {
        isWorkTypeDone = t;
        if (t) {
            if (prog <= 95) {
                prog += 2;
                updateProgressBar();
            }
        }
        if (isDayDone && isDeliveryDayDone && isWorkTypeDone && isTransportDone) {
            seedDone = true;
            hitMain();
        }
        markSyncStep("Work Type", t);

    }

    public void AllSyncDone() {
        try {
            String today = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            String todaytime = dateFormat.format(new Date());
            int val = 1;
            boolean isTrue = dbSyncHelper.Insert_InitTable(today, todaytime);
            if (isTrue) {
                  /*  if(pd!=null ||pd.isShowing())
                    {
                        pd.dismiss();
                    }*/
                System.out.println("AllSync Done!!!!");
                Intent i = new Intent(SyncFromServerActivity.this, MainActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                 /*  Handler handler = new Handler();
                   handler.postDelayed(() -> {
                       System.out.println("AllSync Done!!!!");
                       Intent i = new Intent(SyncFromServerActivity.this, MainActivity.class);
                       i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(i);
                       overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }, 2000);*/
            } else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(), "Not Inserting Initializer!!");
            }

        } catch (Exception ex) {
               /* if(pd!=null ||pd.isShowing())
                {
                    pd.dismiss();
                }*/
            ex.printStackTrace();
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Caught An Exception!!");
        }


    }

    public void NoInternet() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SyncFromServerActivity.this);
        alertDialogBuilder.setIcon(R.drawable.ic_box);
        alertDialogBuilder.setTitle("No Internet Connection!!!");
        alertDialogBuilder.setMessage("Please Connect internet and then press Done");
        alertDialogBuilder.setPositiveButton("Done",
                (arg0, arg1) -> {
                    finish();
                    startActivity(getIntent());
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // pd.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // pd.dismiss();
    }
}
