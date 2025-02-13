package com.example.sari.ips_admin.models.positioning;

import android.util.Pair;
import java.util.ArrayList;


public class RPMeasurement {
    //String RPID; //this is a unique identifier for the reference point. (eg.: MAC ADDRESS)
    String room_id;
    ArrayList<Pair<String,Double>> readings = new ArrayList<>();

    public RPMeasurement(String[] RPIDs, Double[] values,String room_id) {
        for(int i = 0; i<RPIDs.length; ++i)
        {
            readings.add(new Pair<>(RPIDs[i],values[i]));
        }
        this.room_id = room_id;
    }

    public ArrayList<Pair<String, Double>> getReadings() {
        return readings;
    }


    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
