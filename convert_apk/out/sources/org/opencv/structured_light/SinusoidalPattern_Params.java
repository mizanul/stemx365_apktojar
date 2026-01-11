package org.opencv.structured_light;

public class SinusoidalPattern_Params {
    protected final long nativeObj;

    private static native long SinusoidalPattern_Params_0();

    private static native void delete(long j);

    private static native int get_height_0(long j);

    private static native boolean get_horizontal_0(long j);

    private static native int get_methodId_0(long j);

    private static native int get_nbrOfPeriods_0(long j);

    private static native int get_nbrOfPixelsBetweenMarkers_0(long j);

    private static native boolean get_setMarkers_0(long j);

    private static native float get_shiftValue_0(long j);

    private static native int get_width_0(long j);

    private static native void set_height_0(long j, int i);

    private static native void set_horizontal_0(long j, boolean z);

    private static native void set_methodId_0(long j, int i);

    private static native void set_nbrOfPeriods_0(long j, int i);

    private static native void set_nbrOfPixelsBetweenMarkers_0(long j, int i);

    private static native void set_setMarkers_0(long j, boolean z);

    private static native void set_shiftValue_0(long j, float f);

    private static native void set_width_0(long j, int i);

    protected SinusoidalPattern_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static SinusoidalPattern_Params __fromPtr__(long addr) {
        return new SinusoidalPattern_Params(addr);
    }

    public SinusoidalPattern_Params() {
        this.nativeObj = SinusoidalPattern_Params_0();
    }

    public int get_width() {
        return get_width_0(this.nativeObj);
    }

    public void set_width(int width) {
        set_width_0(this.nativeObj, width);
    }

    public int get_height() {
        return get_height_0(this.nativeObj);
    }

    public void set_height(int height) {
        set_height_0(this.nativeObj, height);
    }

    public int get_nbrOfPeriods() {
        return get_nbrOfPeriods_0(this.nativeObj);
    }

    public void set_nbrOfPeriods(int nbrOfPeriods) {
        set_nbrOfPeriods_0(this.nativeObj, nbrOfPeriods);
    }

    public float get_shiftValue() {
        return get_shiftValue_0(this.nativeObj);
    }

    public void set_shiftValue(float shiftValue) {
        set_shiftValue_0(this.nativeObj, shiftValue);
    }

    public int get_methodId() {
        return get_methodId_0(this.nativeObj);
    }

    public void set_methodId(int methodId) {
        set_methodId_0(this.nativeObj, methodId);
    }

    public int get_nbrOfPixelsBetweenMarkers() {
        return get_nbrOfPixelsBetweenMarkers_0(this.nativeObj);
    }

    public void set_nbrOfPixelsBetweenMarkers(int nbrOfPixelsBetweenMarkers) {
        set_nbrOfPixelsBetweenMarkers_0(this.nativeObj, nbrOfPixelsBetweenMarkers);
    }

    public boolean get_horizontal() {
        return get_horizontal_0(this.nativeObj);
    }

    public void set_horizontal(boolean horizontal) {
        set_horizontal_0(this.nativeObj, horizontal);
    }

    public boolean get_setMarkers() {
        return get_setMarkers_0(this.nativeObj);
    }

    public void set_setMarkers(boolean setMarkers) {
        set_setMarkers_0(this.nativeObj, setMarkers);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
