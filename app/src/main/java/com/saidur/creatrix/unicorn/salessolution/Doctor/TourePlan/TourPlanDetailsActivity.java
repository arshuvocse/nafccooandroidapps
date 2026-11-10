package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.CheckedCustomerItem;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.CustomerItemChkAdapter;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.TourPlanReq;
import com.saidur.creatrix.unicorn.salessolution.Interface.DeleteListener;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketStracture;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITourplan;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.Market;
import com.saidur.creatrix.unicorn.salessolution.Model.MarketStructure.LviewHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPurposeViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.TourApiCall;
import com.saidur.creatrix.unicorn.salessolution.Presentar.MarketStructurePresenter;
import com.saidur.creatrix.unicorn.salessolution.Presentar.TourPlanPresenter;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SessionManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTourPlanDetailsBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopTourplanAddMarketwiseBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopTourplanEditMarketwiseBinding;

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

public class TourPlanDetailsActivity extends AppCompatActivity implements IMarketStracture.View, ITourplan.View, DeleteListener, CheckedCustomerItem/*, TourPlanDtailsaAdapter.TPEditListener*/ {
    private ActivityTourPlanDetailsBinding binding;
    //Popup Layout
    PopTourplanAddMarketwiseBinding pbinding;
    PopTourplanEditMarketwiseBinding pbindingEdit;
    int selectedRegionId, selectedAreaId, selectedTeriId, selectedSTeri, selectedMarket, selectedTPP, empId, SN;
    String selectedMarketName, selectedTPPName;
    MarketStructurePresenter mpresenter;
    TourPlanPresenter tppresenter;
    String roleType;

    SessionManagement session;
    HashMap<String, String> user;

    TourPlanDtailsaAdapter adapter;
    SimpleDateFormat dateFormat;
    String ddy;
    Dialog popupTPP, popupTPPEdit;
    DBCrudHelper dbCrudHelper;
    List<TourPurposeViewModel> tppList;
    MonthDate monthDatel;
    List<TourPlanViewModel> tpl = new ArrayList<>();


    List<ModelCustomer> aCustomerList = new ArrayList<>();
    List<ModelCustomer> chkCustomerList = new ArrayList<>();
    String[] listItemCustomer_Customer;
    boolean[] checkedItems_Customer;
    ArrayList<Integer> mUserItems_Cust = new ArrayList<>();


