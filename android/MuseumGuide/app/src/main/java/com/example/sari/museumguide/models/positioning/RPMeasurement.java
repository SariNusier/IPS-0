package com.example.sari.museumguide.models.positioning;

/**
 * Created by sari on 18/02/16.
 */
public class RPMeasurement {
    long RPID; //this is a unique identifier for the reference point. (eg.: MAC ADDRESS)
    double value;

    public RPMeasurement(long RPID, double value) {
        this.RPID = RPID;
        this.value = value;
    }


}
