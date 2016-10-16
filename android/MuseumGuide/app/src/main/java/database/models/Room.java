package database.models;

import java.io.Serializable;

public class Room implements Serializable{

    private String id;
    private String name;
    private int floor;
    private Polygon geoLocation;
    private Exhibit[] exhibits;

    /**
     * Default Constructor for Room Class
     * @param id (Pref. GUID from DB)
     * @param name (The name of the Room E.g. 1A, B24R, PlaneRoom1)
     * @param floor (Number of the Floor E.g. 0, -2, 3)
     * @param geoLocation (Coordinates of every meaningful point of the Room)
     * @param exhibits (Array of Exhibits in that Room. In DB is the FK from Exhibit to Room)
     */
    public Room(String id, String name, int floor, Polygon geoLocation, Exhibit[] exhibits) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.geoLocation = geoLocation;
        this.exhibits = exhibits;
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
     * Default Getter for Floor
     * @return Floor of type int
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Default Getter for GeoLocation
     * @return GeoLocation of type Polygon
     */
    public Polygon getGeoLocation() {
        return geoLocation;
    }

    /**
     * Default Getter for Exhibits
     * @return Array of Exhibits
     */
    public Exhibit[] getExhibits() {
        return exhibits;
    }

}
