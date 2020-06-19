package com.example.catfeedingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class CaseActivity extends AppCompatActivity {
    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);

        EditText editText = findViewById(R.id.multiLineNote);

        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1); //get noteID if an existing note was clicked
                                                                    // nodeID corresponds to position on the String ArrayList cases defined in scsAddNotesFragment
                                                                    // if there is no existing note, noteID = -1

        if(noteID != -1) {
            editText.setText(scsAddNotesFragment.cases.get(noteID)); // If note exists, the string data is retrieved from cases and displayed on EditText view

        }

        else {
            scsAddNotesFragment.cases.add("");                       //If note does not exist, size of ArrayList in increased
            noteID = scsAddNotesFragment.cases.size() -1;            //The new note is assigned a noteID of the last position on the ArrayList
            scsAddNotesFragment.arrayAdapter.notifyDataSetChanged(); //New list is added to the ListView to contain the new note
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scsAddNotesFragment.cases.set(noteID, String.valueOf(s)); //Adds the data typed by user to the String ArrayList case in it's corresponding position
                scsAddNotesFragment.arrayAdapter.notifyDataSetChanged();  //Updates the list view accordingly with the data user has inputted

                SharedPreferences sharedPreferences = getApplicationContext().
                        getSharedPreferences("com.example.catfeedingapp", Context.MODE_PRIVATE); // get shared preferences
                HashSet<String> set = new HashSet<>(scsAddNotesFragment.cases); // assigns the data in the String ArrayList cases to the String HashSet set
                sharedPreferences.edit().putStringSet("Cases", set).apply(); // Passes the HashSet set to preferences editor and commits the changes with apply()

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
