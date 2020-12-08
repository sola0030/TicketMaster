package com.example.ticketmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteEvents<AppCompatActivity> extends Activity {

    //Intialize variables
    AlertDialog.Builder builder;

    DatabaseHelperForFavoriteEvent myDB;


    String savedEventName;

    ListView savedList;

    final ArrayList<String> savedEvent = new ArrayList<>();

    //This method is first called when application is run
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fevorite_events);

        builder = new AlertDialog.Builder(this);

        savedList = findViewById(R.id.fav_list);

        Intent intent = getIntent();
        savedEventName = intent.getStringExtra("eventName");

        myDB = new DatabaseHelperForFavoriteEvent(this);

        Cursor data = myDB.getListContents();

        if(data.getCount() == 0) {
            Toast.makeText(this, "No Event is added", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                savedEvent.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedEvent);
                savedList.setAdapter(listAdapter);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fevorite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav_home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            case R.id.fav_help:
                builder.setMessage(R.string.help_event)
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(R.string.help_title);
                alert.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}