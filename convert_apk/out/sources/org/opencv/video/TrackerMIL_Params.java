package org.opencv.video;

public class TrackerMIL_Params {
    protected final long nativeObj;

    private static native long TrackerMIL_Params_0();

    private static native void delete(long j);

    private static native int get_featureSetNumFeatures_0(long j);

    private static native float get_samplerInitInRadius_0(long j);

    private static native int get_samplerInitMaxNegNum_0(long j);

    private static native float get_samplerSearchWinSize_0(long j);

    private static native float get_samplerTrackInRadius_0(long j);

    private static native int get_samplerTrackMaxNegNum_0(long j);

    private static native int get_samplerTrackMaxPosNum_0(long j);

    private static native void set_featureSetNumFeatures_0(long j, int i);

    private static native void set_samplerInitInRadius_0(long j, float f);

    private static native void set_samplerInitMaxNegNum_0(long j, int i);

    private static native void set_samplerSearchWinSize_0(long j, float f);

    private static native void set_samplerTrackInRadius_0(long j, float f);

    private static native void set_samplerTrackMaxNegNum_0(long j, int i);

    private static native void set_samplerTrackMaxPosNum_0(long j, int i);

    protected TrackerMIL_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static TrackerMIL_Params __fromPtr__(long addr) {
        return new TrackerMIL_Params(addr);
    }

    public TrackerMIL_Params() {
        this.nativeObj = TrackerMIL_Params_0();
    }

    public float get_samplerInitInRadius() {
        return get_samplerInitInRadius_0(this.nativeObj);
    }

    public void set_samplerInitInRadius(float samplerInitInRadius) {
        set_samplerInitInRadius_0(this.nativeObj, samplerInitInRadius);
    }

    public int get_samplerInitMaxNegNum() {
        return get_samplerInitMaxNegNum_0(this.nativeObj);
    }

    public void set_samplerInitMaxNegNum(int samplerInitMaxNegNum) {
        set_samplerInitMaxNegNum_0(this.nativeObj, samplerInitMaxNegNum);
    }

    public float get_samplerSearchWinSize() {
        return get_samplerSearchWinSize_0(this.nativeObj);
    }

    public void set_samplerSearchWinSize(float samplerSearchWinSize) {
        set_samplerSearchWinSize_0(this.nativeObj, samplerSearchWinSize);
    }

    public float get_samplerTrackInRadius() {
        return get_samplerTrackInRadius_0(this.nativeObj);
    }

    public void set_samplerTrackInRadius(float samplerTrackInRadius) {
        set_samplerTrackInRadius_0(this.nativeObj, samplerTrackInRadius);
    }

    public int get_samplerTrackMaxPosNum() {
        return get_samplerTrackMaxPosNum_0(this.nativeObj);
    }

    public void set_samplerTrackMaxPosNum(int samplerTrackMaxPosNum) {
        set_samplerTrackMaxPosNum_0(this.nativeObj, samplerTrackMaxPosNum);
    }

    public int get_samplerTrackMaxNegNum() {
        return get_samplerTrackMaxNegNum_0(this.nativeObj);
    }

    public void set_samplerTrackMaxNegNum(int samplerTrackMaxNegNum) {
        set_samplerTrackMaxNegNum_0(this.nativeObj, samplerTrackMaxNegNum);
    }

    public int get_featureSetNumFeatures() {
        return get_featureSetNumFeatures_0(this.nativeObj);
    }

    public void set_featureSetNumFeatures(int featureSetNumFeatures) {
        set_featureSetNumFeatures_0(this.nativeObj, featureSetNumFeatures);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
