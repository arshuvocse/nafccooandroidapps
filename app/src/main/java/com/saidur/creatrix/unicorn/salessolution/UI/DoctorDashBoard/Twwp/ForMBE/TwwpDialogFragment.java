package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE;

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
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresentarTwwp;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDeliveryDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpMorning;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpSave;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWorkType;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentTwwpEntryBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentWebMapBinding;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwwpDialogFragment extends DialogFragment implements ITwwp.View {
    ComponentTwwpEntryBinding binding;
    ComponentWebMapBinding bindingWebMap;
    PresentarTwwp presentarTwwp;
    DBDoctorHelper dbDoctorHelper;
    DBProductSQLiteHelper dbProduct;
    private callbackHitApi callback;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    ProgressDialog pd;

    public static TwwpDialogFragment newInstance() {
        return new TwwpDialogFragment();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ComponentTwwpEntryBinding.inflate(inflater, container, false);
        bindingWebMap = ComponentWebMapBinding.inflate(getLayoutInflater());
        if (savedInstanceState == null) {

        }
        presentarTwwp = new PresentarTwwp(requireActivity(), this);
        dbDoctorHelper = new DBDoctorHelper(getActivity());
        dbProduct = new DBProductSQLiteHelper(getActivity());
        presentarTwwp.getDay();
        presentarTwwp.getDeliveryDay();
        presentarTwwp.getTourType();
        presentarTwwp.getWorkType();
        // checkLocationPermission();
        //dialogTwwpMap();

        binding.msloc.setOnClickListener(view -> {
            // Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?q=loc:23.7516588,90.3796131"));
            // Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=23.7516588,90.3796131&daddr=23.7516588,90.3796131"));
            // Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
            //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?saddr=My+Location&daddr=23.7516588,90.3796131"));
            //Query search of a Latitude and Longitude
            //Also shows setting a default zoom level
            //  Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?ll=23.7516588,90.3796131&q=&amp;z=14"));
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/?q=-23.7516588,90.3796131"));
            startActivity(intent);
        });
        binding.esloc.setOnClickListener(view -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/?q=-23.7516588,90.3796131"));
            startActivity(intent);
        });
        binding.meloc.setOnClickListener(view -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/?q=-23.7516588,90.3796131"));
            startActivity(intent);
        });
        binding.eeloc.setOnClickListener(view -> {
            //dMap.show();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/?q=-23.7516588,90.3796131"));
            startActivity(intent);
        });

        binding.msclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvMstarttime, requireActivity()));
        binding.esclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvEstarttime, requireActivity()));
        binding.meclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvMendtime, requireActivity()));
        binding.eeclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvEendtime, requireActivity()));

        bindingWebMap.ivSearch.setOnClickListener(view -> {
            //  SearchLocation(bindingWebMap.tvSearch.getText().toString());
        });

        binding.btnSave.setOnClickListener(view ->
        {
            int dayId= 0;
            int mtTypeId= 0;
            int etTypeId= 0;
            int mwTypeId= 0;
            int ewTypeId= 0;
            int mddayId= 0;
            int eddayId= 0;
            try {
                ModelDay day=(ModelDay)binding.spinnerDay.getSelectedItem();
                dayId = day.getDayNameValueId();


                ModelStationType mttype=(ModelStationType)binding.mspinnerTourType.getSelectedItem();
                mtTypeId = mttype.getStationTypeId();
                ModelStationType ettype=(ModelStationType)binding.espinnerTourType.getSelectedItem();
                etTypeId = ettype.getStationTypeId();

                ModelWorkType mwtype=(ModelWorkType)binding.mspinnerWorkType.getSelectedItem();
                mwTypeId = mwtype.getWorkTypeId();
                ModelWorkType ewtype=(ModelWorkType)binding.mspinnerWorkType.getSelectedItem();
                ewTypeId = ewtype.getWorkTypeId();

                ModelDeliveryDay mdday=(ModelDeliveryDay)binding.mspinnerDDay.getSelectedItem();
                mddayId = mdday.getDeliveryDayNameId();
                ModelDeliveryDay edday=(ModelDeliveryDay)binding.espinnerDDay.getSelectedItem();
                eddayId = edday.getDeliveryDayNameId();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage().toString());
            }

            ModelTwwpSave save = new ModelTwwpSave();
            save.setEmpId(String.valueOf(session.getEmpInfoId()));
            ModelTwwpMorning morning = new ModelTwwpMorning();
            morning.setWorkSession("Morning");
            morning.setTourTypeId(String.valueOf(mtTypeId));
            morning.setWorkTypeId(String.valueOf(mwTypeId));
            morning.setDayNameValueId(String.valueOf(dayId));
            morning.setDeliveryDayNameId(String.valueOf(mddayId));
            morning.setStartTime(binding.tvMstarttime.getText().toString().trim());
            morning.setStartPoint(binding.tvMstartpoint.getText().toString().trim());
            morning.setEndTime(binding.tvMendtime.getText().toString().trim());
            morning.setEndPoint(binding.tvMendpoint.getText().toString().trim());

            ModelTwwpMorning evening = new ModelTwwpMorning();
            evening.setWorkSession("Evening");
            evening.setTourTypeId(String.valueOf(etTypeId));
            evening.setWorkTypeId(String.valueOf(ewTypeId));
            evening.setDayNameValueId(String.valueOf(dayId));
            evening.setDeliveryDayNameId(String.valueOf(eddayId));
            evening.setStartTime(binding.tvEstarttime.getText().toString().trim());
            evening.setStartPoint(binding.tvEstartpoint.getText().toString().trim());
            evening.setEndTime(binding.tvEendtime.getText().toString().trim());
            evening.setEndPoint(binding.tvEendpoint.getText().toString().trim());
            save.setTwwpMorning(morning);
            save.setTwwpEvening(evening);
            hitsaveApi(save);
        });
        return binding.getRoot();
    }

    private void hitsaveApi(ModelTwwpSave datas) {
        Gson gson = new Gson();
        String data = gson.toJson(datas);
        System.out.println(data);
        pd = new ProgressDialog(requireActivity());
        pd.setMessage("Saving....");
        pd.show();
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResponseResultInfo> call = service.SaveTwwp(datas);
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
                         SnackBarManagement._success_CustomMessage(binding.getRoot(),info.getMsd());
                        } else {
                            SnackBarManagement._error_CustomMessage(binding.getRoot(),info.getMsd());
                        }

                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(),"Response Null");
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
    public void viewDay(List<ModelDay> days) {
        try {
            if (days != null) {
                ArrayAdapter<ModelDay> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, days);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerDay.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
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

    public interface callbackHitApi {
        void onCallBackHit(String value);
    }


    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        callback.onCallBackHit("Hit");
    }

}
