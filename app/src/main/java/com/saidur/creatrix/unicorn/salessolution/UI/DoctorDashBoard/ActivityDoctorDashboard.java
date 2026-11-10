package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.ActivityDCP;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.ActivityDCR;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.ActivityTwwp;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDoctorDashboardBinding;

public class ActivityDoctorDashboard extends AppCompatActivity {
ActivityDoctorDashboardBinding binding;
ModelSession session;
DBCrudHelper dbCrudHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDoctorDashboardBinding.inflate(getLayoutInflater());
        dbCrudHelper=new DBCrudHelper(ActivityDoctorDashboard.this);
        setContentView(binding.getRoot());

        session=dbCrudHelper.getSession();
        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());
        //Role wise spinner populate
        try {
            switch (session.getRoleType()) {
                case "MBE":
                    binding.addDoctor.setVisibility(View.VISIBLE);
                    binding.addDcp.setVisibility(View.VISIBLE);
                    binding.addDcr.setVisibility(View.VISIBLE);
                    break;
                case "MIO":
                case "AM":
                    binding.addDoctor.setVisibility(View.VISIBLE);
                    binding.addDcp.setVisibility(View.GONE);
                    binding.addDcr.setVisibility(View.GONE);
                    break;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        binding.addDoctor.setOnClickListener(v -> {
            Intent i = new Intent(ActivityDoctorDashboard.this, ActivityDoctorEntry.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.addDcp.setOnClickListener(v -> {
            Intent i = new Intent(ActivityDoctorDashboard.this, ActivityDCP.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.addDcr.setOnClickListener(v -> {
            Constants.From="Doctor_DASHBOARD";
            Intent i = new Intent(ActivityDoctorDashboard.this, ActivityDCR.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}