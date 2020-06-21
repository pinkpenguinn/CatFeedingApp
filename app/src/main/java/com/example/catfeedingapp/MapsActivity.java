package com.example.catfeedingapp;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements LocationListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    private DatabaseReference reference;
    private LocationManager manager;
    private final int MIN_TIME = 3000;
    private final int MIN_DISTANCE = 2;
    private Marker marker;
    private Geocoder geocoder;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ; // Uses firebase autentication to get current user
        reference = FirebaseDatabase.getInstance().getReference().child(currentFirebaseUser.getUid());

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);  // retrieves a location manager to get location updates

        geocoder = new Geocoder(MapsActivity.this);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
//

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // permission is requested to access phone's location before getting current location
            getCurrentLocation();


        }

        else {
            ActivityCompat.requestPermissions(MapsActivity.this, new String []{Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // accesses phone's sensors
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // get accelerometer
            shakeDetector = new ShakeDetector(); // new instance of object ShakeDetector is created
            shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
                @Override
                public void onShake(int count) {
                    mMap.clear(); // markers are cleared upon detection of shake event
                    Toast.makeText(MapsActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
                    getCurrentLocation();


                }
            });
        }
        else {
            Toast.makeText(MapsActivity.this, "Sensor Not Found", Toast.LENGTH_SHORT).show();
        }





    }

    private void readChanges() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {    // Every time location data changes, listener will be called with an immutable snapshot of the data.
                    try {
                        Locations location = dataSnapshot.getValue(Locations.class); // location data is retrieved from the dataSnapshot
                        if(location != null) {
                            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude())); //The main marker's position is reset
                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.paw)); // A custom icon is used instead of the default marker


                        }
                    }catch(Exception e){
                        Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getLocationUpdates() {
        if (manager != null) {  // if Location manager is successfully retrieved
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  // check to see if GPS is available
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                    // Location updates are requested from GPS Provider for every time and distance specified

                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                    // Location updates are requested fro Network Provider for every time and distance specified

                } else {
                    Toast.makeText(this, "No Provider Available", Toast.LENGTH_SHORT).show();
                    // If no providers exist, error message is displayed
                }
            }

            else {
                ActivityCompat.requestPermissions(MapsActivity.this, new String []{Manifest.permission.ACCESS_FINE_LOCATION},
                        44);
            }
        }
    }





    private void getCurrentLocation () {
        Task<Location> task = client.getLastLocation(); // use fusedlocationprovider client to get the user's current location
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null) {   // if location is detected, map activity is called
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            mMap = googleMap;
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            // coordinates of the user's location is stored in latLng


                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10)); // move camera to location
                            mMap.setMinZoomPreference(15);
                            mMap.getUiSettings().setZoomControlsEnabled(true);
                            mMap.getUiSettings().setAllGesturesEnabled(true);

                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are here!")); // add a marker on current location

                            mMap.setOnMapLongClickListener(MapsActivity.this); // Allow actions to be performed when map is long clicked
                            mMap.setOnMarkerDragListener(MapsActivity.this); // Let markers on map be draggable

                        }
                    });

                    getLocationUpdates();
                    readChanges ();


                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            saveLocation(location);
        }

        else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();

        }
    }

    private void saveLocation (Location location) {  // write location to database
        reference.setValue(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1 );
            // converts the coordinates of the location on the map pressed to a physical address
            // address is stored in a list
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String streetAddress = address.getAddressLine(0); // extracting the street name alone
                mMap.addMarker(new MarkerOptions().position(latLng).title(streetAddress).draggable(true).
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.food)));
                // custom marker is added to the map with a tag that contains the street address that matches the marker's position
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng latLng = marker.getPosition(); // after the marker is dragged and placed down, the new position of the marker is retrieved
        try {
            // Location coordinates converted to an address and stored in a list
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1 );
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String streetAddress = address.getAddressLine(0); // street address is extracted
                marker.setTitle(streetAddress); // address of marker is updated to reflect new address


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {   // resumes the sensor when its being used
        super.onResume();
        sensorManager.registerListener(shakeDetector,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector); // suspends the sensor when not in use
        super.onPause();
    }






}
