package com.saidur.creatrix.unicorn.salessolution.UI.Customer;

import static com.saidur.creatrix.unicorn.salessolution.UI.Order.PlaceOrderActivity.MY_PERMISSIONS_REQUEST_LOCATION;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCustomerHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ICustomerAdd;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketStracture;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.CustomerSvModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.CustomerType;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelProviderType;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ProgramType;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.StationType;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterCustomer;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterMarketStructure;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.CameraHelper;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityAddCustomerBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;

public class AddCustomerActivity extends AppCompatActivity implements LocationListener, ICustomerAdd.View, IMarketStracture.View {
    ActivityAddCustomerBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static final int PICK_IMAGE = 1;
    public static final int PICK_TIMAGE = 2;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PIC_REQUEST = 11;
    private static int CAMERA_PIC_REQUEST2;
    private static int TCAMERA_PIC_REQUEST2;
    ICustomerAdd.Presenter presenter;
    IMarketStracture.Presenter mkpresenter;
    ProgressDialog pd;


    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;

    DBCrudHelper dbCrudHelper;
    DBCustomerHelper dbCustomer;
    boolean is_Edit = false;


    int seletedRegionId, seletedAreaId, seletedTeritoryId, seletedSTeritoryId, selectedMarketId;
    Uri imageuri, timageuri;
    File imgFile, timgFile;
    Double lat, lon;
    String address = "";
    CustomerSvModel csv;
    Gson gson = new Gson();
    int custmasterid;
    String path, tpath;
    ModelSession session;
    public String blockCharacterSet = "@~'^|$%&*!/?>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(view -> finish());

        //Blocking Special char
        binding.customerName.setFilters(new InputFilter[]{filter});
        binding.address.setFilters(new InputFilter[]{filter});
        binding.cmistOwnerName.setFilters(new InputFilter[]{filter});
        binding.cmistTradeLicense.setFilters(new InputFilter[]{filter});

        dbCustomer = new DBCustomerHelper(AddCustomerActivity.this);
        dbCrudHelper = new DBCrudHelper(AddCustomerActivity.this);
        presenter = new PresenterCustomer(this, AddCustomerActivity.this);
        mkpresenter = new PresenterMarketStructure(this, AddCustomerActivity.this);
        session=dbCrudHelper.getSession();
        int empId = session.getEmpInfoId();
        String roleType = session.getRoleType();

