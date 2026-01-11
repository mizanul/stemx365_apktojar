package org.opencv.ximgproc;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.utils.Converters;

public class EdgeDrawing extends Algorithm {
    public static final int LSD = 3;
    public static final int PREWITT = 0;
    public static final int SCHARR = 2;
    public static final int SOBEL = 1;

    private static native void delete(long j);

    private static native void detectEdges_0(long j, long j2);

    private static native void detectEllipses_0(long j, long j2);

    private static native void detectLines_0(long j, long j2);

    private static native void getEdgeImage_0(long j, long j2);

    private static native void getGradientImage_0(long j, long j2);

    private static native long getSegments_0(long j);

    private static native void setParams_0(long j, long j2);

    protected EdgeDrawing(long addr) {
        super(addr);
    }

    public static EdgeDrawing __fromPtr__(long addr) {
        return new EdgeDrawing(addr);
    }

    public void detectEdges(Mat src2) {
        detectEdges_0(this.nativeObj, src2.nativeObj);
    }

    public void getEdgeImage(Mat dst) {
        getEdgeImage_0(this.nativeObj, dst.nativeObj);
    }

    public void getGradientImage(Mat dst) {
        getGradientImage_0(this.nativeObj, dst.nativeObj);
    }

    public List<MatOfPoint> getSegments() {
        List<MatOfPoint> retVal = new ArrayList<>();
        Converters.Mat_to_vector_vector_Point(new Mat(getSegments_0(this.nativeObj)), retVal);
        return retVal;
    }

    public void detectLines(Mat lines) {
        detectLines_0(this.nativeObj, lines.nativeObj);
    }

    public void detectEllipses(Mat ellipses) {
        detectEllipses_0(this.nativeObj, ellipses.nativeObj);
    }

    public void setParams(EdgeDrawing_Params parameters) {
        setParams_0(this.nativeObj, parameters.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
