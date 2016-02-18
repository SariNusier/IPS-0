package com.example.sari.museumguide.models.indoormapping;

import com.example.sari.museumguide.tools.Rectangle;

import java.io.Serializable;

public class Room implements Serializable{
    private String roomName;
    private Rectangle roomRectangle;
    private String roomDescription;


    public Room(String roomName, String roomDescription, Rectangle roomRectangle){
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomRectangle = roomRectangle;
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
