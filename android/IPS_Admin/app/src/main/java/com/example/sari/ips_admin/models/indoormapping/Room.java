package com.example.sari.ips_admin.models.indoormapping;

import com.example.sari.ips_admin.tools.Point;
import com.example.sari.ips_admin.tools.Rectangle;
import com.example.sari.ips_admin.tools.RectangleDB;

import java.io.Serializable;

public class Room implements Serializable{
    private String roomName;
    private String id;
    private Rectangle roomRectangle;
    private RectangleDB rectangleDB;
    private String roomDescription;
    private double width,height;


    public Room(String id, String roomName, String roomDescription, RectangleDB rectangleDB,double width, double height){
        this.roomName = roomName;
        this.id = id;
        this.width = width;
        this.height = height;
        this.roomDescription = roomDescription;
        this.rectangleDB = rectangleDB;
        this.roomRectangle = new Rectangle(new Point(0,0),0,0); //for testing!!!
    }

    public Room(Rectangle r){
        this.roomRectangle = r;
        roomName = "";
        roomDescription = "";
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
        //for convenience, a room is not its own neighbour
        if(this == room)
            return false;

        if(this.getRoomRectangle().getCoordinates().getX() + this.getRoomRectangle().getWidth()
                == room.getRoomRectangle().getCoordinates().getX()
                || this.getRoomRectangle().getCoordinates().getY() + this.getRoomRectangle().getHeight()
                == room.getRoomRectangle().getCoordinates().getY()
                || room.getRoomRectangle().getCoordinates().getX() + room.getRoomRectangle().getWidth()
                == this.getRoomRectangle().getCoordinates().getX()
                || room.getRoomRectangle().getCoordinates().getY() + room.getRoomRectangle().getHeight()
                == this.getRoomRectangle().getCoordinates().getY())
            return true;

        return false;

    }
}
