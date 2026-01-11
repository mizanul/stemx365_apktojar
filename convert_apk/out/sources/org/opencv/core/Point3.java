package org.opencv.core;

public class Point3 {

    /* renamed from: x */
    public double f177x;

    /* renamed from: y */
    public double f178y;

    /* renamed from: z */
    public double f179z;

    public Point3(double x, double y, double z) {
        this.f177x = x;
        this.f178y = y;
        this.f179z = z;
    }

    public Point3() {
        this(0.0d, 0.0d, 0.0d);
    }

    public Point3(Point p) {
        this.f177x = p.f175x;
        this.f178y = p.f176y;
        this.f179z = 0.0d;
    }

    public Point3(double[] vals) {
        this();
        set(vals);
    }

    public void set(double[] vals) {
        double d = 0.0d;
        if (vals != null) {
            this.f177x = vals.length > 0 ? vals[0] : 0.0d;
            this.f178y = vals.length > 1 ? vals[1] : 0.0d;
            if (vals.length > 2) {
                d = vals[2];
            }
            this.f179z = d;
            return;
        }
        this.f177x = 0.0d;
        this.f178y = 0.0d;
        this.f179z = 0.0d;
    }

    public Point3 clone() {
        return new Point3(this.f177x, this.f178y, this.f179z);
    }

    public double dot(Point3 p) {
        return (this.f177x * p.f177x) + (this.f178y * p.f178y) + (this.f179z * p.f179z);
    }

    public Point3 cross(Point3 p) {
        Point3 point3 = p;
        double d = this.f178y;
        double d2 = point3.f179z;
        double d3 = this.f179z;
        double d4 = point3.f178y;
        double d5 = point3.f177x;
        double d6 = (d * d2) - (d3 * d4);
        double d7 = this.f177x;
        return new Point3(d6, (d3 * d5) - (d2 * d7), (d7 * d4) - (d * d5));
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.f177x);
        long temp2 = Double.doubleToLongBits(this.f178y);
        int result = (((1 * 31) + ((int) ((temp >>> 32) ^ temp))) * 31) + ((int) ((temp2 >>> 32) ^ temp2));
        long temp3 = Double.doubleToLongBits(this.f179z);
        return (result * 31) + ((int) ((temp3 >>> 32) ^ temp3));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point3)) {
            return false;
        }
        Point3 it = (Point3) obj;
        if (this.f177x == it.f177x && this.f178y == it.f178y && this.f179z == it.f179z) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "{" + this.f177x + ", " + this.f178y + ", " + this.f179z + "}";
    }
}
