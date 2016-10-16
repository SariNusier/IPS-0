package database.models;

import java.io.Serializable;


public class Building implements Serializable{

    private String id;
    private String name;
    private Polygon geoLocation;
    private Room[] rooms;

    /***
     * Default Constructor for Building Class
     * @param id (Pref. GUID from DB)
     * @param name (The name of the Building E.g. N1, N2, W3, Front Building)
     * @param geoLocation (Coordinates of every meaningful point of the Building)
     * @param rooms (Array of Rooms in that Building. In DB is the FK from Room to Building)
     */
    public Building(String id, String name, Polygon geoLocation, Room[] rooms) {
        this.id = id;
        this.name = name;
        this.geoLocation = geoLocation;
        this.rooms = rooms;
    }

    /**
     * Default Getter for ID
     * @return Id of type String (I personally would like GUID)
     */
    public String getId() {
        return id;
    }

    /**
     * Default Getter for Name
     * @return Name of type String
     */
    public String getName() {
        return name;
    }

    /**
     * Default Getter for GeoLocation
     * @return GeoLocation of type Polygon
     */
    public Polygon getGeoLocation() {
        return geoLocation;
    }

    /**
     * Default Getter for Rooms
     * @return Array of Rooms
     */
    public Room[] getRooms() {
        return rooms;
    }

}