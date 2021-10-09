package org.xeks;

import static java.lang.System.arraycopy;

public class Vector<T> {

    public int length;
    public T[] array;

    public Vector(T[] obj) {
        this.array = obj;
        this.length = obj.length;
    }

    public Vector() {
        this.length = 0;
        this.array = ((T[]) new Object[0]);
    }

    public final Vector<T> splice(int pos, int len) {
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
        T[] a = this.array;
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

    public final int indexOf(T x, java.lang.Object fromIndex) {
        int i = ((eq(fromIndex, null)) ? (0) : (((int) (toInt(fromIndex)))));
        if ((i < 0)) {
            i += this.length;
            if ((i < 0)) {
                i = 0;
            }
        }
        while ((i < this.length)) {
            if (eq(this.array[i], x)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    private Object toInt(Object obj) {
        return (obj == null) ? 0 : ((java.lang.Number) obj).intValue();
    }

    public static boolean eq(java.lang.Object j, java.lang.Object k) {

        if (j == k)
            return true;
        if (j == null || k == null)
            return false;

        if (j instanceof java.lang.Number) {
            if (!(k instanceof java.lang.Number))
                return false;
            java.lang.Number _u = (java.lang.Number) j;
            java.lang.Number _s = (java.lang.Number) k;
            if (j instanceof java.lang.Long || k instanceof java.lang.Long)
                return _u.longValue() == _s.longValue();
            return _u.doubleValue() == _s.doubleValue();
        } else {
            return j.equals(k);
        }
    }

    public final int push(T x) {
        if ((this.length >= this.array.length)) {
            int len = (((this.length << 1)) + 1);
            T[] ts = ((T[]) new Object[len]);
            arraycopy(this.array, 0, ts, 0, this.array.length);
            this.array = ts;
        }
        this.array[this.length] = x;
        return ++this.length;
    }

    public final T pop() {
        if ((this.length > 0)) {
            T val = this.array[--this.length];
            this.array[this.length] = null;
            return val;
        } else {
            return null;
        }
    }

    public final String join(String str) {
        StringBuilder buf = new StringBuilder();
        int i = -1;
        boolean first = true;
        int length = this.length;
        while ((++i < length)) {
            if (first) {
                first = false;
            } else {
                buf.append(str);
            }
            buf.append(((T) (this.array[i])));
        }
        return buf.toString();
    }
}
