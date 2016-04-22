package com.example.sari.ips_admin.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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
import android.widget.TextView;
import com.example.sari.ips_admin.R;
import com.example.sari.ips_admin.database.Database;
import com.example.sari.ips_admin.models.indoormapping.Building;
import com.example.sari.ips_admin.models.indoormapping.Room;
import com.example.sari.ips_admin.models.positioning.RPMeasurement;
import java.util.ArrayList;
import java.util.List;


//CHECK IF GETTING BUILDINGS HERE IS ACTUALLY NEEDED!
public class EditBuildingActivity extends AppCompatActivity {
    private ListView museumListView;
    private Building b;
    private String building_id;
    WifiManager wifiManager;
    boolean toMeasure = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_building);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("ON CREATE", "EDIT BUILDING");
        b =(Building) getIntent().getSerializableExtra("building");
        building_id = b.getId();
        museumListView = (ListView)findViewById(R.id.room_list_edit_building);
        museumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startEditRoomActivity(position);
            }
        });
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(broadcastReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList roomNames = new ArrayList<String>();
        b = Database.getBuilding(building_id);
        for(Room r: b.getRooms()){
            roomNames.add(r.getRoomName());
        }
        ArrayAdapter museumListAdapter = new ArrayAdapter(this,
                android.R.layout.simple_expandable_list_item_1, roomNames);
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
            Database.deleteData("buildings",building_id);
            this.finish();
        }

        if(item.getItemId() == R.id.action_addRoom){
            Intent intent = new Intent(this,AddRoomActivity.class);
            intent.putExtra("building_id",building_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void startEditRoomActivity(int position){
        Intent intent = new Intent(this, EditRoomActivity.class);
        intent.putExtra("room_id",b.getRooms()[position].getId());
        startActivity(intent);
    }

    public void learnBuilding(View v){
        Database.getData("learn", building_id);
    }

    public void locateInBuidling(View v){
        toMeasure = true;
        wifiManager.startScan();

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (toMeasure) {
                List<ScanResult> found = wifiManager.getScanResults();
                String[] RPIDs = new String[found.size()];
                Double[] values = new Double[found.size()];

                for (ScanResult sr : found) {
                    Log.d("FOUND: ", "" + sr.BSSID + ": " + sr.level);
                    RPIDs[found.indexOf(sr)] = sr.BSSID;
                    values[found.indexOf(sr)] = (double) sr.level;
                }
                String foundRoomID = Database.classify(new RPMeasurement(RPIDs, values, null),
                                                        building_id);
                for(Room r:b.getRooms()){
                    if(r.getId().equals(foundRoomID)){
                        ((TextView)findViewById(R.id.building_located_room))
                                .setText(r.getRoomName());
                        break;
                    }
                }

                toMeasure = false;
            }
        }
    };

}
