package org.xeks.data.math;

import org.xeks.Vector;
import org.xeks.data.Edge;
import org.xeks.data.Face;
import org.xeks.data.Mesh;
import org.xeks.data.Vertex;
import org.xeks.iterators.FromFaceToInnerEdges;
import org.xeks.iterators.FromVertexToHoldingFaces;

import java.util.HashMap;

public class Geom2D {


    public static RandGenerator randGen = new RandGenerator(1234, 0, 1);

    public static Vector<Vertex> samples = new Vector<>();

    public static Point2D circumcenter;

    public static boolean intersections2segments(double s1p1x, double s1p1y, double s1p2x, double s1p2y, double s2p1x, double s2p1y, double s2p2x, double s2p2y, Point2D posIntersection, Vector<Object> paramIntersection, java.lang.Object infiniteLineMode) {
        boolean tempInfiniteLineMode = toBool(infiniteLineMode);
        double t1 = 0;
        double t2 = 0;
        boolean result;
        double divisor = ((((s1p1x - s1p2x)) * ((s2p1y - s2p2y))) + (((s1p2y - s1p1y)) * ((s2p1x - s2p2x))));
        if ((divisor == 0)) {
            result = false;
        } else {
            result = true;
            if (((!(tempInfiniteLineMode) || (posIntersection != null)) || (paramIntersection != null))) {
                t1 = ((((((s1p1x * ((s2p1y - s2p2y))) + (s1p1y * ((s2p2x - s2p1x)))) + (s2p1x * s2p2y)) - (s2p1y * s2p2x))) / divisor);
                t2 = ((((((s1p1x * ((s2p1y - s1p2y))) + (s1p1y * ((s1p2x - s2p1x)))) - (s1p2x * s2p1y)) + (s1p2y * s2p1x))) / divisor);
                if ((!(tempInfiniteLineMode) && !((((((0 <= t1) && (t1 <= 1)) && (0 <= t2)) && (t2 <= 1)))))) {
                    result = false;
                }
            }
        }
        if (result) {
            if ((posIntersection != null)) {
                posIntersection.x = (s1p1x + (t1 * ((s1p2x - s1p1x))));
                posIntersection.y = (s1p1y + (t1 * ((s1p2y - s1p1y))));
            }
            if ((paramIntersection != null)) {
                paramIntersection.push(t1);
                paramIntersection.push(t2);
            }
        }
        return result;
    }

    private static boolean toBool(Object obj) {
        return obj != null && ((Boolean) obj).booleanValue();
    }

    public static boolean intersections2edges(Edge edge1, Edge edge2, Point2D posIntersection, Vector<Object> paramIntersection, Object infiniteLineMode) {
        return Geom2D.intersections2segments(edge1.getOriginVertex().getPos().x, edge1.getOriginVertex().getPos().y, edge1.getDestinationVertex().getPos().x, edge1.getDestinationVertex().getPos().y, edge2.getOriginVertex().getPos().x, edge2.getOriginVertex().getPos().y, edge2.getDestinationVertex().getPos().x, edge2.getDestinationVertex().getPos().y, posIntersection, paramIntersection, infiniteLineMode);
    }

    public static double distanceSquaredVertexToEdge(Vertex vertex, Edge edge) {
        return Geom2D.distanceSquaredPointToSegment(vertex.getPos().x, vertex.getPos().y, edge.getOriginVertex().getPos().x, edge.getOriginVertex().getPos().y, edge.getDestinationVertex().getPos().x, edge.getDestinationVertex().getPos().y);
    }

    public static double distanceSquaredPointToSegment(double px, double py, double ax, double ay, double bx, double by) {
        double a_b_squaredLength = ((((bx - ax)) * ((bx - ax))) + (((by - ay)) * ((by - ay))));
        double dotProduct = ((((((px - ax)) * ((bx - ax))) + (((py - ay)) * ((by - ay))))) / a_b_squaredLength);
        if ((dotProduct < 0)) {
            return ((((px - ax)) * ((px - ax))) + (((py - ay)) * ((py - ay))));
        } else {
            if ((dotProduct <= 1)) {
                double p_a_squaredLength = ((((ax - px)) * ((ax - px))) + (((ay - py)) * ((ay - py))));
                return (p_a_squaredLength - ((dotProduct * dotProduct) * a_b_squaredLength));
            } else {
                return ((((px - bx)) * ((px - bx))) + (((py - by)) * ((py - by))));
            }
        }
    }


