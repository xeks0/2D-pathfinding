package org.xeks.iterators;

import org.xeks.data.Edge;
import org.xeks.data.Vertex;

public class FromVertexToOutgoingEdges {


    public FromVertexToOutgoingEdges() {
        this.realEdgesOnly = true;
    }

    public Vertex _fromVertex;
    public Edge _nextEdge;
    public boolean realEdgesOnly;

    public Vertex set_fromVertex(Vertex value) {
        this._fromVertex = value;
        this._nextEdge = this._fromVertex.getEdge();
        while ((this.realEdgesOnly && !(this._nextEdge.isReal()))) {
            this._nextEdge = this._nextEdge.getRotLeftEdge();
        }
        return value;
    }


    public Edge _resultEdge;

    public Edge next() {
        if ((this._nextEdge != null)) {
            this._resultEdge = this._nextEdge;
            while (true) {
                this._nextEdge = this._nextEdge.getRotLeftEdge();
                if ((this._nextEdge.getId() == this._fromVertex.getEdge().getId())) {
                    this._nextEdge = null;
                    break;
                }
                if (!(((this.realEdgesOnly && !(this._nextEdge.isReal()))))) {
                    break;
                }

            }

        } else {
            this._resultEdge = null;
        }

        return this._resultEdge;
    }

}
