package org.xeks.data;

import lombok.Getter;
import lombok.Setter;
import org.xeks.Vector;

public class ConstraintSegment {
    public static int INC;

    static {
        ConstraintSegment.INC = 0;
    }

    public ConstraintSegment() {
        staticConstructor(this);
    }

    public static void staticConstructor(ConstraintSegment _this) {
        _this.id = ConstraintSegment.INC;
        ConstraintSegment.INC++;
        _this.setEdges(new Vector<>());
    }

    @Setter
    @Getter
    public int id;

    @Getter
    @Setter
    public Vector<Edge> edges;

    @Getter
    @Setter
    public ConstraintShape fromShape;

    public void dispose() {
        this.edges = null;
        this.fromShape = null;
    }

    public void addEdge(Edge edge) {
        if (((this.edges.indexOf(edge, null) == -1) && (this.edges.indexOf(edge.getOppositeEdge(), null) == -1))) {
            this.edges.push(edge);
        }
    }

    public void removeEdge(Edge edge) {
        int index = this.edges.indexOf(edge, null);
        if ((index == -1)) {
            index = this.edges.indexOf(edge.getOppositeEdge(), null);
        }
        if ((index != -1)) {
            this.edges.splice(index, 1);
        }
    }


}
