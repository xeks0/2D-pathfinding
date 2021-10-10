package org.xeks.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstraintSegmentTest {
    ConstraintSegment constraintSegment = new ConstraintSegment();
    @Test
    public void testPush() {
        constraintSegment.addEdge(new Edge());
        assertEquals(constraintSegment.getEdges().length, 1);
    }

    @Test
    public void testRemoveEdge() {
        Edge edge = new Edge();
        constraintSegment.addEdge(edge);
        constraintSegment.removeEdge(edge);
        assertEquals(constraintSegment.getEdges().length, 0);
    }

    @Test
    public void testRemoveEdgeEmpty() {
        Edge edge = new Edge();
        constraintSegment.removeEdge(edge);
        assertEquals(constraintSegment.getEdges().length, 0);
    }

    @Test
    public void testDispose() {
        Edge edge = new Edge();
        constraintSegment.addEdge(edge);
        constraintSegment.dispose();
        assertThrows(NullPointerException.class,()->constraintSegment.getEdges().toString());
    }
}