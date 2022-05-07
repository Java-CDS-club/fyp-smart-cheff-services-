package com.example.onlinesmartcheffservices.MapPackage;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.onlinesmartcheffservices.Cheff.AddDishes;
import com.example.onlinesmartcheffservices.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PostMapActivityLocation extends FragmentActivity implements OnMapReadyCallback {

    public static final int GPS_REQUEST_CODE = 9003;


    String Lattitude , Longitude ;
    private GoogleMap mMap;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    private boolean mLocationPermissionGrandted;
    public static final int permissioncode = 1234;


    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;


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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(isServiceOK()){
            if(isGpsEnabled()){
                getLocatioPermissionGranted();
                if (mLocationPermissionGrandted) {
                    geTDeviseLoaction();
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                }}}


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {

                new AlertDialog.Builder(PostMapActivityLocation.this)
                        .setTitle("Location Add Manually")
                        .setMessage("Click to Add Location Manually")
                        .setNegativeButton("No" , null)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LatLng CurrentLocation = new LatLng(latLng.latitude, latLng.longitude);
                                mMap.addMarker(new MarkerOptions().position(CurrentLocation).title(" Your Current Location ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                                moveCamera(new LatLng(latLng.latitude, latLng.longitude), 15f);
                                Lattitude = String.valueOf(latLng.latitude);
                                Longitude = String.valueOf(latLng.longitude);
                                String t =        getAddress(getApplicationContext() , latLng.latitude , latLng.longitude);
                                Toast.makeText(getApplicationContext() , "btn" , Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext() , AddDishes.class) ;
                                i.putExtra("Longitude" , Longitude);
                                i.putExtra("Lattitude" , Lattitude);
                                i.putExtra("City" , t);
                                Toast.makeText(getApplicationContext() , "btn" + t  , Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK , i);
                                finish();

                            }
                        }).show();


            }
        });


    }


    public String getAddress(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);
            return cityName;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    private void geTDeviseLoaction() {
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGrandted) {
                Task location = mfusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            try {
                                Location location1 = (Location) task.getResult();

                                if(location1 !=null) {
                                    moveCamera(new LatLng(location1.getLatitude(), location1.getLongitude()), 15f);









                                }

                            }catch (Exception e ){
                                Toast.makeText(getApplicationContext() , e.toString() , Toast.LENGTH_LONG).show();
                                Log.d("error in update"  , e.toString());
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext() , e.toString() , Toast.LENGTH_LONG).show();
                        Log.d("error in update"  , e.toString());
                    }
                });
            }

        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private Boolean isGpsEnabled()
    {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(providerEnabled){
            return true ;
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("Please Enable Gps")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(i , GPS_REQUEST_CODE);
                        }
                    }).setCancelable(false)
                    .show();
        }
        return  false ;
    }



    void moveCamera(LatLng latLng, float Zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Zoom));
        LatLng CurrentLocation = new LatLng(latLng.latitude, latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(CurrentLocation).title(" Your Current Location ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));


        final String lat = String.valueOf(latLng.latitude);
        final String longi = String.valueOf(latLng.longitude) ;

        Handler handler = new Handler() ;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {





            }
        },500);

//        new AlertDialog.Builder(this)
//                .setTitle("Current Location Update")
//                .setMessage("Do You Want Current Location")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//
//                        setResult(RESULT_OK, new Intent().putExtra("Yes", true));
//
//
//                    }
//
//                }).create().show();
    }

    private Boolean isServiceOK(){
        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        int result = googleApi.isGooglePlayServicesAvailable(this);
        if(result == ConnectionResult.SUCCESS){
            return true ;
        }
        else if (googleApi.isUserResolvableError(result)){
            Dialog dialog = googleApi.getErrorDialog(this , result , 9002 , new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface task) {
                    Toast.makeText(PostMapActivityLocation.this, "Dialog is cancel", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        }else{
            Toast.makeText(this, "Google play service requreired", Toast.LENGTH_SHORT).show();

        }
        return false ;
    }

    public  void  getLocatioPermissionGranted(){
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION ,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGrandted =  true ;
            }
            else{
                ActivityCompat.requestPermissions(this , permission,permissioncode);
            }
        }
        else{
            ActivityCompat.requestPermissions(this , permission,permissioncode);
        }
    }
}
