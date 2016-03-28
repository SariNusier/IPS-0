package com.example.sari.ips_admin.activities;

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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sari.ips_admin.R;
import com.example.sari.ips_admin.database.Database;
import com.example.sari.ips_admin.models.positioning.RPMeasurement;

import java.util.List;
//TODO: Add start and stop measurement.
public class EditRoomActivity extends AppCompatActivity {
    private String currentRoom = "";
    WifiManager wifiManager;
    private TextView resultsTextview;
    private boolean toMeasure = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resultsTextview = (TextView) findViewById(R.id.edit_room_results_textview);
        Button measureButton = (Button) findViewById(R.id.room_measure_button);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Intent intent = getIntent();
        currentRoom = intent.getStringExtra("room_id");
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(broadcastReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        measureButton.setOnClickListener(startMListener);

    }

    private void startMeasurement(){
        wifiManager.startScan();
        toMeasure = true;
    }

    private void stopMeasurement(){
        toMeasure = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
    private View.OnClickListener startMListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Button)v).setText("STOP");
            startMeasurement();
            v.setOnClickListener(stopMListener);
        }
    };

    private View.OnClickListener stopMListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Button)v).setText("MEASURE");
            stopMeasurement();
            v.setOnClickListener(startMListener);
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (toMeasure) {
                List<ScanResult> found = wifiManager.getScanResults();
                resultsTextview.setText("");
                String[] RPIDs = new String[found.size()];
                Double[] values = new Double[found.size()];

                for (ScanResult sr : found) {
                    Log.d("FOUND: ", "" + sr.BSSID + ": " + sr.level);
                    resultsTextview.append(sr.BSSID + " = " + sr.level + "\n");
                    RPIDs[found.indexOf(sr)] = sr.BSSID;
                    values[found.indexOf(sr)] = (double) sr.level;
                }
                Database.postMeasurement(new RPMeasurement(RPIDs, values, currentRoom));
                Toast.makeText(getApplicationContext(),"SENT",Toast.LENGTH_SHORT).show();
                wifiManager.startScan();
            }
        }
    };
}
