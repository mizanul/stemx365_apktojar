package org.opencv.features2d;

public class SimpleBlobDetector extends Feature2D {
    private static native long create_0(long j);

    private static native long create_1();

    private static native void delete(long j);

    private static native String getDefaultName_0(long j);

    protected SimpleBlobDetector(long addr) {
        super(addr);
    }

    public static SimpleBlobDetector __fromPtr__(long addr) {
        return new SimpleBlobDetector(addr);
    }

    public static SimpleBlobDetector create(SimpleBlobDetector_Params parameters) {
        return __fromPtr__(create_0(parameters.nativeObj));
    }

    public static SimpleBlobDetector create() {
        return __fromPtr__(create_1());
    }

    public String getDefaultName() {
        return getDefaultName_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
