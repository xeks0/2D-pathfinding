package org.xeks.data;

public class Utils {

    public static int compare(Object v1, Object v2) {

        if (v1 == v2)
            return 0;
        if (v1 == null) return -1;
        if (v2 == null) return 1;

        if (v1 instanceof java.lang.Number || v2 instanceof java.lang.Number) {
            java.lang.Number v1c = (java.lang.Number) v1;
            java.lang.Number v2c = (java.lang.Number) v2;

            if (v1 instanceof java.lang.Long || v2 instanceof java.lang.Long) {
                long l1 = (v1 == null) ? 0L : v1c.longValue();
                long l2 = (v2 == null) ? 0L : v2c.longValue();
                return (l1 < l2) ? -1 : (l1 > l2) ? 1 : 0;
            } else {
                double d1 = (v1 == null) ? 0.0 : v1c.doubleValue();
                double d2 = (v2 == null) ? 0.0 : v2c.doubleValue();

                return (d1 < d2) ? -1 : (d1 > d2) ? 1 : 0;
            }
        }

        return ((java.lang.String) v1).compareTo((java.lang.String) v2);

    }
}
