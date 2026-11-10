package com.saidur.creatrix.unicorn.salessolution.UI.Customer;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBSyncHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ICustomer;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketStracture;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterCustomerList;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterMarketStructure;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter.CustomerAdapter;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityCustomerListForOrderBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopCcallBinding;

import java.util.ArrayList;
import java.util.List;


public class CustomerListForOrderActivity extends AppCompatActivity implements ICustomer.View, IMarketStracture.View {
    ActivityCustomerListForOrderBinding binding;

    PresenterCustomerList presenter;
    ProgressDialog pd;
    DBCrudHelper dbCrudHelper;
    DBSyncHelper dbSyncHelper;
    public List<ModelCustomer> customerArrayList = new ArrayList<>();
    CustomerAdapter cAdapter;
    BottomSheetDialog bsheetdlg;
    FilterMasterBinding ftm;
    String role;

    IMarketStracture.Presenter mkpresenter;
    int selectedGrpId, selectedZoneId, selectedAreaId, selectedTeriId, selectedSTeriId, selectedMarketId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerListForOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(view -> finish());
        ftm = FilterMasterBinding.inflate(getLayoutInflater());



        presenter = new PresenterCustomerList(this, this);
        dbCrudHelper = new DBCrudHelper(this);
        dbSyncHelper = new DBSyncHelper(this);

        mkpresenter = new PresenterMarketStructure(this, CustomerListForOrderActivity.this);
        ModelSession session = dbCrudHelper.getSession();
        role = session.getRoleType();
        mkpresenter.GetGroupLocal();
        mkpresenter.GetRegionLocal(0);
        mkpresenter.GetAreaLocal(0);
        mkpresenter.GetTeritoryLocal(0);
        mkpresenter.GetSTeritoryLocal(0);
        mkpresenter.GetMarketLocal(0);
        openFilter();
        binding.cfilter.setOnClickListener(v -> {
            bsheetdlg.show();
        });

