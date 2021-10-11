package org.xeks.factories;

import org.junit.jupiter.api.Test;
import org.xeks.data.Mesh;

import static org.junit.jupiter.api.Assertions.*;

class RectMeshTest {

    @Test
    public void testCreateMesh() {
        Mesh mesh = RectMesh.buildRectangle(500, 500);
        assertNotNull(mesh);
    }


}