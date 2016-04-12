package com.example.sari.ips_admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        It is implemented like this only because of my laziness.
         */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        museumListView = (ListView)findViewById(R.id.museumListView);
        museumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Item selected", "" + position);
                startEditBuildingActivity(position);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buildings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildings = Database.getBuildings();
        ArrayList buildingNames = new ArrayList<String>();
        for(Building b: buildings){
            buildingNames.add(b.getName());
        }
        museumListAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,
                buildingNames);
        museumListView.setAdapter(museumListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_addBuilding){
            startActivity(new Intent(this, AddBuildingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void startEditBuildingActivity(int position){
        Intent intent = new Intent(this, EditBuildingActivity.class);
        intent.putExtra("building",buildings[position]);
        startActivity(intent);
    }
}
