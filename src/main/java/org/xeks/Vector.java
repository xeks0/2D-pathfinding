package org.xeks;

import static java.lang.System.arraycopy;

public class Vector<T> {

    public int length;
    public T[] _a;

    public Vector(T[] obj) {
        this._a = obj;
        this.length = obj.length;
    }

    public Vector() {
        this.length = 0;
        this._a = ((T[]) new Object[0]);
    }

    public final Vector<T> spice(int pos, int len) {
        if ((len < 0)) {
            return new Vector<T>();
        }
        if ((pos < 0)) {
            pos = (this.length + pos);
            if ((pos < 0)) {
                pos = 0;
            }
        }
        if ((pos > this.length)) {
            pos = 0;
            len = 0;
        } else {
            if (((pos + len) > this.length)) {
                len = (this.length - pos);
                if ((len < 0)) {
                    len = 0;
                }
            }
        }
        T[] a = this._a;
        T[] ret = ((T[]) new Object[len]);
        arraycopy(a, pos, ret, 0, len);
        Vector<T> ret1 = Vector.ofObj(ret);
        int end = (pos + len);
        arraycopy(a, end, a, pos, (this.length - end));
        this.length -= len;
        while ((--len >= 0)) {
            a[(this.length + len)] = null;
        }
        return ret1;
    }

    public static <T> Vector<T> ofObj(T[] obj) {
        return new Vector<T>(obj);
    }
}
