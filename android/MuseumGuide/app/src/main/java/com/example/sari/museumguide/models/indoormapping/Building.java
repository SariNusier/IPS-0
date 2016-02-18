package com.example.sari.museumguide.models.indoormapping;

import java.io.Serializable;

/**
 * Created by sari on 15/02/16.
 */
public class Building implements Serializable{
    Floor[] floors;

    public Building(Floor[] floors)
    {
        floors = floors;
    }

    public Floor[] getFloors() {
        return floors;
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }
}
