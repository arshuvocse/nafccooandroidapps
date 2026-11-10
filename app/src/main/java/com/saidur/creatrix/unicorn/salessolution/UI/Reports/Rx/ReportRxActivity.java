package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Rx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Dashboard.DashBoardFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.ReportsFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.Settings.SettingsFragment;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityReportRxBinding;

public class ReportRxActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityReportRxBinding binding;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportRxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState==null){
            fragmentManager = getSupportFragmentManager();
            FragmentRxDetails detailsFragment = new FragmentRxDetails();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.frameLayout,detailsFragment).commit();
        }


        binding.btnDetails.setOnClickListener(this);
        binding.btnSummary.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_details:
                fragment = new FragmentRxDetails();
                binding.btnDetails.setBackgroundResource(R.drawable.shape_rect);
                binding.btnSummary.setBackgroundResource(0);

                binding.tvDetails.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvSummary.setTextColor(Color.parseColor("#000000"));

                break;
            case R.id.btn_summary:
                fragment = new FragmentRxSummary();
                binding.btnSummary.setBackgroundResource(R.drawable.shape_rect);
                binding.btnDetails.setBackgroundResource(0);
                binding.btnSummary.setPadding(10,10,10,10);

                binding.tvSummary.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvDetails.setTextColor(Color.parseColor("#000000"));

                break;
        }
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.frameLayout, fragment).commit();
        }
    }
}