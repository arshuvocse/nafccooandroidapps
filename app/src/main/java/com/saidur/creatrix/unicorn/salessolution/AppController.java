package com.saidur.creatrix.unicorn.salessolution;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.saidur.creatrix.unicorn.salessolution.Service.LocationPermissionCheck;
import com.saidur.creatrix.unicorn.salessolution.UI.Welcome.SplashActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkStatusBanner;

public class AppController extends Application implements Application.ActivityLifecycleCallbacks {

    private Activity currentActivity;
    private BroadcastReceiver networkReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);

        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateNetworkBanner();
            }
        };
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void updateNetworkBanner() {
        if (currentActivity == null || currentActivity.isFinishing()) return;
        if (NetworkInformation.isConnected(currentActivity)) {
            NetworkStatusBanner.hide(currentActivity);
        } else {
            NetworkStatusBanner.show(currentActivity, "No network connection");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
        updateNetworkBanner();
        if (!(activity instanceof SplashActivity)) {
            LocationPermissionCheck.CheckPermission(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LocationPermissionCheck.dismiss();
        if (currentActivity == activity) {
            currentActivity = null;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
