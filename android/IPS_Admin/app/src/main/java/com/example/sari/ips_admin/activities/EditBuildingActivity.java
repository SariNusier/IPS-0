package com.example.sari.ips_admin.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.sari.ips_admin.models.indoormapping.Room;

import java.util.ArrayList;

public class EditBuildingActivity extends AppCompatActivity {
    private ListView museumListView;
    private ArrayAdapter museumListAdapter;
    private Building b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_building);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b =(Building) getIntent().getSerializableExtra("building");
        museumListView = (ListView)findViewById(R.id.room_list_edit_building);
        museumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startEditRoomActivity(position);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList roomNames = new ArrayList<String>();
        b = Database.getBuilding(b.getId());
        for(Room r: b.getRooms()){
            roomNames.add(r.getRoomName());
        }
        museumListAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,
                roomNames);
        museumListView.setAdapter(museumListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_building, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_deleteBuilding){
            Database.deleteData("buildings",b.getId());
            this.finish();
        }

        if(item.getItemId() == R.id.action_addRoom){
            Intent intent = new Intent(this,AddRoomActivity.class);
            intent.putExtra("building_id",b.getId());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void startEditRoomActivity(int position){
        Intent intent = new Intent(this, EditRoomActivity.class);
        intent.putExtra("room_id",b.getRooms()[position].getId());
        startActivity(intent);
    }

}
