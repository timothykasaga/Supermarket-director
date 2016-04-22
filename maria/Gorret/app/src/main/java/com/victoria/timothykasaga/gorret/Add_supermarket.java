package com.victoria.timothykasaga.gorret;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Leontymo on 4/20/2016.
 */
public class Add_supermarket extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    Toolbar toolbar;
    Button btnDetails;
    RadioGroup group;
    RadioButton radioButton;
    LocalDatabase localDatabase;
    GPStracker gpStracker;
    Location location;
    GoogleMap map;
    LatLng mapLatLng;
    double latitude;
    double longitude;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_supermarket);
        initialize();
        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        localDatabase = new LocalDatabase(this);
        Admin admin = localDatabase.getLoggedInAdmin();
        Toast.makeText(getApplicationContext(),admin.username,Toast.LENGTH_SHORT).show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Locate supermarket");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_supermarket.this,MainActivity.class);
                startActivity(intent);
            }
        });
        onClickButtonListerner();
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(4);
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        mapLatLng = new LatLng(-34.0D, 151.0D);
        map.addMarker(new MarkerOptions().position(mapLatLng).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(mapLatLng));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();
                map.addMarker(new MarkerOptions().position(latLng).title("Your supermarket"));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mapLatLng = latLng;
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "+
                        mapLatLng.latitude, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClickButtonListerner()
    {
        int i = group.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(i);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioButton.getText().toString().equals("Use GPS"))
                {
                    gpStracker = new GPStracker(Add_supermarket.this);
                    if(gpStracker.canGetLocation()){
                        latitude = gpStracker.getLatitude();
                        longitude = gpStracker.getLongitude();
                        mapLatLng = new LatLng(latitude,longitude);
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + mapLatLng.latitude
                                + "\nLong: " + mapLatLng.longitude, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Add_supermarket.this,Supermarket_details.class);
                        startActivity(intent);

                    }else{
                        gpStracker.showSettingsAlert();

                    }
                }
                if (radioButton.getText().toString().equals("Touch place"))
                {
                    latitude = 2.0D;
                    longitude = 2.0D;
                    if (mapLatLng != null)
                    {
                        latitude = mapLatLng.latitude;
                        longitude = mapLatLng.longitude;

                        String local = "";
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        List<Address> addresses;
                        try {
                            addresses = geocoder.getFromLocation(latitude,longitude,1);
                            if(!addresses.isEmpty()){
                                Address address = addresses.get(0);
                                local = address.getCountryName();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude
                                + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }
                }




            }
        });
    }


    public void initialize(){
        group = (RadioGroup) findViewById(R.id.rg1);
        btnDetails = (Button) findViewById(R.id.butDets);
    }
}
