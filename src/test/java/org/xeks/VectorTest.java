package org.xeks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector<String> vector = new Vector<>(new String[]{"Hello", "world", "the", "people"});

    @Test
    public void testObject() {
        assertEquals(vector.array[0], "Hello");
    }

    @Test
    public void testSplice() {
        vector.splice(0, 1);
        assertEquals(vector.array[0] ,"world");
    }

    @Test
    public void testIndexOf() {
        assertEquals(vector.indexOf(vector.array[2], 1) ,2);
    }

    @Test
    public void testPush() {
        assertEquals(vector.push("new"), 5);
    }

    @Test
    public void testPop() {
        assertEquals(vector.pop(), "people");
    }
    @Test
    public void testJoin() {
        assertEquals(vector.join(" "), "Hello world the people");
    }
    @Test
    public void testIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> vector.array[10].isEmpty());
    }
}