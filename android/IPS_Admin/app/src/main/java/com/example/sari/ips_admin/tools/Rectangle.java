package com.example.sari.ips_admin.tools;

/**
 * Created by sari on 15/02/16.
 */
public class Rectangle {
    private Point coordinates;
    private double width, height;

    public Rectangle(Point coordinates, double width, double height){
        this.coordinates = coordinates;
        this.width = width;
        this.height = height;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Point getCenter(){
        return new Point((width+coordinates.getX())/2,(height+coordinates.getY())/2);
    }

}
