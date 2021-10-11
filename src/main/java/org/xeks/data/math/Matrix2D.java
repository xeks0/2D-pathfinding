package org.xeks.data.math;

public class Matrix2D {
    public Matrix2D(double a, double b, double c, double d, double e, double f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }


    public double a;

    public double b;

    public double c;

    public double d;

    public double e;

    public double f;

    public Matrix2D(Double aDouble, Object o, Object o1, Object o2, Object o3, Object o4) {
        this.a = 1;
        this.b = 0;
        this.c = 0;
        this.d = 1;
        this.e = 0;
        this.f = 0;
    }


    public void tranform(Point2D point) {
        double x = ( ( ( this.a * point.x ) + ( this.c * point.y ) ) + this.e );
        double y = ( ( ( this.b * point.x ) + ( this.d * point.y ) ) + this.f );
        point.x = x;
        point.y = y;
    }

    public void identity() {
        this.a = 1;
        this.b = 0;
        this.c = 0;
        this.d = 1;
        this.e = 0;
        this.f = 0;
    }

    public void translate(double tx, double ty) {
        this.e += tx;
        this.f += ty;
    }

    public void scale(double sx, double sy) {
        this.a *= sx;
        this.b *= sy;
        this.c *= sx;
        this.d *= sy;
        this.e *= sx;
        this.f *= sy;
    }

    public void rotate(double rad) {
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double a_ = ((this.a * cos) + (this.b * -(sin)));
        double b_ = ((this.a * sin) + (this.b * cos));
        double c_ = ((this.c * cos) + (this.d * -(sin)));
        double d_ = ((this.c * sin) + (this.d * cos));
        double e_ = ((this.e * cos) + (this.f * -(sin)));
        double f_ = ((this.e * sin) + (this.f * cos));
        this.a = a_;
        this.b = b_;
        this.c = c_;
        this.d = d_;
        this.e = e_;
        this.f = f_;
    }

    public Matrix2D clone()
    {
        return new Matrix2D(this.a, this.b, this.c, this.d, this.e, this.f);
    }

    public double transformX(double x, double y)
    {
        return ( ( ( this.a * x ) + ( this.c * y ) ) + this.e );
    }


    public double transformY(double x, double y)
    {
        return ( ( ( this.b * x ) + ( this.d * y ) ) + this.f );
    }

    public void concat(Matrix2D matrix)
    {
        double a_ = ( ( this.a * matrix.a ) + ( this.b * matrix.c ) );
        double b_ = ( ( this.a * matrix.b ) + ( this.b * matrix.d ) );
        double c_ = ( ( this.c * matrix.a ) + ( this.d * matrix.c ) );
        double d_ = ( ( this.c * matrix.b ) + ( this.d * matrix.d ) );
        double e_ = ( ( ( this.e * matrix.a ) + ( this.f * matrix.c ) ) + matrix.e );
        double f_ = ( ( ( this.e * matrix.b ) + ( this.f * matrix.d ) ) + matrix.f );
        this.a = a_;
        this.b = b_;
        this.c = c_;
        this.d = d_;
        this.e = e_;
        this.f = f_;
    }
}
