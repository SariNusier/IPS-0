package com.example.sari.museumguide.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sari.museumguide.R;
import com.example.sari.museumguide.database.Database;
import com.example.sari.museumguide.models.indoormapping.Building;
import com.example.sari.museumguide.models.indoormapping.Room;

import java.util.ArrayList;
import java.util.List;

public class BuildingActivity extends AppCompatActivity {
    private ListView museumListView;
    private ArrayAdapter museumListAdapter;
    private TextView estTimeView;
    private Building b;
    private String building_id;
    private boolean selected_rooms[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b =(Building) getIntent().getSerializableExtra("building");
        building_id = b.getId();
        museumListView = (ListView)findViewById(R.id.room_list_building);
        museumListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        estTimeView = (TextView)findViewById(R.id.estimated_time_textview);
        /*museumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startEditRoomActivity(position);
            }
        });
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList roomNames = new ArrayList<String>();
        b = Database.getBuilding(building_id);
        for(Room r: b.getRooms()){
            roomNames.add(r.getRoomName());
        }
        museumListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,
                roomNames);
        museumListView.setAdapter(museumListAdapter);
        selected_rooms = new boolean[roomNames.size()];
      /*  museumListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Items selected:", "Selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Items selected:", "Unselected                                                                                                                                                                        ");
            }
        });
       */
        museumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selected_rooms[position]) {
                    selected_rooms[position] = false;
                } else {
                    selected_rooms[position] = true;
                }
                String result = "";
                for (boolean bool : selected_rooms) {
                    result += "," + bool;
                }
                Log.d("Selected", result);
                estTimeView.setText("Estimated time: " +(int) getEstTime());
            }
        });

    }

    public double getEstTime(){
        double toReturn = 0;
        for(int i = 0;i<b.getRooms().length;++i){
            if(selected_rooms[i]){
                toReturn+=b.getRooms()[i].getEst_time();
            }
        }
        return toReturn;
    }

    public void startGuideActivity(View v){
        String selectedRooms = "";
        for(int i = 0;i<b.getRooms().length;++i){
            if(selected_rooms[i]){
                selectedRooms+=b.getRooms()[i].getId()+":"+"1"+",";
            }
        }
        if(!selectedRooms.isEmpty()){
            Intent intent = new Intent(this, GuideActivity.class);
            intent.putExtra("building",b);
            intent.putExtra("selected_rooms",selectedRooms.substring(0,selectedRooms.length()-1));
            //intent.putExtra("deadline",0);
            startActivity(intent);
        }
    }

}
