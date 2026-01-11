package org.opencv.imgproc;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class IntelligentScissorsMB {
    protected final long nativeObj;

    private static native long IntelligentScissorsMB_0();

    private static native long applyImageFeatures_0(long j, long j2, long j3, long j4, long j5);

    private static native long applyImageFeatures_1(long j, long j2, long j3, long j4);

    private static native long applyImage_0(long j, long j2);

    private static native void buildMap_0(long j, double d, double d2);

    private static native void delete(long j);

    private static native void getContour_0(long j, double d, double d2, long j2, boolean z);

    private static native void getContour_1(long j, double d, double d2, long j2);

    private static native long setEdgeFeatureCannyParameters_0(long j, double d, double d2, int i, boolean z);

    private static native long setEdgeFeatureCannyParameters_1(long j, double d, double d2, int i);

    private static native long setEdgeFeatureCannyParameters_2(long j, double d, double d2);

    private static native long setEdgeFeatureZeroCrossingParameters_0(long j, float f);

    private static native long setEdgeFeatureZeroCrossingParameters_1(long j);

    private static native long setGradientMagnitudeMaxLimit_0(long j, float f);

    private static native long setGradientMagnitudeMaxLimit_1(long j);

    private static native long setWeights_0(long j, float f, float f2, float f3);

    protected IntelligentScissorsMB(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static IntelligentScissorsMB __fromPtr__(long addr) {
        return new IntelligentScissorsMB(addr);
    }

    public IntelligentScissorsMB() {
        this.nativeObj = IntelligentScissorsMB_0();
    }

    public IntelligentScissorsMB setWeights(float weight_non_edge, float weight_gradient_direction, float weight_gradient_magnitude) {
        return new IntelligentScissorsMB(setWeights_0(this.nativeObj, weight_non_edge, weight_gradient_direction, weight_gradient_magnitude));
    }

    public IntelligentScissorsMB setGradientMagnitudeMaxLimit(float gradient_magnitude_threshold_max) {
        return new IntelligentScissorsMB(setGradientMagnitudeMaxLimit_0(this.nativeObj, gradient_magnitude_threshold_max));
    }

    public IntelligentScissorsMB setGradientMagnitudeMaxLimit() {
        return new IntelligentScissorsMB(setGradientMagnitudeMaxLimit_1(this.nativeObj));
    }

    public IntelligentScissorsMB setEdgeFeatureZeroCrossingParameters(float gradient_magnitude_min_value) {
        return new IntelligentScissorsMB(setEdgeFeatureZeroCrossingParameters_0(this.nativeObj, gradient_magnitude_min_value));
    }

    public IntelligentScissorsMB setEdgeFeatureZeroCrossingParameters() {
        return new IntelligentScissorsMB(setEdgeFeatureZeroCrossingParameters_1(this.nativeObj));
    }

    public IntelligentScissorsMB setEdgeFeatureCannyParameters(double threshold1, double threshold2, int apertureSize, boolean L2gradient) {
        return new IntelligentScissorsMB(setEdgeFeatureCannyParameters_0(this.nativeObj, threshold1, threshold2, apertureSize, L2gradient));
    }

    public IntelligentScissorsMB setEdgeFeatureCannyParameters(double threshold1, double threshold2, int apertureSize) {
        return new IntelligentScissorsMB(setEdgeFeatureCannyParameters_1(this.nativeObj, threshold1, threshold2, apertureSize));
    }

    public IntelligentScissorsMB setEdgeFeatureCannyParameters(double threshold1, double threshold2) {
        return new IntelligentScissorsMB(setEdgeFeatureCannyParameters_2(this.nativeObj, threshold1, threshold2));
    }

    public IntelligentScissorsMB applyImage(Mat image) {
        return new IntelligentScissorsMB(applyImage_0(this.nativeObj, image.nativeObj));
    }

    public IntelligentScissorsMB applyImageFeatures(Mat non_edge, Mat gradient_direction, Mat gradient_magnitude, Mat image) {
        return new IntelligentScissorsMB(applyImageFeatures_0(this.nativeObj, non_edge.nativeObj, gradient_direction.nativeObj, gradient_magnitude.nativeObj, image.nativeObj));
    }

    public IntelligentScissorsMB applyImageFeatures(Mat non_edge, Mat gradient_direction, Mat gradient_magnitude) {
        return new IntelligentScissorsMB(applyImageFeatures_1(this.nativeObj, non_edge.nativeObj, gradient_direction.nativeObj, gradient_magnitude.nativeObj));
    }

    public void buildMap(Point sourcePt) {
        buildMap_0(this.nativeObj, sourcePt.f175x, sourcePt.f176y);
    }

    public void getContour(Point targetPt, Mat contour, boolean backward) {
        getContour_0(this.nativeObj, targetPt.f175x, targetPt.f176y, contour.nativeObj, backward);
    }

    public void getContour(Point targetPt, Mat contour) {
        getContour_1(this.nativeObj, targetPt.f175x, targetPt.f176y, contour.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
