package com.saidur.creatrix.unicorn.salessolution.UI.Leave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterLVTypeList;
import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterOfferList;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ILeave;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveTypeInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveVM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseLeaveList;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterLeave;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;

import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityLeaveListBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.DialogSuccessCustomBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class ActivityLeaveList extends AppCompatActivity implements ILeave.View {
    ActivityLeaveListBinding binding;
    ComponentLoadingbarBinding lbinding;
    Dialog pd_process;
    PresenterLeave presenter;

    String yearList[] = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int selectedYearpos = 0, selectedMonPos = 0;
    int monthz = 0, yearz = 0;
    private int initCount = 0;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    AdapterLeaveList adapter;
    String selectedMonth,selectedYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try {
           binding = ActivityLeaveListBinding.inflate(getLayoutInflater());
           lbinding=ComponentLoadingbarBinding.inflate(getLayoutInflater());

           presenter = new PresenterLeave(this, ActivityLeaveList.this);

           setContentView(binding.getRoot());
           dbCrudHelper=new DBCrudHelper(ActivityLeaveList.this);
           session=dbCrudHelper.getSession();

           setupMonthSpinner();
           setupYearSpinner();
        //   checkAndSendData();
           binding.toolbarCustom.setNavigationOnClickListener(view -> {
               finish();
           });

           binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   checkAndSendData(); // when month is changed
               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) { }
           });

           binding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   checkAndSendData(); // when year is changed
               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) { }
           });

           try {
               binding.addLeave.setOnClickListener(view -> {
                   Intent insd = new Intent(ActivityLeaveList.this, LeaveActivity.class);
                   startActivity(insd);
                   overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
               });
           }catch  (Exception ex){

           }
       } catch (Exception ex) {
           ex.printStackTrace();
           Toast.makeText(this, "Initialization error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
       }

    }



    private int monthNameToNumber(String monthName) {
        switch (monthName) {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: return 1; // fallback
        }
    }

    private void checkAndSendData() {
        String selectedMonthName = binding.monthSpinner.getSelectedItem().toString();
        String selectedYear = binding.yearSpinner.getSelectedItem().toString();

        int monthV = monthNameToNumber(selectedMonthName); // Convert to numeric month
        int yearV = Integer.parseInt(selectedYear);

        String selectedMonth = String.valueOf(monthV);
        String selectedYearStr = String.valueOf(yearV);

        HashMap<String, String> mapa = new HashMap<>();
        mapa.put("empId", String.valueOf(session.getEmpInfoId()));
        mapa.put("Month", selectedMonth);
        mapa.put("year", selectedYearStr);

        if (NetworkInformation.isConnected(ActivityLeaveList.this)) {
//            pd_process.show();
//            pd_process.setCancelable(false);
//            lbinding.tvText.setText("Loading...");
            presenter.GetLeaveRecords(mapa);
        } else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "No Internet!!");
        }
    }

    private void setupMonthSpinner() {
        String[] months = new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthSpinner.setAdapter(monthAdapter);

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH); // 0-based index
        binding.monthSpinner.setSelection(currentMonth);
    }

    private void setupYearSpinner() {
        ArrayList<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = currentYear - 50; i <= currentYear + 50; i++) {
            years.add(String.valueOf(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpinner.setAdapter(yearAdapter);

        int yearPosition = years.indexOf(String.valueOf(currentYear));
        binding.yearSpinner.setSelection(yearPosition);
    }
    private void prepareLoadingBar() {
        pd_process=new Dialog(ActivityLeaveList.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    @Override
    public void onLeaveTypeGet(List<LeaveTypeInfo> aList) {

    }

    @Override
    public void onSaveSuccess(String message) {

    }

    @Override
    public void onSaveError(String message) {

    }

    @Override
    public void onLeaveRecordsGet(ResponseLeaveList data) {
//        if (pd_process != null || pd_process.isShowing()) {
//            pd_process.dismiss();
//        }
        AdapterLVTypeList offerAdapter = new AdapterLVTypeList(ActivityLeaveList.this,data.getLeaveTypeList());
        binding.lvData.setAdapter(offerAdapter);

        adapter = new AdapterLeaveList(ActivityLeaveList.this, data.getLeaveList());
        binding.rvLeavelist.setLayoutManager(new LinearLayoutManager(ActivityLeaveList.this, RecyclerView.VERTICAL, false));
        binding.rvLeavelist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(NetworkInformation.isConnected(ActivityLeaveList.this))
        {
            HashMap<String, String> mapb = new HashMap<>();
            mapb.put("empId", String.valueOf(session.getEmpInfoId()));
            mapb.put("Month", selectedMonth);
            mapb.put("year", selectedYear);
            pd_process.show();
            pd_process.setCancelable(false);
            lbinding.tvText.setText("Loading...");
            presenter.GetLeaveRecords(mapb);
        }else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(),"No Internet!!");
        }
    }
}