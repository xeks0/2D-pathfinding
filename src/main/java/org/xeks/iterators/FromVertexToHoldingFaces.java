package org.xeks.iterators;

import org.xeks.data.Edge;
import org.xeks.data.Face;
import org.xeks.data.Vertex;

public class FromVertexToHoldingFaces {

    public FromVertexToHoldingFaces() {

    }

    public Vertex fromVertex;

    public Edge nextEdge;

    public Face resultFace;

    public Vertex setFromVertex(Vertex value) {
        this.fromVertex = value;
        this.nextEdge = this.fromVertex.getEdge();
        return value;
    }


    public Face next() {
        if ((this.nextEdge != null)) {
            while (true) {
                this.resultFace = this.nextEdge.getLeftFace();
                this.nextEdge = this.nextEdge.getRotLeftEdge();
                if ((this.nextEdge.getId() == this.fromVertex.getEdge().getId())) {
                    this.nextEdge = null;
                    if (!(this.resultFace.isReal())) {
                        this.resultFace = null;
                    }

                    break;
                }
                if (this.resultFace.isReal()) {
                    break;
                }

            }

        } else {
            this.resultFace = null;
        }

        return this.resultFace;
    }

}
