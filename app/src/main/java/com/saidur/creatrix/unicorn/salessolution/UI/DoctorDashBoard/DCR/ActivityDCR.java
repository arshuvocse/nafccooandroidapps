package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBProductSQLiteHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBSyncHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPromo;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Rp_StockViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterRole;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItem;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Adapter.AdapterPromos;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Adapter.AdapterPromosMenu;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model.ModelCallPriorityProduct;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model.ModelDCRBody;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model.ModelPromoId;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model.ModelRole;
import com.saidur.creatrix.unicorn.salessolution.UI.Leave.LeaveActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.PlaceOrderActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.DateTimeUtil;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDcrBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentVisitedwithlistBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.DialogSuccessCustomBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopStockBinding;

import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDCR extends AppCompatActivity implements AdapterRole.onCheckedListener,AdapterPromosMenu.PromoSelectListener,
AdapterPromos.PromoDltListener{
    ActivityDcrBinding binding;
    private static final int SELECT_DOCTOR_PLACEHOLDER_ID = -1;
    DBDoctorHelper dbDoctorHelper;
    DBCrudHelper dbCrudHelper;

    DBSyncHelper dbSyncHelper;
    DBProductSQLiteHelper dbProductHelper;
    ModelSession session;
    String empId;
    ProgressDialog pd;
    int docId,DCPDetailId=0;
    ComponentVisitedwithlistBinding bindingV;
    Gson gson;
    String promoName = "";
    boolean isP1 = false, isP2 = false, isP3 = false, isP4 = false, isP5 = false;
    boolean isB1 = false, isB2 = false, isB3 = false, isB4 = false, isB5 = false;
    boolean isCHk1 = false, isCHk2 = false, isCHk3 = false, isCHk4 = false, isCHk5 = false;
    String p1 = "", p2 = "", p3 = "", p4 = "", p5 = "";
    ModelDCPItem dtum;
    AdapterPromosMenu adapterMenu;
    AdapterPromos adapte1,adapte2,adapte3,adapte4,adapte5;
    List<ModelPromo> promo_menuList=new ArrayList<>();
    List<ModelPromo> promoList1=new ArrayList<>();
    List<ModelPromo> promoList2=new ArrayList<>();
    List<ModelPromo> promoList3=new ArrayList<>();
    List<ModelPromo> promoList4=new ArrayList<>();
    List<ModelPromo> promoList5=new ArrayList<>();
    int brandId;
    Dialog popup,pd_process;
    DialogSuccessCustomBinding dbinding;
    ComponentLoadingbarBinding lbinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDcrBinding.inflate(getLayoutInflater());
        bindingV = ComponentVisitedwithlistBinding.inflate(getLayoutInflater());
        dbinding= DialogSuccessCustomBinding.inflate(getLayoutInflater());
        lbinding= ComponentLoadingbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prepareDialog();
        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());
        gson = new Gson();
        pd = new ProgressDialog(ActivityDCR.this);
        dbDoctorHelper = new DBDoctorHelper(ActivityDCR.this);
        dbCrudHelper = new DBCrudHelper(ActivityDCR.this);
        session = dbCrudHelper.getSession();
        empId = String.valueOf(session.getEmpInfoId());
        dbProductHelper = new DBProductSQLiteHelper(ActivityDCR.this);
        dbSyncHelper = new DBSyncHelper(ActivityDCR.this);

        binding.ivSync.setOnClickListener(view -> {
            if(NetworkInformation.isConnected(ActivityDCR.this)){
                pd_process.setCancelable(false);
                pd_process.show();
                lbinding.tvText.setText("Syncing...");
                callPromo();
            }else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(),"No Internet!!");
            }

        });

        String today = DateTimeUtil.getDisplayDateNow();
        binding.tvDcrDate.setText(today);

        promo_menuList.add(new ModelPromo(1,"LIT",R.drawable.brochure));
        promo_menuList.add(new ModelPromo(2,"PAD",R.drawable.notebook));
        promo_menuList.add(new ModelPromo(3,"PEN",R.drawable.pen));
        promo_menuList.add(new ModelPromo(4,"GFT",R.drawable.gift));
        promo_menuList.add(new ModelPromo(5,"SMP",R.drawable.samp));
        ViewPromo(promo_menuList);


        switch (Constants.From) {
            case "Doctor_DASHBOARD":
                binding.llPlaned.setVisibility(View.GONE);
                binding.llPlan.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(binding.tvDcrDate.getText().toString().trim())) {
                    for (int j = 0; j < binding.llPlan.getChildCount(); j++) {
                        View view = binding.llPlan.getChildAt(j);
                        view.setEnabled(false); // Or whatever you want to do with the view.
                    }
                } else {
                    for (int s = 0; s < binding.llPlan.getChildCount(); s++) {
                        View view = binding.llPlan.getChildAt(s);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                    }
                }
                binding.tvDcrDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (TextUtils.isEmpty(binding.tvDcrDate.getText().toString().trim())) {
                            for (int j = 0; j < binding.llPlan.getChildCount(); j++) {
                                View view = binding.llPlan.getChildAt(j);
                                view.setEnabled(false); // Or whatever you want to do with the view.
                            }
                        } else {
                            for (int s = 0; s < binding.llPlan.getChildCount(); s++) {
                                View view = binding.llPlan.getChildAt(s);
                                view.setEnabled(true); // Or whatever you want to do with the view.
                            }
                            String data = (String) binding.spinnerPlanned.getSelectedItem();
                            String planned = data.toString();
                            if (planned.equals("Planned")) {
                                binding.tilDoctor.setVisibility(View.VISIBLE);
                                HashMap<String, String> map = new HashMap<>();
                                map.put("empId", empId);
                                if (TextUtils.isEmpty(binding.tvDcrDate.getText().toString().trim())) {
                                    SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Date required");
                                    return;
                                } else {
                                    map.put("date", binding.tvDcrDate.getText().toString().trim());
                                }
                                callPlannedDoctor(map);
                            } else if (planned.equals("Un Planned")) {
                                try {
                                    binding.tilDoctor.setVisibility(View.VISIBLE);
                                    List<DoctorListViewModel> datas = dbDoctorHelper.getDoctorListFromSQLite();
                                    LoadDoctor(datas);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                binding.tilDoctor.setVisibility(View.GONE);
                            }

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                SpinnerSetup();
                binding.ivDatepicker.setOnClickListener(view -> UtilityHelper._datePickerDialogeForDates(binding.tvDcrDate, ActivityDCR.this));
                binding.spinnerPlanned.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String data = (String) binding.spinnerPlanned.getSelectedItem();
                        String planned = data.toString();

                        if (planned.equals("Planned")) {
                            binding.tilDoctor.setVisibility(View.VISIBLE);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("empId", empId);
                            if (TextUtils.isEmpty(binding.tvDcrDate.getText().toString().trim())) {
                                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Date required");
                                return;
                            } else {
                                map.put("date", binding.tvDcrDate.getText().toString().trim());
                            }
                            callPlannedDoctor(map);
                        } else if (planned.equals("Un Planned")) {
                            try {
                                binding.tilDoctor.setVisibility(View.VISIBLE);
                                List<DoctorListViewModel> datas = dbDoctorHelper.getDoctorListFromSQLite();
                                LoadDoctor(datas);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            binding.tilDoctor.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;

            case "DCP_DOCLIST":
                String dcpDate = getIntent().getStringExtra("dcpDate");
                String dataList = getIntent().getStringExtra("dataList");
                String data = getIntent().getStringExtra("data");

                dtum = gson.fromJson(data, ModelDCPItem.class);
                Type type = new TypeToken<List<ModelDCPItem>>() {}.getType();
                List<ModelDCPItem> dtums = gson.fromJson(dataList, type);
                LoadView(dcpDate, dtum, dtums);
                break;
        }

        binding.ivCallclock.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvCallTime, ActivityDCR.this));

        binding.rgCallType.setOnCheckedChangeListener((arg0, id) -> {
            switch (id) {
                case -1:
                    break;
                case R.id.rb_self:
                    binding.llVisitedwith.setVisibility(View.GONE);
                    break;
                case R.id.rb_joint:
                    binding.llVisitedwith.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        });
        binding.rgDrCallType.setOnCheckedChangeListener((arg0, id) -> {
            switch (id) {
                case -1:
                    break;
                case R.id.rb_single:

                    break;
                case R.id.rb_group:

                    break;
                default:
                    break;
            }
        });
        binding.p1.setOnClickListener(view -> {
            try{
                if (((CompoundButton) view).isChecked()) {
                    isP1 = true;
                    isP2 = false;
                    isP3 = false;
                    isP4 = false;
                    isP5 = false;
                    p1 = "P1";
                    isCHk1=true;
                } else {
                    isP1 = false;
                    isCHk1=false;
                    p1 = "";

                    binding.p1.setChecked(false);
                    if(promoList1.size()>0)
                    {
                        promoList1.clear();
                        adapte1.notifyDataSetChanged();
                    }
                }
            }catch (Exception ex){

            }
        });
        binding.p2.setOnClickListener(view -> {
            try{
            if (((CompoundButton) view).isChecked()) {
                isP2 = true;
                isP1 = false;
                isP3 = false;
                isP4 = false;
                isP5 = false;
                p2 = "P2";
                isCHk2=true;
            } else {
                isP2 = false;
                isCHk2=false;
                p2 = "";
                binding.p2.setChecked(false);
                if(promoList2.size()>0)
                {
                    promoList2.clear();
                    adapte2.notifyDataSetChanged();
                }
            }
            }catch (Exception ex){

            }
        });
        binding.p3.setOnClickListener(view -> {
            try{
            if (((CompoundButton) view).isChecked()) {
                isP3 = true;
                isP2 = false;
                isP1 = false;
                isP4 = false;
                isP5 = false;
                p3 = "P3";
                isCHk3=true;
            } else {
                isP3 = false;
                isCHk3=true;
                p3 = "";
                binding.p3.setChecked(false);
                if(promoList3.size()>0)
                {
                    promoList3.clear();
                    adapte3.notifyDataSetChanged();
                }

            }
            }catch (Exception ex){

            }
        });
        binding.p4.setOnClickListener(view -> {
            try{
            if (((CompoundButton) view).isChecked()) {
                isP4 = true;
                isP3 = false;
                isP2 = false;
                isP1 = false;
                isP5 = false;
                p4 = "P4";
                isCHk4=true;

            } else {
                isP4 = false;
                isCHk4=false;
                p4 = "";

                binding.p4.setChecked(false);
                if(promoList4.size()>0)
                {
                    promoList4.clear();
                    adapte4.notifyDataSetChanged();
                }
            }
            }catch (Exception ex){

            }
        });
        binding.p5.setOnClickListener(view -> {
            try{
            if (((CompoundButton) view).isChecked()) {
                isP5 = true;
                isP4 = false;
                isP3 = false;
                isP2 = false;
                isP1 = false;
                isCHk5=true;
                p5 = "P5";
            } else {
                isP5 = false;
                p5 = "";
                isCHk5=false;
                binding.p5.setChecked(false);
                if(promoList5.size()>0)
                {
                    promoList5.clear();
                    adapte5.notifyDataSetChanged();
                }
            }
            }catch (Exception ex){

            }
        });

        binding.spinnerProduct1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                ModelBrand p5Data = (ModelBrand) adapterView.getItemAtPosition(pos);
                ModelBrand b = (ModelBrand) adapterView.getItemAtPosition(pos);
                if(promoList1.size()>0)
                {
                    promoList1.clear();
                   // adapte1.notifyDataSetChanged();
                }
               if(isP1)
               {
                   List<ModelPromo> fromDb = dbDoctorHelper.getPromoByBrand(p5Data.getProductBrandId());
                   promoList1.clear(); // আগের ডেটা মুছতে চাইলে
                   if (fromDb != null && !fromDb.isEmpty()) {
                       promoList1.addAll(fromDb);
                   }
                   List<String> promoNames = new ArrayList<>();
                   for (ModelPromo promo : promoList1) {
                       promoNames.add(promo.getPromoName()); // Assuming getPromoName() returns a String
                   }

                   ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityDCR.this, android.R.layout.simple_spinner_item, promoNames);
                   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   binding.spinnerProductSelect.setAdapter(adapter);
               }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerProduct2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {


                ModelBrand p5Data = (ModelBrand) adapterView.getItemAtPosition(pos);
                ModelBrand b = (ModelBrand) adapterView.getItemAtPosition(pos);
                if(promoList2.size()>0)
                {
                    promoList2.clear();
                    // adapte1.notifyDataSetChanged();
                }
                if(isP2)
                {
                    List<ModelPromo> fromDb = dbDoctorHelper.getPromoByBrand(p5Data.getProductBrandId());
                    promoList2.clear(); // আগের ডেটা মুছতে চাইলে
                    if (fromDb != null && !fromDb.isEmpty()) {
                        promoList2.addAll(fromDb);
                    }
                    List<String> promoNames = new ArrayList<>();
                    for (ModelPromo promo : promoList2) {
                        promoNames.add(promo.getPromoName()); // Assuming getPromoName() returns a String
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityDCR.this, android.R.layout.simple_spinner_item, promoNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerProductSelect2.setAdapter(adapter);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerProduct3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {


                ModelBrand p5Data = (ModelBrand) adapterView.getItemAtPosition(pos);
                ModelBrand b = (ModelBrand) adapterView.getItemAtPosition(pos);
                if(promoList3.size()>0)
                {
                    promoList3.clear();
                    // adapte1.notifyDataSetChanged();
                }
                if(isP3)
                {
                    List<ModelPromo> fromDb = dbDoctorHelper.getPromoByBrand(p5Data.getProductBrandId());
                    promoList3.clear(); // আগের ডেটা মুছতে চাইলে
                    if (fromDb != null && !fromDb.isEmpty()) {
                        promoList3.addAll(fromDb);
                    }
                    List<String> promoNames = new ArrayList<>();
                    for (ModelPromo promo : promoList3) {
                        promoNames.add(promo.getPromoName()); // Assuming getPromoName() returns a String
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityDCR.this, android.R.layout.simple_spinner_item, promoNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerProductSelect3.setAdapter(adapter);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerProduct4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                ModelBrand p5Data = (ModelBrand) adapterView.getItemAtPosition(pos);
                ModelBrand b = (ModelBrand) adapterView.getItemAtPosition(pos);
                if(promoList4.size()>0)
                {
                    promoList4.clear();
                    // adapte1.notifyDataSetChanged();
                }
                if(isP4)
                {
                    List<ModelPromo> fromDb = dbDoctorHelper.getPromoByBrand(p5Data.getProductBrandId());
                    promoList4.clear(); // আগের ডেটা মুছতে চাইলে
                    if (fromDb != null && !fromDb.isEmpty()) {
                        promoList4.addAll(fromDb);
                    }
                    List<String> promoNames = new ArrayList<>();
                    for (ModelPromo promo : promoList4) {
                        promoNames.add(promo.getPromoName()); // Assuming getPromoName() returns a String
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityDCR.this, android.R.layout.simple_spinner_item, promoNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerProductSelect4.setAdapter(adapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerProduct5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                ModelBrand p5Data = (ModelBrand) adapterView.getItemAtPosition(pos);
                ModelBrand b = (ModelBrand) adapterView.getItemAtPosition(pos);
                if(promoList5.size()>0)
                {
                    promoList5.clear();
                    // adapte1.notifyDataSetChanged();
                }
                if(isP5)
                {
                    List<ModelPromo> fromDb = dbDoctorHelper.getPromoByBrand(p5Data.getProductBrandId());
                    promoList5.clear(); // আগের ডেটা মুছতে চাইলে
                    if (fromDb != null && !fromDb.isEmpty()) {
                        promoList5.addAll(fromDb);
                    }
                    List<String> promoNames = new ArrayList<>();
                    for (ModelPromo promo : promoList5) {
                        promoNames.add(promo.getPromoName()); // Assuming getPromoName() returns a String
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityDCR.this, android.R.layout.simple_spinner_item, promoNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerProductSelect5.setAdapter(adapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnSubmit.setOnClickListener(view -> {
            List<String> visitedList = new ArrayList<>();
            if (binding.ckdAm.isChecked()) {
                visitedList.add(binding.ckdAm.getText().toString());
            }
            if (binding.ckdRm.isChecked()) {
                visitedList.add(binding.ckdRm.getText().toString());
            }
            if (binding.ckdCbm.isChecked()) {
                visitedList.add(binding.ckdCbm.getText().toString());
            }
            if (binding.ckdHo.isChecked()) {
                visitedList.add(binding.ckdHo.getText().toString());
            }

            String planned="";
            if(Constants.From.equals("Doctor_DASHBOARD"))
            {
                String datap = (String) binding.spinnerPlanned.getSelectedItem();
                planned = datap.toString();
            }else {
                planned = "Planned";
            }

            int callTypeId = binding.rgCallType.getCheckedRadioButtonId();
            int drcallTypeId = binding.rgDrCallType.getCheckedRadioButtonId();

            RadioButton rbCallType = (RadioButton) findViewById(callTypeId);
            RadioButton drrbCallType = (RadioButton) findViewById(drcallTypeId);
            String calltype = rbCallType.getText().toString().trim();
            String drcalltype = drrbCallType.getText().toString().trim();

            DoctorListViewModel selectedDoctor = getSelectedDoctor();
            if (isPlaceholderDoctor(selectedDoctor)) {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Please select a doctor");
                return;
            }
            docId = selectedDoctor.getDoctorId();
            DCPDetailId = selectedDoctor.getDocTPDetailsId();

            int p1Id = 0;
            int p2Id = 0;
            int p3Id = 0;
            int p4Id = 0;
            int p5Id = 0;

            List<ModelCallPriorityProduct> priorityList = new ArrayList<>();
            // P1
            if (isCHk1 ) {
                ModelBrand p1Data = (ModelBrand) binding.spinnerProduct1.getSelectedItem();
                if (p1Data != null && p1Data.getProductBrandId() != 0 && promoList1 != null && !promoList1.isEmpty()) {
                    p1Id = p1Data.getProductBrandId();

                    List<ModelPromoId> plist = new ArrayList<>();
                    try {
                       // for (int i = 0; i < promoList1.size(); i++) {
                            ModelPromoId pmo = new ModelPromoId();                 // ✅ new instance each time
                            pmo.setPromoId(promoList1.get(0).getPromoId());
                            plist.add(pmo);
                      //  }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ModelCallPriorityProduct cp1 = new ModelCallPriorityProduct(p1, p1Id, plist);
                    priorityList.add(cp1);
                    isB1 = true;                                                   // ✅ set after add
                }
            }

// P2
            if (isCHk2 ) {
                ModelBrand p2Data = (ModelBrand) binding.spinnerProduct2.getSelectedItem();
                if (p2Data != null && p2Data.getProductBrandId() != 0 && promoList2 != null && !promoList2.isEmpty()) {
                    p2Id = p2Data.getProductBrandId();

                    List<ModelPromoId> plist2 = new ArrayList<>();
                    try {
                      //  for (int i = 0; i < promoList2.size(); i++) {
                            ModelPromoId pmo = new ModelPromoId();                 // ✅ new instance
                            pmo.setPromoId(promoList2.get(0).getPromoId());
                            plist2.add(pmo);
                       // }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ModelCallPriorityProduct cp2 = new ModelCallPriorityProduct(p2, p2Id, plist2);
                    priorityList.add(cp2);
                    isB2 = true;                                                   // ✅
                }
            }

// P3
            if (isCHk3) {
                ModelBrand p3Data = (ModelBrand) binding.spinnerProduct3.getSelectedItem();
                if (p3Data != null && p3Data.getProductBrandId() != 0 && promoList3 != null && !promoList3.isEmpty()) {
                    p3Id = p3Data.getProductBrandId();

                    List<ModelPromoId> plist3 = new ArrayList<>();
                    try {
                        //for (int i = 0; i < promoList3.size(); i++) {
                            ModelPromoId pmo = new ModelPromoId();                 // ✅ new instance
                            pmo.setPromoId(promoList3.get(0).getPromoId());
                            plist3.add(pmo);
                       // }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ModelCallPriorityProduct cp3 = new ModelCallPriorityProduct(p3, p3Id, plist3);
                    priorityList.add(cp3);
                    isB3 = true;                                                   // ✅
                }
            }

// P4
            if (isCHk4 ) {
                ModelBrand p4Data = (ModelBrand) binding.spinnerProduct4.getSelectedItem();
                if (p4Data != null && p4Data.getProductBrandId() != 0 && promoList4 != null && !promoList4.isEmpty()) {
                    p4Id = p4Data.getProductBrandId();

                    List<ModelPromoId> plist4 = new ArrayList<>();
                    try {
                      //  for (int i = 0; i < promoList4.size(); i++) {
                            ModelPromoId pmo = new ModelPromoId();                 // ✅ new instance
                            pmo.setPromoId(promoList4.get(0).getPromoId());
                            plist4.add(pmo);
                      //  }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ModelCallPriorityProduct cp4 = new ModelCallPriorityProduct(p4, p4Id, plist4);
                    priorityList.add(cp4);
                    isB4 = true;                                                   // ✅
                }
            }

// P5
            if (isCHk5 ) {
                ModelBrand p5Data = (ModelBrand) binding.spinnerProduct5.getSelectedItem();
                if (p5Data != null && p5Data.getProductBrandId() != 0 && promoList5 != null && !promoList5.isEmpty()) {
                    p5Id = p5Data.getProductBrandId();

                    List<ModelPromoId> plist5 = new ArrayList<>();
                    try {
                        //for (int i = 0; i < promoList5.size(); i++) {
                            ModelPromoId pmo = new ModelPromoId();                 // ✅ new instance
                            pmo.setPromoId(promoList5.get(0).getPromoId());
                            plist5.add(pmo);
                       // }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ModelCallPriorityProduct cp5 = new ModelCallPriorityProduct(p5, p5Id, plist5);
                    priorityList.add(cp5);
                    isB5 = true;                                                   // ✅
                }
            }


            ModelDCRBody save_map = new ModelDCRBody();
            save_map.setEmpId(empId);
            save_map.setDCPDetailId(DCPDetailId);
            if (TextUtils.isEmpty(binding.tvDcrDate.getText().toString().trim())) {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Date Required");
                return;
            } else {
                save_map.setDate(binding.tvDcrDate.getText().toString().trim());
            }
            save_map.setPlanned(planned);
            if(TextUtils.isEmpty(binding.tvCallTime.getText().toString().trim()))
            {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Call Time Required");
                return;
            }else {
                save_map.setCallTime(binding.tvCallTime.getText().toString().trim());
            }
            save_map.setCallType(calltype);
            save_map.setDrcallType(drcalltype);
            save_map.setDrId(String.valueOf(docId));
            save_map.setCallWith(visitedList.toString());
            save_map.setCallpriorityProducts(priorityList);
            Gson gson = new Gson();
            String dataz = gson.toJson(save_map);
            System.out.println(dataz);
            if(NetworkInformation.isConnected(ActivityDCR.this)){
                pd_process.setCancelable(false);
                pd_process.show();
                lbinding.tvText.setText("Submitting...");
                callSave(save_map);
            }else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(),"No Internet!!");
            }

        });

        binding.popStock.setOnClickListener(v -> {
            GetStockData(Integer.parseInt(empId),"Sample");
        });
    }
    public void GetStockData(int empId, String main) {
        pd = new ProgressDialog(ActivityDCR.this);
        pd.setIcon(R.drawable.ic_board);
        pd.setMessage("Stock is Loading....");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        try {
            ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
            Call<List<Rp_StockViewModel>> call = service.GetStockData(empId, main);
            call.enqueue(new Callback<List<Rp_StockViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<Rp_StockViewModel>> call, @NonNull Response<List<Rp_StockViewModel>> response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }

                    // Show stock data in dialog
                    if (response.body() != null && !response.body().isEmpty()) {
                        showStockDataDialog(response.body());
                    } else {
                        ErrorView("No stock data available");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Rp_StockViewModel>> call, @NonNull Throwable t) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                        ErrorView("Slow Connection Detected");
                    } else {
                        ErrorView("Some Error Occurred");
                    }
                }
            });
        } catch (Exception ex) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            ErrorView("Some Error Occurred");
        }
    }
    public void ErrorView(String msg) {
        SnackBarManagement._error_CustomMessage(binding.getRoot(), msg);
    }
    private void showStockDataDialog(List<Rp_StockViewModel> stockList) {
        // Build the message string
        StringBuilder message = new StringBuilder();
        for (Rp_StockViewModel stock : stockList) {
            message.append(String.format("%-30s %10s\n",
                    stock.getProductName(),
                    stock.getStockQty()));
        }

        // Create and show dialog
        AlertDialog dialog = new AlertDialog.Builder(ActivityDCR.this)
                .setTitle("Promo Stock List")
                .setIcon(R.drawable.ic_board)
                .setMessage(message.toString())
                .setPositiveButton("OK", (d, which) -> d.dismiss())
                .setCancelable(true)
                .create();

        dialog.show();

        // Set monospace font for proper alignment
        TextView messageView = dialog.findViewById(android.R.id.message);
        if (messageView != null) {
            messageView.setTypeface(Typeface.MONOSPACE);
        }
    }
    private void prepareDialog() {
        popup=new Dialog(ActivityDCR.this);
        popup.setContentView(dbinding.getRoot());
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popup.getWindow().getAttributes().gravity = Gravity.CENTER;

        pd_process=new Dialog(ActivityDCR.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    private void ViewPromo(List<ModelPromo> promo_menuLists) {
        // Set up RecyclerView
        adapterMenu = new AdapterPromosMenu(ActivityDCR.this, promo_menuLists, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        binding.rvMenuPromo.setLayoutManager(layoutManager);
        binding.rvMenuPromo.setAdapter(adapterMenu);
        binding.rvMenuPromo.setNestedScrollingEnabled(false);
        adapterMenu.notifyDataSetChanged();

        // Populate Spinner

    }


    public void LoadView(String dcpdate, ModelDCPItem dtum, List<ModelDCPItem> dcpList) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date newDate = format.parse(dcpdate);

            String date = DateTimeUtil.getDisplayDate(newDate);
            binding.tvDcrDate.setText(date);
            binding.ivDatepicker.setVisibility(View.GONE);
            binding.ivDatepicker.setEnabled(false);

            binding.llPlaned.setVisibility(View.VISIBLE);
            binding.planned.setText("Planned");
            binding.llPlan.setVisibility(View.GONE);
            List<DoctorListViewModel> docList=new ArrayList<>();
            for(int i=0;i<dcpList.size();i++)
            {
                DoctorListViewModel doc=new DoctorListViewModel();
                doc.setDoctorId(Integer.parseInt(dcpList.get(i).getDoctorId()));
                doc.setDocTPDetailsId(Integer.parseInt(dcpList.get(i).getDCPDetailId()));
                doc.setDoctorName(dcpList.get(i).getDoctorName());

                docList.add(doc);
            }
            LoadDoctor(docList);
            if (dtum.getDoctorName() != null) {
                binding.actDoctor.setSelection(getIndex(binding.actDoctor, dtum.getDoctorName()));
            } else {
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*        binding.spinnerPlanned.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });*/


    }
    private void setProductsSpinner(List<ModelBrand> productList) {

        ModelBrand brand = new ModelBrand();
        brand.setProductBrandId(0);
        brand.setProductSQName("Select");
        productList.add(0, brand);

        ArrayAdapter<ModelBrand> dapter = new ArrayAdapter<>(ActivityDCR.this, android.R.layout.simple_spinner_dropdown_item, productList);
        binding.spinnerProduct1.setAdapter(dapter);
        binding.spinnerProduct2.setAdapter(dapter);
        binding.spinnerProduct3.setAdapter(dapter);
        binding.spinnerProduct4.setAdapter(dapter);
        binding.spinnerProduct5.setAdapter(dapter);

    }
    private void callSave(ModelDCRBody saveDCR) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResponseResultInfo> call = service.SaveDCR(saveDCR);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    ResponseResultInfo rsp = response.body();
                    if (rsp.isSuccess) {
                        dialogSuccess(rsp.getMsd());
                    } else {
                        SnackBarManagement._warning_CustomMessage(binding.getRoot(), rsp.getMsd());
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                        //viewBindings.docTxt.setText("Slow Network detected");
                    } else {
                        // viewBindings.docTxt.setText("Some Error occured");
                    }
                }
            });
        } catch (Exception ex) {
            if (pd_process != null || pd_process.isShowing()) {
                pd_process.dismiss();
            }
        }
    }
    private void callPlannedDoctor(HashMap<String, String> empid) {
        pd.setMessage("Loading...");
        pd.show();
        try {
            //All Doctor Api call
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<DoctorListViewModel>> call = service.GetPlannedDoctorList(empid);
            call.enqueue(new Callback<List<DoctorListViewModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<DoctorListViewModel>> call, @NonNull Response<List<DoctorListViewModel>> response) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    List<DoctorListViewModel> doctors = response.body();
                    if (doctors != null) {
                        LoadDoctor(doctors);
                    } else {
                        LoadDoctor(doctors);
                        SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Response/Doctor Not Found!!");
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<DoctorListViewModel>> call, @NonNull Throwable t) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                        //viewBindings.docTxt.setText("Slow Network detected");
                    } else {
                        // viewBindings.docTxt.setText("Some Error occured");
                    }
                }
            });
        } catch (Exception ex) {
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

    private void callPromo()
    {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<List<ModelPromo>> call = service.GetPromo();
            call.enqueue(new Callback<List<ModelPromo>>() {
                @Override
                public void onResponse(@NonNull Call<List<ModelPromo>> call, @NonNull Response<List<ModelPromo>> response) {
                    List<ModelPromo> docChm = response.body();
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (docChm != null) {
                        boolean isTrue = dbSyncHelper.InsertPromo(docChm);
                        if (isTrue) {
                            popup.show();
                            popup.setCancelable(false);
                            dbinding.dialogTag.setText("Success");
                            dbinding.tvMsg.setText("Promo Synced");
                            dbinding.btnGoto.setOnClickListener(view -> {
                                popup.dismiss();
                            });
                        } else {
                           SnackBarManagement._error_CustomMessage(binding.getRoot(),"Insertion Problem!!");
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<ModelPromo>> call, @NonNull Throwable t) {
                    if (pd_process != null || pd_process.isShowing()) {
                        pd_process.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {
                    } else {
                    }
                }
            });

        } catch (Exception ex) {
        }
    }
    private void SpinnerSetup() {
        List<String> planned = new ArrayList<>();
        planned.add(0, "Planned");
        planned.add(1, "Un Planned");
        ArrayAdapter<String> datapter = new ArrayAdapter<>(ActivityDCR.this, android.R.layout.simple_spinner_item, planned);
        binding.spinnerPlanned.setAdapter(datapter);


    }

    private void LoadDoctor(List<DoctorListViewModel> docList) {
        try {
            List<DoctorListViewModel> doctors = new ArrayList<>();
            DoctorListViewModel placeholder = new DoctorListViewModel();
            placeholder.setDoctorId(SELECT_DOCTOR_PLACEHOLDER_ID);
            placeholder.setDoctorName("-- Select Doctor --");
            doctors.add(placeholder);
            if (docList != null) {
                doctors.addAll(docList);
            }

            DoctorSearchAdapter datapter = new DoctorSearchAdapter(doctors);
            binding.actDoctor.setTitle("Select Doctor");
            binding.actDoctor.setPositiveButton("Close");
            binding.actDoctor.setPrompt("Search by name / code / contact");
            binding.actDoctor.setAdapter(datapter);
            binding.actDoctor.setSelection(0, false);
            try {
                binding.actDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                        DoctorListViewModel doc = (DoctorListViewModel) parent.getItemAtPosition(i);
                        if (isPlaceholderDoctor(doc)) {
                            docId = 0;
                            DCPDetailId = 0;
                            clearPrioritySelection();
                            setProductsSpinner(new ArrayList<>());
                            return;
                        }
                        docId = doc.getDoctorId();
                        DCPDetailId = doc.getDocTPDetailsId();
                        clearPrioritySelection();
                        List<ModelBrand> productList = dbCrudHelper.getBrandList_SQLite(docId);
                       // List<ModelBrand> productList = dbCrudHelper.getBrandList_SQLite(0);
                        if (productList != null) {
                            setProductsSpinner(productList);
                        } else {
                            setProductsSpinner(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                /*binding.actDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                        DoctorListViewModel doc = (DoctorListViewModel) parent.getItemAtPosition(i);
                        int docId = doc.getDoctorId();
                        List<ModelBrand> productList = dbCrudHelper.getBrandList_SQLite(docId);
                        if (productList != null) {
                            setProductsSpinner(productList);
                        }
                    }
                });*/
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage().toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearPrioritySelection() {
        binding.p1.setChecked(false);
        binding.p2.setChecked(false);
        binding.p3.setChecked(false);
        binding.p4.setChecked(false);
        binding.p5.setChecked(false);
        isP1 = false;
        isP2 = false;
        isP3 = false;
        isP4 = false;
        isP5 = false;
        isB1 = false;
        isB2 = false;
        isB3 = false;
        isB4 = false;
        isB5 = false;
        isCHk1 = false;
        isCHk2 = false;
        isCHk3 = false;
        isCHk4 = false;
        isCHk5 = false;
        p1 = "";
        p2 = "";
        p3 = "";
        p4 = "";
        p5 = "";
        promoList1.clear();
        promoList2.clear();
        promoList3.clear();
        promoList4.clear();
        promoList5.clear();
        clearPromoSpinner(binding.spinnerProductSelect);
        clearPromoSpinner(binding.spinnerProductSelect2);
        clearPromoSpinner(binding.spinnerProductSelect3);
        clearPromoSpinner(binding.spinnerProductSelect4);
        clearPromoSpinner(binding.spinnerProductSelect5);
    }

    private void clearPromoSpinner(Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityDCR.this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private DoctorListViewModel getSelectedDoctor() {
        Object selected = binding.actDoctor.getSelectedItem();
        if (selected instanceof DoctorListViewModel) {
            return (DoctorListViewModel) selected;
        }
        return null;
    }

    private boolean isPlaceholderDoctor(DoctorListViewModel doctor) {
        return doctor == null || doctor.getDoctorId() == SELECT_DOCTOR_PLACEHOLDER_ID;
    }

    private String getDoctorDisplayText(DoctorListViewModel doctor) {
        if (doctor == null) {
            return "";
        }
        if (isPlaceholderDoctor(doctor)) {
            return safeString(doctor.getDoctorName());
        }

        String code = safeString(doctor.getDoctorCode());
        String name = safeString(doctor.getDoctorName());
        if (!code.isEmpty() && !name.isEmpty()) {
            return code + " : " + name;
        }
        if (!name.isEmpty()) {
            return name;
        }
        return code;
    }

    private String getDoctorSearchText(DoctorListViewModel doctor) {
        if (doctor == null) {
            return "";
        }
        return (getDoctorDisplayText(doctor) + " "
                + safeString(doctor.getDocContact()) + " "
                + safeString(doctor.getMarketName()) + " "
                + doctor.getDoctorId()).toLowerCase(Locale.US);
    }

    private String safeString(String value) {
        return value == null ? "" : value.trim();
    }

    private class DoctorSearchAdapter extends ArrayAdapter<DoctorListViewModel> {
        private final List<DoctorListViewModel> allDoctors;

        DoctorSearchAdapter(List<DoctorListViewModel> doctors) {
            super(ActivityDCR.this, R.layout.spinner_doctor_item, new ArrayList<>(doctors));
            allDoctors = new ArrayList<>(doctors);
            setDropDownViewResource(R.layout.spinner_doctor_item);
        }

        @Override
        public boolean isEnabled(int position) {
            return !isPlaceholderDoctor(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            bindDoctorText(view, getItem(position));
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            bindDoctorText(view, getItem(position));
            return view;
        }

        @Override
        public Filter getFilter() {
            return doctorFilter;
        }

        private void bindDoctorText(View view, DoctorListViewModel doctor) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                boolean enabled = !isPlaceholderDoctor(doctor);
                textView.setText(getDoctorDisplayText(doctor));
                textView.setEnabled(enabled);
                textView.setAlpha(enabled ? 1f : 0.6f);
            }
        }

        private final Filter doctorFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint == null ? "" : constraint.toString().trim().toLowerCase(Locale.US);
                List<DoctorListViewModel> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered.addAll(allDoctors);
                } else {
                    for (DoctorListViewModel doctor : allDoctors) {
                        if (!isPlaceholderDoctor(doctor) && getDoctorSearchText(doctor).contains(query)) {
                            filtered.add(doctor);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtered;
                results.count = filtered.size();
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                if (results.values != null) {
                    addAll((List<DoctorListViewModel>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    private void dialogSuccess(String msd) {
       /* new androidx.appcompat.app.AlertDialog.Builder(ActivityDCR.this)
                .setTitle("Success")
                .setMessage(msd)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    finish();
                    // alertDialog.dismiss();
                }).setCancelable(false).show();*/

        popup.show();
        popup.setCancelable(false);
        dbinding.dialogTag.setText("Success");
        dbinding.tvMsg.setText(msd);
        dbinding.btnGoto.setOnClickListener(view -> {
            //onBackPressed();
            popup.dismiss();
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public void onCheckedData(List<ModelRole> ckdList) {
    }

    private int getIndex(Spinner designationSpinner, String edit_dname) {
        for (int i = 0; i < designationSpinner.getCount(); i++) {
            if (designationSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(edit_dname)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onPromoSelect(ModelPromo promo) {
        if(isP1 && isB1){
            promoList1.add(promo);
            ViewPromo1(promoList1);
        }
        else if(isP2 && isB2)
        {
            promoList2.add(promo);
            ViewPromo2(promoList2);
        }
        else if(isP3 && isB3)
        {
            promoList3.add(promo);
            ViewPromo3(promoList3);
        }
        else if(isP4 && isB4)
        {
            promoList4.add(promo);
            ViewPromo4(promoList4);
        }
        else if(isP5 && isB5)
        {
            promoList5.add(promo);
            ViewPromo5(promoList5);
        }
    }

    @Override
    public void onPromoDLTClick(ModelPromo promo) {

        if(isP1){

        }

    }
    private void ViewPromo1(List<ModelPromo> promoList1) {
        adapte1=new AdapterPromos(ActivityDCR.this,promoList1,this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,5);
        binding.rvP1Promo.setLayoutManager(layoutManager);
        binding.rvP1Promo.setAdapter(adapte1);
        binding.rvP1Promo.setNestedScrollingEnabled(false);
        adapte1.notifyDataSetChanged();
    }
    private void ViewPromo2(List<ModelPromo> promoList2) {
        adapte2=new AdapterPromos(ActivityDCR.this,promoList2,this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,5);
        binding.rvP2Promo.setLayoutManager(layoutManager);
        binding.rvP2Promo.setAdapter(adapte2);
        binding.rvP2Promo.setNestedScrollingEnabled(false);
        adapte2.notifyDataSetChanged();
    }
    private void ViewPromo3(List<ModelPromo> promoList3) {
        adapte3=new AdapterPromos(ActivityDCR.this,promoList3,this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,5);
        binding.rvP3Promo.setLayoutManager(layoutManager);
        binding.rvP3Promo.setAdapter(adapte3);
        binding.rvP3Promo.setNestedScrollingEnabled(false);
        adapte3.notifyDataSetChanged();
    }
    private void ViewPromo4(List<ModelPromo> promoList4) {
        adapte4=new AdapterPromos(ActivityDCR.this,promoList4,this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,5);
        binding.rvP4Promo.setLayoutManager(layoutManager);
        binding.rvP4Promo.setAdapter(adapte4);
        binding.rvP4Promo.setNestedScrollingEnabled(false);
        adapte4.notifyDataSetChanged();
    }
    private void ViewPromo5(List<ModelPromo> promoList5) {
        adapte5=new AdapterPromos(ActivityDCR.this,promoList5,this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,5);
        binding.rvP5Promo.setLayoutManager(layoutManager);
        binding.rvP5Promo.setAdapter(adapte5);
        binding.rvP5Promo.setNestedScrollingEnabled(false);
        adapte5.notifyDataSetChanged();
    }

}
