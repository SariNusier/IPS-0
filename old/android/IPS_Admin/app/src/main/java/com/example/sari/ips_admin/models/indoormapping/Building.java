package com.example.sari.ips_admin.models.indoormapping;

import com.example.sari.ips_admin.tools.RectangleDB;
import java.io.Serializable;


public class Building implements Serializable{
    private RectangleDB rectangle;
    private String name;
    private double width;
    private double length;
    private String id;
    private Room[] rooms;



    public Building(String id, RectangleDB rectangle, String name,
                    double width, double length, Room[] rooms) {
        this.rectangle = rectangle;
        this.id = id;
        this.name = name;
        this.width = width;
        this.length = length;
        this.rooms = rooms;
    }

    public Building(RectangleDB rectangle, String name, double width, double length) {
        this.rectangle = rectangle;
        this.id = "";
        this.name = name;
        this.width = width;
        this.length = length;
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

    public void setLength(double length) {
        this.length = length;
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

    public double getLength() {
        return length;
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
