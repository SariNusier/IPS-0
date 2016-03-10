package com.example.sari.ips_admin.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sari.ips_admin.R;
import com.example.sari.ips_admin.models.indoormapping.Building;
import com.example.sari.ips_admin.models.indoormapping.Room;

import java.util.ArrayList;

public class EditBuildingActivity extends AppCompatActivity {
    private ListView museumListView;
    private ArrayAdapter museumListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_building);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Building b =(Building) getIntent().getSerializableExtra("building");
        ArrayList roomNames = new ArrayList<String>();
        for(Room r: b.getRooms()){
            roomNames.add(r.getRoomName());
        }
        museumListView = (ListView)findViewById(R.id.room_list_edit_building);
        museumListAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,
                roomNames);
        museumListView.setAdapter(museumListAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
