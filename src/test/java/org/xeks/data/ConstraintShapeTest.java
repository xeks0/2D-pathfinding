package org.xeks.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstraintShapeTest {
    @Test
    public void testDispose() {
        ConstraintSegment constraintSegment = new ConstraintSegment();
        ConstraintShape constraintShape = new ConstraintShape();
        constraintShape.getSegments().push(constraintSegment);
        constraintShape.dispose();
        assertThrows(NullPointerException.class,()->constraintShape.getSegments().toString());
    }
}