package utilities;

public class Point implements Tuple<Double,Double>{

    private Double x;
    private Double y;

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y){
        this.y = y;
    }

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
}
