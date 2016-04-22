package com.example.sari.museumguide.models.indoormapping;

import com.example.sari.museumguide.tools.Point;
import com.example.sari.museumguide.tools.Rectangle;
import com.example.sari.museumguide.tools.RectangleDB;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable{
    private String roomName;
    private String id;
    private String building_id;
    private Rectangle roomRectangle;
    private RectangleDB rectangleDB;
    private String roomDescription;
    private double width,length;
    private double est_time;
    private ArrayList<String> exhibits;


    public Room(String id, String building_id, String roomName,
                RectangleDB rectangleDB, double width, double length, double est_time){
        this.roomName = roomName;
        this.id = id;
        this.width = width;
        this.length = length;
        this.rectangleDB = rectangleDB;
        this.building_id = building_id;
        this.roomRectangle = new Rectangle(new Point(0,0),0,0); //for testing!!!
        this.est_time = est_time;
        exhibits = new ArrayList<>();
    }

    public String getBuilding_id() {
        return building_id;
    }

    public Room(String building_id, String roomName,
                RectangleDB rectangleDB, double width, double length){
        this.building_id = building_id;

        this.roomName = roomName;
        this.width = width;
        this.length = length;
        this.roomDescription = "";
        this.rectangleDB = rectangleDB;
        exhibits = new ArrayList<>();
        this.roomRectangle = new Rectangle(new Point(0,0),0,0); //for testing!!!
    }

    public Room(Rectangle r){
        this.roomRectangle = r;
        roomName = "";
        roomDescription = "";
        exhibits = new ArrayList<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Rectangle getRoomRectangle() {
        return roomRectangle;
    }

    public void setRoomRectangle(Rectangle roomRectangle) {
        this.roomRectangle = roomRectangle;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public boolean isNeighbour(Room room){
        if(this == room)
            return false;

        if(this.getRoomRectangle().getCoordinates().getX()
                + this.getRoomRectangle().getWidth()
                == room.getRoomRectangle().getCoordinates().getX()
                || this.getRoomRectangle().getCoordinates().getY()
                + this.getRoomRectangle().getLength()
                == room.getRoomRectangle().getCoordinates().getY()
                || room.getRoomRectangle().getCoordinates().getX()
                + room.getRoomRectangle().getWidth()
                == this.getRoomRectangle().getCoordinates().getX()
                || room.getRoomRectangle().getCoordinates().getY()
                + room.getRoomRectangle().getLength()
                == this.getRoomRectangle().getCoordinates().getY())
            return true;

        return false;

    }

    public String getId() {
        return id;
    }

    public RectangleDB getRectangleDB() {
        return rectangleDB;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public void setEst_time(double est_time) {
        this.est_time = est_time;
    }

    public void addExhibit(String exhibit){
        exhibits.add(exhibit);
    }

    public ArrayList<String> getExhibits(){
        return exhibits;
    }

    public String hasExhibit(String exhibit_id){
        for(String e: exhibits){
            if(e.split(",")[1].equals(exhibit_id)){
                return e;
            }
        }
        return null;
    }

    public double getEst_time() {

        return est_time;
    }

}
