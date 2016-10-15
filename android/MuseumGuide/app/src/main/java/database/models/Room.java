package database.models;


public class Room {

    private String id;
    private String name;
    private int floor;
    private Polygon geoLocation;
    private Exhibit[] exhibits;


    public Room(String id, String name, int floor, Polygon geoLocation, Exhibit[] exhibits) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.geoLocation = geoLocation;
        this.exhibits = exhibits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFloor() {
        return floor;
    }

    public Polygon getGeoLocation() {
        return geoLocation;
    }

    public Exhibit[] getExhibits() {
        return exhibits;
    }
}
