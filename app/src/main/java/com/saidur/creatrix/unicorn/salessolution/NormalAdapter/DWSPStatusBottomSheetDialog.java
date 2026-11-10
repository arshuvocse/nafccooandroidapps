package com.saidur.creatrix.unicorn.salessolution.NormalAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.DWSPTotals;
import com.saidur.creatrix.unicorn.salessolution.databinding.BottomSheetDwspstatusBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DWSPStatusBottomSheetDialog extends BottomSheetDialogFragment {
    BottomSheetDwspstatusBinding binding;
    BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetDwspstatusBinding.inflate(getLayoutInflater());
        //View v = inflater.inflate(R.layout.bottom_sheet_tourstatus,container);
        Bundle mArgs = getArguments();
        int empId = mArgs.getInt("empId");
        int monthValue = mArgs.getInt("monthValue");
        int year = mArgs.getInt("year");
        GetDWSPTotalsData(monthValue, year, empId);
        binding.psubmitBnt.setOnClickListener(v -> {
            String rmrks = binding.remarksTxt.getText().toString();
            mListener.FinalSubmitClick(monthValue, year, empId, "rmrks");
            dismiss();
        });
        return binding.getRoot();
    }

    public void GetDWSPTotalsData(int month, int year, int empId) {
        try {
//            CalculationApiCall service = RetrofitClientInstance.getRetrofitInstance().create(CalculationApiCall.class);
//            Call<List<DWSPTotals>> call = service.GetDWSPTotals(month, year, empId);
//            call.enqueue(new Callback<List<DWSPTotals>>() {
//                @Override
//                public void onResponse(@NonNull Call<List<DWSPTotals>> call, @NonNull Response<List<DWSPTotals>> response) {
//                    if (response.body() != null) {
//                        SetinView(response.body().get(0));
//                    }else {
//                        SetinView(null);
//                    }
//                }
//                @Override
//                public void onFailure(@NonNull Call<List<DWSPTotals>> call, @NonNull Throwable t) {
//                    {
//                        SetinView(null);
//                    }
//                }
//            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getLocalizedMessage());
            System.out.println(ex.getStackTrace());
        }
    }

    private void SetinView(DWSPTotals body) {
        if(body!=null){
            binding.dwsptotaltarget.setText(String.valueOf(body.getTotalTarget()));
            binding.fcbvalue.setText(String.valueOf(body.getTotalFcb()));
            binding.gvalue.setText(String.valueOf(body.getTotalGeneral()));
            binding.cvalue.setText(String.valueOf(body.getTotalCampaign()));

            if(body.isFinalSubmit()==false){
                binding.statusTxt.setText("Final Submit Pending");
                if(body.getCheckInfo()==0)
                {
                    binding.lnhide.setVisibility(View.GONE);
                }else {
                    binding.lnhide.setVisibility(View.VISIBLE);
                }

            }else{
                if(body.getApprovalStatus().equals("0")){
                    binding.statusTxt.setText("Final Submit Done - Approval Pending");
                    binding.lnhide.setVisibility(View.GONE);
                }
                if(body.getApprovalStatus().equals("2")){
                    binding.statusTxt.setText("DWSP Is Approved");
                    binding.lnhide.setVisibility(View.GONE);
                }
                if(body.getApprovalStatus().equals("3")){
                    binding.statusTxt.setText("DWSP Is DisApprove");
                    binding.lnhide.setVisibility(View.VISIBLE);
                }
            }
        }

    }


    public interface BottomSheetListener {
        void FinalSubmitClick(int month, int year, int empId, String remarks);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement BottomListener");

        }
    }
}



