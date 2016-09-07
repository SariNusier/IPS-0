package com.example.sari.museumguide.models.indoormapping;

import java.io.Serializable;
import java.util.ArrayList;


public class Floor implements Serializable{
    Room[] rooms;

    public Floor(Room[] rooms)
    {
        this.rooms = rooms;
    }

    public Room[] getRooms() {
        return rooms;
    }


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
