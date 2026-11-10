package com.saidur.creatrix.unicorn.salessolution.Service;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationPermissionCheck {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 9001;
    private static AlertDialog activeDialog;

    public static void CheckPermission(Activity activity) {
        if (activity == null || activity.isFinishing()) return;

        boolean permissionGranted = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        boolean gpsEnabled = locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!permissionGranted) {
            showMandatoryDialog(activity,
                    "This app needs location permission to work. Please grant location permission to continue.",
                    "Grant Permission",
                    (dialog, id) -> ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION));
        } else if (!gpsEnabled) {
            showMandatoryDialog(activity,
                    "Location is turned off on your device. Please enable location to continue using this app.",
                    "Enable Location",
                    (dialog, id) -> activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        } else {
            dismiss();
        }
    }

    private static void showMandatoryDialog(Activity activity, String message, String buttonText,
                                             DialogInterface.OnClickListener onClick) {
        if (activeDialog != null && activeDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Location is mandatory!");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(buttonText, onClick);
        activeDialog = builder.create();
        activeDialog.show();
    }

    public static void dismiss() {
        if (activeDialog != null) {
            if (activeDialog.isShowing()) {
                activeDialog.dismiss();
            }
            activeDialog = null;
        }
    }
}
