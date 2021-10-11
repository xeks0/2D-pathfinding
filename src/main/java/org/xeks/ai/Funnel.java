package org.xeks.ai;

import lombok.Getter;
import org.xeks.Vector;
import org.xeks.data.Edge;
import org.xeks.data.Face;
import org.xeks.data.Vertex;
import org.xeks.data.math.Geom2D;
import org.xeks.data.math.Intersection;
import org.xeks.data.math.Point2D;

import java.util.HashMap;

public class Funnel {


    public Funnel() {
        staticConstructor(this);
    }

    public static void staticConstructor(Funnel _this) {

        _this.currPoolPointsIndex = 0;
        _this.poolPointsSize = 3000;
        _this.numSamplesCircle = 16;
        _this.radiusSquared = ((int) (0.0));
        _this.radius = ((int) (0.0));
        _this.poolPoints = new Vector<>();
        {
            int _g1 = 0;
            int _g = _this.poolPointsSize;
            while ((_g1 < _g)) {
                int i = _g1++;
                _this.poolPoints.push(new Point2D());
            }

        }
    }

    @Getter
    public double radius;

    public double radiusSquared;

    public int numSamplesCircle;

    public Vector<Point2D> sampleCircle;

    public double sampleCircleDistanceSquared;

    public int poolPointsSize;

    public Vector<Point2D> poolPoints;

    public int currPoolPointsIndex;

    public Point2D point;

    public void dispose() {
        this.sampleCircle = null;
    }

    public Point2D getPoint(double x, double y) {
        this.point = this.poolPoints.get(this.currPoolPointsIndex);
        this.point.setXY(x, y);
        this.currPoolPointsIndex++;
        if ((this.currPoolPointsIndex == this.poolPointsSize)) {
            this.poolPoints.push(new Point2D());
            this.poolPointsSize++;
        }
        return this.point;
    }


    public double setRadius(double value) {
        this.radius = java.lang.Math.max(((double) (0)), value);
        this.radiusSquared = (this.radius * this.radius);
        this.sampleCircle = new Vector<>();
        if ((this.getRadius() == 0)) {
            return 0;
        }
        {
            int _g1 = 0;
            int _g = this.numSamplesCircle;
            while ((_g1 < _g)) {
                int i = _g1++;
                this.sampleCircle.push(new Point2D((this.radius * java.lang.Math.cos((((-2 * Math.PI) * i) / this.numSamplesCircle))), (this.radius * java.lang.Math.sin((((-2 * Math.PI) * i) / this.numSamplesCircle)))));
            }
        }
        this.sampleCircleDistanceSquared = ((((this.sampleCircle.get(0).x - this.sampleCircle.get(1).x)) * ((this.sampleCircle.get(0).x - this.sampleCircle.get(1).x))) + (((this.sampleCircle.get(0).y - this.sampleCircle.get(1).y)) * ((this.sampleCircle.get(0).y - this.sampleCircle.get(1).y))));
        return this.radius;
    }


