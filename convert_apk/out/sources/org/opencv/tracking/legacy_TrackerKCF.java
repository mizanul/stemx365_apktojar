package org.opencv.tracking;

public class legacy_TrackerKCF extends legacy_Tracker {
    private static native long create_0();

    private static native void delete(long j);

    protected legacy_TrackerKCF(long addr) {
        super(addr);
    }

    public static legacy_TrackerKCF __fromPtr__(long addr) {
        return new legacy_TrackerKCF(addr);
    }

    public static legacy_TrackerKCF create() {
        return __fromPtr__(create_0());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
