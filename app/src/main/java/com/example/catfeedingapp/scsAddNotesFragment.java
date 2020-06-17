package com.example.catfeedingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class scsAddNotesFragment extends Fragment {
    private ListView listView;
    static ArrayList<String> cases = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scs_add_notes,container,false);

        listView = view.findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.catfeedingapp", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet <String>) sharedPreferences.getStringSet("Cases", null);

        if(set == null) {
            cases.add("Template");
        }

        else {
            cases = new ArrayList<>(set);
        }




        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, cases);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editor = new Intent(getContext(), CaseActivity.class);
                editor.putExtra("noteID", position);
                startActivity(editor);
            }
        });

        fab = view.findViewById(R.id.addNotesfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CaseActivity.class);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
               new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).
                       setTitle("Are you sure?").setMessage("Do you want to delete this note?").
                       setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               cases.remove(position);
                               arrayAdapter.notifyDataSetChanged();

                               SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.catfeedingapp", Context.MODE_PRIVATE);
                               HashSet<String> set = new HashSet<>(scsAddNotesFragment.cases);
                               sharedPreferences.edit().putStringSet("Cases", set).apply();
                           }
                       })
                       .setNegativeButton("No",null).show();
                    return true;
            }
        });


        return view;
    }


}
