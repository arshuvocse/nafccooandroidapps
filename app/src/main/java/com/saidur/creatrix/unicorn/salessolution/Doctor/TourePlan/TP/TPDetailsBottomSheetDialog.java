package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TP;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.MorningEveningTimeModel;
import com.saidur.creatrix.unicorn.salessolution.Approval.TourPlan.Model.TimeValidationResponse;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.CheckedCustomerItem;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.CheckedMarketItem;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.CustomerItemChkAdapter;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.MarketItemChkAdapter;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Model.TourPlanReq;
import com.saidur.creatrix.unicorn.salessolution.Interface.DeleteListener;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketNewStracture;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.Market;
import com.saidur.creatrix.unicorn.salessolution.Model.MarketStructure.EmpInfoListModels;
import com.saidur.creatrix.unicorn.salessolution.Model.MarketStructure.LviewHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanWorkedWith;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPurposeViewModel;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Network.TourApiCall;
import com.saidur.creatrix.unicorn.salessolution.Presentar.MarketNewStructurePresenter;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.Service.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SessionManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.TimePick;
import com.saidur.creatrix.unicorn.salessolution.databinding.BottomSheetTpdetailsBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopTourplanAddMarketwiseBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopTourplanEditMarketwiseBinding;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class TPDetailsBottomSheetDialog extends BottomSheetDialogFragment implements IMarketNewStracture.View, ITP.View, DeleteListener, CheckedCustomerItem, CheckedMarketItem {
    BottomSheetTpdetailsBinding binding;
    TPDetailsListener mListener;
    PopTourplanAddMarketwiseBinding pbinding;
    PopTourplanEditMarketwiseBinding pbindingEdit;
    List<EmpInfoListModels> mioList;

    HashMap<String, String> user;
    DBCrudHelper dbCrudHelper;
    MarketNewStructurePresenter mpresenter;
    PresenterTP tppresenter;

    CustomerItemChkAdapter itemChkAdapter;
    MarketItemChkAdapter itemMarketChkAdapter;
    List<ModelCustomer> aCustomerList = new ArrayList<>();
    List<ModelMarket> aMarketListAll = new ArrayList<>();
    List<ModelCustomer> chkCustomerList = new ArrayList<>();
    List<ModelMarket> chMarketList = new ArrayList<>();
    List<ModelCustomer> finalCustomerList = new ArrayList<>();
    List<ModelMarket> finalMarketList = new ArrayList<>();
    String[] listItemCustomer_Customer;
    boolean[] checkedItems_Customer;

    List<TourPurposeViewModel> tppList;

    Dialog popupTPP, popupTPPEdit, popupCustomer;
    boolean isFinalSubmit, isChecked = false;
    String roleType, selectedMarketName,selectedMarketNameEnd, selectedTPPName, selectedTPPNameOther;
    String date, datez, month, year, currentdate, edit_dName, Tedit_dName;
    int empId, selectedGrpId, selectedRegionId,  selectedAreaId, selectedTeriId, selectedTeriIdEnd,  selectedSTeri,selectedSTeriEnd, selectedMarket, selectedMarketEnd, selectedTPP, selectedTPPOther, selectedMio, selectedVisEmp;
    int selectedRegionIdEdit=0;
    int e_selectedRegionIdEdit=0;


    int    e_selectedGrpId, e_selectedRegionId,  e_selectedAreaId, e_selectedTeriId, e_selectedSTeri, e_selectedMarket, e_selectedMarketEnd;
        int selectedTeriIdEdit=0;
        int e_selectedTPP=0;
        int e_selectedTPPOther=0;


        int selectedAreaIdEdit=0;
        int selectedSTeriEdit=0;
        int selectedMarketEdit=0;

int e_selectedTeriIdEdit=0;
        int e_selectedAreaIdEdit=0;
        int e_selectedSTeriEdit=0;
        int e_selectedMarketEdit=0;


    int selectedRegionIdEndEdit=0;
    int selectedTeriIdEndEdit=0;
    int selectedAreaIdEndEdit=0;
    int selectedSTeriEndEdit=0;
    int selectedMarketEndEdit=0;
    int e_selectedRegionIdEndEdit=0;
    int e_selectedTeriIdEndEdit=0;
    int e_selectedAreaIdEndEdit=0;
    int e_selectedSTeriEndEdit=0;
    int e_selectedMarketEndEdit=0;
    ModelSession sessionNew;
    RecyclerView rv_customer;
    EditText srchview;
    TextView done_cust, cancel_cust, title;

    List<TourPlanViewModel> tpl = new ArrayList<>();

    TPDtlsAdapter adapter;
    SimpleDateFormat dateFormat;
    BottomSheetBehavior bottomSheetBehavior;

    // String isMorning = "0", isEvening = "0";

    public static TPDetailsBottomSheetDialog newInstance() {
        return new TPDetailsBottomSheetDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        binding = BottomSheetTpdetailsBinding.inflate(getLayoutInflater());

        dbCrudHelper = new DBCrudHelper(requireActivity());
        mpresenter = new MarketNewStructurePresenter(this, requireActivity());
        tppresenter = new PresenterTP(this, requireActivity());

        sessionNew=dbCrudHelper.getSession();
          empId = sessionNew.getEmpInfoId();
      //  String roleType = sessionNew.getRoleType();
          roleType = sessionNew.getRoleType();

        Bundle mArgs = getArguments();
        if (Constants.From.equals("ActivityTourPlan_")) {
            date = mArgs.getString("formattedDate");
            datez = mArgs.getString("datez");
            month = mArgs.getString("Month");
            year = mArgs.getString("Year");
            isFinalSubmit = mArgs.getBoolean("isFinalSubmit");
        }

        binding.tvMonthdateyear.setText("(M-D-Y) " + datez);





        try {
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date Todate = null;
            Date CurrentDate = null;
            Todate = dateFormat.parse(today);
            CurrentDate = dateFormat.parse(date);

            tppresenter.getTourPlanDailyByEmpId(Integer.parseInt(month), Integer.parseInt(year), empId, date, isFinalSubmit);
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
            // e.printStackTrace();
        }

        onLoadCustomerReceived(dbCrudHelper.getCustomerList_SQLite());
        aCustomerList = dbCrudHelper.getCustomerList_SQLite();
        aMarketListAll = dbCrudHelper.getMarketListAll_SQLite();
    mioList = dbCrudHelper.getMIOListWithSelf_SQLite(empId,roleType);


        binding.fabAdd.setOnClickListener(v -> {
            popup_tpp(datez);


            try{
                String ShiftInfo="";
                Context context = getContext();
                ShiftInfo="Morning";
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                GetStartEndTimeforShift(progressDialog,ShiftInfo);
                progressDialog.dismiss();
            }catch (Exception ex){

            }
        });
        binding.btnDone.setOnClickListener(v -> {
            TourPlanReq req = new TourPlanReq();
            req.setaTourPlanInfo(tpl);
            Gson gson1 = new Gson();
            String data = gson1.toJson(req);
            System.out.println("datapostperday" + data);
            tppresenter.saveTourPlanPerdayByEmpId(req);
        });
        binding.btnClose.setOnClickListener(view -> dismiss());
        /* binding.tourCountTxt.setText(String.valueOf(tourCount));
        binding.monthTxt.setText(monStr + ',' + String.valueOf(year));
        GetTourPlanMasterData(monthValue,year,empId);
        binding.psubmitBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rmrks = binding.remarksTxt.getText().toString();
                mListener.FinalSubmitClick(monthValue,year,empId,rmrks);
                dismiss();
            }
        });*/


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    public void onLoadCustomerReceived(List<ModelCustomer> aList) {
        try {
            if (aList != null) {
                listItemCustomer_Customer = new String[aList.size()];
                for (int i = 0; i < aList.size(); i++) {
                    listItemCustomer_Customer[i] = aList.get(i).getCustomerName();
                }
                checkedItems_Customer = new boolean[listItemCustomer_Customer.length];

            }
        } catch (Exception exception) {
            //exception.printStackTrace();
        }

    }

    //add data for post
    private void Editpopup_tpp(String ddy) {
        popupTPP = new Dialog(requireActivity());
        popupTPP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupTPP.setCancelable(true);
        pbinding = PopTourplanAddMarketwiseBinding.inflate(LayoutInflater.from(requireActivity()));
        popupTPP.setContentView(pbinding.getRoot());
        popupTPP.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupTPP.show();
        pbinding.cbStart.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            if (isChecked1) {
                pbinding.cbEnd.setChecked(false);
            }
        });
        pbinding.cbEnd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                pbinding.cbStart.setChecked(false);
            }
        });

        pbinding.etStartTime.setText("");
        pbinding.etEndTime.setText("");
        pbinding.cbStart.setChecked(false);
        pbinding.cbEnd.setChecked(false);
        //Role wise spinner populate
        try {
            switch (roleType) {
                case "MBE":
                    pbinding.regiondiv.setVisibility(View.GONE);
                    pbinding.areadiv.setVisibility(View.GONE);
                    pbinding.teridiv.setVisibility(View.GONE);

                    pbinding.regiondivend.setVisibility(View.GONE);
                    pbinding.areadivend.setVisibility(View.GONE);
                    pbinding.teridivend.setVisibility(View.GONE);

                    pbinding.llVisitedWith.setVisibility(View.GONE);
                    //   pbinding.llObjective.setVisibility(View.GONE);
               mpresenter.GetTeritoryLocal(0);
              mpresenter.GetTeritoryLocalEnd(0);
                    break;
                case "MIO":
                    pbinding.regiondiv.setVisibility(View.GONE);
                    pbinding.areadiv.setVisibility(View.GONE);

                    pbinding.regiondivend.setVisibility(View.GONE);
                    pbinding.areadivend.setVisibility(View.GONE);

                    pbinding.llVisitedWith.setVisibility(View.GONE);
                    //   pbinding.llObjective.setVisibility(View.GONE);
               mpresenter.GetTeritoryLocal(0);
              mpresenter.GetTeritoryLocalEnd(0);
                    break;
                case "AM":
                    pbinding.regiondiv.setVisibility(View.GONE);
                    pbinding.areadiv.setVisibility(View.VISIBLE);

                    pbinding.regiondivend.setVisibility(View.GONE);
                    pbinding.areadivend.setVisibility(View.VISIBLE);

                    pbinding.llVisitedWith.setVisibility(View.VISIBLE);
                    //  pbinding.llObjective.setVisibility(View.VISIBLE);
               mpresenter.GetAreaLocal(0);
              mpresenter.GetAreaLocalEnd(0);
                    break;
                case "DZSM":
                case "NSM":
                case "Admin":
                    pbinding.regiondiv.setVisibility(View.VISIBLE);
                    pbinding.areadiv.setVisibility(View.VISIBLE);

                    pbinding.regiondivend.setVisibility(View.VISIBLE);
                    pbinding.areadivend.setVisibility(View.VISIBLE);

                    pbinding.llVisitedWith.setVisibility(View.VISIBLE);
                    //   pbinding.llObjective.setVisibility(View.VISIBLE);
//                    mpresenter.GetRegionLocal(0);
//                    mpresenter.GetRegionLocalEdit(0);
//                    mpresenter.GetRegionLocalEnd(0);
                    break;
            }
        } catch (Exception exception) {
        }


        if (mioList != null) {
            System.out.println("mlist " + mioList.toString());
            ArrayAdapter<EmpInfoListModels> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, mioList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pbinding.tourPlanVisitedSpinner.setAdapter(dataAdapter);
            pbinding.tourPlanVisitedSpinner.setTitle("Select Worked With");
            pbinding.tourPlanVisitedSpinner.setPositiveButton("OK");
        }
