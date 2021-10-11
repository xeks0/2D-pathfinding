package org.xeks.factories;

import org.junit.jupiter.api.Test;
import org.xeks.Vector;
import org.xeks.ai.EntityAI;
import org.xeks.ai.PathFinder;
import org.xeks.data.Mesh;

class RectMeshTest {

    @Test
    public void testCreateMesh() {
        long startTime = System.nanoTime();
        Mesh mesh = RectMesh.buildRectangle(50000, 50000);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                mesh.insertConstraintSegment(i*100, j*100, i*300, j*300);
            }
        }
        long startTimeAI = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            EntityAI entityAI = new EntityAI();
            entityAI.setRadius(20);
            entityAI.x = 1;
            entityAI.y = 1;
            PathFinder pathFinder = new PathFinder();
            pathFinder.setMesh(mesh);
            pathFinder.entity = entityAI;
            Vector<Double> resultPath = new Vector<>();
            pathFinder.findPath(49970,49970,resultPath);
        }
        long endTime = System.nanoTime();
        System.out.println("Total execution time: " + (endTime-startTime)/1000000 + "ms" + " AI "+ (endTime-startTimeAI)/1000000 + "ms" );
    }


}