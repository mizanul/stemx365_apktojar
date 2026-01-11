package org.opencv.dnn;

public class TextDetectionModel_DB extends TextDetectionModel {
    private static native long TextDetectionModel_DB_0(long j);

    private static native long TextDetectionModel_DB_1(String str, String str2);

    private static native long TextDetectionModel_DB_2(String str);

    private static native void delete(long j);

    private static native float getBinaryThreshold_0(long j);

    private static native int getMaxCandidates_0(long j);

    private static native float getPolygonThreshold_0(long j);

    private static native double getUnclipRatio_0(long j);

    private static native long setBinaryThreshold_0(long j, float f);

    private static native long setMaxCandidates_0(long j, int i);

    private static native long setPolygonThreshold_0(long j, float f);

    private static native long setUnclipRatio_0(long j, double d);

    protected TextDetectionModel_DB(long addr) {
        super(addr);
    }

    public static TextDetectionModel_DB __fromPtr__(long addr) {
        return new TextDetectionModel_DB(addr);
    }

    public TextDetectionModel_DB(Net network) {
        super(TextDetectionModel_DB_0(network.nativeObj));
    }

    public TextDetectionModel_DB(String model, String config) {
        super(TextDetectionModel_DB_1(model, config));
    }

    public TextDetectionModel_DB(String model) {
        super(TextDetectionModel_DB_2(model));
    }

    public TextDetectionModel_DB setBinaryThreshold(float binaryThreshold) {
        return new TextDetectionModel_DB(setBinaryThreshold_0(this.nativeObj, binaryThreshold));
    }

    public float getBinaryThreshold() {
        return getBinaryThreshold_0(this.nativeObj);
    }

    public TextDetectionModel_DB setPolygonThreshold(float polygonThreshold) {
        return new TextDetectionModel_DB(setPolygonThreshold_0(this.nativeObj, polygonThreshold));
    }

    public float getPolygonThreshold() {
        return getPolygonThreshold_0(this.nativeObj);
    }

    public TextDetectionModel_DB setUnclipRatio(double unclipRatio) {
        return new TextDetectionModel_DB(setUnclipRatio_0(this.nativeObj, unclipRatio));
    }

    public double getUnclipRatio() {
        return getUnclipRatio_0(this.nativeObj);
    }

    public TextDetectionModel_DB setMaxCandidates(int maxCandidates) {
        return new TextDetectionModel_DB(setMaxCandidates_0(this.nativeObj, maxCandidates));
    }

    public int getMaxCandidates() {
        return getMaxCandidates_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
