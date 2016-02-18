package com.example.sari.museumguide.models.indoormapping;

import com.example.sari.museumguide.tools.Rectangle;

import java.io.Serializable;

public class Room implements Serializable{
    private String roomName;
    private Rectangle roomRectangle;
    private String roomDescription;


    public Room(){

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

        //we check only to the right and bottom, so we need this.
        if(room.isNeighbour(this))
            return true;

        if(this.getRoomRectangle().getCoordinates().getX() + this.getRoomRectangle().getWidth()
                == room.getRoomRectangle().getCoordinates().getX()
                || this.getRoomRectangle().getCoordinates().getY() + this.getRoomRectangle().getHeight()
                == room.getRoomRectangle().getCoordinates().getY())
            return true;

        return false;

    }
}
