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
        //Retrieve the notes data from the shared preferences every time the activity is started
        //where key = "Cases" and stores the data in the HashSet, set

        if(set == null) {
            cases.add("Template");    //If there is no data (no existing notes) a template note will be added to the ArrayList
        }

        else {
            cases = new ArrayList<>(set); //If there is existing data, the data is stored in the ArrayList cases
        }




        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, cases);
        listView.setAdapter(arrayAdapter); //Display the data from the ArrayList cases in a List View

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Detects if any of the list items have been clicked and opens up the Case activity if so
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editor = new Intent(getContext(), CaseActivity.class);
                editor.putExtra("noteID", position); // adds set of extended data to the intent
                startActivity(editor);
            }
        });

        fab = view.findViewById(R.id.addNotesfab);
        fab.setOnClickListener(new View.OnClickListener() {  // Detects if the FAB button has been clicked and opens up the Case Activity of so
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CaseActivity.class);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Detects if any of the items on the list view have been long clicked
                //Opens up a dialog box if long click was detected asking users if they want to delete the note
               new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).
                       setTitle("Are you sure?").setMessage("Do you want to delete this note?").
                       setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               cases.remove(position);  // if user clicks yes, the note is removed from the ArrayList cases
                               arrayAdapter.notifyDataSetChanged(); //arrayAdapter is notified and list view is updated accordingly to reflect the deletion
                               //Shared Preferences is retrieved and data from the ArrayList cases is stored in the HashSet set
                               //Data is then passed to the preference editor and saved with the apply() method
                               SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.catfeedingapp", Context.MODE_PRIVATE);
                               HashSet<String> set = new HashSet<>(scsAddNotesFragment.cases);
                               sharedPreferences.edit().putStringSet("Cases", set).apply();
                           }
                       })
                       .setNegativeButton("No",null).show(); // If no is selected, Dialog box closes and list view is returned
                    return true;
            }
        });


        return view;
    }


}
