package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterDcplist;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCPDialogFragment;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDcpactivityBinding;

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

public class ActivityDCP extends AppCompatActivity implements AdapterDcplist.DcpClick {
    String yearList[] = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ActivityDcpactivityBinding binding;
    AdapterDcplist adapter;
    ModelSession session;
    ProgressDialog pd;
    DBCrudHelper dbCrudHelper;
    private int tourCount = 0;
    private int initCount = 0;
    int selectedYearpos = 0, selectedMonPos = 0;
    String selectedMonth,selectedYear;
    int monthzz=0;
    int yearzz=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDcpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dbCrudHelper = new DBCrudHelper(ActivityDCP.this);
        session = dbCrudHelper.getSession();
        int empId = session.getEmpInfoId();


        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            monthzz = localDate.getMonthValue();
            yearzz = localDate.getYear();
        }
        binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int yearV = Integer.parseInt(binding.yearSpinner.getSelectedItem().toString());
                int monthV = i + 1;
                selectedMonPos=i;
                selectedMonth=String.valueOf(monthV);
                selectedYear=String.valueOf(yearV);

                HashMap<String,String> map=new HashMap<>();
                map.put("empId", String.valueOf(empId));
                map.put("month", String.valueOf(monthV));
                map.put("year", String.valueOf(yearV));
                LoadDCPList(map);

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
                    selectedMonth=String.valueOf(monthV+1);
                    selectedYear=String.valueOf(yearV);
                    HashMap<String,String> maps=new HashMap<>();
                    maps.put("empId", String.valueOf(empId));
                    maps.put("month", String.valueOf(monthV+1));
                    maps.put("year", String.valueOf(yearV));
                    LoadDCPList(maps);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.addDcp.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putString("From", "ADDDCP");
            args.putString("Month", selectedMonth);
            args.putString("Year", selectedYear);
            DialogFragment dialog = DCPDialogFragment.newInstance();
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "ActivityDCP");
            ((DCPDialogFragment)dialog).setCallbackApi((a, b) -> {
                HashMap<String,String> mapv=new HashMap<>();
                mapv.put("empId", String.valueOf(empId));
                mapv.put("month", String.valueOf(a));
                mapv.put("year", String.valueOf(b));
                LoadDCPList(mapv);
            });
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] choices = {"Day", "Evening"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDCP.this);
        builder
                .setTitle("Select Time")
                .setSingleChoiceItems(choices, 0, (dialog, which) -> {
                    // Handle selection: which == 0 for "Day", which == 1 for "Evening"
                })
                .setPositiveButton("OK", (dialog, id) -> {
                    // Retrieve selection and pass back to activity or callback
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    dialog.dismiss();
                });
        return builder.create();
    }

    private void LoadDCPList(HashMap<String, String> map) {
        pd=new ProgressDialog(ActivityDCP.this);
        pd.setMessage("DCP Loading...");
        pd.show();
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<ModelDCPList>> call = service.GetDCPList(map);
            call.enqueue(new Callback<List<ModelDCPList>>() {
                @Override
                public void onResponse(@NonNull Call<List<ModelDCPList>> call, @NonNull Response<List<ModelDCPList>> response) {
                    pd.dismiss();
                    if (response.body() != null) {
                        LoadDCPRV(response.body());
                    } else {
                        SnackBarManagement._warning_CustomMessage(binding.getRoot(),"Data Not Found!!");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ModelDCPList>> call, @NonNull Throwable t) {
                    pd.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        SnackBarManagement._warning_CustomMessage(binding.getRoot(),"Slow Internet Connection!!");
                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            SnackBarManagement._warning_CustomMessage(binding.getRoot(),"Exception : "+str);
        }
    }
    private void LoadDCPRV(List<ModelDCPList> dcpList) {
        adapter = new AdapterDcplist(ActivityDCP.this, dcpList, this);
        binding.rvDcplist.setLayoutManager(new LinearLayoutManager(ActivityDCP.this, RecyclerView.VERTICAL, false));
        binding.rvDcplist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int id) {
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        HashMap<String,String> mapv=new HashMap<>();
        mapv.put("empId", String.valueOf(session.getEmpInfoId()));
        mapv.put("month", selectedMonth);
        mapv.put("year", selectedYear);
        LoadDCPList(mapv);

    }
}