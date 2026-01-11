package org.opencv.core;

public class Point {

    /* renamed from: x */
    public double f175x;

    /* renamed from: y */
    public double f176y;

    public Point(double x, double y) {
        this.f175x = x;
        this.f176y = y;
    }

    public Point() {
        this(0.0d, 0.0d);
    }

    public Point(double[] vals) {
        this();
        set(vals);
    }

    public void set(double[] vals) {
        double d = 0.0d;
        if (vals != null) {
            this.f175x = vals.length > 0 ? vals[0] : 0.0d;
            if (vals.length > 1) {
                d = vals[1];
            }
            this.f176y = d;
            return;
        }
        this.f175x = 0.0d;
        this.f176y = 0.0d;
    }

    public Point clone() {
        return new Point(this.f175x, this.f176y);
    }

    public double dot(Point p) {
        return (this.f175x * p.f175x) + (this.f176y * p.f176y);
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.f175x);
        int result = (1 * 31) + ((int) ((temp >>> 32) ^ temp));
        long temp2 = Double.doubleToLongBits(this.f176y);
        return (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point it = (Point) obj;
        if (this.f175x == it.f175x && this.f176y == it.f176y) {
            return true;
        }
        return false;
    }

    public boolean inside(Rect r) {
        return r.contains(this);
    }

    public String toString() {
        return "{" + this.f175x + ", " + this.f176y + "}";
    }
}
