package org.xeks.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import lombok.Setter;
import lombok.ToString;
import org.xeks.Vector;


@EqualsAndHashCode
public class Edge {


    public static int INC = 0;

    public Edge() {
        staticConstructor(this);
    }

    public static void staticConstructor(Edge _this) {
        _this.id = Edge.INC;
        Edge.INC++;
        _this.setFromConstraintSegments(new Vector<>());
    }

    @Getter
    @Setter
    public int id;

    @Getter
    @Setter
    public boolean isReal;

    @Getter
    @Setter
    public boolean isConstrained;

    @Getter
    @Setter
    public Vertex originVertex;

    @Getter
    @Setter
    public Edge oppositeEdge;

    @Getter
    @Setter
    public Edge nextLeftEdge;

    @Getter
    @Setter
    public Face leftFace;

    @Getter
    @Setter
    public int colorDebug;

    @Getter
    @Setter
    public Vector<ConstraintSegment> fromConstraintSegments;

    public void setDatas(Vertex originVertex, Edge oppositeEdge, Edge nextLeftEdge, Face leftFace, boolean isReal, boolean isConstrained) {
        this.isConstrained = isConstrained;
        this.isReal = isReal;
        this.originVertex = originVertex;
        this.oppositeEdge = oppositeEdge;
        this.nextLeftEdge = nextLeftEdge;
        this.leftFace = leftFace;
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
        this.originVertex = null;
        this.oppositeEdge = null;
        this.nextLeftEdge = null;
        this.leftFace = null;
        this.fromConstraintSegments = null;
    }

    public Vertex getDestinationVertex() {
        return this.getOppositeEdge().getOriginVertex();
    }

    public Edge getPrevLeftEdge() {
        return this.getNextLeftEdge().getNextLeftEdge();
    }

    public Edge getNextRightEdge() {
        return this.getOppositeEdge().getNextLeftEdge().getNextLeftEdge().getOppositeEdge();
    }

    public Edge getPrevRightEdge() {
        return this.getOppositeEdge().getNextLeftEdge().getOppositeEdge();
    }

    public Edge getRotLeftEdge() {
        return this.getNextLeftEdge().getNextLeftEdge().getOppositeEdge();
    }

    public Edge getRotRightEdge() {
        return this.getOppositeEdge().getNextLeftEdge();
    }

    public Face getRightFace() {
        return this.getOppositeEdge().getLeftFace();
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", isReal=" + isReal +
                ", isConstrained=" + isConstrained +
                '}';
    }
}
