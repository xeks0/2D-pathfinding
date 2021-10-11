package org.xeks;

import org.xeks.data.SortArgInterface;

import java.util.function.Function;

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

    public T get(int idx) {
        if (((idx >= this.array.length) || (idx < 0))) {
            return null;
        }
        return this.array[idx];
    }

    public final T set(int idx, T v) {
        if ((idx >= this.array.length)) {
            int i = (idx + 1);
            if ((idx == this.array.length)) {
                i = (((idx << 1)) + 1);
            }
            T[] ts = ((T[]) new Object[i]);
            if ((this.length > 0)) {
                arraycopy(this.array, 0, ts, 0, this.length);
            }
            this.array = ts;
        }
        if ((idx >= this.length)) {
            this.length = (idx + 1);
        }
        return this.array[idx] = v;
    }

    public final void unshift(T x) {
        int length = this.length;
        if ((length >= this.array.length)) {
            int i = (((length << 1)) + 1);
            T[] ts = ((T[]) new Object[i]);
            arraycopy(this.array, 0, ts, 1, length);
            this.array = ts;
        } else {
            arraycopy(this.array, 0, this.array, 1, length);
        }
        this.array[0] = x;
        ++this.length;
    }

    public final Vector<T> slice(int pos, java.lang.Object end) {
        if ((pos < 0)) {

            pos = (this.length + pos);
            if ((pos < 0)) {
                pos = 0;
            }
        }
        if (eq(end, null)) {
            end = this.length;
        } else {
            if ((compare(end, 0) < 0)) {
                end = ((int) (toInt(plus(this.length, end))));
            }
        }
        if ((compare(end, this.length) > 0)) {
            end = this.length;
        }

        int len = (((int) (toInt(end))) - pos);
        if ((len < 0)) {
            return new Vector<>();
        }
        T[] ts = ((T[]) new Object[len]);
        java.lang.System.arraycopy(this.array, pos, ts, 0, len);
        return ofObj(ts);
    }

    public static int compare(java.lang.Object v1, java.lang.Object v2) {

        if (v1 == v2)
            return 0;
        if (v1 == null) return -1;
        if (v2 == null) return 1;

        if (v1 instanceof java.lang.Number || v2 instanceof java.lang.Number) {
            assert v1 instanceof Number;
            java.lang.Number v1c = (java.lang.Number) v1;
            java.lang.Number v2c = (java.lang.Number) v2;

            if (v1 instanceof java.lang.Long || v2 instanceof java.lang.Long) {
                long l1 = v1c.longValue();
                long l2 = v2c.longValue();
                return Long.compare(l1, l2);
            } else {
                double d1 = v1c.doubleValue();
                double d2 = v2c.doubleValue();

                return Double.compare(d1, d2);
            }
        }
        //if it's not a number it must be a String
        return ((String) v1).compareTo((String) v2);
    }

    public static Object plus(java.lang.Object v1, java.lang.Object v2) {
        if (v1 instanceof String || v2 instanceof java.lang.String)
            return String.valueOf(v1) + String.valueOf(v2);

        if (v1 instanceof java.lang.Number || v2 instanceof java.lang.Number) {
            java.lang.Number v1c = (java.lang.Number) v1;
            java.lang.Number v2c = (java.lang.Number) v2;

            double d1 = (v1 == null) ? 0.0 : v1c.doubleValue();
            double d2 = (v2 == null) ? 0.0 : v2c.doubleValue();

            return d1 + d2;
        }
        throw new java.lang.IllegalArgumentException("Cannot dynamically add " + v1 + " and " + v2);

    }

    public final void insert(int pos, T x) {
        int l = this.length;
        if ((pos < 0)) {
            pos = (l + pos);
            if ((pos < 0)) {
                pos = 0;
            }
        }
        if ((pos >= l)) {
            this.push(x);
            return;
        } else {
            if ((pos == 0)) {
                this.unshift(x);
                return;
            }
        }
        if ((l >= this.array.length)) {
            int i = (((this.length << 1)) + 1);
            T[] ts = ((T[]) new Object[i]);
            arraycopy(this.array, 0, ts, 0, pos);
            ts[pos] = x;
            arraycopy(this.array, pos, ts, (pos + 1), (l - pos));
            this.array = ts;
        } else {
            arraycopy(this.array, pos, this.array, (pos + 1), (l - pos));
            arraycopy(this.array, 0, this.array, 0, pos);
            this.array[pos] = x;
        }
        ++this.length;
    }

    public final T shift() {
        int l = this.length;
        if ((l == 0)) {
            return null;
        }
        T x = this.array[0];
        --l;
        arraycopy(this.array, 1, this.array, 0, (this.length - 1));
        this.array[l] = null;
        this.length = l;
        return x;
    }

    public final void sort(SortArgInterface function) {
        if ((this.length == 0)) {
            return;
        }
        this.quicksort(function, 0, (this.length - 1));
    }

    public final void quicksort(SortArgInterface function, int lo, int hi) {
        T[] buf = this.array;
        int i = lo;
        int j = hi;
        int p = (i + j) >> 1;
        while ((i <= j)) {
            while (((i < hi) && (function.operation(i, p) < 0))) {
                ++i;
            }
            while (((j > lo) && (function.operation(j, p) > 0))) {
                --j;
            }
            if ((i <= j)) {
                T t = buf[i];
                buf[i++] = buf[j];
                buf[j--] = t;
            }
        }
        if ((lo < j)) {
            this.quicksort(function, lo, j);
        }

        if ((i < hi)) {
            this.quicksort(function, i, hi);
        }
    }


}
