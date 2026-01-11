package org.opencv.p011ml;

import org.opencv.core.Mat;

/* renamed from: org.opencv.ml.DTrees */
public class DTrees extends StatModel {
    public static final int PREDICT_AUTO = 0;
    public static final int PREDICT_MASK = 768;
    public static final int PREDICT_MAX_VOTE = 512;
    public static final int PREDICT_SUM = 256;

    private static native long create_0();

    private static native void delete(long j);

    private static native int getCVFolds_0(long j);

    private static native int getMaxCategories_0(long j);

    private static native int getMaxDepth_0(long j);

    private static native int getMinSampleCount_0(long j);

    private static native long getPriors_0(long j);

    private static native float getRegressionAccuracy_0(long j);

    private static native boolean getTruncatePrunedTree_0(long j);

    private static native boolean getUse1SERule_0(long j);

    private static native boolean getUseSurrogates_0(long j);

    private static native long load_0(String str, String str2);

    private static native long load_1(String str);

    private static native void setCVFolds_0(long j, int i);

    private static native void setMaxCategories_0(long j, int i);

    private static native void setMaxDepth_0(long j, int i);

    private static native void setMinSampleCount_0(long j, int i);

    private static native void setPriors_0(long j, long j2);

    private static native void setRegressionAccuracy_0(long j, float f);

    private static native void setTruncatePrunedTree_0(long j, boolean z);

    private static native void setUse1SERule_0(long j, boolean z);

    private static native void setUseSurrogates_0(long j, boolean z);

    protected DTrees(long addr) {
        super(addr);
    }

    public static DTrees __fromPtr__(long addr) {
        return new DTrees(addr);
    }

    public int getMaxCategories() {
        return getMaxCategories_0(this.nativeObj);
    }

    public void setMaxCategories(int val) {
        setMaxCategories_0(this.nativeObj, val);
    }

    public int getMaxDepth() {
        return getMaxDepth_0(this.nativeObj);
    }

    public void setMaxDepth(int val) {
        setMaxDepth_0(this.nativeObj, val);
    }

    public int getMinSampleCount() {
        return getMinSampleCount_0(this.nativeObj);
    }

    public void setMinSampleCount(int val) {
        setMinSampleCount_0(this.nativeObj, val);
    }

    public int getCVFolds() {
        return getCVFolds_0(this.nativeObj);
    }

    public void setCVFolds(int val) {
        setCVFolds_0(this.nativeObj, val);
    }

    public boolean getUseSurrogates() {
        return getUseSurrogates_0(this.nativeObj);
    }

    public void setUseSurrogates(boolean val) {
        setUseSurrogates_0(this.nativeObj, val);
    }

    public boolean getUse1SERule() {
        return getUse1SERule_0(this.nativeObj);
    }

    public void setUse1SERule(boolean val) {
        setUse1SERule_0(this.nativeObj, val);
    }

    public boolean getTruncatePrunedTree() {
        return getTruncatePrunedTree_0(this.nativeObj);
    }

    public void setTruncatePrunedTree(boolean val) {
        setTruncatePrunedTree_0(this.nativeObj, val);
    }

    public float getRegressionAccuracy() {
        return getRegressionAccuracy_0(this.nativeObj);
    }

    public void setRegressionAccuracy(float val) {
        setRegressionAccuracy_0(this.nativeObj, val);
    }

    public Mat getPriors() {
        return new Mat(getPriors_0(this.nativeObj));
    }

    public void setPriors(Mat val) {
        setPriors_0(this.nativeObj, val.nativeObj);
    }

    public static DTrees create() {
        return __fromPtr__(create_0());
    }

    public static DTrees load(String filepath, String nodeName) {
        return __fromPtr__(load_0(filepath, nodeName));
    }

    public static DTrees load(String filepath) {
        return __fromPtr__(load_1(filepath));
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
