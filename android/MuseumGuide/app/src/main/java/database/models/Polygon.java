package database.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import exceptions.IndexOutOfBoundsException;
import exceptions.InvalidPolygonException;
import utilities.Point;


public class Polygon implements Serializable{

    private Point[] points;

    public static boolean verifyPolygon(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].equals(points[i]))
                return false;
        }
        return points[0].getX().equals(points[points.length - 1].getX()) &&
                points[0].getY().equals(points[points.length - 1].getY()) &&
                points.length > 3;
    }

    public Polygon(Point[] points) throws InvalidPolygonException {
        if(!verifyPolygon(points))
            throw new InvalidPolygonException();
        this.points = points;
    }

    public Point[] getPoints() {
        return this.points;
    }

    public void setPoints(Point[] points) throws InvalidPolygonException {
        if(!verifyPolygon(points))
            throw new InvalidPolygonException();
        this.points = points;
    }

    public Point getPoint(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.points.length)
            throw new IndexOutOfBoundsException();
        return this.points[index];
    }

    public double getArea() {
        double area = 0;
        int length = points.length;
        for (int i = 0; i < length - 1; i++) {
            area += points[i].getX() * points[i + 1].getY() -
                    points[i + 1].getX() * points[i].getY();
        }
        area += points[length - 1].getX() * points[0].getY() -
                points[0].getX() * points[length - 1].getY();
        return area / 2;
    }
}