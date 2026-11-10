package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard;

import static com.saidur.creatrix.unicorn.salessolution.UI.Order.PlaceOrderActivity.MY_PERMISSIONS_REQUEST_LOCATION;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.TextKeyListener;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Adapters.CustomerMultiAdapter;
import com.saidur.creatrix.unicorn.salessolution.Adapters.TagCustAdapter;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDDTUHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IDDT;
import com.saidur.creatrix.unicorn.salessolution.Interface.IDoctor;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketStracture;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.CustomerViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorCategory;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorContact;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDegree;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDesignation;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSM;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSpeciality;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelPracticeShift;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDistrict;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDivision;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelGender;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPriority;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelThana;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponsePotential;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresentarDDT;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterDoctor;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterMarketStructure;
import com.saidur.creatrix.unicorn.salessolution.R;

import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterDoctorContact;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterDoctorShift;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.CameraHelper;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDoctorEntryBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDoctorEntry extends AppCompatActivity implements IDoctor.View, IMarketStracture.View, IDDT.View {

    private boolean isEditMode = false;
    private int doctorId = 0;
    private int e_selectedDivisionIdEdit = 0;
    private int e_selectedDistrictIdEdit = 0;
    private int e_selectedThanaIdEdit    = 0;
    private boolean suppressTourTypeSelect = false;
      // from DB
    List<CustomerViewModel> customerList = new ArrayList<>();
    private final ArrayList<CustomerViewModel> customerItems = new ArrayList<>();
    private final ArrayList<Integer> selectedIds = new ArrayList<>(); // for submit
    private CustomerMultiAdapter multiAdapter;
    private boolean spinnerInitialized = false;
    private int selectedTourTypeId = 0;          // current selection
    private int e_selectedTourTypeIdEdit = 0;

    // ---- Current selections ----
    private int selectedDivisionId = 0;
    private int selectedDistrictId = 0;
    private int selectedDesignationId = 0;          // current selection
    private int e_selectedDesignationIdEdit = 0;    // set this when opening edit screen
    private int e_selectedMarketIdEdit = 0;    // set this when opening edit screen
    private boolean suppressDesignationSelect = false;
    private boolean suppressDocCategorySelect = false;
    private int selectedDoctorCategoryId = 0;        // current selection
    private int e_selectedDoctorCategoryIdEdit = 0;  // set this when opening the edit scree

    // ---- Prevent unwanted cascades while programmatic setSelection ----
    private boolean suppressCascade = false;
    ActivityDoctorEntryBinding binding;
    PresenterDoctor presenterDoctor;
    PresentarDDT presentarDDT;
    PresenterMarketStructure presenterMS;
    Dialog popPractice, popContact;
    Uri imageuri;
    private static int CAMERA_PIC_REQUEST2;
    private static final int PERMISSION_REQUEST_CODE = 200;

    IMarketStracture.Presenter mpresenter;

    String[] listItemsBrand;
    boolean[] checkedItems_Brand;
    ArrayList<Integer> mUserItems_Brand = new ArrayList<>();

    String[] listItemsDegree;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    String[] listItems_Specility;
    boolean[] checkedItems_Specility;
    ArrayList<Integer> mUserItems_Specility = new ArrayList<>();

    String[] listItems_Type;
    boolean[] checkedItems_Type;
    ArrayList<Integer> mUserItems_Type = new ArrayList<>();

    String[] listItems_ins;
    boolean[] checkedItems_ins;
    ArrayList<Integer> mUserItems_ins = new ArrayList<>();
    TimePickerDialog pickTime;

    DBCrudHelper dbCrudHelper;
    DBDoctorHelper dbDoctorHelper;
    DBDDTUHelper dbddtuHelper;
    AdapterDoctorContact contactAdapter;
    AdapterDoctorShift shiftAdapter;
    AdapterDoctorShift TagAdapter;
    TagCustAdapter TagCusAdapter;

    List<ModelBrand> brandList;

    List<ModelPracticeShift> docShiftLists = new ArrayList<>();
    List<ModelPracticeShift> docShiftList = new ArrayList<>();
    List<DoctorContact> doccontactLists = new ArrayList<>();
    List<DoctorContact> doccontactList = new ArrayList<>();

    LocationManager locationManager;
    int seletedRegionId, seletedAreaId, seletedTeritoryId, seletedSTeritoryId, selectedMarketId;
    int selectedShiftId, selectedcontactTypeId,selectedCustId;
    String selectedshiftTypeName, selectedcontactTypeName, path,selectedCustName;
    File imgFile;
    TextView contacttype;
    EditText contactName;
    TextInputLayout til_shiftDD;
    String timestring = "";

    int empId;

    String prioritystatus;
    int thour, tmin;
    public String blockCharacterSet = "@~'#^|$%&*!/?>";
    boolean is_Contact, is_Shift;

    int selectedDivId, selectedDisId, selectedThaId;
    ModelSession session;
    ProgressDialog pd;

    String lat, lon, setStreetAddress;
    private final String[] WEEK_DAYS = {
            "Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"
    };
    private boolean[] selectedDays = new boolean[WEEK_DAYS.length];
    List<String> allCustomers;
    ArrayList<Integer> selectedIndexes = new ArrayList<>();
    ArrayAdapter<String> adapter;
    boolean[] checkedItems2;

    private void setupWeekMultiSelect() {
        AutoCompleteTextView actv = findViewById(R.id.weeklyvisitdaySpinner);
        TextInputLayout til = findViewById(R.id.til_weeklyvisitday);

        // Make ACTV read-only but clickable
        actv.setInputType(0);
        actv.setKeyListener(null);
        actv.setFocusable(false);
        actv.setCursorVisible(false);
        actv.setOnClickListener(v -> showWeekDialog(actv));
        til.setEndIconOnClickListener(v -> showWeekDialog(actv)); // tap on end icon also opens
    }

    private void showWeekDialog(AutoCompleteTextView target) {
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                .setTitle("Select visit days")
                .setMultiChoiceItems(WEEK_DAYS, selectedDays, (dialog, which, isChecked) -> {
                    selectedDays[which] = isChecked;
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    target.setText(joinSelectedDays(), false);
                })
                .setNegativeButton("Clear", (dialog, which) -> {
                    java.util.Arrays.fill(selectedDays, false);
                    target.setText("", false);
                }).setNeutralButton("Cancel", (dialog, which) -> {
                    // do nothing — just dismiss without changing selectedDays
                    dialog.dismiss();
                })
                .show();
    }

    private String joinSelectedDays() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < WEEK_DAYS.length; i++) {
            if (selectedDays[i]) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(WEEK_DAYS[i]);
            }
        }
        return sb.toString();
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            if (locationResult == null) return;

            android.location.Location loc = locationResult.getLastLocation();
            if (loc == null) return; // can be null on first hit or if provider cold-starts

            double latitude = loc.getLatitude();
            double longitude = loc.getLongitude();

            // Defensive: get address may throw or be slow; wrap it
            try {
                setStreetAddress = getCompleteAddressString(latitude, longitude);
            } catch (Exception ignored) {
                // keep going even if reverse-geocode fails
            }

            lat = String.valueOf(latitude);
            lon = String.valueOf(longitude);

            if (binding != null && binding.location != null) {
                binding.location.setText(lat + "," + lon);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //  binding.docName.setFilters(new InputFilter[]{filter});
        dbddtuHelper = new DBDDTUHelper(ActivityDoctorEntry.this);
        dbCrudHelper = new DBCrudHelper(ActivityDoctorEntry.this);
        presenterDoctor = new PresenterDoctor(this, ActivityDoctorEntry.this);
        presentarDDT = new PresentarDDT(ActivityDoctorEntry.this, this);
        mpresenter = new PresenterMarketStructure(this, ActivityDoctorEntry.this);
        session = dbCrudHelper.getSession();
        empId = session.getEmpInfoId();
        pd = new ProgressDialog(ActivityDoctorEntry.this);
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iniPopupContact();
        LoadTourType();




        // Prevent default dropdown — show multi-select dialog instead

        presenterDoctor.GetDegree(0);
        presenterDoctor.GetSpeciality();
        presenterDoctor.GetDoctorDesignation(0);
        presenterDoctor.GetDoctorGender();
        presenterDoctor.GetDoctorPracticeShift();
        presenterDoctor.GetContactType(0);
        presenterDoctor.GetBrand();
        presenterDoctor.GetPriority();
        presenterDoctor.GetDoctorCategory(0);
        mpresenter.GetGroupLocal();
        mpresenter.GetRegionLocal(0);
        mpresenter.GetAreaLocal(0);
        mpresenter.GetTeritoryLocal(0);
        mpresenter.GetSTeritoryLocal(0);
        mpresenter.GetMarketLocal(0);





        locationManager = (LocationManager) ActivityDoctorEntry.this.getSystemService(Context.LOCATION_SERVICE);
        binding.msloc.setOnClickListener(view -> {



                checkLocationPermission();
                locationEnabled();
                getCLocation();


        });
        try {
            switch (session.getRoleType()) {
                case "MBE":
                    binding.groupdiv.setVisibility(View.GONE);
                    binding.regiondiv.setVisibility(View.GONE);
                    binding.areadiv.setVisibility(View.GONE);
                    binding.teridiv.setVisibility(View.GONE);
                    break;
                case "MIO":
                    binding.groupdiv.setVisibility(View.GONE);
                    binding.regiondiv.setVisibility(View.GONE);
                    binding.areadiv.setVisibility(View.GONE);
                    break;
                case "AM":
                    binding.groupdiv.setVisibility(View.GONE);
                    binding.regiondiv.setVisibility(View.GONE);
                    break;
                case "DZSM":
                    binding.groupdiv.setVisibility(View.GONE);

                    break;
                case "NSM":
                    binding.groupdiv.setVisibility(View.VISIBLE);
                    binding.regiondiv.setVisibility(View.VISIBLE);
                    binding.areadiv.setVisibility(View.VISIBLE);
                    binding.teridiv.setVisibility(View.VISIBLE);
                    binding.steridiv.setVisibility(View.VISIBLE);
                    binding.maktdiv.setVisibility(View.VISIBLE);

                    break;
            }
            binding.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelRegion region = (ModelRegion) parent.getSelectedItem();
                    seletedRegionId = region.getRegionId();
                    mpresenter.GetAreaLocal(seletedRegionId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelArea area = (ModelArea) parent.getSelectedItem();
                    seletedAreaId = area.getAreaId();
                    mpresenter.GetTeritoryLocal(seletedAreaId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.territorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelTeritorry teri = (ModelTeritorry) parent.getSelectedItem();
                    seletedTeritoryId = teri.getTerritoryId();
                    mpresenter.GetSTeritoryLocal(seletedTeritoryId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.sterritorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelSubTeritorry steri = (ModelSubTeritorry) parent.getSelectedItem();
                    seletedSTeritoryId = steri.getSubTerritoryId();
                    mpresenter.GetMarketLocal(seletedSTeritoryId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ModelMarket market = (ModelMarket) parent.getSelectedItem();
                    selectedMarketId = market.getMarketId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        presentarDDT.getDivision();
        binding.divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelDivision div = (ModelDivision) parent.getSelectedItem();
                selectedDivId = div.getDivisionId();
                presentarDDT.getDistrict(selectedDivId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelDistrict dis = (ModelDistrict) parent.getSelectedItem();
                selectedDisId = dis.getDistrictId();
                presentarDDT.getThana(selectedDisId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.thanaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelThana thn = (ModelThana) parent.getSelectedItem();
                selectedThaId = thn.getThanaId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        LocationManager locationManagerdd = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManagerdd.isProviderEnabled(locationManagerdd.GPS_PROVIDER)) {
        } else {
            //showGPSDisabledAlertToUser();
        }
        binding.camera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(ActivityDoctorEntry.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                CAMERA_PIC_REQUEST2 = 78911;
                Constants.From = "78911";
                ImagePicker.Companion.with(ActivityDoctorEntry.this)
                        .crop()
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            } else {
                ActivityCompat.requestPermissions(ActivityDoctorEntry.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
            }
        });
        binding.degreeAdd.setOnClickListener(view -> LoadDegree());
        binding.degreeLabel.setOnClickListener(view -> LoadDegree());
        binding.degreeListStr.setOnClickListener(view -> LoadDegree());
        binding.specialityAdd.setOnClickListener(view -> LoadSpecilityInView());
        binding.specialityLabel.setOnClickListener(view -> LoadSpecilityInView());
        binding.specilityListStr.setOnClickListener(view -> LoadSpecilityInView());
        binding.brandAdd.setOnClickListener(v -> LoadBrandInView());
        binding.brandLabel.setOnClickListener(v -> LoadBrandInView());
        binding.brandListStr.setOnClickListener(v -> LoadBrandInView());
        binding.btnSubmit.setOnClickListener(v -> {
            String img_str = null;
            try {
                if (imgFile != null) {
                    Bitmap bitmap = new Compressor(ActivityDoctorEntry.this).compressToBitmap(imgFile);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                    byte[] image = stream.toByteArray();
                    img_str = Base64.encodeToString(image, 0);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            // Centralized mandatory checks
            if (!validateInputs(img_str)) return;

            SubmitClick(empId, img_str);
        });

        binding.rxnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* String rx = binding.rxnumber.getText().toString();
                apiCall(binding.pnumber.getText().toString(), rx);*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(binding.rxnumber.getText().toString())) {
                    apiCall(binding.pnumber.getText().toString(), s.toString());
                }

            }
        });

        setupWeekMultiSelect();   // <-- এখানেই কল করবে

        // যদি আগে থেকে ডেটা থাকে (edit mode), সেটা prefill করো
        prefillWeekDays(binding.weeklyvisitdaySpinner.getText().toString());

        String mode = getIntent().getStringExtra("Mode");
        doctorId = getIntent().getIntExtra("DoctorId", 0);
        isEditMode = (doctorId > 0) || (mode != null && "edit".equalsIgnoreCase(mode));

        if (isEditMode) {
            fetchDoctorForEdit(doctorId);
            binding.btnSubmit.setText("Update");
            binding.toolbarTitle.setText("Edit Doctor");
        } else {
            binding.btnSubmit.setText("Save");
            binding.toolbarTitle.setText("Add Doctor");
        }

    }
    private void fetchDoctorForEdit(int id) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading doctor...");
        pd.setCancelable(false);
        pd.show();

        ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
        Call<DoctorSM> call = service.GetDoctorEdtMode(id);
        call.enqueue(new Callback<DoctorSM>() {
            @Override public void onResponse(Call<DoctorSM> call, Response<DoctorSM> resp) {
                pd.dismiss();
                if (resp.isSuccessful() && resp.body() != null) {
                    bindDoctorToUi(resp.body());
                } else {
                    SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Failed to load");
                    finish();
                }
            }
            @Override public void onFailure(Call<DoctorSM> call, Throwable t) {
                pd.dismiss();
                SnackBarManagement._error_CustomMessage(binding.getRoot(), "Network error: " + t.getMessage());
            }
        });
    }
    private void bindDoctorToUi(DoctorSM d) {
        // basic
        binding.docName.setText(ns(d.getDoctorName()));
        binding.degreeListStr.setText(ns(d.getDegreeStr()));
        binding.specilityListStr.setText(ns(d.getSpecialityStr()));


        String degreeType = d.getDegreeType(); // "MBBS" / "Non-MBBS" / null
        if ("MBBS".equalsIgnoreCase(degreeType)) {
            binding.rgDegreeType.check(binding.rbMbbs.getId());
        } else if ("Non-MBBS".equalsIgnoreCase(degreeType) || "NON_Mbbs".equalsIgnoreCase(degreeType)) {
            binding.rgDegreeType.check(binding.rbNonMbbs.getId());
        } else {
            binding.rgDegreeType.clearCheck();
        }



        String gender = d.getGender(); // e.g. "Male" or "M"
        String[] genderArray = {"Male", "Female"};

// Find matching position
        int pos = 0;
        for (int i = 0; i < genderArray.length; i++) {
            if (genderArray[i].equalsIgnoreCase(gender) ||
                    (gender.equalsIgnoreCase("M") && genderArray[i].equalsIgnoreCase("Male")) ||
                    (gender.equalsIgnoreCase("F") && genderArray[i].equalsIgnoreCase("Female")) ) {
                pos = i;
                break;
            }
        }

// Set selected item
        binding.genderSpinner.setSelection(pos);

        binding.docAddress.setText(ns(d.getDoctorAddress()));
        binding.location.setText(ns(d.getLocation()));
        binding.bmdcRegi.setText(ns(d.getBMDCRegistration()));
        binding.cpmnumber.setText(ns(d.getNoofCallPerMonth()));
        binding.pnumber.setText(ns(d.getNoofPatient()));
        binding.rxnumber.setText(ns(d.getNoofRx()));
        binding.brandListStr.setText(ns(d.getBrandStr()));          // e.g. "FCB,DCN,KMN"
        binding.weeklyvisitdaySpinner.setText(ns(d.getWeeklyvisitday()), false);
        e_selectedDoctorCategoryIdEdit=0;
        e_selectedDivisionIdEdit=0;
        e_selectedDesignationIdEdit=0;
        e_selectedDistrictIdEdit=0;
        e_selectedThanaIdEdit=0;
        e_selectedThanaIdEdit=0;
        e_selectedTourTypeIdEdit=0;
        // dropdowns (ids)
        e_selectedDesignationIdEdit= d.getDesignationId();
        presenterDoctor.GetDoctorDesignation(0);

        e_selectedDoctorCategoryIdEdit= d.getDoctorCategoryId();
        //presentarDDT.getDivision();
//        presentarDDT.getDivision();
//         selectById(binding.divisionSpinner, d.getDivisionId());

        e_selectedDivisionIdEdit=d.getDivisionId();
        e_selectedDistrictIdEdit=d.getDistrictId();
        e_selectedThanaIdEdit=d.getThanaId();
        presenterDoctor.GetDoctorCategory(0);
        presentarDDT.getDivision();
        presentarDDT.getDistrict(d.getDivisionId());
        presentarDDT.getThana(d.getDistrictId());
//        presentarDDT.getDistrict();
//        presentarDDT.getDistrict(d.getDivisionId());
     //   selectById(binding.districtSpinner, d.getDistrictId());
       // selectById(binding.thanaSpinner, d.getThanaId());
        e_selectedTourTypeIdEdit= Integer.parseInt(d.getTourTypeId());
LoadTourType();
        // contacts & practice list if present
        if (d.getaDoctorContactDAO() != null){

            doccontactList  = new ArrayList<>(d.getaDoctorContactDAO()); // master
            doccontactLists = new ArrayList<>(doccontactList);

            contactAdapter = new AdapterDoctorContact(ActivityDoctorEntry.this, doccontactLists, doccontactList);
            binding.rvContactlist.setLayoutManager(new LinearLayoutManager(ActivityDoctorEntry.this));
            binding.rvContactlist.setHasFixedSize(true);
            binding.rvContactlist.setAdapter(contactAdapter);
            contactAdapter.notifyDataSetChanged();
        }
        if (d.getPracticeList() != null)
        {
            docShiftList = d.getPracticeList();

            shiftAdapter = new AdapterDoctorShift(ActivityDoctorEntry.this, docShiftList);
            binding.rvPracticelist.setLayoutManager(new LinearLayoutManager(ActivityDoctorEntry.this));
            binding.rvPracticelist.setHasFixedSize(true);
            binding.rvPracticelist.setAdapter(shiftAdapter);
            shiftAdapter.notifyDataSetChanged();
        }

        if (d.getCustomerList() != null)
        {
            customerList = d.getCustomerList();

            TagCusAdapter = new TagCustAdapter(ActivityDoctorEntry.this, customerList);
            binding.rvTagCustomer.setLayoutManager(new LinearLayoutManager(ActivityDoctorEntry.this));
            binding.rvTagCustomer.setHasFixedSize(true);
            binding.rvTagCustomer.setAdapter(TagCusAdapter);
            TagCusAdapter.notifyDataSetChanged();
        }

        // image string/token (optional hidden field)
         binding.vsCardInfo.setVisibility(View.GONE);
         binding.teridiv.setVisibility(View.GONE);
         binding.maktdiv.setVisibility(View.GONE);
         binding.steridiv.setVisibility(View.GONE);
         binding.areadiv.setVisibility(View.GONE);
         binding.regiondiv.setVisibility(View.GONE);


        presenterDoctor.getCustomerListByMarketId(d.getMarketId());
    }
    private String ns(String s){ return s==null?"":s; }
    private int parseIntSafe(String s){ try {return Integer.parseInt(s);} catch (Exception e){return 0;} }
    private void selectById(Spinner sp, int id){ /* iterate adapter items & setSelection */ }
    private int genderIndexOf(String g){ /* map to index */ return 0; }

    private void prefillWeekDays(String csv) {
        if (csv == null || csv.trim().isEmpty()) return;
        java.util.Arrays.fill(selectedDays, false);
        for (String s : csv.split(",")) {
            String day = s.trim();
            for (int i = 0; i < WEEK_DAYS.length; i++) {
                if (WEEK_DAYS[i].equalsIgnoreCase(day)) {
                    selectedDays[i] = true;
                    break;
                }
            }
        }
    }
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
                                ActivityCompat.requestPermissions(ActivityDoctorEntry.this,
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
        LocationManager lm = (LocationManager) ActivityDoctorEntry.this.getSystemService(Context.LOCATION_SERVICE);
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
            new AlertDialog.Builder(ActivityDoctorEntry.this)
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

    private static final int REQ_LOCATION = 1001;
    public void getCLocation() {
        try {
            // ---- Permission check ----
            boolean fine = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
            boolean coarse = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;

            if (!fine && !coarse) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQ_LOCATION
                );
                return;
            }

            final FusedLocationProviderClient fused =
                    LocationServices.getFusedLocationProviderClient(this);

            // ---- Build requests without try/catch re-assignment ----
            final boolean hasBuilder; // play-services-location 21+?
            boolean hasBuilder1;
            try {
                Class.forName("com.google.android.gms.location.LocationRequest$Builder");
                hasBuilder1 = true;
            } catch (Throwable t) {
                hasBuilder1 = false;
            }

            hasBuilder = hasBuilder1;
            final LocationRequest oneShotReq;
            final LocationRequest longReq;

            if (hasBuilder) {
                // ✅ New API
                oneShotReq = new LocationRequest.Builder(10_000L) // 10s window
                        .setMinUpdateIntervalMillis(5_000L)
                        .setMaxUpdates(1)
                        .setWaitForAccurateLocation(true)
                        .setPriority(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY)
                        .build();

                longReq = new LocationRequest.Builder(300_000L)   // 5 min
                        .setMinUpdateIntervalMillis(180_000L)      // 3 min fastest
                        .setMinUpdateDistanceMeters(10.2f)
                        .setPriority(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY)
                        .build();
            } else {
                // ✅ Legacy API
                LocationRequest r1 = LocationRequest.create();
                r1.setInterval(10_000L);
                r1.setFastestInterval(5_000L);
                r1.setNumUpdates(1);
                r1.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                oneShotReq = r1;

                LocationRequest r2 = LocationRequest.create();
                r2.setInterval(300_000L);
                r2.setFastestInterval(180_000L);
                r2.setSmallestDisplacement(10.2f);
                r2.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                longReq = r2;
            }

            // ---- STEP 1: Try last known (instant) ----
            fused.getLastLocation()
                    .addOnSuccessListener(loc -> {
                        boolean usedLast = false;
                        if (loc != null) {
                            long ageMs = Math.abs(System.currentTimeMillis() - loc.getTime()); // fresh?
                            if (ageMs <= 2 * 60 * 1000) { // <= 2 min
                                double latitude = loc.getLatitude();
                                double longitude = loc.getLongitude();

                                try { setStreetAddress = getCompleteAddressString(latitude, longitude); } catch (Exception ignored) { }
                                lat = String.valueOf(latitude);
                                lon = String.valueOf(longitude);
                                if (binding != null && binding.location != null) {
                                    binding.location.setText(lat + "," + lon);
                                }
                                usedLast = true;
                            }
                        }

                        // ---- STEP 2: If last not usable, request a fast one-shot fix ----
                        if (!usedLast) {
                            try { fused.requestLocationUpdates(oneShotReq, locationCallback, Looper.getMainLooper()); } catch (Exception ignored) { }
                        }

                        // ---- STEP 3: Start continuous background updates (your existing callback) ----
                        try { fused.requestLocationUpdates(longReq, locationCallback, Looper.getMainLooper()); } catch (Exception ignored) { }
                    })
                    .addOnFailureListener(e -> {
                        // If last known failed, still try one-shot + continuous
                        try { fused.requestLocationUpdates(oneShotReq, locationCallback, Looper.getMainLooper()); } catch (Exception ignored) { }
                        try { fused.requestLocationUpdates(longReq,    locationCallback, Looper.getMainLooper()); } catch (Exception ignored) { }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // getCLocation() bails out immediately when permission isn't granted yet and was
        // never retried after the user answered the runtime prompt, so a location request
        // made on the very first tap (before permission existed) silently went nowhere.
        if (requestCode == REQ_LOCATION || requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    getCLocation();
                    break;
                }
            }
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("address", strReturnedAddress.toString());
            } else {
                Log.w("address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("address", "Canont get Address!");
        }
        return strAdd;
    }
    private void apiCall(String patient, String rx) {
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResponsePotential> call = service.GetPotential(patient, rx);
            call.enqueue(new Callback<ResponsePotential>() {
                @Override
                public void onResponse(@NonNull Call<ResponsePotential> call, @NonNull Response<ResponsePotential> response) {
                    ResponsePotential info = response.body();
                    if (info != null) {
                        if (info.getPotentialStatus().equals("")) {
                            binding.llpotential.setVisibility(View.GONE);
                        } else {
                            binding.llpotential.setVisibility(View.VISIBLE);
                            if (info.getColor().equals("Green")) {
                                binding.potential.setText(info.getPotentialStatus());
                                binding.llpotential.setBackgroundResource(R.drawable.shape_solid_green);

                            } else if (info.getColor().equals("Yellow")) {
                                binding.potential.setText(info.getPotentialStatus());
                                binding.llpotential.setBackgroundResource(R.drawable.shape_solid_yellow);
                            } else if (info.getColor().equals("Red")) {
                                binding.potential.setText(info.getPotentialStatus());
                                binding.llpotential.setBackgroundResource(R.drawable.shape_solid_red);
                            }
                        }


                    } else {
                        SnackBarManagement._error_CustomMessage(binding.getRoot(), "Error");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponsePotential> call, @NonNull Throwable t) {

                    if (t instanceof SocketTimeoutException) {

                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);

        }
    }

    public void LoadDegree() {
        if (listItemsDegree == null || listItemsDegree.length == 0) {
            Toast.makeText(this, "No degree found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkedItems == null || checkedItems.length != listItemsDegree.length) {
            checkedItems = new boolean[listItemsDegree.length];
        }
        showSearchableMultiSelectDialog(
                "Doctor Degree",
                "Search degree...",
                listItemsDegree,
                checkedItems,
                mUserItems,
                binding.degreeListStr
        );
    }

    public void LoadSpecilityInView() {
        if (listItems_Specility == null || listItems_Specility.length == 0) {
            Toast.makeText(this, "No speciality found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkedItems_Specility == null || checkedItems_Specility.length != listItems_Specility.length) {
            checkedItems_Specility = new boolean[listItems_Specility.length];
        }
        showSearchableMultiSelectDialog(
                "Doctor Specialties",
                "Search speciality...",
                listItems_Specility,
                checkedItems_Specility,
                mUserItems_Specility,
                binding.specilityListStr
        );

    }

    private void showSearchableMultiSelectDialog(String title,
                                                 String searchHint,
                                                 String[] allItems,
                                                 boolean[] checkedState,
                                                 ArrayList<Integer> selectedIndexes,
                                                 TextView targetText) {
        showSearchableMultiSelectDialog(title, searchHint, allItems, checkedState, selectedIndexes, targetText, 0);
    }

    private void showSearchableMultiSelectDialog(String title,
                                                 String searchHint,
                                                 String[] allItems,
                                                 boolean[] checkedState,
                                                 ArrayList<Integer> selectedIndexes,
                                                 TextView targetText,
                                                 int maxSelection) {
        syncSelectionFromText(targetText.getText().toString(), allItems, checkedState, selectedIndexes);

        boolean[] workingChecked = Arrays.copyOf(checkedState, checkedState.length);
        ArrayList<Integer> workingSelected = new ArrayList<>(selectedIndexes);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_searchable_multiselect, null);
        EditText search = dialogView.findViewById(R.id.et_search);
        ListView listView = dialogView.findViewById(R.id.lv_items);
        TextView emptyView = dialogView.findViewById(R.id.tv_empty);

        search.setHint(searchHint);
        ArrayList<Integer> visibleIndexes = new ArrayList<>();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                ActivityDoctorEntry.this,
                android.R.layout.simple_list_item_multiple_choice,
                new ArrayList<>()
        );
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(listAdapter);

        updateSearchableMultiSelectList("", allItems, workingChecked, visibleIndexes, listAdapter, listView, emptyView);

        search.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSearchableMultiSelectList(s.toString(), allItems, workingChecked, visibleIndexes, listAdapter, listView, emptyView);
            }

            @Override public void afterTextChanged(Editable s) { }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position < 0 || position >= visibleIndexes.size()) return;

            int originalIndex = visibleIndexes.get(position);
            boolean isChecked = listView.isItemChecked(position);
            if (isChecked && maxSelection > 0 && !workingSelected.contains(originalIndex)
                    && workingSelected.size() >= maxSelection) {
                listView.setItemChecked(position, false);
                workingChecked[originalIndex] = false;
                Toast.makeText(
                        ActivityDoctorEntry.this,
                        "Maximum " + maxSelection + " items can be selected",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            workingChecked[originalIndex] = isChecked;
            if (workingChecked[originalIndex]) {
                addSelectedIndex(workingSelected, originalIndex);
            } else {
                workingSelected.remove(Integer.valueOf(originalIndex));
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(ActivityDoctorEntry.this)
                .setTitle(title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("Done", (dialogInterface, which) -> {
                    System.arraycopy(workingChecked, 0, checkedState, 0, checkedState.length);
                    selectedIndexes.clear();
                    selectedIndexes.addAll(workingSelected);
                    targetText.setText(joinSelectedItems(allItems, selectedIndexes));
                })
                .setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.dismiss())
                .setNeutralButton("Clear All", (dialogInterface, which) -> {
                    Arrays.fill(checkedState, false);
                    selectedIndexes.clear();
                    targetText.setText("");
                })
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            search.requestFocus();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        dialog.show();
    }

    private void updateSearchableMultiSelectList(String query,
                                                 String[] allItems,
                                                 boolean[] checkedState,
                                                 ArrayList<Integer> visibleIndexes,
                                                 ArrayAdapter<String> listAdapter,
                                                 ListView listView,
                                                 TextView emptyView) {
        String searchText = query == null ? "" : query.trim().toLowerCase(Locale.getDefault());
        visibleIndexes.clear();
        listAdapter.clear();

        for (int i = 0; i < allItems.length; i++) {
            String item = allItems[i] == null ? "" : allItems[i];
            if (searchText.isEmpty() || item.toLowerCase(Locale.getDefault()).contains(searchText)) {
                visibleIndexes.add(i);
                listAdapter.add(item);
            }
        }

        listAdapter.notifyDataSetChanged();
        boolean hasItems = listAdapter.getCount() > 0;
        listView.setVisibility(hasItems ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(hasItems ? View.GONE : View.VISIBLE);

        listView.clearChoices();
        for (int i = 0; i < visibleIndexes.size(); i++) {
            int originalIndex = visibleIndexes.get(i);
            listView.setItemChecked(i, checkedState[originalIndex]);
        }
    }

    private void syncSelectionFromText(String selectedText,
                                       String[] allItems,
                                       boolean[] checkedState,
                                       ArrayList<Integer> selectedIndexes) {
        Arrays.fill(checkedState, false);
        selectedIndexes.clear();
        if (TextUtils.isEmpty(selectedText)) return;

        String[] selectedItems = selectedText.split(",");
        for (String selectedItem : selectedItems) {
            String selectedName = selectedItem == null ? "" : selectedItem.trim();
            if (selectedName.isEmpty()) continue;

            for (int i = 0; i < allItems.length; i++) {
                String item = allItems[i] == null ? "" : allItems[i].trim();
                if (selectedName.equalsIgnoreCase(item)) {
                    checkedState[i] = true;
                    addSelectedIndex(selectedIndexes, i);
                    break;
                }
            }
        }
    }

    private void addSelectedIndex(ArrayList<Integer> selectedIndexes, int index) {
        if (!selectedIndexes.contains(index)) {
            selectedIndexes.add(index);
        }
    }

    private String joinSelectedItems(String[] allItems, ArrayList<Integer> selectedIndexes) {
        StringBuilder item = new StringBuilder();
        for (int i = 0; i < selectedIndexes.size(); i++) {
            int index = selectedIndexes.get(i);
            if (index < 0 || index >= allItems.length) continue;

            if (item.length() > 0) {
                item.append(",");
            }
            item.append(allItems[index]);
        }
        return item.toString();
    }

    public void LoadBrandInView() {
        if (listItemsBrand == null || listItemsBrand.length == 0) {
            Toast.makeText(this, "No brand found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkedItems_Brand == null || checkedItems_Brand.length != listItemsBrand.length) {
            checkedItems_Brand = new boolean[listItemsBrand.length];
        }
        showSearchableMultiSelectDialog(
                "Select Brand",
                "Search brand...",
                listItemsBrand,
                checkedItems_Brand,
                mUserItems_Brand,
                binding.brandListStr,
                3
        );
    }

    /*    private void iniPopupPractice(int shiftId, String shift) {
            pickTime = new TimePickerDialog(ActivityDoctorEntry.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    (timePicker, hour, min) -> {
                        thour = hour;
                        tmin = min;
                        String time = hour + ":" + min;
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                            Date date = sdf.parse(time);
                            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm aa");

                            timestring = sdf12.format(date);
                            ModelPracticeShift isit = new ModelPracticeShift(shiftId, timestring);
                            docShiftList.add(isit);

                            ModelPracticeShift isitm = new ModelPracticeShift(shift, timestring);
                            docShiftLists.add(isitm);

                            shiftAdapter = new AdapterDoctorShift(ActivityDoctorEntry.this, docShiftLists);
                            binding.rvPracticelist.setLayoutManager(new LinearLayoutManager(ActivityDoctorEntry.this));
                            binding.rvPracticelist.setHasFixedSize(true);
                            binding.rvPracticelist.setAdapter(shiftAdapter);
                            shiftAdapter.notifyDataSetChanged();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }, 12, 0, false);
            pickTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pickTime.show();
            pickTime.updateTime(thour, tmin);
        }*/
    private void iniPopupContact() {
        popContact = new Dialog(ActivityDoctorEntry.this);
        popContact.setContentView(R.layout.pop_contactname);
        //popAddQty.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popContact.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popContact.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popContact.getWindow().getAttributes().gravity = Gravity.CENTER;

        // ini popup widgets
        til_shiftDD = popContact.findViewById(R.id.chamberDD);
        contactName = popContact.findViewById(R.id.chamberName);
        contacttype = popContact.findViewById(R.id.contacttype);
        TextView contactBnt = popContact.findViewById(R.id.contactBnt);
        contactBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!contactName.getText().toString().isEmpty()) {
                    String contacName = contactName.getText().toString();
                    DoctorContact isit = new DoctorContact(selectedcontactTypeId, contacName);
                    doccontactList.add(isit);

                    DoctorContact isitm = new DoctorContact(selectedcontactTypeId, contacName, selectedcontactTypeName);
                    doccontactLists.add(isitm);

                    contactAdapter = new AdapterDoctorContact(ActivityDoctorEntry.this, doccontactLists, doccontactList);
                    binding.rvContactlist.setLayoutManager(new LinearLayoutManager(ActivityDoctorEntry.this));
                    binding.rvContactlist.setHasFixedSize(true);
                    binding.rvContactlist.setAdapter(contactAdapter);
                    contactAdapter.notifyDataSetChanged();
                    contactName.setText("");
                    popContact.dismiss();
                } else {
                    contactName.setError("Enter Contact Name");
                }
            }
        });
    }
    @Nullable
    String readDegreeOrValidate() {
        int checkedId = binding.rgDegreeType.getCheckedRadioButtonId();

        if (checkedId == binding.rbMbbs.getId()) return "MBBS";
        if (checkedId == binding.rbNonMbbs.getId()) return "Non-MBBS";

        // nothing selected → show error

        binding.rgDegreeType.requestFocus();
        return null;
    }


    public void SubmitClick(int empId, String img_str) {
        try {
            String docName = binding.docName.getText().toString();
            String degreeType = readDegreeOrValidate();


            String docDegree = binding.degreeListStr.getText().toString();
            String docSpeciality = binding.specilityListStr.getText().toString();
            String docBrand = binding.brandListStr.getText().toString();
            String docAddress = binding.docAddress.getText().toString();
            String docLocation = binding.location.getText().toString();
            String docBMDC = binding.bmdcRegi.getText().toString();
            String CallPermonth = binding.cpmnumber.getText().toString();
            String NumberOfPatient = binding.pnumber.getText().toString();
            String NumberOfRx = binding.rxnumber.getText().toString();

            ModelGender gender = (ModelGender) binding.genderSpinner.getSelectedItem();
            String genderName = gender.getGenderName();

            DoctorCategory catType = (DoctorCategory) binding.doctorCategorySpinner.getSelectedItem();
            int catTypeId = catType.getCategoryId();


            DoctorDesignation docDesi = (DoctorDesignation) binding.designationSpinner.getSelectedItem();
            int designationId = docDesi.getDesignationId();

            ModelDivision division = (ModelDivision) binding.divisionSpinner.getSelectedItem();
            int divisionId = division.getDivisionId();
            ModelDistrict district = (ModelDistrict) binding.districtSpinner.getSelectedItem();
            int districtId = district.getDistrictId();
            ModelThana thana = (ModelThana) binding.thanaSpinner.getSelectedItem();
            int thanaId = thana.getThanaId();

            ModelStationType tourtype = (ModelStationType) binding.tourtypeSpinner.getSelectedItem();
            int tourtypeId = tourtype.getStationTypeId();

            String weeklyvisitday = binding.weeklyvisitdaySpinner.getText().toString().trim();

            DoctorSM doctorSM = new DoctorSM();
            doctorSM.setDoctorId(doctorId);
            doctorSM.setDoctorName(docName);
            doctorSM.setDegreeType(degreeType);
            doctorSM.setDegreeStr(docDegree);
            doctorSM.setSpecialityStr(docSpeciality);
            doctorSM.setDesignationId(designationId);
            doctorSM.setBMDCRegistration(docBMDC);
            doctorSM.setGender(genderName);
            doctorSM.setWeeklyvisitday(weeklyvisitday);
            doctorSM.setDoctorAddress(docAddress);
            doctorSM.setDivisionId(divisionId);
            doctorSM.setDistrictId(districtId);
            doctorSM.setThanaId(thanaId);
            doctorSM.setLocation(docLocation);
            doctorSM.setaDoctorContactDAO(doccontactList);
            doctorSM.setBrandStr(docBrand);
            doctorSM.setNoofPatient(NumberOfPatient);
            doctorSM.setNoofRx(NumberOfRx);
            doctorSM.setNoofCallPerMonth(CallPermonth);
            doctorSM.setDoctorCategoryId(catTypeId);
            doctorSM.setPracticeList(docShiftList);
            doctorSM.setCustomerList(customerList);
            doctorSM.setDoctorPatientStatus(prioritystatus);
            if (img_str == null) {
                doctorSM.setVisitingCardImg("");
            } else {
                doctorSM.setVisitingCardImg(img_str);
            }
            doctorSM.setTerritoryId(seletedTeritoryId);
            doctorSM.setSubTerritoryId(seletedSTeritoryId);
            if (selectedMarketId > 0) {
                doctorSM.setMarketId(selectedMarketId);
            } else {
                doctorSM.setMarketId(0);
            }
            doctorSM.setSessionUser(empId);
            doctorSM.setTourTypeId(String.valueOf(tourtypeId));
            Gson gs = new Gson();
            String ss = gs.toJson(doctorSM);
            System.out.println("submit : " + ss);
            if (NetworkInformation.isConnected(ActivityDoctorEntry.this)) {
                pd.setMessage("Send to server....");
                pd.show();
                presenterDoctor.SaveDoctor(doctorSM, "Submit");
            } else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(), "Internet Not Connected!!");
                return;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage().toString());
        }


    }


    // ---- Helpers ----
    private boolean isEmpty(CharSequence s) {
        return TextUtils.isEmpty(s == null ? "" : s.toString().trim());
    }

    private int getSpinnerId(Object item, Function<Object, Integer> getter) {
        if (item == null) return 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return getter.apply(item);
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }



    // BD phone quick check (accepts 01XXXXXXXXX or 8801XXXXXXXXX or +8801XXXXXXXXX)
    private boolean isValidBdPhone(String s) {
        if (s == null) return false;
        s = s.trim();

        // ✅ Must start with 01 and have exactly 11 digits
        return s.matches("^01\\d{9}$");
    }

    /**
     * Require everything except: BMDC, map location, email.
     * Email may exist in doccontactLists but is not required.
     * At least one valid phone must exist in doccontactLists.
     */
    private boolean validateInputs(String img_str) {
        // Text fields
        if (isEmpty(binding.docName.getText())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Doctor Name is required");
            return false;
        }

        String degreeType = readDegreeOrValidate();
        if (degreeType == null) {

                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Select MBBS or Non-MBBS");
                return false;

        }
        if (isEmpty(binding.degreeListStr.getText())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Degree is required");
            return false;
        }
        if (isEmpty(binding.specilityListStr.getText())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Speciality is required");
            return false;
        }
        if (isEmpty(binding.brandListStr.getText())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Brand is required");
            return false;
        }
        if (isEmpty(binding.docAddress.getText())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Address is required");
            return false;
        }

        // Numbers (allow 0? Usually not; treat as required non-empty)
        if (isEmpty(binding.cpmnumber.getText())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Call/Month is required");
            return false;
        }
        if (isEmpty(binding.pnumber.getText())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Number of Patients is required");
            return false;
        }
        if (isEmpty(binding.rxnumber.getText())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Number of Rx is required");
            return false;
        }

        // Spinners (must be selected > 0)
        ModelGender gender = (ModelGender) binding.genderSpinner.getSelectedItem();
        if (gender == null || isEmpty(gender.getGenderName())) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Gender is required");
            return false;
        }
        DoctorCategory catType = (DoctorCategory) binding.doctorCategorySpinner.getSelectedItem();
        if (catType == null || catType.getCategoryId() <= 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Doctor Category is required");
            return false;
        }
        DoctorDesignation desi = (DoctorDesignation) binding.designationSpinner.getSelectedItem();
        if (desi == null || desi.getDesignationId() <= 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Designation is required");
            return false;
        }
        ModelDivision division = (ModelDivision) binding.divisionSpinner.getSelectedItem();
        if (division == null || division.getDivisionId() <= 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Division is required");
            return false;
        }
        ModelDistrict district = (ModelDistrict) binding.districtSpinner.getSelectedItem();
        if (district == null || district.getDistrictId() <= 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "District is required");
            return false;
        }
        ModelThana thana = (ModelThana) binding.thanaSpinner.getSelectedItem();
        if (thana == null || thana.getThanaId() <= 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Thana is required");
            return false;
        }
        ModelStationType tourtype = (ModelStationType) binding.tourtypeSpinner.getSelectedItem();
        if (tourtype == null || tourtype.getStationTypeId() <= 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Tour Type is required");
            return false;
        }

        // Lists
        if (doccontactLists == null || doccontactLists.size() == 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "At least one contact is required");
            return false;
        }

        boolean hasMobile = false;
        for (DoctorContact contact : doccontactLists) {
            if ("Mobile".equalsIgnoreCase(contact.getContactType())
                    && contact.getContact() != null
                    && !contact.getContact().trim().isEmpty()) {
                hasMobile = true;
                break;
            }
        }

        if (!hasMobile) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Mobile contact is mandatory");
            return false;
        }
        // Require at least one valid PHONE (email optional)
        boolean hasPhone = false;
        for (DoctorContact c : doccontactLists) {
            if (c == null) continue;
            String v = (c.getContactType() == null ? "" : c.getContactType().trim());
            // If contact type exists, accept "Mobile/Phone"; otherwise infer by regex
            if ((c.getContactType() != null && c.getContactType().toLowerCase().contains("mobile"))
                    || (isValidBdPhone(v))) {
                hasPhone = true;
                break;
            }
        }
        if (!hasPhone) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "At least one valid phone number is required");
            return false;
        }


        String docBrand = binding.brandListStr.getText().toString().trim();

