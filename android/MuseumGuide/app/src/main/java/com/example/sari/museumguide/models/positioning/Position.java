package com.example.sari.museumguide.models.positioning;

public class Position {
    RPMeasurement[] measurements;

    public Position(RPMeasurement[] measurements){
        this.measurements = measurements;
    }

    public RPMeasurement[] getMeasurements() {
        return measurements;
    }

    public void setMeasurements(RPMeasurement[] measurements) {
        this.measurements = measurements;
    }
}
