package com.example.sari.ips_admin.models.positioning;

/**
 * Created by sari on 18/02/16.
 */
public class RPMeasurement {
    String RPID; //this is a unique identifier for the reference point. (eg.: MAC ADDRESS)
    String room_id;
    double value;

    public RPMeasurement(String RPID,String room_id , double value) {
        this.room_id = room_id;
        this.RPID = RPID;
        this.value = value;
    }

    public String getRPID() {
        return RPID;
    }

    public void setRPID(String RPID) {
        this.RPID = RPID;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
