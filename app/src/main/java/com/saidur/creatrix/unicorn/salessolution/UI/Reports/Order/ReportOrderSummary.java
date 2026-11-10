package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityReportOrderSummaryBinding;

public class ReportOrderSummary extends AppCompatActivity {
ActivityReportOrderSummaryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReportOrderSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}