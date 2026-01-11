package org.opencv.ximgproc;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class FastLineDetector extends Algorithm {
    private static native void delete(long j);

    private static native void detect_0(long j, long j2, long j3);

    private static native void drawSegments_0(long j, long j2, long j3, boolean z, double d, double d2, double d3, double d4, int i);

    private static native void drawSegments_1(long j, long j2, long j3, boolean z, double d, double d2, double d3, double d4);

    private static native void drawSegments_2(long j, long j2, long j3, boolean z);

    private static native void drawSegments_3(long j, long j2, long j3);

    protected FastLineDetector(long addr) {
        super(addr);
    }

    public static FastLineDetector __fromPtr__(long addr) {
        return new FastLineDetector(addr);
    }

    public void detect(Mat image, Mat lines) {
        detect_0(this.nativeObj, image.nativeObj, lines.nativeObj);
    }

    public void drawSegments(Mat image, Mat lines, boolean draw_arrow, Scalar linecolor, int linethickness) {
        Scalar scalar = linecolor;
        drawSegments_0(this.nativeObj, image.nativeObj, lines.nativeObj, draw_arrow, scalar.val[0], scalar.val[1], scalar.val[2], scalar.val[3], linethickness);
    }

    public void drawSegments(Mat image, Mat lines, boolean draw_arrow, Scalar linecolor) {
        Scalar scalar = linecolor;
        drawSegments_1(this.nativeObj, image.nativeObj, lines.nativeObj, draw_arrow, scalar.val[0], scalar.val[1], scalar.val[2], scalar.val[3]);
    }

    public void drawSegments(Mat image, Mat lines, boolean draw_arrow) {
        drawSegments_2(this.nativeObj, image.nativeObj, lines.nativeObj, draw_arrow);
    }

    public void drawSegments(Mat image, Mat lines) {
        drawSegments_3(this.nativeObj, image.nativeObj, lines.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
