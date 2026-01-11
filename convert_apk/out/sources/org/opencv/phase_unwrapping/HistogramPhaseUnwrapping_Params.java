package org.opencv.phase_unwrapping;

public class HistogramPhaseUnwrapping_Params {
    protected final long nativeObj;

    private static native long HistogramPhaseUnwrapping_Params_0();

    private static native void delete(long j);

    private static native int get_height_0(long j);

    private static native float get_histThresh_0(long j);

    private static native int get_nbrOfLargeBins_0(long j);

    private static native int get_nbrOfSmallBins_0(long j);

    private static native int get_width_0(long j);

    private static native void set_height_0(long j, int i);

    private static native void set_histThresh_0(long j, float f);

    private static native void set_nbrOfLargeBins_0(long j, int i);

    private static native void set_nbrOfSmallBins_0(long j, int i);

    private static native void set_width_0(long j, int i);

    protected HistogramPhaseUnwrapping_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static HistogramPhaseUnwrapping_Params __fromPtr__(long addr) {
        return new HistogramPhaseUnwrapping_Params(addr);
    }

    public HistogramPhaseUnwrapping_Params() {
        this.nativeObj = HistogramPhaseUnwrapping_Params_0();
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

    public float get_histThresh() {
        return get_histThresh_0(this.nativeObj);
    }

    public void set_histThresh(float histThresh) {
        set_histThresh_0(this.nativeObj, histThresh);
    }

    public int get_nbrOfSmallBins() {
        return get_nbrOfSmallBins_0(this.nativeObj);
    }

    public void set_nbrOfSmallBins(int nbrOfSmallBins) {
        set_nbrOfSmallBins_0(this.nativeObj, nbrOfSmallBins);
    }

    public int get_nbrOfLargeBins() {
        return get_nbrOfLargeBins_0(this.nativeObj);
    }

    public void set_nbrOfLargeBins(int nbrOfLargeBins) {
        set_nbrOfLargeBins_0(this.nativeObj, nbrOfLargeBins);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
