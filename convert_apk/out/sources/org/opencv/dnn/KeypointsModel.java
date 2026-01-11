package org.opencv.dnn;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;

public class KeypointsModel extends Model {
    private static native long KeypointsModel_0(String str, String str2);

    private static native long KeypointsModel_1(String str);

    private static native long KeypointsModel_2(long j);

    private static native void delete(long j);

    private static native long estimate_0(long j, long j2, float f);

    private static native long estimate_1(long j, long j2);

    protected KeypointsModel(long addr) {
        super(addr);
    }

    public static KeypointsModel __fromPtr__(long addr) {
        return new KeypointsModel(addr);
    }

    public KeypointsModel(String model, String config) {
        super(KeypointsModel_0(model, config));
    }

    public KeypointsModel(String model) {
        super(KeypointsModel_1(model));
    }

    public KeypointsModel(Net network) {
        super(KeypointsModel_2(network.nativeObj));
    }

    public MatOfPoint2f estimate(Mat frame, float thresh) {
        return MatOfPoint2f.fromNativeAddr(estimate_0(this.nativeObj, frame.nativeObj, thresh));
    }

    public MatOfPoint2f estimate(Mat frame) {
        return MatOfPoint2f.fromNativeAddr(estimate_1(this.nativeObj, frame.nativeObj));
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
