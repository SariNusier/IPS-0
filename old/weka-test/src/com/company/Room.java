package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable{
    private String roomName;
    private String id;
    private String building_id;
    private String roomDescription;
    public ArrayList<Reading> averages;

    public Room(String id, String building_id, String roomName, ArrayList<Reading> readings){
        this.roomName = roomName;
        this.id = id;
        this.building_id = building_id;
        int sum;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public Room(String building_id, String roomName){
        this.building_id = building_id;

        this.roomName = roomName;
        this.roomDescription = "";
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getId() {
        return id;
    }


}
