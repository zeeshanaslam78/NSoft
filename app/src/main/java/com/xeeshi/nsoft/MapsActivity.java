package com.xeeshi.nsoft;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xeeshi.nsoft.Objects.Settings;
import com.xeeshi.nsoft.Objects.User;
import com.xeeshi.nsoft.Utils.Common;
import com.xeeshi.nsoft.Utils.Constants;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Common.setSelectedTheme(MapsActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        user = getIntent().getParcelableExtra(Constants.USER);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

        double lat = 0.0, longi = 0.0;
        String name = "";

        if (null!=user && null!= user.getMap()) {
            if (null!=user.getMap().getLat() && user.getMap().getLat().length()>0 && null!= user.getMap().getLong() && user.getMap().getLong().length()>0) {
                lat = Double.parseDouble(user.getMap().getLat());
                longi = Double.parseDouble(user.getMap().getLong());
            }

            name = String.format("%s %s", user.getFirstName(), user.getLastName());
        }

        // Add a marker on given latitude and longitude and move the camera
        LatLng location = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(location).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Settings settings = Settings.getSingleItem();
        if (null!=settings ) {
            if (null!=settings.getLocale() && settings.getLocale().length()>0)
                Common.SetLocale(MapsActivity.this, null, settings.getLocale() , false);
            else
                Common.SetLocale(MapsActivity.this, null, "English", false);
        } else
            Common.SetLocale(MapsActivity.this, null, "English", false);
    }

}
