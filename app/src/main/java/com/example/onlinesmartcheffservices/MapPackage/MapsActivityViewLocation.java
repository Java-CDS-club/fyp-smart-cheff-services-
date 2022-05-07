package com.example.onlinesmartcheffservices.MapPackage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.onlinesmartcheffservices.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivityViewLocation extends FragmentActivity implements OnMapReadyCallback {

    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private FusedLocationProviderClient mfusedLocationProviderClient;
    private boolean mLocationPermissionGrandted;
    public static final int permissioncode = 1234;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        getLocatioPermissionGranted();
        geTDeviseLoaction();
        LatLng sydney = new LatLng(Double.valueOf(getIntent().getStringExtra("lat")), Double.valueOf(getIntent().getStringExtra("longi")));
        mMap.addMarker(new MarkerOptions().position(sydney).title(getIntent().getStringExtra("name") + " Locations here "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        getLocation();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

    }


    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        new FusedLocationProviderClient(this).getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null && mMap != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(), location.getLongitude()), 15f
                            ));
                            if (ActivityCompat.checkSelfPermission(MapsActivityViewLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivityViewLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            mMap.setMyLocationEnabled(true);

                            new Handler().postDelayed(MapsActivityViewLocation.this::getLocation, 5000);
                        }
                    }
                });
    }



    public void getLocatioPermissionGranted() {

        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), MapsActivityViewLocation.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGrandted = true;
            } else {
                ActivityCompat.requestPermissions(this, permission, permissioncode);
            }

        } else {
            ActivityCompat.requestPermissions(this, permission, permissioncode);
        }

    }
    private void geTDeviseLoaction() {


        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGrandted ) {

                final Task location = mfusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                          if(task.getResult() !=null) {


                              Location location1 = (Location) task.getResult();
                              if(location1 !=null) {
                                  try {

                                      LatLng CurrentLocation = new LatLng(location1.getLatitude(), location1.getLongitude());
                                      mMap.addMarker(new MarkerOptions().position(CurrentLocation).title(" Your Current Location ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                                      moveCamera(new LatLng(location1.getLatitude(), location1.getLongitude()), 15f);
                                      LatLng CurrentLocation1 = new LatLng(Double.valueOf(getIntent().getStringExtra("lat")), Double.valueOf(getIntent().getStringExtra("longi")));


                                      mMap.addPolyline(
                                              new PolylineOptions()
                                                      .add(CurrentLocation)
                                                      .add(CurrentLocation1)
                                                      .width(5f)
                                                      .color(Color.RED)
                                      );


                                  } catch (Exception e) {
                                      Toast.makeText(getApplicationContext(), "enabling gps", Toast.LENGTH_LONG).show();
                                  }
                              }
                          }else{
                              Toast.makeText(getApplicationContext(), "enabling wifi", Toast.LENGTH_LONG).show();

                          }
                        }
                    }

                });
            }
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    void moveCamera(LatLng latLng, float Zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Zoom));
    }
}
