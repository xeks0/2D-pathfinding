package org.xeks.iterators;

import org.xeks.data.Edge;
import org.xeks.data.Face;

public class FromFaceToInnerEdges {


    public FromFaceToInnerEdges() {

    }

    public Face fromFace;

    public Edge nextEdge;

    public Edge resultEdge;

    public Face setFromFace(Face value) {
        this.fromFace = value;
        this.nextEdge = this.fromFace.getEdge();
        return value;
    }


    public Edge next() {
        if ((this.nextEdge != null)) {
            this.resultEdge = this.nextEdge;
            this.nextEdge = this.nextEdge.getNextLeftEdge();
            if ((this.nextEdge.getId() == this.fromFace.getEdge().getId())) {
                this.nextEdge = null;
            }
        } else {
            this.resultEdge = null;
        }
        return this.resultEdge;
    }
}
