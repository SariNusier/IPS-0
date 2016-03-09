package com.example.sari.ips_admin.models.indoormapping;

import com.example.sari.ips_admin.tools.Rectangle;
import com.example.sari.ips_admin.tools.RectangleDB;

import java.io.Serializable;

/**
 * Created by sari on 15/02/16.
 */
public class Building implements Serializable{
    private RectangleDB rectangle;
    private String name;
    private double width;
    private double height;
    private String id;
    private Room[] rooms;



    public Building(String id, RectangleDB rectangle, String name, double width, double height, Room[] rooms) {
        this.rectangle = rectangle;
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.rooms = rooms;
    }

    public void setRectangle(RectangleDB rectangle) {
        this.rectangle = rectangle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public RectangleDB getRectangle() {

        return rectangle;
    }

    public String getName() {
        return name;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }
}
