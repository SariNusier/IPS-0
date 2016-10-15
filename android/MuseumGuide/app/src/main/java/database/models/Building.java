package database.models;

import java.io.Serializable;


public class Building implements Serializable{

    private String name;
    private String id;
    private Polygon geoLocation;
    private Room[] rooms;



    public Building(String id, Polygon geoLocation, String name, Room[] rooms) {
        this.geoLocation = geoLocation;
        this.id = id;
        this.name = name;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Polygon getGeoLocation() {
        return geoLocation;
    }

    public Room[] getRooms() {
        return rooms;
    }

}