package org.xeks.ai;

import lombok.Getter;
import lombok.Setter;
import org.xeks.Vector;
import org.xeks.data.Edge;
import org.xeks.data.Face;
import org.xeks.data.Mesh;
import org.xeks.data.math.Geom2D;

public class PathFinder {

    public PathFinder() {
        this.astar = new AStar();
        this.funnel = new Funnel();
        this.listFaces = new Vector<>();
        this.listEdges = new Vector<>();
    }

    @Getter
    @Setter
    public EntityAI entity;

    public Mesh _mesh;

    public AStar astar;

    public Funnel funnel;

    public double radius;

    public Vector<Face> listFaces;

    public Vector<Edge> listEdges;


    public void dispose()
    {
        this._mesh = null;
        this.astar.dispose();
        this.astar = null;
        this.funnel.dispose();
        this.funnel = null;
        this.listEdges = null;
        this.listFaces = null;
    }


    public Mesh setMesh(Mesh value)
    {
        this._mesh = value;
        this.astar.setMesh(this._mesh);
        return value;
    }


    public void findPath(double toX, double toY, Vector<Double> resultPath)
    {
        resultPath.splice(0, resultPath.length);
        boolean cond = ( this._mesh == null );
        boolean cond1 = ( this.entity == null );
        if (Geom2D.isCircleIntersectingAnyConstraint(toX, toY, this.entity.get_radius(), this._mesh))
        {
            return ;
        }

        this.astar.setRadius(this.entity.get_radius());
        this.funnel.setRadius(this.entity.get_radius());
        this.listFaces.splice(0, this.listFaces.length);
        this.listEdges.splice(0, this.listEdges.length);
        this.astar.findPath(this.entity.x, this.entity.y, toX, toY, this.listFaces, this.listEdges);
        if (( this.listFaces.length == 0 ))
        {
            return ;
        }

        this.funnel.findPath(this.entity.x, this.entity.y, toX, toY, this.listFaces, this.listEdges, resultPath);
    }

}