    public void findPath(double fromX, double fromY, double toX, double toY, Vector<Face> listFaces, Vector<Edge> listEdges, Vector<Double> resultPath) {
        this.currPoolPointsIndex = 0;
        if ((this.radius > 0)) {
            Face checkFace = listFaces.get(0);
            double distanceSquared = 0.0;
            double distance = 0.0;
            Point2D p1 = null;
            Point2D p2 = null;
            Point2D p3 = null;
            p1 = checkFace.getEdge().getOriginVertex().getPos();
            p2 = checkFace.getEdge().getDestinationVertex().getPos();
            p3 = checkFace.getEdge().getNextLeftEdge().getDestinationVertex().getPos();
            distanceSquared = ((((p1.x - fromX)) * ((p1.x - fromX))) + (((p1.y - fromY)) * ((p1.y - fromY))));
            if ((distanceSquared <= this.radiusSquared)) {
                distance = java.lang.Math.sqrt(distanceSquared);
                fromX = (((this.radius * 1.01) * ((((fromX - p1.x)) / distance))) + p1.x);
                fromY = (((this.radius * 1.01) * ((((fromY - p1.y)) / distance))) + p1.y);
            } else {
                distanceSquared = ((((p2.x - fromX)) * ((p2.x - fromX))) + (((p2.y - fromY)) * ((p2.y - fromY))));
                if ((distanceSquared <= this.radiusSquared)) {
                    distance = java.lang.Math.sqrt(distanceSquared);
                    fromX = (((this.radius * 1.01) * ((((fromX - p2.x)) / distance))) + p2.x);
                    fromY = (((this.radius * 1.01) * ((((fromY - p2.y)) / distance))) + p2.y);
                } else {
                    distanceSquared = ((((p3.x - fromX)) * ((p3.x - fromX))) + (((p3.y - fromY)) * ((p3.y - fromY))));
                    if ((distanceSquared <= this.radiusSquared)) {
                        distance = java.lang.Math.sqrt(distanceSquared);
                        fromX = (((this.radius * 1.01) * ((((fromX - p3.x)) / distance))) + p3.x);
                        fromY = (((this.radius * 1.01) * ((((fromY - p3.y)) / distance))) + p3.y);
                    }

                }

            }

            checkFace = listFaces.get((listFaces.length - 1));
            p1 = checkFace.getEdge().getOriginVertex().getPos();
            p2 = checkFace.getEdge().getDestinationVertex().getPos();
            p3 = checkFace.getEdge().getNextLeftEdge().getDestinationVertex().getPos();
            distanceSquared = ((((p1.x - toX)) * ((p1.x - toX))) + (((p1.y - toY)) * ((p1.y - toY))));
            if ((distanceSquared <= this.radiusSquared)) {
                distance = java.lang.Math.sqrt(distanceSquared);
                toX = (((this.radius * 1.01) * ((((toX - p1.x)) / distance))) + p1.x);
                toY = (((this.radius * 1.01) * ((((toY - p1.y)) / distance))) + p1.y);
            } else {
                distanceSquared = ((((p2.x - toX)) * ((p2.x - toX))) + (((p2.y - toY)) * ((p2.y - toY))));
                if ((distanceSquared <= this.radiusSquared)) {
                    distance = java.lang.Math.sqrt(distanceSquared);
                    toX = (((this.radius * 1.01) * ((((toX - p2.x)) / distance))) + p2.x);
                    toY = (((this.radius * 1.01) * ((((toY - p2.y)) / distance))) + p2.y);
                } else {
                    distanceSquared = ((((p3.x - toX)) * ((p3.x - toX))) + (((p3.y - toY)) * ((p3.y - toY))));
                    if ((distanceSquared <= this.radiusSquared)) {
                        distance = java.lang.Math.sqrt(distanceSquared);
                        toX = (((this.radius * 1.01) * ((((toX - p3.x)) / distance))) + p3.x);
                        toY = (((this.radius * 1.01) * ((((toY - p3.y)) / distance))) + p3.y);
                    }

                }

            }

        }

        Point2D startPoint = null;
        Point2D endPoint = null;
        startPoint = new Point2D(fromX, fromY);
        endPoint = new Point2D(toX, toY);
        if ((listFaces.length == 1)) {
            resultPath.push(startPoint.x);
            resultPath.push(startPoint.y);
            resultPath.push(endPoint.x);
            resultPath.push(endPoint.y);
            return;
        }

        int i = 0;
        int j = 0;
        int k = 0;
        Edge currEdge = null;
        Vertex currVertex = null;
        int direction = 0;
        {
            Intersection _g = Geom2D.isInFace(fromX, fromY, listFaces.get(0));
            switch (_g.index) {
                case 1: {
                    Edge edge = ((Edge) (_g.params[0]));
                    if ((listEdges.get(0) == edge)) {
                        listEdges.shift();
                        listFaces.shift();
                    }
                    break;
                }


                default: {
                    break;
                }

            }

        }

        Vector<Point2D> funnelLeft = new Vector<>();
        Vector<Point2D> funnelRight = new Vector<>();
        funnelLeft.push(startPoint);
        funnelRight.push(startPoint);
        HashMap<Vertex, Integer> verticesDoneSide = new HashMap<>();
        Vector<Point2D> pointsList = new Vector<Point2D>();
        HashMap<Point2D, Integer> pointSides = new HashMap<>();
        HashMap<Point2D, Point2D> pointSuccessor = new HashMap<>();
        pointSides.put(startPoint, 0);
        currEdge = listEdges.get(0);
        int relativPos = Geom2D.getRelativePosition2(fromX, fromY, currEdge);
        Point2D prevPoint = null;
        Point2D newPointA = null;
        Point2D newPointB = null;
        newPointA = this.getCopyPoint(currEdge.getDestinationVertex().getPos());
        newPointB = this.getCopyPoint(currEdge.getOriginVertex().getPos());
        pointsList.push(newPointA);
        pointsList.push(newPointB);
        pointSuccessor.put(startPoint, newPointA);
        pointSuccessor.put(newPointA, newPointB);
        prevPoint = newPointB;
        if ((relativPos == 1)) {
            pointSides.put(newPointA, 1);
            pointSides.put(newPointB, -1);
            verticesDoneSide.put(currEdge.getDestinationVertex(), 1);
            verticesDoneSide.put(currEdge.getOriginVertex(), -1);
        } else {
            if ((relativPos == -1)) {
                pointSides.put(newPointA, -1);
                pointSides.put(newPointB, 1);
                verticesDoneSide.put(currEdge.getDestinationVertex(), -1);
                verticesDoneSide.put(currEdge.getOriginVertex(), 1);
            }

        }

        Vertex fromVertex = listEdges.get(0).getOriginVertex();
        Vertex fromFromVertex = listEdges.get(0).getDestinationVertex();
        {
            int _g2 = 1;
            int _g1 = listEdges.length;
            while ((_g2 < _g1)) {
                int i1 = _g2++;
                currEdge = listEdges.get(i1);
                if ((currEdge.getOriginVertex() == fromVertex)) {
                    currVertex = currEdge.getDestinationVertex();
                } else {
                    if ((currEdge.getDestinationVertex() == fromVertex)) {
                        currVertex = currEdge.getOriginVertex();
                    } else {
                        if ((currEdge.getOriginVertex() == fromFromVertex)) {
                            currVertex = currEdge.getDestinationVertex();
                            fromVertex = fromFromVertex;
                        } else {
                            if ((currEdge.getDestinationVertex() == fromFromVertex)) {
                                currVertex = currEdge.getOriginVertex();
                                fromVertex = fromFromVertex;
                            }

                        }

                    }

                }

                newPointA = this.getCopyPoint(currVertex.getPos());
                pointsList.push(newPointA);
                direction = (-((verticesDoneSide.get(fromVertex))));
                pointSides.put(newPointA, direction);
                pointSuccessor.put(prevPoint, newPointA);
                verticesDoneSide.put(currVertex, direction);
                prevPoint = newPointA;
                fromFromVertex = fromVertex;
                fromVertex = currVertex;
            }

        }

        pointSuccessor.put(prevPoint, endPoint);
        pointSides.put(endPoint, 0);
        Vector<Point2D> pathPoints = new Vector<>();
        HashMap<Point2D, Integer> pathSides = new HashMap<>();
        pathPoints.push(startPoint);
        pathSides.put(startPoint, 0);
        Point2D currPos = null;
        {
            int _g21 = 0;
            int _g11 = pointsList.length;
            while ((_g21 < _g11)) {
                int i2 = _g21++;
                currPos = pointsList.get(i2);
                if (pointSides.get(currPos) == -1) {
                    j = (funnelLeft.length - 2);
                    while ((j >= 0)) {
                        direction = Geom2D.getDirection(funnelLeft.get(j).x, funnelLeft.get(j).y, funnelLeft.get((j + 1)).x, funnelLeft.get((j + 1)).y, currPos.x, currPos.y);
                        if ((direction != -1)) {
                            funnelLeft.shift();
                            {
                                int _g4 = 0;
                                int _g3 = j;
                                while ((_g4 < _g3)) {
                                    int k1 = _g4++;
                                    pathPoints.push(funnelLeft.get(0));
                                    pathSides.put(funnelLeft.get(0), 1);
                                    funnelLeft.shift();
                                }

                            }

                            pathPoints.push(funnelLeft.get(0));
                            pathSides.put(funnelLeft.get(0), 1);
                            funnelRight.splice(0, funnelRight.length);
                            funnelRight.push(funnelLeft.get(0));
                            funnelRight.push(currPos);
                            break;
                        }

                        --j;
                    }

                    funnelRight.push(currPos);
                    j = (funnelRight.length - 3);
                    while ((j >= 0)) {
                        direction = Geom2D.getDirection(funnelRight.get(j).x, funnelRight.get(j).y, funnelRight.get((j + 1)).x, funnelRight.get((j + 1)).y, currPos.x, currPos.y);
                        if ((direction == -1)) {
                            break;
                        } else {
                            funnelRight.splice((j + 1), 1);
                        }

                        --j;
                    }

                } else {
                    j = (funnelRight.length - 2);
                    while ((j >= 0)) {
                        direction = Geom2D.getDirection(funnelRight.get(j).x, funnelRight.get(j).y, funnelRight.get((j + 1)).x, funnelRight.get((j + 1)).y, currPos.x, currPos.y);
                        if ((direction != 1)) {
                            funnelRight.shift();
                            {
                                int _g41 = 0;
                                int _g31 = j;
                                while ((_g41 < _g31)) {
                                    int k2 = _g41++;
                                    pathPoints.push(funnelRight.get(0));
                                    pathSides.put(funnelRight.get(0), -1);
                                    funnelRight.shift();
                                }

                            }

                            pathPoints.push(funnelRight.get(0));
                            pathSides.put(funnelRight.get(0), -1);
                            funnelLeft.splice(0, funnelLeft.length);
                            funnelLeft.push(funnelRight.get(0));
                            funnelLeft.push(currPos);
                            break;
                        }

                        --j;
                    }

                    funnelLeft.push(currPos);
                    j = (funnelLeft.length - 3);
                    while ((j >= 0)) {
                        direction = Geom2D.getDirection(funnelLeft.get(j).x, funnelLeft.get(j).y, funnelLeft.get((j + 1)).x, funnelLeft.get((j + 1)).y, currPos.x, currPos.y);
                        if ((direction == 1)) {
                            break;
                        } else {
                            funnelLeft.splice((j + 1), 1);
                        }

                        --j;
                    }

                }

            }

        }

        boolean blocked = false;
        j = (funnelRight.length - 2);
        while ((j >= 0)) {
            direction = Geom2D.getDirection(funnelRight.get(j).x, funnelRight.get(j).y, funnelRight.get((j + 1)).x, funnelRight.get((j + 1)).y, toX, toY);
            if ((direction != 1)) {
                funnelRight.shift();
                {
                    int _g22 = 0;
                    int _g12 = (j + 1);
                    while ((_g22 < _g12)) {
                        int k3 = _g22++;
                        pathPoints.push(funnelRight.get(0));
                        pathSides.put(funnelRight.get(0), -1);
                        funnelRight.shift();
                    }

                }

                pathPoints.push(endPoint);
                pathSides.put(endPoint, 0);
                blocked = true;
                break;
            }

            --j;
        }

        if (!(blocked)) {
            j = (funnelLeft.length - 2);
            while ((j >= 0)) {
                direction = Geom2D.getDirection(funnelLeft.get(j).x, funnelLeft.get(j).y, funnelLeft.get((j + 1)).x, funnelLeft.get((j + 1)).y, toX, toY);
                if ((direction != -1)) {
                    funnelLeft.shift();
                    {
                        int _g23 = 0;
                        int _g13 = (j + 1);
                        while ((_g23 < _g13)) {
                            int k4 = _g23++;
                            pathPoints.push(funnelLeft.get(0));
                            pathSides.put(funnelLeft.get(0), 1);
                            funnelLeft.shift();
                        }

                    }

                    pathPoints.push(endPoint);
                    pathSides.put(endPoint, 0);
                    blocked = true;
                    break;
                }

                --j;
            }

        }

        if (!(blocked)) {
            pathPoints.push(endPoint);
            pathSides.put(endPoint, 0);
            blocked = true;
        }

        Vector<Point2D> adjustedPoints = new Vector<>();
        if ((this.getRadius() > 0)) {
            Vector<Point2D> newPath = new Vector<>();
            if ((pathPoints.length == 2)) {
                this.adjustWithTangents(pathPoints.get(0), false, pathPoints.get(1), false, pointSides, pointSuccessor, newPath, adjustedPoints);
            } else {
                if ((pathPoints.length > 2)) {
                    this.adjustWithTangents(pathPoints.get(0), false, pathPoints.get(1), true, pointSides, pointSuccessor, newPath, adjustedPoints);
                    if ((pathPoints.length > 3)) {
                        int _g24 = 1;
                        int _g14 = ((pathPoints.length - 3) + 1);
                        while ((_g24 < _g14)) {
                            int i3 = _g24++;
                            this.adjustWithTangents(pathPoints.get(i3), true, pathPoints.get((i3 + 1)), true, pointSides, pointSuccessor, newPath, adjustedPoints);
                        }

                    }

                    int pathLength = pathPoints.length;
                    this.adjustWithTangents(pathPoints.get((pathLength - 2)), true, pathPoints.get((pathLength - 1)), false, pointSides, pointSuccessor, newPath, adjustedPoints);
                }

            }

            newPath.push(endPoint);
            this.checkAdjustedPath(newPath, adjustedPoints, pointSides);
            Vector<Point2D> smoothPoints = new Vector<Point2D>();
            i = (newPath.length - 2);
            while ((i >= 1)) {
                this.smoothAngle(adjustedPoints.get(((i * 2) - 1)), newPath.get(i), adjustedPoints.get((i * 2)), pointSides.get(newPath.get(i)), smoothPoints);
                while ((smoothPoints.length != 0)) {
                    int temp = (i * 2);
                    adjustedPoints.splice(temp, 0);
                    adjustedPoints.insert(temp, ((Point2D) (smoothPoints.pop())));
                }

                --i;
            }

        } else {
            adjustedPoints = pathPoints;
        }

        {
            int _g25 = 0;
            int _g15 = adjustedPoints.length;
            while ((_g25 < _g15)) {
                int i4 = _g25++;
                resultPath.push(adjustedPoints.get(i4).x);
                resultPath.push(adjustedPoints.get(i4).y);
            }

        }

    }


