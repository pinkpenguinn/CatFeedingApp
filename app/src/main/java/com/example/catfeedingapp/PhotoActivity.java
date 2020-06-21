package com.example.catfeedingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;


import android.util.Log;
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
    private photoAdapter photoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo2);

        gridView = findViewById(R.id.photo_activity_grid_view);
        photoDbhelper = new photoDbhelper(this);
        photoAdapter = new photoAdapter(this, this.photoDbhelper.readAllMemories(), false);
        gridView.setAdapter(photoAdapter); // To update the gridView when photos are deleted or added



        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // Detects a long click on any of the elements in the GridView
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] item = {"Delete Photo"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoActivity.this);
                // creates a dialog to display 'Delete Photo' when an item is long clicked
                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0) {
                            // This method queries the database and returns a Cursor that contains all the ID's of the images
                            Cursor cursor = photoDbhelper.getData("SELECT _id FROM memories");
                            ArrayList<Long> IDList = new ArrayList<>(); // Create an ArrayList to contain the IDs
                            while(cursor.moveToNext()) {   // Retrieves data from cursor and stores it in the ArrayList
                                                           // in the position corresponding to their position in the Table
                                IDList.add(cursor.getLong(0));
                            }

                            deleteWarningDialog(IDList.get(position)); // calls the deleteWarningDialog method passing in the
                                                                       // unique ID of the photo tapped as a parameter


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

     // When FAB button is clicked, the newPhotoActivity is opened where users can choose to take photos
    public void addNewMemory(View view) {
        Intent intent = new Intent(PhotoActivity.this, newPhotoActivity.class);
        startActivity(intent);
    }

    private void deleteWarningDialog (final long photoID) {
        //adds another dialog to reconfirm with the users if they want to delete the photo
        AlertDialog.Builder warningDialog = new AlertDialog.Builder(PhotoActivity.this);
        warningDialog.setMessage("Are you sure you want to delete this image?"); //
        warningDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    photoDbhelper.deleteData(photoID); // calls the deleteData method from photoDbhelper, passing the
                                                       // unique ID of the photo clicked as a parameter
                    photoAdapter.notifyDataSetChanged(); // updates the gridView to reflect the deletion


                } catch (Exception e) {
                    Log.e("Error Deleting", e.getMessage());
                }



            }
        });

        warningDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Dialog is closed if user clicks cancel
            }
        });

        warningDialog.show();

    }






}
