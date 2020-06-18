package com.example.catfeedingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;

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
        gridView.setAdapter(photoAdapter);
//        gridView.setAdapter(new photoAdapter(this, this.photoDbhelper.readAllMemories(), false));


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] item = {"Delete Photo"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoActivity.this);

                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0) {

                            Cursor cursor = photoDbhelper.getData("SELECT _id FROM memories");
                            ArrayList<Long> IDList = new ArrayList<>();
                            while(cursor.moveToNext()) {
                                IDList.add(cursor.getLong(0));
                            }

                            deleteWarningDialog(IDList.get(position));






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

    private void deleteWarningDialog (final long photoID) {
        AlertDialog.Builder warningDialog = new AlertDialog.Builder(PhotoActivity.this);
        warningDialog.setMessage("Are you sure you want to delete this image?");
        warningDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    photoDbhelper.deleteData(photoID);

                } catch (Exception e) {
                    Log.e("Error Deleting", e.getMessage());
                }



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
