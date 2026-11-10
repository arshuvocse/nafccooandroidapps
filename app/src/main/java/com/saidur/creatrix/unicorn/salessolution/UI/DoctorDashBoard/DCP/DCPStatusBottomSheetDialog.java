package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDcpMaster;
import com.saidur.creatrix.unicorn.salessolution.databinding.BottomSheetDcpstatusBinding;

import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DCPStatusBottomSheetDialog extends BottomSheetDialogFragment {
    BottomSheetDcpstatusBinding binding;
    BottomSheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= BottomSheetDcpstatusBinding.inflate(getLayoutInflater());
        Bundle mArgs = getArguments();
        int empId = mArgs.getInt("empId");
        int monthValue = mArgs.getInt("monthValue");
        int year = mArgs.getInt("year");
        int tourCount = mArgs.getInt("tourCount");
        String monStr = mArgs.getString("monthTxt");
        binding.tourCountTxt.setText(String.valueOf(tourCount));
        binding.monthTxt.setText(monStr + ',' + String.valueOf(year));
        GetDCPMasterData(monthValue,year,empId);
        binding.psubmitBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rmrks = binding.remarksTxt.getText().toString();
                mListener.FinalSubmitClick(monthValue,year,empId,rmrks);
                dismiss();
            }
        });

        return binding.getRoot();
    }
    public void SetinView(ModelDcpMaster tpMaster){
       // ModelDcpMaster tpMaster = new ModelDcpMaster();
        binding.tourCountTxt.setText(tpMaster.getCoutPlan());
        try {
            String sd = "Ds";
            if(tpMaster!=null){
                if(tpMaster.isFinalSubmit()==false){
                    binding.statusTxt.setText("Final Submit Pending");
                    binding.lnhide.setVisibility(View.VISIBLE);
                }else{
                    if(tpMaster.getApprovalStatus().equals("0")){
                        binding.statusTxt.setText("Final Submit Done - Approval Pending");
                        binding.lnhide.setVisibility(View.GONE);
                    }
                    if(tpMaster.getApprovalStatus().equals("2")){
                        binding.statusTxt.setText("DCP Is Approved");
                        binding.lnhide.setVisibility(View.GONE);
                    }
                    if(tpMaster.getApprovalStatus().equals("3")){
                        binding.statusTxt.setText("DCP Is DisApprove");
                        if(tpMaster.getFinalSubmitRemarks() !=null){
                            binding.statusTxtRejected.setText(tpMaster.getFinalSubmitRemarks());
                        }
                        binding.lnhide.setVisibility(View.VISIBLE);
                    }
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }
    public void GetDCPMasterData(int month,int year,int empId){
        try{
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ModelDcpMaster> call = service.GetTDcpMasterData(month,year,empId);
            call.enqueue(new Callback<ModelDcpMaster>() {
                @Override
                public void onResponse(@NonNull Call<ModelDcpMaster> call, @NonNull Response<ModelDcpMaster> response) {
                    if(response.body()!=null)
                    {
                        SetinView(response.body());
                    }
                    else {

                    }
                }
                @Override
                public void onFailure(@NonNull Call<ModelDcpMaster> call, @NonNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                    }else{
                    }
                }
            });

        }catch (Exception ex){
        }
    }



    public interface  BottomSheetListener{
        void FinalSubmitClick(int month,int year,int empId,String remarks);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must implement BottomListener");

        }
    }
}



