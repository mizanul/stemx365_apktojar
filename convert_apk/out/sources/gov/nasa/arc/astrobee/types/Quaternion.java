package gov.nasa.arc.astrobee.types;

import java.text.DecimalFormat;

public class Quaternion extends Mat33f {
    private static final DecimalFormat s_decimalFormatter = new DecimalFormat("#.###");

    public Quaternion() {
        this(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Quaternion(float x, float y, float z, float w) {
        super(new float[]{x, y, z, w, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f});
    }

    public float getX() {
        return this.m_vec[0];
    }

    public float getY() {
        return this.m_vec[1];
    }

    public float getZ() {
        return this.m_vec[2];
    }

    public float getW() {
        return this.m_vec[3];
    }

    public String toString() {
        return "Mat33f::Quaternion{ x=" + s_decimalFormatter.format((double) this.m_vec[0]) + "; y=" + s_decimalFormatter.format((double) this.m_vec[1]) + "; z=" + s_decimalFormatter.format((double) this.m_vec[2]) + "; w=" + s_decimalFormatter.format((double) this.m_vec[3]) + "}";
    }
}
