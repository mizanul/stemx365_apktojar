package org.opencv.ximgproc;

import org.opencv.core.Mat;

public class EdgeAwareInterpolator extends SparseMatchInterpolator {
    private static native void delete(long j);

    private static native float getFGSLambda_0(long j);

    private static native float getFGSSigma_0(long j);

    private static native int getK_0(long j);

    private static native float getLambda_0(long j);

    private static native float getSigma_0(long j);

    private static native boolean getUsePostProcessing_0(long j);

    private static native void setCostMap_0(long j, long j2);

    private static native void setFGSLambda_0(long j, float f);

    private static native void setFGSSigma_0(long j, float f);

    private static native void setK_0(long j, int i);

    private static native void setLambda_0(long j, float f);

    private static native void setSigma_0(long j, float f);

    private static native void setUsePostProcessing_0(long j, boolean z);

    protected EdgeAwareInterpolator(long addr) {
        super(addr);
    }

    public static EdgeAwareInterpolator __fromPtr__(long addr) {
        return new EdgeAwareInterpolator(addr);
    }

    public void setCostMap(Mat _costMap) {
        setCostMap_0(this.nativeObj, _costMap.nativeObj);
    }

    public void setK(int _k) {
        setK_0(this.nativeObj, _k);
    }

    public int getK() {
        return getK_0(this.nativeObj);
    }

    public void setSigma(float _sigma) {
        setSigma_0(this.nativeObj, _sigma);
    }

    public float getSigma() {
        return getSigma_0(this.nativeObj);
    }

    public void setLambda(float _lambda) {
        setLambda_0(this.nativeObj, _lambda);
    }

    public float getLambda() {
        return getLambda_0(this.nativeObj);
    }

    public void setUsePostProcessing(boolean _use_post_proc) {
        setUsePostProcessing_0(this.nativeObj, _use_post_proc);
    }

    public boolean getUsePostProcessing() {
        return getUsePostProcessing_0(this.nativeObj);
    }

    public void setFGSLambda(float _lambda) {
        setFGSLambda_0(this.nativeObj, _lambda);
    }

    public float getFGSLambda() {
        return getFGSLambda_0(this.nativeObj);
    }

    public void setFGSSigma(float _sigma) {
        setFGSSigma_0(this.nativeObj, _sigma);
    }

    public float getFGSSigma() {
        return getFGSSigma_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
