package com.example.onlinesmartcheffservices.MapPackage;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.onlinesmartcheffservices.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class OnLocationViewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static final String TAG = "ErRRRRRRRRRRRRRRRRRRRRRRr";
    private FusedLocationProviderClient mfusedLocationProviderClient;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int permissioncode = 1234;
    public static final LatLngBounds latB = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 36));

    private AutoCompleteTextView mSearchView;
    DatabaseReference databaseReference;
    private boolean mLocationPermissionGrandted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_location_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getLocatioPermissionGranted();


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

        try {
            getLocatioPermissionGranted();
            // Add a marker in Sydney and move the camera
            geTDeviseLoaction();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
        }catch (Exception ex){
            Toast.makeText(this, ""+ex.toString(),  Toast.LENGTH_SHORT).show();
        }
        // Add a marker in Sydney and move the camera
        try {
            LatLng sydney = new LatLng(Double.valueOf(getIntent().getStringExtra("lat")),
                    Double.valueOf(getIntent().getStringExtra("lon")));
            mMap.addMarker(new MarkerOptions().position(sydney).title(getIntent().getStringExtra("name") + "" +
                    "  Location  "));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



        }catch (Exception ex){
            Toast.makeText(this, "" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        }



    public void getLocatioPermissionGranted() {

        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGrandted = true;
            } else {
                ActivityCompat.requestPermissions(this, permission, permissioncode);
            }

        } else {
            ActivityCompat.requestPermissions(this, permission, permissioncode);
        }

    }



    void moveCamera(LatLng latLng, float Zoom , String title) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Zoom));

        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in " + title ));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    void  keyboardHide(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
                            Location location1 = (Location) task.getResult();

                            try {
                                LatLng CurrentLocation = new LatLng(location1.getLatitude(), location1.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(CurrentLocation).title(" Your Current Location ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));


                                LatLng CurrentLocation1 = new LatLng(Double.valueOf(getIntent().getStringExtra("lat")),
                                        Double.valueOf(getIntent().getStringExtra("lon")));
                                mMap.addMarker(new MarkerOptions().position(CurrentLocation1).title(getIntent().getStringExtra("name") + "" +
                                        "  Location  "));


                                mMap.addPolyline(
                                        new PolylineOptions()
                                                .add(CurrentLocation)
                                                .add(CurrentLocation1)
                                                .width(5f)
                                                .color(Color.RED)
                                );






                            }catch (Exception e){
                                Toast.makeText(getApplicationContext() , "enabling gps" , Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }

        } catch (SecurityException e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            Toast.makeText(getApplicationContext() , "permission ok" , Toast.LENGTH_LONG ).show();
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this ,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},1);
                }

            }

        }

    }
    void moveCamera(LatLng latLng, float Zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Zoom));
    }

}