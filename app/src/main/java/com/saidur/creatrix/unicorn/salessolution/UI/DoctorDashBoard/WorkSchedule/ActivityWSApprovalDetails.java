package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.ActivityTwwpAprrovalDetails;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.Adapter.AdapterTwwpApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpApprovalMaster;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Adapter.AdapterWSApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSApprovalMaster;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTwwpAprrovalDetailsBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityWsapprovalDetailsBinding;

public class ActivityWSApprovalDetails extends AppCompatActivity {
    ActivityWsapprovalDetailsBinding binding;
    ModelWSApprovalMaster aInfoData;
    AdapterWSApproval adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWsapprovalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(view -> {
            finish();
        });
        String who=getIntent().getStringExtra("Who");
        if(who.equals("Report"))
        {
            binding.toolbarTitle.setText("Work Schedule Details");
        }
        Gson gson = new Gson();
        aInfoData = gson.fromJson(getIntent().getStringExtra("Data"), ModelWSApprovalMaster.class);
        LoadView(aInfoData);
    }
    private void LoadView(ModelWSApprovalMaster data) {

        binding.day.setText(data.getDay());
        binding.tvMttype.setText(data.getWSMorning_V().getTourType());
        binding.tvEttype.setText(data.getWSEvening_V().getTourType());

        binding.tvMwtype.setText(data.getWSMorning_V().getWorkType());
        binding.tvEwtype.setText(data.getWSEvening_V().getWorkType());

        binding.tvMdday.setText(data.getWSMorning_V().getDeliveryDayName());
        binding.tvEdday.setText(data.getWSEvening_V().getDeliveryDayName());

        binding.tvMteri.setText(data.getWSMorning_V().getTerritory());
        binding.tvEteri.setText(data.getWSEvening_V().getTerritory());

        binding.tvMstime.setText(data.getWSMorning_V().getStartTime());
        binding.tvEstime.setText(data.getWSEvening_V().getStartTime());

        binding.tvMspoint.setText(data.getWSMorning_V().getStartPoint());
        binding.tvEspoint.setText(data.getWSEvening_V().getStartPoint());
        binding.tvMepoint.setText(data.getWSMorning_V().getEndPoint());
        binding.tvEepoint.setText(data.getWSEvening_V().getEndPoint());
        binding.tvMetime.setText(data.getWSMorning_V().getEndTime());
        binding.tvEetime.setText(data.getWSEvening_V().getEndTime());

    }
}