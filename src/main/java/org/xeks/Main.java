package org.xeks;

public class Main {
    public static void main(String[] args){

        Vector<String> vector = new Vector<>(new String[]{"Hello","world","the","people"});
        System.out.println("vector = " + vector._a[0]);
        vector.spice(0,1);
        System.out.println("vector = " + vector._a[0]);
    }
}
