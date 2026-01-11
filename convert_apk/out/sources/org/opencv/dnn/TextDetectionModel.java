package org.opencv.dnn;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRotatedRect;
import org.opencv.utils.Converters;

public class TextDetectionModel extends Model {
    private static native void delete(long j);

    private static native void detectTextRectangles_0(long j, long j2, long j3, long j4);

    private static native void detectTextRectangles_1(long j, long j2, long j3);

    private static native void detect_0(long j, long j2, long j3, long j4);

    private static native void detect_1(long j, long j2, long j3);

    protected TextDetectionModel(long addr) {
        super(addr);
    }

    public static TextDetectionModel __fromPtr__(long addr) {
        return new TextDetectionModel(addr);
    }

    public void detect(Mat frame, List<MatOfPoint> detections, MatOfFloat confidences) {
        Mat detections_mat = new Mat();
        detect_0(this.nativeObj, frame.nativeObj, detections_mat.nativeObj, confidences.nativeObj);
        Converters.Mat_to_vector_vector_Point(detections_mat, detections);
        detections_mat.release();
    }

    public void detect(Mat frame, List<MatOfPoint> detections) {
        Mat detections_mat = new Mat();
        detect_1(this.nativeObj, frame.nativeObj, detections_mat.nativeObj);
        Converters.Mat_to_vector_vector_Point(detections_mat, detections);
        detections_mat.release();
    }

    public void detectTextRectangles(Mat frame, MatOfRotatedRect detections, MatOfFloat confidences) {
        detectTextRectangles_0(this.nativeObj, frame.nativeObj, detections.nativeObj, confidences.nativeObj);
    }

    public void detectTextRectangles(Mat frame, MatOfRotatedRect detections) {
        detectTextRectangles_1(this.nativeObj, frame.nativeObj, detections.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
