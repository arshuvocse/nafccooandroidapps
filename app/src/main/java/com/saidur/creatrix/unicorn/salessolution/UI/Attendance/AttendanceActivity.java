package com.saidur.creatrix.unicorn.salessolution.UI.Attendance;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IAttendance;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelAttendance;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseButton;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterAttendance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.CameraHelper;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.DateTimeUtil;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityAttendanceBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;

public class AttendanceActivity extends AppCompatActivity implements OnMapReadyCallback, IAttendance.View {
    ActivityAttendanceBinding binding;
    ProgressDialog pd;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    Uri imageuri;
    File imgFile;
    String lat, lng, address, path, worksession = "";


    IAttendance.Presenter presenter;
    DBCrudHelper dbCrudHelper;

    int CAMERA_REQUEST_In2;
    boolean CAMERA_REQUEST_In2B=false;
    int CAMERA_REQUEST_Out2;
    boolean CAMERA_REQUEST_Out2B=false;

    //Data save server
    String formattedDate, time;
    int empId;
    ActivityResultLauncher<String> camPermission;
    ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (CAMERA_REQUEST_In2 == 78911 && result.getResultCode() == RESULT_OK) {
                        if (result != null) {
                            imageuri = result.getData().getData();
                            if (imageuri != null) {
                                path = getUriRealPathAboveKitkat(AttendanceActivity.this, imageuri);
                                if (path == null)
                                    return;
                                imgFile = new File(path);
                                String img_str = null;
                                try {
                                    imgFile = new Compressor(AttendanceActivity.this).compressToFile(imgFile);
                                    Bitmap bitmap = new Compressor(getApplicationContext()).compressToBitmap(imgFile);
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                                    byte[] image = stream.toByteArray();
                                    img_str = Base64.encodeToString(image, 0);
                                    System.out.println("img : " + img_str);
                                    // Toast.makeText(AttendanceActivity.this, "Hit Punchin", Toast.LENGTH_SHORT).show();
                                    hitPunchIn(img_str);
                                } catch (IOException e) {
                                    Toast.makeText(AttendanceActivity.this, "Image Exception", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    } else if (CAMERA_REQUEST_Out2 == 83411 && result.getResultCode() == RESULT_OK) {
                        try {
                            if (result != null) {
                                imageuri = result.getData().getData();
                                if (imageuri != null) {
                                    path = getUriRealPathAboveKitkat(AttendanceActivity.this, imageuri);
                                    if (path == null)
                                        return;
                                    imgFile = new File(path);
                                    String img_stro = null;
                                    try {
                                        imgFile = new Compressor(AttendanceActivity.this).compressToFile(imgFile);
                                        Bitmap bitmap = new Compressor(getApplicationContext()).compressToBitmap(imgFile);
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                                        byte[] image = stream.toByteArray();
                                        img_stro = Base64.encodeToString(image, 0);
                                        System.out.println("img : " + img_stro);
                                        // Toast.makeText(AttendanceActivity.this, "Hit Punchout", Toast.LENGTH_SHORT).show();
                                        hitPunchOut(img_stro);
                                    } catch (IOException e) {
                                        Toast.makeText(AttendanceActivity.this, "Image Exception", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Camera Closed");
                    }

                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        camPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
               if(result)
               {

               }
            }
        });
        pd = new ProgressDialog(AttendanceActivity.this);
        presenter = new PresenterAttendance(this, this);
        dbCrudHelper = new DBCrudHelper(this);

        empId = dbCrudHelper.getUser().getEmpInfoId();


        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());
        initCmeraPermission();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        lat = null;
        lng = null;
        LocationManager locationManagerdd = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManagerdd.isProviderEnabled(locationManagerdd.GPS_PROVIDER)) {
        } else {
            showGPSDisabledAlertToUser();
        }
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        formattedDate = DateTimeUtil.getDisplayDate(c);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        time = dateFormat.format(new Date());
        binding.punchInTime.setText(time);
        binding.punchOutTime.setText(time);
        binding.punchInDate.setText(formattedDate);
        binding.punchOutDate.setText(formattedDate);
        binding.dTXT.setText(formattedDate);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        binding.btnPunchIn.setVisibility(View.GONE);
        LoadTourType();
        binding.morning.setOnClickListener(view -> {
            binding.morning.setBackgroundResource(R.drawable.shape_oval_white_selected);
            binding.morningout.setBackgroundResource(R.drawable.shape_oval_white_selected);
            binding.evening.setBackgroundResource(R.drawable.shape_oval_black_sole);
            binding.evening.setColorFilter(ContextCompat.getColor(AttendanceActivity.this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            worksession = "Morning";
            hitApi_Btn(empId, "Morning");
        });
        binding.morningout.setOnClickListener(view -> {
            binding.morningout.setBackgroundResource(R.drawable.shape_oval_white_selected);
            binding.eveningout.setBackgroundResource(R.drawable.shape_oval_black_sole);
            binding.eveningout.setColorFilter(ContextCompat.getColor(AttendanceActivity.this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            worksession = "Morning";
            hitApi_Btn(empId, "Morning");
        });
        binding.evening.setOnClickListener(view -> {
            binding.morning.setBackgroundResource(R.drawable.shape_oval_white_sole);
            binding.evening.setBackgroundResource(R.drawable.shape_oval_white_selected);
            binding.evening.setColorFilter(ContextCompat.getColor(AttendanceActivity.this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
            worksession = "Evening";
            hitApi_Btn(empId, "Evening");
        });
        binding.eveningout.setOnClickListener(view -> {
            binding.morningout.setBackgroundResource(R.drawable.shape_oval_white_sole);
            binding.eveningout.setBackgroundResource(R.drawable.shape_oval_white_selected);
            binding.eveningout.setColorFilter(ContextCompat.getColor(AttendanceActivity.this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
            worksession = "Evening";
            hitApi_Btn(empId, "Evening");
        });


        binding.btnPunchIn.setOnClickListener(v -> {
            if (!NetworkInformation.isConnected(AttendanceActivity.this)) {
                SnackBarManagement.NoInternetSnackbar(binding.masterLayout);
                Intent i = new Intent(AttendanceActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                //startCamera.launch(78911);
                CAMERA_REQUEST_In2 = 78911;
                CAMERA_REQUEST_In2B=true;
                handleCaptureImage("In", CAMERA_REQUEST_In2,CAMERA_REQUEST_In2B);
            }

        });
        binding.btnPunchOut.setOnClickListener(v -> {
            if (!NetworkInformation.isConnected(AttendanceActivity.this)) {
                SnackBarManagement.NoInternetSnackbar(binding.masterLayout);
                Intent i = new Intent(AttendanceActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                CAMERA_REQUEST_Out2 = 83411;
                CAMERA_REQUEST_Out2B=true;
                handleCaptureImage("Out", CAMERA_REQUEST_Out2,CAMERA_REQUEST_Out2B);
            }
        });
        binding.attRefresh.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
        });
    }

    private void LoadTourType() {
        List<ModelStationType> ms = dbCrudHelper.getStationTypeListFromSQLite();
        // String steri = gson.toJson(steritoryList);
        //System.out.println(steri);
        try {
            if (ms != null) {
                ArrayAdapter<ModelStationType> dataAdapter = new ArrayAdapter<>(AttendanceActivity.this, android.R.layout.simple_spinner_item, ms);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerStation.setAdapter(dataAdapter);
               // binding.spinnerStationOut.setAdapter(dataAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void hitApi_Btn(int empId, String wsession) {
        pd.setMessage("Preparing Attendance..");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        presenter.button(empId, wsession);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        // mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                lat = String.valueOf(location.getLatitude());
                lng = String.valueOf(location.getLongitude());
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                    //addresses.get(0).getLocality();
                    // addresses.get(1).getSubLocality();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    //      markerOptions.title("Current Position");
                    address = addresses.get(0).getAddressLine(0);
                    markerOptions.title(addresses.get(0).getAddressLine(0));
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                    //move map camera
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AttendanceActivity.this,
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onSuccess(String message, boolean status, int type) {
        try {
            if (status) {
                SnackBarManagement._success_CustomMessage(binding.masterLayout, message);
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_alertz)
                        .setTitle("Success")
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                            presenter.button(empId, worksession);
                            finish();
                        })
                        .show();
            }
            if (!status) {
                SnackBarManagement._error_CustomMessage(binding.masterLayout, "Something Went Wrong!!");
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onError(String message, int type) {
        if ((pd != null) && pd.isShowing()) {
            pd.dismiss();
        }
        SnackBarManagement._error_CustomMessage(binding.masterLayout, message);
    }

    @Override
    public void onButtonView(ResponseButton buttonRP) {
        if ((pd != null) && pd.isShowing()) {
            pd.dismiss();
        }

        if (buttonRP.getPunchInBtn().equals("OFF") && buttonRP.getPunchOUTBtn().equals("OFF")) {
            binding.punchInDIv.setVisibility(View.GONE);
            binding.punchOutDiv.setVisibility(View.GONE);
            binding.divAttCompleted.setVisibility(View.VISIBLE);
        } else if (buttonRP.getPunchInBtn().equals("ON") && buttonRP.getPunchOUTBtn().equals("OFF")) {
            binding.punchInDIv.setVisibility(View.VISIBLE);
            binding.btnPunchIn.setVisibility(View.VISIBLE);
            binding.punchOutDiv.setVisibility(View.GONE);
            binding.divAttCompleted.setVisibility(View.GONE);
        } else if (buttonRP.getPunchInBtn().equals("OFF") && buttonRP.getPunchOUTBtn().equals("ON")) {
            binding.punchInDIv.setVisibility(View.GONE);
            binding.punchOutDiv.setVisibility(View.VISIBLE);
            binding.btnPunchOut.setVisibility(View.VISIBLE);
            binding.divAttCompleted.setVisibility(View.GONE);
        }

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                (dialog, id) -> {
                    dialog.cancel();
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void handleCaptureImage(String type, int request,boolean valo) {
        if (type.equals("In") && valo==true && request == 78911) {
            try {
                //CAMERA_REQUEST_In2=78911;
                ImagePicker.Companion.with(AttendanceActivity.this)
                        .cameraOnly()
                        .crop()
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
          /*      createIntent { intent ->
                        startCamera.launch(intent)
                }

                ImagePicker.Companion.with(this)
                        .compress(1024)         //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                        .createIntent { intent ->
                        startCamera.launch(intent)
                }*/

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (type.equals("Out") && valo==true&& request == 83411) {
            try {
                ImagePicker.Companion.with(AttendanceActivity.this)
                        .cameraOnly()
                        .crop()
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (CAMERA_REQUEST_In2B==true && CAMERA_REQUEST_In2 == 78911 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageuri = data.getData();
                if (imageuri != null) {
                    path = getUriRealPathAboveKitkat(AttendanceActivity.this, imageuri);
                    if (path == null)
                        return;
                    imgFile = new File(path);
                    String img_str = null;
                    try {
                        imgFile = new Compressor(AttendanceActivity.this).compressToFile(imgFile);
                        Bitmap bitmap = new Compressor(this).compressToBitmap(imgFile);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                        byte[] image = stream.toByteArray();
                        img_str = Base64.encodeToString(image, 0);
                        System.out.println("img : " + img_str);
                        // Toast.makeText(AttendanceActivity.this, "Hit Punchin", Toast.LENGTH_SHORT).show();
                        hitPunchIn(img_str);
                    } catch (IOException e) {
                        Toast.makeText(AttendanceActivity.this, "Image Exception", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } else if (CAMERA_REQUEST_Out2B==true && CAMERA_REQUEST_Out2 == 83411 && resultCode == Activity.RESULT_OK) {
            try {
                if (data != null) {
                    imageuri = data.getData();
                    if (imageuri != null) {
                        path = getUriRealPathAboveKitkat(AttendanceActivity.this, imageuri);
                        if (path == null)
                            return;
                        imgFile = new File(path);
                        String img_stro = null;
                        try {
                            imgFile = new Compressor(AttendanceActivity.this).compressToFile(imgFile);
                            Bitmap bitmap = new Compressor(this).compressToBitmap(imgFile);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                            byte[] image = stream.toByteArray();
                            img_stro = Base64.encodeToString(image, 0);
                            System.out.println("img : " + img_stro);
                            // Toast.makeText(AttendanceActivity.this, "Hit Punchout", Toast.LENGTH_SHORT).show();
                            hitPunchOut(img_stro);
                        } catch (IOException e) {
                            Toast.makeText(AttendanceActivity.this, "Image Exception", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Camera Closed");
        }
    }

    //here get file path from uri
    private String getUriRealPathAboveKitkat(AttendanceActivity activity, Uri contentURI) {
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

    public void hitPunchIn(String img_str) {
        try {
            if (!img_str.isEmpty()) {
                ModelStationType stype = (ModelStationType) binding.spinnerStation.getSelectedItem();
                int ttypeId = stype.getStationTypeId();

                ModelAttendance aInfo = new ModelAttendance();
                aInfo.setAttendanceDate(formattedDate);
                aInfo.setPunchInTime(time);
                aInfo.setEmpInfoId(empId);
                aInfo.setPInLat(lat);
                aInfo.setPInLog(lng);
                aInfo.setAttAddress(address);
                aInfo.setAttType(1);
                aInfo.setAttImg(img_str);
                if(!TextUtils.isEmpty(binding.etInPlace.getText().toString()))
                {
                    aInfo.setWorkPlace(binding.etInPlace.getText().toString());
                }else {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Work Place Mandatory");
                    return;
                }

                aInfo.setTourType(String.valueOf(ttypeId));
                aInfo.setWorkSession(worksession);
                String sds = lat;
                String sds2 = lng;
                if ((lat == null) || (lng == null)) {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Error in Getting Location.Please Refresh the Page first");
                } else {
                    Gson gson = new Gson();
                    String data = gson.toJson(aInfo);
                    System.out.println("indata :" + data);
                    //  Toast.makeText(this, "indata : "+data, Toast.LENGTH_SHORT).show();
                    presenter.doSavePuncINInfo(aInfo, binding.masterLayout);
                }
            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Your Photo Not Captured." + "\nPunch In Again");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void hitPunchOut(String img_str) {
        try {
            //ModelStationType stype = (ModelStationType) binding.spinnerStationOut.getSelectedItem();
           // int sttypeId = stype.getStationTypeId();

            if (!img_str.isEmpty()) {
                ModelAttendance aInfo = new ModelAttendance();
                aInfo.setAttendanceDate(formattedDate);
                aInfo.setPunchInTime(time);
                aInfo.setEmpInfoId(empId);
                aInfo.setPInLat(lat);
                aInfo.setPInLog(lng);
                aInfo.setAttAddress(address);
                aInfo.setAttType(2);
                aInfo.setAttImg(img_str);
                if(!TextUtils.isEmpty(binding.etOutPlace.getText().toString()))
                {
                    aInfo.setWorkPlace(binding.etOutPlace.getText().toString());
                }else {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Work Place Mandatory");
                    return;
                }
                aInfo.setTourType("In Tour Type");
               // aInfo.setTourType(String.valueOf(sttypeId));
                aInfo.setWorkSession(worksession);
                String remarks = binding.etOutPlace.getText().toString();
                aInfo.setPOutRemarks(remarks);
                if ((lat == null) || (lng == null)) {
                    SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Error in Getting Location.Please Refresh the Page first");
                } else {

                    Gson gson = new Gson();
                    String odata = gson.toJson(aInfo);
                    System.out.println("outdata :" + odata);
                    //  Toast.makeText(this, "outdata : "+odata, Toast.LENGTH_SHORT).show();
                    presenter.doSavePuncINInfo(aInfo, binding.masterLayout);
                }
            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Your Photo Not Captured." + "\nPunch In Again");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void initCmeraPermission() {
        Dexter.withContext(AttendanceActivity.this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ((pd != null) && pd.isShowing()) {
            pd.dismiss();
        }
    }
}