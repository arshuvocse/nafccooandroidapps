package com.saidur.creatrix.unicorn.salessolution.UI.Order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBProductSQLiteHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IOrderManagement;
import com.saidur.creatrix.unicorn.salessolution.Interface.IProduct;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampOrderDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignCalModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignGetReq;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignMaster2;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignMasterNew;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignMasters;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignPostReq;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CheckedCampaignListener;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CordinateUpdate;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.DepoStockModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelCompanyUnit;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMasterModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Rp_StockViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiOrderProcess;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiReport;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiSeedData;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitOrderClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterOrderManagement;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterProduct;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.AdapterCampaignPop;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.AdapterDepoStock;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.Adapter_ordersummary;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.Adapter_product_orderpage;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.RecyclerViewActionListener;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Campaign.CampaignActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.LocationGet;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SessionManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityPlaceOrderBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopStockBinding;

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends AppCompatActivity implements LocationListener, CheckedCampaignListener, IProduct.View, RecyclerViewActionListener, IOrderManagement.View{
    ActivityPlaceOrderBinding binding;
    PopStockBinding psb;
    IProduct.Presenter presenter;
    private Adapter_product_orderpage mAdapter;
    IOrderManagement.Presenter orderPresenter;
    // SessionManagement session;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    View aMasterlayout;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    String orderTYpe;
    DecimalFormat f = new DecimalFormat("##.00"); // this is for 2 decimal place
    private List<ModelProduct> productListOrder = new ArrayList<>();
    private List<ModelProduct> productListCampSummary = new ArrayList<>();
    private List<ModelProduct> productListCampShowList;
    private List<ModelCompanyUnit> comUnitList = new ArrayList<ModelCompanyUnit>();
    DBProductSQLiteHelper pHelper;

    Context defaultContext = PlaceOrderActivity.this;
    ArrayList<ModelProduct> productArrayList;

    List<CampaignModel> cmpaignList;
    List<CampaignGetReq> cmpaignReq = new ArrayList<>();
    CampaignGetReq ca;

    CampaignPostReq cmpaignPReq = new CampaignPostReq();

    List<CampaignMasters> cmpList = new ArrayList<>();
    List<CampaignMaster2> cmp2List = new ArrayList<>();

    CampaignMasters cm;
    CampaignMaster2 cm2; /*= new CampaignMaster2();*/
    AdapterCampaignPop adapter;

    List<CampaignCalModel> cmpaignCalList;
    List<CampOrderDetails> codList;
    Adapter_ordersummary osAdapter;
    CampaignModel campSelected;
    boolean isAdd = false;
    boolean isSetCampaignAdd = false;

    //Depo Stock
    String CustomerMasterId;
    int campId, CustomerId, bonousId;
    int custId = 0;
    String name = "";
    int pid, c2mastid;
    boolean isSub = false;
    int orderLocalId;
    int cust_reqid, cust_reqpid, cust_reqqty;
    double cust_requp;
    int oid;
    ModelCustomer aInfoData;
    String streetAddress = "";
    Double lat, lon;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pHelper = new DBProductSQLiteHelper(PlaceOrderActivity.this);
        presenter = new PresenterProduct(this, this);
        orderPresenter = new PresenterOrderManagement(this, this);
        dbCrudHelper = new DBCrudHelper(this);
        if (ContextCompat.checkSelfPermission(PlaceOrderActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(PlaceOrderActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PlaceOrderActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        locationManager = (LocationManager) PlaceOrderActivity.this.getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getCLocation();
        if (!NetworkInformation.isConnected(this)) {
            checkLocationPermission();
        } else {
            //  SnackBarManagement._warning_CustomMessage(binding.masterLayoutId,"Check Your Internet!!");
        }
        session = dbCrudHelper.getSession();
        String userName = session.getUserName();
        String empId = String.valueOf(session.getEmpInfoId());
        //  String extra = user.get(SessionManagement.Extra);
        Gson gson = new Gson();
        //TODO:Need Customer information for edit approval
        aInfoData = gson.fromJson(getIntent().getStringExtra("myjson"), ModelCustomer.class);
        setCustomerText(aInfoData);
        CustomerMasterId = String.valueOf(aInfoData.getCustomerMasterId());
        CustomerId = aInfoData.getCustomerMasterId();
        //Check where it comes from
        switch (Constants.WHO) {
            case "OrderMainAdapter":
                productListOrder.clear();
                orderTYpe = getIntent().getStringExtra("OrderType");
                binding.btnDraft.setVisibility(View.VISIBLE);
                Constants.From = "MainOrder";
                String today = UtilityHelper._GetCurrentDate();
                binding.txtDeliveryDate.setText(today);
                binding.btnDraft.setOnClickListener(v -> OrderDraft(aInfoData, userName, empId));
                binding.btnSubmit.setOnClickListener(v -> OrderSubmit(aInfoData, userName, empId, "SubmitOrder", productListOrder));
                break;
            case "DraftOrderAdapter":
                Constants.From = "DraftOrder";
                OrderMaster orderProduct = gson.fromJson(getIntent().getStringExtra("orderDetails"), OrderMaster.class);
                orderLocalId = orderProduct.getOrderIdLocal();
                binding.txtDeliveryDate.setText(orderProduct.getDeliveryDate());
                binding.remarksTxt.setText(orderProduct.getRemarks());
                productListOrder.clear();
                int i;
                for (i = 0; i < orderProduct.getOrderDetails().size(); i++) {
                    ModelProduct pr = new ModelProduct();
                    pr = orderProduct.getOrderDetails().get(i);
                    int pids = pr.getProductId();
                    String pnames = pr.getProductName();
                    Double up = pr.getUnitPrice();
                    Double vp = pr.getVatPercentage();
                    int qty = pr.getQuantity();
                    String pcode = pr.getProductCode();
                    ModelProduct aProduct = new ModelProduct(pids, pnames, up, vp, qty, pcode);
                    cust_reqid = CustomerId;
                    cust_reqpid = pids;
                    cust_reqqty = qty;
                    cust_requp = up;
                    campaignReqSetup(cust_reqid, cust_reqpid, cust_reqqty, cust_requp);
                    productListOrder.add(updateProductTotals(aProduct));
                    setTotals();
                }
                // binding.btnDraft.setVisibility(View.GONE);
                binding.btnDraft.setOnClickListener(v -> OrderDraft(aInfoData, userName, empId));
                binding.btnSubmit.setOnClickListener(v -> OrderSubmit(aInfoData, userName, empId, "DraftOrderAdapter", productListOrder));
                break;
        }
        if (!NetworkInformation.isConnected(PlaceOrderActivity.this)) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "You are not connected to internet\nYou Can't Submit");
            binding.btnSubmit.setVisibility(View.GONE);
            binding.btnSelectCampaign.setVisibility(View.GONE);
            presenter.getProductFromDB(Integer.parseInt(empId));
            //  return;
        }
        presenter.getProductFromDB(Integer.parseInt(empId));
        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());
        binding.campaignclick.setOnClickListener(v -> {
            Intent in = new Intent(PlaceOrderActivity.this, CampaignActivity.class);
            startActivity(in);
        });
        aMasterlayout = findViewById(R.id.masterLayoutId);
        // product Adapter for add order
        if (productListOrder != null) {
            mAdapter = new Adapter_product_orderpage(productListOrder, this, this, "MainOrder");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.recyclerViewProduct.setLayoutManager(mLayoutManager);
            binding.recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerViewProduct.setAdapter(mAdapter);
            binding.recyclerViewProduct.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            binding.recyclerViewProduct.setItemAnimator(null);
            binding.recyclerViewProduct.scrollToPosition(0);
            mAdapter.notifyDataSetChanged();
        }
        binding.comSpinner.setTitle("Select Company Unit");
        binding.comSpinner.setPositiveButton("OK");

        binding.comSpinner.setAdapter(new ArrayAdapter<>(PlaceOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, comUnitList));
        binding.comSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    String str = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.datePickerDeliveryDate.setOnClickListener(v -> UtilityHelper._datePickerDialogeForDates_DisableOldDates(binding.txtDeliveryDate, PlaceOrderActivity.this));
        binding.datePickerCollectionDate.setOnClickListener(v -> UtilityHelper._datePickerDialogeForDates_DisableOldDates(binding.txtCollectionDate, PlaceOrderActivity.this));
        binding.searchProducttxt.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            // ca = new CampaignGetReq();
            ModelProduct selected = (ModelProduct) arg0.getAdapter().getItem(arg2);
            double id = selected.getUnitPrice();
            String Test = Double.toString(id);
            LayoutInflater li = LayoutInflater.from(PlaceOrderActivity.this);
            View promptsView = li.inflate(R.layout.quantity_modal, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlaceOrderActivity.this);
            alertDialogBuilder.setView(promptsView);
            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);
            userInput.requestFocus();
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Done",
                            (dialog, id1) -> {
                                ModelProduct sp = (ModelProduct) arg0.getAdapter().getItem(arg2);
                                String pQyu = userInput.getText().toString();
                                int customerId = aInfoData.getCustomerMasterId();
                                if (pQyu.equals("")) {
                                    Toast.makeText(PlaceOrderActivity.this, "Please enter a valid amount", Toast.LENGTH_LONG).show();
                                } else {
                                    int quantity = Integer.parseInt(userInput.getText().toString());
                                    if (quantity == 0) {
                                        Toast.makeText(PlaceOrderActivity.this, "Please enter a valid amount", Toast.LENGTH_LONG).show();
                                    } else {
                                        // this is for with customer demo
                                        Double acPrice = 0.00;
                                        Double acVat = 0.00;

                                        double[] quotdPrice = dbCrudHelper.GetQuotedPrice(customerId, sp.getProductId());
                                        if (quotdPrice[0] == 0.00) {
                                            acPrice = sp.getUnitPrice();
                                            acVat = sp.getVatPercentage();
                                        } else {
                                            acPrice = quotdPrice[0];
                                            acVat = sp.getVatPercentage();
                                        }
                                        boolean is_Exist = false;
                                        int proid = sp.getProductId();
                                        for (int i = 0; i < productListOrder.size(); i++) {
                                            int ProductId = productListOrder.get(i).getProductId();
                                            if (proid == ProductId) {
                                                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Already added this product");
                                                is_Exist = true;
                                                break;
                                            }
                                        }
                                        if (is_Exist == false) {
                                            ModelProduct aProduct = new ModelProduct(sp.getProductId(), sp.getProductName(), acPrice, acVat, quantity, sp.getProductCode());
                                            productListOrder.add(updateProductTotals(aProduct));
                                        }
                                        setTotals();
                                        productListCampShowList = new ArrayList<>();
                                        productListCampShowList = productListOrder;
                                        if (cmpaignCalList != null) {
                                            try {
                                                cmpaignCalList.clear();
                                                osAdapter.notifyDataSetChanged();
                                                binding.totalTPValueSummary.setText("0");
                                                binding.totalVatValueSummary.setText("0");
                                                binding.totalDiscountValueSummary.setText("0");
                                                binding.totalValueSummary.setText("0");
                                            } catch (Exception exception) {
                                                exception.printStackTrace();
                                            }
                                        }
                                        mAdapter.notifyItemChanged(productListOrder.size());
                                        mAdapter.notifyDataSetChanged();
                                        binding.searchProducttxt.setText("");
                                    }
                                }

                            })
                    .setNegativeButton("Cancel",
                            (dialog, id12) -> {
                                binding.searchProducttxt.setText("");
                                dialog.cancel();
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            alertDialog.show();
        });

        //TODO:Select Campaign Button Click
        binding.btnSelectCampaign.setOnClickListener(v -> {
            if (!NetworkInformation.isConnected(PlaceOrderActivity.this)) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "You are not connected to Internet!");
                return;
            }
            fun_SelectCampaignButtonClick();
        });
        binding.updateCustomerLocation.setOnClickListener(view -> {
            int customerId = aInfoData.getCustomerMasterId();
            UpdateCustomerLocation(customerId, Integer.parseInt(empId));
        });
        binding.popStock.setOnClickListener(v -> {
            psb = PopStockBinding.inflate(getLayoutInflater());
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            ProgressDialog progressDoalog = new ProgressDialog(PlaceOrderActivity.this);
            progressDoalog.setMessage("loading.... Please wait");
            progressDoalog.show();
            progressDoalog.setCanceledOnTouchOutside(false);
            try {
               /* ApiSeedData service = RetrofitClientInstance.getRetrofitInstance().create(ApiSeedData.class);
                Call<List<DepoStockModel>> call = service.GetDepoStock(CustomerMasterId);*/
                ApiReport service = RetrofitClientInstance.getRetrofitInstance().create(ApiReport.class);
                Call<List<Rp_StockViewModel>> call = service.GetStockData(Integer.parseInt(empId), "Main");
                call.enqueue(new Callback<List<Rp_StockViewModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Rp_StockViewModel>> call, @NonNull Response<List<Rp_StockViewModel>> response) {
                        progressDoalog.dismiss();
                        if (response != null) {
                            LoadDepoStock(response.body(), psb.rvStock);
                        } else {
                            SnackBarManagement._warning_CustomMessage(psb.rlmaster, "No Product Found");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Rp_StockViewModel>> call, @NonNull Throwable t) {
                        progressDoalog.dismiss();
                        if (t instanceof SocketTimeoutException) {
                            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Some thing went wrong. Please try again");
                        } else {
                            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Some thing went wrong. Please try again");

                        }
                    }
                });

            } catch (Exception ex) {
                progressDoalog.dismiss();
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Some thing went wrong. Please try again");
                ex.printStackTrace();
            }
            dialogBuilder.setView(psb.getRoot());
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
            //  psb.rvStock.setAdapter();
            psb.closeTxt.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });
        });
    }

    private void fun_SelectCampaignButtonClick() {
        if (cmpaignCalList != null) {
            try {
                cmpaignCalList.clear();
                osAdapter.notifyItemRangeRemoved(0, cmpaignCalList.size());
                osAdapter.notifyDataSetChanged();
                binding.totalTPValueSummary.setText("0");
                binding.totalVatValueSummary.setText("0");
                binding.totalDiscountValueSummary.setText("0");
                binding.totalValueSummary.setText("0");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        int customerId = aInfoData.getCustomerMasterId();
        if (cmpList != null) {
            try {
                cmpList.clear();
                adapter.notifyItemRangeRemoved(0, cmpList.size());
                adapter.notifyDataSetChanged();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        cmpaignReq.clear();
        for (int i = 0; i < productListOrder.size(); i++) {

            CampaignGetReq ca = new CampaignGetReq();
            ca.setCustomerId(customerId);
            ca.setProductId(productListOrder.get(i).getProductId());
            ca.setQty(productListOrder.get(i).getQuantity());
            ca.setUnitPrice(productListOrder.get(i).getUnitPrice());
            ca.setTotalPrice(0);
            ca.setApplied(false);
            cmpaignReq.add(ca);
            //  getCampaign_PopupList.add(ca);m
        }
        Gson gson=new Gson();
        String data=gson.toJson(cmpaignReq);
        SelectCampaign(customerId, cmpaignReq);
    }

    private void campaignReqSetup(int cust_reqid, int cust_reqpid, int cust_reqqty, double cust_requp) {
        ca = new CampaignGetReq();
        ca.setCustomerId(cust_reqid);
        ca.setProductId(cust_reqpid);
        ca.setQty(cust_reqqty);
        ca.setUnitPrice(cust_requp);
        ca.setTotalPrice(0);
        ca.setApplied(false);
        cmpaignReq.add(ca);
    }

    public CampaignGetReq updateCampaignRQ(CampaignGetReq ca) {
        ca = new CampaignGetReq();
        ca.setCustomerId(cust_reqid);
        ca.setProductId(cust_reqpid);
        ca.setQty(cust_reqqty);
        ca.setUnitPrice(cust_requp);
        ca.setTotalPrice(0);
        ca.setApplied(false);
        cmpaignReq.add(ca);
        return ca;
    }

    // Operation Process
    //submit order
    //submit order
    public void OrderSubmit(ModelCustomer aInfoData, String userName, String empId, String who, List<ModelProduct> productListOrder) {
        int comUnitId = 2;
        Date c = Calendar.getInstance().getTime();
        String formattedDate = UtilityHelper._GetCurrentDate();
        String deliveryDate = formattedDate;
        String collectioDate = formattedDate;

        OrderMasterModel orderMain = new OrderMasterModel();
        orderMain.setOrderId(oid);
        orderMain.setEmpId(Integer.parseInt(empId));
        orderMain.setComUnitId(String.valueOf(comUnitId));
        orderMain.setOrderType(orderTYpe);
        String remarks = binding.remarksTxt.getText().toString();
        orderMain.setRemarks(remarks);
        orderMain.setTpPercentage(0.0);
        orderMain.setCustomerCode(aInfoData.getCustomerCode());
        orderMain.setSubmittedDate(formattedDate);
        orderMain.setCollectionDate(collectioDate);
        orderMain.setDeliveryDate(binding.txtDeliveryDate.getText().toString().trim());
        if (cmpList.size() > 0) {
            int i, c2mastid = 0;
            for (i = 0; i < cmpList.size(); i++) {
                CampaignMaster2 cam2 = new CampaignMaster2();
                cam2.setCampgainMasterId(cmpList.get(i).getCampgainMasterId());
                cmp2List.add(cam2);
            }
            orderMain.setCampaignMasters(cmp2List);
        } else {
            CampaignMaster2 cam2 = new CampaignMaster2();
            cam2.setCampgainMasterId(0);
            cmp2List = new ArrayList<>();
            cmp2List.add(cam2);
            orderMain.setCampaignMasters(cmp2List);
        }
        if (productListOrder != null) {
            List<ModelProduct> plist = null;
            try {
                plist = new ArrayList<>();
                int p;
                for (p = 0; p < productListOrder.size(); p++) {
                    ModelProduct pr = new ModelProduct();
                    pr.setProductId(productListOrder.get(p).getProductId());
                    pr.setProductName(productListOrder.get(p).getProductName());
                    pr.setQuantity(productListOrder.get(p).getQuantity());
                    pr.setUnitPrice(productListOrder.get(p).getUnitPrice());
                    pr.setTp(productListOrder.get(p).getTp());
                    pr.setVatAmountPerunit(productListOrder.get(p).getVatPercentage());
                    pr.setVatPercentage(productListOrder.get(p).getVatPercentage());

                    pr.setTotalVatAmount(productListOrder.get(p).getTotalVatAmount());
                    pr.setNetAmount(productListOrder.get(p).getNetAmount());
                    pr.setCampaignProduct(false);
                    pr.setDiscountPercentage(0.0);
                    pr.setDiscountValue(0.0);
                    pr.setCampaignMasterId(c2mastid);
                    pr.setCustomerId(aInfoData.getCustomerMasterId());
                    pr.setCustomerTypeId(productListOrder.get(p).getCustomerTypeId());
                    plist.add(pr);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            System.out.println(plist);
            orderMain.setOrderDetails(plist);

        }
        if (productListOrder.size() == 0) {
            SnackBarManagement._warning_CustomMessage(aMasterlayout, "Please add at lest one product to submit Order");
        } else {
            //TODO:Checking campaign set or not
            if (productOrder_IsZeroQuantity(productListOrder)) {
                SnackBarManagement._error_CustomMessage(aMasterlayout, "Product Quantity 0 is not Acceptable");
            } else {
                if (NetworkInformation.isConnected(defaultContext)) {
                    Gson gson = new Gson();
                    String model = gson.toJson(orderMain);
                    System.out.println("Make Order " + model);
                   orderPresenter.makeOrder2(orderMain, who);
                } else {
                    /// showing user to draft the order
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PlaceOrderActivity.this);
                    builder1.setMessage("You Don't Internet Connection Now. Do you want to draft the order ?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Yes",
                            (dialog, id) -> {
                                dialog.cancel();
                            });
                    builder1.setNegativeButton(
                            "No",
                            (dialog, id) -> dialog.cancel());

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }


            }
        }
    }

    public void OrderSubmits(ModelCustomer aInfoData, String userName, String empId, String extra) {
    /*    int comUnitId = 2;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        String deliveryDate = formattedDate;
        String collectioDate = formattedDate;

        //selected campaign id
        CampaignMasters cm = new CampaignMasters();
        cm.setCampgainMasterId(campId);
        cmpList.add(cm);

        OrderMasterNew orderMaster = new OrderMasterNew();
        orderMaster.setCustomerCode(aInfoData.getCustomerCode());
        orderMaster.setSubmittedDate(formattedDate);
        orderMaster.setCollectionDate(collectioDate);
        if (productListCampSummary != null) {
            if (productListCampSummary.size() > 0) {
                orderMaster.setOrderDetails(productListCampSummary);
            } else {
                orderMaster.setOrderDetails(productListOrder);
            }
        } else {
            orderMaster.setOrderDetails(productListOrder);
        }

        if (cmpList != null) {
            if (cmpList.size() > 0) {
                orderMaster.setCampaignMasters(cmpList);

            } else {
                orderMaster.setCampaignMasters(cmpList);
            }
        } else {
            orderMaster.setOrderDetails(productListOrder);
        }
        orderMaster.setEmpId(Integer.parseInt(empId));
        orderMaster.setComunitId(comUnitId);
        orderMaster.setOrderType(orderTYpe);
        String remarks = binding.remarksTxt.getText().toString();
        orderMaster.setRemarks(remarks);
        orderMaster.setTpPercentage(discountPercentageTP);
        if (productListOrder.size() == 0) {
            SnackBarManagement._warning_CustomMessage(aMasterlayout, "Please add atlest one product to submit Order");
        } else {
            //TODO:Checking campaign set or not
            if (productOrder_IsZeroQuantity(productListOrder)) {
                SnackBarManagement._error_CustomMessage(aMasterlayout, "Product Quantity 0 is not Acceptable");
            } else {
                if (NetworkInformation.isConnected(defaultContext)) {
                   binding.btnSubmit.setVisibility(View.GONE);
                    orderPresenter.makeOrder2(orderMaster);
                } else {
                    /// showing user to draft the order
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PlaceOrderActivity.this);
                    builder1.setMessage("You Don't Internet Connection Now. Do you want to draft the order ?");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Yes",
                            (dialog, id) -> {
                                dialog.cancel();
                                orderPresenter.draftOrder2(orderMaster);
                            });
                    builder1.setNegativeButton(
                            "No",
                            (dialog, id) -> dialog.cancel());

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
               *//* if(isSetCampaignAdd)
                {

                }
                else {

                    SnackBarManagement._warning_CustomMessage(binding.masterLayoutId,"Please Set a Campaign");
                }*//*

            }


        }*/
    }

    public void OrderDraft(ModelCustomer aInfoData, String userName, String empId) {
        Date c = Calendar.getInstance().getTime();
        String formattedDate = UtilityHelper._GetCurrentDate();
        int comUnitId = 2;
        OrderMaster orderMaster = new OrderMaster();
        ModelCustomer cust = new ModelCustomer(aInfoData.getCustomerMasterId(), aInfoData.getCustomerCode(), aInfoData.getCustomerName(), aInfoData.Address);
        orderMaster.setCustomer(cust);
        orderMaster.setMioCode(userName);
        orderMaster.setSubmittedDate(formattedDate);
        orderMaster.setCollectionDate(formattedDate);
        orderMaster.setDeliveryDate(binding.txtDeliveryDate.getText().toString().trim());
        orderMaster.setOrderDetails(productListOrder);
        orderMaster.setEmpId(Integer.parseInt(empId));
        orderMaster.setComunitId(comUnitId);
        String remarks = binding.remarksTxt.getText().toString();
        orderMaster.setRemarks(remarks);
        if (productListOrder.size() == 0) {
            SnackBarManagement._warning_CustomMessage(aMasterlayout, "Please add atlest one product to submit Order");
        } else {
            if (productOrder_IsZeroQuantity(productListOrder)) {
                SnackBarManagement._error_CustomMessage(aMasterlayout, "Product Quantity 0 is not Acceptable");
            } else {
                boolean isOk = dbCrudHelper.DeleteOrderMasterDetail(orderLocalId);
                if (isOk == true) {
                    orderPresenter.draftOrder(orderMaster);
                } else {
                    SnackBarManagement._error_CustomMessage(aMasterlayout, "Order Not Saved");
                }

            }
        }
    }

    public void UpdateCustomerLocation(int customerId, int empId) {
        if (!NetworkInformation.isConnected(defaultContext)) {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(),"You are not connected to internet");
            return;
        }
        try {

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Location location = LocationGet.getLastKnownLocation(defaultContext);
            if (location != null) {
                CordinateUpdate cordinateUpdate = new CordinateUpdate();
                cordinateUpdate.setId(customerId);
                cordinateUpdate.setEmpId(empId);
                cordinateUpdate.setLatitudeValue(String.valueOf(lat));
                cordinateUpdate.setLongitudeValue(String.valueOf(lon));
                cordinateUpdate.setStreetaddress(streetAddress);
                orderPresenter.UpdateCustomerLocation(cordinateUpdate);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //region Campaign Segment
    private void SelectCampaign(int customerId,/*, int empId*/List<CampaignGetReq> cmpaignReq) {
        try {
            if (productListOrder.size() == 0) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Please select product first");
                return;
            }
            getCampaign(cmpaignReq);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void getCampaign(List<CampaignGetReq> campaignGetReq) {
        // Show loading dialog
        ProgressDialog progressDoalog = new ProgressDialog(defaultContext);
        progressDoalog.setMessage("Campaign is loading... Please wait");
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.show();

        // Convert request to JSON for logging
        Gson gson = new Gson();
        String data = gson.toJson(campaignGetReq);
        Log.d("Get Campaign", "Request JSON: " + data);

        try {
            // Retrofit service
            ApiOrderProcess service = RetrofitOrderClientInstance
                    .getRetrofitInstance()
                    .create(ApiOrderProcess.class);

            Call<List<CampaignModel>> call = service.GetCampaign(campaignGetReq);
            call.enqueue(new Callback<List<CampaignModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<CampaignModel>> call, @NonNull Response<List<CampaignModel>> response) {
                    if (progressDoalog != null && progressDoalog.isShowing()) {
                        progressDoalog.dismiss();
                    }

                    if (response != null && response.isSuccessful() && response.body() != null) {
                        isSetCampaignAdd = true;
                        cmpaignList = new ArrayList<>(response.body());
                        SetCampaignShow(cmpaignList);
                    } else {
                        Toast.makeText(defaultContext, "Response failed: " + response.code(), Toast.LENGTH_LONG).show();
                        SnackBarManagement._error_CustomMessage(binding.masterLayoutId, "Unexpected response from server.");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<CampaignModel>> call, @NonNull Throwable t) {
                    if (progressDoalog != null && progressDoalog.isShowing()) {
                        progressDoalog.dismiss();
                    }

                    // Show error in Toast
                    Toast.makeText(defaultContext, "API Error: " + t.getMessage(), Toast.LENGTH_LONG).show();

                    // Show user-friendly error
                    if (t instanceof SocketTimeoutException) {
                        SnackBarManagement._error_CustomMessage(binding.masterLayoutId, "Time Out. Please try again.");
                    } else {
                        SnackBarManagement._error_CustomMessage(binding.masterLayoutId, "Campaign Loading Failed. Please try again.");
                    }

                    // Log the full error
                    Log.e("Campaign API Failure", "Error: ", t);
                    t.printStackTrace();
                }
            });

        } catch (Exception ex) {
            if (progressDoalog != null && progressDoalog.isShowing()) {
                progressDoalog.dismiss();
            }

            // Show exception in Toast
            Toast.makeText(defaultContext, "Exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();

            // Show fallback message in Snackbar
            SnackBarManagement._error_CustomMessage(binding.masterLayoutId, ex.getLocalizedMessage());

            // Log exception
            Log.e("Campaign Exception", "Exception occurred: ", ex);
            ex.printStackTrace();
        }
    }

    //    private void getCampaign(List<CampaignGetReq> campaignGetReq) {
//        ProgressDialog progressDoalog = new ProgressDialog(defaultContext);
//        progressDoalog.setMessage("Campaign is loading.... Please wait");
//        progressDoalog.show();
//        progressDoalog.setCanceledOnTouchOutside(false);
//
//        Gson gson = new Gson();
//        String data = gson.toJson(campaignGetReq);
//        System.out.println("Get Campaign " + data);
//        Log.d("Get Campaign","Get Campaign"+ data);
//
//        try {
//            ApiOrderProcess service = RetrofitOrderClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
//            Call<List<CampaignModel>> call = service.GetCampaign(campaignGetReq);
//            call.enqueue(new Callback<List<CampaignModel>>() {
//                @Override
//                public void onResponse(@NonNull Call<List<CampaignModel>> call, @NonNull Response<List<CampaignModel>> response) {
//                    if(progressDoalog!=null || progressDoalog.isShowing())
//                    {
//                        progressDoalog.dismiss();
//                    }
//                    if (response != null) {
//                        isSetCampaignAdd = true;
//                        cmpaignList = new ArrayList<>();
//                        cmpaignList = response.body();
//                        SetCampaignShow(cmpaignList);
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<List<CampaignModel>> call, @NonNull Throwable t) {
//                    if(progressDoalog!=null||progressDoalog.isShowing())
//                    {
//                        progressDoalog.dismiss();
//                    }
//                    if (t instanceof SocketTimeoutException) {
//                        SnackBarManagement._error_CustomMessage(binding.masterLayoutId, "Time Out.Please try again");
//                    } else {
//                        SnackBarManagement._error_CustomMessage(binding.masterLayoutId, "Campaign Loading Failed.Please try again");
//                    }
//                }
//            });
//
//        } catch (Exception ex) {
//            if(progressDoalog!=null||progressDoalog.isShowing())
//            {
//                progressDoalog.dismiss();
//            }
//            SnackBarManagement._error_CustomMessage(binding.masterLayoutId, ex.getLocalizedMessage());
//            ex.printStackTrace();
//        }
//
//    }
    //TODO:Herer Campaign Show into POPUP
    private void SetCampaignShow(List<CampaignModel> cmpaignList) {
        if (cmpaignList.size() > 0) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.pop_campaignshow, null);
            dialogBuilder.setView(dialogView);

            Button btn_done = (Button) dialogView.findViewById(R.id.btn_done);
            Button btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
            RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.rv_campaigns);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            adapter = new AdapterCampaignPop(this, cmpaignList, this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);

            btn_cancel.setOnClickListener(view -> {
                cmpList.clear();
                alertDialog.dismiss();
            });
            btn_done.setOnClickListener(view -> {
                //TODO:Herer Selected Campaign Show into POPUP
                campSelected = adapter.getSelected();
                int customerTypeId = campSelected.getCustomerTypeId();
                codList = new ArrayList<>();
                ModelProduct product = new ModelProduct();
                for (int i = 0; i < productListOrder.size(); i++) {
                    product = productListOrder.get(i);
                    custId = product.getCustomerMasterId();
                    pid = product.getProductId();
                    name = product.getProductName();
                    int qty = product.getQuantity();
                    double up = product.getUnitPrice();
                    double vatAmount = product.getVatPercentage();

                    double TP = (qty * up);
                    double totalVat = (vatAmount * qty);
                    double netAmount = TP + totalVat;
                    // totalFinal = totalFinal + totalVat;
                    CampOrderDetails cod = new CampOrderDetails(pid, name, qty, up, TP, vatAmount, vatAmount, totalVat
                            , netAmount, false, 0.0, 0.0, campId, CustomerId, customerTypeId);
                    codList.add(cod);
                }
                cmpaignPReq.setCampaignMasters(cmpList);
                cmpaignPReq.setOrderDetails(codList);
                //Send req for order details
                Gson gson = new Gson();
                String cmpListd = gson.toJson(cmpaignPReq);
                PostSelectedCampaign(cmpaignPReq/*, discountPercentageTP*/);

              //  String codListd = gson.toJson(codList);
                alertDialog.dismiss();
            });
        } else {
            SnackBarManagement._success_CustomMessage(binding.masterLayoutId, "No Campaign for this product");
        }
    }
    private void PostSelectedCampaign(CampaignPostReq cmpaignPost) {
        Gson gson = new Gson();
        String data = gson.toJson(cmpaignPost);
        System.out.println("Get Campaign Popup Request" + data);

        ProgressDialog progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Please wait...");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);
        try {
            ApiOrderProcess service = RetrofitOrderClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<List<CampaignCalModel>> call = service.GetCampaignProductWise(cmpaignPost);
            call.enqueue(new Callback<List<CampaignCalModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<CampaignCalModel>> call, @NonNull Response<List<CampaignCalModel>> response) {
                    progressDoalog.dismiss();
                    if (response != null) {
                        binding.cardOrderSummary.setVisibility(View.VISIBLE);
                        binding.campaignDiv.setVisibility(View.VISIBLE);
                        SetCampaignShowToCustomer(response.body());
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<CampaignCalModel>> call, @NonNull Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                    SnackBarManagement._error_CustomMessage(binding.getRoot(),"Time Out. Please try again");
                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(),"Please try again");

                    }
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            SnackBarManagement._error_CustomMessage(binding.masterLayoutId, "Some thing went wrong. Please try again");
            ex.printStackTrace();
        }
    }
    private void SetCampaignShowToCustomer(List<CampaignCalModel> cmpaigncalList) {
        if (cmpaigncalList != null) {
            cmpaignCalList = cmpaigncalList;

            setCAmpaignTotals(cmpaignCalList);
            RecyclerView recycler_view_product_Summary = findViewById(R.id.recycler_view_product_Summary);
            osAdapter = new Adapter_ordersummary(cmpaignCalList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view_product_Summary.setLayoutManager(mLayoutManager);
            recycler_view_product_Summary.setItemAnimator(new DefaultItemAnimator());
            recycler_view_product_Summary.setAdapter(osAdapter);
            recycler_view_product_Summary.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            recycler_view_product_Summary.setItemAnimator(null);
            recycler_view_product_Summary.scrollToPosition(0);
            osAdapter.notifyDataSetChanged();
        }
    }

    //endregion
//endregion
    public void setCustomerText(ModelCustomer aCustomer) {
        binding.customerName.setText(aCustomer.getCustomerName());
        binding.customerCodeTxt.setText(aCustomer.getCustomerCode());
        binding.customerAdressTxt.setText(aCustomer.getAddress());
    }
    private void LoadDepoStock(List<Rp_StockViewModel> stocklist, RecyclerView rvStock) {
        // System.out.println("data " + stocklist);
        AdapterDepoStock mAdapter = new AdapterDepoStock(stocklist, PlaceOrderActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(PlaceOrderActivity.this);
        rvStock.setLayoutManager(mLayoutManager);
        rvStock.setItemAnimator(new DefaultItemAnimator());
        rvStock.setAdapter(mAdapter);
        rvStock.setItemAnimator(null);
        rvStock.scrollToPosition(0);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnError(String message) {
        Toast.makeText(this, "error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductsGet(List<ModelProduct> aList) {
        if (aList != null) {
            productArrayList = (ArrayList<ModelProduct>) aList;
            ArrayAdapter<ModelProduct> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_dropdown_item_1line, productArrayList);
            binding.searchProducttxt.setAdapter(adapter);
            binding.searchProducttxt.setThreshold(1);//will start working from first character
        }
    }
    

    @Override
    public void onEditTextFocusChange(int position, int value) {
        productListOrder.get(position).setQuantity(value);
        updateProductTotals(productListOrder.get(position));
        setTotals();
        if (!binding.recyclerViewProduct.isComputingLayout()) {
            try {
                mAdapter.notifyItemChanged(position);
                osAdapter.notifyItemChanged(position);
                osAdapter.notifyDataSetChanged();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }


    @Override
    public boolean onLongClick(int position) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure wants to delete the Item ?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Delete",
                (dialog, id) -> {
                    try {
                        //Todo:When Delete any product


                        // productListCampShowList.remove(position);
                        productListOrder.remove(position);
                        setTotals();
                        mAdapter.notifyItemRemoved(position);

                        cmpaignCalList.clear();
                        cmpList.clear();
                        osAdapter.notifyItemRangeRemoved(0, cmpaignCalList.size());
                        osAdapter.notifyDataSetChanged();
                        binding.totalTPValueSummary.setText("");
                        binding.totalVatValueSummary.setText("");
                        binding.totalDiscountValueSummary.setText("");
                        binding.totalValueSummary.setText("");
                        dialog.cancel();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();

        return true;

    }

    @Override
    public void OrderSync(OrderMaster orderMaster) {

    }


    public ModelProduct updateProductTotals(ModelProduct aProduct) {
        int totalQn = aProduct.getQuantity();
        double unitPrice = aProduct.getUnitPrice();
        double vatPer = aProduct.getVatPercentage();
        double tp = (totalQn * unitPrice);
        double vatamount = aProduct.getVatPercentage();
        double TP = (unitPrice * totalQn);
        double TotalVat = (vatamount * totalQn);
        double tpvat = (TP + TotalVat);
        aProduct.setTp(TP);
        aProduct.setPrice(Double.valueOf(df2.format(tpvat)));
        return aProduct;

    }

    public ModelProduct updateProductDetails(ModelProduct aProduct) {
        int qun = aProduct.getQuantity();
        int totalQn = aProduct.getQuantity();
        double unitPrice = aProduct.getUnitPrice();
        double vatPer = aProduct.getVatPercentage();
        double tp = (totalQn * unitPrice);
        double vatamount = aProduct.getVatPercentage();
        double TP = (unitPrice * totalQn);
        double TotalVat = (vatamount * totalQn);
        double tpvat = (TP + TotalVat);

        aProduct.setTp(TP);
        aProduct.setPrice(Double.valueOf(df2.format(tpvat)));

        return aProduct;

    }

    public CampaignCalModel updateCampaignTotals(CampaignCalModel aProduct) {
        int totalQn = aProduct.getQuantity();
        double unitPrice = aProduct.getUnitPrice();
        double vatPer = aProduct.getVatPercentage();
        double tp = (totalQn * unitPrice);
        double vatAMount = ((Double.valueOf(df2.format(tp)) * vatPer) / 100);
        double priceT = tp + vatAMount;
        aProduct.setNetAmount(Double.valueOf(df2.format(priceT)));
        aProduct.setTotalPrice(tp);
        /*aProduct.setTp(tp);
        aProduct.setPrice(Double.valueOf(df2.format(priceT)));*/
        return aProduct;

    }

    //TODO:SET TOTAL Calculation in product Selection
    public void setTotals() {
        double totaltp = 0;
        double totalVat = 0;
        double totalFinal = 0;
        for (int i = 0; i < productListOrder.size(); i++) {
            ModelProduct product = new ModelProduct();
            product = productListOrder.get(i);
            int totalQn = product.getQuantity();
            double unitPrice = product.getUnitPrice();
            double vatAmaount = product.getVatPercentage();
            double TP = (unitPrice * totalQn);

            double totalvat = (vatAmaount * totalQn);

            totaltp = (totaltp + TP);
            totalVat = totalVat + totalvat;
            totalFinal = (totaltp + totalVat);
        }
        binding.totalTPValue.setText(df2.format(totaltp));
        binding.totalVatValue.setText(df2.format(totalVat));
        binding.totalValue.setText(df2.format(totalFinal));
    }

    public void setCAmpaignTotals(List<CampaignCalModel> cmpaignCalList) {
        double totaltp = 0;
        double totalVat = 0;
        double totalFinal = 0;
        double totalDiscount = 0;
        for (int i = 0; i < cmpaignCalList.size(); i++) {

            CampaignCalModel product = new CampaignCalModel();
            product = cmpaignCalList.get(i);
            int totalQn = product.getQuantity();
            double unitPrice = product.getUnitPrice();
            double vatPer = product.getUnitVatAmount();
            double discount = product.getDiscountValue();

            double TP = (unitPrice * totalQn);
            double pervat = (vatPer * totalQn);

            totalDiscount = totalDiscount + discount;
            totaltp = totaltp + TP;
            totalVat = totalVat + pervat;
            totalFinal = (totaltp + totalVat) - totalDiscount;
        }
        binding.totalTPValueSummary.setText(df2.format(totaltp));
        binding.totalVatValueSummary.setText(df2.format(totalVat));
        binding.totalDiscountValueSummary.setText(df2.format(totalDiscount));
        binding.totalValueSummary.setText(df2.format(totalFinal));
    }

    @Override
    public void onOrderSuccess(String message, String Who) {
        if (!TextUtils.isEmpty(message)) {
            binding.btnSubmit.setVisibility(View.VISIBLE);
            new AlertDialog.Builder(this)
                    .setTitle("Order Success")
                    .setMessage(message)
                    .setPositiveButton("OK", (dialog, which) -> {
                        switch (Who) {
                            case "SubmitOrder":
                                ((AlertDialog) dialog).getButton(which).setVisibility(View.INVISIBLE);
                                Intent i = new Intent(PlaceOrderActivity.this, MainActivity.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;
                            case "DraftOrderAdapter":
                                try {
                                    dbCrudHelper.DeleteOldOrder_OrderTable_SQLite(orderLocalId);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                ((AlertDialog) dialog).getButton(which).setVisibility(View.INVISIBLE);
                                Intent idr = new Intent(PlaceOrderActivity.this, MainActivity.class);
                                idr.addFlags(idr.FLAG_ACTIVITY_CLEAR_TOP | idr.FLAG_ACTIVITY_CLEAR_TASK | idr.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(idr);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;

                        }
                    }).setCancelable(false).show();
        } else {
            binding.btnSubmit.setVisibility(View.GONE);
        }

    }

    @Override
    public void onOrderDraftSuccess(String message) {
        if (!TextUtils.isEmpty(message)) {
            new AlertDialog.Builder(this)
                    .setTitle("Draft Success")
                    .setMessage(message)
                    .setPositiveButton("OK", (dialog, which) -> {
                        productListOrder.clear();
                        binding.recyclerViewProduct.getAdapter().notifyDataSetChanged();
                        binding.totalTPValue.setText("0.00");
                        binding.totalValue.setText("0.00");
                        if (cmpaignCalList != null) {
                            cmpaignCalList.clear();
                            osAdapter.notifyDataSetChanged();
                        }
                        binding.cardOrderSummary.setVisibility(View.GONE);
                        binding.totalTPValueSummary.setText("0");
                        binding.totalVatValueSummary.setText("0");
                        binding.totalDiscountValueSummary.setText("0");
                        binding.totalValueSummary.setText("0");
                        dialog.cancel();

                    })
                    .setCancelable(false)
                    .show();
        } else {
            binding.btnSubmit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onOrderError(String message) {
        //  Toast.makeText(this, "Error : " + message, Toast.LENGTH_SHORT).show();

        SnackBarManagement._error_CustomMessage(binding.masterLayoutId, message);
        //  fun_SelectCampaignButtonClick();
    }

    @Override
    public void onGenericSuccess(String message) {
        SnackBarManagement._success_CustomMessage(binding.masterLayoutId, message);
    }

    @Override
    public void onGenericError(String message) {
        SnackBarManagement._error_CustomMessage(binding.masterLayoutId, message);
    }


    public boolean productOrder_IsZeroQuantity(List<ModelProduct> aList) {
        boolean isZero = false;
        for (int i = 0; i < aList.size(); i++) {
            if (aList.get(i).getQuantity() == 0) {
                isZero = true;
            }

        }

        return isZero;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    public ModelProduct GetSingleProductByCode(int id) {
        ModelProduct product = new ModelProduct();
        for (int i = 0; i < productArrayList.size(); i++) {
            if (productArrayList.get(i).getProductId() == id) {
                product = productArrayList.get(i);
                break;
            }
        }
        return product;
    }

    @Override
    public void ckdItem(List<CampaignModel> camp, int Pos) {
        if (camp != null) {
            cmpList.clear();
            for (int i = 0; i < camp.size(); i++) {
                cm = new CampaignMasters();
                campId = camp.get(i).getCampgainMasterId();
                bonousId = camp.get(i).getBonusProductId();
                cm.setCampaignMasterId(campId);
                int custtypeId = camp.get(i).getCustomerTypeId();
                cm.setCampgainMasterId(campId);
                cmpList.add(cm);

                // Toast.makeText(this, "chk : "+String.valueOf(bonousId), Toast.LENGTH_SHORT).show();
            }

        } else {

            adapter.notifyItemRangeRemoved(0, camp.size());
            cmpList.clear();
            //binding.recyclerCampaignDetail.removeItemDecorationAt(po);
            // rv_doctors.removeItemDecorationAt(pos);

        }
    }

    @Override
    public void ckdItemid2(List<CampaignMaster2> ids2, int Pos) {

    }

    @Override
    public void ckdItemId(List<CampaignMasters> ids, int Pos) {
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(PlaceOrderActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    private void locationEnabled() {
        LocationManager lm = (LocationManager) PlaceOrderActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(PlaceOrderActivity.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places around you.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                    //turnGPSOn();
                                }
                            })

                    // .setNegativeButton("Cancel", null)
                    .show();
        }
    }


    public void getCLocation() {
        try {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (android.location.LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            lat = addresses.get(0).getLatitude();
            lon = addresses.get(0).getLongitude();
            streetAddress = addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
        }
    }
}