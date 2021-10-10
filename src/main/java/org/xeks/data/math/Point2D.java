package org.xeks.data.math;

import lombok.Getter;
import lombok.Setter;

public class Point2D {

    @Getter
    @Setter
    public double x;

    @Getter
    @Setter
    public double y;

    public Point2D() {
        this.x = 0;
        this.y = 0;
    }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void transform(Matrix2D matrix) {
        matrix.tranform(this);
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D clone() {
        return new Point2D(this.x, this.y);
    }

    public void substract(Point2D p) {
        this.x -= p.x;
        this.y -= p.y;
    }

    public double getLength() {
        return Math.sqrt(((this.x * this.x) + (this.y * this.y)));
    }

    public void normalize() {
        double norm = this.getLength();
        this.x /= norm;
        this.y /= norm;
    }

    public void scale(double s) {
        this.x *= s;
        this.y *= s;
    }

    public double distanceTo(Point2D p) {
        double diffX = (this.x - p.x);
        double diffY = (this.y - p.y);
        return Math.sqrt(((diffX * diffX) + (diffY * diffY)));
    }

}