//        pbinding.tourPlanVisitedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                EmpInfoListModels mio = (EmpInfoListModels) parent.getSelectedItem();
//                selectedVisEmp = mio.getMIOEmpId();
//
//                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
//                progressDialog.setMessage("Loading...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//
//                loadWorkWithCopyInfo(progressDialog);
//
//
//                progressDialog.dismiss(); // Dismiss the progress dialog
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        pbinding.tourDate.setText(ddy);
        LoadTourPurposeForMarketVisit(pbinding.tourPlanPurposeSpinner);
        LoadTourPurposeForOtherVisit(pbinding.tourPlanPurposeOtherSpinner);
        pbinding.custAdd.setOnClickListener(v -> {
            popup_Customer();
            popupCustomer.show();
        });
        pbinding.othermarketListLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Show a confirmation dialog before deleting
                new AlertDialog.Builder(requireActivity())
                        .setTitle("Delete Market")
                        .setMessage("Are you sure you want to delete this Market?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Remove the selected item from the chMarketList
                            chMarketList.remove(position);
                            aMarketListAll.remove(position);


                            finalMarketList.remove(position);


                            // Serialize the updated list back to JSON (optional)
                            Gson gson = new Gson();
                            String data = gson.toJson(chMarketList);

                            // Update the adapter and notify about the dataset change
                            ArrayAdapter<ModelMarket> custAd = new ArrayAdapter<>(requireActivity(), R.layout.lv_dcrbrand, R.id.dcrbrand, chMarketList);
                            pbinding.othermarketListLv.setAdapter(custAd);
                            custAd.notifyDataSetChanged();

                            // Optionally, update ListView size if dynamic sizing is required
                            LviewHelper.getListViewSize(pbinding.othermarketListLv);
                            SnackBarManagement._warning_CustomMessage(pbinding.master, "Market removed"
                            );
                            // Show a confirmation toast
                            //  Toast.makeText(requireActivity(), "Market removed", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

                return true; // Return true to indicate the long press is handled
            }
        });
        pbinding.otherMarketAdd.setOnClickListener(v -> {
            popup_OtherMarket();
            popupCustomer.show();
        });
        pbinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupTPP.dismiss();
            }
        });


        pbinding.rgButtonTourplanType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_MarketVisit:
                        pbinding.llMorEve.setVisibility(View.VISIBLE);
                        pbinding.llOther.setVisibility(View.GONE);
                        pbinding.llMorEveRgButton.setVisibility(View.VISIBLE);
                        pbinding.txtStart.setText("08:00 AM");
                        pbinding.txtEnd.setText("02:59:59 PM");
                        break;
                    case R.id.rb_otherVisit:
                        pbinding.llOther.setVisibility(View.VISIBLE);
                        pbinding.llMorEve.setVisibility(View.GONE);
                        pbinding.llMorEveRgButton.setVisibility(View.GONE);

                        break;
                }
            }
        });


        pbinding.rgButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Context context = getContext();
                if (context == null) {
                    return; // Context is null, exit early to avoid errors
                }
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String ShiftInfo="";
                if (pbinding.rbMorning.isChecked()){
                    ShiftInfo="Morning";

                }
                else {
                    ShiftInfo="Evening";

                }

                pbinding.etStartTime.setText("");
                pbinding.etEndTime.setText("");



                GetStartEndTimeforShift(progressDialog,ShiftInfo);


//                Toast.makeText(context, selectedVisEmp, Toast.LENGTH_SHORT).show();
//                loadWorkWithCopyInfo(progressDialog,selectedVisEmp);

                progressDialog.dismiss();
            }
        });

        new TimePick(pbinding.etStartTime, true);
        new TimePick(pbinding.etEndTime, true);

        pbinding.etStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // This is called to notify you that the text is about to change.
                // You can handle any logic if needed before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This is called when the text in the EditText has changed.
                // You can add validation logic here or trigger other actions when the text changes.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This is called after the text has been changed.
                // You can validate the new input here, for example:
                String inputTime = editable.toString();
                String shiftInfo = "";
                if (pbinding.rbMorning.isChecked()) {
                    shiftInfo="Morning";
                }
                if (pbinding.rbEvening.isChecked()) {
                    shiftInfo="Evening";
                }

                if (!inputTime.isEmpty()) {
                    // Perform validation logic on inputTime if needed
                    validateTimeRange(inputTime, shiftInfo, new TimeValidationCallback() {
                        @Override
                        public void onValidationResult(boolean isValid, String msg) {
                            if (isValid) {
                                // Time is valid, proceed with any further logic
                            } else {
                                // Show error message
                                SnackBarManagement._warning_CustomMessage(pbinding.master, msg);
                                pbinding.etStartTime.setText("");
                            }
                        }
                    });
                }
            }
        });
        pbinding.etEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // This is called to notify you that the text is about to change.
                // You can handle any logic if needed before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This is called when the text in the EditText has changed.
                // You can add validation logic here or trigger other actions when the text changes.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This is called after the text has been changed.
                // You can validate the new input here, for example:
                String inputTime = editable.toString();
                String shiftInfo = "";
                if (pbinding.rbMorning.isChecked()) {
                    shiftInfo="Morning";
                }
                if (pbinding.rbEvening.isChecked()) {
                    shiftInfo="Evening";
                }

                if (!inputTime.isEmpty()) {
                    // Perform validation logic on inputTime if needed
                    validateTimeRange(inputTime, shiftInfo, new TimeValidationCallback() {
                        @Override
                        public void onValidationResult(boolean isValid, String msg) {
                            if (isValid) {
                                // Time is valid, proceed with any further logic
                            } else {
                                // Show error message
                                SnackBarManagement._warning_CustomMessage(pbinding.master, msg);
                                pbinding.etEndTime.setText("");
                            }
                        }
                    });
                }
            }
        });


        pbinding.tpsubmitBnt.setOnClickListener(v -> {
            try {
                TourPlanViewModel tv = new TourPlanViewModel();
                tv.setTourPlanId(0);


                if (pbinding.rbMarketVisit.isChecked()) {
                    tv.setIsMarketVisit("1");
                } else {
                    tv.setIsMarketVisit("0");
                }
                if (pbinding.rbOtherVisit.isChecked()) {
                    tv.setIsOtherVisit("1");
                } else {
                    tv.setIsOtherVisit("0");
                }


                if (pbinding.rbMorning.isChecked()) {
                    tv.setIsMorning("1");
                } else {
                    tv.setIsMorning("0");
                }
                if (pbinding.rbEvening.isChecked()) {
                    tv.setIsEvening("1");
                } else {
                    tv.setIsEvening("0");
                }



                if (pbinding.rbMarketVisit.isChecked()){

                    if (TextUtils.isEmpty(selectedTPPName)) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Tour Purpose Mandatory");
                        return;
                    }
                }
                else{

                    if (TextUtils.isEmpty(selectedTPPNameOther)) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Tour Purpose Mandatory");
                        return;
                    }

                }
                if (pbinding.rbMarketVisit.isChecked()) {


                    if (TextUtils.isEmpty(pbinding.StartMarketTxt.getText().toString().trim())) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Start Market Mandatory");
                        return;
                    } else {

                    }
                    if (TextUtils.isEmpty(pbinding.EndMarketTxt.getText().toString().trim())) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "End Market Mandatory");
                        return;
                    } else {

                    }

                    if (TextUtils.isEmpty(pbinding.etStartTime.getText().toString())) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Start Time Mandatory");
                        return;
                    } else {
                        tv.setStarttime(pbinding.etStartTime.getText().toString());
                    }




                    if (TextUtils.isEmpty(pbinding.etEndTime.getText().toString())) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "End Time Mandatory");
                        return;
                    } else {
                        tv.setEndtime(pbinding.etEndTime.getText().toString());
                    }




                    if(chMarketList.size() >5){
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "You will not be able to select more than 5 markets for this visit.\n"
                        );
//                        chMarketList.clear();
//                        finalMarketList.clear();
//                        chMarketList.clear();
//
//// Notify the adapter that the data has changed to refresh the ListView
//                        ArrayAdapter<Market> custAd = new ArrayAdapter<>(requireActivity(), R.layout.lv_dcrbrand, R.id.dcrbrand, chMarketList);
//                        pbinding.othermarketListLv.setAdapter(custAd);
//
//// Optionally, you can call this to reset the ListView size (if needed)
//                        LviewHelper.getListViewSize(pbinding.othermarketListLv);
                        return;
                    }


                    if (TextUtils.isEmpty(pbinding.ObjectiveTxt.getText().toString().trim())) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Objective Mandatory");
                        return;
                    } else {

                    }


                }


                if (selectedVisEmp != 0 || !TextUtils.isEmpty(String.valueOf(selectedVisEmp))) {
                    tv.setVisitedWithEmpInfoId(selectedVisEmp);
                }else {
                    tv.setVisitedWithEmpInfoId(0);
                }

                tv.setMarketId(selectedSTeri);
                tv.setMarketIdEnd(selectedSTeriEnd);
                tv.setMarketName(pbinding.StartMarketTxt.getText().toString().trim());
                tv.setMarketNameEnd(pbinding.EndMarketTxt.getText().toString().trim());

                if (pbinding.rbMarketVisit.isChecked()){
                    tv.setTPId(selectedTPP);
                    tv.setTPName(selectedTPPName);
                }
                else{
                    tv.setTPId(selectedTPPOther);
                    tv.setTPName(selectedTPPNameOther);

                }

                tv.setEmpInfoId(empId);
                tv.setTourPlanDate(pbinding.tourDate.getText().toString());
                String objectiveText = pbinding.ObjectiveTxt.getText().toString().trim();