    public static int getRelativePosition2(double x, double y, Edge eUp) {
        return Geom2D.getDirection2(eUp.getOriginVertex().getPos().x, eUp.getOriginVertex().getPos().y, eUp.getDestinationVertex().getPos().x, eUp.getDestinationVertex().getPos().y, x, y);
    }

    public static int getDirection2(double x1, double y1, double x2, double y2, double x3, double y3) {
        double dot = ((((x3 - x1)) * ((y2 - y1))) + (((y3 - y1)) * ((-(x2) + x1))));
        if ((dot == 0)) {
            return 0;
        } else {
            if ((dot > 0)) {
                if ((Geom2D.distanceSquaredPointToLine(x3, y3, x1, y1, x2, y2) <= 0.0001)) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if ((Geom2D.distanceSquaredPointToLine(x3, y3, x1, y1, x2, y2) <= 0.0001)) {
                    return 0;
                } else {
                    return -1;
                }
            }
        }
    }

    public static double distanceSquaredPointToLine(double px, double py, double ax, double ay, double bx, double by) {
        double a_b_squaredLength = ((((bx - ax)) * ((bx - ax))) + (((by - ay)) * ((by - ay))));
        double dotProduct = ((((px - ax)) * ((bx - ax))) + (((py - ay)) * ((by - ay))));
        double p_a_squaredLength = ((((ax - px)) * ((ax - px))) + (((ay - py)) * ((ay - py))));
        return (p_a_squaredLength - ((dotProduct * dotProduct) / a_b_squaredLength));
    }

    public static Point2D getCircumcenter(double x1, double y1, double x2, double y2, double x3, double y3, Point2D result) {
        if ((result == null)) {
            result = new Point2D();
        }
        double m1 = (((x1 + x2)) / 2);
        double m2 = (((y1 + y2)) / 2);
        double m3 = (((x1 + x3)) / 2);
        double m4 = (((y1 + y3)) / 2);
        double t1 = (((((m1 * ((x1 - x3))) + (((m2 - m4)) * ((y1 - y3)))) + (m3 * ((x3 - x1))))) / ((((x1 * ((y3 - y2))) + (x2 * ((y1 - y3)))) + (x3 * ((y2 - y1))))));
        result.x = (m1 + (t1 * ((y2 - y1))));
        result.y = (m2 - (t1 * ((x2 - x1))));
        return result;
    }

    public static void projectOrthogonaly(Point2D vertexPos, Edge edge) {
        double a = edge.getOriginVertex().getPos().x;
        double b = edge.getOriginVertex().getPos().y;
        double c = edge.getDestinationVertex().getPos().x;
        double d = edge.getDestinationVertex().getPos().y;
        double e = vertexPos.x;
        double f = vertexPos.y;
        double t1 = ((((((((((a * a) - (a * c)) - (a * e)) + (b * b)) - (b * d)) - (b * f)) + (c * e)) + (d * f))) / (((((((a * a) - ((2 * a) * c)) + (b * b)) - ((2 * b) * d)) + (c * c)) + (d * d))));
        vertexPos.x = (a + (t1 * ((c - a))));
        vertexPos.y = (b + (t1 * ((d - b))));
    }

