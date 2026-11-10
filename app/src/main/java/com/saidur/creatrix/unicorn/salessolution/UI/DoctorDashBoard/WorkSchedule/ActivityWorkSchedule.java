package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBProductSQLiteHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITwwpABM;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseTwwpCopyModel;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresentarTwwpABM;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterWSlist;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForABM.ActivityTwwpABM;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForABM.TwwpABMDialogFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDeliveryDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTransport;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMMorning;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMSave;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWorkType;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Adapter.AdapterMWSList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSMBESave;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ResponseWSData;
import com.saidur.creatrix.unicorn.salessolution.UI.Leave.ActivityLeaveList;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTwwpAbmBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityWorkScheduleBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopEditWsBinding;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityWorkSchedule extends AppCompatActivity implements AdapterMWSList.OnUpdateWSListener, ITwwpABM.View {
    ActivityWorkScheduleBinding binding;
    ComponentLoadingbarBinding lbinding;
    PopEditWsBinding editBinding;
    DBCrudHelper dbCrudHelper;
    String yearList[] = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ModelSession session;
    Dialog pd_process, pop_edit;
    AdapterMWSList adapter;
    int selectedYearpos = 0, selectedMonPos = 0, type = 0;
    String masterId;
    int monthz = 0, yearz = 0;
    private int initCount = 0;
    PresentarTwwpABM presentarTwwp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkScheduleBinding.inflate(getLayoutInflater());
        lbinding = ComponentLoadingbarBinding.inflate(getLayoutInflater());
        editBinding = PopEditWsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbCrudHelper = new DBCrudHelper(ActivityWorkSchedule.this);
        session = dbCrudHelper.getSession();
        prepareLoadingBar();

        presentarTwwp = new PresentarTwwpABM(ActivityWorkSchedule.this, this);


        presentarTwwp.getTeritorry();
        presentarTwwp.getDeliveryDay();
        presentarTwwp.getTourType();
        presentarTwwp.getWorkType();
        presentarTwwp.getTransport();
        binding.toolbarCustom.setNavigationOnClickListener(view -> finish());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            monthz = localDate.getMonthValue();
            yearz = localDate.getYear();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("empId", String.valueOf(session.getEmpInfoId()));
        map.put("MonthVal", String.valueOf(monthz));
        map.put("YearVal", String.valueOf(yearz));
        // getWSMBEList(map);

        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpinner.setAdapter(dataAdapter);
        int yearPos = dataAdapter.getPosition(String.valueOf(year));
        selectedYearpos = yearPos;
        binding.yearSpinner.setSelection(yearPos);

        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthNameArray);
        dataAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthSpinner.setAdapter(dataAdapterMonth);
        String monName = monthNameArray[month];
        int monthPos = dataAdapterMonth.getPosition(monName);
        binding.monthSpinner.setSelection(monthPos);

        binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int yearV = Integer.parseInt(binding.yearSpinner.getSelectedItem().toString());
                int monthV = i + 1;
                selectedMonPos = i;
                HashMap<String, String> mapa = new HashMap<>();
                mapa.put("empId", String.valueOf(session.getEmpInfoId()));
                mapa.put("month", String.valueOf(monthV));
                mapa.put("Year", String.valueOf(yearV));
                if (NetworkInformation.isConnected(ActivityWorkSchedule.this)) {
                    pd_process.show();
                    pd_process.setCancelable(false);
                    lbinding.tvText.setText("Loading...");
                    getWSMBEList(mapa);
                } else {
                    SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet!!");
                }

                initCount++;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        binding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initCount > 0) {
                    selectedYearpos = position;
                    int monthV = binding.monthSpinner.getSelectedItemPosition();
                    int yearV = Integer.parseInt(yearList[position].toString());

                    HashMap<String, String> mapb = new HashMap<>();
                    mapb.put("empId", String.valueOf(session.getEmpInfoId()));
                    mapb.put("month", String.valueOf(monthV + 1));
                    mapb.put("Year", String.valueOf(yearV));

                    if (NetworkInformation.isConnected(ActivityWorkSchedule.this)) {
                        pd_process.show();
                        pd_process.setCancelable(false);
                        lbinding.tvText.setText("Loading...");
                        getWSMBEList(mapb);
                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet!!");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.addWs.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString("From", "ActivityWorkschedule");
            args.putString("Month", String.valueOf(monthz));
            args.putString("Year", String.valueOf(yearz));
            DialogFragment dialog = WorkScheduleDialogFragment.newInstance();
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "ActivityWorkschedule");
            ((WorkScheduleDialogFragment) dialog).setCallback(new WorkScheduleDialogFragment.callbackHitApi() {
                @Override
                public void onCallBackHit(String mon, String yer) {
                    HashMap<String, String> mapz = new HashMap<>();
                    mapz.put("empId", String.valueOf(session.getEmpInfoId()));
                    mapz.put("MonthVal", mon);
                    mapz.put("YearVal", yer);
                    //  getWSMBEList(mapz);
                }
            });
        });

        editBinding.ivClose.setOnClickListener(view -> {
            pop_edit.dismiss();
        });
        editBinding.btnSave.setOnClickListener(view -> {
            String date = "";
            int mtTypeId = 0;
            int etTypeId = 0;
            int mwTypeId = 0;
            int ewTypeId = 0;
            int mddayId = 0;
            int eddayId = 0;

            int mtTeriId = 0;
            int etTeriId = 0;
            int mTransId = 0;
            int eTransId = 0;
            try {
                date = editBinding.tvEditDate.getText().toString().trim();

                ModelTeritorry mteri = (ModelTeritorry) editBinding.mspinnerTeri.getSelectedItem();
                mtTeriId = mteri.getTerritoryId();
                ModelTeritorry eteri = (ModelTeritorry) editBinding.espinnerTeri.getSelectedItem();
                etTeriId = eteri.getTerritoryId();

                ModelTransport mtrans = (ModelTransport) editBinding.mspinnerTravel.getSelectedItem();
                mTransId = mtrans.getTransportId();
                ModelTransport etrans = (ModelTransport) editBinding.espinnerTravel.getSelectedItem();
                eTransId = etrans.getTransportId();

                ModelStationType mttype = (ModelStationType) editBinding.mspinnerTourType.getSelectedItem();
                mtTypeId = mttype.getStationTypeId();
                ModelStationType ettype = (ModelStationType) editBinding.espinnerTourType.getSelectedItem();
                etTypeId = ettype.getStationTypeId();

                ModelWorkType mwtype = (ModelWorkType) editBinding.mspinnerWorkType.getSelectedItem();
                mwTypeId = mwtype.getWorkTypeId();
                ModelWorkType ewtype = (ModelWorkType) editBinding.espinnerWorkType.getSelectedItem();
                ewTypeId = ewtype.getWorkTypeId();

                ModelDeliveryDay mdday = (ModelDeliveryDay) editBinding.mspinnerDDay.getSelectedItem();
                mddayId = mdday.getDeliveryDayNameId();
                ModelDeliveryDay edday = (ModelDeliveryDay) editBinding.espinnerDDay.getSelectedItem();
                eddayId = edday.getDeliveryDayNameId();


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage().toString());
            }

            ModelWSMBESave save = new ModelWSMBESave();
            save.setEmpId(String.valueOf(session.getEmpInfoId()));
            save.setDate(editBinding.tvEditDate.getText().toString().trim());

            ModelTwwpABMMorning morning = new ModelTwwpABMMorning();
            morning.setWorkSession("Morning");
            morning.setTourTypeId(String.valueOf(mtTypeId));
            morning.setWorkTypeId(String.valueOf(mwTypeId));
            morning.setScheduleDate(date);
            morning.setDeliveryDayNameId(String.valueOf(mddayId));
            morning.setStartTime(editBinding.tvMstarttime.getText().toString().trim());
            morning.setStartAddress(editBinding.tvMstartpoint.getText().toString().trim());
            morning.setEndTime(editBinding.tvMendtime.getText().toString().trim());
            morning.setEndAddress(editBinding.tvMendpoint.getText().toString().trim());
            morning.setTravelBy(String.valueOf(mTransId));
            morning.setTerritoryId(String.valueOf(mtTeriId));


            ModelTwwpABMMorning evening = new ModelTwwpABMMorning();
            evening.setWorkSession("Evening");
            evening.setTourTypeId(String.valueOf(etTypeId));
            evening.setWorkTypeId(String.valueOf(ewTypeId));
            evening.setScheduleDate(date);
            evening.setDeliveryDayNameId(String.valueOf(eddayId));
            evening.setStartTime(editBinding.tvEstarttime.getText().toString().trim());
            evening.setStartAddress(editBinding.tvEstartpoint.getText().toString().trim());
            evening.setEndTime(editBinding.tvEendtime.getText().toString().trim());
            evening.setEndAddress(editBinding.tvEendpoint.getText().toString().trim());

            evening.setTravelBy(String.valueOf(eTransId));
            evening.setTerritoryId(String.valueOf(etTeriId));
            if (type == 1) {
                save.setMasterId(masterId);
            }else if(type==2)
            {
                save.setMasterId("0");
            }

            if (type == 1) {
                save.setType("Update");
            }else if(type==2)
            {
                save.setType("Amendment");
            }
            save.setWCABMMorning(morning);
            save.setWCABMEvening(evening);
            if (NetworkInformation.isConnected(ActivityWorkSchedule.this)) {
                Gson gson=new Gson();
                String data=gson.toJson(save);
                System.out.println(data);
                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Updating..");
                hitsaveApi(save);
            } else {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "No Internet!!");
            }

        });

    }

    private void prepareLoadingBar() {
        pd_process = new Dialog(ActivityWorkSchedule.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;

        pop_edit = new Dialog(ActivityWorkSchedule.this);
        pop_edit.setContentView(editBinding.getRoot());
        pop_edit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop_edit.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pop_edit.getWindow().getAttributes().gravity = Gravity.CENTER;

    }

    private void getWSMBEList(HashMap<String, String> map) {
        try {
            ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
            Call<List<ResponseWSData>> call = service.GetWSlList(map);
            call.enqueue(new Callback<List<ResponseWSData>>() {
                @Override
                public void onResponse(@NonNull Call<List<ResponseWSData>> call, @NonNull Response<List<ResponseWSData>> response) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (response.body() != null) {

                        LoadRV(response.body());

                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(), "Response Null");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<ResponseWSData>> call, @NonNull Throwable t) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {

                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            if (pd_process != null || pd_process.isShowing()) {
                pd_process.dismiss();
            }
        }
    }

    private void LoadRV(List<ResponseWSData> data) {
        if (pd_process != null || pd_process.isShowing()) {
            pd_process.dismiss();
        }
        adapter = new AdapterMWSList(ActivityWorkSchedule.this, data, this);
        binding.rvWslist.setLayoutManager(new LinearLayoutManager(ActivityWorkSchedule.this, RecyclerView.VERTICAL, false));
        binding.rvWslist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdate(ResponseWSData data, int pos, int ammend) {
        type = ammend;
        masterId=data.getMasterId();
        pop_edit.show();
        pop_edit.setCancelable(false);
        if(ammend==1)
        {
            editBinding.tvTag.setText("Edit For : ");
            editBinding.btnSave.setText("Update");
        }else if(ammend==2)
        {
            editBinding.tvTag.setText("Amendment For : ");
            editBinding.btnSave.setText("Amend");
        }
        editBinding.tvEditDate.setText(data.getDate());
        editBinding.tvMstarttime.setText(data.getMorningList().getStartTime());
        editBinding.tvMendtime.setText(data.getMorningList().getEndTime());
        editBinding.tvMstartpoint.setText(data.getMorningList().getStartPoint());
        editBinding.tvMendpoint.setText(data.getMorningList().getEndPoint());

        editBinding.tvEstarttime.setText(data.getEveningList().getStartTime());
        editBinding.tvEendtime.setText(data.getEveningList().getEndTime());
        editBinding.tvEstartpoint.setText(data.getEveningList().getStartPoint());
        editBinding.tvEendpoint.setText(data.getEveningList().getEndPoint());


        editBinding.msclock.setOnClickListener(view -> UtilityHelper._timePicker(editBinding.tvMstarttime, ActivityWorkSchedule.this));
        editBinding.esclock.setOnClickListener(view -> UtilityHelper._timePicker(editBinding.tvEstarttime, ActivityWorkSchedule.this));
        editBinding.meclock.setOnClickListener(view -> UtilityHelper._timePicker(editBinding.tvMendtime, ActivityWorkSchedule.this));
        editBinding.eeclock.setOnClickListener(view -> UtilityHelper._timePicker(editBinding.tvEendtime, ActivityWorkSchedule.this));


    }

    @Override
    public void viewDeliveryDay(List<ModelDeliveryDay> ddays) {
        try {
            if (ddays != null) {
                ArrayAdapter<ModelDeliveryDay> dataAdapter = new ArrayAdapter<>(ActivityWorkSchedule.this, android.R.layout.simple_spinner_item, ddays);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editBinding.mspinnerDDay.setAdapter(dataAdapter);
                editBinding.espinnerDDay.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewTourType(List<ModelStationType> ttypes) {
        try {
            if (ttypes != null) {
                ArrayAdapter<ModelStationType> dataAdapter = new ArrayAdapter<>(ActivityWorkSchedule.this, android.R.layout.simple_spinner_item, ttypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editBinding.mspinnerTourType.setAdapter(dataAdapter);
                editBinding.espinnerTourType.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewWorkType(List<ModelWorkType> wtypes) {
        try {
            if (wtypes != null) {
                ArrayAdapter<ModelWorkType> dataAdapter = new ArrayAdapter<>(ActivityWorkSchedule.this, android.R.layout.simple_spinner_item, wtypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editBinding.mspinnerWorkType.setAdapter(dataAdapter);
                editBinding.espinnerWorkType.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewTransport(List<ModelTransport> wtypes) {
        try {
            if (wtypes != null) {
                ArrayAdapter<ModelTransport> dataAdapter = new ArrayAdapter<>(ActivityWorkSchedule.this, android.R.layout.simple_spinner_item, wtypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editBinding.mspinnerTravel.setAdapter(dataAdapter);
                editBinding.espinnerTravel.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewTeritorry(List<ModelTeritorry> wtypes) {
        try {
            if (wtypes != null) {
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(ActivityWorkSchedule.this, android.R.layout.simple_spinner_item, wtypes);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editBinding.mspinnerTeri.setAdapter(dataAdapter);
                editBinding.espinnerTeri.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void viewSTeritorry(List<ModelSubTeritorry> wtypes) {

    }

    private void hitsaveApi(ModelWSMBESave datas) {
        Gson gson = new Gson();
        String data = gson.toJson(datas);
        System.out.println(data);

        try {
            ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
            Call<ResponseResultInfo> call = service.SaveWSUpdate(datas);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    ResponseResultInfo info = response.body();
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (info != null) {
                        if (info.isSuccess) {
                            pop_edit.dismiss();
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
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {

                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            if (pd_process != null || pd_process.isShowing()) {
                pd_process.dismiss();
            }
        }
    }
}