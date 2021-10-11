package org.xeks.ai;

import lombok.Getter;
import lombok.Setter;
import org.xeks.Vector;
import org.xeks.data.*;
import org.xeks.data.math.Geom2D;
import org.xeks.data.math.Intersection;
import org.xeks.data.math.Point2D;
import org.xeks.iterators.FromFaceToInnerEdges;

import java.util.Comparator;
import java.util.HashMap;

public class AStar {


    @Getter
    public double radius;

    @Setter
    public Mesh mesh;

    public HashMap<Face, Boolean> closedFaces;

    public Vector<Face> sortedOpenedFaces;

    public HashMap<Face, Boolean> openedFaces;

    public HashMap<Face, Edge> entryEdges;

    public HashMap<Face, Double> entryX;

    public HashMap<Face, Double> entryY;

    public HashMap<Face, Double> scoreF;

    public HashMap<Face, Double> scoreG;

    public HashMap<Face, Double> scoreH;

    public HashMap<Face, Face> predecessor;

    public FromFaceToInnerEdges iterEdge;

    public double radiusSquared;

    public double diameter;

    public double diameterSquared;

    public Face fromFace;

    public Face toFace;

    public Face curFace;


    public void dispose() {
        this.mesh = null;
        this.closedFaces = null;
        this.sortedOpenedFaces = null;
        this.openedFaces = null;
        this.entryEdges = null;
        this.entryX = null;
        this.entryY = null;
        this.scoreF = null;
        this.scoreG = null;
        this.scoreH = null;
        this.predecessor = null;
    }

    public double setRadius(double value) {
        this.radius = value;
        this.radiusSquared = (this.radius * this.radius);
        this.diameter = (this.radius * 2);
        this.diameterSquared = (this.diameter * this.diameter);
        return value;
    }


