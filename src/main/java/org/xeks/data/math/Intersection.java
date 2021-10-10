package org.xeks.data.math;

import org.xeks.data.Edge;
import org.xeks.data.Face;
import org.xeks.data.Vertex;

public class Intersection {

    public Intersection(int index, java.lang.Object[] params) {
        this.index = index;
        this.params = params;
    }

    public final int index;
    public final java.lang.Object[] params;

    public static final java.lang.String[] __hx_constructs = new java.lang.String[]{"EVertex", "EEdge", "EFace", "ENull"};

    public static Intersection EVertex(Vertex vertex) {
        return new Intersection(0, new java.lang.Object[]{vertex});
    }


    public static Intersection EEdge(Edge edge) {
        return new Intersection(1, new java.lang.Object[]{edge});
    }


    public static Intersection EFace(Face face) {
        return new Intersection(2, new java.lang.Object[]{face});
    }


    public static final Intersection ENull = new Intersection(3, null);


    public java.lang.String getTag() {
        return Intersection.__hx_constructs[this.index];
    }

}
