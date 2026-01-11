package org.opencv.core;

public class Rect2d {
    public double height;
    public double width;

    /* renamed from: x */
    public double f182x;

    /* renamed from: y */
    public double f183y;

    public Rect2d(double x, double y, double width2, double height2) {
        this.f182x = x;
        this.f183y = y;
        this.width = width2;
        this.height = height2;
    }

    public Rect2d() {
        this(0.0d, 0.0d, 0.0d, 0.0d);
    }

    public Rect2d(Point p1, Point p2) {
        this.f182x = p1.f175x < p2.f175x ? p1.f175x : p2.f175x;
        this.f183y = p1.f176y < p2.f176y ? p1.f176y : p2.f176y;
        this.width = (p1.f175x > p2.f175x ? p1.f175x : p2.f175x) - this.f182x;
        this.height = (p1.f176y > p2.f176y ? p1.f176y : p2.f176y) - this.f183y;
    }

    public Rect2d(Point p, Size s) {
        this(p.f175x, p.f176y, s.width, s.height);
    }

    public Rect2d(double[] vals) {
        set(vals);
    }

    public void set(double[] vals) {
        double d = 0.0d;
        if (vals != null) {
            this.f182x = vals.length > 0 ? vals[0] : 0.0d;
            this.f183y = vals.length > 1 ? vals[1] : 0.0d;
            this.width = vals.length > 2 ? vals[2] : 0.0d;
            if (vals.length > 3) {
                d = vals[3];
            }
            this.height = d;
            return;
        }
        this.f182x = 0.0d;
        this.f183y = 0.0d;
        this.width = 0.0d;
        this.height = 0.0d;
    }

    public Rect2d clone() {
        return new Rect2d(this.f182x, this.f183y, this.width, this.height);
    }

    /* renamed from: tl */
    public Point mo16438tl() {
        return new Point(this.f182x, this.f183y);
    }

    /* renamed from: br */
    public Point mo16430br() {
        return new Point(this.f182x + this.width, this.f183y + this.height);
    }

    public Size size() {
        return new Size(this.width, this.height);
    }

    public double area() {
        return this.width * this.height;
    }

    public boolean empty() {
        return this.width <= 0.0d || this.height <= 0.0d;
    }

    public boolean contains(Point p) {
        return this.f182x <= p.f175x && p.f175x < this.f182x + this.width && this.f183y <= p.f176y && p.f176y < this.f183y + this.height;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.height);
        long temp2 = Double.doubleToLongBits(this.width);
        int result = (((1 * 31) + ((int) ((temp >>> 32) ^ temp))) * 31) + ((int) ((temp2 >>> 32) ^ temp2));
        long temp3 = Double.doubleToLongBits(this.f182x);
        int result2 = (result * 31) + ((int) ((temp3 >>> 32) ^ temp3));
        long temp4 = Double.doubleToLongBits(this.f183y);
        return (result2 * 31) + ((int) ((temp4 >>> 32) ^ temp4));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Rect2d)) {
            return false;
        }
        Rect2d it = (Rect2d) obj;
        if (this.f182x == it.f182x && this.f183y == it.f183y && this.width == it.width && this.height == it.height) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "{" + this.f182x + ", " + this.f183y + ", " + this.width + "x" + this.height + "}";
    }
}
