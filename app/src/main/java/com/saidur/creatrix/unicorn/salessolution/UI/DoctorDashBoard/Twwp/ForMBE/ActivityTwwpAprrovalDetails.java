package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.Adapter.AdapterTwwpApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpApprovalMaster;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTwwpAprrovalDetailsBinding;

public class ActivityTwwpAprrovalDetails extends AppCompatActivity {
    ActivityTwwpAprrovalDetailsBinding binding;
    ModelTwwpApprovalMaster aInfoData;
    AdapterTwwpApproval adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTwwpAprrovalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(view -> {
            finish();
        });
        String who=getIntent().getStringExtra("Who");
        if(who.equals("Report"))
        {
            binding.toolbarTitle.setText("Twwp Details");
        }

        Gson gson = new Gson();
        aInfoData = gson.fromJson(getIntent().getStringExtra("Data"), ModelTwwpApprovalMaster.class);
        LoadView(aInfoData);
    }

    private void LoadView(ModelTwwpApprovalMaster aInfoData) {

        if (aInfoData != null) {
            adapter = new AdapterTwwpApproval(ActivityTwwpAprrovalDetails.this, aInfoData.getTwwp_App());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.rvTwwpDtails.setLayoutManager(mLayoutManager);
            binding.rvTwwpDtails.setItemAnimator(new DefaultItemAnimator());
            binding.rvTwwpDtails.setAdapter(adapter);
            binding.rvTwwpDtails.setItemAnimator(null);
            binding.rvTwwpDtails.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        }
    }
}