// If the text is empty, pass an empty string
                if (objectiveText.isEmpty()) {
                    tv.setObjective("");
                } else {
                    tv.setObjective(objectiveText);
                }

                if (tpl.size() == 0) {
                    tv.setSerialNo(1);
                } else {
                    tv.setSerialNo(tpl.size() + 1);
                }
                if ((chkCustomerList.size() != 0)) {
                    chkCustomerList.clear();
                    tv.setaCustomerMasterList(finalCustomerList);
                } else {
                    tv.setaCustomerMasterList(chkCustomerList);
                    finalCustomerList.clear();
                }


                if ((chMarketList.size() != 0)) {
                    chMarketList.clear();
                    tv.setaVisitedMarketList(finalMarketList);
                } else {
                    tv.setaVisitedMarketList(chMarketList);
                    finalMarketList.clear();
                }


                tpl.add(tv);
                SetInRecyclerviewData(tpl, 2);
                //finalCustomerList.clear();
            } catch (Exception exception) {
            }
            popupTPP.dismiss();
        });

    }
        private void popup_tpp(String ddy) {
        popupTPP = new Dialog(requireActivity());
        popupTPP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupTPP.setCancelable(true);
        pbinding = PopTourplanAddMarketwiseBinding.inflate(LayoutInflater.from(requireActivity()));
        popupTPP.setContentView(pbinding.getRoot());
        popupTPP.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupTPP.show();
        pbinding.cbStart.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            if (isChecked1) {
                pbinding.cbEnd.setChecked(false);
            }
        });
        pbinding.cbEnd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                pbinding.cbStart.setChecked(false);
            }
        });

        pbinding.etStartTime.setText("");
        pbinding.etEndTime.setText("");
        pbinding.cbStart.setChecked(false);
        pbinding.cbEnd.setChecked(false);
        //Role wise spinner populate
        try {
            switch (roleType) {
                case "MBE":
                    pbinding.regiondiv.setVisibility(View.GONE);
                    pbinding.areadiv.setVisibility(View.GONE);
                    pbinding.teridiv.setVisibility(View.GONE);

                    pbinding.regiondivend.setVisibility(View.GONE);
                    pbinding.areadivend.setVisibility(View.GONE);
                    pbinding.teridivend.setVisibility(View.GONE);

                    pbinding.llVisitedWith.setVisibility(View.GONE);
                 //   pbinding.llObjective.setVisibility(View.GONE);
                    mpresenter.GetTeritoryLocal(0);
                    mpresenter.GetSTeritoryLocal(0);
                    mpresenter.GetSTeritoryLocalEnd(0);
                    mpresenter.GetTeritoryLocalEnd(0);
                    break;
                case "MIO":
                    pbinding.regiondiv.setVisibility(View.GONE);
                    pbinding.areadiv.setVisibility(View.GONE);

                    pbinding.regiondivend.setVisibility(View.GONE);
                    pbinding.areadivend.setVisibility(View.GONE);

                    pbinding.llVisitedWith.setVisibility(View.GONE);
                 //   pbinding.llObjective.setVisibility(View.GONE);
                    mpresenter.GetTeritoryLocal(0);
                    mpresenter.GetTeritoryLocalEnd(0);
                    break;
                case "AM":
                    pbinding.regiondiv.setVisibility(View.GONE);
                    pbinding.areadiv.setVisibility(View.VISIBLE);

                    pbinding.regiondivend.setVisibility(View.GONE);
                    pbinding.areadivend.setVisibility(View.VISIBLE);

                    pbinding.llVisitedWith.setVisibility(View.VISIBLE);
                  //  pbinding.llObjective.setVisibility(View.VISIBLE);
                    mpresenter.GetAreaLocal(0);
                    mpresenter.GetAreaLocalEnd(0);
                    break;
                case "DZSM":
                case "NSM":
                case "Admin":
                    pbinding.regiondiv.setVisibility(View.VISIBLE);
                    pbinding.areadiv.setVisibility(View.VISIBLE);

                    pbinding.regiondivend.setVisibility(View.VISIBLE);
                    pbinding.areadivend.setVisibility(View.VISIBLE);

                    pbinding.llVisitedWith.setVisibility(View.VISIBLE);
                 //   pbinding.llObjective.setVisibility(View.VISIBLE);
                    mpresenter.GetRegionLocal(0);
                    mpresenter.GetRegionLocalEdit(0);
                    mpresenter.GetRegionLocalEnd(0);
                    break;
            }
        } catch (Exception exception) {
        }
        if (mioList != null) {
            System.out.println("mlist " + mioList.toString());
            ArrayAdapter<EmpInfoListModels> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, mioList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pbinding.tourPlanVisitedSpinner.setAdapter(dataAdapter);
            pbinding.tourPlanVisitedSpinner.setTitle("Select Worked With");
            pbinding.tourPlanVisitedSpinner.setPositiveButton("OK");
        }
        pbinding.tourPlanVisitedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EmpInfoListModels mio = (EmpInfoListModels) parent.getSelectedItem();
                selectedVisEmp = mio.getMIOEmpId();

                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                loadWorkWithCopyInfo(progressDialog);


                progressDialog.dismiss(); // Dismiss the progress dialog
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        pbinding.tourDate.setText(ddy);
        LoadTourPurposeForMarketVisit(pbinding.tourPlanPurposeSpinner);
        LoadTourPurposeForOtherVisit(pbinding.tourPlanPurposeOtherSpinner);
        pbinding.custAdd.setOnClickListener(v -> {
            popup_Customer();
            popupCustomer.show();
        });

        pbinding.otherMarketAdd.setOnClickListener(v -> {
            popup_OtherMarket();
            popupCustomer.show();
        });
        pbinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupTPP.dismiss();
            }
        });



        pbinding.rgButtonTourplanType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_MarketVisit:
                        pbinding.llMorEve.setVisibility(View.VISIBLE);
                        pbinding.llOther.setVisibility(View.GONE);
                        pbinding.llMorEveRgButton.setVisibility(View.VISIBLE);
                        pbinding.txtStart.setText("08:00 AM");
                        pbinding.txtEnd.setText("02:59:59 PM");
                        break;
                    case R.id.rb_otherVisit:
                        pbinding.llOther.setVisibility(View.VISIBLE);
                        pbinding.llMorEve.setVisibility(View.GONE);
                        pbinding.llMorEveRgButton.setVisibility(View.GONE);

                        break;
                }
            }
        });


   pbinding.rgButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Context context = getContext();
                if (context == null) {
                    return; // Context is null, exit early to avoid errors
                }
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String ShiftInfo="";
                if (pbinding.rbMorning.isChecked()){
                    ShiftInfo="Morning";

                }
                else {
                    ShiftInfo="Evening";

                }

                pbinding.etStartTime.setText("");
                pbinding.etEndTime.setText("");



                GetStartEndTimeforShift(progressDialog,ShiftInfo);


//                Toast.makeText(context, selectedVisEmp, Toast.LENGTH_SHORT).show();
//                loadWorkWithCopyInfo(progressDialog,selectedVisEmp);

                progressDialog.dismiss();
            }
        });

        new TimePick(pbinding.etStartTime, true);
        new TimePick(pbinding.etEndTime, true);

        //new Implementation
         /*    pbinding.rgButton.setOnCheckedChangeListener((group, id) -> {
            switch (id) {
                case -1:
                    break;
                default:
                case R.id.rb_morning:
                    isMorning="1";
                    isEvening="0";
                    break;
                case R.id.rb_evening:
                    isMorning="0";
                    isEvening="1";
                    break;
            }
        });*/

        //popup for tp details recyclerview

        pbinding.etStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // This is called to notify you that the text is about to change.
                // You can handle any logic if needed before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This is called when the text in the EditText has changed.
                // You can add validation logic here or trigger other actions when the text changes.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This is called after the text has been changed.
                // You can validate the new input here, for example:
                String inputTime = editable.toString();
                String shiftInfo = "";
                if (pbinding.rbMorning.isChecked()) {
                    shiftInfo="Morning";
                }
                if (pbinding.rbEvening.isChecked()) {
                    shiftInfo="Evening";
                }

                if (!inputTime.isEmpty()) {
                    // Perform validation logic on inputTime if needed
                    validateTimeRange(inputTime, shiftInfo, new TimeValidationCallback() {
                        @Override
                        public void onValidationResult(boolean isValid, String msg) {
                            if (isValid) {
                                // Time is valid, proceed with any further logic
                            } else {
                                // Show error message
                                SnackBarManagement._warning_CustomMessage(pbinding.master, msg);
                                pbinding.etStartTime.setText("");
                            }
                        }
                    });
                }
            }
        });
        pbinding.etEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // This is called to notify you that the text is about to change.
                // You can handle any logic if needed before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This is called when the text in the EditText has changed.
                // You can add validation logic here or trigger other actions when the text changes.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This is called after the text has been changed.
                // You can validate the new input here, for example:
                String inputTime = editable.toString();
                String shiftInfo = "";
                if (pbinding.rbMorning.isChecked()) {
                    shiftInfo="Morning";
                }
                if (pbinding.rbEvening.isChecked()) {
                    shiftInfo="Evening";
                }

                if (!inputTime.isEmpty()) {
                    // Perform validation logic on inputTime if needed
                    validateTimeRange(inputTime, shiftInfo, new TimeValidationCallback() {
                        @Override
                        public void onValidationResult(boolean isValid, String msg) {
                            if (isValid) {
                                // Time is valid, proceed with any further logic
                            } else {
                                // Show error message
                                SnackBarManagement._warning_CustomMessage(pbinding.master, msg);
                                pbinding.etEndTime.setText("");
                            }
                        }
                    });
                }
            }
        });


        pbinding.tpsubmitBnt.setOnClickListener(v -> {
            try {
                TourPlanViewModel tv = new TourPlanViewModel();
                tv.setTourPlanId(0);


                if (pbinding.rbMarketVisit.isChecked()) {
                    tv.setIsMarketVisit("1");
                } else {
                    tv.setIsMarketVisit("0");
                }
                if (pbinding.rbOtherVisit.isChecked()) {
                    tv.setIsOtherVisit("1");
                } else {
                    tv.setIsOtherVisit("0");
                }


                if (pbinding.rbMorning.isChecked()) {
                    tv.setIsMorning("1");
                } else {
                    tv.setIsMorning("0");
                }
                if (pbinding.rbEvening.isChecked()) {
                    tv.setIsEvening("1");
                } else {
                    tv.setIsEvening("0");
                }



                if (pbinding.rbMarketVisit.isChecked()){

                    if (TextUtils.isEmpty(selectedTPPName)) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Tour Purpose Mandatory");
                        return;
                    }
                }
                else{

                    if (TextUtils.isEmpty(selectedTPPNameOther)) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Tour Purpose Mandatory");
                        return;
                    }

                }
                if (pbinding.rbMarketVisit.isChecked()) {
                    if (TextUtils.isEmpty(pbinding.etStartTime.getText().toString())) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Start Time Mandatory");
                        return;
                    } else {
                        tv.setStarttime(pbinding.etStartTime.getText().toString());
                    }




                    if (TextUtils.isEmpty(pbinding.etEndTime.getText().toString())) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "End Time Mandatory");
                        return;
                    } else {
                        tv.setEndtime(pbinding.etEndTime.getText().toString());
                    }




                    if(chMarketList.size() >5){
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "You will not be able to select more than 5 markets for this visit.\n"
                              );
