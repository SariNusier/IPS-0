package exceptions;

public class InvalidPolygonException extends Exception {
    public InvalidPolygonException(){
        super("This is not a Polygon!");
    }
}
