package com.example.restaurantmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantmap.data.DatabaseHelper;
import com.example.restaurantmap.model.Locations;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AddRestaurant extends AppCompatActivity {
    EditText location;
    EditText name;
    LocationManager locationManager;
    String address = "";
    LocationListener locationListener;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        location = (EditText) findViewById(R.id.PlaceLocation);
        name = (EditText) findViewById(R.id.PlaceName);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
         db = new DatabaseHelper(this);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), getString(R.string.Maps_API));

        location.setFocusable(false);

    }



    public void showMap(View view) {

        Intent createIntent = new Intent(this, ShowRestaurant.class);
        createIntent.putExtra("location",location.getText().toString());
        createIntent.putExtra("title", name.getText().toString());
        startActivity(createIntent);
    }

    public void seeLocation(View view) {
        List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(AddRestaurant.this);

        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 100 && resultCode == RESULT_OK)
            {
                Place place = Autocomplete.getPlaceFromIntent(data);

                location.setText(place.getAddress());
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR)
            {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }


    }



    public void getCurrentLocation(View view) {

        location.setText(MainActivity.address);

    }

    public void saveLocation(View view) {

        String locationName = name.getText().toString();
        String locationAddress = location.getText().toString();

        if (locationAddress.isEmpty())
        {
            Toast.makeText(this, "Enter an Address", Toast.LENGTH_SHORT).show();
        }
        else if(locationName.isEmpty())
        {
            Toast.makeText(this, "Enter Location Name", Toast.LENGTH_SHORT).show();
        }
        else
        {
            LatLng coordinates =  convertAddress(locationAddress);

            long result = db.insertLocation(new Locations(locationName, coordinates.latitude, coordinates.longitude));

            if (result > 0)
            {
                Toast.makeText(this, "Restaurant saved successfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Restaurant not saved", Toast.LENGTH_SHORT).show();
            }
        }




    }

    private LatLng convertAddress(String location)
    {
        Geocoder coder = new Geocoder(AddRestaurant.this, Locale.getDefault());
        LatLng p1 = null;

        try {
            // May throw an IOException
            List<Address> address = coder.getFromLocationName(location, 5);

            Address loc = address.get(0);

            p1 = new LatLng(loc.getLatitude(), loc.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return  p1;
    }
}