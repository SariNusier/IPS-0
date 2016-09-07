package tools;

import java.io.Serializable;


public class RectangleDB implements Serializable{
    private Point lt,rt,lb,rb;

    public RectangleDB(Point lt, Point rt, Point lb, Point rb) {
        this.lt = lt;
        this.rt = rt;
        this.lb = lb;
        this.rb = rb;
    }

    public Point getLt() {
        return lt;
    }

    public void setLt(Point lt) {
        this.lt = lt;
    }

    public Point getRt() {
        return rt;
    }

    public void setRt(Point rt) {
        this.rt = rt;
    }

    public Point getLb() {
        return lb;
    }

    public void setLb(Point lb) {
        this.lb = lb;
    }

    public Point getRb() {
        return rb;
    }

    public void setRb(Point rb) {
        this.rb = rb;
    }

    public boolean isNeighbour (RectangleDB r){
        if(this.getRt() == r.getLt() && this.getRb() == r.getLb() )
            return true;
        if(this.getLt() == r.getRt() && this.getLb() == r.getRb())
            return true;
        if(this.getLt() == r.getLb() && this.getRt() == r.getRb())
            return true;
        if(this.getLb() == r.getLt() && this.getRb() == this.getRt())
            return true;

        return true;


    }
}
