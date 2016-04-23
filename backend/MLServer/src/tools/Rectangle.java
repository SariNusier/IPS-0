package tools;

import java.io.Serializable;

public class Rectangle implements Serializable{
    private Point coordinates;
    private double width, length;

    public Rectangle(Point coordinates, double width, double length){
        this.coordinates = coordinates;
        this.width = width;
        this.length = length;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Point getCenter(){
        return new Point((width+coordinates.getX())/2,(length +coordinates.getY())/2);
    }

}
