package com.example.sari.museumguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.sari.museumguide.R;
import com.example.sari.museumguide.database.Database;
import com.example.sari.museumguide.models.indoormapping.Building;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Building[] buildings;
    private ListView museumListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode
                .ThreadPolicy.Builder().permitAll().build();
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
        ArrayAdapter museumListAdapter =
                new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,
                        buildingNames);
        museumListView.setAdapter(museumListAdapter);
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
