package com.saidur.creatrix.unicorn.salessolution.UI.Order.OrderTracking;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketStracture;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelOrderTrackigMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiOrderProcess;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterMarketStructure;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.AdapterMioOrderList;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityOrderTrackingBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FilterMasterBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTrackingActivity extends AppCompatActivity implements IMarketStracture.View{
    ActivityOrderTrackingBinding binding;
    ProgressDialog pd;
    ModelSession session;
    BottomSheetDialog bsheetdlg;
    DBCrudHelper dbCrudHelper;
    String role, empId, today;
    IMarketStracture.Presenter mkpresenter;
    FilterMasterBinding ftm;
    int selectedGrpId, selectedZoneId, selectedAreaId, selectedTeriId, selectedSTeriId, selectedMarketId;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderTrackingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper=new DBCrudHelper(OrderTrackingActivity.this);
        session=dbCrudHelper.getSession();

        ftm = FilterMasterBinding.inflate(getLayoutInflater());
        mkpresenter = new PresenterMarketStructure(this, OrderTrackingActivity.this);
        empId = String.valueOf(session.getEmpInfoId());
        role = session.getEmpRole();
        today = UtilityHelper._GetCurrentDate();
        Map<String, String> filter = new HashMap<>();
        filter.put("FromDate", today);
        filter.put("ToDate", today);
        filter.put("AppStatus", "Select");
        filter.put("GroupId", "0");
        filter.put("ZoneId", "0");
        filter.put("AreaId", "0");
        filter.put("TerritoryId", "0");
        filter.put("SubTerritoryId", "0");
        filter.put("MarketId", "0");
        if (empId == null) {
            filter.put("empId", "");
        } else {
            filter.put("empId", empId);
        }
        getOrderList(filter);
        openFilter();
        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());
        binding.filter.setOnClickListener(v -> bsheetdlg.show());
    }

    private void getOrderList(Map<String, String> filter) {
        pd = new ProgressDialog(OrderTrackingActivity.this);
        pd.setMessage("Orders are Loading....");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        try {
            ApiOrderProcess service = RetrofitClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<List<ModelOrderTrackigMaster>> call = service.GetMio_OrderList(filter);
            call.enqueue(new Callback<List<ModelOrderTrackigMaster>>() {
                @Override
                public void onResponse(@NonNull Call<List<ModelOrderTrackigMaster>> call, @NonNull Response<List<ModelOrderTrackigMaster>> response) {
                    bsheetdlg.cancel();
                    if(response.body()!=null)
                    {

                        if(pd!=null||pd.isShowing())
                        {
                            pd.dismiss();
                        }
                        System.out.println("response"+response.body());
                        LoadRecycler(response.body().get(0).getOrder_Lists());
                        LoadV(response.body().get(0).getTotalNetAmount());
                    }else {
                        if(pd!=null||pd.isShowing())
                        {
                            pd.dismiss();
                        }
                        LoadRecycler(null);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<ModelOrderTrackigMaster>> call, @NonNull Throwable t) {
                    if(pd!=null||pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    bsheetdlg.cancel();
                }
            });

        } catch (Exception ex) {
            if(pd!=null||pd.isShowing())
            {
                pd.dismiss();
            }
        }
    }

    private void LoadV(String totalNetAmount) {
        binding.rvAmount.setText(totalNetAmount);
    }
    private void openFilter() {
    /*    FilterMasterBinding ftm;
        ftm = FilterMasterBinding.inflate(getLayoutInflater());*/
        bsheetdlg = new BottomSheetDialog(OrderTrackingActivity.this);
        bsheetdlg.setContentView(ftm.getRoot());
        bsheetdlg.setCanceledOnTouchOutside(true);
        //   mkpresenter.GetGroupLocal();
        ftm.filterStatusLayout.linearLayout.setVisibility(VISIBLE);
        ftm.filterMarketLayout.marketStructure.setVisibility(VISIBLE);

        ftm.linearLayoutemp.setVisibility(GONE);
        ftm.filterTypeLayout.tilDoctype.setVisibility(View.GONE);
        ftm.filterTypeLayout.tilProgtypetype.setVisibility(View.GONE);

        switch (role) {
            case "MBE":
                ftm.filterMarketLayout.divGroup.setVisibility(GONE);
                ftm.filterMarketLayout.divRegion.setVisibility(GONE);
                ftm.filterMarketLayout.divArea.setVisibility(GONE);
                ftm.filterMarketLayout.divTeritorry.setVisibility(GONE);
                mkpresenter.GetSTeritoryLocal(0);
                break;
            case "MIO":
                ftm.filterMarketLayout.divGroup.setVisibility(GONE);
                ftm.filterMarketLayout.divRegion.setVisibility(GONE);
                ftm.filterMarketLayout.divArea.setVisibility(GONE);
                mkpresenter.GetTeritoryLocal(0);
                break;
            case "AM":
                ftm.filterMarketLayout.divGroup.setVisibility(GONE);
                ftm.filterMarketLayout.divRegion.setVisibility(GONE);
                mkpresenter.GetAreaLocal(0);
                break;
            case "DZSM":
                ftm.filterMarketLayout.divGroup.setVisibility(GONE);
                mkpresenter.GetRegionLocal(0);
                break;
            case "NSM":
            case "Admin":
                ftm.filterMarketLayout.divGroup.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divRegion.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divArea.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divTeritorry.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divSteritory.setVisibility(VISIBLE);
                ftm.filterMarketLayout.divMarket.setVisibility(VISIBLE);
                mkpresenter.GetGroupLocal();
                break;
        }
        ftm.filterMonthYearLayout.txtFromDate.setText(today);
        ftm.filterMonthYearLayout.txtToDate.setText(today);
        ftm.filterMonthYearLayout.ivDatePickerFromDate.setOnClickListener(v -> UtilityHelper._datePickerDialogeForDates(ftm.filterMonthYearLayout.txtFromDate, OrderTrackingActivity.this));

        ftm.filterMonthYearLayout.ivDatePickerToDate.setOnClickListener(v -> {
            if (ftm.filterMonthYearLayout.txtFromDate.getText().toString().isEmpty()) {
                SnackBarManagement._warning_CustomMessage(ftm.filterMaster, "From Date is Require");
            } else {
                UtilityHelper._datePickerDialogeForDates(ftm.filterMonthYearLayout.txtToDate, OrderTrackingActivity.this);
            }
        });
        ftm.filterTopLayout.btnDonefilter.setOnClickListener(v -> {
            if (ftm.filterMonthYearLayout.txtToDate.getText().toString().equals("")) {
                SnackBarManagement._warning_CustomMessage(binding.mainmaster, "To Date is Require");
            }
            Map<String, String> filters = new HashMap<>();
            filters.put("FromDate", ftm.filterMonthYearLayout.txtFromDate.getText().toString());
            filters.put("ToDate", ftm.filterMonthYearLayout.txtToDate.getText().toString());
            filters.put("AppStatus", "Select");
            filters.put("GroupId", String.valueOf(selectedGrpId));
            filters.put("ZoneId", String.valueOf(selectedZoneId));
            filters.put("AreaId", String.valueOf(selectedAreaId));
            filters.put("TerritoryId", String.valueOf(selectedTeriId));
            filters.put("SubTerritoryId", String.valueOf(selectedSTeriId));
            filters.put("MarketId", String.valueOf(selectedMarketId));
            if (empId == null) {
                filters.put("empId", "");
            } else {
                filters.put("empId", empId);
            }
            System.out.println("filter :" + filters);
            getOrderList(filters);

        });

        ftm.filterTopLayout.ivCancel.setOnClickListener(v -> bsheetdlg.cancel());

    }
    private void LoadRecycler(List<OrderViewModel> order_lists) {
        if (order_lists != null ) {
            binding.count.setText(String.valueOf(order_lists.size()));
            AdapterMioOrderList mAdapter = new AdapterMioOrderList(order_lists);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(mAdapter);
            binding.recyclerView.setItemAnimator(null);
            binding.recyclerView.scrollToPosition(0);
            mAdapter.notifyDataSetChanged();
        }else {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(),"No Order Found!!");
        }
    }

    @Override
    public void vGroup(List<ModelGroup> groupList) {
        try {
            if (groupList != null) {
                ModelGroup g = new ModelGroup();
                g.setGroupName("Select");
                groupList.add(0, g);

                ArrayAdapter<ModelGroup> dataAdapter = new ArrayAdapter<>(OrderTrackingActivity.this, android.R.layout.simple_spinner_item, groupList);
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

                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(OrderTrackingActivity.this, android.R.layout.simple_spinner_item, regionList);
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
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(OrderTrackingActivity.this, android.R.layout.simple_spinner_item, areaList);
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

                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(OrderTrackingActivity.this, android.R.layout.simple_spinner_item, teritoryList);
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

                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(OrderTrackingActivity.this, android.R.layout.simple_spinner_item, steritoryList);
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

                ArrayAdapter<ModelMarket> dataAdapter = new ArrayAdapter<>(OrderTrackingActivity.this, android.R.layout.simple_spinner_item, marketList);
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