    public static Intersection locatePosition(double x, double y, Mesh mesh) {
        if ((Geom2D.randGen == null)) {

            Geom2D.randGen = new RandGenerator(1234, 0, 1);
        }

        Geom2D.randGen.set_currSeed(((int) (((x * 10) + (4 * y)))));
        int i = 0;
        Geom2D.samples.splice(0, Geom2D.samples.length);
        int numSamples = ((int) (java.lang.Math.pow(((double) (mesh.vertices.length)), 0.333333333333333315)));
        Geom2D.randGen.rangeMin = 0;
        Geom2D.randGen.rangeMax = (mesh.vertices.length - 1);
        {
            int _g1 = 0;
            int _g = numSamples;
            while ((_g1 < _g)) {
                int i1 = _g1++;
                int _rnd = Geom2D.randGen.next();
                boolean cond = ((_rnd < 0) || (_rnd > (mesh.vertices.length - 1)));
                {
                    boolean cond1 = (mesh.vertices == null);
                    java.lang.String message = ("vertices: " + mesh.vertices.length);
                }

                Geom2D.samples.push(mesh.vertices.get(_rnd));
            }

        }

        Vertex currVertex = null;
        Point2D currVertexPos = null;
        double distSquared = 0.0;
        double minDistSquared = java.lang.Double.POSITIVE_INFINITY;
        Vertex closedVertex = null;
        {
            int _g11 = 0;
            int _g2 = numSamples;
            while ((_g11 < _g2)) {
                int i2 = _g11++;
                currVertex = Geom2D.samples.get(i2);
                currVertexPos = currVertex.getPos();
                distSquared = ((((currVertexPos.x - x)) * ((currVertexPos.x - x))) + (((currVertexPos.y - y)) * ((currVertexPos.y - y))));
                if ((distSquared < minDistSquared)) {
                    minDistSquared = distSquared;
                    closedVertex = currVertex;
                }

            }

        }

        Face currFace = null;
        FromVertexToHoldingFaces iterFace = new FromVertexToHoldingFaces();
        iterFace.setFromVertex(closedVertex);
        currFace = iterFace.next();
        HashMap<Face, Boolean> faceVisited = new HashMap<>();
        Edge currEdge = null;
        FromFaceToInnerEdges iterEdge = new FromFaceToInnerEdges();
        Intersection objectContainer = Intersection.ENull;
        int relativPos = 0;
        int numIter = 0;
        while (true) {
            //todo check currFace
            if(currFace == null){
                break;
            }
            boolean tmp = false;
            if (currFace != null && (!(faceVisited.get(currFace)))) {
                objectContainer = Geom2D.isInFace(x, y, currFace);
                Intersection _g3 = objectContainer;
                switch (_g3.index) {
                    case 3: {
                        tmp = true;
                        break;
                    }
                    default: {
                        tmp = false;
                        break;
                    }

                }

            } else {
                tmp = true;
            }

            if (!(tmp)) {
                break;
            }

            faceVisited.get(currFace);
            ++numIter;
            boolean tmp1 = (numIter == 50);
            iterEdge.setFromFace(currFace);
            while (true) {
                currEdge = iterEdge.next();
                if ((currEdge == null)) {
                    return Intersection.ENull;
                }

                relativPos = Geom2D.getRelativePosition(x, y, currEdge);
                if (!((((relativPos == 1) || (relativPos == 0))))) {
                    break;
                }
            }
            currFace = currEdge.getRightFace();
        }

        return objectContainer;
    }

    public static int getDirection(double x1, double y1, double x2, double y2, double x3, double y3) {
        double dot = ((((x3 - x1)) * ((y2 - y1))) + (((y3 - y1)) * ((-(x2) + x1))));
        if ((dot == 0)) {
            return 0;
        } else {
            if ((dot > 0)) {
                return 1;
            } else {
                return -1;
            }

        }

    }
    
    public static int getRelativePosition(double x, double y, Edge eUp) {

        return Geom2D.getDirection(eUp.getOriginVertex().getPos().x, eUp.getOriginVertex().getPos().y, eUp.getDestinationVertex().getPos().x, eUp.getDestinationVertex().getPos().y, x, y);
    }

