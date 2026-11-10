package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForABM;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterTwwplist;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterWSlist;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpList;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTwwpAbmBinding;

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

public class ActivityTwwpABM extends AppCompatActivity {
    String yearList[] = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ActivityTwwpAbmBinding binding;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    ProgressDialog pd;
    AdapterWSlist adapter;
    int selectedYearpos = 0, selectedMonPos = 0;
    int monthz=0,yearz=0;
    private int initCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTwwpAbmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pd = new ProgressDialog(ActivityTwwpABM.this);
        dbCrudHelper = new DBCrudHelper(ActivityTwwpABM.this);
        session = dbCrudHelper.getSession();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            monthz = localDate.getMonthValue();
            yearz = localDate.getYear();
        }

        HashMap<String,String> map=new HashMap<>();
        map.put("empId", String.valueOf(session.getEmpInfoId()));
        map.put("MonthVal", String.valueOf(monthz));
        map.put("YearVal", String.valueOf(yearz));
        getWSABMList(map);

        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpinner.setAdapter(dataAdapter);
        int yearPos = dataAdapter.getPosition(String.valueOf(year));
        selectedYearpos=yearPos;
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
                selectedMonPos=i;
                HashMap<String,String> mapa=new HashMap<>();
                mapa.put("empId", String.valueOf(session.getEmpInfoId()));
                mapa.put("MonthVal", String.valueOf(monthV));
                mapa.put("YearVal", String.valueOf(yearV));
                getWSABMList(mapa);
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
                    selectedYearpos=position;
                    int monthV = binding.monthSpinner.getSelectedItemPosition();
                    int yearV = Integer.parseInt(yearList[position].toString());

                    HashMap<String,String> mapb=new HashMap<>();
                    mapb.put("empId", String.valueOf(session.getEmpInfoId()));
                    mapb.put("MonthVal", String.valueOf(monthV+1));
                    mapb.put("YearVal", String.valueOf(yearV));
                    getWSABMList(mapb);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.addTwwp.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString("From", "ActivityWorkschedule");
            args.putString("Month", String.valueOf(monthz));
            args.putString("Year", String.valueOf(yearz));
            DialogFragment dialog = TwwpABMDialogFragment.newInstance();
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "ActivityWorkschedule");
            ((TwwpABMDialogFragment)dialog).setCallback(new TwwpABMDialogFragment.callbackHitApi() {
                @Override
                public void onCallBackHit(String mon,String yer) {
                    HashMap<String,String> mapz=new HashMap<>();
                    mapz.put("empId", String.valueOf(session.getEmpInfoId()));
                    mapz.put("MonthVal", mon);
                    mapz.put("YearVal", yer);
                    getWSABMList(mapz);
                }
            });
        });
    }

    private void getWSABMList(HashMap<String, String> map) {
        pd.setMessage("Loading....");
        pd.show();
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<ModelTwwpABMList>> call = service.GetWSABMList(map);
            call.enqueue(new Callback<List<ModelTwwpABMList>>() {
                @Override
                public void onResponse(@NonNull Call<List<ModelTwwpABMList>> call, @NonNull Response<List<ModelTwwpABMList>> response) {

                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.body() != null) {

                        LoadRV(response.body());

                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(),"Response Null");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<ModelTwwpABMList>> call, @NonNull Throwable t) {
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

    private void LoadRV(List<ModelTwwpABMList> data) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        adapter = new AdapterWSlist(ActivityTwwpABM.this, data);
        binding.rvWslist.setLayoutManager(new LinearLayoutManager(ActivityTwwpABM.this, RecyclerView.VERTICAL, false));
        binding.rvWslist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}