    public Point2D getCopyPoint(Point2D pointToCopy) {
        return this.getPoint(pointToCopy.x, pointToCopy.y);
    }

    public void adjustWithTangents(Point2D p1, boolean applyRadiusToP1, Point2D p2, boolean applyRadiusToP2, HashMap<Point2D, Integer> pointSides, HashMap<Point2D, Point2D> pointSuccessor, Vector<Point2D> newPath, Vector<Point2D> adjustedPoints) {
        Vector<Double> tangentsResult = new Vector<>();
        int side1 = pointSides.get(p1);
        int side2 = pointSides.get(p2);
        Point2D pTangent1 = null;
        Point2D pTangent2 = null;
        if ((!(applyRadiusToP1) && !(applyRadiusToP2))) {
            pTangent1 = p1;
            pTangent2 = p2;
        } else {
            if (!(applyRadiusToP1)) {
                if (Geom2D.tangentsPointToCircle(p1.x, p1.y, p2.x, p2.y, this.radius, tangentsResult)) {
                    if ((side2 == 1)) {
                        pTangent1 = p1;
                        pTangent2 = this.getPoint(tangentsResult.get(2), tangentsResult.get(3));
                    } else {
                        pTangent1 = p1;
                        pTangent2 = this.getPoint(tangentsResult.get(0), tangentsResult.get(1));
                    }

                } else {
                    return;
                }

            } else {
                if (!(applyRadiusToP2)) {
                    if (Geom2D.tangentsPointToCircle(p2.x, p2.y, p1.x, p1.y, this.radius, tangentsResult)) {
                        if ((tangentsResult.length > 0)) {
                            if ((side1 == 1)) {
                                pTangent1 = this.getPoint(tangentsResult.get(0), tangentsResult.get(1));
                                pTangent2 = p2;
                            } else {
                                pTangent1 = this.getPoint(tangentsResult.get(2), tangentsResult.get(3));
                                pTangent2 = p2;
                            }

                        }

                    } else {
                        return;
                    }

                } else {
                    if (((side1 == 1) && (side2 == 1))) {
                        Geom2D.tangentsParalCircleToCircle(this.radius, p1.x, p1.y, p2.x, p2.y, tangentsResult);
                        pTangent1 = this.getPoint(tangentsResult.get(2), tangentsResult.get(3));
                        pTangent2 = this.getPoint(tangentsResult.get(4), tangentsResult.get(5));
                    } else {
                        if (((side1 == -1) && (side2 == -1))) {
                            Geom2D.tangentsParalCircleToCircle(this.radius, p1.x, p1.y, p2.x, p2.y, tangentsResult);
                            pTangent1 = this.getPoint(tangentsResult.get(0), tangentsResult.get(1));
                            pTangent2 = this.getPoint(tangentsResult.get(6), tangentsResult.get(7));
                        } else {
                            if (((side1 == 1) && (side2 == -1))) {
                                if (Geom2D.tangentsCrossCircleToCircle(this.radius, p1.x, p1.y, p2.x, p2.y, tangentsResult)) {
                                    pTangent1 = this.getPoint(tangentsResult.get(2), tangentsResult.get(3));
                                    pTangent2 = this.getPoint(tangentsResult.get(6), tangentsResult.get(7));
                                } else {
                                    return;
                                }

                            } else {
                                if (Geom2D.tangentsCrossCircleToCircle(this.radius, p1.x, p1.y, p2.x, p2.y, tangentsResult)) {
                                    pTangent1 = this.getPoint(tangentsResult.get(0), tangentsResult.get(1));
                                    pTangent2 = this.getPoint(tangentsResult.get(4), tangentsResult.get(5));
                                } else {
                                    return;
                                }

                            }

                        }

                    }

                }

            }

        }

        Point2D successor = ((Point2D) (pointSuccessor.get(p1)));
        double distance = 0.0;
        while ((successor != p2)) {
            distance = Geom2D.distanceSquaredPointToSegment(successor.x, successor.y, pTangent1.x, pTangent1.y, pTangent2.x, pTangent2.y);
            if ((distance < this.radiusSquared)) {
                this.adjustWithTangents(p1, applyRadiusToP1, successor, true, pointSides, pointSuccessor, newPath, adjustedPoints);
                this.adjustWithTangents(successor, true, p2, applyRadiusToP2, pointSides, pointSuccessor, newPath, adjustedPoints);
                return;
            } else {
                successor = ((Point2D) (pointSuccessor.get(successor)));
            }

        }

        adjustedPoints.push(pTangent1);
        adjustedPoints.push(pTangent2);
        newPath.push(p1);
    }

