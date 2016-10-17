package database.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import exceptions.IndexOutOfBoundsException;
import exceptions.InvalidPolygonException;
import utilities.Point;

//TODO: Implement
public class Polygon implements Serializable{

    private ArrayList<Point> points;

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
        this.points = new ArrayList<>(Arrays.asList(points));
    }

    public Point[] getPoints() {
        return this.points.toArray(new Point[points.size()]);
    }

    public void setPoints(Point[] points) throws InvalidPolygonException {
        if(!verifyPolygon(points))
            throw new InvalidPolygonException();
        this.points = new ArrayList<>(Arrays.asList(points));
    }

    public Point getPoint(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.points.size())
            throw new IndexOutOfBoundsException();
        return this.points.get(index);
    }

    public double getArea() {
        double area = 0;
        int length = points.size();
        for (int i = 0; i < length - 1; i++) {
            area += points.get(i).getX() * points.get(i + 1).getY() -
                    points.get(i + 1).getX() * points.get(i).getY();
        }
        area += points.get(points.size() - 1).getX() * points.get(0).getY() -
                points.get(0).getX() * points.get(points.size() - 1).getY();
        return area / 2;
    }
}