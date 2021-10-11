package org.xeks.factories;

import org.xeks.Vector;
import org.xeks.data.*;

public class RectMesh {

    public static Mesh buildRectangle(double width, double height)
    {
        Vertex vTL = new Vertex();
        Vertex vTR = new Vertex();
        Vertex vBR = new Vertex();
        Vertex vBL = new Vertex();
        Edge eTL_TR = new Edge();
        Edge eTR_TL = new Edge();
        Edge eTR_BR = new Edge();
        Edge eBR_TR = new Edge();
        Edge eBR_BL = new Edge();
        Edge eBL_BR = new Edge();
        Edge eBL_TL = new Edge();
        Edge eTL_BL = new Edge();
        Edge eTR_BL = new Edge();
        Edge eBL_TR = new Edge();
        Edge eTL_BR = new Edge();
        Edge eBR_TL = new Edge();
        Face fTL_BL_TR = new Face();
        Face fTR_BL_BR = new Face();
        Face fTL_BR_BL = new Face();
        Face fTL_TR_BR = new Face();
        ConstraintShape boundShape = new ConstraintShape();
        ConstraintSegment segTop = new ConstraintSegment();
        ConstraintSegment segRight = new ConstraintSegment();
        ConstraintSegment segBot = new ConstraintSegment();
        ConstraintSegment segLeft = new ConstraintSegment();
        Mesh mesh = new Mesh(width, height);
        double offset = 10.;
        vTL.getPos().setXY(( 0 - offset ), ( 0 - offset ));
        vTR.getPos().setXY(( width + offset ), ( 0 - offset ));
        vBR.getPos().setXY(( width + offset ), ( height + offset ));
        vBL.getPos().setXY(( 0 - offset ), ( height + offset ));
        vTL.setDatas(eTL_TR, false);
        vTR.setDatas(eTR_BR, false);
        vBR.setDatas(eBR_BL, false);
        vBL.setDatas(eBL_TL, false);
        eTL_TR.setDatas(vTL, eTR_TL, eTR_BR, fTL_TR_BR, true, true);
        eTR_TL.setDatas(vTR, eTL_TR, eTL_BL, fTL_BL_TR, true, true);
        eTR_BR.setDatas(vTR, eBR_TR, eBR_TL, fTL_TR_BR, true, true);
        eBR_TR.setDatas(vBR, eTR_BR, eTR_BL, fTR_BL_BR, true, true);
        eBR_BL.setDatas(vBR, eBL_BR, eBL_TL, fTL_BR_BL, true, true);
        eBL_BR.setDatas(vBL, eBR_BL, eBR_TR, fTR_BL_BR, true, true);
        eBL_TL.setDatas(vBL, eTL_BL, eTL_BR, fTL_BR_BL, true, true);
        eTL_BL.setDatas(vTL, eBL_TL, eBL_TR, fTL_BL_TR, true, true);
        eTR_BL.setDatas(vTR, eBL_TR, eBL_BR, fTR_BL_BR, true, false);
        eBL_TR.setDatas(vBL, eTR_BL, eTR_TL, fTL_BL_TR, true, false);
        eTL_BR.setDatas(vTL, eBR_TL, eBR_BL, fTL_BR_BL, false, false);
        eBR_TL.setDatas(vBR, eTL_BR, eTL_TR, fTL_TR_BR, false, false);
        fTL_BL_TR.setDatas(eBL_TR, false);
        fTR_BL_BR.setDatas(eTR_BL, false);
        fTL_BR_BL.setDatas(eBR_BL, false);
        fTL_TR_BR.setDatas(eTR_BR, false);
        vTL.setFromConstraintSegments(new Vector<>(new ConstraintSegment[]{segTop, segLeft}));
        vTR.setFromConstraintSegments(new Vector<>(new ConstraintSegment[]{segTop, segRight}));
        vBR.setFromConstraintSegments(new Vector<>(new ConstraintSegment[]{segRight, segBot}));
        vBL.setFromConstraintSegments(new Vector<>(new ConstraintSegment[]{segBot, segLeft}));
        eTL_TR.fromConstraintSegments.push(segTop);
        eTR_TL.fromConstraintSegments.push(segTop);
        eTR_BR.fromConstraintSegments.push(segRight);
        eBR_TR.fromConstraintSegments.push(segRight);
        eBR_BL.fromConstraintSegments.push(segBot);
        eBL_BR.fromConstraintSegments.push(segBot);
        eBL_TL.fromConstraintSegments.push(segLeft);
        eTL_BL.fromConstraintSegments.push(segLeft);
        segTop.getEdges().push(eTL_TR);
        segRight.getEdges().push(eTR_BR);
        segBot.getEdges().push(eBR_BL);
        segLeft.getEdges().push(eBL_TL);
        segTop.fromShape = boundShape;
        segRight.fromShape = boundShape;
        segBot.fromShape = boundShape;
        segLeft.fromShape = boundShape;
        {
            int _g = 0;
            Vector<ConstraintSegment> _g1 = new Vector<ConstraintSegment>(new ConstraintSegment[]{segTop, segRight, segBot, segLeft});
            while (( _g < _g1.length ))
            {
                ConstraintSegment f = _g1.get(_g);
                ++ _g;
                boundShape.segments.push(f);
            }

        }

        {
            int _g2 = 0;
            Vector<Vertex> _g11 = new Vector<Vertex>(new Vertex[]{vTL, vTR, vBR, vBL});
            while (( _g2 < _g11.length ))
            {
                Vertex f1 = _g11.get(_g2);
                ++ _g2;
                mesh.vertices.push(f1);
            }

        }

        {
            int _g3 = 0;
            Vector<Edge> _g12 = new Vector<>(new Edge[]{eTL_TR, eTR_TL, eTR_BR, eBR_TR, eBR_BL, eBL_BR, eBL_TL, eTL_BL, eTR_BL, eBL_TR, eTL_BR, eBR_TL});
            while (( _g3 < _g12.length ))
            {
                Edge f2 = _g12.get(_g3);
                ++ _g3;
                mesh.edges.push(f2);
            }

        }

        {
            int _g4 = 0;
            Vector<Face> _g13 = new Vector<>(new Face[]{fTL_BL_TR, fTR_BL_BR, fTL_BR_BL, fTL_TR_BR});
            while (( _g4 < _g13.length ))
            {
                Face f3 = _g13.get(_g4);
                ++ _g4;
                mesh.faces.push(f3);
            }

        }

        mesh.getConstraintShapes().push(boundShape);
        Vector<java.lang.Double> securityRect = new Vector<>();
        {
            int _g5 = 0;
            Vector<java.lang.Double> _g14 = new Vector<>(new Double[]{Double.valueOf(0), Double.valueOf(0), width, Double.valueOf(0)});
            while (( _g5 < _g14.length ))
            {
                double f4 = _g14.get(_g5);
                ++ _g5;
                securityRect.push(f4);
            }

        }

        {
            int _g6 = 0;
            Vector<java.lang.Double> _g15 = new Vector<>(new Double[]{width, Double.valueOf(0), width, height});
            while (( _g6 < _g15.length ))
            {
                double f5 = _g15.get(_g6);
                ++ _g6;
                securityRect.push(f5);
            }

        }

        {
            int _g7 = 0;
            Vector<java.lang.Double> _g16 = new Vector<>(new java.lang.Double[]{width, height, Double.valueOf(0), height});
            while (( _g7 < _g16.length ))
            {
                double f6 = _g16.get(_g7);
                ++ _g7;
                securityRect.push(f6);
            }

        }

        {
            int _g8 = 0;
            Vector<java.lang.Double> _g17 = new Vector<>(new java.lang.Double[]{Double.valueOf(0), height, Double.valueOf(0), Double.valueOf(0)});
            while (( _g8 < _g17.length ))
            {
                double f7 = _g17.get(_g8);
                ++ _g8;
                securityRect.push(f7);
            }

        }

        mesh.setClipping(false);
        mesh.insertConstraintShape(securityRect);
        mesh.setClipping(true);
        return mesh;
    }
    
}
