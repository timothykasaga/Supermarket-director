package com.victoria.timothykasaga.gorret;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Leontymo on 4/20/2016.
 */
public class Add_supermarket extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    Toolbar toolbar;
    Button btnDetails;
    LocalDatabase localDatabase;
    GPStracker gpStracker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_supermarket);
        initialize();
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
    }

    @Override
    public void onLocationChanged(Location location) {

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

    }

    public void initialize(){
        btnDetails = (Button) findViewById(R.id.butDets);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpStracker = new GPStracker(Add_supermarket.this);
                if(gpStracker.canGetLocation()){
                    double latitude = gpStracker.getLatitude();
                    double longitude = gpStracker.getLongitude();
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude
                            + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    gpStracker.showSettingsAlert();

                }
                Intent intent = new Intent(Add_supermarket.this,Supermarket_details.class);
                startActivity(intent);
            }
        });
    }
}
