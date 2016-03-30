package com.example.sari.museumguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sari.museumguide.R;
import com.example.sari.museumguide.database.Database;
import com.example.sari.museumguide.models.indoormapping.Building;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Building[] buildings;
    private ListView museumListView;
    private ArrayAdapter museumListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        museumListView = (ListView)findViewById(R.id.museumListView);
        museumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startBuildingActivity(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startMapsActivity(int position){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void startBuildingActivity(int position){
        Intent intent = new Intent(this, BuildingActivity.class);
        intent.putExtra("building",buildings[position]);
        startActivity(intent);
    }

}
