package com.example.sari.museumguide.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sari.museumguide.R;
import com.example.sari.museumguide.database.Database;
import com.example.sari.museumguide.models.indoormapping.Building;
import com.example.sari.museumguide.models.indoormapping.Room;
import com.example.sari.museumguide.models.positioning.RPMeasurement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GuideActivity extends AppCompatActivity {
    private Building b;
    WifiManager wifiManager;
    Room currentRoom;
    String selectedRooms;
    long timeOfChange = System.currentTimeMillis();
    TextView currentRoomView;
    TextView routeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        b =(Building) getIntent().getSerializableExtra("building");
        selectedRooms = getIntent().getStringExtra("selected_rooms");
        currentRoomView = (TextView)findViewById(R.id.current_room_textview);
        routeTextView = (TextView)findViewById(R.id.visitor_route_textview);
        String route = Database.getRoute(b.getId(),selectedRooms,getIntent().getIntExtra("deadline",10000));
        routeTextView.setText(parseRoute(route));
        currentRoom = null; //Entrance room?
        wifiManager.startScan();
    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            List<ScanResult> found = wifiManager.getScanResults();
            String[] RPIDs = new String[found.size()];
            Double[] values = new Double[found.size()];

            currentRoomView.setText("");
            for (ScanResult sr : found) {
                Log.d("FOUND: ", "" + sr.BSSID + ": " + sr.level);
                RPIDs[found.indexOf(sr)] = sr.BSSID;
                values[found.indexOf(sr)] = (double) sr.level;
            }
            String foundRoomID = Database.classify(new RPMeasurement(RPIDs, values, null), b.getId());
            String[] results = foundRoomID.split(",");
            for(String result:results){
                for(Room room:b.getRooms()){
                    if(room.getId().equals(result.split(":")[1])){
                        currentRoomView.setText(currentRoomView.getText()+result.split(":")[0]+":"+room.getRoomName()+"\n");
                        shouldChangeRoom(room);
                        break;
                    }
                }
            }
            //currentRoomView.setText(currentRoomView.getText()+currentRoom.getRoomName());
            shouldChangeRoom(currentRoom);
            wifiManager.startScan();
        }
    };

    public boolean shouldChangeRoom(Room r){
        boolean shouldChange = false;
        if(currentRoom == null){
            shouldChange = true;
            currentRoom = r;
        }
        if(!currentRoom.equals(r)){
            currentRoom = r;
            shouldChange = true;
        }
        if(shouldChange){
            long curTime = System.currentTimeMillis();
            long duration = curTime-timeOfChange;
            timeOfChange = curTime;
            Database.postLocationData(currentRoom.getId(), TimeUnit.MILLISECONDS.toSeconds(duration));
        }
        return true;
    }

    private String parseRoute(String route){
        String toReturn = "";
        String[] steps = route.split(";");
        for(String step:steps){
            if(step.split(":")[0].equals("view")){
                toReturn+="View room: "+b.findRoomById(step.split(":")[1]).getRoomName()+"\n";
            } else {
                toReturn+="Go to room: "+b.findRoomById(step.split(":")[1].split(",")[1]).getRoomName()+"\n";
            }
        }
        return toReturn;
    }
}
