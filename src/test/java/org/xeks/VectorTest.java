package org.xeks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VectorTest {

    Vector<String> vector = new Vector<>(new String[]{"Hello", "world", "the", "people"});
    @Test
    public void testObject() {
        assert vector.array[0].equals("Hello");
    }
    @Test
    public void testSplice() {
        vector.splice(0,1);
        assert vector.array[0].equals("world");
    }
    @Test
    public void testIndexOf() {
        assert vector.indexOf(vector.array[2],1) == 2;
    }
    @Test
    public void testIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> vector.array[10].isEmpty());
    }
}