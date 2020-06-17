package com.example.catfeedingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;


import android.view.View;
import android.widget.CursorAdapter;
import android.widget.GridView;

import com.example.catfeedingapp.db.photoAdapter;
import com.example.catfeedingapp.db.photoDbhelper;

public class PhotoActivity extends AppCompatActivity {
    private photoDbhelper photoDbhelper;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo2);

        gridView = findViewById(R.id.photo_activity_grid_view);
        photoDbhelper = new photoDbhelper(this);
        gridView.setAdapter(new photoAdapter(this, this.photoDbhelper.readAllMemories(), false));

    }

        @Override
        protected void onResume(){
            super.onResume();

            ((CursorAdapter)gridView.getAdapter()).swapCursor(this.photoDbhelper.readAllMemories());
        }

    public void addNewMemory(View view) {
        Intent intent = new Intent(PhotoActivity.this, newPhotoActivity.class);
        startActivity(intent);
    }




}
