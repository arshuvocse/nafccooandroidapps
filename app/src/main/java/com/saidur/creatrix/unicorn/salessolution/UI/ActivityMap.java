package com.saidur.creatrix.unicorn.salessolution.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityMapBinding;

public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback {
ActivityMapBinding binding;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Double lat, lan;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        lan = Double.parseDouble(getIntent().getStringExtra("lon"));
        address = getIntent().getStringExtra("address");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            LatLng sydney = new LatLng(lat, lan);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(sydney);
            markerOptions.title(address);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = googleMap.addMarker(markerOptions);
            //move map camera
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}