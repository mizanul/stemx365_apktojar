package org.opencv.calib3d;

public class UsacParams {
    protected final long nativeObj;

    private static native long UsacParams_0();

    private static native void delete(long j);

    private static native double get_confidence_0(long j);

    private static native boolean get_isParallel_0(long j);

    private static native int get_loIterations_0(long j);

    private static native int get_loMethod_0(long j);

    private static native int get_loSampleSize_0(long j);

    private static native int get_maxIterations_0(long j);

    private static native int get_neighborsSearch_0(long j);

    private static native int get_randomGeneratorState_0(long j);

    private static native int get_sampler_0(long j);

    private static native int get_score_0(long j);

    private static native double get_threshold_0(long j);

    private static native void set_confidence_0(long j, double d);

    private static native void set_isParallel_0(long j, boolean z);

    private static native void set_loIterations_0(long j, int i);

    private static native void set_loMethod_0(long j, int i);

    private static native void set_loSampleSize_0(long j, int i);

    private static native void set_maxIterations_0(long j, int i);

    private static native void set_neighborsSearch_0(long j, int i);

    private static native void set_randomGeneratorState_0(long j, int i);

    private static native void set_sampler_0(long j, int i);

    private static native void set_score_0(long j, int i);

    private static native void set_threshold_0(long j, double d);

    protected UsacParams(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static UsacParams __fromPtr__(long addr) {
        return new UsacParams(addr);
    }

    public UsacParams() {
        this.nativeObj = UsacParams_0();
    }

    public double get_confidence() {
        return get_confidence_0(this.nativeObj);
    }

    public void set_confidence(double confidence) {
        set_confidence_0(this.nativeObj, confidence);
    }

    public boolean get_isParallel() {
        return get_isParallel_0(this.nativeObj);
    }

    public void set_isParallel(boolean isParallel) {
        set_isParallel_0(this.nativeObj, isParallel);
    }

    public int get_loIterations() {
        return get_loIterations_0(this.nativeObj);
    }

    public void set_loIterations(int loIterations) {
        set_loIterations_0(this.nativeObj, loIterations);
    }

    public int get_loMethod() {
        return get_loMethod_0(this.nativeObj);
    }

    public void set_loMethod(int loMethod) {
        set_loMethod_0(this.nativeObj, loMethod);
    }

    public int get_loSampleSize() {
        return get_loSampleSize_0(this.nativeObj);
    }

    public void set_loSampleSize(int loSampleSize) {
        set_loSampleSize_0(this.nativeObj, loSampleSize);
    }

    public int get_maxIterations() {
        return get_maxIterations_0(this.nativeObj);
    }

    public void set_maxIterations(int maxIterations) {
        set_maxIterations_0(this.nativeObj, maxIterations);
    }

    public int get_neighborsSearch() {
        return get_neighborsSearch_0(this.nativeObj);
    }

    public void set_neighborsSearch(int neighborsSearch) {
        set_neighborsSearch_0(this.nativeObj, neighborsSearch);
    }

    public int get_randomGeneratorState() {
        return get_randomGeneratorState_0(this.nativeObj);
    }

    public void set_randomGeneratorState(int randomGeneratorState) {
        set_randomGeneratorState_0(this.nativeObj, randomGeneratorState);
    }

    public int get_sampler() {
        return get_sampler_0(this.nativeObj);
    }

    public void set_sampler(int sampler) {
        set_sampler_0(this.nativeObj, sampler);
    }

    public int get_score() {
        return get_score_0(this.nativeObj);
    }

    public void set_score(int score) {
        set_score_0(this.nativeObj, score);
    }

    public double get_threshold() {
        return get_threshold_0(this.nativeObj);
    }

    public void set_threshold(double threshold) {
        set_threshold_0(this.nativeObj, threshold);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