        if (ContextCompat.checkSelfPermission(AddCustomerActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(AddCustomerActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddCustomerActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        locationManager = (LocationManager) AddCustomerActivity.this.getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getCLocation();

        if (!NetworkInformation.isConnected(this)) {
            checkLocationPermission();
        } else {
        }
        presenter.GetProviderType();
        mkpresenter.GetGroupLocal();
        mkpresenter.GetRegionLocal(0);
        mkpresenter.GetAreaLocal(0);
        mkpresenter.GetTeritoryLocal(0);
        mkpresenter.GetSTeritoryLocal(0);
        mkpresenter.GetMarketLocal(0);
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
                    mkpresenter.GetAreaLocal(seletedRegionId);
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
                    mkpresenter.GetTeritoryLocal(seletedAreaId);
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
                    mkpresenter.GetSTeritoryLocal(seletedTeritoryId);
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
                    mkpresenter.GetMarketLocal(seletedSTeritoryId);
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

            binding.btnSubmit.setOnClickListener(v -> {
                String img_str = null;
                try {

                    Bitmap bitmap = new Compressor(AddCustomerActivity.this).compressToBitmap(imgFile);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                    byte[] image = stream.toByteArray();
                    img_str = Base64.encodeToString(image, 0);
                    System.out.println("img : " + img_str);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                String timg_str = null;
                try {
                    if (timgFile != null) {
                        Bitmap bitmap2 = new Compressor(this).compressToBitmap(timgFile);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                        byte[] image = stream.toByteArray();
                        timg_str = Base64.encodeToString(image, 0);
                        System.out.println("img : " + timg_str);
                    } else {
                        timg_str = "";
                    }

                } catch (IOException e) {
                    Toast.makeText(AddCustomerActivity.this, "Image Exception", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(binding.customerMobile.getText().toString())) {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Mobile is required");
                    return;
                }
                if (binding.customerMobile.getText().toString().trim().length() < 11) {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Mobile Can't Be Less Than 11");
                    return;
                }
                if (TextUtils.isEmpty(binding.customerName.getText().toString())) {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Name is required");
                    return;
                }
                if (TextUtils.isEmpty(binding.address.getText().toString())) {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Address is required");
                    return;
                }
                if (TextUtils.isEmpty(binding.cmistOwnerName.getText().toString())) {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Owner Name is required");
                    return;
                }
                if (binding.imgaeView.getDrawable() == null || img_str == null) {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Shop Image required");
                    return;
                }
                if (lat == null || lon == null) {
                    // String.valueOf(lat) used to be checked with TextUtils.isEmpty(), but
                    // String.valueOf(null) returns the literal text "null" - never empty -
                    // so this guard silently let missing coordinates through to SaveCustomer().
                    SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Your Latitude/Longitude is not ready yet. Please wait a moment and try again.");
                    getCLocation();
                    return;
                }

                SaveCustomer(custmasterid, empId, img_str, timg_str, "AddCust");
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        binding.camera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(AddCustomerActivity.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                CAMERA_PIC_REQUEST2 = 78911;
                Constants.From = "78911";
                ImagePicker.Companion.with(AddCustomerActivity.this)
                        .crop()
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            } else {
                ActivityCompat.requestPermissions(AddCustomerActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
            }
        });
        binding.tcamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(AddCustomerActivity.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                TCAMERA_PIC_REQUEST2 = 83411;
                Constants.From = "83411";
                ImagePicker.Companion.with(AddCustomerActivity.this)
                        .crop()
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            } else {
                ActivityCompat.requestPermissions(AddCustomerActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
            }
        });
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
                                ActivityCompat.requestPermissions(AddCustomerActivity.this,
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
        LocationManager lm = (LocationManager) AddCustomerActivity.this.getSystemService(Context.LOCATION_SERVICE);
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
            new AlertDialog.Builder(AddCustomerActivity.this)
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
        boolean fine = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        boolean coarse = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        if (!fine && !coarse) {
            return;
        }
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            // GPS_PROVIDER first: NETWORK_PROVIDER alone depends on Play services'
            // network geolocation backend, which can silently never resolve on some
            // devices/coverage areas even with GPS + permission both on.
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, this);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            Location last = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (last == null) {
                last = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (last != null) {
                onLocationChanged(last);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void SaveCustomer(int custmasterid, int empId, String img_str, String timg_str, String who) {
        CustomerSvModel csm = new CustomerSvModel();
        try {
            if (String.valueOf(custmasterid).isEmpty()) {
                csm.setCustomerMasterId(0);
            } else {
                csm.setCustomerMasterId(custmasterid);
            }
            csm.setImageBase64String(img_str);
            csm.setTradeLicenseImg(timg_str);
            csm.setTermOfPayment("Cash");

          /*  ModelProviderType ptype = (ModelProviderType) binding.ptypeSpinner.getSelectedItem();
            int ptypeId = ptype.getProviderTypeId();*/

            csm.setProgramTypeId(0);
            csm.setSMCTypeId(0);
            csm.setCustomerName(binding.customerName.getText().toString());
            csm.setAddress(binding.address.getText().toString());
            csm.setCellNo(binding.customerMobile.getText().toString());
            csm.setConPerson(binding.cmistOwnerName.getText().toString());
            csm.setVoterID(binding.cmistNid.getText().toString());
          //  csm.setTradeLicense(binding.cmistTradeLicense.getText().toString());

            csm.setMarketId(selectedMarketId);
            if (lat == null && lon == null) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "Your Latitude and Longitude Getting Null.Try Again");
                return;
            } else {
                csm.setLatitude(String.valueOf(lat));
                csm.setLongitude(String.valueOf(lon));
            }
            csm.setStreetAddress(address);
            csm.setEmpId(empId);
            Gson gson = new Gson();
            String data = gson.toJson(csm);
            System.out.println("save " + data);
            presenter.SaveCustomer(csm, who);
        } catch (Exception ex) {
            ex.printStackTrace();
            SnackBarManagement._warning_CustomMessage(binding.getRoot(),"Some thing went wrong. Try again");
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
                path = getUriRealPathAboveKitkat(AddCustomerActivity.this, imageuri);
                if (path == null)
                    return;
                imgFile = new File(path);
                try {
                    imgFile = new Compressor(AddCustomerActivity.this).compressToFile(imgFile);
                    System.out.println("file" + imgFile);
                } catch (IOException e) {
                    Toast.makeText(AddCustomerActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (Constants.From.equals("83411") && resultCode == RESULT_OK) {
            timageuri = data.getData();
            if (timageuri != null) {
                binding.timgaeView.setImageURI(timageuri);
                binding.timgaeView.setVisibility(View.VISIBLE);
                tpath = getUriRealPathAboveKitkat(AddCustomerActivity.this, timageuri);
                if (tpath == null)
                    return;
                timgFile = new File(tpath);
                try {
                    timgFile = new Compressor(AddCustomerActivity.this).compressToFile(timgFile);
                    System.out.println("file" + timgFile);
                } catch (IOException e) {
                    Toast.makeText(AddCustomerActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    private String getUriRealPathAboveKitkat(AddCustomerActivity activity, Uri contentURI) {
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Use the actual GPS/network fix for lat/lon. Reverse-geocoding needs a
        // network round-trip and must never gate whether we accept the coordinates -
        // it previously did, so lat/lon stayed null whenever geocoding failed
        // (no data connection, geocoder backend unavailable, etc.) even though a
        // valid fix had already arrived.
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses != null && !addresses.isEmpty()) {
                address = addresses.get(0).getAddressLine(0);
            }
        } catch (Exception e) {
            // best-effort only; keep the coordinates we already captured
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Without this, granting the runtime prompt did nothing: getCLocation() had
        // already returned (permission not yet granted at call time) and was never
        // retried, so lat/lon stayed null for the rest of the session.
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                getCLocation();
                break;
            }
        }
    }
    private int getIndex(Spinner designationSpinner, String edit_dname) {
        for (int i = 0; i < designationSpinner.getCount(); i++) {
            if (designationSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(edit_dname)) {
                return i;
            }
        }
        return 0;
    }
    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    public void onProgramType(List<ProgramType> ptype) {

    }

    @Override
    public void onProviderType(List<ModelProviderType> ptype) {
        ArrayAdapter<ModelProviderType> dataAdapter = new ArrayAdapter<>(AddCustomerActivity.this, android.R.layout.simple_spinner_item, ptype);// dbDoctor.getProgramTypeListFromSQLite(0));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.ptypeSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onCustomerTypeReceived(List<CustomerType> aList) {

    }

    @Override
    public void onStationReceived(List<StationType> aList) {

    }

    @Override
    public void onSubmitSuccess(String mesg, String who) {
        if (who.equals("AddCust")) {
            new androidx.appcompat.app.AlertDialog.Builder(AddCustomerActivity.this)
                    .setTitle("Success")
                    .setMessage(mesg)
                    .setPositiveButton("OK", (dialog, which) -> {
                        dialog.cancel();
                        Intent i = new Intent(AddCustomerActivity.this, MainActivity.class);
                        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }).setCancelable(false).show();
        }
      /*  if (who.equals("CustEdit")) {
            new androidx.appcompat.app.AlertDialog.Builder(AddCustomerActivity.this)
                    .setTitle("Success")
                    .setMessage("Customer Updated")
                    .setPositiveButton("OK", (dialog, which) -> {
                        dialog.cancel();
                        Intent i = new Intent(AddCustomerActivity.this, CustomerApprovalListActivity.class);
                        // i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }).setCancelable(false).show();
        }*/
    }

    @Override
    public void onSubmitError(String mesg) {
        new androidx.appcompat.app.AlertDialog.Builder(AddCustomerActivity.this)
                .setTitle("Error")
                .setMessage(mesg)
                .setPositiveButton("OK", (dialog, which) -> dialog.cancel()).setCancelable(false).show();
    }

    @Override
    public void vGroup(List<ModelGroup> groupList) {
        try {
            if (groupList != null) {
                ArrayAdapter<ModelGroup> dataAdapter = new ArrayAdapter<>(AddCustomerActivity.this, android.R.layout.simple_spinner_item, groupList);
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
                ArrayAdapter<ModelRegion> dataAdapter = new ArrayAdapter<>(AddCustomerActivity.this, android.R.layout.simple_spinner_item, regionList);
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
                ArrayAdapter<ModelArea> dataAdapter = new ArrayAdapter<>(AddCustomerActivity.this, android.R.layout.simple_spinner_item, areaList);
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
                ArrayAdapter<ModelTeritorry> dataAdapter = new ArrayAdapter<>(AddCustomerActivity.this, android.R.layout.simple_spinner_item, teritoryList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.territorySpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void vSTeritory(List<ModelSubTeritorry> steritoryList) {
        String steri = gson.toJson(steritoryList);
        System.out.println(steri);
        try {
            if (steritoryList != null) {
                ArrayAdapter<ModelSubTeritorry> dataAdapter = new ArrayAdapter<>(AddCustomerActivity.this, android.R.layout.simple_spinner_item, steritoryList);
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
            if (marketList != null) {
                ArrayAdapter<ModelMarket> dataAdapter = new ArrayAdapter<>(AddCustomerActivity.this, android.R.layout.simple_spinner_item, marketList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.marketSpinner.setAdapter(dataAdapter);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}