package org.xeks;

import org.junit.jupiter.api.Test;
import org.xeks.data.SortArgInterface;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector<String> vector = new Vector<>(new String[]{"Hello", "world", "the", "people"});

    @Test
    public void testObject() {
        assertEquals(vector.get(0), "Hello");
    }

    @Test
    public void testSplice() {
        vector.splice(0, 1);
        assertEquals(vector.get(0) ,"world");
    }

    @Test
    public void testIndexOf() {
        assertEquals(vector.indexOf(vector.get(2), 1) ,2);
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
    public void testUnshift() {
        vector.unshift(vector.get(1));
        assertEquals(vector.get(0) , "world");
    }

    @Test
    public void testInsert() {
        vector.insert(0,"Int");
        assertEquals(vector.get(0) , "Int");
    }

    @Test
    public void testShift() {
        vector.shift();
        assertEquals(vector.get(0) , "world");
    }

    @Test
    public void testSort() {
        SortArgInterface d = (int a, int b)-> Integer.compare(vector.get(b).length(), vector.get(a).length());
        vector.sort(d);
        assertEquals(vector.get(0) , "people");
    }

    @Test
    public void testIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> vector.array[10].isEmpty());
    }

    @Test
    public void testNullPointerException() {
        assertThrows(NullPointerException.class, () -> vector.get(10).isEmpty());
    }
}