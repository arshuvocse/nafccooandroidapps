package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBProductSQLiteHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiSeedData;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseTwwpCopyModel;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Adapter.AdapterWS;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSMorning;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentWorkscheduleEntryMbeBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.DialogSuccessCustomBinding;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkScheduleDialogFragment extends DialogFragment implements OnEditWSListener{
    String yearList[] = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ComponentLoadingbarBinding lbinding;

    Dialog pd_process,dSuccess;
    ComponentWorkscheduleEntryMbeBinding binding;
    DialogSuccessCustomBinding bindingSuccess;

    DBProductSQLiteHelper dbProduct;
    private callbackHitApi callback;
    ModelSession session;
    DBCrudHelper dbCrudHelper;

    String selectedmonth, selectedyear;


    AdapterWS adapter;
    int selectedYearpos = 0, selectedMonPos = 0;
    int monthz=0,yearz=0;
    private int initCount = 0;
    List<ResponseTwwpCopyModel> saveDataList=new ArrayList<>();
    List<ResponseTwwpCopyModel> saveDataList2=new ArrayList<>();
    ModelWSMorning mData;
    ModelWSMorning eData;

    public static WorkScheduleDialogFragment newInstance() {
        return new WorkScheduleDialogFragment();
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
        binding = ComponentWorkscheduleEntryMbeBinding.inflate(inflater, container, false);
        lbinding=ComponentLoadingbarBinding.inflate(getLayoutInflater());
        bindingSuccess=DialogSuccessCustomBinding.inflate(getLayoutInflater());
        prepareLoadingBar();
        dbProduct = new DBProductSQLiteHelper(getActivity());
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpinner.setAdapter(dataAdapter);
        int yearPos = dataAdapter.getPosition(String.valueOf(year));
        selectedYearpos = yearPos;
        binding.yearSpinner.setSelection(yearPos);

        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, monthNameArray);
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

                selectedmonth=String.valueOf(monthV);
                selectedyear=String.valueOf(yearV);

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
                    selectedmonth=String.valueOf(monthV+1);
                    selectedyear=String.valueOf(yearV);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.tvWeek.setOnClickListener(view -> {
            HashMap<String, String> mapb = new HashMap<>();
            mapb.put("empId", String.valueOf(session.getEmpInfoId()));
            mapb.put("month", selectedmonth);
            mapb.put("Year", selectedyear);
            mapb.put("Type", "Week");

            if(NetworkInformation.isConnected(requireActivity())){
                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Loading...");
                callApiForWS(mapb);
            }else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(),"No Internet");
            }

        });
        binding.tvFullMonth.setOnClickListener(view -> {
            HashMap<String, String> mapa = new HashMap<>();
            mapa.put("empId", String.valueOf(session.getEmpInfoId()));
            mapa.put("month", selectedmonth);
            mapa.put("Year", selectedyear);
            mapa.put("Type", "Month");

            if(NetworkInformation.isConnected(requireActivity())){
                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Loading...");
                callApiForWS(mapa);
            }else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(),"No Internet");
            }
        });


        binding.btnSave.setOnClickListener(view ->
        {
            Gson gson=new Gson();
            String data=gson.toJson(saveDataList);
            String data2=gson.toJson(saveDataList2);
            System.out.println(data);
            System.out.println(data2);
            if(NetworkInformation.isConnected(requireActivity())){
                pd_process.show();
                pd_process.setCancelable(false);
                lbinding.tvText.setText("Processing...");
                hitsaveApi(saveDataList);
            }else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(),"No Internet");
            }

        });
        return binding.getRoot();

    }

    private void callApiForWS(HashMap<String, String> map) {
        try {
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<List<ResponseTwwpCopyModel>> call = service.GetTwwpForWS(map);

            call.enqueue(new Callback<List<ResponseTwwpCopyModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<ResponseTwwpCopyModel>> call, @NonNull Response<List<ResponseTwwpCopyModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            LoadView(response.body());
                        } catch (Exception e) {
                            Log.e("TAG", "Error while processing response", e);
                        }
                    } else {
                        Log.e("TAG", "Unsuccessful response: " + response.code() + " - " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ResponseTwwpCopyModel>> call, @NonNull Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        Log.e("TAG", "Timeout occurred", t);
                    } else {
                        Log.e("TAG", "Network failure", t);
                    }
                }
            });

        } catch (Exception ex) {
            Log.e("TAG", "Error setting up Retrofit call", ex);
        }
    }


    private void LoadView(List<ResponseTwwpCopyModel> body) {
        if (pd_process != null || pd_process.isShowing()) {
            pd_process.dismiss();
        }
        saveDataList=body;
        adapter = new AdapterWS(requireActivity(), saveDataList,this);
        binding.rvWslist.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.rvWslist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void prepareLoadingBar() {
        pd_process=new Dialog(requireActivity());
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;

        dSuccess=new Dialog(requireActivity());
        dSuccess.setContentView(bindingSuccess.getRoot());
        dSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dSuccess.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        dSuccess.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    private void hitsaveApi(List<ResponseTwwpCopyModel> saveDataList) {

        try {
            ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
            Call<ResponseResultInfo> call = service.SaveWS(saveDataList);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    ResponseResultInfo info = response.body();
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (info.isSuccess) {
                        dSuccess.show();
                        dSuccess.setCancelable(false);
                        bindingSuccess.dialogTag.setText("Work Schedule");
                        bindingSuccess.tvMsg.setText("Saved Successfully!!!");
                        bindingSuccess.btnGoto.setOnClickListener(view -> {
                            dSuccess.dismiss();
                            dismiss();
                        });

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



    @Override
    public void onMTeriNameEditFocusChange(int position, String value,List<ResponseTwwpCopyModel> dList) {
        saveDataList.get(position).getMorningList().setTerritoryName(value);
        dList.get(position).getMorningList().setTerritoryName(value);
        saveDataList2=dList;
        //adapter.notifyItemChanged(position);

       // adapter.notifyItemChanged(position);
       /* if (!binding.rvWslist.isComputingLayout()) {
            try {
                adapter.notifyItemChanged(position);
               // adapter.notifyDataSetChanged();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }*/
    }

    @Override
    public void onETeriNameEditFocusChange(int position, String value) {
        saveDataList.get(position).getEveningList().setTerritoryName(value);
    }

    @Override
    public void onMPlaceEditFocusChange(int position, String value) {
        saveDataList.get(position).getMorningList().setPlace(value);
    }

    @Override
    public void onEPlaceEditFocusChange(int position, String value) {
        saveDataList.get(position).getEveningList().setPlace(value);
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
