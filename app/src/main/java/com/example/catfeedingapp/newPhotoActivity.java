package com.example.catfeedingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.catfeedingapp.db.photoDbhelper;
import com.example.catfeedingapp.photomodel.photo;



public class newPhotoActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 200;
    private ImageView newImageView;
    private EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);

        newImageView = findViewById(R.id.new_image);
        editText = findViewById(R.id.add_photo_title);


    }
    // Method requests permission Camera access from the user and Opens up the Camera
    public void openCamera(View view) {
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhoto, CAMERA_REQUEST_CODE);
        }
    }
    // The image in the ImageView newImageView is converted to a bitmap using the getBitmap() method
    // A new instance of the object photo, named newPhoto, is created by passing in the title of the photo
    // from the EditText field that accepts user input and by passing in the bitmap image
    // The newPhoto object is then stored into the database by the addMemory() method in the photoDbhelper class
    public void save(View view) {

        Bitmap image = ((BitmapDrawable)newImageView.getDrawable()).getBitmap();
        photo newPhoto = new photo(editText.getText().toString(), image);


        new photoDbhelper(this).addMemory(newPhoto);

        finish();
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data"); // gets image taken from camera and stores it to imageBitmap
            newImageView.setImageBitmap(imageBitmap); // the image stored in imageBitmap is passed to the ImageView newImageView
        }
    }



}
