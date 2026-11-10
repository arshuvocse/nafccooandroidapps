package com.saidur.creatrix.unicorn.salessolution.NormalAdapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.TPHQTableAdapter;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanMasterViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.TourApiCall;
import com.saidur.creatrix.unicorn.salessolution.databinding.BottomSheetTourstatusBinding;

import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourStatusBottomSheetDialog  extends BottomSheetDialogFragment {
    BottomSheetTourstatusBinding binding;
    BottomSheetListener mListener;
    TPHQTableAdapter tphq;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= BottomSheetTourstatusBinding.inflate(getLayoutInflater());
        //View v = inflater.inflate(R.layout.bottom_sheet_tourstatus,container);
        Bundle mArgs = getArguments();
        int empId = mArgs.getInt("empId");
        int monthValue = mArgs.getInt("monthValue");
        int year = mArgs.getInt("year");
        int tourCount = mArgs.getInt("tourCount");
        String monStr = mArgs.getString("monthTxt");
        binding.tourCountTxt.setText(String.valueOf(tourCount));
        binding.monthTxt.setText(monStr + ',' + String.valueOf(year));
        GetTourPlanMasterData(monthValue,year,empId);
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
    public void SetinView(List<TourPlanMasterViewModel> aList){
        TourPlanMasterViewModel tpMaster = new TourPlanMasterViewModel();
        if(aList!=null&&aList.size()>0){
            tphq=new TPHQTableAdapter(getActivity(),aList.get(0).getaTourTypeDAOList());
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.rvHq.setLayoutManager(mLayoutManager);
            binding.rvHq.setAdapter(tphq);
           // tphq.notifyDataSetChanged();
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
                            binding.statusTxt.setText("Tour Plan Is Approved");
                            binding.lnhide.setVisibility(View.GONE);
                        }
                        if(tpMaster.getApprovalStatus().equals("3")){
                            binding.statusTxt.setText("Tour Plan Is DisApprove");
                            if(tpMaster.getApprovalRemarks() !=null){
                                binding.statusTxtRejected.setText(tpMaster.getApprovalRemarks());
                            }
                            binding.lnhide.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }catch (Exception exception){
            }
        }
        else {}
    }
    public void GetTourPlanMasterData(int month,int year,int empId){
        try{
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);

            Call<List<TourPlanMasterViewModel>> call = service.GetTourPlanMasterData(month,year,empId);
            call.enqueue(new Callback<List<TourPlanMasterViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<TourPlanMasterViewModel>> call, @NonNull Response<List<TourPlanMasterViewModel>> response) {
                    if(response.body()!=null||response.body().size()>0)
                    {
                        SetinView(response.body());
                    }
                    else {

                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<TourPlanMasterViewModel>> call, @NonNull Throwable t) {
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