    public void findPath(double fromX, double fromY, double toX, double toY, Vector<Face> resultListFaces, Vector<Edge> resultListEdges) {
        this.closedFaces = new HashMap<>();
        this.sortedOpenedFaces = new Vector<>();
        this.openedFaces = new HashMap<>();
        this.entryEdges = new HashMap<>();
        this.entryX = new HashMap<>();
        this.entryY = new HashMap<>();
        this.scoreF = new HashMap<>();
        this.scoreG = new HashMap<>();
        this.scoreH = new HashMap<>();
        this.predecessor = new HashMap<>();
        Intersection loc = null;
        Edge locEdge = null;
        Vertex locVertex = null;
        double distance = 0.0;
        Point2D p1 = null;
        Point2D p2 = null;
        Point2D p3 = null;
        loc = Geom2D.locatePosition(fromX, fromY, this.mesh);
        switch (loc.index) {
            case 0: {
                Vertex vertex = ((Vertex) (loc.params[0]));
                locVertex = vertex;
                return;
            }


            case 1: {
                Edge edge = ((Edge) (loc.params[0]));
                {
                    locEdge = edge;
                    if (locEdge.isConstrained()) {
                        return;
                    }

                    this.fromFace = locEdge.getLeftFace();
                }
                break;
            }
            case 2: {
                Face face = ((Face) (loc.params[0]));
                this.fromFace = face;
                break;
            }
            case 3: {
                break;
            }


        }

        loc = Geom2D.locatePosition(toX, toY, this.mesh);
        switch (loc.index) {
            case 0: {
                Vertex vertex1 = ((Vertex) (loc.params[0]));
                {
                    locVertex = vertex1;
                    this.toFace = locVertex.getEdge().getLeftFace();
                }
                break;
            }
            case 1: {
                Edge edge1 = ((Edge) (loc.params[0]));
                {
                    locEdge = edge1;
                    this.toFace = locEdge.getLeftFace();
                }
                break;
            }
            case 2: {
                Face face1 = ((Face) (loc.params[0]));
                this.toFace = face1;
                break;
            }
            case 3: {
                break;
            }


        }

        this.sortedOpenedFaces.push(this.fromFace);
        ((this.entryEdges)).put(this.fromFace, null);
        this.entryX.put(this.fromFace, fromX);

        this.entryY.put(this.fromFace, fromY);
        this.scoreG.put(this.fromFace, (double) 0);
        double dist = Math.sqrt(((((toX - fromX)) * ((toX - fromX))) + (((toY - fromY)) * ((toY - fromY)))));
        this.scoreH.put(this.fromFace, dist);
        this.scoreF.put(this.fromFace, dist);
        Edge innerEdge = null;
        Face neighbourFace = null;
        Double f = 0.0;
        Double g = 0.0;
        Double h = 0.0;
        Point2D fromPoint = new Point2D();
        Point2D entryPoint = new Point2D();
        Point2D distancePoint = new Point2D();
        boolean fillDatas = false;
        while (true) {
            if ((this.sortedOpenedFaces.length == 0)) {
                this.curFace = null;
                break;
            }

            this.curFace = ((Face) (this.sortedOpenedFaces.pop()));
            if ((this.curFace == this.toFace)) {
                break;
            }

            this.iterEdge.setFromFace(this.curFace);
            while (true) {
                innerEdge = this.iterEdge.next();
                if (innerEdge == null) {
                    break;
                }
                if (innerEdge.isConstrained()) {
                    continue;
                }

                neighbourFace = innerEdge.getRightFace();
                if (((this.closedFaces).get(neighbourFace) == null)) {
                    if ((((this.curFace != this.fromFace) && (this.radius > 0)) && !(this.isWalkableByRadius(this.entryEdges.get(this.curFace), this.curFace, innerEdge)))) {
                        continue;
                    }

                    fromPoint.x = (this.entryX).get(this.curFace);
                    fromPoint.y = (this.entryY).get(this.curFace);
                    entryPoint.x = (((innerEdge.getOriginVertex().getPos().x + innerEdge.getDestinationVertex().getPos().x)) / 2);
                    entryPoint.y = (((innerEdge.getOriginVertex().getPos().y + innerEdge.getDestinationVertex().getPos().y)) / 2);
                    distancePoint.x = (entryPoint.x - toX);
                    distancePoint.y = (entryPoint.y - toY);
                    h = distancePoint.getLength();
                    distancePoint.x = (fromPoint.x - entryPoint.x);
                    distancePoint.y = (fromPoint.y - entryPoint.y);
                    g = this.scoreG.get(this.curFace) +distancePoint.getLength();
                    f = (h + g);
                    fillDatas = false;
                    //todo check syntax
                    if (( (this.openedFaces).get(neighbourFace) == null) || (!(this.openedFaces.get(neighbourFace) != null && this.openedFaces.get(neighbourFace)))) {
                        this.sortedOpenedFaces.push(neighbourFace);
                        (this.openedFaces).put(neighbourFace, true);
                        fillDatas = true;
                    } else {
                        if (Utils.compare(this.scoreF.get(neighbourFace), f) > 0) {
                            fillDatas = true;
                        }
                    }

                    if (fillDatas) {
                        this.entryEdges.put(neighbourFace, innerEdge);
                        {
                            double v = entryPoint.x;
                           (this.entryX).put(neighbourFace, v);
                        }
                        {
                            double v1 = entryPoint.y;
                           (this.entryY).put(neighbourFace, v1);
                        }

                        (this.scoreF).put(neighbourFace, f);
                        (this.scoreG).put(neighbourFace, g);
                        (this.scoreH).put(neighbourFace, h);
                        {
                            Face v2 = this.curFace;
                            this.predecessor.put(neighbourFace, v2);
                        }

                    }

                }

            }

            this.openedFaces.put(this.curFace, false);
            this.closedFaces.put(this.curFace, true);
            //with low distance value are at the end of the array
            SortArgInterface sortArgInterface = (int a, int b)-> scoreF.get(b).compareTo(scoreF.get(a));
            this.sortedOpenedFaces.sort(sortArgInterface);
        }

        if ((this.curFace == null)) {
            return;
        }

        resultListFaces.push(this.curFace);
        while ((this.curFace != this.fromFace)) {
            resultListEdges.unshift((this.entryEdges).get(this.curFace));
            this.curFace = (this.predecessor).get(this.curFace);
            resultListFaces.unshift(this.curFace);
        }

    }

