package org.xeks.ai;

import lombok.Getter;
import lombok.Setter;
import org.xeks.Vector;
import org.xeks.data.Obstacle;

public class EntityAI {
    static {
       EntityAI.NUM_SEGMENTS = 6;
    }
    public static int NUM_SEGMENTS;

    public double dirNormY;

    public double dirNormX;

    @Getter
    @Setter
    public double y;

    @Getter
    @Setter
    public double x;

    @Getter
    public double _radius;

    public double _radiusSquared;



    public Obstacle _approximateObject;

    public EntityAI() {
        this._radius = 10;
        this.x = this.y = 0;
        this.dirNormX = 1;
        this.dirNormY = 0;
    }

    public void buildApproximation() {
        this._approximateObject = new Obstacle();
        this._approximateObject.getMatrix().translate(this.x, this.y);
        Vector<Double> coordinates = new Vector<>();
        this._approximateObject.setCoordinates(coordinates);
        if ((this._radius == 0)) {
            return;
        }

        {
            int _g = 0;
            while ((_g < 6)) {
                int i = _g++;
                coordinates.push((this._radius * java.lang.Math.cos((((2 * java.lang.Math.PI) * i) / 6))));
                coordinates.push((this._radius * java.lang.Math.sin((((2 * java.lang.Math.PI) * i) / 6))));
                coordinates.push((this._radius * java.lang.Math.cos((((2 * java.lang.Math.PI) * ((i + 1))) / 6))));
                coordinates.push((this._radius * java.lang.Math.sin((((2 * java.lang.Math.PI) * ((i + 1))) / 6))));
            }

        }

    }

    public Obstacle getApproximateObject() {
        this._approximateObject.getMatrix().identity();
        this._approximateObject.getMatrix().translate(this.x, this.y);
        return this._approximateObject;
    }

    public double setRadius(double value) {
        this._radius = value;
        this._radiusSquared = (this._radius * this._radius);
        return value;
    }
}
