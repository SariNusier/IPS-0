package com.example.sari.museumguide.tools;

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
}
