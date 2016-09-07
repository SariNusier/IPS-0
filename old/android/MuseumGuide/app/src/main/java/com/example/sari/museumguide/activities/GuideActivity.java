package com.example.sari.museumguide.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
import android.widget.TextView;
import com.example.sari.museumguide.R;
import com.example.sari.museumguide.database.Database;
import com.example.sari.museumguide.models.indoormapping.Building;
import com.example.sari.museumguide.models.indoormapping.Room;
import com.example.sari.museumguide.models.positioning.RPMeasurement;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GuideActivity extends AppCompatActivity {
    private static final int BLUETOOTH_THRESHOLD = -40;
    private Building b;
    WifiManager wifiManager;
    BluetoothAdapter bluetoothAdapter;
    Room currentRoom;
    String selectedRooms;
    boolean btAvailable = false;
    long timeOfChange = System.currentTimeMillis();
    String currentExhibit;
    TextView currentRoomView;
    TextView routeTextView;
    TextView exhibitTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            btAvailable = false;
        } else if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(wifiBroadcastReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        b =(Building) getIntent().getSerializableExtra("building");
        selectedRooms = getIntent().getStringExtra("selected_rooms");
        currentRoomView = (TextView)findViewById(R.id.current_room_textview);
        routeTextView = (TextView)findViewById(R.id.visitor_route_textview);
        exhibitTextView = (TextView) findViewById(R.id.visitor_exhibit_textview);
        String route = Database.getRoute(b.getId(),selectedRooms,
                getIntent().getIntExtra("deadline",10000));
        routeTextView.setText(parseRoute(route));
        currentRoom = null; //Entrance room?
        wifiManager.startScan();
        currentExhibit = "";
        exhibitTextView.setText("No exhibit nearby");
        registerReceiver(btBroadcastReceiver,
                new IntentFilter(BluetoothDevice.ACTION_FOUND));
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                bluetoothAdapter.startDiscovery();
            }
        };
        new Timer().scheduleAtFixedRate(tt,0,5000);

    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(wifiBroadcastReceiver);
        unregisterReceiver(btBroadcastReceiver);
        super.onStop();
    }

    private BroadcastReceiver wifiBroadcastReceiver = new BroadcastReceiver() {
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
            String foundRoomID = Database.classify(new RPMeasurement(RPIDs, values, null),
                    b.getId());
            String[] results = foundRoomID.split(",");
            for(String result:results){
                for(Room room:b.getRooms()){
                    if(room.getId().equals(result.split(":")[1])){
                        currentRoomView.setText(currentRoomView.getText()
                                +result.split(":")[0]+":"+room.getRoomName()+"\n");
                        shouldChangeRoom(room);
                        break;
                    }
                }
            }
            shouldChangeRoom(currentRoom);
            wifiManager.startScan();
        }
    };

    private final BroadcastReceiver btBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("BLUE:", device.getAddress()+" "+device.getName()+" "+rssi);
                if(currentExhibit.isEmpty())
                {
                    if(device.getName() != null && currentRoom != null){
                        String exhibit = currentRoom.hasExhibit(device.getAddress());
                        if(exhibit !=null && rssi > BLUETOOTH_THRESHOLD){
                            bluetoothAdapter.cancelDiscovery();
                            currentExhibit = device.getAddress();
                            exhibitTextView.setText("You are viewing: "+exhibit.split(",")[0]);
                        }
                    }
                } else {
                    if(device.getAddress().equals(currentExhibit)
                            && rssi >= BLUETOOTH_THRESHOLD){
                        bluetoothAdapter.cancelDiscovery();
                    } else {
                        currentExhibit = "";
                        exhibitTextView.setText("No exhibit nearby");
                    }
                }
            }
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
            Database.postLocationData(currentRoom.getId(),
                    TimeUnit.MILLISECONDS.toSeconds(duration));
        }
        return true;
    }

    private String parseRoute(String route){
        String toReturn = "";
        String[] steps = route.split(";");
        for(String step:steps){
            if(step.split(":")[0].equals("view")){
                toReturn+="View room: "
                        +b.findRoomById(step.split(":")[1]).getRoomName()+"\n";
            } else {
                toReturn+="Go to room: "
                        +b.findRoomById(step.split(":")[1].split(",")[1])
                        .getRoomName()+"\n";
            }
        }
        return toReturn;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            btAvailable = true;
        } else
            btAvailable = false;
    }
}
