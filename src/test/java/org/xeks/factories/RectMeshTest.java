package org.xeks.factories;

import org.junit.jupiter.api.Test;
import org.xeks.Vector;
import org.xeks.ai.EntityAI;
import org.xeks.ai.PathFinder;
import org.xeks.data.Mesh;
import org.xeks.data.Obstacle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RectMeshTest {

    @Test
    public void testCreateMesh() {
        long startTime = System.nanoTime();
        Mesh mesh = RectMesh.buildRectangle(50000, 50000);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                mesh.insertConstraintSegment(i * 100, j * 100, i * 300, j * 300);
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
            pathFinder.findPath(49970, 49970, resultPath);
        }
        long endTime = System.nanoTime();
        System.out.println("Total execution time: " + (endTime - startTime) / 1000000 + "ms" + " AI " + (endTime - startTimeAI) / 1000000 + "ms");
    }


    @Test
    public void testCreateObject() {
        Vector<Double> vertices = new Vector<>();
        vertices.push((double) (0));
        vertices.push((double) (0));
        vertices.push((double) ( 50));
        vertices.push((double) ( 50));
        long startTime = System.nanoTime();
        Mesh mesh = RectMesh.buildRectangle(50000, 50000);
        mesh.insertConstraintSegment(30, 50, 230, 300);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if ((int) (j % 5) == 0) {
                    Obstacle obstacle = new Obstacle();
                    Vector<Double> array = new Vector<>();
                    double prevX = vertices.get(vertices.length - 2);
                    double prevY = vertices.get(vertices.length - 1);
                    for (int s = 0; s < vertices.length; s += 2) {
                        double vx = vertices.get(s);
                        double vy = vertices.get(s + 1);
                        array.push(prevX);
                        //noinspection SuspiciousNameCombination
                        array.push(prevY);
                        array.push(vx);
                        array.push(vy);
                        prevX = vx;
                        prevY = vy;
                    }
                    obstacle.setX(i*100);
                    obstacle.setY(j*100);
                    obstacle.setCoordinates(array);
                    mesh.insertObject(obstacle);

                }
            }
        }
        mesh.updateObjects();
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
            pathFinder.findPath(49970, 49970, resultPath);
        }
        long endTime = System.nanoTime();
        System.out.println("Total execution time: " + (endTime - startTime) / 1000000 + "ms" + " AI " + (endTime - startTimeAI) / 1000000 + "ms");
        assertNotNull(mesh);
    }
}