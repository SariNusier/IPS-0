package com.example.sari.museumguide.activities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.example.sari.museumguide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Marker locationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng scienceMuseum = new LatLng(51.4972,-0.1767);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scienceMuseum, 18));
        locationMarker = mMap.addMarker(new MarkerOptions().position(scienceMuseum)
                .title("You are here!"));

        PolylineOptions options = new PolylineOptions().color(Color.BLUE).width(5).visible(true);
        options.add(scienceMuseum);
        options.add(new LatLng(51.4972531,-0.175478));
        mMap.addPolyline(options);

    }

    public void updateLocationMarker(double latitude, double longitude){
        locationMarker.setPosition(new LatLng(latitude,longitude));
    }

    public void updateLocationMarker(LatLng latLng){
        locationMarker.setPosition(latLng);
    }
}
