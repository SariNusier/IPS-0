package com.example.sari.museumguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.sari.museumguide.R;
import com.example.sari.museumguide.database.Database;
import com.example.sari.museumguide.models.indoormapping.Building;
import com.example.sari.museumguide.models.indoormapping.Room;
import com.example.sari.museumguide.tools.CustomAdapter;
import java.util.ArrayList;

public class BuildingActivity extends AppCompatActivity {
    private ListView museumListView;
    private TextView estTimeView;
    private EditText deadlineText;
    private Building b;
    private String building_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b =(Building) getIntent().getSerializableExtra("building");
        building_id = b.getId();
        museumListView = (ListView)findViewById(R.id.room_list_building);
        deadlineText = (EditText) findViewById(R.id.deadline_edittext);
        museumListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        estTimeView = (TextView)findViewById(R.id.estimated_time_textview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ArrayList roomNames = new ArrayList<String>();
        b = Database.getBuilding(building_id);
        for(Room r: b.getRooms()){
            roomNames.add(r.getRoomName());
        }
        ArrayAdapter museumListAdapter = new CustomAdapter(this, roomNames);
        museumListView.setAdapter(museumListAdapter);
        updateEstTime();
    }

    public void updateEstTime(){
        estTimeView.setText("Estimated time: " +(int) getEstTime());
    }

    public double getEstTime(){
        double toReturn = 0;
        for(int i = 0;i<b.getRooms().length;++i){
            if(((CustomAdapter) museumListView.getAdapter()).isChecked(i)){
                toReturn+=b.getRooms()[i].getEst_time();
            }
        }
        return toReturn;
    }

    public void startGuideActivity(View v){
        String selectedRooms = "";
        for(int i = 0;i<b.getRooms().length;++i){
            if(((CustomAdapter) museumListView.getAdapter()).isChecked(i)){
                selectedRooms+=b.getRooms()[i].getId()+":"
                        +((CustomAdapter) museumListView.getAdapter()).getProgress(i)
                        +",";
            }
        }
        Log.d("SELECTED ROOMS: ",selectedRooms);
        if(!selectedRooms.isEmpty() && !deadlineText.getText().toString().isEmpty()){
            Intent intent = new Intent(this, GuideActivity.class);
            intent.putExtra("building",b);
            intent.putExtra("selected_rooms",selectedRooms
                                             .substring(0,selectedRooms.length()-1));
            intent.putExtra("deadline",Integer.parseInt(deadlineText.getText().toString()));
            startActivity(intent);
        }
    }

}
