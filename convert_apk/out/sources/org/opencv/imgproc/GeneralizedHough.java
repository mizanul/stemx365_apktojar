package org.opencv.imgproc;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.Point;

public class GeneralizedHough extends Algorithm {
    private static native void delete(long j);

    private static native void detect_0(long j, long j2, long j3, long j4);

    private static native void detect_1(long j, long j2, long j3);

    private static native void detect_2(long j, long j2, long j3, long j4, long j5, long j6);

    private static native void detect_3(long j, long j2, long j3, long j4, long j5);

    private static native int getCannyHighThresh_0(long j);

    private static native int getCannyLowThresh_0(long j);

    private static native double getDp_0(long j);

    private static native int getMaxBufferSize_0(long j);

    private static native double getMinDist_0(long j);

    private static native void setCannyHighThresh_0(long j, int i);

    private static native void setCannyLowThresh_0(long j, int i);

    private static native void setDp_0(long j, double d);

    private static native void setMaxBufferSize_0(long j, int i);

    private static native void setMinDist_0(long j, double d);

    private static native void setTemplate_0(long j, long j2, double d, double d2);

    private static native void setTemplate_1(long j, long j2);

    private static native void setTemplate_2(long j, long j2, long j3, long j4, double d, double d2);

    private static native void setTemplate_3(long j, long j2, long j3, long j4);

    protected GeneralizedHough(long addr) {
        super(addr);
    }

    public static GeneralizedHough __fromPtr__(long addr) {
        return new GeneralizedHough(addr);
    }

    public void setTemplate(Mat templ, Point templCenter) {
        setTemplate_0(this.nativeObj, templ.nativeObj, templCenter.f175x, templCenter.f176y);
    }

    public void setTemplate(Mat templ) {
        setTemplate_1(this.nativeObj, templ.nativeObj);
    }

    public void setTemplate(Mat edges, Mat dx, Mat dy, Point templCenter) {
        Point point = templCenter;
        setTemplate_2(this.nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, point.f175x, point.f176y);
    }

    public void setTemplate(Mat edges, Mat dx, Mat dy) {
        setTemplate_3(this.nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj);
    }

    public void detect(Mat image, Mat positions, Mat votes) {
        detect_0(this.nativeObj, image.nativeObj, positions.nativeObj, votes.nativeObj);
    }

    public void detect(Mat image, Mat positions) {
        detect_1(this.nativeObj, image.nativeObj, positions.nativeObj);
    }

    public void detect(Mat edges, Mat dx, Mat dy, Mat positions, Mat votes) {
        detect_2(this.nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, positions.nativeObj, votes.nativeObj);
    }

    public void detect(Mat edges, Mat dx, Mat dy, Mat positions) {
        detect_3(this.nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, positions.nativeObj);
    }

    public void setCannyLowThresh(int cannyLowThresh) {
        setCannyLowThresh_0(this.nativeObj, cannyLowThresh);
    }

    public int getCannyLowThresh() {
        return getCannyLowThresh_0(this.nativeObj);
    }

    public void setCannyHighThresh(int cannyHighThresh) {
        setCannyHighThresh_0(this.nativeObj, cannyHighThresh);
    }

    public int getCannyHighThresh() {
        return getCannyHighThresh_0(this.nativeObj);
    }

    public void setMinDist(double minDist) {
        setMinDist_0(this.nativeObj, minDist);
    }

    public double getMinDist() {
        return getMinDist_0(this.nativeObj);
    }

    public void setDp(double dp) {
        setDp_0(this.nativeObj, dp);
    }

    public double getDp() {
        return getDp_0(this.nativeObj);
    }

    public void setMaxBufferSize(int maxBufferSize) {
        setMaxBufferSize_0(this.nativeObj, maxBufferSize);
    }

    public int getMaxBufferSize() {
        return getMaxBufferSize_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