    CustomerItemChkAdapter itemChkAdapter;
    Dialog popupCustomer;
    RecyclerView rv_customer;
    EditText srchview;
    TextView done_cust, cancel_cust, title;
    int a, b, c;
    String currentdate, edit_dName, Tedit_dName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTourPlanDetailsBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_tour_plan_details);
        setContentView(binding.getRoot());
        dbCrudHelper = new DBCrudHelper(TourPlanDetailsActivity.this);
        mpresenter = new MarketStructurePresenter(this, this);
        tppresenter = new TourPlanPresenter(this, this);

        session = new SessionManagement(getApplicationContext());
        user = session.getUserDetails();

        empId = Integer.parseInt(user.get(SessionManagement.KEY_EmpId));
        roleType = user.get(SessionManagement.KEY_EmpRoleType);


        String month = getIntent().getStringExtra("Month");
        String year = getIntent().getStringExtra("Year");
        String date = getIntent().getStringExtra("Date");
        String day = getIntent().getStringExtra("Day");

        ddy = month + " " + "," + year;
        // binding.tvMonthdateyear.setText(month + " " + date + "," + year);
        binding.tvMonthdateyear.setText(ddy);
        SimpleDateFormat nameV = new SimpleDateFormat("EEE", Locale.US);
        binding.tvOnlyday.setText(day);

        Gson gson = new Gson();
        monthDatel = gson.fromJson(getIntent().getStringExtra("tpitemjson"), MonthDate.class);
       /* if (monthDatel != null) {
            //SetInRecyclerview(monthDatel);
        } else {
        }*/
        // aMondateList= CalculateTotaldayesinMonth(mCalendar,daysInMonth,year,month);
        try {
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            currentdate = getIntent().getStringExtra("TDate");
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date Todate = null;
            Date CurrentDate = null;
            Todate = dateFormat.parse(today);
            CurrentDate = dateFormat.parse(currentdate);

            tppresenter.getTourPlanDailyByEmpId(a, b, empId, currentdate);

            if (CurrentDate != null && Todate != null) {
                if (CurrentDate.before(Todate)) {
                    binding.fabAdd.setVisibility(View.GONE);
                    binding.btnDone.setVisibility(View.GONE);
                } else {
                    binding.fabAdd.setVisibility(View.VISIBLE);
                    binding.btnDone.setVisibility(View.VISIBLE);
                }
            } else {
                binding.fabAdd.setVisibility(View.VISIBLE);
                binding.btnDone.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        onLoadCustomerReceived(dbCrudHelper.getCustomerList_SQLite());
        aCustomerList = dbCrudHelper.getCustomerList_SQLite();

        popup_tpp(ddy);
        //popup_tppedit(ddy);
        binding.fabAdd.setOnClickListener(v -> {
            chkCustomerList.clear();
            popupTPP.show();
        });
        binding.btnDone.setOnClickListener(v -> {
            TourPlanReq req = new TourPlanReq();
            req.setaTourPlanInfo(tpl);
            Gson gson1 = new Gson();
            String data = gson1.toJson(req);
            System.out.println("datapostperday" + data);
           tppresenter.saveTourPlanPerdayByEmpId(req);
        });
    }

    //add data for post
    private void popup_tpp(String ddy) {
        popupTPP = new Dialog(TourPlanDetailsActivity.this);
        popupTPP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupTPP.setCancelable(true);
        pbinding = PopTourplanAddMarketwiseBinding.inflate(LayoutInflater.from(TourPlanDetailsActivity.this));
        //popupDoctor.setContentView(popupView);
        popupTPP.setContentView(pbinding.getRoot());
        // popupTPP.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupTPP.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //Role wise spinner populate
        try {
            switch (roleType) {
                case "MIO":
                    pbinding.regiondiv.setVisibility(View.GONE);
                    pbinding.areadiv.setVisibility(View.GONE);
                    mpresenter.GetTeritoryLocal(0);
                    break;
                case "AM":
                    pbinding.regiondiv.setVisibility(View.GONE);
                    pbinding.areadiv.setVisibility(View.VISIBLE);
                    mpresenter.GetAreaLocal(0);
                    break;
                case "DZSM":
                case "NSM":
                case "Admin":
                    pbinding.regiondiv.setVisibility(View.VISIBLE);
                    pbinding.areadiv.setVisibility(View.VISIBLE);
                    mpresenter.GetRegionLocal(0);
                    break;

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        pbinding.tourDate.setText(ddy);
        LoadTourPurpose(pbinding.tourPlanPurposeSpinner);
        pbinding.custAdd.setOnClickListener(v -> {
            // showDialog_Customer();
            /*try {
                for (int i = 0; i < monthDatel.getaTpViewList().size(); i++) {
                    rv_customer.removeItemDecorationAt(i);
                    //chkCustomerList.clear();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }*/
            popup_Customer();
            popupCustomer.show();

        });
        pbinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupTPP.cancel();
            }
        });
        //popup for tp details recyclerview
        pbinding.tpsubmitBnt.setOnClickListener(v -> {
            try {
                TourPlanViewModel tv = new TourPlanViewModel();
                tv.setTourPlanId(0);
                tv.setMarketId(selectedMarket);
                tv.setMarketName(selectedMarketName);
                tv.setTPId(selectedTPP);
                tv.setEmpInfoId(empId);
                tv.setTourPlanDate(pbinding.tourDate.getText().toString());
                tv.setTPName(selectedTPPName);
                if (tpl.size() == 0) {
                    tv.setSerialNo(1);
                } else {
                    tv.setSerialNo(tpl.size() + 1);
                }
                tv.setaCustomerMasterList(chkCustomerList);
                tpl.add(tv);
                SetInRecyclerviewData(tpl);
                adapter.notifyDataSetChanged();

                // chkCustomerList.clear();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            popupTPP.dismiss();
        });
    }

    //edit tour Plan
    private void popup_tppedit(String ddy) {
        popupTPPEdit = new Dialog(TourPlanDetailsActivity.this);
        popupTPPEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupTPPEdit.setCancelable(true);
        pbindingEdit = PopTourplanEditMarketwiseBinding.inflate(LayoutInflater.from(TourPlanDetailsActivity.this));
        popupTPPEdit.setContentView(pbindingEdit.getRoot());
        popupTPPEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //Role wise spinner populate
        try {
            switch (roleType) {
                case "MIO":
                    pbindingEdit.regiondiv.setVisibility(View.GONE);
                    pbindingEdit.areadiv.setVisibility(View.GONE);
                    mpresenter.GetTeritoryLocal(0);
                    break;
                case "AM":
                    pbindingEdit.regiondiv.setVisibility(View.GONE);
                    pbindingEdit.areadiv.setVisibility(View.VISIBLE);
                    mpresenter.GetAreaLocal(0);
                    break;
                case "DZSM":
                case "NSM":
                case "Admin":
                    pbindingEdit.regiondiv.setVisibility(View.VISIBLE);
                    pbindingEdit.areadiv.setVisibility(View.VISIBLE);
                    mpresenter.GetRegionLocal(0);
                    break;

            }
            /*if (cal.getRegionName() != null) {
                edit_dName = cal.getRegionName();
                viewBinding.regionSpinner.setSelection(getIndex(viewBinding.regionSpinner, edit_dName));
            } else {
            }*/
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        pbindingEdit.tourDate.setText(ddy);


        LoadTourPurpose(pbindingEdit.tourPlanPurposeSpinnerE);
        pbindingEdit.custAdd.setOnClickListener(v -> {
            // showDialog_Customer();
            popup_Customer();
            popupCustomer.show();

        });
        pbindingEdit.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupTPPEdit.cancel();
            }
        });
        //popup for tp details recyclerview
        pbindingEdit.tpupdateBnt.setOnClickListener(v -> {
            try {
                TourPlanViewModel tv = new TourPlanViewModel();
                tv.setTourPlanId(0);
                tv.setMarketId(selectedMarket);
                tv.setMarketName(selectedMarketName);
                tv.setTPId(selectedTPP);
                tv.setEmpInfoId(empId);
                tv.setTourPlanDate(pbinding.tourDate.getText().toString());
                tv.setTPName(selectedTPPName);
                if (tpl.size() == 0) {
                    tv.setSerialNo(1);
                } else {
                    tv.setSerialNo(tpl.size() + 1);
                }
                tv.setaCustomerMasterList(chkCustomerList);
                tpl.add(tv);
                SetInRecyclerviewData(tpl);
                adapter.notifyDataSetChanged();
                // chkCustomerList.clear();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            popupTPPEdit.dismiss();
        });
    }

    //Customer List CheckboxView
    public void popup_Customer() {
        popupCustomer = new Dialog(TourPlanDetailsActivity.this);
        popupCustomer.setContentView(R.layout.common_dialog);
        popupCustomer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupCustomer.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        popupCustomer.getWindow().getAttributes().gravity = Gravity.TOP;
        popupCustomer.setCanceledOnTouchOutside(false);

        rv_customer = popupCustomer.findViewById(R.id.rv_doclist);
        title = popupCustomer.findViewById(R.id.ff);
        srchview = popupCustomer.findViewById(R.id.srchview);
        done_cust = popupCustomer.findViewById(R.id.btn_done);
        cancel_cust = popupCustomer.findViewById(R.id.btn_cancel);
        title.setText("Select Customer");
       /* cleare = popupDoctor.findViewById(R.id.cleare);
        cleare.setOnClickListener(v -> {
            try {
                for (int i = 0; i < monthDatel.getVisitplanList().size(); i++) {
                    rv_doctors.removeItemDecorationAt(i);
                    vpl.clear();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });*/
        try {
            itemChkAdapter = new CustomerItemChkAdapter(TourPlanDetailsActivity.this, aCustomerList, this);
            rv_customer.setLayoutManager(new LinearLayoutManager(TourPlanDetailsActivity.this));
            rv_customer.setAdapter(itemChkAdapter);
            rv_customer.scrollToPosition(0);
            //  itemChkAdapter.notifyDataSetChanged();

            done_cust.setOnClickListener(v -> {
                popupCustomer.dismiss();
                Gson gson = new Gson();
                String data = gson.toJson(chkCustomerList);
                //    Toast.makeText(this, "data 2 "+data, Toast.LENGTH_SHORT).show();

                ArrayAdapter<ModelCustomer> custAd = new ArrayAdapter<>(TourPlanDetailsActivity.this, R.layout.lv_dcrbrand, R.id.dcrbrand, chkCustomerList);
                pbinding.custListLv.setAdapter(custAd);
                LviewHelper.getListViewSize(pbinding.custListLv);
                // adapter = new DoctorVisitPlanDtailsaAdapter(TourPlanDetailsActivity.this, monthDatel, this);
                // ArrayAdapter<Customer> custAdapter=new ArrayAdapter<>(TourPlanDetailsActivity.this,R.layout.lv_visited);
            });
            cancel_cust.setOnClickListener(v -> {
               // rv_customer.removeItemDecorationAt();
                chkCustomerList.clear();
                popupCustomer.dismiss();
             /*   try {
                    for (int i = 0; i < monthDatel.getaTpViewList().size(); i++) {
                        rv_customer.removeItemDecorationAt(i);
                        chkCustomerList.clear();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/
            });
            search();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void SetInRecyclerviewData(List<TourPlanViewModel> aList) {
        adapter = new TourPlanDtailsaAdapter(TourPlanDetailsActivity.this, aList, this);
        binding.rvNewadddoc.setHasFixedSize(true);
        binding.rvNewadddoc.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(TourPlanDetailsActivity.this);
        binding.rvNewadddoc.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }

    //CUSTOMER popup
    public void showDialog_Customer() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TourPlanDetailsActivity.this);
        mBuilder.setTitle("Select Customer");
        mBuilder.setMultiChoiceItems(listItemCustomer_Customer, checkedItems_Customer, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked) {
                    mUserItems_Cust.add(position);
                } else {
                    mUserItems_Cust.remove((Integer.valueOf(position)));
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                String custId;
                for (int i = 0; i < mUserItems_Cust.size(); i++) {
                    /*Customer customer=new Customer();
                    customer.setCustomerMasterId();
                    aCustomerList.add(customer);*/

                    item = item + listItemCustomer_Customer[mUserItems_Cust.get(i)];
                    if (i != mUserItems_Cust.size() - 1) {
                        item = item + ",";
                    }
                }
                //pbinding.custListStr.setText(item);

            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                try {
                    for (int i = 0; i < checkedItems_Customer.length; i++) {
                        checkedItems_Customer[i] = false;
                        mUserItems_Cust.clear();
                        // pbinding.custListStr.setText("");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    public void onLoadCustomerReceived(List<ModelCustomer> aList) {
        try {
            if (aList != null) {
                listItemCustomer_Customer = new String[aList.size()];
                for (int i = 0; i < aList.size(); i++) {
                    listItemCustomer_Customer[i] = aList.get(i).getCustomerName();
                    // aCustomerList=aList;
                }
                checkedItems_Customer = new boolean[listItemCustomer_Customer.length];

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void deleteItem(int pos) {
        tpl.remove(pos);
        // adapter.notifyItemRemoved(pos);
        try {
            new AlertDialog.Builder(TourPlanDetailsActivity.this)
                    .setTitle("Success")
                    .setMessage("Tour Plan Deleted Successfully")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            for (int i = 0; i < tpl.size(); i++) {
                                tpl.get(i).setSerialNo(i + 1);
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }).setCancelable(false).show();

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            SnackBarManagement._error_CustomMessage(binding.masterLayout, str);
        }
    }

    @Override
    public void deleteItemFromServer(int pos, int id) {
        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<ResultInfo> call = service.DeleteTourPlanData(id);
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                    ResultInfo info = response.body();
                    if (info.getSuccess() == true) {

                        new AlertDialog.Builder(TourPlanDetailsActivity.this)
                                .setTitle("Success")
                                .setMessage("Tour Plan Deleted Successfully")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        // tppresenter.getTourPlanDailyByEmpId(a, b, empId, currentdate);
                                        tpl.remove(pos);
                                        for (int i = 0; i < tpl.size(); i++) {
                                            tpl.get(i).setSerialNo(i + 1);
                                            /*tpl.get(i).setMarketId(tpl.get(i).getMarketId());
                                            tpl.get(i).setMarketName(tpl.get(i).getMarketName());
                                            tpl.get(i).setTPId(tpl.get(i).getTPId());
                                            tpl.get(i).setEmpInfoId(tpl.get(i).getEmpInfoId());
                                            tpl.get(i).setTourPlanDate(tpl.get(i).getTourPlanDate());
                                            tpl.get(i).setTPName(tpl.get(i).getTPName());
                                            tpl.get(i).setTourPlanDate(tpl.get(i).getTourPlanDate());
                                            tpl.get(i).setaCustomerMasterList(tpl.get(i).getaCustomerMasterList());*/
                                        }
                                        adapter.notifyDataSetChanged();
                                    }

                                }).setCancelable(false).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultInfo> call, @NonNull Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        SnackBarManagement._error_CustomMessage(binding.masterLayout, t.getMessage());
                    } else {
                        SnackBarManagement._error_CustomMessage(binding.masterLayout, t.getMessage());
                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            SnackBarManagement._error_CustomMessage(binding.masterLayout, str);
        }
    }

    @Override
    public void editTourPlanInfo(int pos, int id) {

    }

    @Override
    public void editItem(int pos, int id, int rid, int aid, int tid, int stid, int mid, String region, String area, String territory, String subTerritory, String market) {
        onLoadCustomerReceived(dbCrudHelper.getCustomerList_SQLite());
        aCustomerList = dbCrudHelper.getCustomerList_SQLite();
       /* if (String.valueOf(id) != null) {
            popupTPPEdit.show();
            //SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Under Development");
        }*/
        popupTPPEdit = new Dialog(TourPlanDetailsActivity.this);
        popupTPPEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupTPPEdit.setCancelable(true);
        pbindingEdit = PopTourplanEditMarketwiseBinding.inflate(LayoutInflater.from(TourPlanDetailsActivity.this));
        popupTPPEdit.setContentView(pbindingEdit.getRoot());
        popupTPPEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //Role wise spinner populate
        try {
            switch (roleType) {
                case "MIO":
                    pbindingEdit.regiondiv.setVisibility(View.GONE);
                    pbindingEdit.areadiv.setVisibility(View.GONE);
                    mpresenter.GetTeritoryLocal(0);
                    break;
                case "AM":
                    pbindingEdit.regiondiv.setVisibility(View.GONE);
                    pbindingEdit.areadiv.setVisibility(View.VISIBLE);
                    mpresenter.GetAreaLocal(0);
                    break;
                case "DZSM":
                case "NSM":
                case "Admin":
                    pbindingEdit.regiondiv.setVisibility(View.VISIBLE);
                    pbindingEdit.areadiv.setVisibility(View.VISIBLE);
                    mpresenter.GetGroupLocal();
                    mpresenter.GetRegionLocal(0);

                    mpresenter.GetAreaLocal(0);
                    mpresenter.GetTeritoryLocal(0);
                    mpresenter.GetSTeritoryLocal(0);
                    mpresenter.GetMarketLocal(0);
                    break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (region != null) {
            edit_dName = region;
            pbindingEdit.regionSpinnerE.setSelection(getIndex(pbindingEdit.regionSpinnerE, edit_dName));

            ModelRegion rtype = (ModelRegion) pbindingEdit.regionSpinnerE.getSelectedItem();
            if(!String.valueOf(rid).equals(""))
            {
                mpresenter.GetAreaLocal(rid);
            }else {
                mpresenter.GetAreaLocal(rtype.getRegionId());
            }
        } else {
        }
        if (area != null) {
            edit_dName = area;
            pbindingEdit.areaSpinnerE.setSelection(getIndex(pbindingEdit.areaSpinnerE, edit_dName));
            ModelArea areaType=(ModelArea) pbindingEdit.areaSpinnerE.getSelectedItem();
            if(!String.valueOf(aid).equals(""))
            {
                mpresenter.GetTeritoryLocal(aid);
            }else {
               /* pbindingEdit.areaSpinnerE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            Area area = (Area) parent.getSelectedItem();
                            selectedAreaId = area.getAreaId();
                            mpresenter.GetTeritoryLocal(selectedAreaId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        mpresenter.GetTeritoryLocal(aid);
                        Toast.makeText(TourPlanDetailsActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
                    }
                });*/
                mpresenter.GetTeritoryLocal(areaType.getAreaId());
            }
            /*pbindingEdit.areaSpinnerE.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    pbindingEdit.areaSpinnerE.removeOnLayoutChangeListener(this::onLayoutChange);
                    Area areaType=(Area) pbindingEdit.areaSpinnerE.getSelectedItem();
                    if(areaType.getAreaId()==0)
                    {
                        mpresenter.GetTeritoryLocal(aid);
                    }else {
                        mpresenter.GetTeritoryLocal(areaType.getAreaId());
                    }


                }
            });*/
        } else {
        }

        if (territory != null) {
             Tedit_dName = territory;
           // pbindingEdit.territorySpinnerE.setSelection(getIndex(pbindingEdit.territorySpinnerE, Tedit_dName));
            ModelTeritorry teritorryType=(ModelTeritorry) pbindingEdit.territorySpinnerE.getSelectedItem();
            if(!String.valueOf(tid).equals(""))
            {
                mpresenter.GetSTeritoryLocal(tid);
            }else {
                mpresenter.GetSTeritoryLocal(teritorryType.getTerritoryId());
            }

        } else {
        }
        if (subTerritory != null) {
            edit_dName = subTerritory;
            pbindingEdit.sterritorySpinnerE.setSelection(getIndex(pbindingEdit.sterritorySpinnerE, edit_dName));
            mpresenter.GetMarketLocal(stid);

        } else {
        }

        if (market != null) {
            edit_dName = market;
            pbindingEdit.marketSpinnerE.setSelection(getIndex(pbindingEdit.marketSpinnerE, edit_dName));
        } else {
        }

        /*pbindingEdit.sterritorySpinnerE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubTeritorry steri = (SubTeritorry) parent.getSelectedItem();
                selectedSTeri = steri.getSubTerritoryId();
                mpresenter.GetMarketLocal(selectedSTeri);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        pbindingEdit.tourDate.setText(ddy);
        LoadTourPurpose(pbindingEdit.tourPlanPurposeSpinnerE);
        pbindingEdit.custAdd.setOnClickListener(v -> {
            // showDialog_Customer();
            popup_Customer();
            popupCustomer.show();

        });
        pbindingEdit.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupTPPEdit.cancel();
            }
        });
        //popup for tp details recyclerview
        pbindingEdit.tpupdateBnt.setOnClickListener(v -> {
            try {
                TourPlanViewModel tv = new TourPlanViewModel();
                tv.setTourPlanId(0);
                tv.setMarketId(selectedMarket);
                tv.setMarketName(selectedMarketName);
                tv.setTPId(selectedTPP);
                tv.setEmpInfoId(empId);
                tv.setTourPlanDate(pbinding.tourDate.getText().toString());
                tv.setTPName(selectedTPPName);
                if (tpl.size() == 0) {
                    tv.setSerialNo(1);
                } else {
                    tv.setSerialNo(tpl.size() + 1);
                }
                tv.setaCustomerMasterList(chkCustomerList);
                tpl.add(tv);
                SetInRecyclerviewData(tpl);
                adapter.notifyDataSetChanged();
                // chkCustomerList.clear();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            popupTPPEdit.dismiss();
        });
        popupTPPEdit.show();

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
    public void vGroup(List<ModelGroup> groupList) {
    }

    @Override
    public void vRegion(List<ModelRegion> regionList) {
        try {
            if (regionList != null) {
                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(TourPlanDetailsActivity.this, android.R.layout.simple_spinner_item, regionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.regionSpinner.setAdapter(dataAdapter);
              //  pbindingEdit.regionSpinnerE.setAdapter(dataAdapter);
            }
            pbinding.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelRegion region = (ModelRegion) parent.getSelectedItem();
                    selectedRegionId = region.getRegionId();
                    mpresenter.GetAreaLocal(selectedRegionId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
     /*       pbindingEdit.regionSpinnerE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Region region = (Region) parent.getSelectedItem();
                    selectedRegionId = region.getRegionId();
                    mpresenter.GetAreaLocal(selectedRegionId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vArea(List<ModelArea> areaList) {
        try {
            if (areaList != null) {
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(TourPlanDetailsActivity.this, android.R.layout.simple_spinner_item, areaList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.areaSpinner.setAdapter(dataAdapter);
                //pbindingEdit.areaSpinnerE.setAdapter(dataAdapter);
            }
            pbinding.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelArea area = (ModelArea) parent.getSelectedItem();
                    selectedAreaId = area.getAreaId();
                    mpresenter.GetTeritoryLocal(selectedAreaId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vTeritory(List<ModelTeritorry> teritoryList) {
        try {
            if (teritoryList != null) {
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(TourPlanDetailsActivity.this, android.R.layout.simple_spinner_item, teritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.territorySpinner.setAdapter(dataAdapter);
               // pbindingEdit.territorySpinnerE.setAdapter(dataAdapter);
            }
            pbinding.territorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelTeritorry teri = (ModelTeritorry) parent.getSelectedItem();
                    selectedTeriId = teri.getTerritoryId();
                    mpresenter.GetSTeritoryLocal(selectedTeriId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            pbindingEdit.territorySpinnerE.setSelection(getIndex(pbindingEdit.territorySpinnerE, Tedit_dName));
            pbindingEdit.territorySpinnerE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelTeritorry steri = (ModelTeritorry) parent.getSelectedItem();
                    selectedTeriId = steri.getTerritoryId();
                    mpresenter.GetSTeritoryLocal(selectedTeriId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vSTeritory(List<ModelSubTeritorry> steritoryList) {
        try {
            if (steritoryList != null) {
                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(TourPlanDetailsActivity.this, android.R.layout.simple_spinner_item, steritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.sterritorySpinner.setAdapter(dataAdapter);
               // pbindingEdit.sterritorySpinnerE.setAdapter(dataAdapter);
            }
            pbinding.sterritorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelSubTeritorry steri = (ModelSubTeritorry) parent.getSelectedItem();
                    selectedSTeri = steri.getSubTerritoryId();
                    mpresenter.GetMarketLocal(selectedSTeri);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vMarket(List<ModelMarket> marketList) {
        try {
            if (marketList != null) {
                ArrayAdapter<ModelMarket> dataAdapter = new ArrayAdapter<>(TourPlanDetailsActivity.this, android.R.layout.simple_spinner_item, marketList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.marketSpinner.setAdapter(dataAdapter);
               // pbindingEdit.marketSpinnerE.setAdapter(dataAdapter);
            }
            pbinding.marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelMarket market = (ModelMarket) parent.getSelectedItem();
                    selectedMarket = market.getMarketId();
                    selectedMarketName = market.getMarketName();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        /*    pbindingEdit.marketSpinnerE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Market market = (Market) parent.getSelectedItem();
                    selectedMarket = market.getMarketId();
                    selectedMarketName = market.getMarketName();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void LoadTourPurpose(Spinner setSpiner) {
        tppList = new ArrayList<>();
        tppList = dbCrudHelper.getTPPList_SQLite();
        try {
            if (tppList != null) {
                ArrayAdapter<TourPurposeViewModel> dataAdapter = new ArrayAdapter<>(TourPlanDetailsActivity.this, android.R.layout.simple_spinner_item, tppList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                setSpiner.setAdapter(dataAdapter);
            }
            setSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TourPurposeViewModel tpp = (TourPurposeViewModel) parent.getSelectedItem();
                    selectedTPP = tpp.getTPId();
                    selectedTPPName = tpp.getTPName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void OnTourPlanDataGet(List<TourPlanViewModel> aList) {
        Gson gson = new Gson();
        String data = gson.toJson(aList);
        System.out.println("tourplan get" + data);
        tpl = aList;
        try {
            //SetInRecyclerviewData(aList);
            SetInRecyclerviewData(tpl);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void OnTourPlanDailyDataGet(List<MonthDate> aList) {
        // SetInRecyclerview(monthDatel);
    }

    @Override
    public void OnArreangList(List<MonthDate> aMondateList, boolean is_Entry, List<TourPlanViewModel> aTpLIst) {

    }


    @Override
    public void OnFailour(String msg) {

    }

    @Override
    public void OnSuccessTPPDay(String msg) {
        if (msg.equals("TourPlanSave")) {
            new AlertDialog.Builder(TourPlanDetailsActivity.this)
                    .setTitle("Success")
                    .setMessage("Tour Plan Added Successfully")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            /*Intent in=new Intent(TourPlanDetailsActivity.this,TourePlanAC.class);
                            startActivity(in);
                            finish();*/
                         /*   Intent in =new Intent(TourPlanDetailsActivity.this,TourePlanAC.class);
                            startActivity(in);
                            finish();*/
                            onBackPressed();
                            //mListener.ReloadCurrentActivity();
                        }

                    }).setCancelable(false).show();
        }

    }
/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(TourPlanDetailsActivity.this, TourPlanActivity.class);
        startActivity(in);
        finish();

        *//*FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        TourePlanListFragment tplFragment = new TourePlanListFragment();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left).replace(R.id.fragmentContaineplant,tplFragment).commit();*//*
        // finish();
       *//* if (savedInstanceState==null){

        }*//*

    }*/

    @Override
    public void ckdItemName(List<ModelCustomer> st, int Pos) {
        if (st != null) {
            chkCustomerList = st;
        } else {
            adapter.notifyItemRangeRemoved(0, st.size());
            //  vpl.clear();
            // rv_doctors.removeItemDecorationAt(pos);

        }
    }

    @Override
    public void unckdItemName(List<ModelCustomer> st, int Pos) {

    }

    //search
    private void search() {
        srchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rv_customer.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
                rv_customer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void filter(String word) {
        List<ModelCustomer> filterwordlist = new ArrayList<>();
        for (ModelCustomer words : aCustomerList) {
            if (words.getCustomerName() == null) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (words.getCustomerName().toLowerCase().contains(word.toLowerCase())) {
                filterwordlist.add(words);
            }
        }
        itemChkAdapter.filterListFun(filterwordlist);
        itemChkAdapter.notifyDataSetChanged();
    }


    private void Edit_TP() {
        mpresenter.GetGroupLocal();
        //mpresenter.GetRegionLocal();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // Toast.makeText(this, "Save Value", Toast.LENGTH_SHORT).show();
    }
}