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
        noteID = intent.getIntExtra("noteID", -1);

        if(noteID != -1) {
            editText.setText(scsAddNotesFragment.cases.get(noteID));

        }

        else {
            scsAddNotesFragment.cases.add("");
            noteID = scsAddNotesFragment.cases.size() -1;
            scsAddNotesFragment.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scsAddNotesFragment.cases.set(noteID, String.valueOf(s));
                scsAddNotesFragment.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.catfeedingapp", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(scsAddNotesFragment.cases);
                sharedPreferences.edit().putStringSet("Cases", set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
