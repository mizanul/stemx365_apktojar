package org.opencv.dnn;

import org.opencv.core.Mat;

public class ClassificationModel extends Model {
    private static native long ClassificationModel_0(String str, String str2);

    private static native long ClassificationModel_1(String str);

    private static native long ClassificationModel_2(long j);

    private static native void classify_0(long j, long j2, double[] dArr, double[] dArr2);

    private static native void delete(long j);

    protected ClassificationModel(long addr) {
        super(addr);
    }

    public static ClassificationModel __fromPtr__(long addr) {
        return new ClassificationModel(addr);
    }

    public ClassificationModel(String model, String config) {
        super(ClassificationModel_0(model, config));
    }

    public ClassificationModel(String model) {
        super(ClassificationModel_1(model));
    }

    public ClassificationModel(Net network) {
        super(ClassificationModel_2(network.nativeObj));
    }

    public void classify(Mat frame, int[] classId, float[] conf) {
        double[] classId_out = new double[1];
        double[] conf_out = new double[1];
        classify_0(this.nativeObj, frame.nativeObj, classId_out, conf_out);
        if (classId != null) {
            classId[0] = (int) classId_out[0];
        }
        if (conf != null) {
            conf[0] = (float) conf_out[0];
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
