package com.example.restaurantmap;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.restaurantmap.data.DatabaseHelper;
import com.example.restaurantmap.model.Locations;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ShowAllRestaurants extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseHelper db;
    ArrayList<Locations> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_restaurants);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locations = new ArrayList<Locations>();

        db = new DatabaseHelper(this);

        locations = db.fetchAllLocations();

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

        for (int i =0 ; i< locations.size(); i++)
        {

            double lat = locations.get(i).getLatitude();
            double lon = locations.get(i).getLongitude();
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(locations.get(i).getTitle()));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),10));
        }

    }
}