package com.example.catfeedingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button logOutButton;
    Button goToMapView;
    Button scsMainPage;
    Button toPhotoAlbum;
    FirebaseAuth firebaseAuthorization;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private boolean mLocationPositionAccessable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logOutButton = findViewById(R.id.logOutButton);
        goToMapView = findViewById(R.id.mapView);
        scsMainPage = findViewById(R.id.scsButton);
        toPhotoAlbum = findViewById(R.id.photoAlbum);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent toSignInPage = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(toSignInPage);
            }
        });

        goToMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapView = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(toMapView);
            }
        });

        scsMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toScsPage = new Intent(HomeActivity.this, scsActivity.class);
                startActivity(toScsPage);
            }
        });

        toPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPhotoAlbum = new Intent(HomeActivity.this, PhotoActivity.class);
                startActivity(toPhotoAlbum);
            }
        });


    }
}
