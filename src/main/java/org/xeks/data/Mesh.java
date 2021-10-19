package org.xeks.data;

import lombok.Getter;
import lombok.Setter;
import org.xeks.Vector;
import org.xeks.data.math.Geom2D;
import org.xeks.data.math.Intersection;
import org.xeks.data.math.Matrix2D;
import org.xeks.data.math.Point2D;
import org.xeks.iterators.FromVertexToOutgoingEdges;

import java.util.HashMap;

public class Mesh {

    public static int INC = 0;

    public Mesh(double width, double height) {
        staticConstructor(this, width, height);
    }

    public static void staticConstructor(Mesh _this, double width, double height) {
        _this.id = Mesh.INC;
        Mesh.INC++;
        _this.objectsUpdateInProgress = false;
        _this.edgesToCheck = null;
        _this.centerVertex = null;
        _this.objects = null;
        _this.constraintShapes = null;
        _this.faces = null;
        _this.edges = null;
        _this.vertices = null;
        _this.clipping = false;
        _this.height = 0;
        _this.width = 0;
        _this.width = width;
        _this.height = height;
        _this.clipping = true;
        _this.vertices = new Vector<>();
        _this.edges = new Vector<>();
        _this.faces = new Vector<>();
        _this.constraintShapes = new Vector<>();
        _this.objects = new Vector<>();
        _this.edgesToCheck = new Vector<>();
    }

    @Getter
    @Setter
    public int id;
    @Getter
    @Setter
    public double width;

    @Getter
    @Setter
    public double height;

    @Getter
    @Setter
    public boolean clipping;

    @Getter
    @Setter
    public Vector<Vertex> vertices;

    @Getter
    @Setter
    public Vector<Edge> edges;

    @Getter
    @Setter
    public Vector<Face> faces;

    @Getter
    @Setter
    public Vector<ConstraintShape> constraintShapes;

    @Getter
    @Setter
    public Vector<Obstacle> objects;

    @Getter
    @Setter
    public Vertex centerVertex;

    @Getter
    @Setter
    public Vector<Edge> edgesToCheck;

    @Getter
    @Setter
    public boolean objectsUpdateInProgress;


    public void dispose() {
        while ((this.vertices.length > 0)) {
            this.vertices.pop().dispose();
        }
        this.vertices = null;
        while ((this.edges.length > 0)) {
            this.edges.pop().dispose();
        }
        this.edges = null;
        while ((this.faces.length > 0)) {
            this.faces.pop().dispose();
        }
        this.faces = null;
        while ((this.constraintShapes.length > 0)) {

            this.constraintShapes.pop().dispose();
        }
        this.constraintShapes = null;
        while ((this.objects.length > 0)) {
            this.objects.pop().dispose();
        }
        this.objects = null;
        this.edgesToCheck = null;
        this.centerVertex = null;
    }

    public void buildFromRecord(String rec) {

        String[] positions = rec.split(";");
        int i = 0;
        while ((i < positions.length)) {
            this.insertConstraintSegment(Float.parseFloat(positions[i]), Float.parseFloat(positions[i + 1]), Float.parseFloat(positions[i + 2]), Float.parseFloat(positions[i + 3]));
            i += 4;
        }

    }

