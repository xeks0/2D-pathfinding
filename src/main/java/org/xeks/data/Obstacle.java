package org.xeks.data;

import lombok.Getter;
import lombok.Setter;
import org.xeks.Vector;
import org.xeks.data.math.Matrix2D;

public class Obstacle {

    public static int INC = 0;

    public Obstacle() {
        staticConstructor(this);
    }

    public static void staticConstructor(Obstacle _this) {
        _this.id = Obstacle.INC;
        Obstacle.INC++;
        _this.pivotX = 0;
        _this.pivotY = 0;
        _this.matrix = new Matrix2D(null, null, null, null, null, null);
        _this.scaleX = 1;
        _this.scaleY = 1;
        _this.rotation = 0;
        _this.x = 0;
        _this.y = 0;
        _this.coordinates = new Vector<>();
        _this.hasChanged = false;
    }


    @Getter
    @Setter
    public int id;

    @Getter
    @Setter
    public Matrix2D matrix;

    @Getter
    @Setter
    public Vector<java.lang.Object> coordinates;

    @Getter
    @Setter
    public ConstraintShape constraintShape;

    @Getter
    public double pivotX;

    @Getter
    public double pivotY;

    @Getter
    public double scaleX;

    @Getter
    public double scaleY;

    @Getter
    public double rotation;

    @Getter
    public double x;

    @Getter
    @Setter
    public double y;

    @Getter
    @Setter
    public boolean hasChanged;


    public void updateValuesFromMatrix() {
    }

    public void updateMatrixFromValues() {
        this.matrix.identity();
        this.matrix.translate(-(this.pivotX), -(this.pivotY));
        this.matrix.scale(this.scaleX, this.scaleY);
        this.matrix.rotate(this.rotation);
        this.matrix.translate(this.x, this.y);
    }

    public double setPivotX(double value) {
        this.pivotX = value;
        this.hasChanged = true;
        return value;
    }

    public double setPivotY(double value) {
        this.pivotY = value;
        this.hasChanged = true;
        return value;
    }

    public double setScaleX(double value) {
        if ((this.scaleX != value)) {

            this.scaleX = value;
            this.hasChanged = true;
        }
        return value;
    }

    public double setScaleY(double value) {
        if ((this.scaleY != value)) {
            this.scaleY = value;
            this.hasChanged = true;
        }
        return value;
    }

    public double setRotation(double value) {
        if ((this.rotation != value)) {
            this.rotation = value;
            this.hasChanged = true;
        }
        return value;
    }

    public double setX(double value) {
        if ((this.x != value)) {
            this.x = value;
            this.hasChanged = true;
        }
        return value;
    }

    public double setY(double value) {
        if ((this.y != value)) {
            this.y = value;
            this.hasChanged = true;
        }
        return value;
    }

    public Matrix2D setMatrix(Matrix2D matrix) {
        this.matrix = matrix;
        this.hasChanged = true;
        return matrix;
    }

    public Vector<Object> setCoordinates(Vector<Object> vector) {
        this.coordinates = vector;
        this.hasChanged = true;
        return vector;
    }

    public ConstraintShape setConstraintShape(ConstraintShape constraintShape) {
        this.constraintShape = constraintShape;
        this.hasChanged = true;
        return constraintShape;
    }

    public Vector<Edge> getEdges() {
        Vector<Edge> res = new Vector<Edge>();
        for (int i = 0; i < this.constraintShape.getSegments().length; i++) {
            if (this.constraintShape.getSegments().get(i).getEdges() != null)
                for (int j = 0; j < this.constraintShape.getSegments().get(i).getEdges().length; j++)
                    res.push(this.constraintShape.getSegments().get(i).getEdges().get(j));
        }
        return res;
    }
}