    public static Intersection isInFace(double x, double y, Face polygon) {
        Intersection result = Intersection.ENull;
        Edge e1_2 = polygon.getEdge();
        Edge e2_3 = e1_2.getNextLeftEdge();
        Edge e3_1 = e2_3.getNextLeftEdge();
        if ((((Geom2D.getRelativePosition(x, y, e1_2) >= 0) && (Geom2D.getRelativePosition(x, y, e2_3) >= 0)) && (Geom2D.getRelativePosition(x, y, e3_1) >= 0))) {
            Vertex v1 = e1_2.getOriginVertex();
            Vertex v2 = e2_3.getOriginVertex();
            Vertex v3 = e3_1.getOriginVertex();
            double x1 = v1.getPos().x;
            double y1 = v1.getPos().y;
            double x2 = v2.getPos().x;
            double y2 = v2.getPos().y;
            double x3 = v3.getPos().x;
            double y3 = v3.getPos().y;
            double v_v1squaredLength = ((((x1 - x)) * ((x1 - x))) + (((y1 - y)) * ((y1 - y))));
            double v_v2squaredLength = ((((x2 - x)) * ((x2 - x))) + (((y2 - y)) * ((y2 - y))));
            double v_v3squaredLength = ((((x3 - x)) * ((x3 - x))) + (((y3 - y)) * ((y3 - y))));
            double v1_v2squaredLength = ((((x2 - x1)) * ((x2 - x1))) + (((y2 - y1)) * ((y2 - y1))));
            double v2_v3squaredLength = ((((x3 - x2)) * ((x3 - x2))) + (((y3 - y2)) * ((y3 - y2))));
            double v3_v1squaredLength = ((((x1 - x3)) * ((x1 - x3))) + (((y1 - y3)) * ((y1 - y3))));
            double dot_v_v1v2 = ((((x - x1)) * ((x2 - x1))) + (((y - y1)) * ((y2 - y1))));
            double dot_v_v2v3 = ((((x - x2)) * ((x3 - x2))) + (((y - y2)) * ((y3 - y2))));
            double dot_v_v3v1 = ((((x - x3)) * ((x1 - x3))) + (((y - y3)) * ((y1 - y3))));
            double v_e1_2squaredLength = (v_v1squaredLength - ((dot_v_v1v2 * dot_v_v1v2) / v1_v2squaredLength));
            double v_e2_3squaredLength = (v_v2squaredLength - ((dot_v_v2v3 * dot_v_v2v3) / v2_v3squaredLength));
            double v_e3_1squaredLength = (v_v3squaredLength - ((dot_v_v3v1 * dot_v_v3v1) / v3_v1squaredLength));
            boolean closeTo_e1_2 = (v_e1_2squaredLength <= 0.0001);
            boolean closeTo_e2_3 = (v_e2_3squaredLength <= 0.0001);
            boolean closeTo_e3_1 = (v_e3_1squaredLength <= 0.0001);
            if (closeTo_e1_2) {
                if (closeTo_e3_1) {
                    result = Intersection.EVertex(v1);
                } else {
                    if (closeTo_e2_3) {
                        result = Intersection.EVertex(v2);
                    } else {
                        result = Intersection.EEdge(e1_2);
                    }

                }

            } else {
                if (closeTo_e2_3) {
                    if (closeTo_e3_1) {
                        result = Intersection.EVertex(v3);
                    } else {
                        result = Intersection.EEdge(e2_3);
                    }

                } else {
                    if (closeTo_e3_1) {
                        result = Intersection.EEdge(e3_1);
                    } else {
                        result = Intersection.EFace(polygon);
                    }
                }
            }
        }
        return result;
    }

    public static boolean isDelaunay(Edge edge)
    {
        Vertex vLeft = edge.getOriginVertex();
        Vertex vRight = edge.getDestinationVertex();
        Vertex vCorner = edge.getNextLeftEdge().getDestinationVertex();
        Vertex vOpposite = edge.getNextRightEdge().getDestinationVertex();
        getCircumcenter(vCorner.getPos().x, vCorner.getPos().y, vLeft.getPos().x, vLeft.getPos().y, vRight.getPos().x, vRight.getPos().y,Geom2D.circumcenter);
        double squaredRadius = ( ( (( vCorner.getPos().x - Geom2D.circumcenter.x )) * (( vCorner.getPos().x - Geom2D.circumcenter.x )) ) + ( (( vCorner.getPos().y - Geom2D.circumcenter.y )) * (( vCorner.getPos().y - Geom2D.circumcenter.y )) ) );
        double squaredDistance = ( ( (( vOpposite.getPos().x - Geom2D.circumcenter.x )) * (( vOpposite.getPos().x - Geom2D.circumcenter.x )) ) + ( (( vOpposite.getPos().y - Geom2D.circumcenter.y )) * (( vOpposite.getPos().y -Geom2D.circumcenter.y )) ) );
        return ( squaredDistance >= squaredRadius );
    }
}
