package org.opencv.xfeatures2d;

public class TBMR extends AffineFeature2D {
    private static native long create_0(int i, float f, float f2, int i2);

    private static native long create_1(int i, float f, float f2);

    private static native long create_2(int i, float f);

    private static native long create_3(int i);

    private static native long create_4();

    private static native void delete(long j);

    private static native float getMaxAreaRelative_0(long j);

    private static native int getMinArea_0(long j);

    private static native int getNScales_0(long j);

    private static native float getScaleFactor_0(long j);

    private static native void setMaxAreaRelative_0(long j, float f);

    private static native void setMinArea_0(long j, int i);

    private static native void setNScales_0(long j, int i);

    private static native void setScaleFactor_0(long j, float f);

    protected TBMR(long addr) {
        super(addr);
    }

    public static TBMR __fromPtr__(long addr) {
        return new TBMR(addr);
    }

    public static TBMR create(int min_area, float max_area_relative, float scale_factor, int n_scales) {
        return __fromPtr__(create_0(min_area, max_area_relative, scale_factor, n_scales));
    }

    public static TBMR create(int min_area, float max_area_relative, float scale_factor) {
        return __fromPtr__(create_1(min_area, max_area_relative, scale_factor));
    }

    public static TBMR create(int min_area, float max_area_relative) {
        return __fromPtr__(create_2(min_area, max_area_relative));
    }

    public static TBMR create(int min_area) {
        return __fromPtr__(create_3(min_area));
    }

    public static TBMR create() {
        return __fromPtr__(create_4());
    }

    public void setMinArea(int minArea) {
        setMinArea_0(this.nativeObj, minArea);
    }

    public int getMinArea() {
        return getMinArea_0(this.nativeObj);
    }

    public void setMaxAreaRelative(float maxArea) {
        setMaxAreaRelative_0(this.nativeObj, maxArea);
    }

    public float getMaxAreaRelative() {
        return getMaxAreaRelative_0(this.nativeObj);
    }

    public void setScaleFactor(float scale_factor) {
        setScaleFactor_0(this.nativeObj, scale_factor);
    }

    public float getScaleFactor() {
        return getScaleFactor_0(this.nativeObj);
    }

    public void setNScales(int n_scales) {
        setNScales_0(this.nativeObj, n_scales);
    }

    public int getNScales() {
        return getNScales_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
