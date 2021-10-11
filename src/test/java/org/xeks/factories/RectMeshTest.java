package org.xeks.factories;

import org.junit.jupiter.api.Test;
import org.xeks.data.Mesh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RectMeshTest {

    @Test
    public void testCreateMesh() {
        Mesh mesh = RectMesh.buildRectangle(500, 500);
        assertNotNull(mesh);
        mesh.insertConstraintSegment(30, 50, 230, 300);
        assertEquals(mesh.getEdges().length , 48);
        assertEquals(mesh.getVertices().length , 10);
        assertEquals(mesh.getFaces().length , 16);
    }
}