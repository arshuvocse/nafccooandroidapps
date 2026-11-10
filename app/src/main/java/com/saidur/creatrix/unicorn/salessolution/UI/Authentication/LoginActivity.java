package com.saidur.creatrix.unicorn.salessolution.UI.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBSyncHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ILogin;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelUser;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresentarLogin;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.Service.LocationPermissionCheck;
import com.saidur.creatrix.unicorn.salessolution.Service.LocationService;
import com.saidur.creatrix.unicorn.salessolution.UI.Dashboard.DashBoardFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.Sync.SyncFromServerActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.DeviceInfo;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityLoginBinding;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements ILogin.View {
    ActivityLoginBinding binding;
    PresentarLogin presentar;
    ProgressDialog pd;
    String imei, device;
    DBCrudHelper dbCrudHelper;
    DBSyncHelper dbSyncHelper;
    ModelSession session;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper = new DBCrudHelper(LoginActivity.this);
        dbSyncHelper = new DBSyncHelper(LoginActivity.this);
        presentar = new PresentarLogin(this, this);
        session = dbCrudHelper.getSession();
        if (session.getLoginName() != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        binding.vname.setText(GetVersionName());
        binding.poweredByLogo.setOnClickListener(view -> openCreatrixWebsite());
        binding.btnLogin.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
        binding.btnLogin.setOnClickListener(view -> HitLogin());
        //  location servics
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            startLocationService();
        }
    }

    private void openCreatrixWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://creatrixbd.com/"));
        startActivity(intent);
    }

    private void HitLogin() {
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Processing...");
        pd.show();
        HashMap<String, String> loginMap = new HashMap<>();
        if (!binding.inputLoginName.getText().toString().trim().equals("") && !binding.inputPassword.getText().toString().trim().equals("")) {

            if (NetworkInformation.isConnected(getApplicationContext())) {
                System.out.println("Imei:" + DeviceInfo.getIMEI(this));
                if (DeviceInfo.getIMEI(LoginActivity.this) == null) {
                    imei = "Unknown_imei_";
                } else {
                    imei = DeviceInfo.getIMEI(LoginActivity.this);
                }
                if (DeviceInfo.getDeviceInfo() == null) {
                    device = "Unknown";
                } else {
                    device = DeviceInfo.getDeviceInfo();
                }
                loginMap.put("UserName", binding.inputLoginName.getText().toString().trim());
                loginMap.put("password", binding.inputPassword.getText().toString().trim());
                loginMap.put("Imei", imei);
                loginMap.put("DeviceToken", "1.7");
                loginMap.put("Device", DeviceInfo.getDeviceInfo());
                loginMap.put("AppVersion", GetVersionCode());
                loginMap.put("OS", "Android");
                loginMap.put("OS_Version", DeviceInfo.getAndroidVersion());
                presentar.hitLogin(loginMap);

            } else {
                if (dbCrudHelper.checkSessonExist() == true) {
                    dbCrudHelper.getLoginProfile_SQLite();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    SnackBarManagement._error_CustomMessage(binding.getRoot(), "Incorrect User Id or Password.");
                }
            }
        } else {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Please provide User Id and Password");
        }


    }

    @Override
    public void OnSuccess(int userId, String userName, String empMasterco, String loginName, String password, int empId, String userCo, int IsImeiMatch, String versionName, String role, int roleTypeid, String roleType, int isAccepted, int isForward, String DesigName) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }


    }

    @Override
    public void OnError(String message) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        SnackBarManagement._error_CustomMessage(binding.getRoot(), message);
    }

    @Override
    public void OnLoginSuccess(ModelSession session) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        try {
            dbCrudHelper.InsertIntoLoginProfile_SQLite(session.getUserId(), session.getUserName(), session.getEmpMasterCode(), session.getLoginName(), session.getEmpInfoId(), session.getUserCo(), session.getEmpRole(), session.getRoleTypeId(), session.getRoleType(), 1, 1, session.getDesigName());

            if (!dbCrudHelper.CheckDataInTable("tblInitTable")) {
                try {
                    dbCrudHelper.InsertSessionInfo_SQLite(session);
                    Intent i = new Intent(LoginActivity.this, SyncFromServerActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                dbCrudHelper.InsertSessionInfo_SQLite(session);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isLocatioServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (LocationService.class.getName().equals(service.service.getClassName())) {
                    if (service.foreground) {
                        return true;
                    }
                }
            }
            return false;

        }
        return false;
    }

    private void startLocationService() {
        if (!isLocatioServiceRunning()) {
            Intent intent = new Intent(LoginActivity.this, LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        }
    }
    private String GetVersionCode() {
        String versioncode = "";
        try {
            PackageInfo pInfo = LoginActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versioncode = String.valueOf(pInfo.versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }
    private String GetVersionName() {
        String versioncode = "";
        try {
            PackageInfo pInfo = LoginActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versioncode = String.valueOf(pInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        LocationPermissionCheck.CheckPermission(this);
    }
}
