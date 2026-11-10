package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForABM;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBProductSQLiteHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITwwp;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITwwpABM;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresentarTwwp;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresentarTwwpABM;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDeliveryDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTransport;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMMorning;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMSave;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpMorning;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpSave;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWorkType;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentTwwpEntryBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentWebMapBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentWorkscheduleEntryAbmRbmBinding;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwwpABMDialogFragment extends DialogFragment implements ITwwpABM.View {
    ComponentWorkscheduleEntryAbmRbmBinding binding;
    PresentarTwwpABM presentarTwwp;
    DBDoctorHelper dbDoctorHelper;
    DBProductSQLiteHelper dbProduct;
    private callbackHitApi callback;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    ProgressDialog pd;
    String selectedmonth, selectedyear;

    public static TwwpABMDialogFragment newInstance() {
        return new TwwpABMDialogFragment();
    }

    public void setCallback(callbackHitApi callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
        dbCrudHelper = new DBCrudHelper(requireActivity());
        session = dbCrudHelper.getSession();
        Bundle mBundle = getArguments();
        assert mBundle != null;
        selectedmonth = mBundle.getString("Month");
        selectedyear = mBundle.getString("Year");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ComponentWorkscheduleEntryAbmRbmBinding.inflate(inflater, container, false);
        if (savedInstanceState == null) {

        }
        presentarTwwp = new PresentarTwwpABM(requireActivity(), this);
        dbDoctorHelper = new DBDoctorHelper(getActivity());
        dbProduct = new DBProductSQLiteHelper(getActivity());
        presentarTwwp.getTeritorry();
        presentarTwwp.getSTeritorry(0);
        presentarTwwp.getDeliveryDay();
        presentarTwwp.getTourType();
        presentarTwwp.getWorkType();
        presentarTwwp.getTransport();
        // checkLocationPermission();
        //dialogTwwpMap();

        binding.ivDatepicker.setOnClickListener(view -> UtilityHelper._datePickerDialogeForDates(binding.tvTwwpDate, requireActivity()));
        binding.msclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvMstarttime, requireActivity()));
        binding.esclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvEstarttime, requireActivity()));
        binding.meclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvMendtime, requireActivity()));
        binding.eeclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvEendtime, requireActivity()));

        binding.btnSave.setOnClickListener(view ->
        {
            String date = "";
            int mtTypeId = 0;
            int etTypeId = 0;
            int mwTypeId = 0;
            int ewTypeId = 0;
            int mddayId = 0;
            int eddayId = 0;

            int mtTeriId = 0;
            int etTeriId = 0;
            int mtSTeriId = 0;
            int etSTeriId = 0;
            int mTransId = 0;
            int eTransId = 0;
            try {
                date = binding.tvTwwpDate.getText().toString().trim();

                ModelTeritorry mteri = (ModelTeritorry) binding.mspinnerTeri.getSelectedItem();
                mtTeriId = mteri.getTerritoryId();
                ModelTeritorry eteri = (ModelTeritorry) binding.espinnerTeri.getSelectedItem();
                etTeriId = eteri.getTerritoryId();

                ModelSubTeritorry mSteri = (ModelSubTeritorry) binding.mspinnerSTeri.getSelectedItem();
                mtSTeriId = mSteri.getSubTerritoryId();
                ModelSubTeritorry eSteri = (ModelSubTeritorry) binding.espinnerSTeri.getSelectedItem();
                etSTeriId = eSteri.getSubTerritoryId();

                ModelTransport mtrans = (ModelTransport) binding.mspinnerTravel.getSelectedItem();
                mTransId = mtrans.getTransportId();
                ModelTransport etrans = (ModelTransport) binding.espinnerTravel.getSelectedItem();
                eTransId = etrans.getTransportId();

                ModelStationType mttype = (ModelStationType) binding.mspinnerTourType.getSelectedItem();
                mtTypeId = mttype.getStationTypeId();
                ModelStationType ettype = (ModelStationType) binding.espinnerTourType.getSelectedItem();
                etTypeId = ettype.getStationTypeId();

                ModelWorkType mwtype = (ModelWorkType) binding.mspinnerWorkType.getSelectedItem();
                mwTypeId = mwtype.getWorkTypeId();
                ModelWorkType ewtype = (ModelWorkType) binding.espinnerWorkType.getSelectedItem();
                ewTypeId = ewtype.getWorkTypeId();

                ModelDeliveryDay mdday = (ModelDeliveryDay) binding.mspinnerDDay.getSelectedItem();
                mddayId = mdday.getDeliveryDayNameId();
                ModelDeliveryDay edday = (ModelDeliveryDay) binding.espinnerDDay.getSelectedItem();
                eddayId = edday.getDeliveryDayNameId();


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage().toString());
            }

            ModelTwwpABMSave save = new ModelTwwpABMSave();
            save.setEmpId(String.valueOf(session.getEmpInfoId()));
            ModelTwwpABMMorning morning = new ModelTwwpABMMorning();
            morning.setWorkSession("Morning");
            morning.setTourTypeId(String.valueOf(mtTypeId));
            morning.setWorkTypeId(String.valueOf(mwTypeId));
            morning.setScheduleDate(date);
            morning.setDeliveryDayNameId(String.valueOf(mddayId));
            morning.setStartTime(binding.tvMstarttime.getText().toString().trim());
            morning.setStartAddress(binding.tvMstartpoint.getText().toString().trim());
            morning.setEndTime(binding.tvMendtime.getText().toString().trim());
            morning.setEndAddress(binding.tvMendpoint.getText().toString().trim());

            morning.setPlace(String.valueOf(mtSTeriId));
            morning.setTravelBy(String.valueOf(mTransId));
            morning.setTerritoryId(String.valueOf(mtTeriId));


            ModelTwwpABMMorning evening = new ModelTwwpABMMorning();
            evening.setWorkSession("Evening");
            evening.setTourTypeId(String.valueOf(etTypeId));
            evening.setWorkTypeId(String.valueOf(ewTypeId));
            evening.setScheduleDate(date);
            evening.setDeliveryDayNameId(String.valueOf(eddayId));
            evening.setStartTime(binding.tvEstarttime.getText().toString().trim());
            evening.setStartAddress(binding.tvEstartpoint.getText().toString().trim());
            evening.setEndTime(binding.tvEendtime.getText().toString().trim());
            evening.setEndAddress(binding.tvEendpoint.getText().toString().trim());

            evening.setPlace(String.valueOf(etSTeriId));
            evening.setTravelBy(String.valueOf(eTransId));
            evening.setTerritoryId(String.valueOf(etTeriId));


            save.setWCABMMorning(morning);
            save.setWCABMEvening(evening);
            hitsaveApi(save);
        });
        return binding.getRoot();
    }

    private void hitsaveApi(ModelTwwpABMSave datas) {
        Gson gson = new Gson();
        String data = gson.toJson(datas);
        System.out.println(data);
        pd = new ProgressDialog(requireActivity());
        pd.setMessage("Saving....");
        pd.show();
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResponseResultInfo> call = service.SaveTwwpABM(datas);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    ResponseResultInfo info = response.body();
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (info != null) {
                        if (info.isSuccess) {
                            binding.tvMstarttime.setText("");
                            binding.tvMstartpoint.setText("");
                            binding.tvMendpoint.setText("");
                            binding.tvMendtime.setText("");
                            binding.tvEstarttime.setText("");
                            binding.tvEstartpoint.setText("");
                            binding.tvEendpoint.setText("");
                            binding.tvEendtime.setText("");
/*
                            binding.etMtName.setText("");
                            binding.etEtName.setText("");
                            binding.etMplace.setText("");
                            binding.etEplace.setText("");*/
                            SnackBarManagement._success_CustomMessage(binding.getRoot(), info.getMsd());
                        } else {
                            SnackBarManagement._error_CustomMessage(binding.getRoot(), info.getMsd());
                        }

                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(), "Response Null");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {

                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

    @Override
    public void viewDeliveryDay(List<ModelDeliveryDay> ddays) {
        try {
            if (ddays != null) {
                ArrayAdapter<ModelDeliveryDay> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, ddays);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.mspinnerDDay.setAdapter(dataAdapter);
                binding.espinnerDDay.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewTourType(List<ModelStationType> ttypes) {
        try {
            if (ttypes != null) {
                ArrayAdapter<ModelStationType> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, ttypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.mspinnerTourType.setAdapter(dataAdapter);
                binding.espinnerTourType.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewWorkType(List<ModelWorkType> wtypes) {
        try {
            if (wtypes != null) {
                ArrayAdapter<ModelWorkType> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, wtypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.mspinnerWorkType.setAdapter(dataAdapter);
                binding.espinnerWorkType.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewTransport(List<ModelTransport> wtypes) {
        try {
            if (wtypes != null) {
                ArrayAdapter<ModelTransport> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, wtypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.mspinnerTravel.setAdapter(dataAdapter);
                binding.espinnerTravel.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewTeritorry(List<ModelTeritorry> wtypes) {
        try {
            if (wtypes != null) {
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, wtypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.mspinnerTeri.setAdapter(dataAdapter);
                binding.espinnerTeri.setAdapter(dataAdapter);


            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewSTeritorry(List<ModelSubTeritorry> wtypes) {
        try {
            if (wtypes != null) {
                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, wtypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.mspinnerSTeri.setAdapter(dataAdapter);
                binding.espinnerSTeri.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public interface callbackHitApi {
        void onCallBackHit(String month,String year);
    }


    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        callback.onCallBackHit(selectedmonth,selectedyear);
    }

}
