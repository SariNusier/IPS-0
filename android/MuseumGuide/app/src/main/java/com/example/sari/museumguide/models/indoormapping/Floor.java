package com.example.sari.museumguide.models.indoormapping;

import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sari on 18/02/16.
 */
public class Floor implements Serializable{
    Room[] rooms;

    public Floor(Room[] rooms)
    {
        this.rooms = rooms;
    }

    public Room[] getRooms() {
        return rooms;
    }

    /**
     * Return the neighbours of a room.
     * @param room The room of which neighbours we would like returned.
     * @return An array containing the neighbouring rooms.
     */
    public Room[] getNeighbours(Room room) {
        ArrayList<Room> neighbours = new ArrayList<>();
        for(Room r:rooms){
            if(r.isNeighbour(room))
                neighbours.add(r);
        }
        return neighbours.toArray(new Room[neighbours.size()]);


    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

}