//                        chMarketList.clear();
//                        finalMarketList.clear();
//                        chMarketList.clear();
//
//// Notify the adapter that the data has changed to refresh the ListView
//                        ArrayAdapter<Market> custAd = new ArrayAdapter<>(requireActivity(), R.layout.lv_dcrbrand, R.id.dcrbrand, chMarketList);
//                        pbinding.othermarketListLv.setAdapter(custAd);
//
//// Optionally, you can call this to reset the ListView size (if needed)
//                        LviewHelper.getListViewSize(pbinding.othermarketListLv);
                        return;
                    }


                    if (TextUtils.isEmpty(pbinding.ObjectiveTxt.getText().toString().trim())) {
                        SnackBarManagement._warning_CustomMessage(pbinding.master, "Objective Mandatory");
                        return;
                    } else {

                    }


                }


                if (selectedVisEmp != 0 || !TextUtils.isEmpty(String.valueOf(selectedVisEmp))) {
                    tv.setVisitedWithEmpInfoId(selectedVisEmp);
                }else {
                    tv.setVisitedWithEmpInfoId(0);
                }


                tv.setMarketId(selectedSTeri);
                tv.setMarketIdEnd(selectedTeriIdEnd);
                tv.setMarketName(pbinding.StartMarketTxt.getText().toString().trim());
                tv.setMarketNameEnd(pbinding.EndMarketTxt.getText().toString().trim());

                if (pbinding.rbMarketVisit.isChecked()){
                    tv.setTPId(selectedTPP);
                    tv.setTPName(selectedTPPName);
                }
                else{
                    tv.setTPId(selectedTPPOther);
                    tv.setTPName(selectedTPPNameOther);

                }

                tv.setEmpInfoId(empId);
                tv.setTourPlanDate(pbinding.tourDate.getText().toString());
                String objectiveText = pbinding.ObjectiveTxt.getText().toString().trim();