        try {
            if (dbCrudHelper.CheckDataInTable("tblCustomerInfo") == true) {
                customerArrayList = dbCrudHelper.getCustomerList_SQLite();
                OnSuccessz(customerArrayList);
            } else {

                if (NetworkInformation.isConnected(CustomerListForOrderActivity.this)) {
                    pd = new ProgressDialog(CustomerListForOrderActivity.this);
                    pd.setMessage("Customer is Loading....");
                    pd.show();
                    pd.setCanceledOnTouchOutside(false);
                    presenter.GetCustomerByUser(session.getEmpInfoId());
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
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


    }





    private void openFilter() {
        bsheetdlg = new BottomSheetDialog(CustomerListForOrderActivity.this);
        bsheetdlg.setContentView(ftm.getRoot());
        bsheetdlg.setCanceledOnTouchOutside(true);
        ftm.filterMarketLayout.marketStructure.setVisibility(VISIBLE);
        ftm.filterStatusLayout.getRoot().setVisibility(GONE);
        ftm.filterMonthYearLayout.getRoot().setVisibility(GONE);
        ftm.linearLayoutemp.setVisibility(GONE);
        ftm.filterTypeLayout.tilDoctype.setVisibility(View.GONE);
        ftm.filterTypeLayout.tilProgtypetype.setVisibility(View.GONE);
        switch (role) {
            case "MBE":
                ftm.filterMarketLayout.divGroup.setVisibility(GONE);
                ftm.filterMarketLayout.divRegion.setVisibility(GONE);
                ftm.filterMarketLayout.divArea.setVisibility(GONE);
                ftm.filterMarketLayout.divTeritorry.setVisibility(GONE);
                //mkpresenter.GetSTeritoryLocal(0);

                break;
            case "MIO":
                ftm.filterMarketLayout.divGroup.setVisibility(GONE);
                ftm.filterMarketLayout.divRegion.setVisibility(GONE);
                ftm.filterMarketLayout.divArea.setVisibility(GONE);
                // mkpresenter.GetTeritoryLocal(0);

                break;
            case "AM":
                ftm.filterMarketLayout.divGroup.setVisibility(GONE);
                ftm.filterMarketLayout.divRegion.setVisibility(GONE);
                //mkpresenter.GetAreaLocal(0);
                break;
            case "DZSM":
                ftm.filterMarketLayout.divGroup.setVisibility(GONE);
                //mkpresenter.GetRegionLocal(0);
                break;
            case "NSM":
            case "Admin":
                ftm.filterMarketLayout.divGroup.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divRegion.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divArea.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divTeritorry.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divSteritory.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divMarket.setVisibility(VISIBLE);
                // mkpresenter.GetGroupLocal();
                break;
        }
        ftm.filterMonthYearLayout.ivDatePickerFromDate.setVisibility(GONE);
        ftm.filterMonthYearLayout.ivDatePickerToDate.setVisibility(GONE);
        ftm.filterTopLayout.btnDonefilter.setOnClickListener(v -> {
            try {
                if (dbCrudHelper.CheckDataInTable("tblCustomerInfo") == true) {
                    customerArrayList = dbCrudHelper.getCustomerFilter_SQLite(String.valueOf(selectedGrpId), String.valueOf(selectedZoneId), String.valueOf(selectedAreaId)
                            , String.valueOf(selectedTeriId), String.valueOf(selectedSTeriId), String.valueOf(selectedMarketId));
                    OnSuccessz(customerArrayList);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            bsheetdlg.cancel();
        });
        ftm.filterTopLayout.ivCancel.setOnClickListener(v -> {
            bsheetdlg.cancel();
        });
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

                cAdapter.filterList(arrayList);

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private void OnSuccessz(List<ModelCustomer> customerArrayList) {
        if (customerArrayList != null) {
            binding.count.setText(String.valueOf(customerArrayList.size()));
            cAdapter = new CustomerAdapter(this, customerArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(cAdapter);
            binding.recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            binding.recyclerView.setItemAnimator(null);
            binding.recyclerView.scrollToPosition(0);
            cAdapter.notifyDataSetChanged();
        } else {
            binding.count.setText("0");
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
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        if (type == 1) {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "Slow connection detected. Make sure you have a working internet connection");
        }
        if (type == 2) {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "Error getting response from server.Make sure you have a working Internet connection");
        }
        if (type == 3) {
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "System get an exception ! Please contact with developer");
        }
    }

    @Override
    public void OnErrorReport(String msg) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    @Override
    public void vGroup(List<ModelGroup> groupList) {
        try {
            if (groupList != null) {
                ModelGroup g = new ModelGroup();
                g.setGroupName("Select");
                groupList.add(0, g);

                ArrayAdapter<ModelGroup> dataAdapter = new ArrayAdapter<>(CustomerListForOrderActivity.this, android.R.layout.simple_spinner_item, groupList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.filterMarketLayout.groupSpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ftm.filterMarketLayout.groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelGroup grp = (ModelGroup) parent.getSelectedItem();
                selectedGrpId = grp.getGroupId();
                mkpresenter.GetRegionLocal(selectedGrpId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void vRegion(List<ModelRegion> regionList) {
        try {
            if (regionList != null) {
                ModelRegion r = new ModelRegion();
                r.setRegionName("Select");
                regionList.add(0, r);

                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(CustomerListForOrderActivity.this, android.R.layout.simple_spinner_item, regionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.filterMarketLayout.regionSpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ftm.filterMarketLayout.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelRegion rgn = (ModelRegion) parent.getSelectedItem();
                selectedZoneId = rgn.getRegionId();
                mkpresenter.GetAreaLocal(selectedZoneId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void vArea(List<ModelArea> areaList) {
        try {
            if (areaList != null) {
                ModelArea a = new ModelArea();
                a.setAreaName("Select");
                areaList.add(0, a);
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(CustomerListForOrderActivity.this, android.R.layout.simple_spinner_item, areaList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.filterMarketLayout.areaSpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ftm.filterMarketLayout.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelArea area = (ModelArea) parent.getSelectedItem();
                selectedAreaId = area.getAreaId();
                mkpresenter.GetTeritoryLocal(selectedAreaId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void vTeritory(List<ModelTeritorry> teritoryList) {
        try {
            if (teritoryList != null) {
                ModelTeritorry t = new ModelTeritorry();
                t.setTerritoryName("Select");
                teritoryList.add(0, t);

                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(CustomerListForOrderActivity.this, android.R.layout.simple_spinner_item, teritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.filterMarketLayout.territorySpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ftm.filterMarketLayout.territorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelTeritorry teritorry = (ModelTeritorry) parent.getSelectedItem();
                selectedTeriId = teritorry.getTerritoryId();
                mkpresenter.GetSTeritoryLocal(selectedTeriId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void vSTeritory(List<ModelSubTeritorry> steritoryList) {
        try {
            if (steritoryList != null) {
                ModelSubTeritorry st = new ModelSubTeritorry();
                st.setSubTerritoryName("Select");
                steritoryList.add(0, st);

                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(CustomerListForOrderActivity.this, android.R.layout.simple_spinner_item, steritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.filterMarketLayout.sterritorySpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ftm.filterMarketLayout.sterritorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelSubTeritorry steritorry = (ModelSubTeritorry) parent.getSelectedItem();
                selectedSTeriId = steritorry.getSubTerritoryId();
                mkpresenter.GetMarketLocal(selectedSTeriId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void vMarket(List<ModelMarket> marketList) {
        try {
            if (marketList != null) {
                ModelMarket m = new ModelMarket();
                m.setMarketName("Select");
                marketList.add(0, m);

                ArrayAdapter<ModelMarket> dataAdapter = new ArrayAdapter<>(CustomerListForOrderActivity.this, android.R.layout.simple_spinner_item, marketList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ftm.filterMarketLayout.marketSpinner.setAdapter(dataAdapter);
                ftm.filterMarketLayout.marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelMarket market = (ModelMarket) parent.getSelectedItem();
                        selectedMarketId = market.getMarketId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}