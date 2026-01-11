package org.opencv.xfeatures2d;

public class SURF_CUDA {
    public static final int ANGLE_ROW = 5;
    public static final int HESSIAN_ROW = 6;
    public static final int LAPLACIAN_ROW = 2;
    public static final int OCTAVE_ROW = 3;
    public static final int ROWS_COUNT = 7;
    public static final int SIZE_ROW = 4;
    public static final int X_ROW = 0;
    public static final int Y_ROW = 1;
    protected final long nativeObj;

    private static native long create_0(double d, int i, int i2, boolean z, float f, boolean z2);

    private static native long create_1(double d, int i, int i2, boolean z, float f);

    private static native long create_2(double d, int i, int i2, boolean z);

    private static native long create_3(double d, int i, int i2);

    private static native long create_4(double d, int i);

    private static native long create_5(double d);

    private static native int defaultNorm_0(long j);

    private static native void delete(long j);

    private static native int descriptorSize_0(long j);

    private static native boolean get_extended_0(long j);

    private static native double get_hessianThreshold_0(long j);

    private static native float get_keypointsRatio_0(long j);

    private static native int get_nOctaveLayers_0(long j);

    private static native int get_nOctaves_0(long j);

    private static native boolean get_upright_0(long j);

    protected SURF_CUDA(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static SURF_CUDA __fromPtr__(long addr) {
        return new SURF_CUDA(addr);
    }

    public static SURF_CUDA create(double _hessianThreshold, int _nOctaves, int _nOctaveLayers, boolean _extended, float _keypointsRatio, boolean _upright) {
        return __fromPtr__(create_0(_hessianThreshold, _nOctaves, _nOctaveLayers, _extended, _keypointsRatio, _upright));
    }

    public static SURF_CUDA create(double _hessianThreshold, int _nOctaves, int _nOctaveLayers, boolean _extended, float _keypointsRatio) {
        return __fromPtr__(create_1(_hessianThreshold, _nOctaves, _nOctaveLayers, _extended, _keypointsRatio));
    }

    public static SURF_CUDA create(double _hessianThreshold, int _nOctaves, int _nOctaveLayers, boolean _extended) {
        return __fromPtr__(create_2(_hessianThreshold, _nOctaves, _nOctaveLayers, _extended));
    }

    public static SURF_CUDA create(double _hessianThreshold, int _nOctaves, int _nOctaveLayers) {
        return __fromPtr__(create_3(_hessianThreshold, _nOctaves, _nOctaveLayers));
    }

    public static SURF_CUDA create(double _hessianThreshold, int _nOctaves) {
        return __fromPtr__(create_4(_hessianThreshold, _nOctaves));
    }

    public static SURF_CUDA create(double _hessianThreshold) {
        return __fromPtr__(create_5(_hessianThreshold));
    }

    public int descriptorSize() {
        return descriptorSize_0(this.nativeObj);
    }

    public int defaultNorm() {
        return defaultNorm_0(this.nativeObj);
    }

    public double get_hessianThreshold() {
        return get_hessianThreshold_0(this.nativeObj);
    }

    public int get_nOctaves() {
        return get_nOctaves_0(this.nativeObj);
    }

    public int get_nOctaveLayers() {
        return get_nOctaveLayers_0(this.nativeObj);
    }

    public boolean get_extended() {
        return get_extended_0(this.nativeObj);
    }

    public boolean get_upright() {
        return get_upright_0(this.nativeObj);
    }

    public float get_keypointsRatio() {
        return get_keypointsRatio_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
