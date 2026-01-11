package org.opencv.core;

public class Rect {
    public int height;
    public int width;

    /* renamed from: x */
    public int f180x;

    /* renamed from: y */
    public int f181y;

    public Rect(int x, int y, int width2, int height2) {
        this.f180x = x;
        this.f181y = y;
        this.width = width2;
        this.height = height2;
    }

    public Rect() {
        this(0, 0, 0, 0);
    }

    public Rect(Point p1, Point p2) {
        this.f180x = (int) (p1.f175x < p2.f175x ? p1.f175x : p2.f175x);
        this.f181y = (int) (p1.f176y < p2.f176y ? p1.f176y : p2.f176y);
        this.width = ((int) (p1.f175x > p2.f175x ? p1.f175x : p2.f175x)) - this.f180x;
        this.height = ((int) (p1.f176y > p2.f176y ? p1.f176y : p2.f176y)) - this.f181y;
    }

    public Rect(Point p, Size s) {
        this((int) p.f175x, (int) p.f176y, (int) s.width, (int) s.height);
    }

    public Rect(double[] vals) {
        set(vals);
    }

    public void set(double[] vals) {
        int i = 0;
        if (vals != null) {
            this.f180x = vals.length > 0 ? (int) vals[0] : 0;
            this.f181y = vals.length > 1 ? (int) vals[1] : 0;
            this.width = vals.length > 2 ? (int) vals[2] : 0;
            if (vals.length > 3) {
                i = (int) vals[3];
            }
            this.height = i;
            return;
        }
        this.f180x = 0;
        this.f181y = 0;
        this.width = 0;
        this.height = 0;
    }

    public Rect clone() {
        return new Rect(this.f180x, this.f181y, this.width, this.height);
    }

    /* renamed from: tl */
    public Point mo16427tl() {
        return new Point((double) this.f180x, (double) this.f181y);
    }

    /* renamed from: br */
    public Point mo16419br() {
        return new Point((double) (this.f180x + this.width), (double) (this.f181y + this.height));
    }

    public Size size() {
        return new Size((double) this.width, (double) this.height);
    }

    public double area() {
        return (double) (this.width * this.height);
    }

    public boolean empty() {
        return this.width <= 0 || this.height <= 0;
    }

    public boolean contains(Point p) {
        return ((double) this.f180x) <= p.f175x && p.f175x < ((double) (this.f180x + this.width)) && ((double) this.f181y) <= p.f176y && p.f176y < ((double) (this.f181y + this.height));
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits((double) this.height);
        long temp2 = Double.doubleToLongBits((double) this.width);
        int result = (((1 * 31) + ((int) ((temp >>> 32) ^ temp))) * 31) + ((int) ((temp2 >>> 32) ^ temp2));
        long temp3 = Double.doubleToLongBits((double) this.f180x);
        int result2 = (result * 31) + ((int) ((temp3 >>> 32) ^ temp3));
        long temp4 = Double.doubleToLongBits((double) this.f181y);
        return (result2 * 31) + ((int) ((temp4 >>> 32) ^ temp4));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Rect)) {
            return false;
        }
        Rect it = (Rect) obj;
        if (this.f180x == it.f180x && this.f181y == it.f181y && this.width == it.width && this.height == it.height) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "{" + this.f180x + ", " + this.f181y + ", " + this.width + "x" + this.height + "}";
    }
}