    public void checkAdjustedPath(Vector<Point2D> newPath, Vector<Point2D> adjustedPoints, HashMap<Point2D, Integer> pointSides) {
        boolean needCheck = true;
        Point2D point0 = null;
        int point0Side = 0;
        Point2D point1 = null;
        int point1Side = 0;
        Point2D point2 = null;
        int point2Side = 0;
        Point2D pt1 = null;
        Point2D pt2 = null;
        Point2D pt3 = null;
        double dot = 0.0;
        Vector<Double> tangentsResult = new Vector<>();
        Point2D pTangent1 = null;
        Point2D pTangent2 = null;
        while (needCheck) {
            needCheck = false;
            int i = 2;
            while ((i < newPath.length)) {
                point2 = newPath.get(i);
                point2Side = pointSides.get(point2);
                point1 = newPath.get((i - 1));
                point1Side = pointSides.get(point1);
                point0 = newPath.get((i - 2));
                point0Side = pointSides.get(point0);
                if ((point1Side == point2Side)) {
                    pt1 = adjustedPoints.get((((i - 2)) * 2));
                    pt2 = adjustedPoints.get(((((i - 1)) * 2) - 1));
                    pt3 = adjustedPoints.get((((i - 1)) * 2));
                    dot = ((((pt1.x - pt2.x)) * ((pt3.x - pt2.x))) + (((pt1.y - pt2.y)) * ((pt3.y - pt2.y))));
                    if ((dot > 0)) {
                        if ((i == 2)) {
                            Geom2D.tangentsPointToCircle(point0.x, point0.y, point2.x, point2.y, this.radius, tangentsResult);
                            if ((point2Side == 1)) {
                                pTangent1 = point0;
                                pTangent2 = this.getPoint(tangentsResult.get(2), tangentsResult.get(3));
                            } else {
                                pTangent1 = point0;
                                pTangent2 = this.getPoint(tangentsResult.get(0), tangentsResult.get(1));
                            }

                        } else {
                            if ((i == (newPath.length - 1))) {
                                Geom2D.tangentsPointToCircle(point2.x, point2.y, point0.x, point0.y, this.radius, tangentsResult);
                                if ((point0Side == 1)) {
                                    pTangent1 = this.getPoint(tangentsResult.get(0), tangentsResult.get(1));
                                    pTangent2 = point2;
                                } else {
                                    pTangent1 = this.getPoint(tangentsResult.get(2), tangentsResult.get(3));
                                    pTangent2 = point2;
                                }

                            } else {
                                if (((point0Side == 1) && (point2Side == -1))) {
                                    Geom2D.tangentsCrossCircleToCircle(this.radius, point0.x, point0.y, point2.x, point2.y, tangentsResult);
                                    pTangent1 = this.getPoint(tangentsResult.get(2), tangentsResult.get(3));
                                    pTangent2 = this.getPoint(tangentsResult.get(6), tangentsResult.get(7));
                                } else {
                                    if (((point0Side == -1) && (point2Side == 1))) {
                                        Geom2D.tangentsCrossCircleToCircle(this.radius, point0.x, point0.y, point2.x, point2.y, tangentsResult);
                                        pTangent1 = this.getPoint(tangentsResult.get(0), tangentsResult.get(1));
                                        pTangent2 = this.getPoint(tangentsResult.get(4), tangentsResult.get(5));
                                    } else {
                                        if (((point0Side == 1) && (point2Side == 1))) {
                                            Geom2D.tangentsParalCircleToCircle(this.radius, point0.x, point0.y, point2.x, point2.y, tangentsResult);
                                            pTangent1 = this.getPoint(tangentsResult.get(2), tangentsResult.get(3));
                                            pTangent2 = this.getPoint(tangentsResult.get(4), tangentsResult.get(5));
                                        } else {
                                            if (((point0Side == -1) && (point2Side == -1))) {
                                                Geom2D.tangentsParalCircleToCircle(this.radius, point0.x, point0.y, point2.x, point2.y, tangentsResult);
                                                pTangent1 = this.getPoint(tangentsResult.get(0), tangentsResult.get(1));
                                                pTangent2 = this.getPoint(tangentsResult.get(6), tangentsResult.get(7));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        int temp = (((i - 2)) * 2);
                        adjustedPoints.splice(temp, 1);
                        adjustedPoints.insert(temp, pTangent1);
                        temp = ((i * 2) - 1);
                        adjustedPoints.splice(temp, 1);
                        adjustedPoints.insert(temp, pTangent2);
                        newPath.splice((i - 1), 1);
                        adjustedPoints.splice(((((i - 1)) * 2) - 1), 2);
                        tangentsResult.splice(0, tangentsResult.length);
                        --i;
                    }
                }
                ++i;
            }
        }
    }


    public void smoothAngle(Point2D prevPoint, Point2D pointToSmooth, Point2D nextPoint, int side, Vector<Point2D> encirclePoints) {
        int angleType = Geom2D.getDirection(prevPoint.x, prevPoint.y, pointToSmooth.x, pointToSmooth.y, nextPoint.x, nextPoint.y);
        double distanceSquared = ((((prevPoint.x - nextPoint.x)) * ((prevPoint.x - nextPoint.x))) + (((prevPoint.y - nextPoint.y)) * ((prevPoint.y - nextPoint.y))));
        if ((distanceSquared <= this.sampleCircleDistanceSquared)) {
            return;
        }

        int index = 0;
        int side1 = 0;
        int side2 = 0;
        boolean pointInArea = false;
        double xToCheck = 0.0;
        double yToCheck = 0.0;
        {
            int _g1 = 0;
            int _g = this.numSamplesCircle;
            while ((_g1 < _g)) {
                int i = _g1++;
                pointInArea = false;
                xToCheck = (pointToSmooth.x + this.sampleCircle.get(i).x);
                yToCheck = (pointToSmooth.y + this.sampleCircle.get(i).y);
                side1 = Geom2D.getDirection(prevPoint.x, prevPoint.y, pointToSmooth.x, pointToSmooth.y, xToCheck, yToCheck);
                side2 = Geom2D.getDirection(pointToSmooth.x, pointToSmooth.y, nextPoint.x, nextPoint.y, xToCheck, yToCheck);
                if ((side == 1)) {
                    if ((angleType == -1)) {
                        if (((side1 == -1) && (side2 == -1))) {
                            pointInArea = true;
                        }

                    } else {
                        if (((side1 == -1) || (side2 == -1))) {
                            pointInArea = true;
                        }

                    }

                } else {
                    if ((angleType == 1)) {
                        if (((side1 == 1) && (side2 == 1))) {
                            pointInArea = true;
                        }

                    } else {
                        if (((side1 == 1) || (side2 == 1))) {
                            pointInArea = true;
                        }

                    }

                }

                if (pointInArea) {
                    encirclePoints.splice(index, 0);
                    encirclePoints.insert(index, new Point2D(xToCheck, yToCheck));
                    ++index;
                } else {
                    index = 0;
                }

            }

        }

        if ((side == -1)) {
            encirclePoints.reverse();
        }

    }

}
