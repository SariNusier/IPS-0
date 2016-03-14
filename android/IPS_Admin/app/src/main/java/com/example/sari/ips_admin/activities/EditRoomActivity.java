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

import com.example.sari.ips_admin.R;

import java.util.List;

public class EditRoomActivity extends AppCompatActivity {
    private String currentRoom = "";
    WifiManager wifiManager;
    private TextView resultsTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resultsTextview = (TextView) findViewById(R.id.edit_room_results_textview);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        currentRoom = intent.getStringExtra("room_id");
        Button measureButton = (Button) findViewById(R.id.room_measure_button);
        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measureRoom();
            }
        });
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> found = wifiManager.getScanResults();
        wifiManager.startScan();
        registerReceiver(broadcastReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

    }

    private void measureRoom(){

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> found = wifiManager.getScanResults();
            resultsTextview.setText("");
            for(ScanResult sr : found){
                Log.d("FOUND: ", ""+sr.BSSID+": "+sr.level);
                resultsTextview.append(sr.BSSID+" = "+sr.level+"\n");
            }
            wifiManager.startScan();
        }
    };
}
