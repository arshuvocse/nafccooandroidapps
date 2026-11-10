package com.saidur.creatrix.unicorn.salessolution.UI.Settings;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_NR;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.saidur.creatrix.unicorn.salessolution.BuildConfig;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ISettings;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterSettings;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Authentication.LoginActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Authentication.PWChangeActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Sync.SyncFromServerActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentSettingsBinding;

import java.io.IOException;
import java.util.List;

public class SettingsFragment extends Fragment implements ISettings.View {
    FragmentSettingsBinding binding;
    PresenterSettings presenter;
    DBCrudHelper dbCrudHelper;
    ModelSession session;
    boolean isWifiConn = false;
    boolean isMobileConn = false;
    FusedLocationProviderClient mFusedLocationClient;
    String lat, lng;
    int empId;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
       // mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_more);
        dbCrudHelper=new DBCrudHelper(getContext());
        session = dbCrudHelper.getSession();
        presenter = new PresenterSettings(this, getContext());
        empId=session.getEmpInfoId();
        binding.Name.setText(session.getUserName());
        binding.userCode.setText(session.getEmpMasterCode());
        binding.tvVersionname.setText(BuildConfig.VERSION_NAME);

        checkLocationPermission();
        LocationManager locationManagerdd = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
        if (!locationManagerdd.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
       // binding.tvLastsynctime.setText(Constants.LastSyncTime);
        if (getArguments() != null) {
        }
        binding.settingdown.setOnClickListener(v -> {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            lat = null;
            lng = null;
            LocationManager locationManagerdz = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);

           /* if (!locationManagerdd.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showGPSDisabledAlertToUser();
            }
            mapFrag.getMapAsync(this);
            networkStatus();*/

            binding.settingLayout.setVisibility(View.VISIBLE);
            binding.settingdown.setVisibility(View.GONE);
            binding.settingup.setVisibility(View.VISIBLE);
        });
        binding.settingup.setOnClickListener(v -> {
            binding.settingLayout.setVisibility(View.GONE);
            binding.settingup.setVisibility(View.GONE);
            binding.settingdown.setVisibility(View.VISIBLE);
           /* if (mFusedLocationClient != null) {
                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            }*/
        });
        binding.btnLogout.setOnClickListener(view -> {
            new AlertDialog.Builder(requireActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit App")
                    .setMessage("Are you sure you want to Log out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean isDone=dbCrudHelper._deleteAllRecordsFromaTableBolean("tblSession");
                        if(isDone)
                        {
                            dbCrudHelper._deleteAllRecordsFromaTable("tblInitTable");
                            dbCrudHelper._deleteAllRecordsFromaTable("tbl_ProductInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblCustomerInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblLoginProfile");

                            dbCrudHelper._deleteAllRecordsFromaTable("tbl_Group");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblRegion");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblArea");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblTerritory");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblSubTerritory");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblMarket");

                            dbCrudHelper._deleteAllRecordsFromaTable("tblNSMInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblRSMInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblASMInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblMIOInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblMBEInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblQuotedPrice");

                            Intent i = new Intent(getContext(), LoginActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        binding.btnChangepw.setOnClickListener(v -> {
            Intent gotocpw = new Intent(getActivity(), PWChangeActivity.class);
            startActivity(gotocpw);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });
        binding.btnAllSync.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SyncFromServerActivity.class);
            startActivity(i);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.btnCustomerSync.setOnClickListener(v -> {
            try {

                presenter.doCustomerSync(empId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        binding.btnDoctorSync.setOnClickListener(v -> {
            try {

                presenter.doDoctorSync(empId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
     /*
        binding.btnProductSyncs.setOnClickListener(v -> {

            try {
                assert empId != null;
                presenter.doProductSync(Integer.parseInt(empId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });*/

        return binding.getRoot();
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(requireActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(requireActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }
    @SuppressLint({"SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void networkStatus() {
        ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);
        TelephonyManager telephonyManager = (TelephonyManager) requireActivity().getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
                binding.tvNetwork.setText("Wifi");
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
                switch (telephonyManager.getDataNetworkType()) {
                    case NETWORK_TYPE_EDGE:
                    case NETWORK_TYPE_GPRS:
                    case NETWORK_TYPE_CDMA:
                    case NETWORK_TYPE_IDEN:
                    case NETWORK_TYPE_1xRTT:
                        binding.tvNetwork.setText("2G");
                        break;
                    case NETWORK_TYPE_UMTS:
                    case NETWORK_TYPE_HSDPA:
                    case NETWORK_TYPE_HSPA:
                    case NETWORK_TYPE_HSPAP:
                    case NETWORK_TYPE_EVDO_0:
                    case NETWORK_TYPE_EVDO_A:
                    case NETWORK_TYPE_EVDO_B:
                        binding.tvNetwork.setText("3G");
                        break;
                    case NETWORK_TYPE_LTE:
                        binding.tvNetwork.setText("4G");
                        break;
                    case NETWORK_TYPE_NR:
                        binding.tvNetwork.setText("5G");
                        break;

                    default:
                        binding.tvNetwork.setText("Unknown");
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                        break;
                    case TelephonyManager.NETWORK_TYPE_GSM:
                        break;
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        break;
                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                        break;
                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                        break;
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        break;
                }
            }
        }
    }

    @Override
    public void onSuccess(String Message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Success")
                .setMessage(Message)
                .setPositiveButton("OK", (dialog, which) -> {

                }).setCancelable(false).show();
    }

    @Override
    public void onError(String Message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("ERROR")
                .setMessage(Message)
                .setPositiveButton("OK", (dialog, which) -> {

                }).setCancelable(false).show();
    }

    @Override
    public void onCustomerSync(String Message) {
        SnackBarManagement._success_CustomMessage(binding.masterLayout,Message);
    }

    @Override
    public void onProductSync(String Message) {

    }

    @Override
    public void onDoctorSync(String Message, boolean a) {

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        });


        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);

                //Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                try {
                    //Place current location marker
                    lat = String.valueOf(location.getLatitude());
                    lng = String.valueOf(location.getLongitude());
                    Geocoder geocoder = new Geocoder(requireActivity());
                    List<Address> addresses;

                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);

                    if(NetworkInformation.isConnected(requireActivity())){
                        markerOptions.title(addresses.get(0).getAddressLine(0));
                    }
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
}