// empty check

        if (docBrand.isEmpty()) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Brand name is required");
            return false;
        }

        // Split by comma
        String[] brandArray = docBrand.split(",");
        int validCount = 0;

        for (String brand : brandArray) {
            String b = brand.trim();
            if (b.isEmpty()) continue;
            validCount++;

            // Stop if more than 3
            if (validCount > 3) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId,
                        "You can select a maximum of 3 brands only");
                return false;
            }

            // Optional: only letters allowed
            if (!b.matches("^[A-Za-z ]+$")) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId,
                        "Invalid brand name (letters and spaces only): " + b);
                return false;
            }
        }


        if (docShiftList == null || docShiftList.size() == 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Practice (shift) list is required");
            return false;
        }

        if (customerList == null || customerList.size() == 0) {
            SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Customer list is required");
            return false;
        }

        // Territory / SubTerritory / Market (mandatory per instruction)
        if (!isEditMode) {
            if (img_str == null || img_str.trim().isEmpty()) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Visiting Card is required");
                return false;
            }
        }

        // Optional fields explicitly allowed to be empty:
        // - BMDC (binding.bmdcRegi)  ✅ optional
        // - Map/Location (binding.location) ✅ optional
        // - Email ✅ optional (already handled via contact check above)

        return true;
    }

    private void LoadTourType() {
        List<ModelStationType> ms = dbCrudHelper.getStationTypeListFromSQLite();
        try {
            if (ms != null && !ms.isEmpty()) {
                // Ensure spinner shows name (fallback to toString if model already overrides it)
                ArrayAdapter<ModelStationType> dataAdapter =
                        new ArrayAdapter<>(ActivityDoctorEntry.this,
                                android.R.layout.simple_spinner_item, ms);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.tourtypeSpinner.setAdapter(dataAdapter);
            }

            // --- Edit mode preselect ---
            if (e_selectedTourTypeIdEdit > 0 && ms != null) {
                for (int i = 0; i < ms.size(); i++) {
                    ModelStationType t = ms.get(i);
                    if (t != null && t.getStationTypeId() == e_selectedTourTypeIdEdit) {
                        suppressTourTypeSelect = true;
                        binding.tourtypeSpinner.setSelection(i);
                        suppressTourTypeSelect = false;
                        selectedTourTypeId = t.getStationTypeId();
                        break;
                    }
                }
            }

            // --- Selection listener ---
            binding.tourtypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (suppressTourTypeSelect) return;
                    ModelStationType t = (ModelStationType) parent.getItemAtPosition(position);
                    if (t != null) selectedTourTypeId = t.getStationTypeId();
                }
                @Override public void onNothingSelected(AdapterView<?> parent) { /* no-op */ }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    @Override
    public void onDegreeGet(List<DoctorDegree> aList) {
        try {
            if (aList != null) {
                listItemsDegree = new String[aList.size()];
                for (int i = 0; i < aList.size(); i++) {
                    listItemsDegree[i] = aList.get(i).getDegreeName();
                }
                checkedItems = new boolean[listItemsDegree.length];

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onSpecialityGet(List<DoctorSpeciality> aList) {
        try {
            if (aList != null) {
                listItems_Specility = new String[aList.size()];
                for (int i = 0; i < aList.size(); i++) {
                    listItems_Specility[i] = aList.get(i).getSpecialityName();
                }
                checkedItems_Specility = new boolean[listItems_Specility.length];

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }




    @Override
    public void onDoctorDesignationGet(List<DoctorDesignation> aList) {
        try {
            if (aList != null) {
                ArrayAdapter<DoctorDesignation> dataAdapter =
                        new ArrayAdapter<>(ActivityDoctorEntry.this,
                                android.R.layout.simple_spinner_item, aList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.designationSpinner.setAdapter(dataAdapter);
                binding.designationSpinner.setTitle("Select Designation");
                binding.designationSpinner.setPositiveButton("Close");
                binding.designationSpinner.setPrompt("Search designation");
            }

            // --- Edit mode preselect (like Division) ---
            if (e_selectedDesignationIdEdit > 0 && aList != null) {
                for (int i = 0; i < aList.size(); i++) {
                    DoctorDesignation d = aList.get(i);
                    if (d != null && d.getDesignationId() == e_selectedDesignationIdEdit) {
                        suppressDesignationSelect = true;
                        binding.designationSpinner.setSelection(i);
                        suppressDesignationSelect = false;
                        selectedDesignationId = d.getDesignationId();
                        break;
                    }
                }
            }

            // --- Selection listener ---
            binding.designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (suppressDesignationSelect) return;
                    DoctorDesignation d = (DoctorDesignation) parent.getSelectedItem();
                    if (d != null) selectedDesignationId = d.getDesignationId();
                }
                @Override public void onNothingSelected(AdapterView<?> parent) { /* no-op */ }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onDoctorGenderGet(List<ModelGender> aList) {
        try {
            if (aList != null) {
                ArrayAdapter<ModelGender> dataAdapter = new ArrayAdapter<>(ActivityDoctorEntry.this, android.R.layout.simple_spinner_item, aList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.genderSpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onDoctorPracticeGet(List<ModelPracticeShift> aList) {
        try {
            if (aList.size() > 0) {
                ArrayAdapter<ModelPracticeShift> dataAdaptera = new ArrayAdapter<>(ActivityDoctorEntry.this, R.layout.spinner_custom_tv, aList);
                binding.practiceSpinner.setAdapter(dataAdaptera);
                binding.practiceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        ModelPracticeShift cmm = (ModelPracticeShift) dataAdaptera.getItem(position);
                        selectedShiftId = cmm.getPracticeShiftId();
                        selectedshiftTypeName = cmm.getPracticeShift();
                        if (selectedshiftTypeName.equals("Morning")) {
                            is_Shift = true;
                        }
                        if (selectedshiftTypeName.equals("Evening")) {
                            is_Shift = true;
                        }
                        if (selectedshiftTypeName.equals("Both")) {
                            is_Shift = true;
                        }

                        ModelPracticeShift isit = new ModelPracticeShift();
                        isit.setPracticeShiftId(selectedShiftId);
                        isit.setPracticeShift(selectedshiftTypeName);
                        docShiftList.add(isit);

                        shiftAdapter = new AdapterDoctorShift(ActivityDoctorEntry.this, docShiftList);
                        binding.rvPracticelist.setLayoutManager(new LinearLayoutManager(ActivityDoctorEntry.this));
                        binding.rvPracticelist.setHasFixedSize(true);
                        binding.rvPracticelist.setAdapter(shiftAdapter);
                        shiftAdapter.notifyDataSetChanged();
                    }
                });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    // class member হিসেবে রাখলে ভালো (initial trigger skip করতে)
    private boolean marketCustomerSpinnerInitDone = false;

    @Override
    public void onCustomerListByMarketIdGet(List<CustomerViewModel> aList) {
        try {
            if (aList == null || aList.isEmpty()) {
                SnackBarManagement._error_CustomMessage(binding.getRoot(), "No customers found for this market!");
                return;
            }

            // 0) Add placeholder at top
            List<CustomerViewModel> withPlaceholder = new ArrayList<>();
            CustomerViewModel ph = new CustomerViewModel();
            ph.setCustomerMasterId(-1);
            ph.setCustomerName("-- Select Customer --");
            withPlaceholder.add(ph);
            withPlaceholder.addAll(aList);

            // 1) Adapter create / update (disable pos 0 visually)
            ArrayAdapter<CustomerViewModel> dataAdapter;
            if (binding.tagCustomerSpinner.getAdapter() instanceof ArrayAdapter) {
                //noinspection unchecked
                dataAdapter = (ArrayAdapter<CustomerViewModel>) binding.tagCustomerSpinner.getAdapter();
                dataAdapter.clear();
                dataAdapter.addAll(withPlaceholder);
                dataAdapter.notifyDataSetChanged();
            } else {
                dataAdapter = new ArrayAdapter<CustomerViewModel>(
                        ActivityDoctorEntry.this,
                        R.layout.spinner_customer_list,
                        R.id.tv_spinner_item,
                        withPlaceholder
                ) {
                    @Override public boolean isEnabled(int position) { return position != 0; }
                    @Override public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) v;
                        tv.setEnabled(position != 0);
                        tv.setAlpha(position == 0 ? 0.6f : 1f);
                        return v;
                    }
                };
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.tagCustomerSpinner.setAdapter(dataAdapter);

                // Search dialog props
                binding.tagCustomerSpinner.setTitle("Select Customer");
                binding.tagCustomerSpinner.setPositiveButton("Close");
                binding.tagCustomerSpinner.setPrompt("Search by name / code / phone");
            }

            // 2) Force show placeholder (nothing selected)
            binding.tagCustomerSpinner.setSelection(0, false);

            // 3) Bind listener once
            if (!"listener_bound".equals(binding.tagCustomerSpinner.getTag())) {
                binding.tagCustomerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Ignore placeholder row
                        if (position == 0) return;

                        CustomerViewModel selected = (CustomerViewModel) parent.getItemAtPosition(position);
                        if (selected == null) return;

                        int selectedCustId = selected.getCustomerMasterId();
                        String selectedCustName = selected.getCustomerName();

                        // 1) Max limit
                        if (customerList.size() >= 5) {
                            SnackBarManagement._error_CustomMessage(binding.getRoot(), "Maximum 5 customers allowed!");
                            // Revert to placeholder
                            binding.tagCustomerSpinner.setSelection(0, false);
                            return;
                        }

                        // 2) Duplicate
                        for (CustomerViewModel c : customerList) {
                            if (c.getCustomerMasterId() == selectedCustId) {
                                SnackBarManagement._error_CustomMessage(binding.getRoot(), "This customer is already added!");
                                binding.tagCustomerSpinner.setSelection(0, false);
                                return;
                            }
                        }

                        // 3) Add
                        CustomerViewModel newCustomer = new CustomerViewModel();
                        newCustomer.setCustomerMasterId(selectedCustId);
                        newCustomer.setCustomerName(selectedCustName);
                        newCustomer.setChecked(true);
                        customerList.add(newCustomer);

                        // 4) RecyclerView bind/update
                        if (TagCusAdapter == null) {
                            TagCusAdapter = new TagCustAdapter(ActivityDoctorEntry.this, customerList);
                            binding.rvTagCustomer.setLayoutManager(new LinearLayoutManager(ActivityDoctorEntry.this));
                            binding.rvTagCustomer.setAdapter(TagCusAdapter);
                        } else {
                            TagCusAdapter.notifyItemInserted(customerList.size() - 1);
                        }

                        // 5) Reset spinner back to placeholder after adding
                        binding.tagCustomerSpinner.setSelection(0, false);
                    }

                    @Override public void onNothingSelected(AdapterView<?> parent) { }
                });

                binding.tagCustomerSpinner.setTag("listener_bound");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ActivityDoctorEntry.this, "Error loading customer list", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onContactTypeGet(List<DoctorContact> aList) {
        try {
            if (aList.size() > 0) {
                ArrayAdapter<DoctorContact> dataAdapters = new ArrayAdapter<>(ActivityDoctorEntry.this, R.layout.spinner_custom_tv, aList);
                binding.contactTypeSpinner.setAdapter(dataAdapters);
                binding.contactTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        DoctorContact cmm = (DoctorContact) dataAdapters.getItem(position);
                        selectedcontactTypeId = cmm.getContactTypeId();
                        selectedcontactTypeName = cmm.getContactType();
                        contacttype.setText(selectedcontactTypeName);
                        contactName.setText("");
                        til_shiftDD.setHint("Contact " + selectedcontactTypeName);
                        if (cmm.getContactType().equals("Mobile")) {
                            contactName.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                            is_Contact = true;
                            contactName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                        }
                        if (cmm.getContactType().equals("Email")) {
                            contactName.setKeyListener(TextKeyListener.getInstance());
                            contactName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                            is_Contact = true;
                        }
                        popContact.show();
                    }
                });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onBrandGet(List<ModelBrand> aList) {
        try {
            if (aList != null) {
                listItemsBrand = new String[aList.size()];
                for (int i = 0; i < aList.size(); i++) {
                    listItemsBrand[i] = aList.get(i).getProductSQName();
                }
                checkedItems_Brand = new boolean[listItemsBrand.length];

            }
            brandList = new ArrayList<>();
            brandList = dbDoctorHelper.getBrandListFromSQLite();
            if (brandList != null) {
                listItemsBrand = new String[brandList.size()];
                for (int i = 0; i < brandList.size(); i++) {
                    listItemsBrand[i] = brandList.get(i).getProductSQName();
                }
                checkedItems_Brand = new boolean[listItemsBrand.length];
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onPriorityGet(List<ModelPriority> aList) {
        try {
            if (aList != null) {
                ArrayAdapter<ModelPriority> dataAdapter = new ArrayAdapter<>(ActivityDoctorEntry.this, android.R.layout.simple_spinner_item, aList);
               /* dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.doctorCategorySpinner.setAdapter(dataAdapter);*/
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onDocCategoryGet(List<DoctorCategory> aList) {
        try {
            if (aList != null) {
                ArrayAdapter<DoctorCategory> dataAdapter =
                        new ArrayAdapter<>(ActivityDoctorEntry.this,
                                android.R.layout.simple_spinner_item, aList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.doctorCategorySpinner.setAdapter(dataAdapter);
            }

            // --- Edit mode preselect ---
            if (e_selectedDoctorCategoryIdEdit > 0 && aList != null) {
                for (int i = 0; i < aList.size(); i++) {
                    DoctorCategory c = aList.get(i);
                    if (c != null && c.getCategoryId() == e_selectedDoctorCategoryIdEdit) {
                        suppressDocCategorySelect = true;
                        binding.doctorCategorySpinner.setSelection(i);
                        suppressDocCategorySelect = false;
                        selectedDoctorCategoryId = c.getCategoryId();
                        break;
                    }
                }
            }

            // --- Selection listener ---
            binding.doctorCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (suppressDocCategorySelect) return;
                    DoctorCategory c = (DoctorCategory) parent.getItemAtPosition(position);
                    if (c == null) return;

                    selectedDoctorCategoryId = c.getCategoryId();

                    // If you have a cascade (Category -> DoctorType), trigger it here:
                    // if (hasCategoryTypeCascade) presenter.getDoctorTypes(selectedDoctorCategoryId);
                }

                @Override public void onNothingSelected(AdapterView<?> parent) { /* no-op */ }
            });

            // If category was preselected in edit mode and you cascade, kick off child load once:
            // if (e_selectedDoctorCategoryIdEdit > 0 && hasCategoryTypeCascade) {
            //     presenter.getDoctorTypes(selectedDoctorCategoryId);
            // }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    @Override
    public void onSubmitSuccess(String mesg) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        new androidx.appcompat.app.AlertDialog.Builder(ActivityDoctorEntry.this)
                .setTitle("Success")
                .setMessage("Doctor Saved Successfully")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }

                }).setCancelable(false).show();
    }

    @Override
    public void onSubmitError(String mesg) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        SnackBarManagement._error_CustomMessage(binding.getRoot(), mesg);
    }

    @Override
    public void vGroup(List<ModelGroup> groupList) {
        try {
            if (groupList != null) {
                ArrayAdapter<ModelGroup> dataAdapter = new ArrayAdapter<>(ActivityDoctorEntry.this, android.R.layout.simple_spinner_item, groupList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.groupSpinner.setAdapter(dataAdapter);

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vRegion(List<ModelRegion> regionList) {
        try {
            if (regionList != null) {
                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(ActivityDoctorEntry.this, android.R.layout.simple_spinner_item, regionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.regionSpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void vArea(List<ModelArea> areaList) {
        try {
            if (areaList != null) {
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(ActivityDoctorEntry.this, android.R.layout.simple_spinner_item, areaList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.areaSpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void vTeritory(List<ModelTeritorry> teritoryList) {
        try {
            if (teritoryList != null) {
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(ActivityDoctorEntry.this, android.R.layout.simple_spinner_item, teritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.territorySpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void vSTeritory(List<ModelSubTeritorry> steritoryList) {
        try {
            if (steritoryList != null) {
                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(ActivityDoctorEntry.this, android.R.layout.simple_spinner_item, steritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.sterritorySpinner.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vMarket(List<ModelMarket> marketList) {
        try {
            if (marketList != null && !marketList.isEmpty()) {
                ArrayAdapter<ModelMarket> dataAdapter =
                        new ArrayAdapter<>(ActivityDoctorEntry.this,
                                android.R.layout.simple_spinner_item, marketList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.marketSpinner.setAdapter(dataAdapter);
            }

            // --- Edit mode preselect ---
            if (e_selectedMarketIdEdit > 0 && marketList != null && !marketList.isEmpty()) {
                for (int i = 0; i < marketList.size(); i++) {
                    ModelMarket m = marketList.get(i);
                    if (m.getMarketId() == e_selectedMarketIdEdit) {
                        suppressCascade = true;
                        binding.marketSpinner.setSelection(i, false);
                        suppressCascade = false;
                        selectedMarketId = m.getMarketId();
                        Log.d("PreSelect", "Market preselected: " + selectedMarketId);
                        break;
                    }
                }
            }

            // --- Cascade: Market → Customer ---
            binding.marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (suppressCascade) return;

                        if (marketList == null || marketList.isEmpty()) return;
                        if (position < 0 || position >= marketList.size()) return;

                        ModelMarket selected = marketList.get(position);
                        if (selected != null) {
                            selectedMarketId = selected.getMarketId();
                            Log.d("MarketSelected", "Selected Market ID: " + selectedMarketId);

                            // ✅ call your customer loader here
                           //LoadCustomerListByMarketID(selectedMarketId);

                            presenterDoctor.getCustomerListByMarketId(selectedMarketId);
                        }
                    } catch (Exception e) {
                        Log.e("MarketSpinner", "onItemSelected error: " + e.getMessage());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("vMarket", "Error binding spinner: " + e.getMessage());
        }
    }

    // ---- Edit-mode target IDs (bindDoctorToUi থেকে set করবে) ----

    // ---- Edit-mode target IDs (bindDoctorToUi থেকে set করবে) ----


    @Override
    public void DivisionList(List<ModelDivision> divisionList) {
        try {
            if (divisionList != null) {
                ArrayAdapter<ModelDivision> dataAdapter =
                        new ArrayAdapter<>(ActivityDoctorEntry.this,
                                android.R.layout.simple_spinner_item, divisionList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.divisionSpinner.setAdapter(dataAdapter);
            }

            // --- Edit mode preselect ---
            if (e_selectedDivisionIdEdit > 0 && divisionList != null) {
                for (int i = 0; i < divisionList.size(); i++) {
                    ModelDivision d = divisionList.get(i);
                    if (d.getDivisionId() == e_selectedDivisionIdEdit) {
                        suppressCascade = true;
                        binding.divisionSpinner.setSelection(i);
                        suppressCascade = false;
                        selectedDivisionId = d.getDivisionId();
                        break;
                    }
                }
            }

            // --- Cascade: Division → District ---
            binding.divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (suppressCascade) return;
                    ModelDivision d = (ModelDivision) parent.getSelectedItem();
                    if (d == null) return;

                    selectedDivisionId = d.getDivisionId();

                    if (e_selectedDistrictIdEdit > 0) {
                        // presenter will load districts (and we will preselect in DistrictList)
                        presentarDDT.getDistrict(selectedDivisionId);

                    } else {
                        presentarDDT.getDistrict(selectedDivisionId);
                    }
                }
                @Override public void onNothingSelected(AdapterView<?> parent) { }
            });

            // If division preselected by edit-mode, trigger district load once
            if (e_selectedDivisionIdEdit > 0) {

                presentarDDT.getDistrict(selectedDivisionId);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void DistrictList(List<ModelDistrict> districtList) {
        try {
            if (districtList != null) {
                ArrayAdapter<ModelDistrict> dataAdapter =
                        new ArrayAdapter<>(ActivityDoctorEntry.this,
                                android.R.layout.simple_spinner_item, districtList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.districtSpinner.setAdapter(dataAdapter);
            }

            // --- Edit mode preselect ---
            if (e_selectedDistrictIdEdit > 0 && districtList != null) {
                for (int i = 0; i < districtList.size(); i++) {
                    ModelDistrict d = districtList.get(i);
                    if (d.getDistrictId() == e_selectedDistrictIdEdit) {
                        suppressCascade = true;
                        binding.districtSpinner.setSelection(i);
                        suppressCascade = false;
                        selectedDistrictId = d.getDistrictId();
                        break;
                    }
                }
            }

            // --- Cascade: District → Thana ---
            binding.districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (suppressCascade) return;
                    ModelDistrict d = (ModelDistrict) parent.getSelectedItem();
                    if (d == null) return;

                    selectedDistrictId = d.getDistrictId();

                    if (e_selectedThanaIdEdit > 0) {
                        // load thanas; preselect will be applied in ThanaList

                        presentarDDT.getThana(selectedDistrictId);
                    } else {
                        presentarDDT.getThana(selectedDistrictId);
                    }
                }
                @Override public void onNothingSelected(AdapterView<?> parent) { }
            });

            // If district preselected by edit-mode, trigger thana load once
            if (e_selectedDistrictIdEdit > 0) {
                presentarDDT.getThana(selectedDistrictId);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    @Override
    public void ThanaList(List<ModelThana> thanaList) {
        try {
            if (thanaList == null || thanaList.isEmpty()) {
                binding.thanaSpinner.setAdapter(null);
                return;
            }

            ArrayAdapter<ModelThana> dataAdapter =
                    new ArrayAdapter<>(ActivityDoctorEntry.this,
                            android.R.layout.simple_spinner_item, thanaList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.thanaSpinner.setAdapter(dataAdapter);

            // ---- Edit-mode preselect ----
            if (e_selectedThanaIdEdit > 0) {
                for (int i = 0; i < thanaList.size(); i++) {
                    ModelThana t = thanaList.get(i);
                    if (t != null && t.getThanaId() == e_selectedThanaIdEdit) {
                        // leaf-level; no further cascade, but keep suppress for safety
                        suppressCascade = true;
                        binding.thanaSpinner.setSelection(i);
                        suppressCascade = false;
                        break;
                    }
                }
            }

            // (Optional) set listener if you need side-effects on Thana change
            binding.thanaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { /* no child */ }
                @Override public void onNothingSelected(AdapterView<?> parent) { }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Constants.From.equals("78911") && resultCode == RESULT_OK) {
            imageuri = data.getData();
            if (imageuri != null) {
                binding.imgaeView.setImageURI(imageuri);
                binding.imgaeView.setVisibility(View.VISIBLE);
                path = getUriRealPathAboveKitkat(ActivityDoctorEntry.this, imageuri);
                if (path == null)
                    return;
                imgFile = new File(path);
                try {
                    imgFile = new Compressor(ActivityDoctorEntry.this).compressToFile(imgFile);
                    System.out.println("file" + imgFile);
                } catch (IOException e) {
                    Toast.makeText(ActivityDoctorEntry.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    private String getUriRealPathAboveKitkat(ActivityDoctorEntry activity, Uri contentURI) {
        String ret = "";
        if (activity != null && contentURI != null) {
            if (CameraHelper.isContentUri(contentURI)) {
                //if(isGooglePhotoDoc(contentURI.getAuthority()))
                if (CameraHelper.isGooglePhotoDoc(contentURI.getAuthority())) {
                    ret = contentURI.getLastPathSegment();
                } else {
                    ret = getImageRealPath(getContentResolver(), contentURI, null);
                }
            } else if (CameraHelper.isFileUri(contentURI)) {
                ret = contentURI.getPath();
            } else if (CameraHelper.isDocumentUri(activity, contentURI)) {
                // Get uri related document id.
                String documentId = DocumentsContract.getDocumentId(contentURI);
                // Get uri authority.
                String uriAuthority = contentURI.getAuthority();
                if (CameraHelper.isMediaDoc(uriAuthority)) {
                    String[] idArr = documentId.split(":");
                    if (idArr.length == 2) {
                        // First item is document type.
                        String docType = idArr[0];
                        // Second item is document real id.
                        String realDocId = idArr[1];
                        // Get content uri by document type.
                        Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        if ("image".equals(docType)) {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        }
                        // Get where clause with real document id.
                        String whereClause = MediaStore.Images.Media._ID + " = " + realDocId;
                        ret = getImageRealPath(getContentResolver(), mediaContentUri, whereClause);
                    }
                } else if (CameraHelper.isDownloadDoc(uriAuthority)) {
                    // Build download URI.
                    Uri downloadUri = Uri.parse("content://downloads/public_downloads");
                    // Append download document id at URI end.
                    Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));
                    ret = getImageRealPath(getContentResolver(), downloadUriAppendId, null);
                } else if (CameraHelper.isExternalStoreDoc(uriAuthority)) {
                    String[] idArr = documentId.split(":");
                    if (idArr.length == 2) {
                        String type = idArr[0];
                        String realDocId = idArr[1];
                        if ("primary".equalsIgnoreCase(type)) {
                            ret = Environment.getExternalStorageDirectory() + "/" + realDocId;
                        }
                    }
                }
            }
        }
        return ret;
    }

    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause) {
        String ret = "";
        // Query the URI with the condition.
        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);
        if (cursor != null) {
            boolean moveToFirst = cursor.moveToFirst();
            if (moveToFirst) {
                // Get columns name by URI type.
                String columnName = MediaStore.Images.Media.DATA;
                if (uri == MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Images.Media.DATA;
                } else if (uri == MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Audio.Media.DATA;
                } else if (uri == MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Video.Media.DATA;
                }
                // Get column index.
                int imageColumnIndex = cursor.getColumnIndex(columnName);
                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex);
            }
        }
        return ret;
    }
}
