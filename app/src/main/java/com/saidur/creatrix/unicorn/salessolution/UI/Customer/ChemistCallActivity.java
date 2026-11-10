package com.saidur.creatrix.unicorn.salessolution.UI.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ICustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelCustomerCallReason;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCustomerCall;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiSeedData;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelCCSvBody;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterCustomerList;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter.CCrAdapter;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter.CcReasonAdapter;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter.CustomerAdapter;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter.CustomerCallAdapter;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.LocationGet;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityChemistCallBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.DialogSuccessCustomBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopCcallBinding;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChemistCallActivity extends AppCompatActivity implements CustomerCallAdapter.Click_call, CcReasonAdapter.ItemClickListener, ICustomer.View {
    ActivityChemistCallBinding binding;
    CustomerCallAdapter adapter;
    PopCcallBinding bindingCCall;
    Dialog pop_ccall;
    ModelCustomer customerz;
    ProgressDialog pd;
    PresenterCustomerList presenter;
    public List<ModelCustomer> customerArrayList = new ArrayList<>();
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    boolean isOrder=false,validate=false;
    CcReasonAdapter adapterz;
    int reason_id=0;
    ComponentLoadingbarBinding lbinding;
    DialogSuccessCustomBinding bindingSuccess;
    Dialog pd_process,dSuccess;
    String address="",selectedTime="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChemistCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lbinding= ComponentLoadingbarBinding.inflate(getLayoutInflater());
        bindingSuccess= DialogSuccessCustomBinding.inflate(getLayoutInflater());
        binding.toolbarCustom.setNavigationOnClickListener(view -> {
            finish();
        });
        prepareLoadingBar();
        dbCrudHelper = new DBCrudHelper(this);
        session = dbCrudHelper.getSession();
        bindingCCall = PopCcallBinding.inflate(getLayoutInflater());
        presenter = new PresenterCustomerList(this, this);

        preparePopUp();
        setupCallTimePicker();
        setupGivenOrderToggle();
        bindingCCall.ivClose.setOnClickListener(view -> pop_ccall.dismiss());
        if (NetworkInformation.isConnected(ChemistCallActivity.this)) {

            pd = new ProgressDialog(ChemistCallActivity.this);
            pd.setMessage("Customer is Loading....");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            presenter.GetCustomerByUser(session.getEmpInfoId());
        }
        binding.searchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        Location location= LocationGet.getLastKnownLocation(ChemistCallActivity.this);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            address = addresses.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        bindingCCall.btnSave.setOnClickListener(view -> {
         /*   int selectedId = bindingCCall.rgCall.getCheckedRadioButtonId();
            rb = (RadioButton) findViewById(selectedId);
            Boolean RadioButtonState = rb.isChecked();*/

           // if(rb.getText().toString().equals("Yes"))
            if(bindingCCall.rbYes.isChecked())
            {
                isOrder=true;
                validate=true;
           // }else if(rb.getText().toString().equals("No")) {
            }else if(bindingCCall.rbNo.isChecked())
            {
                isOrder=false;
                validate=true;
            }else {
                validate=false;
            }
            String callDateTime=buildCallDateTime();

            if(!validate)
            {
                SnackBarManagement._warning_CustomMessage(bindingCCall.getRoot(),"Can't Submit");
            }else if(TextUtils.isEmpty(bindingCCall.etCallTime.getText().toString().trim()))
            {
                SnackBarManagement._warning_CustomMessage(bindingCCall.getRoot(),"Select call time");
            }else if(bindingCCall.rbNo.isChecked() && reason_id==0)
            {
                SnackBarManagement._warning_CustomMessage(bindingCCall.getRoot(),"Select reason");
            }else {

                ModelCCSvBody body = new ModelCCSvBody();
                body.setChemistCallDate(callDateTime);
                body.setCustomerMasterId(customerz.getCustomerMasterId());
                body.setEmpId(session.getEmpInfoId());
                body.setGivenOrder(isOrder);
                body.setReasonId(reason_id);
                body.setLatVal(Constants.LATITUDE);
                body.setLongVal(Constants.LONGITUDE);
                body.setComments(bindingCCall.etComment.getText().toString());
                body.setCallTime(bindingCCall.etCallTime.getText().toString());
                body.setAddress(address);

                pd_process.show();
                pd_process.setCancelable(true);
                lbinding.tvText.setText("Submitting...");
                Gson gson=new Gson();
                String data=gson.toJson(body);
                //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                System.out.println(data);
                submitCall(body);
            }

        });
    }

    private void setupCallTimePicker() {
        setCurrentTimeAsDefault();
        bindingCCall.etCallTime.setOnClickListener(view -> showTimePickerDialog());
    }

    private void setCurrentTimeAsDefault() {
        Calendar calendar = Calendar.getInstance();
        selectedTime = new SimpleDateFormat("hh:mm a", Locale.US).format(calendar.getTime());
        bindingCCall.etCallTime.setText(selectedTime);
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog picker = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            calendar.set(Calendar.MINUTE, selectedMinute);
            selectedTime = new SimpleDateFormat("hh:mm a", Locale.US).format(calendar.getTime());
            bindingCCall.etCallTime.setText(selectedTime);
        }, hour, minute, false);
        picker.show();
    }

    private void setupGivenOrderToggle() {
        bindingCCall.rgCall.setOnCheckedChangeListener((group, checkedId) -> toggleReasonSection(checkedId));
        toggleReasonSection(bindingCCall.rgCall.getCheckedRadioButtonId());
    }

    private void toggleReasonSection(int checkedId) {
        boolean isNoSelected = checkedId == bindingCCall.rbNo.getId();
        if (isNoSelected) {
            bindingCCall.layoutReason.setVisibility(View.VISIBLE);
        } else {
            bindingCCall.layoutReason.setVisibility(View.GONE);
            bindingCCall.etComment.setVisibility(View.GONE);
            bindingCCall.etComment.setText("");
            reason_id = 0;
        }
    }

    private String buildCallDateTime() {
        String today=new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(new Date());
        if(selectedTime==null || selectedTime.isEmpty())
        {
            setCurrentTimeAsDefault();
        }
        return today+" "+selectedTime;
    }

    private void submitCall(ModelCCSvBody body) {

        try {
            ApiCustomerCall service = RetrofitClientInstance.getRetrofitInstance().create(ApiCustomerCall.class);
            Call<ResponseResultInfo> call = service.saveCC(body);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (response.body().isSuccess) {

                        dSuccess.show();
                        dSuccess.setCancelable(false);
                        bindingSuccess.dialogTag.setText("Chemist Call");
                        bindingSuccess.tvMsg.setText(response.body().getMsd());
                        bindingSuccess.btnGoto.setOnClickListener(view -> {
                            dSuccess.dismiss();
                            pop_ccall.dismiss();
                        });
                    }else {
                        dSuccess.show();
                        dSuccess.setCancelable(false);
                        bindingSuccess.dialogTag.setText("Chemist Call");
                        bindingSuccess.tvMsg.setText("Call Not Saved");
                        bindingSuccess.btnGoto.setOnClickListener(view -> {
                            dSuccess.dismiss();
                        });

                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    SnackBarManagement._error_CustomMessage(bindingCCall.getRoot(),t.getMessage());
                }
            });

        } catch (Exception ex) {

        }
    }

    private void callReason() {
        try {
            ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
            Call<List<ModelCustomerCallReason>> call = service.GetCCReason();
            call.enqueue(new Callback<List<ModelCustomerCallReason>>() {
                @Override
                public void onResponse(@NonNull Call<List<ModelCustomerCallReason>> call, @NonNull Response<List<ModelCustomerCallReason>> response) {
                    if (response.isSuccessful()) {
                        LoadReason(response.body());
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<ModelCustomerCallReason>> call, @NonNull Throwable t) {

                }
            });

        } catch (Exception ex) {

        }
    }

    private void LoadReason(List<ModelCustomerCallReason> body) {
        adapterz = new CcReasonAdapter(ChemistCallActivity.this,body, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        bindingCCall.rvCcreason.setLayoutManager(mLayoutManager);
        bindingCCall.rvCcreason.setAdapter(adapterz);
        adapterz.notifyDataSetChanged();

    }

    private void preparePopUp() {
        pop_ccall = new Dialog(ChemistCallActivity.this);
        pop_ccall.setContentView(bindingCCall.getRoot());
        pop_ccall.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop_ccall.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pop_ccall.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    @Override
    public void call_click(int pos, ModelCustomer customer) {
        callReason();
        reason_id=0;
        bindingCCall.rgCall.clearCheck();
        toggleReasonSection(bindingCCall.rgCall.getCheckedRadioButtonId());
        bindingCCall.etComment.setText("");
        setCurrentTimeAsDefault();
        pop_ccall.show();
        customerz = customer;
    }

    private void OnSuccessz(List<ModelCustomer> customerArrayList) {
        if (customerArrayList != null) {
            binding.count.setText(String.valueOf(customerArrayList.size()));
            adapter = new CustomerCallAdapter(this, customerArrayList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.rvCc.setLayoutManager(mLayoutManager);
            binding.rvCc.setItemAnimator(new DefaultItemAnimator());
            binding.rvCc.setAdapter(adapter);
         /*   binding.rvCc.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));*/
            binding.rvCc.setItemAnimator(null);
            binding.rvCc.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            binding.count.setText("0");
        }
    }

    private void filter(String customerName) {
        try {
            if (customerArrayList != null) {

                ArrayList<ModelCustomer> arrayList = new ArrayList<>();
                for (ModelCustomer item : customerArrayList) {
                    if ((item.getCustomerName().toLowerCase().contains(customerName.toLowerCase()) || (item.getCustomerCode().contains(customerName)))) {
                        arrayList.add(item);
                    }
                }

                adapter.filterList(arrayList);

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void OnSuccess(List<ModelCustomer> aList) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        if (aList != null) {
            customerArrayList = aList;
            OnSuccessz(aList);
        } else {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Customer Not Found!!");
        }
    }

    @Override
    public void OnError(int type) {

    }

    @Override
    public void OnErrorReport(String msg) {

    }

    @Override
    public void onClick(String s,int id) {
        {
            // Notify adapter
            bindingCCall.rvCcreason.post(new Runnable() {
                @Override
                public void run() {
                    adapterz.notifyDataSetChanged();
                }
            });
            // Display toast
            reason_id=id;
            if(s.equals("Other"))
            {
                bindingCCall.etComment.setVisibility(View.VISIBLE);
            }else {
                bindingCCall.etComment.setVisibility(View.GONE);
            }
        /*    Toast.makeText(getApplicationContext(),
                            "Selected : " + s,
                            Toast.LENGTH_SHORT)
                    .show();*/
        }
    }

    private void prepareLoadingBar() {
        pd_process=new Dialog(ChemistCallActivity.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;

        dSuccess=new Dialog(ChemistCallActivity.this);
        dSuccess.setContentView(bindingSuccess.getRoot());
        dSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dSuccess.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        dSuccess.getWindow().getAttributes().gravity = Gravity.CENTER;
    }
}
