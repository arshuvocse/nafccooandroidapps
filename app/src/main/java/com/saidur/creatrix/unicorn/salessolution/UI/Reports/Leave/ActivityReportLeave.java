package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Leave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityReportLeaveBinding;

public class ActivityReportLeave extends AppCompatActivity {
ActivityReportLeaveBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReportLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}