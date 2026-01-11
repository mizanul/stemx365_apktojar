package org.opencv.ximgproc;

public class EdgeDrawing_Params {
    protected final long nativeObj;

    private static native long EdgeDrawing_Params_0();

    private static native void delete(long j);

    private static native int get_AnchorThresholdValue_0(long j);

    private static native int get_EdgeDetectionOperator_0(long j);

    private static native int get_GradientThresholdValue_0(long j);

    private static native double get_LineFitErrorThreshold_0(long j);

    private static native double get_MaxDistanceBetweenTwoLines_0(long j);

    private static native double get_MaxErrorThreshold_0(long j);

    private static native int get_MinLineLength_0(long j);

    private static native int get_MinPathLength_0(long j);

    private static native boolean get_NFAValidation_0(long j);

    private static native boolean get_PFmode_0(long j);

    private static native int get_ScanInterval_0(long j);

    private static native float get_Sigma_0(long j);

    private static native boolean get_SumFlag_0(long j);

    private static native void set_AnchorThresholdValue_0(long j, int i);

    private static native void set_EdgeDetectionOperator_0(long j, int i);

    private static native void set_GradientThresholdValue_0(long j, int i);

    private static native void set_LineFitErrorThreshold_0(long j, double d);

    private static native void set_MaxDistanceBetweenTwoLines_0(long j, double d);

    private static native void set_MaxErrorThreshold_0(long j, double d);

    private static native void set_MinLineLength_0(long j, int i);

    private static native void set_MinPathLength_0(long j, int i);

    private static native void set_NFAValidation_0(long j, boolean z);

    private static native void set_PFmode_0(long j, boolean z);

    private static native void set_ScanInterval_0(long j, int i);

    private static native void set_Sigma_0(long j, float f);

    private static native void set_SumFlag_0(long j, boolean z);

    protected EdgeDrawing_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static EdgeDrawing_Params __fromPtr__(long addr) {
        return new EdgeDrawing_Params(addr);
    }

    public EdgeDrawing_Params() {
        this.nativeObj = EdgeDrawing_Params_0();
    }

    public boolean get_PFmode() {
        return get_PFmode_0(this.nativeObj);
    }

    public void set_PFmode(boolean PFmode) {
        set_PFmode_0(this.nativeObj, PFmode);
    }

    public int get_EdgeDetectionOperator() {
        return get_EdgeDetectionOperator_0(this.nativeObj);
    }

    public void set_EdgeDetectionOperator(int EdgeDetectionOperator) {
        set_EdgeDetectionOperator_0(this.nativeObj, EdgeDetectionOperator);
    }

    public int get_GradientThresholdValue() {
        return get_GradientThresholdValue_0(this.nativeObj);
    }

    public void set_GradientThresholdValue(int GradientThresholdValue) {
        set_GradientThresholdValue_0(this.nativeObj, GradientThresholdValue);
    }

    public int get_AnchorThresholdValue() {
        return get_AnchorThresholdValue_0(this.nativeObj);
    }

    public void set_AnchorThresholdValue(int AnchorThresholdValue) {
        set_AnchorThresholdValue_0(this.nativeObj, AnchorThresholdValue);
    }

    public int get_ScanInterval() {
        return get_ScanInterval_0(this.nativeObj);
    }

    public void set_ScanInterval(int ScanInterval) {
        set_ScanInterval_0(this.nativeObj, ScanInterval);
    }

    public int get_MinPathLength() {
        return get_MinPathLength_0(this.nativeObj);
    }

    public void set_MinPathLength(int MinPathLength) {
        set_MinPathLength_0(this.nativeObj, MinPathLength);
    }

    public float get_Sigma() {
        return get_Sigma_0(this.nativeObj);
    }

    public void set_Sigma(float Sigma) {
        set_Sigma_0(this.nativeObj, Sigma);
    }

    public boolean get_SumFlag() {
        return get_SumFlag_0(this.nativeObj);
    }

    public void set_SumFlag(boolean SumFlag) {
        set_SumFlag_0(this.nativeObj, SumFlag);
    }

    public boolean get_NFAValidation() {
        return get_NFAValidation_0(this.nativeObj);
    }

    public void set_NFAValidation(boolean NFAValidation) {
        set_NFAValidation_0(this.nativeObj, NFAValidation);
    }

    public int get_MinLineLength() {
        return get_MinLineLength_0(this.nativeObj);
    }

    public void set_MinLineLength(int MinLineLength) {
        set_MinLineLength_0(this.nativeObj, MinLineLength);
    }

    public double get_MaxDistanceBetweenTwoLines() {
        return get_MaxDistanceBetweenTwoLines_0(this.nativeObj);
    }

    public void set_MaxDistanceBetweenTwoLines(double MaxDistanceBetweenTwoLines) {
        set_MaxDistanceBetweenTwoLines_0(this.nativeObj, MaxDistanceBetweenTwoLines);
    }

    public double get_LineFitErrorThreshold() {
        return get_LineFitErrorThreshold_0(this.nativeObj);
    }

    public void set_LineFitErrorThreshold(double LineFitErrorThreshold) {
        set_LineFitErrorThreshold_0(this.nativeObj, LineFitErrorThreshold);
    }

    public double get_MaxErrorThreshold() {
        return get_MaxErrorThreshold_0(this.nativeObj);
    }

    public void set_MaxErrorThreshold(double MaxErrorThreshold) {
        set_MaxErrorThreshold_0(this.nativeObj, MaxErrorThreshold);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
