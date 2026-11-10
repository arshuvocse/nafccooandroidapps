package com.saidur.creatrix.unicorn.salessolution.UI.Welcome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ProgressBar;

import com.saidur.creatrix.unicorn.salessolution.Interface.IVersionUpdate;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelAppVersion;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterVersion;
import com.saidur.creatrix.unicorn.salessolution.UI.Authentication.LoginActivity;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements IVersionUpdate.View {
    ActivitySplashBinding binding;
    ProgressBar splashProgress;
    int SPLASH_TIME = 1500; //Minimum splash duration, also used as the version-check fallback timeout

    private final Handler handler = new Handler();
    private final Runnable fallbackNavigate = this::proceedToLogin;
    private boolean navigated = false;
    private AlertDialog updateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Fail-open: if the version-check call is slow/unreachable, proceed to Login anyway
        // after SPLASH_TIME instead of blocking the user (bad network shouldn't lock the app).
        handler.postDelayed(fallbackNavigate, SPLASH_TIME);

        new PresenterVersion(this).GetActiveVersion();
    }

    @Override
    public void onVersionGet(ModelAppVersion appVersion) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        int installedVersionCode = getInstalledVersionCode();
        if (appVersion != null && appVersion.getVersion() > installedVersionCode) {
            handler.removeCallbacks(fallbackNavigate);
            showUpdateDialog(appVersion);
        }
        // else: already on the latest (or newer, e.g. debug build) version -- let the
        // fallback handler continue on to Login as normal, no dialog needed.
    }

    @Override
    public void onVersionCheckFailed() {
        // fail-open: let the fallback handler proceed to Login as normal
    }

    private void showUpdateDialog(ModelAppVersion appVersion) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        boolean forceUpdate = appVersion.isForceUpdate();
        String versionName = appVersion.getVersionName() == null ? "" : appVersion.getVersionName();

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(forceUpdate ? "Update Required" : "Update Available")
                .setMessage(forceUpdate
                        ? "A new version" + (versionName.isEmpty() ? "" : " (" + versionName + ")")
                            + " is available. Please update to continue using the app."
                        : "A new version" + (versionName.isEmpty() ? "" : " (" + versionName + ")")
                            + " is available. Update now for the latest features and fixes.")
                .setCancelable(!forceUpdate)
                .setPositiveButton("Update Now", (dialog, which) -> openDownloadLink(appVersion.getDownloadUrl()));

        if (!forceUpdate) {
            builder.setNegativeButton("Later", (dialog, which) -> {
                dialog.dismiss();
                proceedToLogin();
            });
        }

        updateDialog = builder.create();
        updateDialog.setCanceledOnTouchOutside(false);
        if (forceUpdate) {
            // Block the back button so a mandatory update can't be dismissed without updating.
            updateDialog.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        }
        updateDialog.show();
    }

    private void openDownloadLink(String downloadUrl) {
        String url = TextUtils.isEmpty(downloadUrl)
                ? "https://play.google.com/store/apps/details?id=" + getPackageName()
                : downloadUrl;
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private int getInstalledVersionCode() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // Can't verify our own version -- fail-safe by never blocking on this.
            return Integer.MAX_VALUE;
        }
    }

    private void proceedToLogin() {
        if (navigated || isFinishing() || isDestroyed()) {
            return;
        }
        navigated = true;
        try {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(fallbackNavigate);
        if (updateDialog != null && updateDialog.isShowing()) {
            updateDialog.dismiss();
        }
        super.onDestroy();
    }
}
