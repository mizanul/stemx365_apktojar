package org.opencv.phase_unwrapping;

import org.opencv.core.Mat;

public class HistogramPhaseUnwrapping extends PhaseUnwrapping {
    private static native long create_0(long j);

    private static native long create_1();

    private static native void delete(long j);

    private static native void getInverseReliabilityMap_0(long j, long j2);

    protected HistogramPhaseUnwrapping(long addr) {
        super(addr);
    }

    public static HistogramPhaseUnwrapping __fromPtr__(long addr) {
        return new HistogramPhaseUnwrapping(addr);
    }

    public static HistogramPhaseUnwrapping create(HistogramPhaseUnwrapping_Params parameters) {
        return __fromPtr__(create_0(parameters.nativeObj));
    }

    public static HistogramPhaseUnwrapping create() {
        return __fromPtr__(create_1());
    }

    public void getInverseReliabilityMap(Mat reliabilityMap) {
        getInverseReliabilityMap_0(this.nativeObj, reliabilityMap.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
