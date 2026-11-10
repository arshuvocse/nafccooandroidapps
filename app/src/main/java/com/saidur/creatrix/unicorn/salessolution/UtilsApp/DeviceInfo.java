package com.saidur.creatrix.unicorn.salessolution.UtilsApp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;

import java.util.Locale;

public class DeviceInfo {
    public static String IMEI_NUMBER = "";
    public static String ANDROID_VERSION = "";
    public static String DEVICE_INFO = "";

    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        if (AppSetting.isEmptyString(IMEI_NUMBER)) {
            if (context != null) {
                IMEI_NUMBER = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
               // Toast.makeText(context, "1.Android  id: "+IMEI_NUMBER, Toast.LENGTH_SHORT).show();
                if (AppSetting.isEmptyString(IMEI_NUMBER)) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                                == PackageManager.PERMISSION_GRANTED) {
                            TelephonyManager telephonyManager =
                                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            if (telephonyManager != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    IMEI_NUMBER = telephonyManager.getImei();
                                   // Toast.makeText(context, "2.telephonyManager Imei: "+IMEI_NUMBER, Toast.LENGTH_SHORT).show();
                                } else {
                                    IMEI_NUMBER = telephonyManager.getDeviceId();
                                    //Toast.makeText(context, "3.telephonyManager Device id: "+IMEI_NUMBER, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }
        if (IMEI_NUMBER == null) {
            IMEI_NUMBER = "";
        }
        return IMEI_NUMBER;
    }

    public static String getAndroidVersion() {
        if (AppSetting.isEmptyString(ANDROID_VERSION)) {
            String release = Build.VERSION.RELEASE;
            int sdk = Build.VERSION.SDK_INT;
            ANDROID_VERSION = String.format(Locale.US, "%d (%s)", sdk, release);
        }
        return ANDROID_VERSION;
    }


    public static String getDeviceInfo() {
        if (AppSetting.isEmptyString(DEVICE_INFO)) {
            DEVICE_INFO = String.format(Locale.US, "%s (%s)", Build.MODEL, Build.MANUFACTURER);
        }
        return DEVICE_INFO;
    }


}
