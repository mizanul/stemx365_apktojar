package org.opencv.dnn;

public class TextDetectionModel_EAST extends TextDetectionModel {
    private static native long TextDetectionModel_EAST_0(long j);

    private static native long TextDetectionModel_EAST_1(String str, String str2);

    private static native long TextDetectionModel_EAST_2(String str);

    private static native void delete(long j);

    private static native float getConfidenceThreshold_0(long j);

    private static native float getNMSThreshold_0(long j);

    private static native long setConfidenceThreshold_0(long j, float f);

    private static native long setNMSThreshold_0(long j, float f);

    protected TextDetectionModel_EAST(long addr) {
        super(addr);
    }

    public static TextDetectionModel_EAST __fromPtr__(long addr) {
        return new TextDetectionModel_EAST(addr);
    }

    public TextDetectionModel_EAST(Net network) {
        super(TextDetectionModel_EAST_0(network.nativeObj));
    }

    public TextDetectionModel_EAST(String model, String config) {
        super(TextDetectionModel_EAST_1(model, config));
    }

    public TextDetectionModel_EAST(String model) {
        super(TextDetectionModel_EAST_2(model));
    }

    public TextDetectionModel_EAST setConfidenceThreshold(float confThreshold) {
        return new TextDetectionModel_EAST(setConfidenceThreshold_0(this.nativeObj, confThreshold));
    }

    public float getConfidenceThreshold() {
        return getConfidenceThreshold_0(this.nativeObj);
    }

    public TextDetectionModel_EAST setNMSThreshold(float nmsThreshold) {
        return new TextDetectionModel_EAST(setNMSThreshold_0(this.nativeObj, nmsThreshold));
    }

    public float getNMSThreshold() {
        return getNMSThreshold_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
