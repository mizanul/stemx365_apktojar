package org.opencv.photo;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.utils.Converters;

public class AlignMTB extends AlignExposures {
    private static native double[] calculateShift_0(long j, long j2, long j3);

    private static native void computeBitmaps_0(long j, long j2, long j3, long j4);

    private static native void delete(long j);

    private static native boolean getCut_0(long j);

    private static native int getExcludeRange_0(long j);

    private static native int getMaxBits_0(long j);

    private static native void process_0(long j, long j2, long j3, long j4, long j5);

    private static native void process_1(long j, long j2, long j3);

    private static native void setCut_0(long j, boolean z);

    private static native void setExcludeRange_0(long j, int i);

    private static native void setMaxBits_0(long j, int i);

    private static native void shiftMat_0(long j, long j2, long j3, double d, double d2);

    protected AlignMTB(long addr) {
        super(addr);
    }

    public static AlignMTB __fromPtr__(long addr) {
        return new AlignMTB(addr);
    }

    public void process(List<Mat> src2, List<Mat> dst, Mat times, Mat response) {
        process_0(this.nativeObj, Converters.vector_Mat_to_Mat(src2).nativeObj, Converters.vector_Mat_to_Mat(dst).nativeObj, times.nativeObj, response.nativeObj);
    }

    public void process(List<Mat> src2, List<Mat> dst) {
        process_1(this.nativeObj, Converters.vector_Mat_to_Mat(src2).nativeObj, Converters.vector_Mat_to_Mat(dst).nativeObj);
    }

    public Point calculateShift(Mat img0, Mat img1) {
        return new Point(calculateShift_0(this.nativeObj, img0.nativeObj, img1.nativeObj));
    }

    public void shiftMat(Mat src2, Mat dst, Point shift) {
        shiftMat_0(this.nativeObj, src2.nativeObj, dst.nativeObj, shift.f175x, shift.f176y);
    }

    public void computeBitmaps(Mat img, Mat tb, Mat eb) {
        computeBitmaps_0(this.nativeObj, img.nativeObj, tb.nativeObj, eb.nativeObj);
    }

    public int getMaxBits() {
        return getMaxBits_0(this.nativeObj);
    }

    public void setMaxBits(int max_bits) {
        setMaxBits_0(this.nativeObj, max_bits);
    }

    public int getExcludeRange() {
        return getExcludeRange_0(this.nativeObj);
    }

    public void setExcludeRange(int exclude_range) {
        setExcludeRange_0(this.nativeObj, exclude_range);
    }

    public boolean getCut() {
        return getCut_0(this.nativeObj);
    }

    public void setCut(boolean value) {
        setCut_0(this.nativeObj, value);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
