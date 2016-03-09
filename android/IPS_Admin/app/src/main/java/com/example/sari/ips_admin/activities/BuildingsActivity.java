package com.example.sari.ips_admin.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sari.ips_admin.R;
import com.example.sari.ips_admin.database.Database;
import com.example.sari.ips_admin.models.indoormapping.Building;

import java.util.ArrayList;
import java.util.List;

public class BuildingsActivity extends AppCompatActivity {
    Building[] buildings;
    private  ListView museumListView;
    private ArrayAdapter museumListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
                /*
        WARNING!!!
        Should not stay like this forever, fetching data must be done outside the UI thread.
        It is implemented like this only for debugging purposes.
         */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        buildings = Database.getBuildings();
        ArrayList buildingNames = new ArrayList<String>();
        for(Building b: buildings){
           buildingNames.add(b.getName());
        }
        museumListView = (ListView)findViewById(R.id.museumListView);
        museumListAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,
                buildingNames);
        museumListView.setAdapter(museumListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buildings, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
