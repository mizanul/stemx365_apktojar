package gov.nasa.arc.astrobee.types;

import java.text.DecimalFormat;

public class Vec3d {
    private static final DecimalFormat s_decimalFormatter = new DecimalFormat("#.###");
    protected final double[] m_vec;

    public Vec3d() {
        this(0.0d, 0.0d, 0.0d);
    }

    public Vec3d(double x, double y, double z) {
        this.m_vec = new double[]{x, y, z};
    }

    public Vec3d(double[] vec) {
        if (vec == null) {
            throw new NullPointerException("vec must not be null");
        } else if (vec.length == 3) {
            this.m_vec = (double[]) vec.clone();
        } else {
            throw new IllegalArgumentException("array must be of size 3");
        }
    }

    public double[] toArray() {
        return (double[]) this.m_vec.clone();
    }

    public String toString() {
        String clzName = getClass().getName();
        return clzName + "[" + s_decimalFormatter.format(this.m_vec[0]) + ", " + s_decimalFormatter.format(this.m_vec[1]) + ", " + s_decimalFormatter.format(this.m_vec[2]) + "]";
    }
}