    public ConstraintSegment insertConstraintSegment(double x1, double y1, double x2, double y2) {
        int p1pos = this.findPositionFromBounds(x1, y1);
        int p2pos = this.findPositionFromBounds(x2, y2);
        double newX1 = x1;
        double newY1 = y1;
        double newX2 = x2;
        double newY2 = y2;
        if ((this.clipping && (((p1pos != 0) || (p2pos != 0))))) {
            Point2D intersectPoint = new Point2D();
            if (((p1pos != 0) && (p2pos != 0))) {
                if ((((((x1 <= 0) && (x2 <= 0)) || ((x1 >= this.width) && (x2 >= this.width))) || ((y1 <= 0) && (y2 <= 0))) || ((y1 >= this.height) && (y2 >= this.height)))) {
                    return null;
                }

                if ((((p1pos == 8) && (p2pos == 4)) || ((p1pos == 4) && (p2pos == 8)))) {

                    Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), ((double) (0)), this.height, intersectPoint, null, null);
                    newX1 = intersectPoint.x;
                    newY1 = intersectPoint.y;
                    Geom2D.intersections2segments(x1, y1, x2, y2, this.width, ((double) (0)), this.width, this.height, intersectPoint, null, null);
                    newX2 = intersectPoint.x;
                    newY2 = intersectPoint.y;
                } else {
                    if ((((p1pos == 2) && (p2pos == 6)) || ((p1pos == 6) && (p2pos == 2)))) {
                        Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), this.width, ((double) (0)), intersectPoint, null, null);
                        newX1 = intersectPoint.x;
                        newY1 = intersectPoint.y;

                        Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), this.height, this.width, this.height, intersectPoint, null, null);
                        newX2 = intersectPoint.x;
                        newY2 = intersectPoint.y;
                    } else {
                        if ((((p1pos == 2) && (p2pos == 8)) || ((p1pos == 8) && (p2pos == 2)))) {

                            if (Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), this.width, ((double) (0)), intersectPoint, null, null)) {
                                newX1 = intersectPoint.x;
                                newY1 = intersectPoint.y;
                                Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), ((double) (0)), this.height, intersectPoint, null, null);
                                newX2 = intersectPoint.x;
                                newY2 = intersectPoint.y;
                            } else {
                                return null;
                            }

                        } else {

                            if ((((p1pos == 2) && (p2pos == 4)) || ((p1pos == 4) && (p2pos == 2)))) {
                                if (Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), this.width, ((double) (0)), intersectPoint, null, null)) {
                                    newX1 = intersectPoint.x;
                                    newY1 = intersectPoint.y;
                                    Geom2D.intersections2segments(x1, y1, x2, y2, this.width, ((double) (0)), this.width, this.height, intersectPoint, null, null);
                                    newX2 = intersectPoint.x;
                                    newY2 = intersectPoint.y;
                                } else {
                                    return null;
                                }

                            } else {
                                if ((((p1pos == 6) && (p2pos == 4)) || ((p1pos == 4) && (p2pos == 6)))) {

                                    if (Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), this.height, this.width, this.height, intersectPoint, null, null)) {
                                        newX1 = intersectPoint.x;
                                        newY1 = intersectPoint.y;
                                        Geom2D.intersections2segments(x1, y1, x2, y2, this.width, ((double) (0)), this.width, this.height, intersectPoint, null, null);
                                        newX2 = intersectPoint.x;
                                        newY2 = intersectPoint.y;
                                    } else {
                                        return null;
                                    }

                                } else {

                                    if ((((p1pos == 8) && (p2pos == 6)) || ((p1pos == 6) && (p2pos == 8)))) {
                                        if (Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), this.height, this.width, this.height, intersectPoint, null, null)) {
                                            newX1 = intersectPoint.x;
                                            newY1 = intersectPoint.y;
                                            Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), ((double) (0)), this.height, intersectPoint, null, null);
                                            newX2 = intersectPoint.x;
                                            newY2 = intersectPoint.y;
                                        } else {
                                            return null;
                                        }

                                    } else {
                                        boolean firstDone = false;
                                        boolean secondDone = false;
                                        if (Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), this.width, ((double) (0)), intersectPoint, null, null)) {
                                            newX1 = intersectPoint.x;
                                            newY1 = intersectPoint.y;
                                            firstDone = true;
                                        }

                                        if (Geom2D.intersections2segments(x1, y1, x2, y2, this.width, ((double) (0)), this.width, this.height, intersectPoint, null, null)) {
                                            if (!(firstDone)) {
                                                newX1 = intersectPoint.x;
                                                newY1 = intersectPoint.y;
                                                firstDone = true;
                                            } else {
                                                newX2 = intersectPoint.x;
                                                newY2 = intersectPoint.y;
                                                secondDone = true;
                                            }

                                        }

                                        if ((!(secondDone) && Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), this.height, this.width, this.height, intersectPoint, null, null))) {
                                            if (!(firstDone)) {
                                                newX1 = intersectPoint.x;
                                                newY1 = intersectPoint.y;
                                                firstDone = true;
                                            } else {
                                                newX2 = intersectPoint.x;
                                                newY2 = intersectPoint.y;
                                                secondDone = true;
                                            }

                                        }
                                        if ((!(secondDone) && Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), ((double) (0)), this.height, intersectPoint, null, null))) {
                                            newX2 = intersectPoint.x;
                                            newY2 = intersectPoint.y;
                                        }
                                        if (!(firstDone)) {
                                            return null;
                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            } else {
                if (((p1pos == 2) || (p2pos == 2))) {
                    Geom2D.intersections2segments(x1, y1, x2, y2, 0, 0, this.width, 0, intersectPoint, null, null);
                } else {
                    if (((p1pos == 4) || (p2pos == 4))) {
                        Geom2D.intersections2segments(x1, y1, x2, y2, this.width, ((double) (0)), this.width, this.height, intersectPoint, null, null);
                    } else {
                        if (((p1pos == 6) || (p2pos == 6))) {
                            Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), this.height, this.width, this.height, intersectPoint, null, null);
                        } else {
                            if (((p1pos == 8) || (p2pos == 8))) {
                                Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), ((double) (0)), this.height, intersectPoint, null, null);
                            } else {
                                if (!(Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), this.width, ((double) (0)), intersectPoint, null, null))) {
                                    if (!(Geom2D.intersections2segments(x1, y1, x2, y2, this.width, ((double) (0)), this.width, this.height, intersectPoint, null, null))) {
                                        if (!(Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), this.height, this.width, this.height, intersectPoint, null, null))) {
                                            Geom2D.intersections2segments(x1, y1, x2, y2, ((double) (0)), ((double) (0)), ((double) (0)), this.height, intersectPoint, null, null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if ((p1pos == 0)) {
                    newX1 = x1;
                    newY1 = y1;
                } else {
                    newX1 = x2;
                    newY1 = y2;
                }
                newX2 = intersectPoint.x;
                newY2 = intersectPoint.y;
            }

        }

        Vertex vertexDown = this.insertVertex(newX1, newY1);
        if ((vertexDown == null)) {
            return null;
        }
        Vertex vertexUp = this.insertVertex(newX2, newY2);
        if ((vertexUp == null)) {
            return null;
        }
        if ((vertexDown == vertexUp)) {
            return null;
        }


        FromVertexToOutgoingEdges iterVertexToOutEdges = new FromVertexToOutgoingEdges();
        Vertex currVertex = null;
        Edge currEdge = null;
        int i = 0;
        ConstraintSegment segment = new ConstraintSegment();
        Edge tempEdgeDownUp = new Edge();
        Edge tempSdgeUpDown = new Edge();
        tempEdgeDownUp.setDatas(vertexDown, tempSdgeUpDown, null, null, true, true);
        tempSdgeUpDown.setDatas(vertexUp, tempEdgeDownUp, null, null, true, true);
        Vector<Edge> intersectedEdges = new Vector<>();
        Vector<Edge> leftBoundingEdges = new Vector<>();
        Vector<Edge> rightBoundingEdges = new Vector<>();
        Intersection currObjet = null;
        Point2D pIntersect = new Point2D();
        Edge edgeLeft = null;
        Edge newEdgeDownUp = null;
        Edge newEdgeUpDown = null;
        boolean done;
        currVertex = vertexDown;
        currObjet = Intersection.EVertex(currVertex);
        while (true) {
            done = false;
            switch (currObjet.index) {
                case 0: {
                    Vertex vertex = ((Vertex) (currObjet.params[0]));
                    {
                        currVertex = vertex;
                        iterVertexToOutEdges.set_fromVertex(currVertex);
                        while (true) {
                            currEdge = iterVertexToOutEdges.next();
                            if (currEdge == null) {
                                break;
                            }
                            if ((currEdge.getDestinationVertex() == vertexUp)) {
                                if (!(currEdge.isConstrained())) {
                                    currEdge.setConstrained(true);
                                    currEdge.getOppositeEdge().setConstrained(true);
                                }
                                currEdge.addFromConstraintSegment(segment);
                                currEdge.getOppositeEdge().fromConstraintSegments = currEdge.fromConstraintSegments;
                                vertexDown.addFromConstraintSegment(segment);
                                vertexUp.addFromConstraintSegment(segment);
                                segment.addEdge(currEdge);
                                return segment;
                            }
                            if ((Geom2D.distanceSquaredVertexToEdge(currEdge.getDestinationVertex(), tempEdgeDownUp) <= 0.0001)) {
                                if (!(currEdge.isConstrained())) {
                                    currEdge.setConstrained(true);
                                    currEdge.getOppositeEdge().setConstrained(true);
                                }
                                currEdge.addFromConstraintSegment(segment);
                                currEdge.getOppositeEdge().fromConstraintSegments = currEdge.fromConstraintSegments;
                                vertexDown.addFromConstraintSegment(segment);
                                segment.addEdge(currEdge);
                                vertexDown = currEdge.getDestinationVertex();
                                tempEdgeDownUp.setOriginVertex(vertexDown);
                                currObjet = Intersection.EVertex(vertexDown);
                                done = true;
                                break;
                            }
                        }
                        if (done) {
                            continue;
                        }
                        iterVertexToOutEdges.set_fromVertex(currVertex);
                        while (true) {
                            currEdge = iterVertexToOutEdges.next();
                            if (currEdge == null) {
                                break;
                            }
                            currEdge = currEdge.getNextLeftEdge();
                            if (Geom2D.intersections2edges(currEdge, tempEdgeDownUp, pIntersect, null, null)) {
                                if (currEdge.isConstrained()) {
                                    vertexDown = this.splitEdge(currEdge, pIntersect.x, pIntersect.y);
                                    iterVertexToOutEdges.set_fromVertex(currVertex);
                                    while (true) {
                                        currEdge = iterVertexToOutEdges.next();
                                        if (currEdge == null) {
                                            break;
                                        }
                                        if ((currEdge.getDestinationVertex() == vertexDown)) {
                                            currEdge.setConstrained(true);
                                            currEdge.getOppositeEdge().setConstrained(true);
                                            currEdge.addFromConstraintSegment(segment);
                                            currEdge.getOppositeEdge().fromConstraintSegments = currEdge.fromConstraintSegments;
                                            segment.addEdge(currEdge);
                                            break;
                                        }
                                    }
                                    currVertex.addFromConstraintSegment(segment);
                                    tempEdgeDownUp.setOriginVertex(vertexDown);
                                    currObjet = Intersection.EVertex(vertexDown);
                                } else {
                                    intersectedEdges.push(currEdge);
                                    leftBoundingEdges.unshift(currEdge.getNextLeftEdge());
                                    rightBoundingEdges.push(currEdge.getPrevLeftEdge());
                                    currEdge = currEdge.getOppositeEdge();
                                    currObjet = Intersection.EEdge(currEdge);
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
                case 1: {
                    Edge edge = ((Edge) (currObjet.params[0]));
                    {
                        currEdge = edge;
                        edgeLeft = currEdge.getNextLeftEdge();
                        if ((edgeLeft.getDestinationVertex() == vertexUp)) {
                            leftBoundingEdges.unshift(edgeLeft.getNextLeftEdge());
                            rightBoundingEdges.push(edgeLeft);
                            newEdgeDownUp = new Edge();
                            newEdgeUpDown = new Edge();
                            newEdgeDownUp.setDatas(vertexDown, newEdgeUpDown, null, null, true, true);
                            newEdgeUpDown.setDatas(vertexUp, newEdgeDownUp, null, null, true, true);
                            leftBoundingEdges.push(newEdgeDownUp);
                            rightBoundingEdges.push(newEdgeUpDown);
                            this.insertNewConstrainedEdge(segment, newEdgeDownUp, intersectedEdges, leftBoundingEdges, rightBoundingEdges);
                            return segment;
                        } else {
                            if ((Geom2D.distanceSquaredVertexToEdge(edgeLeft.getDestinationVertex(), tempEdgeDownUp) <= 0.0001)) {
                                leftBoundingEdges.unshift(edgeLeft.getNextLeftEdge());
                                rightBoundingEdges.push(edgeLeft);
                                newEdgeDownUp = new Edge();
                                newEdgeUpDown = new Edge();
                                newEdgeDownUp.setDatas(vertexDown, newEdgeUpDown, null, null, true, true);
                                newEdgeUpDown.setDatas(edgeLeft.getDestinationVertex(), newEdgeDownUp, null, null, true, true);
                                leftBoundingEdges.push(newEdgeDownUp);
                                rightBoundingEdges.push(newEdgeUpDown);
                                this.insertNewConstrainedEdge(segment, newEdgeDownUp, intersectedEdges, leftBoundingEdges, rightBoundingEdges);
                                intersectedEdges.splice(0, intersectedEdges.length);
                                leftBoundingEdges.splice(0, leftBoundingEdges.length);
                                rightBoundingEdges.splice(0, rightBoundingEdges.length);
                                vertexDown = edgeLeft.getDestinationVertex();
                                tempEdgeDownUp.setOriginVertex(vertexDown);
                                currObjet = Intersection.EVertex(vertexDown);
                            } else {
                                if (Geom2D.intersections2edges(edgeLeft, tempEdgeDownUp, pIntersect, null, null)) {
                                    if (edgeLeft.isConstrained()) {
                                        currVertex = this.splitEdge(edgeLeft, pIntersect.x, pIntersect.y);
                                        iterVertexToOutEdges.set_fromVertex(currVertex);

                                        while (true) {
                                            currEdge = iterVertexToOutEdges.next();
                                            if (currEdge == null) {
                                                break;
                                            }
                                            if ((currEdge.getDestinationVertex().getId() == leftBoundingEdges.get(0).getOriginVertex().getId())) {
                                                leftBoundingEdges.unshift(currEdge);
                                            }
                                            if ((currEdge.getDestinationVertex().getId() == rightBoundingEdges.get((rightBoundingEdges.length - 1)).getDestinationVertex().getId())) {
                                                rightBoundingEdges.push(currEdge.getOppositeEdge());
                                            }
                                        }
                                        newEdgeDownUp = new Edge();
                                        newEdgeUpDown = new Edge();
                                        newEdgeDownUp.setDatas(vertexDown, newEdgeUpDown, null, null, true, true);
                                        newEdgeUpDown.setDatas(currVertex, newEdgeDownUp, null, null, true, true);
                                        leftBoundingEdges.push(newEdgeDownUp);
                                        rightBoundingEdges.push(newEdgeUpDown);
                                        this.insertNewConstrainedEdge(segment, newEdgeDownUp, intersectedEdges, leftBoundingEdges, rightBoundingEdges);
                                        intersectedEdges.splice(0, intersectedEdges.length);
                                        leftBoundingEdges.splice(0, leftBoundingEdges.length);
                                        rightBoundingEdges.splice(0, rightBoundingEdges.length);
                                        vertexDown = currVertex;
                                        tempEdgeDownUp.setOriginVertex(vertexDown);
                                        currObjet = Intersection.EVertex(vertexDown);
                                    } else {
                                        intersectedEdges.push(edgeLeft);
                                        leftBoundingEdges.unshift(edgeLeft.getNextLeftEdge());
                                        currEdge = edgeLeft.getOppositeEdge();
                                        currObjet = Intersection.EEdge(currEdge);
                                    }

                                } else {
                                    edgeLeft = edgeLeft.getNextLeftEdge();
                                    Geom2D.intersections2edges(edgeLeft, tempEdgeDownUp, pIntersect, null, null);
                                    if (edgeLeft.isConstrained()) {
                                        currVertex = this.splitEdge(edgeLeft, pIntersect.x, pIntersect.y);
                                        iterVertexToOutEdges.set_fromVertex(currVertex);
                                        while (true) {
                                            currEdge = iterVertexToOutEdges.next();
                                            if (currEdge == null) {
                                                break;
                                            }
                                            if ((currEdge.getDestinationVertex() == leftBoundingEdges.get(0).getOriginVertex())) {
                                                leftBoundingEdges.unshift(currEdge);
                                            }
                                            if ((currEdge.getDestinationVertex() == rightBoundingEdges.get((rightBoundingEdges.length - 1)).getDestinationVertex())) {
                                                rightBoundingEdges.push(currEdge.getOppositeEdge());
                                            }
                                        }
                                        newEdgeDownUp = new Edge();
                                        newEdgeUpDown = new Edge();
                                        newEdgeDownUp.setDatas(vertexDown, newEdgeUpDown, null, null, true, true);
                                        newEdgeUpDown.setDatas(currVertex, newEdgeDownUp, null, null, true, true);
                                        leftBoundingEdges.push(newEdgeDownUp);
                                        rightBoundingEdges.push(newEdgeUpDown);
                                        this.insertNewConstrainedEdge(segment, newEdgeDownUp, intersectedEdges, leftBoundingEdges, rightBoundingEdges);
                                        intersectedEdges.splice(0, intersectedEdges.length);
                                        leftBoundingEdges.splice(0, leftBoundingEdges.length);
                                        rightBoundingEdges.splice(0, rightBoundingEdges.length);
                                        vertexDown = currVertex;
                                        tempEdgeDownUp.setOriginVertex(vertexDown);
                                        currObjet = Intersection.EVertex(vertexDown);
                                    } else {
                                        intersectedEdges.push(edgeLeft);
                                        rightBoundingEdges.push(edgeLeft.getPrevLeftEdge());
                                        currEdge = edgeLeft.getOppositeEdge();
                                        currObjet = Intersection.EEdge(currEdge);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    Face face = ((Face) (currObjet.params[0]));
                    break;
                }
                case 3: {
                    break;
                }
            }
        }
    }

    public void insertNewConstrainedEdge(ConstraintSegment fromSegment, Edge edgeDownUp, Vector<Edge> intersectedEdges, Vector<Edge> leftBoundingEdges, Vector<Edge> rightBoundingEdges) {
        this.edges.push(edgeDownUp);
        this.edges.push(edgeDownUp.getOppositeEdge());
        edgeDownUp.addFromConstraintSegment(fromSegment);
        edgeDownUp.getOppositeEdge().fromConstraintSegments = edgeDownUp.fromConstraintSegments;
        fromSegment.addEdge(edgeDownUp);
        edgeDownUp.getOriginVertex().addFromConstraintSegment(fromSegment);
        edgeDownUp.getDestinationVertex().addFromConstraintSegment(fromSegment);
        this.untriangulate(intersectedEdges);
        this.triangulate(leftBoundingEdges, true);
        this.triangulate(rightBoundingEdges, true);
    }


    public void untriangulate(Vector<Edge> edgesList) {
        int i = 0;
        HashMap<Vertex, Object> verticesCleaned = new HashMap<>();
        Edge currEdge = null;
        Edge outEdge = null;
        {
            int _g1 = 0;
            int _g = edgesList.length;
            while ((_g1 < _g)) {
                int i1 = _g1++;
                currEdge = edgesList.get(i1);
                if (verticesCleaned.get(currEdge.getOriginVertex()) == null) { //todo check npe
                    currEdge.getOriginVertex().setEdge(currEdge.getPrevLeftEdge().getOppositeEdge());
                    verticesCleaned.put(currEdge.getOriginVertex(), true);
                }
                if (verticesCleaned.get(currEdge.getDestinationVertex()) == null) { //todo check npe
                    currEdge.getDestinationVertex().setEdge(currEdge.getNextLeftEdge());
                    verticesCleaned.put(currEdge.getDestinationVertex(), true);
                }

                this.faces.splice(this.faces.indexOf(currEdge.getLeftFace(), null), 1);
                currEdge.getLeftFace().dispose();
                if ((i1 == (edgesList.length - 1))) {
                    this.faces.splice(this.faces.indexOf(currEdge.getRightFace(), null), 1);
                    currEdge.getRightFace().dispose();
                }
            }
        }
        {
            int _g11 = 0;
            int _g2 = edgesList.length;
            while ((_g11 < _g2)) {
                int i2 = _g11++;
                currEdge = edgesList.get(i2);
                this.edges.splice(this.edges.indexOf(currEdge.getOppositeEdge(), null), 1);
                this.edges.splice(this.edges.indexOf(currEdge, null), 1);
                currEdge.getOppositeEdge().dispose();
                currEdge.dispose();
            }

        }

    }


    public void triangulate(Vector<Edge> bound, boolean isReal) {
        if ((bound.length < 2)) {
            return;
        } else {
            if ((bound.length == 2)) {
                Object value = ((("  - edge0: " + bound.get(0).getOriginVertex().getId()) + " -> ") + bound.get(0).getDestinationVertex().getId());
                //line 1404 "/Users/tao/projects/hxDaedalus/src/Mesh.hx"
                Object value1 = ((("  - edge1: " + bound.get(1).getOriginVertex().getId()) + " -> ") + bound.get(1).getDestinationVertex().getId());
                return;
            } else {
                if ((bound.length == 3)) {
                    Face f = new Face();
                    f.setDatas(bound.get(0), isReal);
                    this.faces.push(f);
                    bound.get(0).setLeftFace(f);
                    bound.get(1).setLeftFace(f);
                    bound.get(2).setLeftFace(f);
                    bound.get(0).setNextLeftEdge(bound.get(1));
                    bound.get(1).setNextLeftEdge(bound.get(2));
                    bound.get(2).setNextLeftEdge(bound.get(0));
                } else {
                    Edge baseEdge = bound.get(0);
                    Vertex vertexA = baseEdge.getOriginVertex();
                    Vertex vertexB = baseEdge.getDestinationVertex();
                    Vertex vertexC = null;
                    Vertex vertexCheck = null;
                    Point2D circumcenter = new Point2D();
                    double radiusSquared = 0.0;
                    double distanceSquared = 0.0;
                    boolean isDelaunay = false;
                    int index = 0;
                    int i = 0;
                    {
                        int _g1 = 2;
                        int _g = bound.length;
                        while ((_g1 < _g)) {
                            int i1 = _g1++;
                            vertexC = bound.get(i1).getOriginVertex();
                            if ((Geom2D.getRelativePosition2(vertexC.getPos().x, vertexC.getPos().y, baseEdge) == 1)) {
                                index = i1;
                                isDelaunay = true;
                                Geom2D.getCircumcenter(vertexA.getPos().x, vertexA.getPos().y, vertexB.getPos().x, vertexB.getPos().y, vertexC.getPos().x, vertexC.getPos().y, circumcenter);
                                radiusSquared = ((((vertexA.getPos().x - circumcenter.x)) * ((vertexA.getPos().x - circumcenter.x))) + (((vertexA.getPos().y - circumcenter.y)) * ((vertexA.getPos().y - circumcenter.y))));
                                radiusSquared -= 0.0001;
                                {
                                    int _g3 = 2;
                                    int _g2 = bound.length;
                                    while ((_g3 < _g2)) {
                                        int j = _g3++;
                                        if ((j != i1)) {
                                            vertexCheck = bound.get(j).getOriginVertex();
                                            distanceSquared = ((((vertexCheck.getPos().x - circumcenter.x)) * ((vertexCheck.getPos().x - circumcenter.x))) + (((vertexCheck.getPos().y - circumcenter.y)) * ((vertexCheck.getPos().y - circumcenter.y))));
                                            if ((distanceSquared < radiusSquared)) {
                                                isDelaunay = false;
                                                break;
                                            }

                                        }

                                    }

                                }
                                if (isDelaunay) {
                                    break;
                                }

                            }

                        }

                    }
                    if (!(isDelaunay)) {
                        java.lang.String s = "";
                        {
                            int _g11 = 0;
                            int _g4 = bound.length;
                            while ((_g11 < _g4)) {
                                int i2 = _g11++;
                                s += (bound.get(i2).getOriginVertex().getPos().x + " , ");
                                s += (bound.get(i2).getOriginVertex().getPos().y + " , ");
                                s += (bound.get(i2).getDestinationVertex().getPos().x + " , ");
                                s += (bound.get(i2).getDestinationVertex().getPos().y + " , ");
                            }

                        }

                        index = 2;
                    }
                    Edge edgeA = null;
                    Edge edgeAopp = null;
                    Edge edgeB = null;
                    Edge edgeBopp = null;
                    Vector<Edge> boundA = null;
                    Vector<Edge> boundM = null;
                    Vector<Edge> boundB = null;
                    if ((index < (bound.length - 1))) {
                        edgeA = new Edge();
                        edgeAopp = new Edge();
                        this.edges.push(edgeA);
                        this.edges.push(edgeAopp);
                        edgeA.setDatas(vertexA, edgeAopp, null, null, isReal, false);
                        edgeAopp.setDatas(bound.get(index).getOriginVertex(), edgeA, null, null, isReal, false);
                        boundA = bound.slice(index, null);
                        boundA.push(edgeA);
                        this.triangulate(boundA, isReal);
                    }
                    if ((index > 2)) {
                        edgeB = new Edge();
                        edgeBopp = new Edge();
                        this.edges.push(edgeB);
                        this.edges.push(edgeBopp);
                        edgeB.setDatas(bound.get(1).getOriginVertex(), edgeBopp, null, null, isReal, false);
                        edgeBopp.setDatas(bound.get(index).getOriginVertex(), edgeB, null, null, isReal, false);
                        boundB = bound.slice(1, index);
                        boundB.push(edgeBopp);
                        this.triangulate(boundB, isReal);
                    }
                    if ((index == 2)) {
                        boundM = new Vector<Edge>(new Edge[]{baseEdge, bound.get(1), edgeAopp});
                    } else {
                        if ((index == (bound.length - 1))) {
                            boundM = new Vector<Edge>(new Edge[]{baseEdge, edgeB, bound.get(index)});
                        } else {
                            boundM = new Vector<Edge>(new Edge[]{baseEdge, edgeB, edgeAopp});
                        }
                    }
                    this.triangulate(boundM, isReal);
                }
            }
        }
    }

    public Vertex splitEdge(Edge edge, double x, double y) {

        this.edgesToCheck.splice(0, this.edgesToCheck.length);

        Edge eLeft_Right = edge;
        Edge eRight_Left = eLeft_Right.getOppositeEdge();
        Edge eRight_Top = eLeft_Right.getNextLeftEdge();
        Edge eTop_Left = eRight_Top.getNextLeftEdge();
        Edge eLeft_Bot = eRight_Left.getNextLeftEdge();
        Edge eBot_Right = eLeft_Bot.getNextLeftEdge();
        Vertex vTop = eTop_Left.getOriginVertex();
        Vertex vLeft = eLeft_Right.getOriginVertex();
        Vertex vBot = eBot_Right.getOriginVertex();
        Vertex vRight = eRight_Left.getOriginVertex();
        Face fTop = eLeft_Right.getLeftFace();
        Face fBot = eRight_Left.getLeftFace();
        if ((((((vLeft.getPos().x - x)) * ((vLeft.getPos().x - x))) + (((vLeft.getPos().y - y)) * ((vLeft.getPos().y - y)))) <= 0.0001)) {
            return vLeft;
        }
        if ((((((vRight.getPos().x - x)) * ((vRight.getPos().x - x))) + (((vRight.getPos().y - y)) * ((vRight.getPos().y - y)))) <= 0.0001)) {
            return vRight;
        }
        Vertex vCenter = new Vertex();
        Edge eTop_Center = new Edge();
        Edge eCenter_Top = new Edge();
        Edge eBot_Center = new Edge();
        Edge eCenter_Bot = new Edge();
        Edge eLeft_Center = new Edge();
        Edge eCenter_Left = new Edge();
        Edge eRight_Center = new Edge();
        Edge eCenter_Right = new Edge();
        Face fTopLeft = new Face();
        Face fBotLeft = new Face();
        Face fBotRight = new Face();
        Face fTopRight = new Face();
        this.vertices.push(vCenter);
        this.edges.push(eCenter_Top);
        this.edges.push(eTop_Center);
        this.edges.push(eCenter_Left);
        this.edges.push(eLeft_Center);
        this.edges.push(eCenter_Bot);
        this.edges.push(eBot_Center);
        this.edges.push(eCenter_Right);
        this.edges.push(eRight_Center);
        this.faces.push(fTopRight);
        this.faces.push(fBotRight);
        this.faces.push(fBotLeft);
        this.faces.push(fTopLeft);
        vCenter.setDatas(((fTop.isReal()) ? (eCenter_Top) : (eCenter_Bot)), false);
        vCenter.getPos().x = x;
        vCenter.getPos().y = y;
        Geom2D.projectOrthogonaly(vCenter.getPos(), eLeft_Right);
        eCenter_Top.setDatas(vCenter, eTop_Center, eTop_Left, fTopLeft, fTop.isReal(), false);
        eTop_Center.setDatas(vTop, eCenter_Top, eCenter_Right, fTopRight, fTop.isReal(), false);
        eCenter_Left.setDatas(vCenter, eLeft_Center, eLeft_Bot, fBotLeft, edge.isReal(), edge.isConstrained());
        eLeft_Center.setDatas(vLeft, eCenter_Left, eCenter_Top, fTopLeft, edge.isReal(), edge.isConstrained());
        eCenter_Bot.setDatas(vCenter, eBot_Center, eBot_Right, fBotRight, fBot.isReal(), false);
        eBot_Center.setDatas(vBot, eCenter_Bot, eCenter_Left, fBotLeft, fBot.isReal(), false);
        eCenter_Right.setDatas(vCenter, eRight_Center, eRight_Top, fTopRight, edge.isReal(), edge.isConstrained());
        eRight_Center.setDatas(vRight, eCenter_Right, eCenter_Bot, fBotRight, edge.isReal(), edge.isConstrained());
        fTopLeft.setDatas(eCenter_Top, fTop.isReal());
        fBotLeft.setDatas(eCenter_Left, fBot.isReal());
        fBotRight.setDatas(eCenter_Bot, fBot.isReal());
        fTopRight.setDatas(eCenter_Right, fTop.isReal());
        if ((vLeft.getEdge() == eLeft_Right)) {
            vLeft.setDatas(eLeft_Center, true);
        }
        if ((vRight.getEdge() == eRight_Left)) {
            vRight.setDatas(eRight_Center, true);
        }
        eTop_Left.setNextLeftEdge(eLeft_Center);
        eTop_Left.setLeftFace(fTopLeft);
        eLeft_Bot.setNextLeftEdge(eBot_Center);
        eLeft_Bot.setLeftFace(fBotLeft);
        eBot_Right.setNextLeftEdge(eRight_Center);
        eBot_Right.setLeftFace(fBotRight);
        eRight_Top.setNextLeftEdge(eTop_Center);
        eRight_Top.setLeftFace(fTopRight);
        if (eLeft_Right.isConstrained()) {
            Vector<ConstraintSegment> fromSegments = eLeft_Right.fromConstraintSegments;
            eLeft_Center.fromConstraintSegments = fromSegments.slice(0, null);
            eCenter_Left.fromConstraintSegments = eLeft_Center.fromConstraintSegments;
            eCenter_Right.fromConstraintSegments = fromSegments.slice(0, null);
            eRight_Center.fromConstraintSegments = eCenter_Right.fromConstraintSegments;
            Vector<Edge> edges = null;
            int index = 0;
            {
                int _g1 = 0;
                int _g = eLeft_Right.fromConstraintSegments.length;
                while ((_g1 < _g)) {
                    int i = _g1++;
                    edges = eLeft_Right.fromConstraintSegments.get(i).getEdges();
                    index = edges.indexOf(eLeft_Right, null);
                    if ((index != -1)) {
                        edges.splice(index, 1);
                        edges.insert(index, eLeft_Center);
                        edges.insert((index + 1), eCenter_Right);
                    } else {
                        index = edges.indexOf(eRight_Left, null);
                        edges.splice(index, 1);
                        edges.insert(index, eRight_Center);
                        edges.insert((index + 1), eCenter_Left);
                    }
                }
            }
            vCenter.setFromConstraintSegments(fromSegments.slice(0, null));
        }

        eLeft_Right.dispose();
        eRight_Left.dispose();
        this.edges.splice(this.edges.indexOf(eLeft_Right, null), 1);
        this.edges.splice(this.edges.indexOf(eRight_Left, null), 1);
        fTop.dispose();
        fBot.dispose();
        this.faces.splice(this.faces.indexOf(fTop, null), 1);
        this.faces.splice(this.faces.indexOf(fBot, null), 1);
        this.centerVertex = vCenter;
        this.edgesToCheck.push(eTop_Left);
        this.edgesToCheck.push(eLeft_Bot);
        this.edgesToCheck.push(eBot_Right);
        this.edgesToCheck.push(eRight_Top);
        return vCenter;
    }


    public Vertex splitFace(Face face, double x, double y) {
        this.edgesToCheck.splice(0, this.edgesToCheck.length);
        Edge eTop_Left = face.getEdge();
        Edge eLeft_Right = eTop_Left.getNextLeftEdge();
        Edge eRight_Top = eLeft_Right.getNextLeftEdge();
        Vertex vTop = eTop_Left.getOriginVertex();
        Vertex vLeft = eLeft_Right.getOriginVertex();
        Vertex vRight = eRight_Top.getOriginVertex();
        Vertex vCenter = new Vertex();
        Edge eTop_Center = new Edge();
        Edge eCenter_Top = new Edge();
        Edge eLeft_Center = new Edge();
        Edge eCenter_Left = new Edge();
        Edge eRight_Center = new Edge();
        Edge eCenter_Right = new Edge();
        Face fTopLeft = new Face();
        Face fBot = new Face();
        Face fTopRight = new Face();
        this.vertices.push(vCenter);
        this.edges.push(eTop_Center);
        this.edges.push(eCenter_Top);
        this.edges.push(eLeft_Center);
        this.edges.push(eCenter_Left);
        this.edges.push(eRight_Center);
        this.edges.push(eCenter_Right);
        this.faces.push(fTopLeft);
        this.faces.push(fBot);
        this.faces.push(fTopRight);
        vCenter.setDatas(eCenter_Top, true);
        vCenter.getPos().x = x;
        vCenter.getPos().y = y;
        eTop_Center.setDatas(vTop, eCenter_Top, eCenter_Right, fTopRight, true, false);
        eCenter_Top.setDatas(vCenter, eTop_Center, eTop_Left, fTopLeft, true, false);
        eLeft_Center.setDatas(vLeft, eCenter_Left, eCenter_Top, fTopLeft, true, false);
        eCenter_Left.setDatas(vCenter, eLeft_Center, eLeft_Right, fBot, true, false);
        eRight_Center.setDatas(vRight, eCenter_Right, eCenter_Left, fBot, true, false);
        eCenter_Right.setDatas(vCenter, eRight_Center, eRight_Top, fTopRight, true, false);
        fTopLeft.setDatas(eCenter_Top, true);
        fBot.setDatas(eCenter_Left, true);
        fTopRight.setDatas(eCenter_Right, true);
        eTop_Left.setNextLeftEdge(eLeft_Center);
        eTop_Left.setLeftFace(fTopLeft);
        eLeft_Right.setNextLeftEdge(eRight_Center);
        eLeft_Right.setLeftFace(fBot);
        eRight_Top.setNextLeftEdge(eTop_Center);
        eRight_Top.setLeftFace(fTopRight);
        face.dispose();
        this.faces.splice(this.faces.indexOf(face, null), 1);
        this.centerVertex = vCenter;
        this.edgesToCheck.push(eTop_Left);
        this.edgesToCheck.push(eLeft_Right);
        this.edgesToCheck.push(eRight_Top);
        return vCenter;
    }


    public Vertex insertVertex(double x, double y) {
        if (((((x < 0) || (y < 0)) || (x > this.width)) || (y > this.height))) {
            return null;
        }

        this.edgesToCheck.splice(0, this.edgesToCheck.length);
        Intersection inObject = Geom2D.locatePosition(x, y, this);
        Vertex newVertex = null;
        switch (inObject.index) {
            case 0: {
                Vertex vertex = ((Vertex) (inObject.params[0]));
                newVertex = vertex;
                break;
            }


            case 1: {
                Edge edge = ((Edge) (inObject.params[0]));
                newVertex = this.splitEdge(edge, x, y);
                break;
            }


            case 2: {
                Face face = ((Face) (inObject.params[0]));
                newVertex = this.splitFace(face, x, y);
                break;
            }


            case 3: {
                break;
            }


        }

        this.restoreAsDelaunay();
        return newVertex;
    }

    public void restoreAsDelaunay() {
        Edge edge = null;
        while ((this.edgesToCheck.length > 0)) {
            edge = ((Edge) (this.edgesToCheck.shift()));
            if (((edge.isReal() && !(edge.isConstrained())) && !(Geom2D.isDelaunay(edge)))) {
                if ((edge.getNextLeftEdge().getDestinationVertex().getId() == this.centerVertex.getId())) {
                    this.edgesToCheck.push(edge.getNextRightEdge());
                    this.edgesToCheck.push(edge.getPrevRightEdge());
                } else {
                    this.edgesToCheck.push(edge.getNextLeftEdge());
                    this.edgesToCheck.push(edge.getPrevLeftEdge());
                }
                this.flipEdge(edge);
            }
        }
    }

    public Edge flipEdge(Edge edge) {
        Edge eBot_Top = edge;
        Edge eTop_Bot = edge.getOppositeEdge();
        Edge eLeft_Right = new Edge();
        Edge eRight_Left = new Edge();
        Edge eTop_Left = eBot_Top.getNextLeftEdge();
        Edge eLeft_Bot = eTop_Left.getNextLeftEdge();
        Edge eBot_Right = eTop_Bot.getNextLeftEdge();
        Edge eRight_Top = eBot_Right.getNextLeftEdge();
        Vertex vBot = eBot_Top.getOriginVertex();
        Vertex vTop = eTop_Bot.getOriginVertex();
        Vertex vLeft = eLeft_Bot.getOriginVertex();
        Vertex vRight = eRight_Top.getOriginVertex();
        Face fLeft = eBot_Top.getLeftFace();
        Face fRight = eTop_Bot.getLeftFace();
        Face fBot = new Face();
        Face fTop = new Face();
        this.edges.push(eLeft_Right);
        this.edges.push(eRight_Left);
        this.faces.push(fTop);
        this.faces.push(fBot);
        eLeft_Right.setDatas(vLeft, eRight_Left, eRight_Top, fTop, edge.isReal(), edge.isConstrained());
        eRight_Left.setDatas(vRight, eLeft_Right, eLeft_Bot, fBot, edge.isReal(), edge.isConstrained());
        fTop.setDatas(eLeft_Right, true);
        fBot.setDatas(eRight_Left, true);
        if ((vTop.getEdge().getId() == eTop_Bot.getId())) //todo equals impl
        {
            vTop.setDatas(eTop_Left, true);
        }
        if ((vBot.getEdge().getId() == eBot_Top.getId())) {
            vBot.setDatas(eBot_Right, true);
        }
        eTop_Left.setNextLeftEdge(eLeft_Right);
        eTop_Left.setLeftFace(fTop);
        eLeft_Bot.setNextLeftEdge(eBot_Right);
        eLeft_Bot.setLeftFace(fBot);
        eBot_Right.setNextLeftEdge(eRight_Left);
        eBot_Right.setLeftFace(fBot);
        eRight_Top.setNextLeftEdge(eTop_Left);
        eRight_Top.setLeftFace(fTop);
        eBot_Top.dispose();
        eTop_Bot.dispose();
        this.edges.splice(this.edges.indexOf(eBot_Top, null), 1);
        this.edges.splice(this.edges.indexOf(eTop_Bot, null), 1);
        fLeft.dispose();
        fRight.dispose();
        this.faces.splice(this.faces.indexOf(fLeft, null), 1);
        this.faces.splice(this.faces.indexOf(fRight, null), 1);
        return eRight_Left;
    }


    public int findPositionFromBounds(double x, double y) {
        if ((x <= 0)) {
            if ((y <= 0)) {
                return 1;
            } else {
                if ((y >= this.height)) {
                    return 7;
                } else {
                    return 8;
                }
            }
        } else {
            if ((x >= this.width)) {
                if ((y <= 0)) {
                    return 3;
                } else {
                    if ((y >= this.height)) {
                        return 5;
                    } else {
                        return 4;
                    }
                }
            } else {
                if ((y <= 0)) {
                    return 2;
                } else {
                    if ((y >= this.height)) {
                        return 6;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    public ConstraintShape insertConstraintShape(Vector<Double> coordinates) {
        ConstraintShape shape = new ConstraintShape();
        ConstraintSegment segment = null;
        int i = 0;
        while ((i < coordinates.length)) {
            segment = this.insertConstraintSegment(coordinates.get(i), coordinates.get(i + 1), coordinates.get(i + 2), coordinates.get(i + 3));
            if ((segment != null)) {
                segment.fromShape = shape;
                shape.segments.push(segment);
            }

            i += 4;
        }

        this.constraintShapes.push(shape);
        return shape;
    }


    public void insertObject(Obstacle object) {
        if ((object.getConstraintShape() != null)) {
            this.deleteObject(object);
        }

        ConstraintShape shape = new ConstraintShape();
        ConstraintSegment segment = null;
        Vector<Double> coordinates = object.getCoordinates();
        Matrix2D m = object.getMatrix();
        object.updateMatrixFromValues();
        double x1 = 0.0;
        double y1 = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;
        double transfx1 = 0.0;
        double transfy1 = 0.0;
        double transfx2 = 0.0;
        double transfy2 = 0.0;
        int i = 0;
        while ((i < coordinates.length)) {
            x1 = coordinates.get(i);
            y1 = coordinates.get(i + 1);
            x2 = coordinates.get(i + 2);
            y2 = coordinates.get(i + 3);
            transfx1 = m.transformX(x1, y1);
            transfy1 = m.transformY(x1, y1);
            transfx2 = m.transformX(x2, y2);
            transfy2 = m.transformY(x2, y2);
            segment = this.insertConstraintSegment(transfx1, transfy1, transfx2, transfy2);
            if ((segment != null)) {
                segment.fromShape = shape;
                shape.segments.push(segment);
            }
            i += 4;
        }
        this.constraintShapes.push(shape);
        object.setConstraintShape(shape);
        if (!(this.objectsUpdateInProgress)) {
            this.objects.push(object);
        }
    }


    public void deleteObject(Obstacle object) {
        if ((object.getConstraintShape() == null)) {
            return;
        }

        this.deleteConstraintShape(object.getConstraintShape());
        object.setConstraintShape(null);
        if (!(this.objectsUpdateInProgress)) {
            int index = this.objects.indexOf(object, null);
            this.objects.splice(index, 1);
        }

    }

    public void deleteConstraintShape(ConstraintShape shape) {
        {
            int _g1 = 0;
            int _g = shape.segments.length;
            while ((_g1 < _g)) {
                int i = _g1++;
                this.deleteConstraintSegment(shape.segments.get(i));
            }

        }

        shape.dispose();
        this.constraintShapes.splice(this.constraintShapes.indexOf(shape, null), 1);
    }

    public void deleteConstraintSegment(ConstraintSegment segment) {
        int i = 0;
        Vector<Vertex> vertexToDelete = new Vector<>();
        Edge edge = null;
        Vertex vertex = null;
        Vector<ConstraintSegment> fromConstraintSegment = null;
        {
            int _g1 = 0;
            int _g = segment.getEdges().length;
            while ((_g1 < _g)) {
                int i1 = _g1++;
                edge = segment.getEdges().get(i1);
                edge.removeFromConstraintSegment(segment);
                if ((edge.fromConstraintSegments.length == 0)) {
                    edge.setConstrained(false);
                    edge.getOppositeEdge().setConstrained(false);
                }

                vertex = edge.getOriginVertex();
                vertex.removeFromConstraintSegment(segment);
                vertexToDelete.push(vertex);
            }

        }

        vertex = edge.getDestinationVertex();
        vertex.removeFromConstraintSegment(segment);
        vertexToDelete.push(vertex);
        {
            int _g11 = 0;
            int _g2 = vertexToDelete.length;
            while ((_g11 < _g2)) {
                int i2 = _g11++;
                this.deleteVertex(vertexToDelete.get(i2));
            }

        }
        segment.dispose();
    }

    public boolean deleteVertex(Vertex vertex) {
        int i = 0;
        boolean freeOfConstraint = false;
        FromVertexToOutgoingEdges iterEdges = new FromVertexToOutgoingEdges();
        iterEdges.set_fromVertex(vertex);
        iterEdges.realEdgesOnly = false;
        Edge edge = null;
        Vector<Edge> outgoingEdges = new Vector<>();
        freeOfConstraint = (vertex.getFromConstraintSegments().length == 0);
        Vector<Edge> bound = new Vector<>();
        boolean realA = false;
        boolean realB = false;
        Vector<Edge> boundA = new Vector<Edge>(new Edge[]{});
        Vector<Edge> boundB = new Vector<Edge>(new Edge[]{});
        if (freeOfConstraint) {
            while (true) {
                edge = iterEdges.next();
                if (edge == null) {
                    break;
                }

                outgoingEdges.push(edge);
                bound.push(edge.getNextLeftEdge());
            }

        } else {
            Vector<Edge> edges = null;
            {
                int _g1 = 0;
                int _g = vertex.getFromConstraintSegments().length;
                while ((_g1 < _g)) {
                    int i1 = _g1++;
                    edges = vertex.getFromConstraintSegments().get(i1).getEdges();
                    if (((edges.get(0).getOriginVertex() == vertex) || (edges.get((edges.length - 1)).getDestinationVertex().getId() == vertex.getId()))) {
                        return false;
                    }
                }
            }
            int count = 0;
            while (true) {
                edge = iterEdges.next();
                if (edge == null) {
                    break;
                }
                outgoingEdges.push(edge);
                if (edge.isConstrained()) {
                    ++count;
                    if ((count > 2)) {
                        return false;
                    }
                }
            }
            boundA = new Vector<>();
            boundB = new Vector<>();
            Edge constrainedEdgeA = null;
            Edge constrainedEdgeB = null;
            Edge edgeA = new Edge();
            Edge edgeB = new Edge();
            this.edges.push(edgeA);
            this.edges.push(edgeB);
            {
                int _g11 = 0;
                int _g2 = outgoingEdges.length;
                while ((_g11 < _g2)) {
                    int i2 = _g11++;
                    edge = outgoingEdges.get(i2);
                    if (edge.isConstrained()) {
                        if ((constrainedEdgeA == null)) {
                            edgeB.setDatas(edge.getDestinationVertex(), edgeA, null, null, true, true);
                            boundA.push(edgeA);
                            boundA.push(edge.getNextLeftEdge());
                            boundB.push(edgeB);
                            constrainedEdgeA = edge;
                        } else {
                            if ((constrainedEdgeB == null)) {
                                edgeA.setDatas(edge.getDestinationVertex(), edgeB, null, null, true, true);
                                boundB.push(edge.getNextLeftEdge());
                                constrainedEdgeB = edge;
                            }

                        }

                    } else {
                        if ((constrainedEdgeA == null)) {
                            boundB.push(edge.getNextLeftEdge());
                        } else {
                            if ((constrainedEdgeB == null)) {
                                boundA.push(edge.getNextLeftEdge());
                            } else {
                                boundB.push(edge.getNextLeftEdge());
                            }

                        }

                    }

                }

            }

            assert constrainedEdgeA != null;
            realA = constrainedEdgeA.getLeftFace().isReal();
            assert constrainedEdgeB != null;
            realB = constrainedEdgeB.getLeftFace().isReal();
            edgeA.fromConstraintSegments = constrainedEdgeA.fromConstraintSegments.slice(0, null);
            edgeB.fromConstraintSegments = edgeA.fromConstraintSegments;
            int index = 0;
            {
                int _g12 = 0;
                int _g3 = vertex.getFromConstraintSegments().length;
                while ((_g12 < _g3)) {
                    int i3 = _g12++;
                    edges = vertex.getFromConstraintSegments().get(i3).getEdges();
                    index = edges.indexOf(constrainedEdgeA, null);
                    if ((index != -1)) {
                        edges.splice((index - 1), 2);
                        edges.insert((index - 1), edgeA);
                    } else {
                        int index2 = (edges.indexOf(constrainedEdgeB, null) - 1);
                        edges.splice(index2, 2);
                        edges.insert(index2, edgeB);
                    }

                }

            }

        }

        Face faceToDelete = null;
        {
            int _g13 = 0;
            int _g4 = outgoingEdges.length;
            while ((_g13 < _g4)) {
                int i4 = _g13++;
                edge = outgoingEdges.get(i4);
                faceToDelete = edge.getLeftFace();
                this.faces.splice(this.faces.indexOf(faceToDelete, null), 1);
                faceToDelete.dispose();
                edge.getDestinationVertex().setEdge(edge.getNextLeftEdge());
                this.edges.splice(this.edges.indexOf(edge.getOppositeEdge(), null), 1);
                edge.getOppositeEdge().dispose();
                this.edges.splice(this.edges.indexOf(edge, null), 1);
                edge.dispose();
            }

        }

        this.vertices.splice(this.vertices.indexOf(vertex, null), 1);
        vertex.dispose();
        if (freeOfConstraint) {
            this.triangulate(bound, true);
        } else {
            this.triangulate(boundA, realA);
            this.triangulate(boundB, realB);
        }
        return true;
    }


    public void updateObjects() {
        this.objectsUpdateInProgress = true;
        {
            for (int i = 0; i < this.objects.length; i++) {
                if (this.objects.get(i).isHasChanged()) {
                    this.deleteObject(this.objects.get(i));
                    this.insertObject(this.objects.get(i));
                    this.objects.get(i).setHasChanged(false);
                }
            }
        }
        this.objectsUpdateInProgress = false;
    }


}
