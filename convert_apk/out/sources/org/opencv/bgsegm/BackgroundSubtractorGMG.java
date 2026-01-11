package org.opencv.bgsegm;

import org.opencv.video.BackgroundSubtractor;

public class BackgroundSubtractorGMG extends BackgroundSubtractor {
    private static native void delete(long j);

    private static native double getBackgroundPrior_0(long j);

    private static native double getDecisionThreshold_0(long j);

    private static native double getDefaultLearningRate_0(long j);

    private static native int getMaxFeatures_0(long j);

    private static native double getMaxVal_0(long j);

    private static native double getMinVal_0(long j);

    private static native int getNumFrames_0(long j);

    private static native int getQuantizationLevels_0(long j);

    private static native int getSmoothingRadius_0(long j);

    private static native boolean getUpdateBackgroundModel_0(long j);

    private static native void setBackgroundPrior_0(long j, double d);

    private static native void setDecisionThreshold_0(long j, double d);

    private static native void setDefaultLearningRate_0(long j, double d);

    private static native void setMaxFeatures_0(long j, int i);

    private static native void setMaxVal_0(long j, double d);

    private static native void setMinVal_0(long j, double d);

    private static native void setNumFrames_0(long j, int i);

    private static native void setQuantizationLevels_0(long j, int i);

    private static native void setSmoothingRadius_0(long j, int i);

    private static native void setUpdateBackgroundModel_0(long j, boolean z);

    protected BackgroundSubtractorGMG(long addr) {
        super(addr);
    }

    public static BackgroundSubtractorGMG __fromPtr__(long addr) {
        return new BackgroundSubtractorGMG(addr);
    }

    public int getMaxFeatures() {
        return getMaxFeatures_0(this.nativeObj);
    }

    public void setMaxFeatures(int maxFeatures) {
        setMaxFeatures_0(this.nativeObj, maxFeatures);
    }

    public double getDefaultLearningRate() {
        return getDefaultLearningRate_0(this.nativeObj);
    }

    public void setDefaultLearningRate(double lr) {
        setDefaultLearningRate_0(this.nativeObj, lr);
    }

    public int getNumFrames() {
        return getNumFrames_0(this.nativeObj);
    }

    public void setNumFrames(int nframes) {
        setNumFrames_0(this.nativeObj, nframes);
    }

    public int getQuantizationLevels() {
        return getQuantizationLevels_0(this.nativeObj);
    }

    public void setQuantizationLevels(int nlevels) {
        setQuantizationLevels_0(this.nativeObj, nlevels);
    }

    public double getBackgroundPrior() {
        return getBackgroundPrior_0(this.nativeObj);
    }

    public void setBackgroundPrior(double bgprior) {
        setBackgroundPrior_0(this.nativeObj, bgprior);
    }

    public int getSmoothingRadius() {
        return getSmoothingRadius_0(this.nativeObj);
    }

    public void setSmoothingRadius(int radius) {
        setSmoothingRadius_0(this.nativeObj, radius);
    }

    public double getDecisionThreshold() {
        return getDecisionThreshold_0(this.nativeObj);
    }

    public void setDecisionThreshold(double thresh) {
        setDecisionThreshold_0(this.nativeObj, thresh);
    }

    public boolean getUpdateBackgroundModel() {
        return getUpdateBackgroundModel_0(this.nativeObj);
    }

    public void setUpdateBackgroundModel(boolean update) {
        setUpdateBackgroundModel_0(this.nativeObj, update);
    }

    public double getMinVal() {
        return getMinVal_0(this.nativeObj);
    }

    public void setMinVal(double val) {
        setMinVal_0(this.nativeObj, val);
    }

    public double getMaxVal() {
        return getMaxVal_0(this.nativeObj);
    }

    public void setMaxVal(double val) {
        setMaxVal_0(this.nativeObj, val);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