    public boolean isWalkableByRadius(Edge fromEdge, Face throughFace, Edge toEdge) {
        Vertex vA = null;
        Vertex vB = null;
        Vertex vC = null;
        if ((fromEdge.getOriginVertex() == toEdge.getOriginVertex())) {
            vA = fromEdge.getDestinationVertex();
            vB = toEdge.getDestinationVertex();
            vC = fromEdge.getOriginVertex();
        } else {
            if ((fromEdge.getDestinationVertex() == toEdge.getDestinationVertex())) {
                vA = fromEdge.getOriginVertex();
                vB = toEdge.getOriginVertex();
                vC = fromEdge.getDestinationVertex();
            } else {
                if ((fromEdge.getOriginVertex() == toEdge.getDestinationVertex())) {
                    vA = fromEdge.getDestinationVertex();
                    vB = toEdge.getOriginVertex();
                    vC = fromEdge.getOriginVertex();
                } else {
                    if ((fromEdge.getDestinationVertex() == toEdge.getOriginVertex())) {
                        vA = fromEdge.getOriginVertex();
                        vB = toEdge.getDestinationVertex();
                        vC = fromEdge.getDestinationVertex();
                    }

                }

            }

        }

        double dot = 0.0;
        boolean result = false;
        double distSquared = 0.0;
        dot = ((((vC.getPos().x - vA.getPos().x)) * ((vB.getPos().x - vA.getPos().x))) + (((vC.getPos().y - vA.getPos().y)) * ((vB.getPos().y - vA.getPos().y))));
        if ((dot <= 0)) {
            distSquared = ((((vC.getPos().x - vA.getPos().x)) * ((vC.getPos().x - vA.getPos().x))) + (((vC.getPos().y - vA.getPos().y)) * ((vC.getPos().y - vA.getPos().y))));
            if ((distSquared >= this.diameterSquared)) {
                return true;
            } else {
                return false;
            }

        }

        dot = ((((vC.getPos().x - vB.getPos().x)) * ((vA.getPos().x - vB.getPos().x))) + (((vC.getPos().y - vB.getPos().y)) * ((vA.getPos().y - vB.getPos().y))));
        if ((dot <= 0)) {
            distSquared = ((((vC.getPos().x - vB.getPos().x)) * ((vC.getPos().x - vB.getPos().x))) + (((vC.getPos().y - vB.getPos().y)) * ((vC.getPos().y - vB.getPos().y))));
            if ((distSquared >= this.diameterSquared)) {
                return true;
            } else {
                return false;
            }

        }

        Edge adjEdge = null;
        if (((((throughFace.getEdge() != fromEdge) && (throughFace.getEdge().getOppositeEdge() != fromEdge)) && (throughFace.getEdge() != toEdge)) && (throughFace.getEdge().getOppositeEdge() != toEdge))) {
            adjEdge = throughFace.getEdge();
        } else {
            if (((((throughFace.getEdge().getNextLeftEdge() != fromEdge) && (throughFace.getEdge().getNextLeftEdge().getOppositeEdge() != fromEdge)) && (throughFace.getEdge().getNextLeftEdge() != toEdge)) && (throughFace.getEdge().getNextLeftEdge().getOppositeEdge() != toEdge))) {
                adjEdge = throughFace.getEdge().getNextLeftEdge();
            } else {
                adjEdge = throughFace.getEdge().getPrevLeftEdge();
            }

        }

        if (adjEdge.isConstrained()) {
            Point2D proj = new Point2D(vC.getPos().x, vC.getPos().y);
            Geom2D.projectOrthogonaly(proj, adjEdge);
            distSquared = ((((proj.x - vC.getPos().x)) * ((proj.x - vC.getPos().x))) + (((proj.y - vC.getPos().y)) * ((proj.y - vC.getPos().y))));
            if ((distSquared >= this.diameterSquared)) {
                return true;
            } else {
                return false;
            }

        } else {
            double distSquaredA = ((((vC.getPos().x - vA.getPos().x)) * ((vC.getPos().x - vA.getPos().x))) + (((vC.getPos().y - vA.getPos().y)) * ((vC.getPos().y - vA.getPos().y))));
            double distSquaredB = ((((vC.getPos().x - vB.getPos().x)) * ((vC.getPos().x - vB.getPos().x))) + (((vC.getPos().y - vB.getPos().y)) * ((vC.getPos().y - vB.getPos().y))));
            if (((distSquaredA < this.diameterSquared) || (distSquaredB < this.diameterSquared))) {
                return false;
            } else {
                Vector<Face> vFaceToCheck = new Vector<Face>();
                Vector<Edge> vFaceIsFromEdge = new Vector<Edge>();
                HashMap<Face, java.lang.Object> facesDone = new HashMap<>();
                vFaceIsFromEdge.push(adjEdge);
                if ((adjEdge.getLeftFace() == throughFace)) {
                    vFaceToCheck.push(adjEdge.getRightFace());
                    facesDone.put(adjEdge.getRightFace(), true);
                } else {
                    vFaceToCheck.push(adjEdge.getLeftFace());
                    facesDone.put(adjEdge.getLeftFace(), true);
                }

                Face currFace = null;
                Edge faceFromEdge = null;
                Edge currEdgeA = null;
                Face nextFaceA = null;
                Edge currEdgeB = null;
                Face nextFaceB = null;
                while ((vFaceToCheck.length > 0)) {
                    currFace = ((Face) (vFaceToCheck.shift()));
                    faceFromEdge = ((Edge) (vFaceIsFromEdge.shift()));
                    if (((currFace.getEdge() == faceFromEdge) || (currFace.getEdge() == faceFromEdge.getOppositeEdge()))) {
                        currEdgeA = currFace.getEdge().getNextLeftEdge();
                        currEdgeB = currFace.getEdge().getNextLeftEdge().getNextLeftEdge();
                    } else {
                        if (((currFace.getEdge().getNextLeftEdge() == faceFromEdge) || (currFace.getEdge().getNextLeftEdge() == faceFromEdge.getOppositeEdge()))) {
                            currEdgeA = currFace.getEdge();
                            currEdgeB = currFace.getEdge().getNextLeftEdge().getNextLeftEdge();
                        } else {
                            currEdgeA = currFace.getEdge();
                            currEdgeB = currFace.getEdge().getNextLeftEdge();
                        }

                    }

                    if ((currEdgeA.getLeftFace() == currFace)) {
                        nextFaceA = currEdgeA.getRightFace();
                    } else {
                        nextFaceA = currEdgeA.getLeftFace();
                    }

                    if ((currEdgeB.getLeftFace() == currFace)) {
                        nextFaceB = currEdgeB.getRightFace();
                    } else {
                        nextFaceB = currEdgeB.getLeftFace();
                    }

                    if (((facesDone.get(nextFaceA) == null)) && (Geom2D.distanceSquaredVertexToEdge(vC, currEdgeA) < this.diameterSquared)) {
                        if (currEdgeA.isConstrained()) {
                            return false;
                        } else {
                            vFaceToCheck.push(nextFaceA);
                            vFaceIsFromEdge.push(currEdgeA);
                            facesDone.put(nextFaceA, true);
                        }

                    }

                    if (((facesDone.get(nextFaceB) == null)) && (Geom2D.distanceSquaredVertexToEdge(vC, currEdgeB) < this.diameterSquared))
                    {
                        if (currEdgeB.isConstrained()) {
                            return false;
                        } else {
                            vFaceToCheck.push(nextFaceB);
                            vFaceIsFromEdge.push(currEdgeB);
                            facesDone.put(nextFaceB, true);
                        }
                    }
                }
                return true;
            }
        }
    }
}