// If the text is empty, pass an empty string
                if (objectiveText.isEmpty()) {
                    tv.setObjective("");
                } else {
                    tv.setObjective(objectiveText);
                }

                if (tpl.size() == 0) {
                    tv.setSerialNo(1);
                } else {
                    tv.setSerialNo(tpl.size() + 1);
                }
                if ((chkCustomerList.size() != 0)) {
                    chkCustomerList.clear();
                    tv.setaCustomerMasterList(finalCustomerList);
                } else {
                    tv.setaCustomerMasterList(chkCustomerList);
                    finalCustomerList.clear();
                }


                if ((chMarketList.size() != 0)) {
                    chMarketList.clear();
                    tv.setaVisitedMarketList(finalMarketList);
                } else {
                    tv.setaVisitedMarketList(chMarketList);
                    finalMarketList.clear();
                }


                tpl.add(tv);
                SetInRecyclerviewData(tpl, 2);
                //finalCustomerList.clear();
            } catch (Exception exception) {
            }
            popupTPP.dismiss();
        });
    }



    interface TimeValidationCallback {
        void onValidationResult(boolean isValid, String msg);
    }

    private void validateTimeRange(String startTime, String shiftInfo, TimeValidationCallback callback) {
        try {
            // Initialize the Retrofit service instance
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<TimeValidationResponse> call = service.ValidateTimeInRange(startTime, shiftInfo);

            // Make the API call asynchronously
            call.enqueue(new Callback<TimeValidationResponse>() {
                @Override
                public void onResponse(@NonNull Call<TimeValidationResponse> call, @NonNull Response<TimeValidationResponse> response) {
                    if (response.body() != null) {
                        TimeValidationResponse timeValidationResponse = response.body();
                        callback.onValidationResult(timeValidationResponse.isValid(), timeValidationResponse.getMessage());
                    } else {
                        // Handle failure
                        callback.onValidationResult(false, "Failed to validate time.");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TimeValidationResponse> call, @NonNull Throwable t) {
                    // Handle failure
                    callback.onValidationResult(false, "API call failed: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            // Handle exception
            callback.onValidationResult(false, "An error occurred: " + e.getMessage());
        }
    }
    private void GetStartEndTimeforShift(ProgressDialog progressDialog, String ShiftInfo) {

        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<MorningEveningTimeModel> call = service.GetMorningEveningTime( ShiftInfo);

            call.enqueue(new Callback<MorningEveningTimeModel>() {
                @Override
                public void onResponse(@NonNull Call<MorningEveningTimeModel> call, @NonNull Response<MorningEveningTimeModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        MorningEveningTimeModel tourPlan = response.body();
                        pbinding.txtStart.setText(tourPlan.getStartTime());
                        pbinding.txtEnd.setText(tourPlan.getEndTime());
                      //  loadWorkWithCopyInfo(progressDialog);
                    } else {
                        // Handle the case where response is not successful or body is null
                        // view.onError("Failed to get tour plan: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MorningEveningTimeModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss(); // Dismiss the progress dialog
                    if (t instanceof SocketTimeoutException) {
                        // Handle timeout exception
                    } else if (t instanceof IOException) {
                        // Handle network exception
                    } else if (t instanceof HttpException) {
                        // Handle HTTP exception
                    } else {
                        // Handle other exceptions
                    }
                }
            });
        } catch (JsonSyntaxException e) {
            progressDialog.dismiss(); // Dismiss the progress dialog
        } catch (Exception e) {
            progressDialog.dismiss(); // Dismiss the progress dialog
        }

    }

    private void loadWorkWithCopyInfo(ProgressDialog progressDialog ) {
        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<TourPlanWorkedWith> call = service.GetTourPlanForWorkedwith(selectedVisEmp, datez);

            call.enqueue(new Callback<TourPlanWorkedWith>() {
                @Override
                public void onResponse(@NonNull Call<TourPlanWorkedWith> call, @NonNull Response<TourPlanWorkedWith> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        pbinding.tvMorning.setText(response.body().getTpMorning());
                        pbinding.tvEvening.setText(response.body().getEvMorning());
                    } else {
                        // Handle the case where response is not successful or body is null
                        // view.onError("Failed to get tour plan: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TourPlanWorkedWith> call, @NonNull Throwable t) {
                    if(t instanceof SocketTimeoutException) {
                        progressDialog.dismiss(); // Dismiss the progress dialog
                    } else if(t instanceof IOException) {
                        progressDialog.dismiss(); // Dismiss the progress dialog
                    } else if(t instanceof HttpException) {
                        progressDialog.dismiss(); // Dismiss the progress dialog
                    } else {
                        progressDialog.dismiss(); // Dismiss the progress dialog
                    }
                }
            });
        } catch (JsonSyntaxException e) {
            progressDialog.dismiss(); // Dismiss the progress dialog
        } catch (Exception e) {
            progressDialog.dismiss(); // Dismiss the progress dialog
        }


        String   morEve="";

        if (pbinding.rbMorning.isChecked()) {
            morEve="Morning";
        } else {
            morEve="Evening";
        }
        selectedGrpId=     0;

        selectedRegionIdEdit=     0;
        selectedAreaIdEdit=   0;
        selectedTeriIdEdit=   0;
        selectedSTeriEdit=    0;
        selectedMarketEdit=    0;

          selectedRegionIdEndEdit=0;
          selectedTeriIdEndEdit=0;
          selectedAreaIdEndEdit=0;
          selectedSTeriEndEdit=0;
          selectedMarketEndEdit=0;

        mpresenter.GetGroupLocal();
        mpresenter.GetRegionLocal(0);
        mpresenter.GetAreaLocal(0);
        mpresenter.GetTeritoryLocal(0);
        mpresenter.GetSTeritoryLocal(0);
        mpresenter.GetMarketLocal(0);

        mpresenter.GetRegionLocalEnd(0);
        mpresenter.GetAreaLocalEnd(0);
        mpresenter.GetTeritoryLocalEnd(0);
        mpresenter.GetSTeritoryLocalEnd(0);
        mpresenter.GetMarketLocalEnd(0);
        if (selectedVisEmp!=empId){
            try {
                TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
                Call<TourPlanViewModel> call = service.GetTourPlanForWorkedwithCopy(selectedVisEmp, datez, morEve);

                call.enqueue(new Callback<TourPlanViewModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TourPlanViewModel> call, @NonNull Response<TourPlanViewModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            TourPlanViewModel tourPlan = response.body();

                            if (tourPlan.getStarttime() != null && tourPlan.getEndtime() != null) {
                                pbinding.etStartTime.setText(tourPlan.getStarttime());
                                pbinding.etEndTime.setText(tourPlan.getEndtime());
                                selectedGrpId=     tourPlan.getGroupId();
                                selectedRegionIdEdit=     tourPlan.getRegionId();
                                selectedAreaIdEdit=     tourPlan.getAreaId();
                                selectedTeriIdEdit=     tourPlan.getTerritoryId();
                                selectedSTeriEdit=     tourPlan.getSubTerritoryId();
                                selectedMarketEdit=     tourPlan.getMarketId();


                                selectedRegionIdEndEdit=tourPlan.getRegionIdEnd();
                                selectedTeriIdEndEdit=tourPlan.getTerritoryIdEnd();
                                selectedAreaIdEndEdit=tourPlan.getAreaIdEnd();
                                selectedSTeriEndEdit=tourPlan.getSubTerritoryIdEnd();
                                selectedMarketEndEdit=tourPlan.getMarketIdEnd();

                                mpresenter.GetGroupLocal();
                                mpresenter.GetRegionLocalEdit(selectedRegionIdEdit);
                                mpresenter.GetAreaLocalEdit(selectedAreaIdEdit);
                                mpresenter.GetTeritoryLocalEdit(selectedTeriIdEdit);
                                mpresenter.GetSTeritoryLocalEdit(selectedSTeriEdit);
                                mpresenter.GetMarketLocalEndEdit(selectedMarketEdit);






//
                              mpresenter.GetRegionLocalEndEdit(selectedRegionIdEndEdit);
                               mpresenter.GetAreaLocalEndEdit(selectedAreaIdEndEdit);
                              mpresenter.GetTeritoryLocalEditEndEdit(selectedTeriIdEndEdit);
                              mpresenter.GetSTeritoryLocalEditEnd(selectedSTeriEndEdit);
//
                               mpresenter.GetMarketLocalEndEdit(selectedMarketEndEdit);
//
                            } else {
//                                mpresenter.GetGroupLocal();
//                                mpresenter.GetRegionLocal(0);
//                                mpresenter.GetAreaLocal(0);
//                                mpresenter.GetTeritoryLocal(0);
//                                mpresenter.GetSTeritoryLocal(0);
//                                mpresenter.GetMarketLocal(0);
//
//
//                                mpresenter.GetRegionLocalEnd(0);
//                                mpresenter.GetAreaLocalEnd(0);
//                                mpresenter.GetTeritoryLocalEnd(0);
//                                mpresenter.GetSTeritoryLocalEnd(0);
//                                mpresenter.GetMarketLocalEnd(0);
                            }
                        } else {
                            // Handle the case where response is not successful or body is null
                            // view.onError("Failed to get tour plan: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TourPlanViewModel> call, @NonNull Throwable t) {
                        progressDialog.dismiss(); // Dismiss the progress dialog
                        if (t instanceof SocketTimeoutException) {
                            // Handle timeout exception
                        } else if (t instanceof IOException) {
                            // Handle network exception
                        } else if (t instanceof HttpException) {
                            // Handle HTTP exception
                        } else {
                            // Handle other exceptions
                        }
                    }
                });
            } catch (JsonSyntaxException e) {
                progressDialog.dismiss(); // Dismiss the progress dialog
            } catch (Exception e) {
                progressDialog.dismiss(); // Dismiss the progress dialog
            }

        }
    }

    private void loadEditInfoById(int id ) {
        try {
//            TourApiCall service = RetrofitClientInstanceTP.getRetrofitInstance().create(TourApiCall.class);
//            Call<TourPlanWorkedWith> call = service.GetTourPlanEditbyId(id, datez);
//
//            call.enqueue(new Callback<TourPlanWorkedWith>() {
//                @Override
//                public void onResponse(@NonNull Call<TourPlanWorkedWith> call, @NonNull Response<TourPlanWorkedWith> response) {
//                    if(response.isSuccessful() && response.body() != null) {
//                        pbinding.tvMorning.setText(response.body().getTpMorning());
//                        pbinding.tvEvening.setText(response.body().getEvMorning());
//                    } else {
//                        // Handle the case where response is not successful or body is null
//                        // view.onError("Failed to get tour plan: " + response.message());
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<TourPlanWorkedWith> call, @NonNull Throwable t) {
//                    if(t instanceof SocketTimeoutException) {
//                      //  progressDialog.dismiss(); // Dismiss the progress dialog
//                    } else if(t instanceof IOException) {
//                       // progressDialog.dismiss(); // Dismiss the progress dialog
//                    } else if(t instanceof HttpException) {
//                       // progressDialog.dismiss(); // Dismiss the progress dialog
//                    } else {
//                       // progressDialog.dismiss(); // Dismiss the progress dialog
//                    }
//                }
//            });
//        } catch (JsonSyntaxException e) {
//            //progressDialog.dismiss(); // Dismiss the progress dialog
//        } catch (Exception e) {
//           // progressDialog.dismiss(); // Dismiss the progress dialog
//        }


        String   morEve="";

//        if (pbinding.rbMorning.isChecked()) {
//            morEve="Morning";
//        } else {
//            morEve="Evening";
//        }
            e_selectedGrpId=     0;

            e_selectedRegionIdEdit=     0;
            e_selectedAreaIdEdit=   0;
            e_selectedTeriIdEdit=   0;
            e_selectedSTeriEdit=    0;
            e_selectedMarketEdit=    0;

            e_selectedRegionIdEndEdit=0;
            e_selectedTeriIdEndEdit=0;
            e_selectedAreaIdEndEdit=0;
            e_selectedSTeriEndEdit=0;
            e_selectedMarketEndEdit=0;



            try {
                TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
                Call<TourPlanViewModel> call = service.GetTourPlanEditbyId(id, datez);

                call.enqueue(new Callback<TourPlanViewModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TourPlanViewModel> call, @NonNull Response<TourPlanViewModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            TourPlanViewModel tourPlan = response.body();

                         //   if (tourPlan.getStarttime() != null && tourPlan.getEndtime() != null)
                            {
                                String IsMarketVisit = tourPlan.getIsMarketVisit();  // Assuming this is a String, "true" or "false"
                                String IsOtherVisit = tourPlan.getIsOtherVisit();    // Assuming this is a String, "true" or "false"

// Convert to boolean using Boolean.parseBoolean()
                                boolean isMarketVisit = Boolean.parseBoolean(IsMarketVisit);  // Directly parse the String
                                boolean isOtherVisit = Boolean.parseBoolean(IsOtherVisit);


                                if (isMarketVisit) {
                                    pbinding.rbMarketVisit.setChecked(true);
                                    pbinding.rbOtherVisit.setChecked(false);
                                    e_selectedTPP=tourPlan.getTPId();
                                    LoadTourPurposeForMarketVisitNew(pbinding.tourPlanPurposeSpinner,e_selectedTPP);
                                } else if (isOtherVisit) {
                                    pbinding.rbOtherVisit.setChecked(true);
                                    pbinding.rbMarketVisit.setChecked(false);
                                    e_selectedTPPOther=tourPlan.getTPId();
                                    LoadTourPurposeForOtherVisitNew(pbinding.tourPlanPurposeOtherSpinner,e_selectedTPPOther);

                                }





                                String  Mor = tourPlan.getIsMorning();  // Assuming this is a String, "true" or "false"
                                String  Eve = tourPlan.getIsEvening();    // Assuming this is a String, "true" or "false"

// Convert to boolean using Boolean.parseBoolean()
                                boolean isMor = Boolean.parseBoolean(Mor);  // Directly parse the String
                                boolean isEve = Boolean.parseBoolean(Eve);



                                if (isMor) {
                                    pbinding.rbMorning.setChecked(true);
                                    pbinding.rbEvening.setChecked(false);
                                } else if (isEve) {
                                    pbinding.rbEvening.setChecked(true);
                                    pbinding.rbMorning.setChecked(false);
                                }
                                pbinding.ObjectiveTxt.setText(tourPlan.getObjective());




//                                LoadTourPurposeForMarketVisit(pbinding.tourPlanPurposeSpinner);
//                                LoadTourPurposeForOtherVisit(pbinding.tourPlanPurposeOtherSpinner);
//                                pbinding.rbMarketVisit.isChecked();
          try {
              pbinding.etStartTime.setText(tourPlan.getStarttime());
          }catch (Exception ex){

          }
//
//
                                try {
                           pbinding.etEndTime.setText(tourPlan.getEndtime());
                                }catch (Exception ex){

                                }
                                e_selectedGrpId=     tourPlan.getGroupId();
                                e_selectedRegionIdEdit=     tourPlan.getRegionId();
                                e_selectedAreaIdEdit=     tourPlan.getAreaId();
                                e_selectedTeriIdEdit=     tourPlan.getTerritoryId();
                                e_selectedSTeriEdit=     tourPlan.getSubTerritoryId();
                                e_selectedMarketEdit=     tourPlan.getMarketId();


                                e_selectedRegionIdEndEdit=tourPlan.getRegionIdEnd();
                                e_selectedTeriIdEndEdit=tourPlan.getTerritoryIdEnd();
                                e_selectedAreaIdEndEdit=tourPlan.getAreaIdEnd();
                                e_selectedSTeriEndEdit=tourPlan.getSubTerritoryIdEnd();
                                e_selectedMarketEndEdit=tourPlan.getMarketIdEnd();


//                                vArea(areaList);  // Passing areaList populated with area data
//                                vTeritory(territoryList);  // Passing territoryList populated with territory data
//                                vSTeritory(subTerritoryList);

                                mpresenter.GetGroupLocal();
                                mpresenter.GetRegionLocal(0);
                                mpresenter.GetAreaLocal(0);
                                mpresenter.GetTeritoryLocal(0);
                                mpresenter.GetSTeritoryLocal(0);
                                mpresenter.GetMarketLocal(0);
//
                                mpresenter.GetRegionLocalEnd(0);
                                mpresenter.GetAreaLocalEnd(0);
                                mpresenter.GetTeritoryLocalEnd(0);
                                mpresenter.GetSTeritoryLocalEnd(0);
                                mpresenter.GetMarketLocalEnd(0);



                                // Assuming you already have your Retrofit instance and service set up

                                try {
                                    // Make the call to fetch the list of markets
                                    Call<List<ModelMarket>> callOtherVisit = service.GetOtherMarketVisitListTourPlanEditbyId(id, datez);

                                    // Call the API asynchronously
                                    callOtherVisit.enqueue(new Callback<List<ModelMarket>>() {
                                        @Override
                                        public void onResponse(@NonNull Call<List<ModelMarket>> call, @NonNull Response<List<ModelMarket>> response) {
                                            if (response.isSuccessful() && response.body() != null) {
                                                // Handle the successful response and get the list of markets
                                                List<ModelMarket> aVisitedMarketList = response.body(); // Populate this list
                                                if (aMarketListAll == null) {
                                                    aMarketListAll = new ArrayList<>();
                                                }

                                                // Add aVisitedMarketList to aMarketListAll (assuming you want to combine both lists)
                                                aMarketListAll.addAll(aVisitedMarketList);

                                                finalMarketList.addAll(aVisitedMarketList);
                                                chMarketList.addAll(aVisitedMarketList);
                                                // Now set the adapter to display the markets in the ListView
                                                ArrayAdapter<ModelMarket> marketAdapter = new ArrayAdapter<>(requireActivity(), R.layout.lv_dcrbrand, R.id.dcrbrand, aVisitedMarketList);
                                                pbinding.othermarketListLv.setAdapter(marketAdapter);

                                                // Optionally update the ListView size
                                                LviewHelper.getListViewSize(pbinding.othermarketListLv);
                                            } else {
                                                // Handle the case where the response is unsuccessful or body is null
                                                Log.e("APIError", "Response was not successful or body was null.");
                                             // --  pbinding.progressBar.setVisibility(View.GONE);
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<List<ModelMarket>> call, @NonNull Throwable t) {
                                            // Handle the error (e.g., network error, no connection, etc.)
                                            Log.e("APIError", "Error: " + t.getMessage());
                                       //    -- pbinding.progressBar.setVisibility(View.GONE);
                                        }
                                    });

                                } catch (Exception e) {
                                    // Handle exceptions such as network failures or other unexpected errors
                                    Log.e("APIError", "Exception occurred: " + e.getMessage());
                                   // --pbinding.progressBar.setVisibility(View.GONE);
                                }



                                // If the list of visited markets is not null, process it

                           //     mpresenter.GetGroupLocal();
//                                mpresenter.GetRegionLocal(e_selectedGrpId);
//                                mpresenter.GetAreaLocal(e_selectedRegionIdEdit);
//                                mpresenter.GetTeritoryLocal(e_selectedAreaIdEdit);
//                                mpresenter.GetSTeritoryLocal(e_selectedTeriIdEdit);
//                                mpresenter.GetMarketLocal(e_selectedSTeriEdit);
//                                mpresenter.GetRegionLocalEdit(e_selectedRegionIdEdit);
//                                mpresenter.GetAreaLocalEdit(e_selectedAreaIdEdit);
//                                mpresenter.GetTeritoryLocalEdit(e_selectedTeriIdEdit);
//                                mpresenter.GetSTeritoryLocalEdit(e_selectedSTeriEdit);
//                                mpresenter.GetMarketLocalEndEdit(e_selectedMarketEdit);
//
//
//
//
//
//
////
//                              mpresenter.GetRegionLocalEndEdit(e_selectedRegionIdEndEdit);
//                               mpresenter.GetAreaLocalEndEdit(e_selectedAreaIdEndEdit);
//                              mpresenter.GetTeritoryLocalEditEndEdit(e_selectedTeriIdEndEdit);
//                              mpresenter.GetSTeritoryLocalEditEnd(e_selectedSTeriEndEdit);
////
//                               mpresenter.GetMarketLocalEndEdit(e_selectedMarketEndEdit);


//
                            }

                            //else {
//                                mpresenter.GetGroupLocal();
//                                mpresenter.GetRegionLocal(0);
//                                mpresenter.GetAreaLocal(0);
//                                mpresenter.GetTeritoryLocal(0);
//                                mpresenter.GetSTeritoryLocal(0);
//                                mpresenter.GetMarketLocal(0);
//
//
//                                mpresenter.GetRegionLocalEnd(0);
//                                mpresenter.GetAreaLocalEnd(0);
//                                mpresenter.GetTeritoryLocalEnd(0);
//                                mpresenter.GetSTeritoryLocalEnd(0);
//                                mpresenter.GetMarketLocalEnd(0);
                           // }
                        } else {
                            // Handle the case where response is not successful or body is null
                            // view.onError("Failed to get tour plan: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TourPlanViewModel> call, @NonNull Throwable t) {
                //        progressDialog.dismiss(); // Dismiss the progress dialog
                        if (t instanceof SocketTimeoutException) {
                            // Handle timeout exception
                        } else if (t instanceof IOException) {
                            // Handle network exception
                        } else if (t instanceof HttpException) {
                            // Handle HTTP exception
                        } else {
                            // Handle other exceptions
                        }
                    }
                });
            } catch (JsonSyntaxException e) {
               // progressDialog.dismiss(); // Dismiss the progress dialog
            } catch (Exception e) {
               // progressDialog.dismiss(); // Dismiss the progress dialog
            }





        } catch (Exception e) {
            // progressDialog.dismiss(); // Dismiss the progress dialog
       }
    }

    private void setSpinnerSelectionByTerritoryId(Spinner spinner, int territoryId) {
        ArrayAdapter<ModelTeritorry> adapter = (ArrayAdapter<ModelTeritorry>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).getTerritoryId() == territoryId) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    private void setSpinnerSelectionByAreaId(Spinner spinner, int areaId) {
        ArrayAdapter<ModelArea> adapter = (ArrayAdapter<ModelArea>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).getAreaId() == areaId) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    private void setSpinnerSelectionByRegionId(Spinner spinner, int regionId) {
        if (spinner != null && spinner.getAdapter() instanceof ArrayAdapter) {
            ArrayAdapter<ModelRegion> adapter = (ArrayAdapter<ModelRegion>) spinner.getAdapter();
            if (adapter != null) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    ModelRegion item = adapter.getItem(i);
                    if (item != null && item.getRegionId() == regionId) {
                        spinner.setSelection(i);
                        return; // Found the item, exit loop
                    }
                }
            }
        }
        // Handle case where spinner or adapter is null, or regionId is not found
        // Optionally, you can log an error or handle this case according to your app's logic
    }

    public void LoadTourPurpose(Spinner setSpiner) {
        tppList = new ArrayList<>();
        tppList = dbCrudHelper.getTPPList_SQLite();
        try {
            if (tppList != null) {
                ArrayAdapter<TourPurposeViewModel> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, tppList);
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
        }
    }

    public void LoadTourPurposeForMarketVisit(Spinner setSpiner) {
        tppList = new ArrayList<>();
        tppList = dbCrudHelper.getTPPListForMarketVisit_SQLite();
        try {
            if (tppList != null) {
                ArrayAdapter<TourPurposeViewModel> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, tppList);
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
        }
    }
    public void LoadTourPurposeForMarketVisitNew(Spinner setSpiner, int e_selectedTPP) {
        tppList = new ArrayList<>();
        tppList = dbCrudHelper.getTPPListForMarketVisit_SQLite(); // Load the data
        try {
            if (tppList != null) {
                ArrayAdapter<TourPurposeViewModel> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, tppList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                setSpiner.setAdapter(dataAdapter);
            }

            if (e_selectedTPP > 0 && tppList != null) {
                for (int i = 0; i < tppList.size(); i++) {
                    // Compare the ID of the tour purpose with the selectedTPP value using primitive comparison (==)
                    if (tppList.get(i).getTPId() == e_selectedTPP) {
                        setSpiner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }

            setSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TourPurposeViewModel tpp = (TourPurposeViewModel) parent.getSelectedItem();
                    selectedTPP = tpp.getTPId(); // Set the selected TPId
                    selectedTPPName = tpp.getTPName(); // Set the selected TPName
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle if nothing is selected
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace(); // Optionally log the exception for debugging
        }
    }

    public void LoadTourPurposeForOtherVisit(Spinner setSpiner) {
        tppList = new ArrayList<>();
        tppList = dbCrudHelper.getTPPListForOtherVisit_SQLite();
        try {
            if (tppList != null) {
                ArrayAdapter<TourPurposeViewModel> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, tppList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                setSpiner.setAdapter(dataAdapter);
            }
            setSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TourPurposeViewModel tpp = (TourPurposeViewModel) parent.getSelectedItem();
                    selectedTPPOther = tpp.getTPId();
                    selectedTPPNameOther = tpp.getTPName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception exception) {
        }
    }

    public void LoadTourPurposeForOtherVisitNew(Spinner setSpiner , int e_selectedTPP) {
        tppList = new ArrayList<>();
        tppList = dbCrudHelper.getTPPListForOtherVisit_SQLite();
        try {
            if (tppList != null) {
                ArrayAdapter<TourPurposeViewModel> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, tppList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                setSpiner.setAdapter(dataAdapter);
            }

            if (e_selectedTPP > 0 && tppList != null) {
                for (int i = 0; i < tppList.size(); i++) {
                    // Compare the ID of the tour purpose with the selectedTPP value using primitive comparison (==)
                    if (tppList.get(i).getTPId() == e_selectedTPP) {
                        setSpiner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }
            setSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TourPurposeViewModel tpp = (TourPurposeViewModel) parent.getSelectedItem();
                    selectedTPPOther = tpp.getTPId();
                    selectedTPPNameOther = tpp.getTPName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception exception) {
        }
    }

    //Customer List CheckboxView
    public void popup_Customer() {
        popupCustomer = new Dialog(requireActivity());
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

        try {
            itemChkAdapter = new CustomerItemChkAdapter(requireActivity(), aCustomerList, this);
            rv_customer.setLayoutManager(new LinearLayoutManager(requireActivity()));
            rv_customer.setAdapter(itemChkAdapter);
            rv_customer.scrollToPosition(0);
            itemChkAdapter.notifyDataSetChanged();

            done_cust.setOnClickListener(v -> {
                popupCustomer.dismiss();
                Gson gson = new Gson();
                String data = gson.toJson(chMarketList);
                ArrayAdapter<ModelCustomer> custAd = new ArrayAdapter<>(requireActivity(), R.layout.lv_dcrbrand, R.id.dcrbrand, chkCustomerList);
                pbinding.custListLv.setAdapter(custAd);
                LviewHelper.getListViewSize(pbinding.custListLv);

            });
            cancel_cust.setOnClickListener(v -> {
                chkCustomerList.clear();
                popupCustomer.dismiss();
            });
            search();
        } catch (Exception exception) {
            //  exception.printStackTrace();
        }
    }
    public void popup_OtherMarket() {
        popupCustomer = new Dialog(requireActivity());
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
        title.setText("Select Market");

        try {
            itemMarketChkAdapter = new MarketItemChkAdapter(requireActivity(), aMarketListAll, this);
            rv_customer.setLayoutManager(new LinearLayoutManager(requireActivity()));
            rv_customer.setAdapter(itemMarketChkAdapter);
            rv_customer.scrollToPosition(0);
            itemMarketChkAdapter.notifyDataSetChanged();


            pbinding.othermarketListLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    // Show a confirmation dialog before deleting
                    new AlertDialog.Builder(requireActivity())
                            .setTitle("Delete Market")
                            .setMessage("Are you sure you want to delete this Market?")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                // Remove the selected item from the chMarketList
                                chMarketList.remove(position);
                                aMarketListAll.remove(position);


                        finalMarketList.remove(position);


                                // Serialize the updated list back to JSON (optional)
                                Gson gson = new Gson();
                                String data = gson.toJson(chMarketList);

                                // Update the adapter and notify about the dataset change
                                ArrayAdapter<ModelMarket> custAd = new ArrayAdapter<>(requireActivity(), R.layout.lv_dcrbrand, R.id.dcrbrand, chMarketList);
                                pbinding.othermarketListLv.setAdapter(custAd);
                                custAd.notifyDataSetChanged();

                                // Optionally, update ListView size if dynamic sizing is required
                                LviewHelper.getListViewSize(pbinding.othermarketListLv);
                                SnackBarManagement._warning_CustomMessage(pbinding.master, "Market removed"
                                );
                                // Show a confirmation toast
                              //  Toast.makeText(requireActivity(), "Market removed", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();

                    return true; // Return true to indicate the long press is handled
                }
            });

            done_cust.setOnClickListener(v -> {



                    popupCustomer.dismiss();
                    Gson gson = new Gson();
                    String data = gson.toJson(chMarketList);
                    ArrayAdapter<ModelMarket> custAd = new ArrayAdapter<>(requireActivity(), R.layout.lv_dcrbrand, R.id.dcrbrand, chMarketList);
                    pbinding.othermarketListLv.setAdapter(custAd);
                    LviewHelper.getListViewSize(pbinding.othermarketListLv);




            });
            cancel_cust.setOnClickListener(v -> {
                chMarketList.clear();
                popupCustomer.dismiss();
            });
            searchMarket();
        } catch (Exception exception) {
            //  exception.printStackTrace();
        }
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
    private void searchMarket() {
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
                filterMarket(editable.toString());
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

    private void filterMarket(String word) {
        List<ModelMarket> filterwordlist = new ArrayList<>();
        for (ModelMarket words : aMarketListAll) {
            if (words.getMarketName() == null) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (words.getMarketName().toLowerCase().contains(word.toLowerCase())) {
                filterwordlist.add(words);
            }
        }
        itemMarketChkAdapter.filterListFun(filterwordlist);
        itemMarketChkAdapter.notifyDataSetChanged();
    }

    public void SetInRecyclerviewData(List<TourPlanViewModel> aList, int i) {
        adapter = new TPDtlsAdapter(requireActivity(), aList, this, isFinalSubmit, i);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        binding.rvNewadddoc.setLayoutManager(layoutManager);
        binding.rvNewadddoc.setHasFixedSize(true);
        binding.rvNewadddoc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ckdItemName(List<ModelCustomer> st, int Pos) {
        if (st != null) {
            chkCustomerList = new ArrayList<>();
            finalCustomerList = new ArrayList<>();
            for (int i = 0; st.size() > i; i++) {
                ModelCustomer ct = new ModelCustomer();
                ct.setCustomerName(st.get(i).getCustomerName());
                ct.setCustomerMasterId(st.get(i).getCustomerMasterId());
                chkCustomerList.add(ct);
                finalCustomerList.add(ct);
            }
            // finalCustomerList = st;
        } else {
            adapter.notifyItemRangeRemoved(0, st.size());
        }
    }

    @Override
    public void unckdItemName(List<ModelCustomer> st, int Pos) {

    }

    @Override
    public void OnTourPlanDataGet(List<TourPlanViewModel> aList) {

    }

    @Override
    public void OnTourPlanDailyDataGet(List<TourPlanViewModel> aList, boolean isFinalSubmit) {
        Gson gson = new Gson();
        String data = gson.toJson(aList);
        System.out.println("tourplan get" + data);
        tpl = aList;
        try {
            SetInRecyclerviewData(tpl, 1);
        } catch (Exception exception) {
        }
    }


    @Override
    public void OnFailour(String msg) {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Warning!!")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // dismiss();
                    }

                }).setCancelable(false).show();
    }

    @Override
    public void OnSuccessTPPDay(String msg) {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Success")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dismiss();
                        mListener.DataSaved(Integer.parseInt(month), Integer.parseInt(year), empId, "test");
                    }

                }).setCancelable(false).show();
    }

    @Override
    public void deleteItemFromServer(int pos, int id) {
        try {
            TourApiCall service = RetrofitClientInstance.getRetrofitInstance().create(TourApiCall.class);
            Call<ResultInfo> call = service.DeleteTourPlanData(id);
            call.enqueue(new Callback<ResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResultInfo> call, @NonNull Response<ResultInfo> response) {
                    ResultInfo info = response.body();
                    if (info.getSuccess() == true) {

                        new AlertDialog.Builder(requireActivity())
                                .setTitle("Success")
                                .setMessage("Tour Plan Deleted Successfully")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        tpl.remove(pos);
                                        for (int i = 0; i < tpl.size(); i++) {
                                            tpl.get(i).setSerialNo(i + 1);
                                        }
                                        adapter.notifyDataSetChanged();
                                        mListener.DataSaved(Integer.parseInt(month), Integer.parseInt(year), empId, "test");
                                    }

                                }).setCancelable(false).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResultInfo> call, @NonNull Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(), t.getMessage());
                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(), t.getMessage());
                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            SnackBarManagement._error_CustomMessage(binding.getRoot(), str);
        }
    }

    @Override
    public void deleteItem(int pos) {
        tpl.remove(pos);
        try {
            new AlertDialog.Builder(requireActivity())
                    .setTitle("Success")
                    .setMessage("Tour Plan Deleted Successfully")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            for (int i = 0; i < tpl.size(); i++) {
                                tpl.get(i).setSerialNo(i + 1);
                            }
                            mListener.DataSaved(Integer.parseInt(month), Integer.parseInt(year), empId, "test");
                            adapter.notifyDataSetChanged();
                        }

                    }).setCancelable(false).show();

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            SnackBarManagement._error_CustomMessage(binding.getRoot(), str);
        }
    }
    @Override
    public void editTourPlanInfo(int pos, int id)
    {


        Editpopup_tpp(datez);


//        try{
//            String ShiftInfo="";
//            Context context = getContext();
//            ShiftInfo="Morning";
//            ProgressDialog progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//          //  GetStartEndTimeforShift(progressDialog,ShiftInfo);
//            progressDialog.dismiss();
//        }catch (Exception ex){
//
//        }

         // Dismiss the progress dialog
        loadEditInfoById(id);
    }
    @Override
    public void editItem(int pos, int id, int rid, int aid, int tid, int stid, int mid, String region, String area, String territory, String subTerritory, String market) {
        onLoadCustomerReceived(dbCrudHelper.getCustomerList_SQLite());
        aCustomerList = dbCrudHelper.getCustomerList_SQLite();
        popupTPPEdit = new Dialog(requireActivity());
        popupTPPEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupTPPEdit.setCancelable(true);
        pbindingEdit = PopTourplanEditMarketwiseBinding.inflate(LayoutInflater.from(requireActivity()));
        popupTPPEdit.setContentView(pbindingEdit.getRoot());
        popupTPPEdit.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //Role wise spinner populate
        try {
            switch (roleType) {
                case "MIO":
                    pbindingEdit.regiondiv.setVisibility(View.GONE);
                    pbindingEdit.areadiv.setVisibility(View.GONE);
                    mpresenter.GetTeritoryLocal(0);
                    mpresenter.GetTeritoryLocalEnd(0);
                    break;
                case "AM":
                    pbindingEdit.regiondiv.setVisibility(View.GONE);
                    pbindingEdit.areadiv.setVisibility(View.VISIBLE);
                    mpresenter.GetAreaLocal(0);
                    mpresenter.GetAreaLocalEnd(0);
                    break;
                case "DZSM":
                case "NSM":
                case "Admin":
                    pbindingEdit.regiondiv.setVisibility(View.VISIBLE);
                    pbindingEdit.areadiv.setVisibility(View.VISIBLE);
                    mpresenter.GetGroupLocal();
                    mpresenter.GetRegionLocalEdit(0);
                    mpresenter.GetRegionLocal(0);
                    mpresenter.GetRegionLocalEnd(0);

                    mpresenter.GetAreaLocal(0);
                    mpresenter.GetAreaLocalEnd(0);
                    mpresenter.GetTeritoryLocal(0);
                    mpresenter.GetTeritoryLocalEnd(0);
                    mpresenter.GetSTeritoryLocal(0);
                    mpresenter.GetSTeritoryLocalEnd(0);
                    mpresenter.GetMarketLocal(0);
                    mpresenter.GetMarketLocalEnd(0);
                    break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (region != null) {
            edit_dName = region;
            pbindingEdit.regionSpinnerE.setSelection(getIndex(pbindingEdit.regionSpinnerE, edit_dName));

            ModelRegion rtype = (ModelRegion) pbindingEdit.regionSpinnerE.getSelectedItem();
            if (!String.valueOf(rid).equals("")) {
                mpresenter.GetAreaLocal(rid);
                mpresenter.GetAreaLocalEnd(rid);
            } else {
                mpresenter.GetAreaLocal(rtype.getRegionId());
                mpresenter.GetAreaLocalEnd(rtype.getRegionId());
            }
        } else {
        }
        if (area != null) {
            edit_dName = area;
            pbindingEdit.areaSpinnerE.setSelection(getIndex(pbindingEdit.areaSpinnerE, edit_dName));
            ModelArea areaType = (ModelArea) pbindingEdit.areaSpinnerE.getSelectedItem();
            if (!String.valueOf(aid).equals("")) {
                mpresenter.GetTeritoryLocal(aid);
                mpresenter.GetTeritoryLocalEnd(aid);
            } else {
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
            ModelTeritorry teritorryType = (ModelTeritorry) pbindingEdit.territorySpinnerE.getSelectedItem();
            if (!String.valueOf(tid).equals("")) {
                mpresenter.GetSTeritoryLocal(tid);
                mpresenter.GetSTeritoryLocalEnd(tid);
            } else {
                mpresenter.GetSTeritoryLocal(teritorryType.getTerritoryId());
                mpresenter.GetSTeritoryLocalEnd(teritorryType.getTerritoryId());
            }

        } else {
        }
        if (subTerritory != null) {
            edit_dName = subTerritory;
            pbindingEdit.sterritorySpinnerE.setSelection(getIndex(pbindingEdit.sterritorySpinnerE, edit_dName));
            mpresenter.GetMarketLocal(stid);
            mpresenter.GetMarketLocalEnd(stid);

        } else {
        }

        if (market != null) {
            edit_dName = market;
            pbindingEdit.marketSpinnerE.setSelection(getIndex(pbindingEdit.marketSpinnerE, edit_dName));
        } else {
        }

        pbindingEdit.tourDate.setText(datez);
        LoadTourPurpose(pbindingEdit.tourPlanPurposeSpinnerE);
        pbindingEdit.custAdd.setOnClickListener(v -> {
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
                SetInRecyclerviewData(tpl, 2);
                //adapter.notifyDataSetChanged();

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
                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, regionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.regionSpinner.setAdapter(dataAdapter);

            }



            if (e_selectedRegionIdEdit > 0 && regionList != null) {
                for (int i = 0; i < regionList.size(); i++) {
                    ModelRegion region = regionList.get(i);
                    // Compare region ID with e_selectedTeriIdEdit
                    if (region.getRegionId() == e_selectedRegionIdEdit) {
                        pbinding.regionSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }

            pbinding.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelRegion region = (ModelRegion) parent.getSelectedItem();
                    if (selectedAreaIdEdit>0){
                        selectedRegionId = region.getRegionId();
                        mpresenter.GetAreaLocalEdit(selectedAreaIdEdit);
                    }

//                    if (e_selectedAreaIdEdit>0){
//                        selectedRegionId = region.getRegionId();
//                        mpresenter.GetAreaLocalEdit(e_selectedAreaIdEdit);
//                    }


                    else {

                        selectedRegionId = region.getRegionId();
                        mpresenter.GetAreaLocal(selectedRegionId);
                    }
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
    public void vRegionEdit(List<ModelRegion> regionList) {
        try {
            if (regionList != null) {
                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, regionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.regionSpinner.setAdapter(dataAdapter);

            }
            pbinding.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelRegion region = (ModelRegion) parent.getSelectedItem();
                    if (selectedAreaIdEdit>0){
                        selectedRegionId = region.getRegionId();
                        mpresenter.GetAreaLocalEdit(selectedAreaIdEdit);
                    }
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
    public void vRegionEnd(List<ModelRegion> regionList) {
        try {
            if (regionList != null) {
                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, regionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                pbinding.regionendSpinner.setAdapter(dataAdapter);
            }



            if (e_selectedRegionIdEndEdit > 0 && regionList != null) {
                for (int i = 0; i < regionList.size(); i++) {
                    ModelRegion region = regionList.get(i);
                    // Compare region ID with e_selectedTeriIdEdit
                    if (region.getRegionId() == e_selectedRegionIdEndEdit) {
                        pbinding.regionendSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }

            pbinding.regionendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelRegion region = (ModelRegion) parent.getSelectedItem();


                        selectedRegionId = region.getRegionId();
                        mpresenter.GetAreaLocalEnd(selectedRegionId);

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
    public void vRegionEndEdit(List<ModelRegion> regionList) {
        try {
            if (regionList != null) {
                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, regionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                pbinding.regionendSpinner.setAdapter(dataAdapter);
            }


            if (e_selectedRegionIdEndEdit > 0 && regionList != null) {
                for (int i = 0; i < regionList.size(); i++) {
                    ModelRegion region = regionList.get(i);
                    // Compare region ID with e_selectedTeriIdEdit
                    if (region.getRegionId() == e_selectedRegionIdEndEdit) {
                        pbinding.regionendSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }

            pbinding.regionendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelRegion region = (ModelRegion) parent.getSelectedItem();

                    if (selectedAreaIdEndEdit>0){
                        selectedRegionId = region.getRegionId();
                        mpresenter.GetAreaLocalEndEdit(selectedAreaIdEndEdit);
                    }
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
    public void vArea(List<ModelArea> areaList) {
        try {
            if (areaList != null) {
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, areaList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.areaSpinner.setAdapter(dataAdapter);
                //pbindingEdit.areaSpinnerE.setAdapter(dataAdapter);
            }


            if (e_selectedAreaIdEdit > 0 && areaList != null) {
                for (int i = 0; i < areaList.size(); i++) {
                    ModelArea area = areaList.get(i);
                    // Compare region ID with e_selectedTeriIdEdit
                    if (area.getAreaId() == e_selectedAreaIdEdit) {
                        pbinding.areaSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }
            pbinding.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelArea area = (ModelArea) parent.getSelectedItem();
                    if(selectedTeriIdEdit>0){
                        selectedAreaId = area.getAreaId();
                        mpresenter.GetTeritoryLocalEdit(selectedTeriIdEdit);
                    }
//                    if(e_selectedTeriIdEdit>0){
//                        selectedAreaId = area.getAreaId();
//                        mpresenter.GetTeritoryLocalEdit(e_selectedTeriIdEdit);
//                    }
                    else {
                        selectedAreaId = area.getAreaId();
                        mpresenter.GetTeritoryLocal(selectedAreaId);
                    }

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
    public void vAreaEnd(List<ModelArea> areaList) {
        try {
              if (areaList != null) {
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, areaList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.areaendSpinner.setAdapter(dataAdapter);
                //pbindingEdit.areaSpinnerE.setAdapter(dataAdapter);
            }


            if (e_selectedAreaIdEndEdit > 0 && areaList != null) {
                for (int i = 0; i < areaList.size(); i++) {
                    ModelArea area = areaList.get(i);
                    // Compare region ID with e_selectedTeriIdEdit
                    if (area.getAreaId() == e_selectedAreaIdEndEdit) {
                        pbinding.areaendSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }

            pbinding.areaendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelArea area = (ModelArea) parent.getSelectedItem();

                        selectedAreaId = area.getAreaId();
                        mpresenter.GetTeritoryLocalEnd(selectedAreaId);

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
    public void vAreaEndEdit(List<ModelArea> areaList) {
        try {
              if (areaList != null) {
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, areaList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.areaendSpinner.setAdapter(dataAdapter);
                //pbindingEdit.areaSpinnerE.setAdapter(dataAdapter);
            }


            if (e_selectedAreaIdEndEdit > 0 && areaList != null) {
                for (int i = 0; i < areaList.size(); i++) {
                    ModelArea area = areaList.get(i);
                    // Compare region ID with e_selectedTeriIdEdit
                    if (area.getAreaId() == e_selectedAreaIdEndEdit) {
                        pbinding.areaendSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }
            pbinding.areaendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelArea area = (ModelArea) parent.getSelectedItem();
                    if(selectedTeriIdEndEdit>0){
                        selectedAreaId = area.getAreaId();
                        mpresenter.GetTeritoryLocalEditEndEdit(selectedTeriIdEndEdit);
                    }

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
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, teritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.territorySpinner.setAdapter(dataAdapter);

            }


            // Check if we are in edit mode (if e_selectedSTeriEdit is greater than 0)
            if (e_selectedTeriIdEdit > 0 && teritoryList != null) {
                for (int i = 0; i < teritoryList.size(); i++) {
                    ModelTeritorry teri = teritoryList.get(i);
                    // Compare territory ID to the selected territory ID in edit mode
                    if (teri.getTerritoryId() == e_selectedTeriIdEdit) {
                        pbinding.territorySpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }
            pbinding.territorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelTeritorry teri = (ModelTeritorry) parent.getSelectedItem();
                    if(selectedSTeriEdit>0){
                        selectedTeriId = teri.getTerritoryId();
                        aMarketListAll = dbCrudHelper.getMarketListAll_SQLiteByTerritoryId(String.valueOf(selectedTeriId));
                        mpresenter.GetSTeritoryLocalEdit(selectedSTeriEdit);
                    }


//                    if(e_selectedSTeriEdit>0){
//                        selectedTeriId = teri.getTerritoryId();
//                        aMarketListAll = dbCrudHelper.getMarketListAll_SQLiteByTerritoryId(String.valueOf(selectedTeriId));
//                        mpresenter.GetSTeritoryLocalEdit(e_selectedSTeriEdit);
//                    }
                    else {
                        selectedTeriId = teri.getTerritoryId();
                        mpresenter.GetSTeritoryLocal(selectedTeriId);
                        aMarketListAll = dbCrudHelper.getMarketListAll_SQLiteByTerritoryId(String.valueOf(selectedTeriId));
                    }


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
    public void vTeritoryEnd(List<ModelTeritorry> teritoryList) {
        try {
            if (teritoryList != null) {
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, teritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                pbinding.territoryendSpinner.setAdapter(dataAdapter);
            }

            if (e_selectedTeriIdEndEdit > 0 && teritoryList != null) {
                for (int i = 0; i < teritoryList.size(); i++) {
                    ModelTeritorry teri = teritoryList.get(i);
                    // Compare territory ID to the selected territory ID in edit mode
                    if (teri.getTerritoryId() == e_selectedTeriIdEndEdit) {
                        pbinding.territoryendSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }
            pbinding.territoryendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelTeritorry teri = (ModelTeritorry) parent.getSelectedItem();


                    selectedTeriIdEnd = teri.getTerritoryId();
                        mpresenter.GetSTeritoryLocalEnd(selectedTeriIdEnd);

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
    public void vTeritoryEndEdit(List<ModelTeritorry> teritoryList) {
        try {
            if (teritoryList != null) {
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, teritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                pbinding.territoryendSpinner.setAdapter(dataAdapter);
            }


            if (e_selectedTeriIdEndEdit > 0 && teritoryList != null) {
                for (int i = 0; i < teritoryList.size(); i++) {
                    ModelTeritorry teri = teritoryList.get(i);
                    // Compare territory ID to the selected territory ID in edit mode
                    if (teri.getTerritoryId() == e_selectedTeriIdEndEdit) {
                        pbinding.territoryendSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }

            pbinding.territoryendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelTeritorry teri = (ModelTeritorry) parent.getSelectedItem();

                    if(selectedSTeriEndEdit>0){
                        selectedTeriId = teri.getTerritoryId();
                        mpresenter.GetSTeritoryLocalEditEnd(selectedSTeriEndEdit);
                    }

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
                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, steritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.sterritorySpinner.setAdapter(dataAdapter);

            }


            // Check if we're in edit mode based on e_selectedSTeriEdit
            if (e_selectedSTeriEdit > 0 && steritoryList != null) {
                for (int i = 0; i < steritoryList.size(); i++) {
                    ModelSubTeritorry steri = steritoryList.get(i);
                    // Compare SubTerritoryId with e_selectedSTeriEdit
                    if (steri.getSubTerritoryId() == e_selectedSTeriEdit) {
                        pbinding.sterritorySpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
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
    public void vSTeritoryEnd(List<ModelSubTeritorry> steritoryList) {
        try {
            if (steritoryList != null) {
                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, steritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                pbinding.sterritoryendSpinner.setAdapter(dataAdapter);
            }

            // Check if we're in edit mode based on e_selectedSTeriEdit
            if (e_selectedSTeriEndEdit > 0 && steritoryList != null) {
                for (int i = 0; i < steritoryList.size(); i++) {
                    ModelSubTeritorry steri = steritoryList.get(i);
                    // Compare SubTerritoryId with e_selectedSTeriEdit
                    if (steri.getSubTerritoryId() == e_selectedSTeriEndEdit) {
                        pbinding.sterritoryendSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }
            pbinding.sterritoryendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelSubTeritorry steri = (ModelSubTeritorry) parent.getSelectedItem();


                    selectedSTeriEnd = steri.getSubTerritoryId();
                        mpresenter.GetMarketLocalEnd(selectedSTeriEnd);

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
    public void vSTeritoryEndEdit(List<ModelSubTeritorry> steritoryList) {
        try {
            if (steritoryList != null) {
                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, steritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                pbinding.sterritoryendSpinner.setAdapter(dataAdapter);
            }


            // Check if we're in edit mode based on e_selectedSTeriEdit
            if (e_selectedSTeriEndEdit > 0 && steritoryList != null) {
                for (int i = 0; i < steritoryList.size(); i++) {
                    ModelSubTeritorry steri = steritoryList.get(i);
                    // Compare SubTerritoryId with e_selectedSTeriEdit
                    if (steri.getSubTerritoryId() == e_selectedSTeriEndEdit) {
                        pbinding.sterritoryendSpinner.setSelection(i); // Set the correct item in the spinner
                        break;
                    }
                }
            }
            pbinding.sterritoryendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelSubTeritorry steri = (ModelSubTeritorry) parent.getSelectedItem();

                    if(selectedMarketEndEdit>0){
                        selectedSTeri = steri.getSubTerritoryId();
                        mpresenter.GetMarketLocalEndEdit(selectedMarketEndEdit);
                    }   if(e_selectedMarketEndEdit>0){
                        selectedSTeri = steri.getSubTerritoryId();
                        mpresenter.GetMarketLocalEndEdit(e_selectedMarketEndEdit);
                    }
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
                ArrayAdapter<ModelMarket> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, marketList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pbinding.marketSpinner.setAdapter(dataAdapter);
                pbinding.marketStartSpinner.setAdapter(dataAdapter);

            }

            // Check if we're in edit mode for marketSpinner
            if (e_selectedMarketEdit > 0 && marketList != null) {
                for (int i = 0; i < marketList.size(); i++) {
                    ModelMarket market = marketList.get(i);
                    // Compare market ID with e_selectedMarketEdit
                    if (market.getMarketId() == e_selectedMarketEdit) {
                        pbinding.marketSpinner.setSelection(i); // Set the correct item in the marketSpinner
                        pbinding.marketStartSpinner.setSelection(i); // Set the same item in the marketStartSpinner
                        break;
                    }
                }
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
            });     pbinding.marketStartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void vMarketEnd(List<ModelMarket> marketList) {
        try {
            if (marketList != null) {
                ArrayAdapter<ModelMarket> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, marketList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                pbinding.marketEndSpinner.setAdapter(dataAdapter);
            }

            if (e_selectedMarketEndEdit > 0 && marketList != null) {
                for (int i = 0; i < marketList.size(); i++) {
                    ModelMarket market = marketList.get(i);
                    // Compare market ID with e_selectedMarketEdit
                    if (market.getMarketId() == e_selectedMarketEndEdit) {
                        pbinding.marketEndSpinner.setSelection(i); // Set the correct item in the marketSpinner
                        pbinding.marketEndSpinner.setSelection(i); // Set the same item in the marketStartSpinner
                        break;
                    }
                }
            }

            pbinding.marketEndSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelMarket market = (ModelMarket) parent.getSelectedItem();
                    selectedMarketEnd = market.getMarketId();
                    selectedMarketNameEnd = market.getMarketName();

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
    public void vMarketEndEdit(List<ModelMarket> marketList) {
        try {
            if (marketList != null) {
                ArrayAdapter<ModelMarket> dataAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, marketList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                pbinding.marketEndSpinner.setAdapter(dataAdapter);
            }

            // Check if we're in edit mode for marketSpinner
            if (e_selectedMarketEndEdit > 0 && marketList != null) {
                for (int i = 0; i < marketList.size(); i++) {
                    ModelMarket market = marketList.get(i);
                    // Compare market ID with e_selectedMarketEdit
                    if (market.getMarketId() == e_selectedMarketEndEdit) {
                        pbinding.marketEndSpinner.setSelection(i); // Set the correct item in the marketSpinner
                        pbinding.marketEndSpinner.setSelection(i); // Set the same item in the marketStartSpinner
                        break;
                    }
                }
            }

            pbinding.marketEndSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelMarket market = (ModelMarket) parent.getSelectedItem();
                    selectedMarketEnd = market.getMarketId();
                    selectedMarketNameEnd = market.getMarketName();

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
    public void ckdItemMarketName(List<ModelMarket> st, int Pos) {
        if (st != null) {
            chMarketList = new ArrayList<>();
            finalMarketList = new ArrayList<>();
            for (int i = 0; st.size() > i; i++) {
                ModelMarket ct = new ModelMarket();
                ct.setMarketName(st.get(i).getMarketName());
                ct.setMarketId(st.get(i).getMarketId());
                chMarketList.add(ct);
                finalMarketList.add(ct);
            }
            // finalCustomerList = st;
        } else {
            adapter.notifyItemRangeRemoved(0, st.size());
        }
    }

    @Override
    public void unckdItemMarketName(List<ModelMarket> st, int Pos) {

    }


    public interface TPDetailsListener {
        void DataSaved(int month, int year, int empId, String remarks);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (TPDetailsListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement BottomListener");

        }
    }
}



