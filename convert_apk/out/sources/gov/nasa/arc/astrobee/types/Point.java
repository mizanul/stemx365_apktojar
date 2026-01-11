package gov.nasa.arc.astrobee.types;

public class Point extends Vec3d {
    public Point() {
        this(0.0d, 0.0d, 0.0d);
    }

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    public double getX() {
        return this.m_vec[0];
    }

    public double getY() {
        return this.m_vec[1];
    }

    public double getZ() {
        return this.m_vec[2];
    }
}
