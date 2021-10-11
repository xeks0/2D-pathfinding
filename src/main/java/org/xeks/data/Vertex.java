package org.xeks.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.xeks.Vector;
import org.xeks.data.math.Point2D;


@EqualsAndHashCode
public class Vertex {


    public static int INC = 0;

    public Vertex() {
        staticConstructor(this);
    }

    public static void staticConstructor(Vertex _this) {
        _this.id = Vertex.INC;
        Vertex.INC++;
        _this.colorDebug = -1;
        _this.setFromConstraintSegments(new Vector<>());
        _this.setPos(new Point2D());
    }

    @Getter
    @Setter
    public int id;

    @Getter
    @Setter
    public Point2D pos;

    @Getter
    @Setter
    public boolean isReal;

    @Getter
    @Setter
    public Edge edge;

    @Getter
    @Setter
    public Vector<ConstraintSegment> fromConstraintSegments;

    @Getter
    @Setter
    public int colorDebug;

    public void setDatas(Edge edge, boolean isReal) {
        this.isReal = isReal;
        this.edge = edge;
    }

    public void addFromConstraintSegment(ConstraintSegment segment) {
        if ((this.fromConstraintSegments.indexOf(segment, null) == -1)) {
            this.fromConstraintSegments.push(segment);
        }
    }

    public void removeFromConstraintSegment(ConstraintSegment segment) {
        int index = this.fromConstraintSegments.indexOf(segment, null);
        if ((index != -1)) {
            this.fromConstraintSegments.splice(index, 1);
        }
    }

    public void dispose() {
        this.pos = null;
        this.edge = null;
        this.fromConstraintSegments = null;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", isReal=" + isReal +
                '}';
    }
}
