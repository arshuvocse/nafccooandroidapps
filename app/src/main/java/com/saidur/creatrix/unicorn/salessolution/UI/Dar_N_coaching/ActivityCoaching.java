package com.saidur.creatrix.unicorn.salessolution.UI.Dar_N_coaching;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityCoachingBinding;

public class ActivityCoaching extends AppCompatActivity {
ActivityCoachingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCoachingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(view -> {
            finish();
        });
    }
}