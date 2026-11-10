package com.saidur.creatrix.unicorn.salessolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.saidur.creatrix.unicorn.salessolution.UI.Dashboard.DashBoardFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.ReportsFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.Settings.SettingsFragment;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityMainBinding;
import com.saidur.creatrix.unicorn.salessolution.webview.DashboardOverviewFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            DashBoardFragment dashFragment = new DashBoardFragment();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainContainer, dashFragment).commit();
            updateBottomNav(0);
        }
        fun_bottomClick();
    }

    private void fun_bottomClick() {
        binding.btnOperation.setOnClickListener(MainActivity.this);
        binding.btnDashboard.setOnClickListener(MainActivity.this);
        binding.btnReports.setOnClickListener(MainActivity.this);
        binding.btnSettings.setOnClickListener(MainActivity.this);
    }

    private void updateBottomNav(int selectedTab) {
        // Tab 0: Operation, Tab 1: Dashboard, Tab 2: Reports, Tab 3: Settings

        // Icons
        binding.icOperation.setImageResource(selectedTab == 0 ? R.drawable.ic_nav_home_white : R.drawable.ic_nav_home_dim);
        binding.icDash.setImageResource(selectedTab == 1 ? R.drawable.ic_nav_dashboard_white : R.drawable.ic_nav_dashboard_dim);
        binding.icReports.setImageResource(selectedTab == 2 ? R.drawable.ic_nav_reports_white : R.drawable.ic_nav_reports_dim);
        binding.icSettings.setImageResource(selectedTab == 3 ? R.drawable.ic_nav_settings_white : R.drawable.ic_nav_settings_dim);

        // Text Colors & Typography
        binding.tvOperation.setTextColor(Color.parseColor(selectedTab == 0 ? "#FFFFFF" : "#B2DFDB"));
        binding.tvOperation.setTypeface(null, selectedTab == 0 ? Typeface.BOLD : Typeface.NORMAL);

        binding.tvDash.setTextColor(Color.parseColor(selectedTab == 1 ? "#FFFFFF" : "#B2DFDB"));
        binding.tvDash.setTypeface(null, selectedTab == 1 ? Typeface.BOLD : Typeface.NORMAL);

        binding.tvReports.setTextColor(Color.parseColor(selectedTab == 2 ? "#FFFFFF" : "#B2DFDB"));
        binding.tvReports.setTypeface(null, selectedTab == 2 ? Typeface.BOLD : Typeface.NORMAL);

        binding.tvSettings.setTextColor(Color.parseColor(selectedTab == 3 ? "#FFFFFF" : "#B2DFDB"));
        binding.tvSettings.setTypeface(null, selectedTab == 3 ? Typeface.BOLD : Typeface.NORMAL);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_operation:
                fragment = new DashBoardFragment();
                updateBottomNav(0);
                break;

            case R.id.btn_dashboard:
                fragment = new DashboardOverviewFragment();
                updateBottomNav(1);
                break;

            case R.id.btn_reports:
                fragment = new ReportsFragment();
                updateBottomNav(2);
                break;

            case R.id.btn_settings:
                fragment = new SettingsFragment();
                updateBottomNav(3);
                break;
        }
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainContainer, fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        if (currentFragment instanceof DashboardOverviewFragment) {
            DashboardOverviewFragment webFrag = (DashboardOverviewFragment) currentFragment;
            if (webFrag.canGoBack()) {
                webFrag.goBack();
                return;
            }
        }
        super.onBackPressed();
    }
}
