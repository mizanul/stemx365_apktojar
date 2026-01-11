package gov.nasa.arc.astrobee.types;

public class Mat33f {
    protected float[] m_vec;

    public Mat33f(float[] vec) {
        if (vec == null) {
            throw new NullPointerException("vec may not be null");
        } else if (vec.length == 9) {
            this.m_vec = (float[]) vec.clone();
        } else {
            throw new IllegalArgumentException("vec must be length 9");
        }
    }

    public float[] toArray() {
        return (float[]) this.m_vec.clone();
    }
}
