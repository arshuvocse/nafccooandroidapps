package com.saidur.creatrix.unicorn.salessolution.NormalAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saidur.creatrix.unicorn.salessolution.Model.VisitPlanMaster;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.databinding.BottomSheetTourstatusBinding;

import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorTourStatusBottomSheetDialog  extends BottomSheetDialogFragment {
    BottomSheetTourstatusBinding binding;
    TextView statusTxt ;
    TextView statusTxt_rejected ;
    LinearLayout lnhide;
    BottomSheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View v = inflater.inflate(R.layout.bottom_sheet_tourstatus,container);
       binding=BottomSheetTourstatusBinding.inflate(getLayoutInflater());
        Bundle mArgs = getArguments();
        int empId = mArgs.getInt("empId");
        int monthValue = mArgs.getInt("monthValue");
        int year = mArgs.getInt("year");
        int tourCount = mArgs.getInt("visitCount");
        String monStr = mArgs.getString("monthTxt");

        binding.tourCountTxt.setText(String.valueOf(tourCount));
        binding.monthTxt.setText(monStr + ',' + String.valueOf(year));
        GetTourPlanMasterData(monthValue,year,empId);

        binding.psubmitBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rmrks =  binding.remarksTxt.getText().toString();
                mListener.FinalSubmitClick(monthValue,year,empId,rmrks);
                dismiss();
            }
        });
        return binding.getRoot();
    }

    public void SetinView(List<VisitPlanMaster> aList){
        VisitPlanMaster tpMaster = new VisitPlanMaster();
        if(aList!=null){
            try {
                tpMaster = aList.get(0);
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
                            binding.statusTxt.setText("Visit Plan Approved");
                            binding.lnhide.setVisibility(View.GONE);
                        }
                        if(tpMaster.getApprovalStatus().equals("3")){
                            binding.statusTxt.setText("Visit Plan Rejected");
                            if(tpMaster.getApprovalRemarks() !=null){
                                binding.statusTxtRejected.setText(tpMaster.getApprovalRemarks());

                            }
                            binding.lnhide.setVisibility(View.VISIBLE);
                        }
                    }


                }
            }catch (Exception exception){
                exception.printStackTrace();
            }

        }

    }
    public void GetTourPlanMasterData(int month,int year,int empId){
        try{
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<VisitPlanMaster>> call = service.GetDoctorVisitPlanMaster(month,year,empId);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<List<VisitPlanMaster>>() {
                @Override
                public void onResponse(@NonNull Call<List<VisitPlanMaster>> call, @NonNull Response<List<VisitPlanMaster>> response) {
                    SetinView(response.body());
                }
                @Override
                public void onFailure(@NonNull Call<List<VisitPlanMaster>> call, @NonNull Throwable t) {
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



