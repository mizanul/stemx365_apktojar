package org.opencv.dnn;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;

public class DetectionModel extends Model {
    private static native long DetectionModel_0(String str, String str2);

    private static native long DetectionModel_1(String str);

    private static native long DetectionModel_2(long j);

    private static native void delete(long j);

    private static native void detect_0(long j, long j2, long j3, long j4, long j5, float f, float f2);

    private static native void detect_1(long j, long j2, long j3, long j4, long j5, float f);

    private static native void detect_2(long j, long j2, long j3, long j4, long j5);

    private static native boolean getNmsAcrossClasses_0(long j);

    private static native long setNmsAcrossClasses_0(long j, boolean z);

    protected DetectionModel(long addr) {
        super(addr);
    }

    public static DetectionModel __fromPtr__(long addr) {
        return new DetectionModel(addr);
    }

    public DetectionModel(String model, String config) {
        super(DetectionModel_0(model, config));
    }

    public DetectionModel(String model) {
        super(DetectionModel_1(model));
    }

    public DetectionModel(Net network) {
        super(DetectionModel_2(network.nativeObj));
    }

    public DetectionModel setNmsAcrossClasses(boolean value) {
        return new DetectionModel(setNmsAcrossClasses_0(this.nativeObj, value));
    }

    public boolean getNmsAcrossClasses() {
        return getNmsAcrossClasses_0(this.nativeObj);
    }

    public void detect(Mat frame, MatOfInt classIds, MatOfFloat confidences, MatOfRect boxes, float confThreshold, float nmsThreshold) {
        detect_0(this.nativeObj, frame.nativeObj, classIds.nativeObj, confidences.nativeObj, boxes.nativeObj, confThreshold, nmsThreshold);
    }

    public void detect(Mat frame, MatOfInt classIds, MatOfFloat confidences, MatOfRect boxes, float confThreshold) {
        detect_1(this.nativeObj, frame.nativeObj, classIds.nativeObj, confidences.nativeObj, boxes.nativeObj, confThreshold);
    }

    public void detect(Mat frame, MatOfInt classIds, MatOfFloat confidences, MatOfRect boxes) {
        detect_2(this.nativeObj, frame.nativeObj, classIds.nativeObj, confidences.nativeObj, boxes.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
