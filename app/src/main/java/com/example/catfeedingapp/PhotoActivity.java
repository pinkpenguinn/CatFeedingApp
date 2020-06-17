package com.example.catfeedingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.catfeedingapp.db.photoAdapter;
import com.example.catfeedingapp.db.photoDbhelper;

import java.util.ArrayList;

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

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] item = {"Delete Photo"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoActivity.this);

                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0) {

                            deleteWarningDialog();
                        }

                    }
                });
                dialog.show();
                return true;
            }
        });

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

    private void deleteWarningDialog () {
        AlertDialog.Builder warningDialog = new AlertDialog.Builder(PhotoActivity.this);
        warningDialog.setMessage("Are you sure you want to delete this image?");
        warningDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        warningDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        warningDialog.show();
    }






}
