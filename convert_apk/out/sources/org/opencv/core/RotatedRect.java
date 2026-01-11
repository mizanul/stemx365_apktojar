package org.opencv.core;

public class RotatedRect {
    public double angle;
    public Point center;
    public Size size;

    public RotatedRect() {
        this.center = new Point();
        this.size = new Size();
        this.angle = 0.0d;
    }

    public RotatedRect(Point c, Size s, double a) {
        this.center = c.clone();
        this.size = s.clone();
        this.angle = a;
    }

    public RotatedRect(double[] vals) {
        this();
        set(vals);
    }

    public void set(double[] vals) {
        double d = 0.0d;
        if (vals != null) {
            this.center.f175x = vals.length > 0 ? vals[0] : 0.0d;
            this.center.f176y = vals.length > 1 ? vals[1] : 0.0d;
            this.size.width = vals.length > 2 ? vals[2] : 0.0d;
            this.size.height = vals.length > 3 ? vals[3] : 0.0d;
            if (vals.length > 4) {
                d = vals[4];
            }
            this.angle = d;
            return;
        }
        this.center.f175x = 0.0d;
        this.center.f176y = 0.0d;
        this.size.width = 0.0d;
        this.size.height = 0.0d;
        this.angle = 0.0d;
    }

    public void points(Point[] pt) {
        double _angle = (this.angle * 3.141592653589793d) / 180.0d;
        double b = Math.cos(_angle) * 0.5d;
        double a = Math.sin(_angle) * 0.5d;
        pt[0] = new Point((this.center.f175x - (this.size.height * a)) - (this.size.width * b), (this.center.f176y + (this.size.height * b)) - (this.size.width * a));
        pt[1] = new Point((this.center.f175x + (this.size.height * a)) - (this.size.width * b), (this.center.f176y - (this.size.height * b)) - (this.size.width * a));
        pt[2] = new Point((this.center.f175x * 2.0d) - pt[0].f175x, (this.center.f176y * 2.0d) - pt[0].f176y);
        pt[3] = new Point((this.center.f175x * 2.0d) - pt[1].f175x, (this.center.f176y * 2.0d) - pt[1].f176y);
    }

    public Rect boundingRect() {
        Point[] pt = new Point[4];
        points(pt);
        Rect r = new Rect((int) Math.floor(Math.min(Math.min(Math.min(pt[0].f175x, pt[1].f175x), pt[2].f175x), pt[3].f175x)), (int) Math.floor(Math.min(Math.min(Math.min(pt[0].f176y, pt[1].f176y), pt[2].f176y), pt[3].f176y)), (int) Math.ceil(Math.max(Math.max(Math.max(pt[0].f175x, pt[1].f175x), pt[2].f175x), pt[3].f175x)), (int) Math.ceil(Math.max(Math.max(Math.max(pt[0].f176y, pt[1].f176y), pt[2].f176y), pt[3].f176y)));
        r.width -= r.f180x - 1;
        r.height -= r.f181y - 1;
        return r;
    }

    public RotatedRect clone() {
        return new RotatedRect(this.center, this.size, this.angle);
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.center.f175x);
        long temp2 = Double.doubleToLongBits(this.center.f176y);
        int result = (((1 * 31) + ((int) ((temp >>> 32) ^ temp))) * 31) + ((int) ((temp2 >>> 32) ^ temp2));
        long temp3 = Double.doubleToLongBits(this.size.width);
        int result2 = (result * 31) + ((int) ((temp3 >>> 32) ^ temp3));
        long temp4 = Double.doubleToLongBits(this.size.height);
        int result3 = (result2 * 31) + ((int) ((temp4 >>> 32) ^ temp4));
        long temp5 = Double.doubleToLongBits(this.angle);
        return (result3 * 31) + ((int) ((temp5 >>> 32) ^ temp5));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RotatedRect)) {
            return false;
        }
        RotatedRect it = (RotatedRect) obj;
        if (!this.center.equals(it.center) || !this.size.equals(it.size) || this.angle != it.angle) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "{ " + this.center + " " + this.size + " * " + this.angle + " }";
    }
}
